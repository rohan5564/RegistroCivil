
package GUI_RegistroCivil;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.PopOver;
import utilidades.ArchivoProperties;

public class LoginBD {
    private ArchivoProperties prop = ArchivoProperties.getInstancia();
    private static LoginBD login;
    
    private LoginBD(){
    }
    
    public static LoginBD getInstrancia(){
        if(login == null)
            login = new LoginBD();
        return login;
    }
    
    public void configurarLogin(){
        prop.crear();
        Stage ventana = new Stage();
        ventana.setResizable(false);
        ventana.initStyle(StageStyle.UNDECORATED);
        ventana.setAlwaysOnTop(true);
        ventana.initModality(Modality.APPLICATION_MODAL);
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(30);
        grid.setAlignment(Pos.CENTER);
        
        TextField ip = new TextField();
        ip.setPromptText("IP");
        ip.setMaxWidth(130);
        TextField port = new TextField();
        port.setPromptText("puerto");
        port.setMaxWidth(60);
        TextField user = new TextField();
        user.setPromptText("usuario");
        user.setMaxWidth(220);
        PasswordField pass = new PasswordField();
        pass.setPromptText("contraseña");
        pass.setMaxWidth(220);
        
        Button guardar = new Button("guardar");
        guardar.disableProperty().bind( 
                ip.textProperty().isEmpty().or( 
                port.textProperty().isEmpty().or(
                user.textProperty().isEmpty().or(
                pass.textProperty().isEmpty()
                ))));
        Button restablecer = new Button("restablecer");
        Button salir = new Button("salir");
        
        guardar.setOnMouseClicked(lambda->{
            prop.getProp().setProperty("ip", ip.getText());
            prop.getProp().setProperty("user", user.getText());
            prop.getProp().setProperty("pass", pass.getText());
            Notifications.create()
                    .text("Datos del servidor actualizados")
                    .darkStyle()
                    .hideAfter(Duration.seconds(1.5))
                    .showConfirm();
            ventana.close();
        });
        
        restablecer.setOnMouseClicked(lambda->{
            prop.getProp().setProperty("ip", prop.getIp());
            prop.getProp().setProperty("user", prop.getUser());
            prop.getProp().setProperty("pass", prop.getPass());
            Notifications.create()
                    .text("Datos del servidor restablecidos\na su valor inicial")
                    .darkStyle()
                    .hideAfter(Duration.seconds(1.5))
                    .showConfirm();
            ip.clear();
            user.clear();
            pass.clear();
        });
        
        salir.setOnMouseClicked(lambda -> ventana.close());
        
        HBox box = new HBox(guardar,restablecer,salir);
        box.setSpacing(20);
        HBox conexion = new HBox(ip, port);
        conexion.setSpacing(30);
        
        grid.addColumn(0, conexion, user, pass, box);
        
        Scene scene = new Scene(grid, 400, 300);
        grid.setBorder(Elementos.borde(3));
        scene.getStylesheets().add(prop.getProp().getProperty("tema_actual"));
        ventana.setScene(scene);
        ventana.show();
    }
}
