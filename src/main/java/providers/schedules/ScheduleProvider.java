package providers.schedules;

import com.typesafe.config.Config;
import org.javalite.http.Http;
import org.javalite.http.HttpException;
import org.javalite.http.Get;
import exceptions.StatusErrorException;
import spark.Session;
import com.typesafe.config.ConfigFactory;

public class ScheduleProvider {
  private static Config constants = ConfigFactory.defaultApplication().getConfig("services.schedules");

  public static String listMonthByFieldIdYearMonth(Session session, String fieldId, String month, String year, String language) throws HttpException, StatusErrorException, Exception{
    String rpta = "";
    try {
      Get req = Http.get(constants.getString("url") + "schedule/list_month_by_field_id_year_month?field_id=" + fieldId + "&month=" + month + "&year=" + year)
        .header(constants.getString("csrf_key"), constants.getString("csrf_value"));
      rpta = req.text();
      if(req.responseCode() != 200){
        String lang = session.attribute("lang");
        String error = ConfigFactory.parseResources("errors/provider_schedules_schedule.conf").getConfig(lang).getString("list-month-by-fieldId-year-month-neq200") + "::" + rpta;
        throw new StatusErrorException(error, null); 
      }
		} catch (HttpException e) {
			//e.printStackTrace();
      String lang = session.attribute("lang");
      String error = ConfigFactory.parseResources("errors/provider_schedules_schedule.conf").getConfig(lang).getString("list-month-by-fieldId-year-month-http-exception") + "::" + e.toString();
      throw new HttpException(error, e);  
    } catch (Exception e) {
      //e.printStackTrace();
      String lang = session.attribute("lang");
      String error = ConfigFactory.parseResources("errors/provider_schedules_schedule.conf").getConfig(lang).getString("list-month-by-fieldId-year-month-exception") + "::" + e.toString();
      throw new Exception(error, e);  
    } 
    return rpta;
  }
}