package providers.access;

import com.typesafe.config.Config;
import org.javalite.http.Get;
import org.javalite.http.Http;
import org.javalite.http.HttpException;
import org.javalite.http.Post;
import exceptions.StatusErrorException;
import spark.Session;
import com.typesafe.config.ConfigFactory;

public class UserProvider {
  private static Config constants = ConfigFactory.defaultApplication().getConfig("services.access");
  
  public static String create(String lang, String user, String pass, String email, String systemId) throws HttpException, StatusErrorException, Exception{
    String rpta = "";
    try {
      Post req = Http.post(constants.getString("url") + "user/create")
        .param("user", user)
        .param("pass", pass)
        .param("email", email)
        .param("system_id", systemId)
        .header(constants.getString("csrf_key"), constants.getString("csrf_value"));
      int responseCode = req.responseCode();
      rpta = req.text();
      if (responseCode == 501){  
        String error = "repeated";
        throw new StatusErrorException(error, null);  
      }else if (responseCode != 200 && responseCode != 501){   
        String error = ConfigFactory.parseResources("errors/provider_access_user.conf").getConfig(lang).getString("create.exception") + "::" + rpta;
        throw new StatusErrorException(error, null);    
      }
		} catch (HttpException e) {
			//e.printStackTrace();
      String error = ConfigFactory.parseResources("errors/provider_access_user.conf").getConfig(lang).getString("create.http-exception") + "::" + e.toString();
      throw new HttpException(error, e);  
    } catch (StatusErrorException e){
      //e.printStackTrace();
      throw new StatusErrorException(e.getMessage(), null);
    } catch (Exception e) {
      //e.printStackTrace();
      String error = ConfigFactory.parseResources("errors/provider_access_user.conf").getConfig(lang).getString("create.exception") + "::" + e.toString();
      throw new Exception(error, e);  
    }
    return rpta;
  }

  public static String delete(int userId) {
    String rpta = "";
    try {
      Post req = Http.post(constants.getString("url") + "user/delete/" + userId)
        .header(constants.getString("csrf_key"), constants.getString("csrf_value"));
      req.responseCode();
    } catch (Exception e) {
      //e.printStackTrace();
    }
    return rpta;
  }
  
  public static String userSystemValidate(String lang, String user, String pass,  int systemId) throws HttpException, StatusErrorException, Exception{
    String rpta = "";
    try {
      Post req = Http.post(constants.getString("url") + "user/system/validate")
        .param("user", user)
        .param("pass", pass)
        .param("system_id", systemId + "")
        .header(constants.getString("csrf_key"), constants.getString("csrf_value"));
      int responseCode = req.responseCode();
      rpta = req.text();
      if (responseCode != 200){   
        String error = ConfigFactory.parseResources("errors/provider_access_user.conf").getConfig(lang).getString("user_system_validate.neq200") + "::" + rpta;
        throw new StatusErrorException(error, null);    
      }
		} catch (HttpException e) {
			//e.printStackTrace();
      String error = ConfigFactory.parseResources("errors/provider_access_user.conf").getConfig(lang).getString("user_system_validate.http-exception") + "::" + e.toString();
      throw new HttpException(error, e);  
    } catch (StatusErrorException e){
      //e.printStackTrace();
      throw new StatusErrorException(e.getMessage(), null);
    } catch (Exception e) {
      //e.printStackTrace();
      String error = ConfigFactory.parseResources("errors/provider_access_user.conf").getConfig(lang).getString("user_system_validate.exception") + "::" + e.toString();
      throw new Exception(error, e);  
    }
    return rpta;
  }

  public static String getUserId(Session session, String language, String user) throws HttpException, StatusErrorException, Exception{
    String rpta = "";
    try {
      Get req = Http.get(constants.getString("url") + "user/get_id_by_user?user=" + user)
        .header(constants.getString("csrf_key"), constants.getString("csrf_value"));
      int responseCode = req.responseCode();
      rpta = req.text();
      if (responseCode != 200){ 
        String lang = session.attribute("lang");
        String error = ConfigFactory.parseResources("errors/provider_access_user.conf").getConfig(lang).getString("get_user_id.neq200") + "::" + rpta;
        throw new StatusErrorException(error, null);    
      }
		} catch (HttpException e) {
			//e.printStackTrace();
      String lang = session.attribute("lang");
      String error = ConfigFactory.parseResources("errors/provider_access_user.conf").getConfig(lang).getString("get_user_id.http-exception") + "::" + e.toString();
      throw new HttpException(error, e);  
    } catch (StatusErrorException e){
      //e.printStackTrace();
      throw new StatusErrorException(e.getMessage(), null);
    } catch (Exception e) {
      //e.printStackTrace();
      String lang = session.attribute("lang");
      String error = ConfigFactory.parseResources("errors/provider_access_user.conf").getConfig(lang).getString("get_user_id.exception") + "::" + e.toString();
      throw new Exception(error, e);  
    }
    return rpta;
  }

