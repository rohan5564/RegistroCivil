
package GUI_RegistroCivil;

import colecciones.Operador;
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
    
    public void menu(Stage logueo, Operador operador){
        Pantalla_Principal app = new Pantalla_Principal(logueo, operador);
        app.menu();
    }
    
    @Override
    public void start(Stage primaryStage) {
        Login login = new Login(primaryStage);
    }
}