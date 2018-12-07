package providers.access;

import com.typesafe.config.Config;
import org.javalite.http.Http;
import org.javalite.http.HttpException;
import org.javalite.http.Post;
import exceptions.StatusErrorException;
import spark.Session;
import com.typesafe.config.ConfigFactory;

public class KeyProvider {
  private static Config constants = ConfigFactory.defaultApplication().getConfig("services.access");

  public static String resetByEmail(Session session, String email) throws HttpException, StatusErrorException, Exception{
    String rpta = "";
    try {
      Post req = Http.post(constants.getString("url") + "key/reset_by_email")
        .param("email", email)
        .header(constants.getString("csrf_key"), constants.getString("csrf_value"));
      rpta = req.text();
      if (!rpta.equalsIgnoreCase("user_not_found") && req.responseCode() != 200){
        String lang = session.attribute("lang");
        String error = ConfigFactory.parseResources("errors/provider_access_key.conf").getConfig(lang).getString("list-neq200") + "::" + rpta;
        throw new StatusErrorException(error, null); 
      }
		} catch (HttpException e) {
			//e.printStackTrace();
      String lang = session.attribute("lang");
      String error = ConfigFactory.parseResources("errors/provider_access_key.conf").getConfig(lang).getString("http-exception") + "::" + e.toString();
      throw new HttpException(error, e);  
    } catch (Exception e) {
      //e.printStackTrace();
      String lang = session.attribute("lang");
      String error = ConfigFactory.parseResources("errors/provider_cipher_encrypt.conf").getConfig(lang).getString("exception") + "::" + e.toString();
      throw new Exception(error, e);  
    } 
    return rpta;
  }

  public static String resetValidate(Session session, int userId, String resetKey) throws HttpException, StatusErrorException, Exception{
    String rpta = "";
    try {
      Post req = Http.post(constants.getString("url") + "key/reset/validate")
        .param("user_id", userId + "")
        .param("reset_key", resetKey)
        .header(constants.getString("csrf_key"), constants.getString("csrf_value"));
      rpta = req.text();
		} catch (HttpException e) {
			//e.printStackTrace();
      String lang = session.attribute("lang");
      String error = ConfigFactory.parseResources("errors/provider_access_key.conf").getConfig(lang).getString("http-exception") + "::" + e.toString();
      throw new HttpException(error, e);  
    } catch (Exception e) {
      //e.printStackTrace();
      String lang = session.attribute("lang");
      String error = ConfigFactory.parseResources("errors/provider_cipher_encrypt.conf").getConfig(lang).getString("exception") + "::" + e.toString();
      throw new Exception(error, e);  
    } 
    return rpta;
  }

  public static String activationValidate(Session session, int userId, String activationKey) throws HttpException, StatusErrorException, Exception{
    String rpta = "";
    try {
      Post req = Http.post(constants.getString("url") + "key/activation/validate")
        .param("user_id", userId + "")
        .param("activation_key", activationKey)
        .header(constants.getString("csrf_key"), constants.getString("csrf_value"));
      rpta = req.text();
		} catch (HttpException e) {
			//e.printStackTrace();
      String lang = session.attribute("lang");
      String error = ConfigFactory.parseResources("errors/provider_access_key.conf").getConfig(lang).getString("http-exception") + "::" + e.toString();
      throw new HttpException(error, e);  
    } catch (Exception e) {
      //e.printStackTrace();
      String lang = session.attribute("lang");
      String error = ConfigFactory.parseResources("errors/provider_access_key.conf").getConfig(lang).getString("exception") + "::" + e.toString();
      throw new Exception(error, e);  
    } 
    return rpta;
  }

  public static String updateResetByUserId(Session session, String userId) throws HttpException, StatusErrorException, Exception{
    String rpta = "";
    try {
      Post req = Http.post(constants.getString("url") + "key/reset/update_by_user_id")
        .param("user_id", userId)
        .header(constants.getString("csrf_key"), constants.getString("csrf_value"));
      rpta = req.text();
      if (!rpta.equalsIgnoreCase("user_not_found") && req.responseCode() != 200){
        String lang = session.attribute("lang");
        String error = ConfigFactory.parseResources("errors/provider_access_key.conf").getConfig(lang).getString("list-neq200") + "::" + rpta;
        throw new StatusErrorException(error, null); 
      }
		} catch (HttpException e) {
			//e.printStackTrace();
      String lang = session.attribute("lang");
      String error = ConfigFactory.parseResources("errors/provider_access_key.conf").getConfig(lang).getString("http-exception") + "::" + e.toString();
      throw new HttpException(error, e);  
    } catch (Exception e) {
      //e.printStackTrace();
      String lang = session.attribute("lang");
      String error = ConfigFactory.parseResources("errors/provider_cipher_encrypt.conf").getConfig(lang).getString("exception") + "::" + e.toString();
      throw new Exception(error, e);  
    } 
    return rpta;
  }

  public static String updateActivationByUserId(Session session, String userId, String stateId) throws HttpException, StatusErrorException, Exception{
    String rpta = "";
    try {
      Post req = Http.post(constants.getString("url") + "key/activation/update_by_user_id")
        .param("user_id", userId)
        .param("user_state_id", stateId)
        .header(constants.getString("csrf_key"), constants.getString("csrf_value"));
      rpta = req.text();
      if (!rpta.equalsIgnoreCase("user_not_found") && req.responseCode() != 200){
        String lang = session.attribute("lang");
        String error = ConfigFactory.parseResources("errors/provider_access_key.conf").getConfig(lang).getString("list-neq200") + "::" + rpta;
        throw new StatusErrorException(error, null); 
      }
		} catch (HttpException e) {
			//e.printStackTrace();
      String lang = session.attribute("lang");
      String error = ConfigFactory.parseResources("errors/provider_access_key.conf").getConfig(lang).getString("http-exception") + "::" + e.toString();
      throw new HttpException(error, e);  
    } catch (Exception e) {
      //e.printStackTrace();
      String lang = session.attribute("lang");
      String error = ConfigFactory.parseResources("errors/provider_cipher_encrypt.conf").getConfig(lang).getString("exception") + "::" + e.toString();
      throw new Exception(error, e);  
    } 
    return rpta;
  }
}