
package GUI_RegistroCivil;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.stage.Stage;


public class Main extends Application {
    /**
     * 
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        launch(args);
    }
    
    public void menu(Stage logueo){
        Pantalla_Principal app = new Pantalla_Principal(logueo);
        app.menu();
    }
    
    @Override
    public void start(Stage primaryStage) {
        Login login = new Login(primaryStage);
    }
}