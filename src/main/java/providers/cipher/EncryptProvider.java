package providers.cipher;

import com.typesafe.config.Config;
import org.javalite.http.Http;
import org.javalite.http.HttpException;
import org.javalite.http.Post;
import exceptions.StatusErrorException;
import com.typesafe.config.ConfigFactory;

public class EncryptProvider {
  private static Config constants = ConfigFactory.defaultApplication();

  public static String encrypt(String data, String lang) throws HttpException, StatusErrorException, Exception{
    String rpta = "";
    try {
      Post req = Http.post(constants.getString("services.cipher.url") + "encrypt")
        .param("data", data)
        .param("key", constants.getString("key"))
        .header(constants.getString("services.cipher.csrf_key"), constants.getString("services.cipher.csrf_value"));
      rpta = req.text();
		} catch (HttpException e) {
			//e.printStackTrace();
      String error = ConfigFactory.parseResources("errors/provider_cipher_encrypt.conf").getConfig(lang).getString("http-exception") + "::" + e.toString();
      throw new HttpException(error, e);  
    } catch (Exception e) {
      //e.printStackTrace();
      String error = ConfigFactory.parseResources("errors/provider_cipher_encrypt.conf").getConfig(lang).getString("exception") + "::" + e.toString();
      throw new Exception(error, e);  
    } 
    return rpta;
  }
}