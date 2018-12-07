package providers.schedules;

import com.typesafe.config.Config;
import org.javalite.http.Http;
import org.javalite.http.HttpException;
import org.javalite.http.Post;
import exceptions.StatusErrorException;
import spark.Session;
import org.json.JSONObject;
import com.typesafe.config.ConfigFactory;

public class HourProvider {
  private static Config constants = ConfigFactory.defaultApplication().getConfig("services.schedules");

  public static String reservate(Session session, String fieldId, String hour, int playerId, String transactionId, String day, String newStatus, String language) throws HttpException, StatusErrorException, Exception{
    String rpta = "";
    try {
      JSONObject data = new JSONObject();
      data.put("hour", hour);
      data.put("field_id", fieldId);
      data.put("player_id", playerId);
      data.put("transaction_id", transactionId);
      data.put("day", day);
      data.put("status", newStatus);
      data.put("date_format", "yyyy-MM-dd");
      Post req = Http.post(constants.getString("url") + "hour/reservate_one")
        .param("data", data.toString())
        .header(constants.getString("csrf_key"), constants.getString("csrf_value"));
      rpta = req.text();
      if(req.responseCode() != 200){
        String lang = session.attribute("lang");
        String error = ConfigFactory.parseResources("errors/provider_schedules_hour.conf").getConfig(lang).getString("reservate-neq200") + "::" + rpta;
        throw new StatusErrorException(error, null); 
      }
		} catch (HttpException e) {
			//e.printStackTrace();
      String lang = session.attribute("lang");
      String error = ConfigFactory.parseResources("errors/provider_schedules_hour.conf").getConfig(lang).getString("reservate-http-exception") + "::" + e.toString();
      throw new HttpException(error, e);  
    } catch (Exception e) {
      //e.printStackTrace();
      String lang = session.attribute("lang");
      String error = ConfigFactory.parseResources("errors/provider_schedules_hour.conf").getConfig(lang).getString("reservate-exception") + "::" + e.toString();
      throw new Exception(error, e);  
    } 
    return rpta;
  }
}