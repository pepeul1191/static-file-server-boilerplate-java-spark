package providers.managment;

import com.typesafe.config.Config;
import org.javalite.http.Http;
import org.javalite.http.HttpException;
import org.javalite.http.Post;
import org.json.JSONObject;

import exceptions.StatusErrorException;
import com.typesafe.config.ConfigFactory;

public class PlayerProvider {
  private static Config constants = ConfigFactory.defaultApplication().getConfig("services.managment");
  
  public static String create(String name, boolean notifications, int userId, String lang) throws HttpException, StatusErrorException, Exception{
    String rpta = "";
    try {
      JSONObject data = new JSONObject();
      data.put("name", name);
      data.put("notifications", notifications);
      data.put("phone", "");
      data.put("user_id", userId);
      Post req = Http.post(constants.getString("url") + "player/create")
        .param("data", data.toString())
        .header(constants.getString("csrf_key"), constants.getString("csrf_value"));
      int responseCode = req.responseCode();
      rpta = req.text();
      if (responseCode != 200){  
        String error = ConfigFactory.parseResources("errors/provider_managment_player.conf").getConfig(lang).getString("create.neq200") + "::" + rpta;
        throw new StatusErrorException(error, null);  
      }
		} catch (HttpException e) {
			//e.printStackTrace();
      String error = ConfigFactory.parseResources("errors/provider_managment_player.conf").getConfig(lang).getString("create.http-exception") + "::" + e.toString();
      throw new HttpException(error, e);  
    } catch (StatusErrorException e){
      //e.printStackTrace();
      throw new StatusErrorException(e.getMessage(), null);
    } catch (Exception e) {
      //e.printStackTrace();
      String error = ConfigFactory.parseResources("errors/provider_managment_player.conf").getConfig(lang).getString("create.exception") + "::" + e.toString();
      throw new Exception(error, e);  
    }
    return rpta;
  }
}