package providers.access;

import com.typesafe.config.Config;
import org.javalite.http.Get;
import org.javalite.http.Http;
import org.javalite.http.HttpException;
import exceptions.StatusErrorException;
import spark.Session;
import com.typesafe.config.ConfigFactory;

public class UserStateProvider {
  private static Config constants = ConfigFactory.defaultApplication().getConfig("services.access");
  
  public static String list(Session session) throws HttpException, StatusErrorException, Exception{
    String rpta = "";
    try {
      Get req = Http.get(constants.getString("url") + "user_state/list")
        .header(constants.getString("csrf_key"), constants.getString("csrf_value"));
      int responseCode = req.responseCode();
      rpta = req.text();
      if (responseCode != 200){ 
        String lang = session.attribute("lang");
        String error = ConfigFactory.parseResources("errors/provider_access_user_state.conf").getConfig(lang).getString("list.neq200") + "::" + rpta;
        throw new StatusErrorException(error, null);    
      }
		} catch (HttpException e) {
			//e.printStackTrace();
      String lang = session.attribute("lang");
      String error = ConfigFactory.parseResources("errors/provider_access_user_state.conf").getConfig(lang).getString("list.http-exception") + "::" + e.toString();
      throw new HttpException(error, e);  
    } catch (StatusErrorException e){
      //e.printStackTrace();
      throw new StatusErrorException(e.getMessage(), null);
    } catch (Exception e) {
      //e.printStackTrace();
      String lang = session.attribute("lang");
      String error = ConfigFactory.parseResources("errors/provider_access_user_state.conf").getConfig(lang).getString("list.exception") + "::" + e.toString();
      throw new Exception(error, e);  
    }
    return rpta;
  }
}