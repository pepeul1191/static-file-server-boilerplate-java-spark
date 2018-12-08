package providers.mails;

import com.typesafe.config.Config;
import org.javalite.http.Http;
import org.javalite.http.HttpException;
import org.javalite.http.Post;
import org.json.JSONObject;
import exceptions.StatusErrorException;
import spark.Session;
import com.typesafe.config.ConfigFactory;

public class WellcomeProvider {
  private static Config constants = ConfigFactory.defaultApplication().getConfig("services.mail");

  public static String send(String name, int userId, String activationtKey, String lang, String email) throws HttpException, StatusErrorException, Exception{
    String rpta = "";
    try {
      JSONObject data = new JSONObject();
      data.put("name", name);
      data.put("user_id", userId);
      data.put("activation_key", activationtKey);
      data.put("lang", lang);
      data.put("to", email);
      data.put("base_url", constants.getString("validation_service_url"));
      Post req = Http.post(constants.getString("url") + "wellcome_client")
        .param("data", data.toString())
        .header(constants.getString("csrf_key"), constants.getString("csrf_value"));
      rpta = req.text();
      if(req.responseCode() != 200){        
        String error = ConfigFactory.parseResources("errors/provider_mails_wellcome.conf").getConfig(lang).getString("list-neq200") + "::" + rpta;
        throw new StatusErrorException(error, null); 
      }
		} catch (HttpException e) {
			//e.printStackTrace();      
      String error = ConfigFactory.parseResources("errors/provider_mails_wellcome.conf").getConfig(lang).getString("http-exception") + "::" + e.toString();
      throw new HttpException(error, e);  
    } catch (Exception e) {
      //e.printStackTrace();      
      String error = ConfigFactory.parseResources("errors/provider_mails_wellcome.conf").getConfig(lang).getString("exception") + "::" + e.toString();
      System.out.println(error);
      throw new Exception("email-error", e);  
    } 
    return rpta;
  }
}