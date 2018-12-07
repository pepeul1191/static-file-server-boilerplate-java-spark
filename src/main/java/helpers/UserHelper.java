package helpers;

public class UserHelper extends ApplicationHelper{
  public String resetPassCSS() {
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
  
  public String resetPassJS() {
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

  public String keyCSS() {
    switch(getConfValue("ambiente_static")) {
      case "desarrollo":
        return this.loadCSS(new String[] {
          "bower_components/font-awesome/css/font-awesome.min",
          "bower_components/bootstrap/dist/css/bootstrap.min",
          "assets/css/constants",
          "assets/css/styles",
          "assets/css/key",
        });
      case "produccion":
        return this.loadCSS(new String[] {
          "dist/login.min"
        });
      default:
        return this.loadCSS(new String[] {});
    }
  }  
}
