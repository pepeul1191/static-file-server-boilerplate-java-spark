package helpers;

public class ErrorHelper extends ApplicationHelper{
  public String indexCSS() {
    switch(getConfValue("ambiente_static")) {
      case "desarrollo":
        return this.loadCSS(new String[] {
          "bower_components/bootstrap/dist/css/bootstrap.min",
          "bower_components/font-awesome/css/font-awesome.min",
          "bower_components/swp-backbone/assets/css/constants",
          "assets/css/constants",
          "assets/css/error",
        });
      case "produccion":
        return this.loadCSS(new String[] {
          "dist/error.min"
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