  public static String getUserById(Session session, int userId) throws HttpException, StatusErrorException, Exception{
    String rpta = "";
    try {
      Get req = Http.get(constants.getString("url") + "user/get/" + userId)
        .header(constants.getString("csrf_key"), constants.getString("csrf_value"));
      int responseCode = req.responseCode();
      rpta = req.text();
      if (responseCode != 200){ 
        String lang = session.attribute("lang");
        String error = ConfigFactory.parseResources("errors/provider_access_user.conf").getConfig(lang).getString("get_user_by_id.neq200") + "::" + rpta;
        throw new StatusErrorException(error, null);    
      }
		} catch (HttpException e) {
			//e.printStackTrace();
      String lang = session.attribute("lang");
      String error = ConfigFactory.parseResources("errors/provider_access_user.conf").getConfig(lang).getString("get_user_by_id.http-exception") + "::" + e.toString();
      throw new HttpException(error, e);  
    } catch (StatusErrorException e){
      //e.printStackTrace();
      throw new StatusErrorException(e.getMessage(), null);
    } catch (Exception e) {
      //e.printStackTrace();
      String lang = session.attribute("lang");
      String error = ConfigFactory.parseResources("errors/provider_access_user.conf").getConfig(lang).getString("get_user_by_id.exception") + "::" + e.toString();
      throw new Exception(error, e);  
    }
    return rpta;
  }

  public static String userUpdate(Session session, int userId, String email, int userStatusId) throws HttpException, StatusErrorException, Exception{
    String rpta = "";
    try {
      Post req = Http.post(constants.getString("url") + "user/update")
        .param("user_id", userId + "")
        .param("email", email)
        .param("user_state_id", userStatusId + "")
        .header(constants.getString("csrf_key"), constants.getString("csrf_value"));
      int responseCode = req.responseCode();
      rpta = req.text();
      if (responseCode != 200){ 
        String lang = session.attribute("lang");
        String error = ConfigFactory.parseResources("errors/provider_access_user.conf").getConfig(lang).getString("update_user.neq200") + "::" + rpta;
        throw new StatusErrorException(error, null);    
      }
		} catch (HttpException e) {
			//e.printStackTrace();
      String lang = session.attribute("lang");
      String error = ConfigFactory.parseResources("errors/provider_access_user.conf").getConfig(lang).getString("update_user.http-exception") + "::" + e.toString();
      throw new HttpException(error, e);  
    } catch (StatusErrorException e){
      //e.printStackTrace();
      throw new StatusErrorException(e.getMessage(), null);
    } catch (Exception e) {
      //e.printStackTrace();
      String lang = session.attribute("lang");
      String error = ConfigFactory.parseResources("errors/provider_access_user.conf").getConfig(lang).getString("update_user.exception") + "::" + e.toString();
      throw new Exception(error, e);  
    }
    return rpta;
  }

  public static String updatePass(Session session, String userId, String pass) throws HttpException, StatusErrorException, Exception{
    String rpta = "";
    try {
      Post req = Http.post(constants.getString("url") + "user/update_pass")
        .param("user_id", userId)
        .param("pass", pass)
        .header(constants.getString("csrf_key"), constants.getString("csrf_value"));
      int responseCode = req.responseCode();
      rpta = req.text();
      if (responseCode != 200){ 
        String lang = session.attribute("lang");
        String error = ConfigFactory.parseResources("errors/provider_access_user.conf").getConfig(lang).getString("update_pass.neq200") + "::" + rpta;
        throw new StatusErrorException(error, null);    
      }
		} catch (HttpException e) {
			//e.printStackTrace();
      String lang = session.attribute("lang");
      String error = ConfigFactory.parseResources("errors/provider_access_user.conf").getConfig(lang).getString("update_pass.http-exception") + "::" + e.toString();
      throw new HttpException(error, e);  
    } catch (StatusErrorException e){
      //e.printStackTrace();
      throw new StatusErrorException(e.getMessage(), null);
    } catch (Exception e) {
      //e.printStackTrace();
      String lang = session.attribute("lang");
      String error = ConfigFactory.parseResources("errors/provider_access_user.conf").getConfig(lang).getString("update_pass.exception") + "::" + e.toString();
      throw new Exception(error, e);  
    }
    return rpta;
  }

  public static String updateState(Session session, int userId, String state) throws HttpException, StatusErrorException, Exception{
    String rpta = "";
    try {
      Post req = Http.post(constants.getString("url") + "user/update_state")
        .param("user_id", userId + "")
        .param("user_state_id", state)
        .header(constants.getString("csrf_key"), constants.getString("csrf_value"));
      int responseCode = req.responseCode();
      rpta = req.text();
      if (responseCode != 200){ 
        String lang = session.attribute("lang");
        String error = ConfigFactory.parseResources("errors/provider_access_user.conf").getConfig(lang).getString("update_pass.neq200") + "::" + rpta;
        throw new StatusErrorException(error, null);    
      }
		} catch (HttpException e) {
			//e.printStackTrace();
      String lang = session.attribute("lang");
      String error = ConfigFactory.parseResources("errors/provider_access_user.conf").getConfig(lang).getString("update_pass.http-exception") + "::" + e.toString();
      throw new HttpException(error, e);  
    } catch (StatusErrorException e){
      //e.printStackTrace();
      throw new StatusErrorException(e.getMessage(), null);
    } catch (Exception e) {
      //e.printStackTrace();
      String lang = session.attribute("lang");
      String error = ConfigFactory.parseResources("errors/provider_access_user.conf").getConfig(lang).getString("update_pass.exception") + "::" + e.toString();
      throw new Exception(error, e);  
    }
    return rpta;
  }
}