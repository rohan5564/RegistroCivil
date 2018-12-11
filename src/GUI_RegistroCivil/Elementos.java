
package GUI_RegistroCivil;

import Enums.Sexo;
import java.time.LocalTime;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Collections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.converter.LocalTimeStringConverter;
import utilidades.ArchivoProperties;
import Interfaces.Chile;
import colecciones.Chileno;
import colecciones.Ciudadano;
import colecciones.Poblacion;
import java.time.LocalDate;
import java.util.HashMap;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.PopOver;


public class Elementos {
    private static ArchivoProperties prop = ArchivoProperties.getInstancia();
    
    /**
     * crea un campo de texto para lectura de hora con formato en horas:minutos:segundos
     * @param str mensaje para mostrar en el campo de texto
     * @return Spinner<LocalTime> de JavaFx
     * @since Entrega A
     */
    public static Spinner<LocalTime> hora(String str){
        Spinner<LocalTime> hora = new Spinner(new SpinnerValueFactory() {
            {
                setConverter(new LocalTimeStringConverter(FormatStyle.MEDIUM));
            }
            @Override
            public void decrement(int steps) {
                if (getValue() == null)
                    setValue(LocalTime.now());
                else {
                    LocalTime time = (LocalTime)getValue();
                    setValue(time.minusMinutes(steps));
                }
            }
            
            @Override
            public void increment(int steps) {
                if (this.getValue() == null)
                    setValue(LocalTime.now());
                else {
                    LocalTime time = (LocalTime)getValue();
                    setValue(time.plusMinutes(steps));
                }
            }
        });
        
        hora.getValueFactory().setValue(LocalTime.MIDNIGHT);
        hora.setEditable(true);
        hora.setTooltip(new Tooltip(str));
        return hora;
    }
    
    /**
     * crea una ventana emergente para mostrar un mensaje
     * @param str mensaje a mostrar en la ventana
     * @param largo largo de la ventana
     * @param ancho ancho de la ventana
     * @since Entrega A
     */
    public static void popMensaje(String str, double largo, double ancho){
        prop.crear();
        Stage popup = new Stage();
        popup.setResizable(false);
        popup.initStyle(StageStyle.TRANSPARENT);
        popup.setAlwaysOnTop(true);
        popup.initModality(Modality.APPLICATION_MODAL);
        GridPane pop = new GridPane();
        pop.setBorder(borde(4));
        pop.setHgap(5);
        pop.setVgap(5);
        pop.setAlignment(Pos.CENTER);
        pop.setPrefHeight(100);
        pop.setPrefWidth(100);        
        Label txt = new Label(str);
        txt.setFont(Font.font("bold", FontWeight.LIGHT, 22));
        pop.add(txt,0,0);
        Button ok = new Button("aceptar");
        ok.setTranslateX(largo/5);
        pop.add(ok, 0, 1);
        
        ok.addEventHandler(MouseEvent.MOUSE_CLICKED, alpha -> popup.close());
        
        ok.addEventHandler(KeyEvent.KEY_PRESSED, alpha -> {
            if(alpha.getCode() == KeyCode.ENTER ){
                popup.close();
            }
        });
        
        Scene scene = new Scene(pop, largo, ancho);
        scene.getStylesheets().add(prop.getProp().getProperty("tema_actual"));
        popup.setScene(scene);
        popup.setUserData((Button)ok);
        popup.show();
    }
    
    public static Stage popMensajeStage(String str, double largo, double ancho){
        prop.crear();
        Stage popup = new Stage();
        popup.setResizable(false);
        popup.initStyle(StageStyle.TRANSPARENT);
        popup.setAlwaysOnTop(true);
        popup.initModality(Modality.APPLICATION_MODAL);
        GridPane pop = new GridPane();
        pop.setBorder(borde(4));
        pop.setHgap(30);
        pop.setVgap(20);
        pop.setAlignment(Pos.CENTER);
        pop.setPrefHeight(100);
        pop.setPrefWidth(100);        
        Label txt = new Label(str);
        txt.setFont(Font.font("bold", FontWeight.LIGHT, 22));
        pop.add(txt,0,0);
        Button ok = new Button("aceptar");
        ok.setTranslateX(largo/5);
        pop.add(ok, 0, 1);
        
        ok.addEventHandler(MouseEvent.MOUSE_CLICKED, alpha -> popup.close());
        
        ok.addEventHandler(KeyEvent.KEY_PRESSED, alpha -> {
            if(alpha.getCode() == KeyCode.ENTER ){
                popup.close();
            }
        });
        
        Scene scene = new Scene(pop, largo, ancho);
        scene.getStylesheets().add(prop.getProp().getProperty("tema_actual"));
        popup.setScene(scene);
        popup.setUserData((Button)ok);
        return popup;
    }
    
