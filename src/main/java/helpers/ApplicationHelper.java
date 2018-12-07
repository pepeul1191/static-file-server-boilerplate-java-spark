package helpers;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import java.util.ArrayList;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigObject;
import org.json.JSONObject;
import spark.Session;

public abstract class ApplicationHelper {
  private Config constants = ConfigFactory.defaultApplication();
  
  public String getConfValue(String key){
    return constants.getString(key);
  }
  
  public String loadCSS(String[] csss) {
    String rpta = "";
    for (String css : csss) {
      String temp = "<link rel='stylesheet' type='text/css' href='" + constants.getString("static_url") + css + ".css'/>";
      rpta = rpta + temp;
    }
    return rpta;
  }    

  public String loadJS(String[] jss) {
    String rpta = "";
    for (String js : jss) {
      String temp = "<script src='" + constants.getString("static_url") + js + ".js' type='text/javascript'></script>";
      rpta = rpta + temp;
    }
    return rpta;
  }  

  public ArrayList<JSONObject> leftMenu(Session session) {
    ArrayList<JSONObject> menu = new ArrayList<>();
    String language = session.attribute("lang");
    String state;
    try {
      if(((String)session.attribute("status")).equalsIgnoreCase("active")){
        state = "logged";
      }else{
        state = "external";  
      }
    } catch (java.lang.NullPointerException e) {
      state = "external";
    }
    Config menuConfig = ConfigFactory.parseResources("contents/_menus.conf").getConfig(language + "." + state);
    ArrayList<ConfigObject> menuConfigArray =  (ArrayList<ConfigObject>) menuConfig.getObjectList("left");
    for (int i = 0; i < menuConfigArray.size(); i++){ 
      JSONObject temp = new JSONObject();
      Config t = menuConfigArray.get(i).toConfig();
      temp.put("url", t.getString("url"));
      temp.put("name", t.getString("name"));
      menu.add(temp);
    } 
    return menu;
  }  
  
  public ArrayList<JSONObject> rightMenu(Session session) {
    ArrayList<JSONObject> menu = new ArrayList<>();
    String language = session.attribute("lang");
    String state;
    try {
      if(((String)session.attribute("status")).equalsIgnoreCase("active")){
        state = "logged";
      }else{
        state = "external";  
      }
    } catch (java.lang.NullPointerException e) {
      state = "external";
    }
    Config menuConfig = ConfigFactory.parseResources("contents/_menus.conf").getConfig(language + "." + state);
    ArrayList<ConfigObject> menuConfigArray =  (ArrayList<ConfigObject>) menuConfig.getObjectList("right");
    for (int i = 0; i < menuConfigArray.size(); i++){ 
      JSONObject temp = new JSONObject();
      Config t = menuConfigArray.get(i).toConfig();
      temp.put("url", t.getString("url"));
      temp.put("name", t.getString("name"));
      menu.add(temp);
    } 
    return menu;
  }  
}
