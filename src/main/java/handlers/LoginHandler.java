package handlers;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import spark.Request;
import spark.Response;
import spark.Route;
import java.util.HashMap;
import java.util.Map;
import configs.App;
import helpers.LoginHelper;


public class LoginHandler {
  public static Route index = (Request request, Response response) -> {
    LoginHelper helper = new LoginHelper();
    Config constants = ConfigFactory.defaultApplication();
    Map<String, Object> model = new HashMap<>();
    model.put("title", ConfigFactory.parseResources("contents/_titles.conf").getConfig("sp").getString("login_index"));
    model.put("content", ConfigFactory.parseResources("contents/login_index.conf").getConfig("sp"));
    model.put("constants", constants);
    model.put("message", "");
    model.put("load_css", helper.indexCSS());
    model.put("load_js", helper.indexJS());
    return App.render("login/index.ftl.html", model);
  };
}