    /**
     * crea un icono diminuto de tamaño 20x20 
     * @param str carpeta donde se encuentra la imagen
     * @return ImageView de JavaFx
     * @since Entrega A
     */
    private static ImageView icono(String str){
        ImageView icono = new ImageView(new Image(str, true));        
        icono.setFitWidth(20);
        icono.setFitHeight(20);
        icono.setPreserveRatio(true);
        icono.setSmooth(true);
        icono.setTranslateX(80);
        icono.setVisible(false);
        return icono;
    }
    
    /**
     * crea un campo de texto para ingresar un rut sin puntos ni guion.
     * <p>
     * contiene 2 iconos para usar de forma opcional y de acuerdo a las 
     * necesidades, y corresponden a una señal verde y una señal amarilla.
     * <p>
     * los elementos se se obtienen en de la siguiente forma:
     * <pre><code>
     *   //campo de ingreso
     *   TextField rut = (TextField)checkRut.getChildrenUnmodifiable().<b>get(0);</b>
     * 
     *   //señal verde
     *   ImageView check = (ImageView)checkRut.getChildrenUnmodifiable().<b>get(1);</b>
     * 
     *   //señal amarilla
     *   ImageView mark = (ImageView)checkRut.getChildrenUnmodifiable().<b>get(2);</b>
     * 
     *   //señal roja
     *   ImageView error = (ImageView)checkRut.getChildrenUnmodifiable().<b>get(3);</b>
     * </code></pre>
     * @return StackPane de JavaFx
     * @since Entrega A
     */
    public static StackPane checkRut(){
        StackPane checkRut = new StackPane();
        ImageView check = Elementos.icono("Resources/check.png");
        check.setTranslateX(100);
        ImageView mark = Elementos.icono("Resources/yellow_mark.png");
        mark.setTranslateX(100);
        ImageView error = Elementos.icono("Resources/x.png");
        error.setTranslateX(100);
        TextField rut = new TextField();
        rut.setTextFormatter(new TextFormatter<>((formato) -> {
                formato.setText(formato.getText().toUpperCase());
                return formato;
        }));
        rut.setMaxSize(200, 40);
        rut.setPromptText("ingrese rut sin puntos ni guion");
        rut.lengthProperty().addListener((observable, o, n)->{
            if(n.intValue()>9)
                rut.setText(rut.getText().substring(0, 9));
        });
        checkRut.getChildren().addAll(rut, check, mark, error);
        return checkRut;
    }
    
    /**
     * crea un campo de texto para ingresar un rut sin puntos ni guion o un pasaporte.
     * <p>
     * contiene 2 iconos para usar de forma opcional y de acuerdo a las 
     * necesidades, y corresponden a una señal verde y una señal amarilla.
     * <p>
     * los elementos se se obtienen en de la siguiente forma:
     * <pre><code>
     *   //campo de ingreso
     *   TextField rut = (TextField)checkIdentificador.getChildrenUnmodifiable().<b>get(0);</b>
     * 
     *   //señal verde
     *   ImageView check = (ImageView)checkIdentificador.getChildrenUnmodifiable().<b>get(1);</b>
     * 
     *   //señal amarilla
     *   ImageView mark = (ImageView)checkIdentificador.getChildrenUnmodifiable().<b>get(2);</b>
     * 
     *   //señal roja
     *   ImageView error = (ImageView)checkIdentificador.getChildrenUnmodifiable().<b>get(4);</b>
     * 
     *   //checkbox
     *   CheckBox pasaporte = (CheckBox)checkIdentificador.getChildrenUnmodifiable().<b>get(3);</b>
     * </code></pre>
     * @return StackPane de JavaFx
     * @since Entrega B
     */
    public static StackPane checkIdentificador(){
        StackPane checkIdentificador = new StackPane();
        CheckBox pasaporte = new CheckBox();
        pasaporte.setSelected(false);
        ImageView check = Elementos.icono("Resources/check.png");
        check.setTranslateX(110);
        ImageView mark = Elementos.icono("Resources/yellow_mark.png");
        mark.setTranslateX(110);
        ImageView error = Elementos.icono("Resources/x.png");
        error.setTranslateX(110);
        mark.setVisible(true);
        TextField identificador = new TextField();
        identificador.setTextFormatter(new TextFormatter<>((formato) -> {
                formato.setText(formato.getText().toUpperCase());
                return formato;
        }));
        identificador.setMaxSize(200, 40);
        pasaporte.selectedProperty().addListener((obs, old, seleccionado)->{
            if(!seleccionado.booleanValue()){
                identificador.lengthProperty().addListener((observable, o, n)->{
                    if(n.intValue()>9)
                    identificador.setText(identificador.getText().substring(0, 9));
                });
            }
        });
        checkIdentificador.getChildren().addAll(identificador, check, mark, pasaporte, error);
        return checkIdentificador;
    }
    
