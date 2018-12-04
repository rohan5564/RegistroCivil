
package GUI_RegistroCivil;

import Enums.EstadoCivil;
import Enums.Nacionalidad;
import Enums.Sexo;
import Enums.Visa;
import Excepciones.FormatoPasaporteException;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utilidades.ArchivoProperties;


public class Registrar_Extranjero {
    private final String horaActual = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    private TextArea logReporte;
    private ArchivoProperties prop;
    private Poblacion poblacion;

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
            checkearPasaporte(check, mark, mark, o, n);
        });
        checkIdentificador.setTranslateX(-30);
        
        ToggleGroup sexo = new ToggleGroup();
        RadioButton f = new RadioButton("femenino");
        f.setToggleGroup(sexo);
        RadioButton m = new RadioButton("masculino");
        m.setToggleGroup(sexo);
        HBox sexoBox = new HBox(f, m);        
        
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
                hora.valueProperty().isNull()
                ))))));
        
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
            
            pasaporte.clear();
            
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
                pasaporte.clear();
                
                //registrar nacido
                //ArchivoXML archivo = new ArchivoXML();
                //archivo.guardar(aux);
                logReporte.appendText(
                        "["+horaActual+"] "+aux.getNombre().toLowerCase()+" "+aux.getApellido().toLowerCase()+
                                ", pasaporte: "+aux.getPasaporte()+" registrado \n");   
                Elementos.popMensaje("Operacion Exitosa!", 300, 100);
            }
            else{
                Elementos.popMensaje("pasaporte ya registrado", 300, 100);
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
        grid.add(barra,0,7);
        
        Scene scene = new Scene(grid);
        scene.getStylesheets().add(prop.getProp().getProperty("tema_actual"));
        ventana.setScene(scene);
        ventana.show();
    }
    
    private void checkearPasaporte(ImageView check, ImageView mark, ImageView error, String o, String n){
        try{
            if(Extranjero.comprobarPasaporte(n)){
                if(poblacion.esRegistrable(n)){
                    check.setVisible(true);
                    mark.setVisible(false);
                    error.setVisible(false);
                }
                else{
                    mark.setVisible(false);
                    check.setVisible(true);
                    error.setVisible(false);
                }
            }
            else if(!Extranjero.comprobarPasaporte(n) || Extranjero.comprobarPasaporte(o)){
                check.setVisible(false);
                mark.setVisible(false);
                error.setVisible(true);
            }
        }catch(FormatoPasaporteException e){
            check.setVisible(false);
            mark.setVisible(false);
            error.setVisible(true);
        }
    }
}
