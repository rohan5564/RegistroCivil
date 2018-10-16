/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI_RegistroCivil;

import Enums.EstadoCivil;
import Enums.Nacionalidad;
import Enums.Sexo;
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
import utilidades.ArchivoProperties;
import Interfaces.Chile;
import colecciones.Poblacion;
import java.time.LocalDate;
import javafx.scene.control.Tooltip;

/**
 *
 * @author Jean
 */
public class Buscar_Nacimiento {
    private final String horaActual = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    private TextArea logReporte;
    private ArchivoProperties prop;
    private Poblacion poblacion;
    private Chileno aux;

    public Buscar_Nacimiento(TextArea logReporte, Poblacion poblacion, ArchivoProperties prop) {
        this.logReporte = logReporte;
        this.poblacion = poblacion;
        this.prop = prop;
    }
    
    public void buscarNacimiento(MouseEvent click){
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
        
        TextField nombre = Elementos.textfield("nombre", 240, 40);
        TextField apellido = Elementos.textfield("apellido", 240, 40);
        DatePicker nacimiento = Elementos.fecha("fecha de nacimiento");
        Spinner<LocalTime> hora = Elementos.hora("hora del nacimiento");
        StackPane checkRut = Elementos.checkRut();
        ImageView check = (ImageView)checkRut.getChildrenUnmodifiable().get(1);
        TextField rut = (TextField)checkRut.getChildrenUnmodifiable().get(0);
        CheckBox editar = new CheckBox();
        editar.setSelected(false);
        editar.setVisible(false);
        editar.setTooltip(new Tooltip("Modificar datos"));
        editar.setTranslateX(120);
        checkRut.getChildren().add(editar);
        rut.textProperty().addListener((observable, o, n)->{
            if(n.length()<8){
                check.setVisible(false);
                editar.setVisible(false);
            }
            else if(Chileno.comprobarRut(n)){
                if(poblacion.getPoblacion().containsKey(n)){
                    editar.setVisible(true);
                    check.setVisible(true);
                }
                else{
                    editar.setVisible(false);
                    check.setVisible(false);
                }
            }
            else if(!Chileno.comprobarRut(n) || Chileno.comprobarRut(o)){
                check.setVisible(false);
                editar.setVisible(false);
            }
        });
        ToggleGroup sexo = new ToggleGroup();
        RadioButton f = new RadioButton("femenino");
        f.setToggleGroup(sexo);
        RadioButton m = new RadioButton("masculino");
        m.setToggleGroup(sexo);
        HBox sexoBox = new HBox(f, m);        
        
        StackPane parienteMadre = new StackPane();
        TextField madre = Elementos.textfield("identificador madre", 240, 40);
        StackPane parientePadre = new StackPane();
        TextField padre = Elementos.textfield("identificador padre", 240, 40);
        
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
        
        TextArea comentario = new TextArea();
        comentario.setWrapText(true);
        comentario.setPromptText("comentarios");
        comentario.setMaxSize(400, 200);
        
        Button guardar = new Button("guardar");
        guardar.setDisable(true);
        Button salir = new Button("salir");
        
        check.visibleProperty().addListener((obs, o, n) -> {
            if(n.booleanValue()){
                editar.setVisible(true);
                aux = (Chileno)poblacion.getPoblacion().get(rut.getText());
                nombre.setText(aux.getNombre());
                apellido.setText(aux.getApellido());
                nacimiento.setValue(aux.getNacimiento());
                hora.getValueFactory().setValue(LocalTime.parse(aux.getHoraNacimiento()));
                if(aux.getParientes().size()== 1)
                    madre.setText(aux.getParientes().keySet().stream().findFirst().get());
                else if(aux.getParientes().size()> 1)
                    padre.setText(aux.getParientes().keySet().stream().skip(1).findFirst().get());
                if(aux.getSexo().equals(Sexo.FEMENINO))
                    f.setSelected(true);
                else 
                    m.setSelected(true);
                ObservableList<String>itemsRegion = region.getItems();
                int casilla=0;
                for(String i : itemsRegion){
                    if(i.equals(aux.getRegion())){
                        region.getSelectionModel().select(casilla);
                        casilla = 0;
                        break;
                    }
                    casilla++;
                }
                ObservableList<String>itemsComuna = comuna.getItems();
                for(String i : itemsComuna){
                    if(i.equals(aux.getComuna())){
                        comuna.getSelectionModel().select(casilla);
                        casilla = 0;
                        break;
                    }
                    casilla++;
                }
                /*comuna.setValue(true);*/
                comentario.setText(aux.getComentarioNacimiento());
            }
            else{
                editar.setVisible(true);
                aux = null;
                nombre.clear();
                apellido.clear();
                nacimiento.setValue(LocalDate.MIN);
                hora.getValueFactory().setValue(LocalTime.MIDNIGHT);
                madre.clear();
                padre.clear();
                comentario.clear();
            }
        });
        
        nombre.setEditable(false);
        apellido.setEditable(false);
        nacimiento.setEditable(false);
        hora.setEditable(false);
        madre.setEditable(false);
        padre.setEditable(false);
        region.setEditable(false);
        comuna.setEditable(false);
        comentario.setEditable(false);
        guardar.setDisable(true);
        editar.selectedProperty().addListener((obs, o, n)->{
            if(n.booleanValue()){
                nombre.setEditable(true);
                apellido.setEditable(true);
                nacimiento.setEditable(true);
                hora.setEditable(true);
                madre.setEditable(true);
                padre.setEditable(true);
                region.setEditable(true);
                comuna.setEditable(true);
                comentario.setEditable(true);
                guardar.setDisable(false);
            }
            else{
                nombre.setEditable(false);
                apellido.setEditable(false);
                nacimiento.setEditable(false);
                hora.setEditable(false);
                madre.setEditable(false);
                padre.setEditable(false);
                region.setEditable(false);
                comuna.setEditable(false);
                comentario.setEditable(false);
                guardar.setDisable(true);
            }
        });
        
        BooleanBinding validacion = 
                nombre.textProperty().isEmpty().or(
                apellido.textProperty().isEmpty().or(
                nacimiento.valueProperty().isNull().or(
                check.visibleProperty().not().or(
                f.selectedProperty().or(
                m.selectedProperty()).not()).or(
                region.valueProperty().isNull().or(
                comuna.valueProperty().isNull().or(
                hora.valueProperty().isNull()
                ))))));
        
        guardar.disableProperty().bind(validacion);
        
        guardar.setOnMouseClicked(lambda -> {
            Chileno aux = (Chileno)poblacion.getPoblacion().get(rut.getText());
            //requisitos minimos
            aux.setNombre(nombre.getText());
            aux.setApellido(apellido.getText());
            aux.setRegion(comuna.getSelectionModel().getSelectedItem().toString()+", "+region.getSelectionModel().getSelectedItem().toString());
            aux.setSexo(f.isSelected()?Sexo.FEMENINO:Sexo.MASCULINO);
            aux.setNacimiento(nacimiento.getValue());
            aux.setHoraNacimiento(hora.getValue().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
            aux.setRut(rut.getText());
            aux.setEstadoCivil(EstadoCivil.HIJO);
            aux.setNacionalidades(Nacionalidad.CHILE);
            
            //requisitos opcionales
            if(!comentario.getText().isEmpty())
                aux.setComentarioNacimiento(comentario.getText());
            Ciudadano mama = poblacion.getPoblacion().get(madre.getText());
            if(!madre.getText().isEmpty() && poblacion.getPoblacion().containsKey(madre.getText())){
                mama.setEstadoCivil(EstadoCivil.MADRE);
                aux.agregarParientes(madre.getText(), mama);
                /*if(extMadre.isSelected()){
                    aux.setNacionalidades(mama.getNacionalidades());
                }*/
            }
            
            Ciudadano papa = poblacion.getPoblacion().get(padre.getText());
            if(!padre.getText().isEmpty() && poblacion.getPoblacion().containsKey(padre.getText())){
                papa.setEstadoCivil(EstadoCivil.PADRE);
                aux.agregarParientes(padre.getText(), papa);
                /*if(extPadre.isSelected()){
                    aux.setNacionalidades(papa.getNacionalidades());
                }*/
            }
            
            rut.clear();
            
            if(aux.registrarNacimiento()){
                if(poblacion.getPoblacion().containsKey(aux.getRut()))
                    Elementos.popMensaje("Rut ya registrado", 300, 100);
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
                    rut.clear();
                    madre.clear();
                    //extMadre.setSelected(false);
                    padre.clear();
                    //extPadre.setSelected(false);
                    
                    //registrar nacido
                    poblacion.getPoblacion().put(aux.getRut(), aux);
                    logReporte.appendText(
                            "["+horaActual+"]"+aux.getNombre().toLowerCase()+" "+aux.getApellido().toLowerCase()+
                            ", rut: "+aux.getRut()+" registrado \n");
                    Elementos.popMensaje("Operacion Exitosa!", 300, 100);
                }
            }
        });
        
        salir.setOnMouseClicked(lambda -> ventana.close());
        HBox barra = new HBox(20, guardar, salir);
        barra.setAlignment(Pos.CENTER);
        
        grid.add(checkRut,0,0);
        grid.add(nombre,0,1);
        grid.add(apellido,0,2);
        grid.add(ciudadOrigen,0,3);
        grid.add(sexoBox, 0,4);
        grid.add(nacimiento,0,5);
        grid.add(hora, 0,6);
        grid.add(parienteMadre, 0,8);
        grid.add(parientePadre, 0,9);
        GridPane.setRowSpan(comentario, 3);
        grid.add(comentario, 1, 2);
        grid.add(barra,1,6);
        
        Scene scene = new Scene(grid);
        scene.getStylesheets().add(prop.getProp().getProperty("tema_actual"));
        ventana.setScene(scene);
        ventana.show();
    }
}
