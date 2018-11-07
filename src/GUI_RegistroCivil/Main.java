
package GUI_RegistroCivil;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.stage.Stage;
import org.hibernate.Session;



/**
 *
 * @author Jean
 */


public class Main extends Application {
    /**
     * 
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        launch(args);
    }
    
    public void menu(Stage logueo, Session conexion){
        Pantalla_Principal app = new Pantalla_Principal(logueo, conexion);
        app.menu();
    }
    
    @Override
    public void start(Stage primaryStage) {
        Login login = new Login(primaryStage);
    }
}