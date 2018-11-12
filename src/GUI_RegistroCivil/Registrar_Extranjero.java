/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI_RegistroCivil;

import Enums.EstadoCivil;
import Enums.Nacionalidad;
import Enums.Sexo;
import Enums.Visa;
import static GUI_RegistroCivil.Elementos.checkRut;
import Interfaces.Chile;
import colecciones.Chileno;
import colecciones.Ciudadano;
import colecciones.Extranjero;
import colecciones.Poblacion;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utilidades.ArchivoProperties;

/**
 *
 * @author Jean
 */
public class Registrar_Extranjero {
    private final String horaActual = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    private TextArea logReporte;
    private ArchivoProperties prop;
    private Poblacion poblacion;
    private Chileno aux1;
    private Chileno aux2;

    public Registrar_Extranjero(TextArea logReporte, Poblacion poblacion, ArchivoProperties prop) {
        this.logReporte = logReporte;
        this.poblacion = poblacion;
        this.prop = prop;
    }
    
    public void registrarExtranjero(MouseEvent click){
        Stage ventana = new Stage();
        ventana.setX(370);
        ventana.setY(80);
        ventana.setResizable(false);
        ventana.initStyle(StageStyle.UTILITY);
        ventana.initModality(Modality.APPLICATION_MODAL);
        ventana.setMinHeight(620);
        ventana.setMinWidth(450);
        ventana.setMaxHeight(620);
        ventana.setMaxWidth(450);
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER_LEFT);
        grid.setHgap(50);
        grid.setVgap(20);
        grid.setPadding(new Insets(50,50,50,50));
        
        TextField nombre = new TextField();
        nombre.setMinSize(200, 40);
        nombre.setPromptText("ingrese nombres");
        
        TextField apellido = new TextField();
        apellido.setMinSize(200, 40);
        apellido.setPromptText("ingrese apellidos");
        
        DatePicker nacimiento = Elementos.fecha("fecha de nacimiento");
        
        Spinner<LocalTime> hora = Elementos.hora("hora del nacimiento");
        
        StackPane checkIdentificador = Elementos.checkIdentificador();
        ImageView check = (ImageView)checkIdentificador.getChildrenUnmodifiable().get(1);
        ImageView mark = (ImageView)checkIdentificador.getChildrenUnmodifiable().get(2);
        CheckBox chkPasaporte = (CheckBox)checkIdentificador.getChildrenUnmodifiable().get(3);
        TextField pasaporte = (TextField)checkIdentificador.getChildrenUnmodifiable().get(0);
        pasaporte.setPromptText("ingrese pasaporte");
        chkPasaporte.setSelected(true);
        chkPasaporte.setVisible(false);
        
        pasaporte.textProperty().addListener((observable, o, n)->{
            if(n.length()<8){
                check.setVisible(false);
                mark.setVisible(false);
            }
            else if(Extranjero.comprobarPasaporte(n)){
                if(poblacion.getPoblacion().containsKey(n)){
                    check.setVisible(false);
                    mark.setVisible(true);
                }
                else{
                    mark.setVisible(false);
                    check.setVisible(true);
                }
            }
            else if(!Extranjero.comprobarPasaporte(n) || Extranjero.comprobarPasaporte(o)){
                check.setVisible(false);
                mark.setVisible(false);
            }
        });
        checkIdentificador.setTranslateX(-30);
        
        ToggleGroup sexo = new ToggleGroup();
        RadioButton f = new RadioButton("femenino");
        f.setToggleGroup(sexo);
        RadioButton m = new RadioButton("masculino");
        m.setToggleGroup(sexo);
        HBox sexoBox = new HBox(f, m);        
        