    /**
     * crea un campo de texto para ingresar un rut sin puntos ni guion.
     * <p>
     * contiene 2 iconos para usar de forma opcional y de acuerdo a las 
     * necesidades, y corresponden a una señal verde y una señal amarilla.
     * <p>
     * los elementos se se obtienen en de la siguiente forma:
     * <pre><code>
     *   //campo de ingreso
     *   TextField rut = (TextField)checkRut.getChildrenUnmodifiable().<b>get(0);</b>
     * 
     *   //señal verde
     *   ImageView check = (ImageView)checkRut.getChildrenUnmodifiable().<b>get(1);</b>
     * 
     *   //señal amarilla
     *   ImageView mark = (ImageView)checkRut.getChildrenUnmodifiable().<b>get(2);</b>
     * </code></pre>
     * @param parentesco texto a mostrar en la casilla de entrada
     * @return StackPane de JavaFx
     * @since Entrega A
     */
    public static StackPane checkPariente(String parentesco){
        StackPane pariente = new StackPane();
        TextField id = new TextField();
        ImageView check = Elementos.icono("Resources/check.png");
        ImageView mark = Elementos.icono("Resources/yellow_mark.png");
        id.setTextFormatter(new TextFormatter<>((formato) -> {
                formato.setText(formato.getText().toUpperCase());
                return formato;
        }));
        id.setMaxSize(200, 40);
        id.setPromptText(parentesco);
        CheckBox extPariente = new CheckBox();
        extPariente.setTranslateX(118);
        extPariente.setTooltip(new Tooltip("extanjero: debe ingreesar pasaporte"));
        pariente.getChildren().addAll(id, extPariente, check, mark);
        return pariente;
    }
    
    
    public static DatePicker fecha(String str){
        DatePicker fecha = new DatePicker();
        fecha.setMaxSize(200, 40);
        fecha.setPromptText(str);
        return fecha;
    }
    
    /**
     * crea un campo de texto
     * @param str mensaje que muestra el campo de textp
     * @param largo largo del campo
     * @param ancho ancho del campo
     * @return TextField de JavaFx
     * @since Entrega A
     */
    public static TextField textfield(String str, double largo, double ancho){
        TextField txt = new TextField();
        txt.setMinSize(largo, ancho);
        txt.setPromptText(str);
        return txt;
    }
    
