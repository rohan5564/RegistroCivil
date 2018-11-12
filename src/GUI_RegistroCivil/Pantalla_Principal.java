
package GUI_RegistroCivil;

import utilidades.ArchivoTxt;
import Enums.Tema;
import utilidades.ArchivoProperties;
import colecciones.Poblacion;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import org.controlsfx.control.ToggleSwitch;
import utilidades.ConexionBD;
import utilidades.MenuOpciones;


public class Pantalla_Principal{
    
    private Stage logueo;
    private final String horaActual = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    private TextArea logReporte = new TextArea("[" + horaActual +"]: Inicio de sesion.\n");    //reportes por pantalla
    private ArchivoProperties prop = new ArchivoProperties();
    private FileWriter log;
    private Poblacion poblacion = new Poblacion();
    private MenuOpciones menuOpcion = new MenuOpciones(logReporte, poblacion, prop);
   
    
    public Pantalla_Principal(Stage logueo) {
        this.logueo = logueo;
    }        

    public void menu(){
        //cargar datos de inicio
        prop.crear();
        Elementos.crearDatosIniciales(poblacion);
        ConexionBD.getInstancia().crearTablas();
        
        Stage menu = new Stage();
        menu.initOwner(null);
        if(prop.getProp().getProperty("tamanho_por_defecto").equals("verdad")){
            menu.initStyle(StageStyle.TRANSPARENT);
            menu.setMaximized(true);
        }
        menu.setResizable(false);
        
        menu.centerOnScreen();
        menu.setHeight(Double.valueOf(prop.getProp().getProperty("largo_pantalla")));
        menu.setWidth(Double.valueOf(prop.getProp().getProperty("ancho_pantalla")));
        GridPane grid = new GridPane();
        Scene menuPpal = new Scene(grid, Double.valueOf(prop.getProp().getProperty("ancho_pantalla")), 
                Double.valueOf(prop.getProp().getProperty("largo_pantalla")));
        
        menuPpal.getStylesheets().add(prop.getProp().getProperty("tema_actual"));
        grid.setHgap(100);
        grid.setVgap(100);       
        
        Label logTitulo = new Label("Historial");
        logTitulo.setFont(Font.font("monospaced", FontWeight.SEMI_BOLD, 28));
        logTitulo.setTextFill(Color.CORNSILK);
        logTitulo.setTranslateX(20);
        logTitulo.setTranslateY(-320);
        grid.add(logTitulo, 3, 0);
        logReporte.setStyle(
                "-fx-background-color: whitesmoke;"+
                "-fx-background-radius: 11.0;"
        );
        logReporte.setTranslateY(-50);
        logReporte.setPrefSize(600, 500);
        logReporte.setMinSize(600, 500);
        logReporte.setMaxSize(600, 500);
        logReporte.setWrapText(true);
        logReporte.setEditable(false);
        grid.add(logReporte, 3, 0);
        
        StackPane switchTema = tema(menuPpal, menu);
        grid.add(switchTema, 3, 0);
        
        ArrayList<Rectangle> rectangulo = separadorRectangulos(switchTema);
        for(int i=0 ; i<2 ; i++)
            grid.add(rectangulo.get(i), i, 0);
        
        ArrayList<Polygon> triangulo = separadorTriangulos(switchTema);
        for(int i=0 ; i<5 ; i++)
            grid.add(triangulo.get(i), 1, 0);
            
        VBox vbox1 = vboxMain();
        grid.add(vbox1, 0, 0);
        
        StackPane stack = stackPaneMain(vbox1.getChildrenUnmodifiable(), triangulo);
        grid.add(stack,2,0);
        
        HBox hbox1 = hboxMain(menu);
        grid.add(hbox1, 3, 0);
        
        menu.setIconified(true);
        menu.setScene(menuPpal);
        menu.show();
        
        menu.addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, lambda -> {
            lambda.consume();
            cerrar(menu);
        }); //abre la ultima ventana de logueo cargada
    }
    
    private VBox vboxMain(){
        VBox vbox1 = new VBox(80);
        String str = new String();
        for(int i=0; i<=4; i++){
            switch(i){
                case 0: 
                    str = "General";
                    break;
                case 1: 
                    str = "Nacimiento";
                    break;
                case 2: 
                    str = "Defuncion";
                    break;
                case 3: 
                    str = "Matrimonio";
                    break;
                case 4: 
                    str = "Extranjeros";
                    break;
            }
            Label texto = new Label(str);
            texto.setAlignment(Pos.CENTER);
            texto.setFont(Font.font("times new roman", FontWeight.LIGHT, 
                    prop.getProp().getProperty("tamanho_por_defecto").equals("verdad")?40:24));
            vbox1.getChildren().add(texto);
        }
        vbox1.setTranslateX(100);
        vbox1.setTranslateY(100);
        
        return vbox1;
    }
    
    private StackPane stackPaneMain(List<Node> opciones, List<Polygon> triangulo){
        StackPane stack = new StackPane();
        List<VBox> boxes = new ArrayList<>(Arrays.asList(
                new VBox(50),new VBox(50),new VBox(50),new VBox(50),new VBox(50) ));
        
        Button buscarNacimiento = new Button("modificar datos");
        buscarNacimiento.setPrefSize(200, 50);
        boxes.get(0).getChildren().addAll(buscarNacimiento);
        boxes.get(0).setVisible(false);
        seleccionarOpcion(opciones.get(0), boxes, 0, triangulo);
        buscarNacimiento.setOnMouseClicked(menuOpcion::buscarCiudadano);
        
        Button regNacimiento = new Button("Registrar nacimiento");
        regNacimiento.setPrefSize(200, 50);
        Button cantNacimiento = new Button("Mostrar total registrados");
        cantNacimiento.setPrefSize(200, 50);
        boxes.get(1).getChildren().addAll(regNacimiento,cantNacimiento);
        boxes.get(1).setVisible(false);
        seleccionarOpcion(opciones.get(1), boxes, 1, triangulo);
        
        regNacimiento.setOnMouseClicked(menuOpcion::registrarNacimiento);
        cantNacimiento.setOnMouseClicked(menuOpcion::cantidadNacidos);
        Button regDefuncion = new Button("Registrar defuncion");
        regDefuncion.setPrefSize(200, 50);
        //Button buscarDefuncion = new Button("Buscar difunto");
        //buscarDefuncion.setPrefSize(200, 50);
        boxes.get(2).getChildren().addAll(regDefuncion/*, buscarDefuncion*/);
        boxes.get(2).setVisible(false);
        seleccionarOpcion(opciones.get(2), boxes, 2, triangulo);
        
        regDefuncion.setOnMouseClicked(menuOpcion::registrarDefuncion);
        //buscarDefuncion.setOnMouseClicked(menuOpcion::buscarDefuncion);
        
        Button regMatrimonio = new Button("Registrar matrimonio");
        regMatrimonio.setPrefSize(200, 50);
        //Button buscarMatrimonio = new Button("Buscar matrimonio");
        //buscarMatrimonio.setPrefSize(200, 50);
        boxes.get(3).getChildren().addAll(regMatrimonio/*, buscarMatrimonio*/);
        boxes.get(3).setVisible(false);
        seleccionarOpcion(opciones.get(3), boxes, 3, triangulo);
        
        regMatrimonio.setOnMouseClicked(menuOpcion::registrarMatrimonio);
        //buscarMatrimonio.setOnMouseClicked(menuOpcion::buscarMatrimonio);
        
        Button regExtranjeros = new Button("Registrar antecedente");
        regExtranjeros.setPrefSize(200, 50);
        boxes.get(4).getChildren().addAll(regExtranjeros);
        boxes.get(4).setVisible(false);
        seleccionarOpcion(opciones.get(4), boxes, 4, triangulo);
        
        regExtranjeros.setOnMouseClicked(menuOpcion::registrarExtranjero);
        
        /*Button regVehiculo = new Button("Registrar vehiculo");
        regVehiculo.setPrefSize(200, 50);
        Button certVehiculo = new Button("Certificado de vehiculo");
        certVehiculo.setPrefSize(200, 50);
        boxes.get(4).getChildren().addAll(regVehiculo, certVehiculo);
        boxes.get(4).setVisible(false);
        opciones.get(4).setOnMouseEntered(lambda -> {
            for(VBox x:boxes){
                if(x.isVisible())
                    x.setVisible(false);
            }
            for(int i = 0 ; i<5 ; i++){
                if(i==4){
                    triangulo.get(i).setVisible(true);
                    continue;
                }
                triangulo.get(i).setVisible(false);
            }
            boxes.get(4).setVisible(true);
            opciones.get(4).setEffect(new DropShadow(70,Color.AQUA));
        });
        opciones.get(4).setOnMouseExited(lambda -> {
            opciones.get(4).setEffect(null);
        });*/
        
        stack.getChildren().addAll(boxes);
        stack.setTranslateX(10);
        stack.setTranslateY(200);
        return stack;
    }
    
    private HBox hboxMain(Stage ventana){
        HBox hbox1 = new HBox(300);
        Button guardar = new Button("guardar reporte");
        guardar.setOnMouseClicked(lambda -> {
            ArchivoTxt dir = new ArchivoTxt(ventana);
            dir.setDirectorio(logReporte.getText());
            if(dir.getDirectorio() != null)
                try{
                    new File(dir.getDirectorio()).createNewFile();
                }catch(IOException e){
                    e.printStackTrace();
                }
                logReporte.appendText("[reporte archivado en: "+dir.getDirectorio()+"]\n");
        });
        
        Button subir = new Button("subir datos a la red");
        subir.setOnMouseClicked(lambda -> {
            /*try{
                if(conexion == null)
                    logReporte.appendText("[modo offline]\n");
                else{
                    if(conexion.isConnected()){
                        conexion.save(poblacion);
                        logReporte.appendText("[base de datos actualizada]\n");
                    }
                    else
                        logReporte.appendText("[conexion fallida]\n");
                }
            }catch(ObjectNotFoundException e){
                e.printStackTrace();
            }catch(Exception e){
                e.printStackTrace();
            }*/
            
        });
        
        hbox1.getChildren().addAll(guardar, subir);
        hbox1.setTranslateX(20);
        hbox1.setTranslateY(620);
        return hbox1;
    }
    
    private ArrayList<Polygon> separadorTriangulos(StackPane tema){
        ArrayList<Polygon> tri = new ArrayList<Polygon>();
        ToggleSwitch sw = (ToggleSwitch)tema.getChildren().get(2);
        for(int i=0 ; i<5 ; i++){
            Polygon triangulo = new Polygon();
            triangulo.getPoints().addAll(new Double[]{
                    0.0, 0.0,
                    0.0, 70.0,
                    35.0, 35.0
            });
            if(sw.isSelected())
                triangulo.setFill(Color.ORANGE);
            else
                triangulo.setFill(Color.MEDIUMTURQUOISE);
            sw.selectedProperty().addListener((observable, uncheck, check)->{
                if(check)
                    triangulo.setFill(Color.ORANGE);
                    
                if(uncheck)
                    triangulo.setFill(Color.MEDIUMTURQUOISE);    
            });
            triangulo.setTranslateX(30);
            triangulo.setTranslateY(-255 + (i*125));
            triangulo.setVisible(false);
            tri.add(triangulo);
        }
        return tri;
    }   
    
    private ArrayList<Rectangle> separadorRectangulos(StackPane tema){
        
        ToggleSwitch sw = (ToggleSwitch)tema.getChildren().get(2);
        
        Rectangle rc1 = new Rectangle();
        if(sw.isSelected()) rc1.setFill(Color.ORANGE);
        else rc1.setFill(Color.MEDIUMTURQUOISE);
        sw.selectedProperty().addListener((observable, uncheck, check)->{
            if(check)
                rc1.setFill(Color.ORANGE);
            if(uncheck)
                rc1.setFill(Color.MEDIUMTURQUOISE);
        });
        rc1.setWidth(42);
        rc1.setHeight(Double.valueOf(prop.getProp().getProperty("largo_pantalla")));
        rc1.setTranslateX(0);
        
        Rectangle rc2 = new Rectangle();
        if(sw.isSelected()) rc2.setFill(Color.ORANGE);
        else rc2.setFill(Color.MEDIUMTURQUOISE);
        sw.selectedProperty().addListener((observable, uncheck, check)->{
            if(check)
                rc2.setFill(Color.ORANGE);
            if(uncheck)
                rc2.setFill(Color.MEDIUMTURQUOISE);
        });
        rc2.setWidth(4);
        rc2.setHeight(Double.valueOf(prop.getProp().getProperty("largo_pantalla")));
        rc2.setTranslateX(30);
        
        return new ArrayList<Rectangle>(Arrays.asList(rc1,rc2));
    }
    
    private void seleccionarOpcion(Node opcion, List<VBox> boxes, int pos, List<Polygon> triangulo){
        opcion.setOnMouseEntered(lambda -> {
            for(VBox x:boxes){
                if(x.isVisible())
                    x.setVisible(false);
            }
            for(int i = 0 ; i<5 ; i++){
                if(i==pos){
                    triangulo.get(i).setVisible(true);
                    continue;
                }
                triangulo.get(i).setVisible(false);
            }
            boxes.get(pos).setVisible(true);
            opcion.setEffect(new DropShadow(70,Color.AQUA));
            
        });
        opcion.setOnMouseExited(lambda -> {
            opcion.setEffect(null);
        });
    }
    
    private StackPane tema(Scene scene, Stage menu){
        StackPane switchTema = new StackPane();
        ImageView iconoTema = new ImageView(new Image("Resources/moon_icon.png", true));
        iconoTema.setFitWidth(25);
        iconoTema.setFitHeight(25);
        iconoTema.setPreserveRatio(true);
        iconoTema.setTranslateX(280);
        iconoTema.setTranslateY(-320);
        
        Button x = new Button("X");
        x.setStyle(
                "-fx-background-color: red;"
              + "-fx-text-fill: whitesmoke;"
        );
        x.setFont(Font.font("Bold", FontWeight.BOLD, 10));
        x.setMaxSize(25, 5);
        x.setScaleX(0.75);
        x.setScaleY(0.5);
        x.setTranslateX(320);
        x.setTranslateY(-370);
        if(prop.getProp().getProperty("tamanho_por_defecto").equals("falso"))
            x.setVisible(false);
        
        ToggleSwitch tema = new ToggleSwitch();
        tema.setTranslateX(230);
        tema.setTranslateY(-320);
        if(prop.getProp().getProperty("tema_actual").equals(prop.getProp().getProperty("tema_oscuro")))
            tema.setSelected(true);
        tema.setOnMouseEntered(lambda -> tema.setEffect(new DropShadow(20,Color.AQUA)));
        tema.setOnMouseExited(lambda -> tema.setEffect(null));
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
        
        x.setOnMouseClicked(lambda ->{
            cerrar(menu);
        });
        
        switchTema.getChildren().addAll(x, iconoTema, tema);
        return switchTema;
    }
    
    private void cerrar(Stage menu){
        Stage fin = new Stage();
        fin.setResizable(false);
        fin.setAlwaysOnTop(true);
        fin.initStyle(StageStyle.UTILITY);
        fin.initModality(Modality.APPLICATION_MODAL);
        GridPane pop = new GridPane();
        pop.setAlignment(Pos.CENTER);
        pop.setPrefHeight(100);
        pop.setPrefWidth(100);
        fin.setScene(new Scene(pop, 300, 100));
        HBox botones = new HBox(5);
        Button guardar = new Button("guardar y salir");
        guardar.setTooltip(new Tooltip("guardado de manera local!"));
        Button cerrar = new Button("salir");
        cerrar.setStyle("-fx-background-color: lightsalmon;");
        
        cerrar.setDisable(true);
        final Timeline animation = new Timeline(
                new KeyFrame(Duration.seconds(1.2), alpha -> {
                    cerrar.setDisable(false);
                    cerrar.setStyle("");
                })
        );
        animation.setCycleCount(1);
        animation.play();
        
        Button cancelar = new Button("cancelar");
        botones.setPadding(new Insets(10,0,0,0));
        Pane espacio = new Pane();
        espacio.setPrefSize(40,10);
        botones.getChildren().addAll(guardar, cerrar, espacio, cancelar);
        pop.add(botones, 0, 1);
        Label txt = new Label("confirme accion");
        txt.setTranslateX(70);
        pop.add(txt, 0, 0);
            
        guardar.setOnAction(alpha -> {
            ////////////////////////////////////////////////////////////////guardar el xml y subir a la base de datos
            fin.close();
            menu.close(); 
            logueo.show();
        });
        
        cerrar.addEventHandler(MouseEvent.MOUSE_CLICKED, alpha -> {
            fin.close();
            menu.close(); 
            logueo.show();
        });
            
        cerrar.addEventHandler(KeyEvent.KEY_PRESSED, alpha -> {
            if(alpha.getCode() == KeyCode.ENTER ){
                fin.close();
                menu.close(); 
                logueo.show();
            }
        });
            
        cancelar.setCancelButton(true);
        cancelar.setOnAction(alpha -> fin.close());            
        fin.show();
    }
    
}
