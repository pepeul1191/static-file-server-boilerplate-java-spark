package helpers;

public class LoginHelper extends ApplicationHelper{
  public String indexCSS() {
    switch(getConfValue("ambiente_static")) {
      case "desarrollo":
        return this.loadCSS(new String[] {
          "bower_components/font-awesome/css/font-awesome.min",
          "bower_components/bootstrap/dist/css/bootstrap.min",
          "assets/css/constants",
          "assets/css/styles",
          "assets/css/login",
        });
      case "produccion":
        return this.loadCSS(new String[] {
          "dist/login.min"
        });
      default:
        return this.loadCSS(new String[] {});
    }
  }  
  
  public String indexJS() {
    switch(getConfValue("ambiente_static")) {
      case "desarrollo":
        return this.loadJS(new String[] {
        });
      case "produccion":
        return this.loadJS(new String[] {
        });
      default:
        return this.loadJS(new String[] {});
    }
  }  
}