    /**
     * encuentra las comunas que contiene una region
     * @param region region buscada
     * @return ArrayList de comunas en la region
     * @since Entrega A
     */
    public static ArrayList<String> comunas(String region){
        ArrayList<String> arreglo = new ArrayList<>();
        switch(region.toUpperCase().replace(" ", "_")){
            case "TARAPACA": 
                for(Chile.TARAPACA i: Chile.TARAPACA.values())
                    arreglo.add(i.getNombre());
                break;
            case "ANTOFAGASTA": 
                for(Chile.ANTOFAGASTA i: Chile.ANTOFAGASTA.values())
                    arreglo.add(i.getNombre());
                break;
            case "ATACAMA": 
                for(Chile.ATACAMA i: Chile.ATACAMA.values())
                    arreglo.add(i.getNombre());
                break;
            case "COQUIMBO": 
                for(Chile.COQUIMBO i: Chile.COQUIMBO.values())
                    arreglo.add(i.getNombre());
                break;
            case "VALPARAISO": 
                for(Chile.VALPARAISO i: Chile.VALPARAISO.values())
                    arreglo.add(i.getNombre());
                break;
            case "LIBERTADOR_GENERAL_BERNARDO_OHIGGINS": 
                for(Chile.LIBERTADOR_GENERAL_BERNARDO_OHIGGINS i: Chile.LIBERTADOR_GENERAL_BERNARDO_OHIGGINS.values())
                    arreglo.add(i.getNombre());
                break;
            case "MAULE": 
                for(Chile.MAULE i: Chile.MAULE.values())
                    arreglo.add(i.getNombre());
                break;
            case "BIO_BIO": 
                for(Chile.BIO_BIO i: Chile.BIO_BIO.values())
                    arreglo.add(i.getNombre());
                break;
            case "LA_ARAUCANIA": 
                for(Chile.LA_ARAUCANIA i: Chile.LA_ARAUCANIA.values())
                    arreglo.add(i.getNombre());
                break;
            case "LOS_LAGOS": 
                for(Chile.LOS_LAGOS i: Chile.LOS_LAGOS.values())
                    arreglo.add(i.getNombre());
                break;
            case "AYSEN_DEL_GENERAL_CARLOS_IBANEZ_DEL_CAMPO": 
                for(Chile.AYSEN_DEL_GENERAL_CARLOS_IBANEZ_DEL_CAMPO i: Chile.AYSEN_DEL_GENERAL_CARLOS_IBANEZ_DEL_CAMPO.values())
                    arreglo.add(i.getNombre());
                break;
            case "MAGALLANES_Y_ANTARTICA": 
                for(Chile.MAGALLANES_Y_ANTARTICA i: Chile.MAGALLANES_Y_ANTARTICA.values())
                    arreglo.add(i.getNombre());
                break;
            case "REGION_METROPOLITANA": 
                for(Chile.REGION_METROPOLITANA i: Chile.REGION_METROPOLITANA.values())
                    arreglo.add(i.getNombre());
                break;
            case "LOS_RIOS":
                for(Chile.LOS_RIOS i: Chile.LOS_RIOS.values())
                    arreglo.add(i.getNombre());
                break;
            case "ARICA_Y_PARINACOTA":
                for(Chile.ARICA_Y_PARINACOTA i: Chile.ARICA_Y_PARINACOTA.values())
                    arreglo.add(i.getNombre());
                break;
            case "ÑUBLE":
                for(Chile.ÑUBLE i: Chile.ÑUBLE.values())
                    arreglo.add(i.getNombre());
                break;
        }
        Collections.sort(arreglo);
        return arreglo;
    }
    
    public static Ciudadano nuevoNacido(String nombre, String apellido, String region, String comuna, 
            Sexo sexo, String fecha, String hora, String rut){
        return  new Chileno.BuilderChileno()
                .setRut(rut)
                .setRegionDeNacimiento(region.replace("_"," ").toLowerCase())
                .setComunaDeNacimiento(comuna.replace("_"," ").toLowerCase())
                .setNombre(nombre)
                .setApellido(apellido)
                .setSexo(sexo)
                .setNacimiento(LocalDate.parse(fecha))
                .setHoraNacimiento(hora).build();
    }
    
    public static PopOver popTip(String msj){
        Label tip = new Label(msj);
        tip.setStyle("-fx-text-fill: black");
        tip.setFont(Font.font("bold", FontWeight.NORMAL, 16));
        PopOver tooltip = new PopOver();
        tooltip.setContentNode(tip);
        return tooltip;
    }
    
    public static Notifications notificar(String titulo, String mensaje){
        return Notifications.create()
                    .title(titulo)
                    .text(mensaje)
                    .darkStyle()
                    .hideAfter(Duration.seconds(1.5));
    }
    
    public static Border borde(Color color, int ancho){
        return new Border(new BorderStroke(color, BorderStrokeStyle.SOLID, null, new BorderWidths(ancho)));
    }
    
    public static Border borde(int ancho){
        return new Border(new BorderStroke(Color.DARKCYAN, BorderStrokeStyle.SOLID, null, new BorderWidths(ancho)));
    }
}
