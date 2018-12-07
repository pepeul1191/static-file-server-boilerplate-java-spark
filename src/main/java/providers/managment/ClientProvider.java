package providers.managment;

import com.typesafe.config.Config;
import org.javalite.http.Http;
import org.javalite.http.HttpException;
import org.javalite.http.Get;
import exceptions.StatusErrorException;
import spark.Session;
import com.typesafe.config.ConfigFactory;

public class ClientProvider {
  private static Config constants = ConfigFactory.defaultApplication().getConfig("services.managment");

  public static String listByUserId(Session session, String userId, String language) throws HttpException, StatusErrorException, Exception{
    String rpta = "";
    try {
      Get req = Http.get(constants.getString("url") + "client/list_by_user_id/" + userId)
        .header(constants.getString("csrf_key"), constants.getString("csrf_value"));
      rpta = req.text();
      if(req.responseCode() != 200){
        String lang = session.attribute("lang");
        String error = ConfigFactory.parseResources("errors/provider_managment_client.conf").getConfig(lang).getString("list-neq200") + "::" + rpta;
        throw new StatusErrorException(error, null); 
      }
		} catch (HttpException e) {
			//e.printStackTrace();
      String lang = session.attribute("lang");
      String error = ConfigFactory.parseResources("errors/provider_managment_client.conf").getConfig(lang).getString("list-http-exception") + "::" + e.toString();
      throw new HttpException(error, e);  
    } catch (Exception e) {
      //e.printStackTrace();
      String lang = session.attribute("lang");
      String error = ConfigFactory.parseResources("errors/provider_managment_client.conf").getConfig(lang).getString("list-exception") + "::" + e.toString();
      throw new Exception(error, e);  
    } 
    return rpta;
  }
}