        StackPane parienteMadre = Elementos.checkIdentificador();
        TextField madre = (TextField)parienteMadre.getChildrenUnmodifiable().get(0);
        madre.setTranslateX(-30);
        madre.setPromptText("identificador de madre");
        CheckBox extMadre = (CheckBox)parienteMadre.getChildrenUnmodifiable().get(3);
        extMadre.setSelected(true);
        extMadre.setTranslateX(60);
        extMadre.setTooltip(new Tooltip("extanjero: debe ingreesar pasaporte"));
        ImageView checkIdMadre = (ImageView)parienteMadre.getChildrenUnmodifiable().get(1);
        ImageView markIdMadre = (ImageView)parienteMadre.getChildrenUnmodifiable().get(2);
        madre.textProperty().addListener((observable, o, n)->{
            if(n.equals(pasaporte.getText())){
                checkIdMadre.setVisible(false);
                markIdMadre.setVisible(false);
            }
            else if(n.length()==0){
                checkIdMadre.setVisible(false);
                markIdMadre.setVisible(true);
            }
            else if(n.length()>0 && n.length()<8){
                checkIdMadre.setVisible(false);
                markIdMadre.setVisible(false);
            }
            else if(!extMadre.isSelected() && Chileno.comprobarRut(n)){
                if(poblacion.getPoblacion().containsKey(n)){
                    checkIdMadre.setVisible(true);
                    markIdMadre.setVisible(false);
                }
                else{
                    markIdMadre.setVisible(true);
                    checkIdMadre.setVisible(false);
                }
            }
            else if(extMadre.isSelected()){
                if(poblacion.getPoblacion().containsKey(n)){
                    checkIdMadre.setVisible(true);
                    markIdMadre.setVisible(false);
                }
                else{
                    markIdMadre.setVisible(true);
                    checkIdMadre.setVisible(false);
                }
            }
            else if(!extMadre.isSelected() && (!Chileno.comprobarRut(n) || Chileno.comprobarRut(o)) ){
                checkIdMadre.setVisible(false);
                markIdMadre.setVisible(false);
            }
        });
        
        ComboBox tipoVisa = new ComboBox();
        tipoVisa.setPromptText(" Visa");
        tipoVisa.setMaxSize(120, 40);
        tipoVisa.setMinSize(120, 40);
        ArrayList<String> visas = new ArrayList<>();
        for(Visa i: Visa.values()){
            visas.add(i.getNombre());
        }
        ObservableList<String> listaVisas = FXCollections.observableArrayList();
        listaVisas.addAll(visas);
        tipoVisa.getItems().setAll(listaVisas);
        
        DatePicker primeraVisa = Elementos.fecha("primera visa");
        
        HBox visa = new HBox(20, primeraVisa, tipoVisa);
                
        StackPane parientePadre = Elementos.checkIdentificador();
        TextField padre = (TextField)parientePadre.getChildrenUnmodifiable().get(0);
        padre.setTranslateX(-30);
        padre.setPromptText("identificador de padre");
        CheckBox extPadre = (CheckBox)parientePadre.getChildrenUnmodifiable().get(3);
        extPadre.setTranslateX(60);
        extPadre.setSelected(true);
        extPadre.setTooltip(new Tooltip("extanjero: debe ingreesar pasaporte"));
        ImageView checkIdPadre = (ImageView)parientePadre.getChildrenUnmodifiable().get(1);
        ImageView markIdPadre = (ImageView)parientePadre.getChildrenUnmodifiable().get(2);
        padre.textProperty().addListener((observable, o, n)->{
            if(n.length()==0){
                checkIdPadre.setVisible(false);
                markIdPadre.setVisible(true);
            }
            else if(n.length()>0 && n.length()<8){
                checkIdPadre.setVisible(false);
                markIdPadre.setVisible(false);
            }
            else if(!extPadre.isSelected() && Chileno.comprobarRut(n)){
                if(poblacion.getPoblacion().containsKey(n)){
                    checkIdPadre.setVisible(true);
                    markIdPadre.setVisible(false);
                }
                else{
                    markIdPadre.setVisible(true);
                    checkIdPadre.setVisible(false);
                }
            }
            else if(extPadre.isSelected()){
                if(poblacion.getPoblacion().containsKey(n)){
                    checkIdPadre.setVisible(true);
                    markIdPadre.setVisible(false);
                }
                else{
                    markIdPadre.setVisible(true);
                    checkIdPadre.setVisible(false);
                }
            }
            else if(!extPadre.isSelected() && (!Chileno.comprobarRut(n) || Chileno.comprobarRut(o)) ){
                checkIdPadre.setVisible(false);
                markIdPadre.setVisible(false);
            }
        });
        
        Button guardar = new Button("guardar");
        guardar.setDisable(true);
        Button cancelar = new Button("cancelar");
        
