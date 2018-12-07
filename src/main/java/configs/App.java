package configs;

import static spark.Spark.exception;
import static spark.Spark.staticFiles;
import static spark.Spark.port;
import static spark.Spark.options;
import static spark.Spark.before;
import static spark.Spark.get;
import static spark.Spark.post;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;
import java.io.StringWriter;
import static spark.Spark.halt;
import java.util.Map;
import spark.Spark;
import configs.FilterHandler;
import handlers.ErrorHandler;
import handlers.LoginHandler;

public class App {
  public static void main(String args[]){
    exception(Exception.class, (e, req, res) -> e.printStackTrace());
		staticFiles.location("/public");
		staticFiles.header("Access-Control-Allow-Origin", "*");
		staticFiles.header("Access-Control-Request-Method",  "*");
		staticFiles.header("Access-Control-Allow-Headers",  "*");
		//staticFiles.expireTime(600);
		//puerto
		port(4000);
		//CORS
		options("/*", (request, response) -> {
			String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
			if (accessControlRequestHeaders != null) {
				response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
			}
			String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
			if (accessControlRequestMethod != null) {
				response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
			}
			return "OK";
		});
		//filters
		before("*", FilterHandler.setHeaders);
		before("*", FilterHandler.ambinteLogs);
		//rutas a login
		get("/login", LoginHandler.index);
		//rutas a error
		get("/access/error/:error", ErrorHandler.index);
		//rutas de servicios REST a handlers
		//errors si no encuentra recurso
		get("/*", ErrorHandler.errorGET);
		post("/*", ErrorHandler.errorPOST);
		//ruta de test/conexion
		get("/test/conexion", (request, response) -> {
			return "Conxión OK";
		});
	}
	
	public static StringWriter render(String template, Map model){
		StringWriter writer = new StringWriter();
		Configuration configuration = new Configuration(new Version(2, 3, 0));
    configuration.setClassForTemplateLoading(App.class, "/templates");
		try {
			Template resultTemplate = configuration.getTemplate(template);
			Config constants = ConfigFactory.defaultApplication();
			model.put("constants", constants);
			resultTemplate.process(model, writer);
		} catch (Exception e) {
			e.printStackTrace();
			Spark.halt(500);
		}
		return writer;
	} 
}