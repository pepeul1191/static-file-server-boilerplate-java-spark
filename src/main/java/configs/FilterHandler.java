package configs;

import spark.Filter;
import spark.Request;
import spark.Response;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class FilterHandler{
  public static Filter setHeaders = (Request request, Response response) -> {
    response.header("Access-Control-Allow-Origin", "*");
    response.header("Access-Control-Request-Method",  "*");
    response.header("Access-Control-Allow-Headers",  "*");
    response.header("Access-Control-Allow-Credentials", "true");
    response.header("Server",  "Ubuntu, Jetty");
    // Note: this may or may not be necessary in your particular application
    //response.type("application/json");
  };

  public static Filter ambinteLogs = (Request request, Response response) -> {
    Config constants = ConfigFactory.defaultApplication();
    if(constants.getString("ambiente_request_logs").equalsIgnoreCase("activo")){
      System.out.println(request.requestMethod() + " - " + request.pathInfo());
    }
  };
}