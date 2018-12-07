package handlers;

import spark.Request;
import spark.Response;
import spark.Route;
import org.json.JSONObject;
import exceptions.StatusErrorException;
import org.javalite.http.HttpException;

public class UserHandler {
  public static Route access = (Request request, Response response) -> {
    //form data
    String user = request.queryParams("user");
    String pass = request.queryParams("pass");
    int systemId =  Integer.parseInt(request.queryParams("system_id"));
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

  public static Route resetByEmail = (Request request, Response response) -> {
    //form data
    String email = request.queryParams("email");
    //session language and other data
    String rpta = "";
    int status = 500;
    String lang = request.headers("lang");
    boolean _redirect = false;
    boolean _continue = true;
    //reset password
    try {
      String t = providers.access.KeyProvider.resetByEmail(lang, email);
      if(t.equalsIgnoreCase("user_not_found")){
        rpta = t;
      }else{
        JSONObject r = new JSONObject(t);
        //int userId, String resetKey, String email, String language
        providers.mails.ResetProvider.send(r.getInt("user_id"), r.getString("reset_key"), email, lang);
        rpta = "success";
      }
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