        BooleanBinding validacion = 
                nombre.textProperty().isEmpty().or(
                apellido.textProperty().isEmpty().or(
                nacimiento.valueProperty().isNull().or(
                check.visibleProperty().not().or(
                f.selectedProperty().or(
                m.selectedProperty()).not()).or(
                tipoVisa.valueProperty().isNull().or(
                primeraVisa.valueProperty().isNull().or(
                hora.valueProperty().isNull().or(
                markIdMadre.visibleProperty().not().and(
                checkIdMadre.visibleProperty().not()
                ))))))));
        
        guardar.disableProperty().bind(validacion);
        
        guardar.setOnMouseClicked(lambda -> {
            Extranjero aux = new Extranjero();
            //requisitos minimos
            aux.setNombre(nombre.getText());
            aux.setApellido(apellido.getText());
            aux.setTipoDeVisa(Visa.valorDe(tipoVisa.getSelectionModel().getSelectedItem().toString()));
            aux.setPrimeraVisa(primeraVisa.getValue());
            aux.setSexo(f.isSelected()?Sexo.FEMENINO:Sexo.MASCULINO);
            aux.setNacimiento(nacimiento.getValue());
            aux.setHoraNacimiento(hora.getValue().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
            aux.setPasaporte(pasaporte.getText());
            aux.setEstadoCivil(EstadoCivil.HIJO);
            aux.setNacionalidades(Nacionalidad.CHILE);
            
            //requisitos opcionales
            Ciudadano mama = poblacion.getPoblacion().get(madre.getText());
            if(mama != null && aux.getParientes().buscarPariente(mama) == null){
                mama.setEstadoCivil(EstadoCivil.MADRE);
                aux.getParientes().agregarPariente(mama, EstadoCivil.MADRE);
                mama.getParientes().agregarPariente(aux, EstadoCivil.HIJO);
                if(extMadre.isSelected())
                    aux.setNacionalidades(mama.getNacionalidades());
            }
            
            Ciudadano papa = poblacion.getPoblacion().get(padre.getText());
            if(papa != null && aux.getParientes().buscarPariente(papa) == null){
                papa.setEstadoCivil(EstadoCivil.PADRE);
                aux.getParientes().agregarPariente(papa, EstadoCivil.PADRE);
                papa.getParientes().agregarPariente(aux, EstadoCivil.HIJO);
                if(extPadre.isSelected())
                    aux.setNacionalidades(papa.getNacionalidades());
            }
            
            pasaporte.clear();
            
            if(aux.registrar()){
                if(poblacion.getPoblacion().containsKey(aux.getPasaporte()))
                    Elementos.popMensaje("pasaporte ya registrado", 300, 100);
                else{
                    //limpiar casillas
                    nombre.clear();
                    apellido.clear();
                    if(f.isSelected())
                        f.setSelected(false);
                    else
                        m.setSelected(false);
                    nacimiento.setValue(null);
                    hora.setUserData(null);
                    pasaporte.clear();
                    madre.clear();
                    //extMadre.setSelected(false);
                    padre.clear();
                    //extPadre.setSelected(false);
                    
                    //registrar nacido
                    //ArchivoXML archivo = new ArchivoXML();
                    //archivo.guardar(aux);
                    poblacion.getPoblacion().put(aux.getPasaporte(), aux);
                    logReporte.appendText(
                            "["+horaActual+"] "+aux.getNombre().toLowerCase()+" "+aux.getApellido().toLowerCase()+
                            ", pasaporte: "+aux.getPasaporte()+" registrado \n");
                    Elementos.popMensaje("Operacion Exitosa!", 300, 100);
                }
            }
        });
        
        cancelar.setOnMouseClicked(lambda -> ventana.close());
        
        HBox barra = new HBox(20, guardar, cancelar);
        barra.setAlignment(Pos.CENTER);
        
        grid.add(nombre,0,0);
        grid.add(apellido,0,1);
        grid.add(sexoBox, 0,2);
        grid.add(visa, 0, 3);
        grid.add(nacimiento,0,4);
        grid.add(hora, 0, 5);
        grid.add(checkIdentificador,0,6);
        grid.add(parienteMadre, 0, 7);
        grid.add(parientePadre, 0, 8);
        grid.add(barra,0,9);
        
        Scene scene = new Scene(grid);
        scene.getStylesheets().add(prop.getProp().getProperty("tema_actual"));
        ventana.setScene(scene);
        ventana.show();
    }
}
