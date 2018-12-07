package providers.managment;

import com.typesafe.config.Config;
import org.javalite.http.Http;
import org.javalite.http.HttpException;
import org.javalite.http.Get;
import org.javalite.http.Post;
import org.json.JSONObject;
import exceptions.StatusErrorException;
import spark.Session;
import com.typesafe.config.ConfigFactory;

public class FieldProvider {
  private static Config constants = ConfigFactory.defaultApplication().getConfig("services.managment");

  public static String listById(Session session, String clientId, String language) throws HttpException, StatusErrorException, Exception{
    String rpta = "";
    try {
      Get req = Http.get(constants.getString("url") + "field/list/" + clientId)
        .header(constants.getString("csrf_key"), constants.getString("csrf_value"));
      rpta = req.text();
      if(req.responseCode() != 200){
        String lang = session.attribute("lang");
        String error = ConfigFactory.parseResources("errors/provider_managment_field.conf").getConfig(lang).getString("list-neq200") + "::" + rpta;
        throw new StatusErrorException(error, null); 
      }
		} catch (HttpException e) {
			//e.printStackTrace();
      String lang = session.attribute("lang");
      String error = ConfigFactory.parseResources("errors/provider_managment_field.conf").getConfig(lang).getString("list-http-exception") + "::" + e.toString();
      throw new HttpException(error, e);  
    } catch (Exception e) {
      //e.printStackTrace();
      String lang = session.attribute("lang");
      String error = ConfigFactory.parseResources("errors/provider_managment_field.conf").getConfig(lang).getString("list-exception") + "::" + e.toString();
      throw new Exception(error, e);  
    } 
    return rpta;
  }

  public static String validateUser(Session session, String userId, String fieldId, String language) throws HttpException, StatusErrorException, Exception{
    String rpta = "";
    try {
      JSONObject data = new JSONObject();
      data.put("user_id", userId);
      data.put("field_id", fieldId);
      Post req = Http.post(constants.getString("url") + "field/validate_user")
        .param("data", data.toString())
        .header(constants.getString("csrf_key"), constants.getString("csrf_value"));
      rpta = req.text();
      if(req.responseCode() != 200){
        String lang = session.attribute("lang");
        String error = ConfigFactory.parseResources("errors/provider_managment_field.conf").getConfig(lang).getString("validate-user-neq200") + "::" + rpta;
        throw new StatusErrorException(error, null); 
      }
		} catch (HttpException e) {
			//e.printStackTrace();
      String lang = session.attribute("lang");
      String error = ConfigFactory.parseResources("errors/provider_managment_field.conf").getConfig(lang).getString("validate-user-http-exception") + "::" + e.toString();
      throw new HttpException(error, e);  
    } catch (Exception e) {
      //e.printStackTrace();
      String lang = session.attribute("lang");
      String error = ConfigFactory.parseResources("errors/provider_managment_field.conf").getConfig(lang).getString("validate-user-exception") + "::" + e.toString();
      throw new Exception(error, e);  
    } 
    return rpta;
  }
}