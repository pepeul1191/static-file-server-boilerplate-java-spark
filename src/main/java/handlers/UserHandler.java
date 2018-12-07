package handlers;

/*
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;
import java.util.HashMap;
import java.util.Map;
import configs.App;
import exceptions.StatusErrorException;
import org.javalite.http.HttpException;
import helpers.UserHelper;
*/

public class UserHandler {
  /*
  public static Route resetPassword = (Request request, Response response) -> {
    //constants and session
    Config constants = ConfigFactory.defaultApplication();
    Session session = request.session();
    //path params
    int userId = Integer.parseInt(request.params(":user_id"));
    String resetKey = request.params(":reset_key");
    //session language and other data
    String lang = "sp";
    String message = "";
    String colorMessage = "color-error";
    boolean _continue = true;
    // grabar user_id para el formulario post en caso de error
    try {
      String exist = providers.access.KeyProvider.resetValidate(session, userId, resetKey);
      if(!exist.equalsIgnoreCase("1")){
        //render to 404
        response.redirect(constants.getString("base_url") + "access/error/404");
      }
    } catch (StatusErrorException e) {
      _continue = false;
      message = e.getMessage().toString().split("::")[0];
      System.out.println("++++++++ StatusErrorException ++++++++");
      System.out.println(e.getMessage());
      e.printStackTrace();
    }catch (HttpException e) {
      _continue = false;
      message = e.getMessage().toString().split("::")[0];
      System.out.println("++++++++ HttpException ++++++++");
      System.out.println(e.getMessage());
      e.printStackTrace();
    } catch (Exception e) {
      _continue = false;
      message = e.getMessage().toString().split("::")[0];
      System.out.println("++++++++ Exception ++++++++");
      System.out.println(e.getMessage());
      e.printStackTrace();
    }
    //if _continue == true, show view, else redirect to 505 error
    if (_continue == false){
      response.redirect(constants.getString("base_url") + "access/error/505");
    }
    UserHelper helper = new UserHelper();
    Map<String, Object> model = new HashMap<>();
    model.put("title", ConfigFactory.parseResources("contents/_titles.conf").getConfig("sp").getString("reset_index"));
    model.put("constants", constants);
    model.put("content", ConfigFactory.parseResources("contents/user_reset_pass.conf").getConfig("sp"));
    model.put("message", "");
    model.put("message_type", colorMessage);
    model.put("load_css", helper.resetPassCSS());
    model.put("user_id", userId);
    model.put("load_js", helper.resetPassJS());
    return App.render("user/reset_pass.ftl.html", model);
  };

  public static Route resetPasswordPOST = (Request request, Response response) -> {
    //constants and session
    Config constants = ConfigFactory.defaultApplication();
    Session session = request.session();
    String csrfKey = constants.getString("csrf.key");
    String csrfValue = constants.getString("csrf.secret");
    String colorMessage = "color-error";
    //form data
    String pass1 = request.queryParams("pass1");
    String pass2 = request.queryParams("pass2");
    String userId = request.queryParams("user_id");
    //session language and other data
    String lang = "sp";
    String message = "";
    boolean _continue = true;
    //reset password
    try {
      String csrfRequestValue = request.queryParams(csrfKey);
      //validar csrf token
      if(!csrfRequestValue.equalsIgnoreCase(csrfValue) ){
        message =  ConfigFactory.parseResources("errors/post_csrf.conf").getConfig(lang).getString("message");
        _continue = false;
      }
      //validar usuario y contraseña si csrf token es correcto
      if(_continue == true){
        if(pass1.equals(pass2)){
          //encrypt pass with cipher service
          pass1 = providers.cipher.EncryptProvider.encrypt(session, pass1);
          //update user pass
          providers.access.UserProvider.updatePass(session, userId, pass1);
          //change reset key
          providers.access.KeyProvider.updateResetByUserId(session, userId);
          //render
          UserHelper helper = new UserHelper();
          Map<String, Object> model = new HashMap<>();
          model.put("title", ConfigFactory.parseResources("contents/_titles.conf").getConfig("sp").getString("reset_ok"));
          model.put("constants", constants);
          model.put("message", ConfigFactory.parseResources("contents/user_reset_pass.conf").getConfig("sp").getString("messege_success"));
          model.put("icon", "fa fa-thumbs-o-up");
          model.put("load_css", helper.keyCSS());
          model.put("load_js", helper.resetPassJS());
          return App.render("user/key.ftl.html", model);
        }else{
          message = ConfigFactory.parseResources("contents/user_reset_pass.conf").getConfig(lang).getString("message_passwords_not_equals");
        }
      }
    } catch (StatusErrorException e) {
      message = e.getMessage().toString().split("::")[0];
      System.out.println("++++++++ StatusErrorException ++++++++");
      System.out.println(e.getMessage());
      e.printStackTrace();
    }catch (HttpException e) {
      message = e.getMessage().toString().split("::")[0];
      System.out.println("++++++++ HttpException ++++++++");
      System.out.println(e.getMessage());
      e.printStackTrace();
    } catch(NullPointerException e){
        //e.printStackTrace();
      message = ConfigFactory.parseResources("errors/post_csrf.conf").getConfig(lang).getString("message");
    } catch (Exception e) {
      message = e.getMessage().toString().split("::")[0];
      System.out.println("++++++++ Exception ++++++++");
      System.out.println(e.getMessage());
      e.printStackTrace();
    }
    //render
    UserHelper helper = new UserHelper();
    Map<String, Object> model = new HashMap<>();
    model.put("title", ConfigFactory.parseResources("contents/_titles.conf").getConfig("sp").getString("reset_index"));
    model.put("constants", constants);
    model.put("content", ConfigFactory.parseResources("contents/user_reset_pass.conf").getConfig("sp"));
    model.put("message", message);
    model.put("message_type", colorMessage);
    model.put("load_css", helper.resetPassCSS());
    model.put("user_id", userId);
    model.put("load_js", helper.resetPassJS());
    return App.render("user/reset_pass.ftl.html", model);
  };

  public static Route activationKey = (Request request, Response response) -> {
    //constants and session
    Config constants = ConfigFactory.defaultApplication();
    Session session = request.session();
    //path params
    int userId = Integer.parseInt(request.params(":user_id"));
    String activationKey = request.params(":activation_key");
    //session language and other data
    String lang = "sp";
    String message = ConfigFactory.parseResources("contents/user_activate.conf").getConfig("sp").getString("error-message");
    String icon = "fa fa-exclamation-triangle";
    boolean _continue = true;
    int status = 500;
    // grabar user_id para el formulario post en caso de error
    try {
      String exist = providers.access.KeyProvider.activationValidate(session, userId, activationKey);
      if(!exist.equalsIgnoreCase("1")){
        //render to 404
        response.redirect(constants.getString("base_url") + "access/error/404");
      }
      //update user state
      providers.access.UserProvider.updateState(session, userId, "1");
      //change activation key
      providers.access.KeyProvider.updateActivationByUserId(session, userId + "", "1");
      icon = "fa fa-thumbs-o-up";
      message = ConfigFactory.parseResources("contents/user_activate.conf").getConfig("sp").getString("success-message");
      status = 200;
    } catch (StatusErrorException e) {
      _continue = false;
      message = e.getMessage().toString().split("::")[0];
      System.out.println("++++++++ StatusErrorException ++++++++");
      System.out.println(e.getMessage());
      e.printStackTrace();
    }catch (HttpException e) {
      _continue = false;
      message = e.getMessage().toString().split("::")[0];
      System.out.println("++++++++ HttpException ++++++++");
      System.out.println(e.getMessage());
      e.printStackTrace();
    } catch (Exception e) {
      _continue = false;
      message = e.getMessage().toString().split("::")[0];
      System.out.println("++++++++ Exception ++++++++");
      System.out.println(e.getMessage());
      e.printStackTrace();
    }
    //if _continue == true, show view, else redirect to 505 error
    if (_continue == false){
      response.redirect(constants.getString("base_url") + "access/error/505");
    }
    UserHelper helper = new UserHelper();
    Map<String, Object> model = new HashMap<>();
    model.put("title", ConfigFactory.parseResources("contents/_titles.conf").getConfig("sp").getString("reset_index"));
    model.put("constants", constants);
    model.put("message", message);
    model.put("icon", icon);
    model.put("load_css", helper.keyCSS());
    model.put("user_id", userId);
    model.put("load_js", helper.resetPassJS());
    response.status(status);
    return App.render("user/key.ftl.html", model);
  };
  */
}
