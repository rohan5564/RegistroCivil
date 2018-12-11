
package GUI_RegistroCivil;

import utilidades.ConexionBD;
import Enums.Tema;
import utilidades.ArchivoProperties;
import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.control.ToggleSwitch;



public class Login extends Main{
    
    private String horaActual = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH"));
    private ArchivoProperties prop = ArchivoProperties.getInstancia();
    
    public Login(){
        System.exit(0);
    }
    
    public Login(Stage logueo) {
        ventanaLogueo(logueo);
    }
    
    public void ventanaLogueo(Stage logueo){
        
        prop.crear();
        if(Integer.parseInt(horaActual)>20 || Integer.parseInt(horaActual)<8)
            prop.modificarTema(Tema.OSCURO);
        
        //ventana
        logueo.initStyle(StageStyle.TRANSPARENT);
        
        //crear cuadricula para poner botones, texto, etc
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10,10,10,10));
        Scene scene = new Scene(grid, 540, 480);
        if(prop.getProp().getProperty("tema_actual").equals(""))
            prop.modificarTema(Tema.CLARO);
        scene.getStylesheets().add(prop.getProp().getProperty("tema_actual"));
        logueo.setScene(scene);
        
        //logo
        ImageView logo = new ImageView(new Image("Resources/logo.jpg", true));
        logo.setOpacity(0.6);
        logo.setFitWidth(120);
        logo.setFitHeight(120);
        logo.setPreserveRatio(true);
        logo.setSmooth(true);
        logo.setOnMouseExited(lambda -> logo.setEffect( null ));
        logo.setOnMouseMoved(lambda -> {
            if(prop.getProp().getProperty("tema_actual").equals(prop.getProp().getProperty("tema_oscuro")))
                logo.setEffect(new DropShadow(15,Color.LIGHTCYAN));
            else if(prop.getProp().getProperty("tema_actual").equals(prop.getProp().getProperty("tema_claro")))
                logo.setEffect(new DropShadow(15,Color.DODGERBLUE));
        });
        logo.setOnMouseClicked(lambda -> {
            if(prop.getProp().getProperty("tema_actual").equals(prop.getProp().getProperty("tema_oscuro")))
                logo.setEffect(new DropShadow(30,Color.LIGHTCYAN));
            else if(prop.getProp().getProperty("tema_actual").equals(prop.getProp().getProperty("tema_claro")))
                logo.setEffect(new DropShadow(30,Color.DODGERBLUE));
            //extends Main   
            getHostServices().showDocument(
                    "http://www.registrocivil.cl/principal/paginas-frecuentes/contacto-registro-civil"   
            );
        });
        grid.add(logo,0,17,6,5);
        
        //titulo + switch tema
        StackPane titulo = new StackPane();
        Label textTitulo = new Label(" Bienvenido al registro civil\n");
        textTitulo.setFont(Font.font("bold", FontWeight.NORMAL, 34));
        textTitulo.setTranslateY(-10);
        ImageView iconoTema = new ImageView(new Image("Resources/moon_icon.png", true));
        iconoTema.setFitWidth(30);
        iconoTema.setFitHeight(30);
        iconoTema.setPreserveRatio(true);
        iconoTema.setSmooth(true);
        iconoTema.setTranslateX(240);
        iconoTema.setTranslateY(-90);
        ToggleSwitch tema = new ToggleSwitch();
        tema.setTranslateX(190);
        tema.setTranslateY(-90);
        tema.setOnMouseEntered(lambda -> tema.setEffect(new DropShadow(20,Color.AQUA)));
        tema.setOnMouseExited(lambda -> tema.setEffect(null));
        if(prop.getProp().getProperty("tema_actual").equals(prop.getProp().getProperty("tema_oscuro")))
            tema.setSelected(true);
        tema.selectedProperty().addListener((observable, uncheck, check)->{
            if(check){
                prop.modificarTema(Tema.OSCURO);
                scene.getStylesheets().setAll(prop.getProp().getProperty("tema_actual"));               
            }
            if(uncheck){
                prop.modificarTema(Tema.CLARO);
                scene.getStylesheets().setAll(prop.getProp().getProperty("tema_actual"));
            }
        });
        titulo.getChildren().addAll(iconoTema, tema, textTitulo);
        grid.add(titulo, 0,8,6,3);
        
        //texto plano
        Label nombreDeUsuario = new Label("Usuario:");
        nombreDeUsuario.setFont(Font.font("monospaced", FontWeight.NORMAL, 16));
        grid.add(nombreDeUsuario, 0, 12);
        Label pw = new Label("contraseña:");
        pw.setFont(Font.font("monospaced", FontWeight.NORMAL, 16));
        grid.add(pw, 0, 14);
        
        //texto inicial dentro de las casillas
        TextField leerTexto = new TextField();
        leerTexto.setPromptText("Ingrese nombre de usuario");
        grid.add(leerTexto, 2, 12);
        PasswordField password = new PasswordField();
        password.setPromptText("ingrese contraseña");
        grid.add(password, 2, 14);
        
        //crea botones y HBOX (horizontal box)
        Button ingreso = new Button("ingresar");
        ingreso.disableProperty().bind( //boton no presionable si casillas estan vacias
                leerTexto.textProperty().isEmpty().or( //or = ambas casillas llenas                     
                        password.textProperty().isEmpty()));
        
        Button cerrar = new Button("cerrar"); 
        Button btnEspacio = new Button("espacio"); 
        btnEspacio.setVisible(false);
        btnEspacio.setMouseTransparent(true);
        cerrar.setCancelButton(true);
        Pane espacio = new Pane();
        espacio.setPrefSize(70,10);
        HBox hbIngreso = new HBox(10);
        hbIngreso.setAlignment(Pos.BOTTOM_LEFT);
        ImageView config = new ImageView(new Image("Resources/configuracion.png", true));
        config.setFitWidth(35);
        config.setFitHeight(35);
        config.setPreserveRatio(true);
        config.setSmooth(true);
        config.setTranslateY(60);
        config.setTranslateX(320);
        hbIngreso.getChildren().addAll(espacio, btnEspacio, ingreso, cerrar);//ingresa todos los botones creados de forma ordenada
        grid.add(hbIngreso, 2,20);
        grid.add(config, 2, 21);
        ingreso.setDefaultButton(true);
        boton1(logueo, ingreso, leerTexto, password);
        cerrar(logueo, cerrar);
        config.setOnMouseClicked(lambda-> LoginBD.getInstrancia().configurarLogin());
        logueo.show();
    }
    
    public void boton2(Stage logueo, Button ayuda, TextField password){
        ayuda.setVisible(true);
        ayuda.setOnAction(lambda->{
            password.clear();
            logueo.close();
            super.menu(logueo);
        });       
    }
    
    public void boton1(Stage logueo, Button ingreso, TextField leerTexto, TextField password){
        ingreso.setOnAction(lambda -> {
            try{
                ConexionBD.getInstancia().getConexion(
                        prop.getProp().getProperty("ip")+":"+prop.getProp().getProperty("port")+"/"+prop.getProp().getProperty("tabla"), 
                        prop.getProp().getProperty("user"), 
                        prop.getProp().getProperty("pass"));
                if (!ConexionBD.getInstancia().checkConexion()){ //conexion establecida?
                    Elementos.notificar("Error de conexion", "compruebe la configuracion del servidor").showError();
                }
                else {
                    if (ConexionBD.getInstancia().login(leerTexto.getText(),password.getText())){
                        password.clear();
                        super.menu(logueo);
                        logueo.hide();
                    }
                    else{
                        Elementos.notificar("Error de logueo", "usuario y/o contraseña incorrectos").showWarning();
                    }
                }
            }catch(PropertyVetoException | SQLException e){
                e.printStackTrace();
                Elementos.notificar("error de conexion", "revise la configuracion del servidor").showError();
            }
            //limpiar casillas con la entrada de texto 
            leerTexto.clear();
            password.clear(); 
        });
    }
    
    public void cerrar(Stage logueo, Button cerrar){
        cerrar.setOnAction(lambda -> logueo.close());
    }
}
