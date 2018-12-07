package providers.mails;

import com.typesafe.config.Config;
import org.javalite.http.Http;
import org.javalite.http.HttpException;
import org.javalite.http.Post;
import org.json.JSONObject;
import exceptions.StatusErrorException;
import spark.Session;
import com.typesafe.config.ConfigFactory;

public class ResetProvider {
  private static Config constants = ConfigFactory.defaultApplication().getConfig("services.mail");

  public static String send(Session session, int userId, String resetKey, String email, String language) throws HttpException, StatusErrorException, Exception{
    String rpta = "";
    try {
      JSONObject data = new JSONObject();
      data.put("user_id", userId);
      data.put("reset_key", resetKey);
      data.put("lang", language);
      data.put("to", email);
      data.put("base_url", constants.getString("validation_service_url"));
      Post req = Http.post(constants.getString("url") + "reset_password")
        .param("data", data.toString())
        .header(constants.getString("csrf_key"), constants.getString("csrf_value"));
      rpta = req.text();
      if(req.responseCode() != 200){
        String lang = session.attribute("lang");
        String error = ConfigFactory.parseResources("errors/provider_mails_reset.conf").getConfig(lang).getString("list-neq200") + "::" + rpta;
        throw new StatusErrorException(error, null); 
      }
		} catch (HttpException e) {
			//e.printStackTrace();
      String lang = session.attribute("lang");
      String error = ConfigFactory.parseResources("errors/provider_mails_reset.conf").getConfig(lang).getString("http-exception") + "::" + e.toString();
      throw new HttpException(error, e);  
    } catch (Exception e) {
      //e.printStackTrace();
      String lang = session.attribute("lang");
      String error = ConfigFactory.parseResources("errors/provider_mails_reset.conf").getConfig(lang).getString("exception") + "::" + e.toString();
      throw new Exception(error, e);  
    } 
    return rpta;
  }
}