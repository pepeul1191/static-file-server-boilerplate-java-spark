package handlers;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import spark.Request;
import spark.Response;
import spark.Route;
import exceptions.StatusErrorException;
import org.javalite.http.HttpException;

public class UserHandler {
  public static Route access = (Request request, Response response) -> {
    //constants and session
    Config constants = ConfigFactory.defaultApplication();
    //form data
    String user = request.queryParams("user");
    String pass = request.queryParams("pass");
    int systemId =  Integer.parseInt(request.queryParams("system_id"));
    System.out.println(user);
    System.out.println(pass);
    System.out.println(systemId);
    //session language and other data
    int status = 500;
    String rpta = "";
    String lang = request.headers("lang");
    //reset password
    try {
      //encrypt pass with cipher service
      pass = providers.cipher.EncryptProvider.encrypt(pass, lang);
      //get status of user
      rpta = providers.access.UserProvider.userSystemValidate(lang, user, pass, systemId);
      status = 200;
    } catch (StatusErrorException e) {
      rpta = e.getMessage().toString().split("::")[0];
      System.out.println("++++++++ StatusErrorException ++++++++");
      System.out.println(e.getMessage());
      e.printStackTrace();
    }catch (HttpException e) {
      rpta = e.getMessage().toString().split("::")[0];
      System.out.println("++++++++ HttpException ++++++++");
      System.out.println(e.getMessage());
      e.printStackTrace();
    } catch (Exception e) {
      rpta = e.getMessage().toString().split("::")[0];
      System.out.println("++++++++ Exception ++++++++");
      System.out.println(e.getMessage());
      e.printStackTrace();
    }
    response.status(status);
    return rpta;
  };
}
