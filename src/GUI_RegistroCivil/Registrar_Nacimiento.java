
package GUI_RegistroCivil;

import Enums.EstadoCivil;
import Enums.Nacionalidad;
import Enums.Sexo;
import Excepciones.CantidadParentescoException;
import Excepciones.FormatoPasaporteException;
import Excepciones.FormatoRutException;
import Excepciones.LongitudRutException;
import utilidades.ArchivoProperties;
import colecciones.Chileno;
import colecciones.Ciudadano;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import Interfaces.Chile;
import colecciones.Extranjero;
import colecciones.Poblacion;
import javafx.scene.control.Tooltip;
import org.controlsfx.control.PopOver;


public class Registrar_Nacimiento {
    private final String horaActual = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    private PopOver tooltip;
    private TextArea logReporte;
    private ArchivoProperties prop;
    private Poblacion poblacion = Poblacion.getInstancia();
    
    public Registrar_Nacimiento(TextArea logReporte, ArchivoProperties prop) {
        this.logReporte = logReporte;
        this.poblacion = poblacion;
    }
    
    public void registrarNacimiento(MouseEvent click){

        Stage ventana = new Stage();
        ventana.setX(370);
        ventana.setY(80);
        ventana.setResizable(false);
        ventana.initStyle(StageStyle.UTILITY);
        ventana.initModality(Modality.APPLICATION_MODAL);
        ventana.setMinHeight(620);
        ventana.setMinWidth(650);
        ventana.setMaxHeight(620);
        ventana.setMaxWidth(650);
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
        
        StackPane checkRut = Elementos.checkRut();
        TextField rut = (TextField)checkRut.getChildrenUnmodifiable().get(0);
        ImageView check = (ImageView)checkRut.getChildrenUnmodifiable().get(1);
        ImageView mark = (ImageView)checkRut.getChildrenUnmodifiable().get(2);
        ImageView error = (ImageView)checkRut.getChildrenUnmodifiable().get(3);
        
        rut.textProperty().addListener((observable, o, n)->{
            checkearRut(check, mark, error, o, n);
        });
        
        error.setOnMouseEntered(lambda-> tooltip.show(error));
        error.setOnMouseExited(lambda-> tooltip.hide());
        
        mark.setOnMouseEntered(lambda-> tooltip.show(mark));
        mark.setOnMouseExited(lambda-> tooltip.hide());
        
        checkRut.setTranslateX(-30);
        
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
        extMadre.setTranslateX(60);
        extMadre.setTooltip(new Tooltip("extanjero: debe ingreesar pasaporte"));
        ImageView checkRutMadre = (ImageView)parienteMadre.getChildrenUnmodifiable().get(1);
        ImageView markRutMadre = (ImageView)parienteMadre.getChildrenUnmodifiable().get(2);
        ImageView errorRutMadre = (ImageView)parienteMadre.getChildrenUnmodifiable().get(4);
        madre.textProperty().addListener((observable, o, n)->{
            if(n.equals(rut.getText())){
                checkRutMadre.setVisible(false);
                markRutMadre.setVisible(false);
                errorRutMadre.setVisible(true);
                tooltip = Elementos.popTip("Identico al rut que se desea \nregistrar");
            }
            else if(!extMadre.isSelected()){
                checkearRutExistente(checkRutMadre, markRutMadre, errorRutMadre, o, n);
            }
            else if(extMadre.isSelected()){
                checkearPasaporteExistente(checkRutMadre, markRutMadre, errorRutMadre, o, n);
            }
        });
        
        errorRutMadre.setOnMouseEntered(lambda-> tooltip.show(errorRutMadre));
        errorRutMadre.setOnMouseExited(lambda-> tooltip.hide());
        
        markRutMadre.setOnMouseEntered(lambda-> tooltip.show(markRutMadre));
        markRutMadre.setOnMouseExited(lambda-> tooltip.hide());
        
        ComboBox region = new ComboBox();
        region.setPromptText(" Region");
        region.setMaxSize(120, 40);
        region.setMinSize(120, 40);
        ArrayList<String> regiones = new ArrayList<>();
        for(Chile i: Chile.REGIONES.values()){
            regiones.add(i.toString().replace("_"," ").toLowerCase());
        }
        ObservableList<String> listaRegiones = FXCollections.observableArrayList();
        listaRegiones.addAll(regiones);
        region.getItems().setAll(listaRegiones);
        ComboBox comuna = new ComboBox();
        comuna.setPromptText(" Comuna");
        comuna.setMaxSize(120, 40);
        comuna.setMinSize(120, 40);
        region.valueProperty().addListener((obs, o, n)-> {            
            if(n == null){
                comuna.getItems().clear();
                comuna.setDisable(true);
            }
            else{
                comuna.getItems().clear();
                ObservableList<String> listaComunas = FXCollections.observableArrayList();
                listaComunas.addAll(Elementos.comunas(n.toString()));
                comuna.getItems().setAll(listaComunas);
                comuna.setDisable(false);
            }
        });
        HBox ciudadOrigen = new HBox(20, region, comuna);
                
        StackPane parientePadre = Elementos.checkIdentificador();
        TextField padre = (TextField)parientePadre.getChildrenUnmodifiable().get(0);
        padre.setTranslateX(-30);
        padre.setPromptText("identificador de padre");
        CheckBox extPadre = (CheckBox)parientePadre.getChildrenUnmodifiable().get(3);
        extPadre.setTranslateX(60);
        extPadre.setTooltip(new Tooltip("extanjero: debe ingreesar pasaporte"));
        ImageView checkRutPadre = (ImageView)parientePadre.getChildrenUnmodifiable().get(1);
        ImageView markRutPadre = (ImageView)parientePadre.getChildrenUnmodifiable().get(2);
        ImageView errorRutPadre = (ImageView)parientePadre.getChildrenUnmodifiable().get(4);
        padre.textProperty().addListener((observable, o, n)->{
            if(n.equals(rut.getText())){
                checkRutPadre.setVisible(false);
                markRutPadre.setVisible(false);
                errorRutPadre.setVisible(true);
                tooltip = Elementos.popTip("Identico al rut que se desea \nregistrar");
            }
            else if(!extMadre.isSelected()){
                checkearRutExistente(checkRutPadre, markRutPadre, errorRutPadre, o, n);
            }
            else if(extMadre.isSelected()){
                checkearPasaporteExistente(checkRutPadre, markRutPadre, errorRutPadre, o, n);
            }
        });
        
        errorRutPadre.setOnMouseEntered(lambda-> tooltip.show(errorRutPadre));
        errorRutPadre.setOnMouseExited(lambda-> tooltip.hide());
        
        markRutPadre.setOnMouseEntered(lambda-> tooltip.show(markRutPadre));
        markRutPadre.setOnMouseExited(lambda-> tooltip.hide());
        
        TextArea comentario = new TextArea();
        comentario.setWrapText(true);
        comentario.setPromptText("comentarios");
        comentario.setMaxSize(400, 200);
        
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
                region.valueProperty().isNull().or(
                comuna.valueProperty().isNull().or(
                hora.valueProperty().isNull().or(
                markRutMadre.visibleProperty().not().and(
                checkRutMadre.visibleProperty().not()
                ))))))));
        
        guardar.disableProperty().bind(validacion);
        
        guardar.setOnMouseClicked(lambda -> {
            Chileno aux = new Chileno();
            //requisitos minimos
            aux.setNombre(nombre.getText());
            aux.setApellido(apellido.getText());
            aux.setRegionDeNacimiento(region.getSelectionModel().getSelectedItem().toString());
            aux.setComunaDeNacimiento(comuna.getSelectionModel().getSelectedItem().toString());
            aux.setSexo(f.isSelected()?Sexo.FEMENINO:Sexo.MASCULINO);
            aux.setNacimiento(nacimiento.getValue());
            aux.setHoraNacimiento(hora.getValue().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
            aux.setRut(rut.getText());
            aux.setEstadoCivil(EstadoCivil.HIJO);
            aux.setNacionalidades(Nacionalidad.CHILE);
            
            //requisitos opcionales
            if(!comentario.getText().isEmpty())
                aux.setComentarioNacimiento(comentario.getText());
            
            Ciudadano mama = poblacion.getCiudadano(madre.getText());
            if(mama != null && aux.getParientes().buscarPariente(mama.mostrarIdentificador()) == null){
                try{
                    aux.getParientes().agregarPariente(mama.mostrarIdentificador(), EstadoCivil.MADRE);
                    mama.setEstadoCivil(EstadoCivil.MADRE);
                    mama.getParientes().agregarPariente(aux.mostrarIdentificador(), EstadoCivil.HIJO);
                    if(extMadre.isSelected())
                        aux.setNacionalidades(mama.getNacionalidades());
                }catch(CantidadParentescoException e){
                    Elementos.notificar("Advertencia", CantidadParentescoException.getMensaje());
                }
            }
            
            Ciudadano papa = poblacion.getCiudadano(padre.getText());
            if(papa != null && aux.getParientes().buscarPariente(papa.mostrarIdentificador()) == null){
                try{
                    aux.getParientes().agregarPariente(papa.mostrarIdentificador(), EstadoCivil.PADRE);
                    papa.setEstadoCivil(EstadoCivil.PADRE);
                    papa.getParientes().agregarPariente(aux.mostrarIdentificador(), EstadoCivil.HIJO);
                    if(extPadre.isSelected())
                        aux.setNacionalidades(papa.getNacionalidades());
                }catch(CantidadParentescoException e){
                    Elementos.notificar("Advertencia", CantidadParentescoException.getMensaje());
                }
            }
            
            rut.clear();
            
            if(poblacion.esRegistrable(aux.mostrarIdentificador())){
                poblacion.registrarCiudadano(aux);
                //limpiar casillas
                nombre.clear();
                apellido.clear();
                if(f.isSelected())
                    f.setSelected(false);
                else
                    m.setSelected(false);
                nacimiento.setValue(null);
                hora.setUserData(null);
                rut.clear();
                madre.clear();
                //extMadre.setSelected(false);
                padre.clear();
                //extPadre.setSelected(false);
                
                logReporte.appendText(
                        "["+horaActual+"] "+aux.getNombre().toLowerCase()+" "+aux.getApellido().toLowerCase()+
                        ", rut: "+aux.getRut()+" registrado \n");
                Elementos.popMensaje("Operacion Exitosa!", 300, 100);
            }
            else{   
                Elementos.popMensaje("Rut ya registrado", 300, 100);
            }
        });
        
        cancelar.setOnMouseClicked(lambda -> ventana.close());
        
        HBox barra = new HBox(20, guardar, cancelar);
        barra.setAlignment(Pos.CENTER);
        
        grid.add(nombre,0,0);
        grid.add(apellido,0,1);
        grid.add(ciudadOrigen,0,2);
        grid.add(sexoBox, 0,3);
        grid.add(nacimiento,0,4);
        grid.add(hora, 0, 5);
        grid.add(checkRut,0,6);
        grid.add(parienteMadre, 0, 7);
        grid.add(parientePadre, 0, 8);
        GridPane.setRowSpan(comentario, 3);
        grid.add(comentario, 1, 2);
        grid.add(barra,1,6);
        
        Scene scene = new Scene(grid);
        scene.getStylesheets().add(prop.getProp().getProperty("tema_actual"));
        ventana.setScene(scene);
        ventana.show();
    }
    
    
    private void checkearRut(ImageView check, ImageView mark, ImageView error, String o, String n){
        try{
            if(Chileno.comprobarRut(n)){
                if(poblacion.getChileno(n)==null){
                    check.setVisible(true);
                    mark.setVisible(false);
                    error.setVisible(false);
                }
                else{
                    check.setVisible(false);
                    mark.setVisible(false);
                    error.setVisible(true);
                    tooltip = Elementos.popTip("rut ya registrado");
                }
            }
            else if(!Chileno.comprobarRut(n) || Chileno.comprobarRut(o)){
                check.setVisible(false);
                mark.setVisible(false);
                error.setVisible(false);
            }
        }catch(LongitudRutException e){
            if(n.length()==0){
                check.setVisible(false);
                mark.setVisible(false);
                error.setVisible(true);
                tooltip = Elementos.popTip("Debe ingresar un rut");
            }
            else{
                check.setVisible(false);
                mark.setVisible(false);
                error.setVisible(true);
                tooltip = Elementos.popTip(LongitudRutException.getMensaje());
            }
        }catch(FormatoRutException e){
            check.setVisible(false);
            mark.setVisible(false);
            error.setVisible(true);
            tooltip = Elementos.popTip(FormatoRutException.getMensaje());
        }
    }
    
    private void checkearPasaporteExistente(ImageView check, ImageView mark, ImageView error, String o, String n){
        try{
            if(Extranjero.comprobarPasaporte(n)){
                if(poblacion.getExtranjero(n)!=null){
                    check.setVisible(true);
                    mark.setVisible(false);
                    error.setVisible(false);
                }
                else{
                    mark.setVisible(false);
                    check.setVisible(false);
                    error.setVisible(true);
                }
            }
            else if(!Extranjero.comprobarPasaporte(n) || Extranjero.comprobarPasaporte(o)){
                check.setVisible(false);
                mark.setVisible(false);
                error.setVisible(false);
            }
        }catch(FormatoPasaporteException e){
            check.setVisible(false);
            mark.setVisible(false);
            error.setVisible(true);
        }
    }
    
    private void checkearRutExistente(ImageView check, ImageView mark, ImageView error, String o, String n){
        try{
            if(Chileno.comprobarRut(n)){
                if(poblacion.getChileno(n)!=null){
                    check.setVisible(true);
                    mark.setVisible(false);
                    error.setVisible(false);
                }
                else{
                    mark.setVisible(false);
                    check.setVisible(false);
                    error.setVisible(true);
                    tooltip = Elementos.popTip("no existe Chileno registrado con el rut");
                }
            }
            else if(!Chileno.comprobarRut(n) || Chileno.comprobarRut(o)){
                check.setVisible(false);
                mark.setVisible(false);
                error.setVisible(false);
            }
        }catch(LongitudRutException e){
            if(n.length()==0){
                check.setVisible(false);
                mark.setVisible(true);
                error.setVisible(false);
                tooltip = Elementos.popTip("¿no registrara al pariente?");
            }
            else{
                check.setVisible(false);
                mark.setVisible(false);
                error.setVisible(true);
                tooltip = Elementos.popTip(LongitudRutException.getMensaje());
            }
        }catch(FormatoRutException e){
            check.setVisible(false);
            mark.setVisible(false);
            error.setVisible(true);
            tooltip = Elementos.popTip(FormatoRutException.getMensaje());
        }
    }
    
}