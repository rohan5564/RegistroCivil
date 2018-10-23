/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 *
 * @author Jean
 */
public class Elementos {
    private static ArchivoProperties prop = new ArchivoProperties();
    
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
        popup.initStyle(StageStyle.UTILITY);
        popup.setAlwaysOnTop(true);
        popup.initModality(Modality.APPLICATION_MODAL);
        GridPane pop = new GridPane();
        pop.setHgap(30);
        pop.setVgap(20);
        pop.setAlignment(Pos.CENTER);
        pop.setPrefHeight(100);
        pop.setPrefWidth(100);        
        Label txt = new Label(str);
        txt.setFont(Font.font("bold", FontWeight.LIGHT, 22));
        pop.add(txt,0,0);
        Button ok = new Button("aceptar");
        ok.setTranslateX(largo/2);
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
        popup.show();
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
     * </code></pre>
     * @return StackPane de JavaFx
     * @since Entrega A
     */
    public static StackPane checkRut(){
        StackPane checkRut = new StackPane();
        ImageView check = Elementos.icono("Resources/check.png");
        ImageView mark = Elementos.icono("Resources/yellow_mark.png");
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
        checkRut.getChildren().addAll(rut, check, mark);
        return checkRut;
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
                for(Chile i: Chile.TARAPACA.values())
                    arreglo.add(i.toString().replace("_"," ").toLowerCase());
                break;
            case "ANTOFAGASTA": 
                for(Chile i: Chile.ANTOFAGASTA.values())
                    arreglo.add(i.toString().replace("_"," ").toLowerCase());
                break;
            case "ATACAMA": 
                for(Chile i: Chile.ATACAMA.values())
                    arreglo.add(i.toString().replace("_"," ").toLowerCase());
                break;
            case "COQUIMBO": 
                for(Chile i: Chile.COQUIMBO.values())
                    arreglo.add(i.toString().replace("_"," ").toLowerCase());
                break;
            case "VALPARAISO": 
                for(Chile i: Chile.VALPARAISO.values())
                    arreglo.add(i.toString().replace("_"," ").toLowerCase());
                break;
            case "LIBERTADOR_GENERAL_BERNARDO_OHIGGINS": 
                for(Chile i: Chile.LIBERTADOR_GENERAL_BERNARDO_OHIGGINS.values())
                    arreglo.add(i.toString().replace("_"," ").toLowerCase());
                break;
            case "MAULE": 
                for(Chile i: Chile.MAULE.values())
                    arreglo.add(i.toString().replace("_"," ").toLowerCase());
                break;
            case "BIO_BIO": 
                for(Chile i: Chile.BIO_BIO.values())
                    arreglo.add(i.toString().replace("_"," ").toLowerCase());
                break;
            case "LA_ARAUCANIA": 
                for(Chile i: Chile.LA_ARAUCANIA.values())
                    arreglo.add(i.toString().replace("_"," ").toLowerCase());
                break;
            case "LOS_LAGOS": 
                for(Chile i: Chile.LOS_LAGOS.values())
                    arreglo.add(i.toString().replace("_"," ").toLowerCase());
                break;
            case "AYSEN_DEL_GENERAL_CARLOS_IBANEZ_DEL_CAMPO": 
                for(Chile i: Chile.AYSEN_DEL_GENERAL_CARLOS_IBANEZ_DEL_CAMPO.values())
                    arreglo.add(i.toString().replace("_"," ").toLowerCase());
                break;
            case "MAGALLANES_Y_ANTARTICA": 
                for(Chile i: Chile.MAGALLANES_Y_ANTARTICA.values())
                    arreglo.add(i.toString().replace("_"," ").toLowerCase());
                break;
            case "REGION_METROPOLITANA": 
                for(Chile i: Chile.REGION_METROPOLITANA.values())
                    arreglo.add(i.toString().replace("_"," ").toLowerCase());
                break;
            case "LOS_RIOS":
                for(Chile i: Chile.LOS_RIOS.values())
                    arreglo.add(i.toString().replace("_"," ").toLowerCase());
                break;
            case "ARICA_Y_PARINACOTA":
                for(Chile i: Chile.ARICA_Y_PARINACOTA.values())
                    arreglo.add(i.toString().replace("_"," ").toLowerCase());
                break;
            case "ÑUBLE":
                for(Chile i: Chile.ÑUBLE.values())
                    arreglo.add(i.toString().replace("_"," ").toLowerCase());
                break;
        }
        Collections.sort(arreglo);
        return arreglo;
    }
    
    public static void crearDatosIniciales(Poblacion poblacion){
        HashMap<String, Ciudadano> aux = (HashMap<String, Ciudadano>)poblacion.getPoblacion();
        Chileno n1 = (Chileno)nuevoNacido("jean","rodriguez",Chile.REGIONES.VALPARAISO.toString(), Chile.VALPARAISO.NOGALES.toString(), Sexo.MASCULINO, "1996-07-08", "03:33:33", "194037228");
        aux.put(n1.getRut(), n1);
        Chileno n2 = (Chileno)nuevoNacido("pablo","contreras",Chile.REGIONES.REGION_METROPOLITANA.toString(), Chile.REGION_METROPOLITANA.ESTACIÓN_CENTRAL.toString(), Sexo.MASCULINO, "1995-11-20", "13:33:33", "19287285K");
        aux.put(n2.getRut(), n2);
        Chileno n3 = (Chileno)nuevoNacido("roberto","rojas",Chile.REGIONES.REGION_METROPOLITANA.toString(), Chile.REGION_METROPOLITANA.MAIPÚ.toString(), Sexo.MASCULINO, "1994-11-22", "04:44:44", "189545614");
        aux.put(n3.getRut(), n3);
        Chileno n4 = (Chileno)nuevoNacido("claudio","cubillos",Chile.REGIONES.VALPARAISO.toString(), Chile.VALPARAISO.VIÑA_DEL_MAR.toString(), Sexo.MASCULINO, "1974-12-22", "12:12:12", "105771959");
        aux.put(n4.getRut(), n4);
        Chileno n5 = (Chileno)nuevoNacido("monica","galindo",Chile.REGIONES.MAGALLANES_Y_ANTARTICA.toString(), Chile.MAGALLANES_Y_ANTARTICA.ANTÁRTICA.toString(), Sexo.FEMENINO, "1999-01-12", "13:13:13", "19403721K");
        aux.put(n5.getRut(), n5);
        
        
        Chileno nq = (Chileno)nuevoNacido("j","roduez",Chile.REGIONES.ANTOFAGASTA.toString(), Chile.ANTOFAGASTA.CALAMA.toString(), Sexo.MASCULINO, "1996-07-08", "03:33:33", "194237228");
        aux.put(nq.getRut(), nq);
        Chileno nw = (Chileno)nuevoNacido("pao","contreras",Chile.REGIONES.ARICA_Y_PARINACOTA.toString(), Chile.ARICA_Y_PARINACOTA.CAMARONES.toString(), Sexo.MASCULINO, "1995-11-20", "13:33:33", "19281285K");
        aux.put(nw.getRut(), nw);
        Chileno ne = (Chileno)nuevoNacido("rberto","rojas",Chile.REGIONES.ATACAMA.toString(), Chile.ATACAMA.CALDERA.toString(), Sexo.MASCULINO, "1994-11-22", "04:44:44", "181545614");
        aux.put(ne.getRut(), ne);
        Chileno nr = (Chileno)nuevoNacido("caudio","cubillos",Chile.REGIONES.AYSEN_DEL_GENERAL_CARLOS_IBANEZ_DEL_CAMPO.toString(), Chile.AYSEN_DEL_GENERAL_CARLOS_IBANEZ_DEL_CAMPO.CHILE_CHICO.toString(), Sexo.MASCULINO, "1974-12-22", "12:12:12", "115771959");
        aux.put(nr.getRut(), nr);
        Chileno nt = (Chileno)nuevoNacido("mnica","galindo",Chile.REGIONES.BIO_BIO.toString(), Chile.MAGALLANES_Y_ANTARTICA.ANTÁRTICA.toString(), Sexo.FEMENINO, "1999-01-12", "13:13:13", "11403721K");
        aux.put(nt.getRut(), nt);
        
        Chileno ny = (Chileno)nuevoNacido("jean","rodriguez",Chile.REGIONES.COQUIMBO.toString(), Chile.VALPARAISO.NOGALES.toString(), Sexo.MASCULINO, "1996-07-08", "03:33:33", "94037228");
        aux.put(ny.getRut(), ny);
        Chileno nu = (Chileno)nuevoNacido("pablo","contreras",Chile.REGIONES.LA_ARAUCANIA.toString(), Chile.REGION_METROPOLITANA.ESTACIÓN_CENTRAL.toString(), Sexo.MASCULINO, "1995-11-20", "13:33:33", "9287285K");
        aux.put(nu.getRut(), nu);
        Chileno ni = (Chileno)nuevoNacido("roberto","rojas",Chile.REGIONES.LIBERTADOR_GENERAL_BERNARDO_OHIGGINS.toString(), Chile.REGION_METROPOLITANA.MAIPÚ.toString(), Sexo.MASCULINO, "1994-11-22", "04:44:44", "89545614");
        aux.put(ni.getRut(), ni);
        Chileno no = (Chileno)nuevoNacido("claudio","cubillos",Chile.REGIONES.LOS_LAGOS.toString(), Chile.VALPARAISO.VIÑA_DEL_MAR.toString(), Sexo.MASCULINO, "1974-12-22", "12:12:12", "05771959");
        aux.put(no.getRut(), no);
        Chileno np = (Chileno)nuevoNacido("monica","galindo",Chile.REGIONES.LOS_RIOS.toString(), Chile.MAGALLANES_Y_ANTARTICA.ANTÁRTICA.toString(), Sexo.FEMENINO, "1999-01-12", "13:13:13", "9403721K");
        aux.put(np.getRut(), np);
        
        
        Chileno na = (Chileno)nuevoNacido("jean","rodriguez",Chile.REGIONES.MAULE.toString(), Chile.VALPARAISO.NOGALES.toString(), Sexo.MASCULINO, "1996-07-08", "03:33:33", "19037228");
        aux.put(na.getRut(), na);
        Chileno ns = (Chileno)nuevoNacido("pablo","contreras",Chile.REGIONES.TARAPACA.toString(), Chile.REGION_METROPOLITANA.ESTACIÓN_CENTRAL.toString(), Sexo.MASCULINO, "1995-11-20", "13:33:33", "1928725K");
        aux.put(ns.getRut(), ns);
        Chileno nd = (Chileno)nuevoNacido("roberto","rojas",Chile.REGIONES.ÑUBLE.toString(), Chile.REGION_METROPOLITANA.MAIPÚ.toString(), Sexo.MASCULINO, "1994-11-22", "04:44:44", "18955614");
        aux.put(nd.getRut(), nd);
        Chileno nf = (Chileno)nuevoNacido("claudio","cubillos",Chile.REGIONES.VALPARAISO.toString(), Chile.VALPARAISO.VIÑA_DEL_MAR.toString(), Sexo.MASCULINO, "1974-12-22", "12:12:12", "10577199");
        aux.put(nf.getRut(), nf);
        Chileno ng = (Chileno)nuevoNacido("monica","galindo",Chile.REGIONES.MAGALLANES_Y_ANTARTICA.toString(), Chile.MAGALLANES_Y_ANTARTICA.ANTÁRTICA.toString(), Sexo.FEMENINO, "1999-01-12", "13:13:13", "1940371K");
        aux.put(ng.getRut(), ng);
        
    }
    
    public static Ciudadano nuevoNacido(String nombre, String apellido, String region, String comuna, 
            Sexo sexo, String fecha, String hora, String rut){
        Chileno aux = new Chileno();
        aux.setNombre(nombre);
        aux.setApellido(apellido);
        aux.setRegion(region.replace("_"," ").toLowerCase());
        aux.setComuna(comuna.replace("_"," ").toLowerCase());
        aux.setSexo(sexo);
        aux.setNacimiento(LocalDate.parse(fecha));
        aux.setHoraNacimiento(hora);
        aux.setRut(rut);
        return aux;
    }
}
