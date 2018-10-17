/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI_RegistroCivil;

import Enums.EstadoCivil;
import Enums.Nacionalidad;
import Enums.Sexo;
import static GUI_RegistroCivil.Elementos.checkRut;
import static GUI_RegistroCivil.Elementos.hora;
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
import static com.sun.deploy.security.ruleset.DRSHelper.check;
import java.time.LocalDate;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.Tooltip;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

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
        //creacion de ventana
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
        //se restringe uso de tabulador para seguridad del software
        EventHandler filtroTab = new EventHandler<KeyEvent>() {
            public void handle(KeyEvent evento) {
                if(evento.getCode() == KeyCode.TAB)
                    evento.consume();
            }
        };
        ventana.addEventFilter(KeyEvent.KEY_PRESSED, filtroTab);
        
        //ventana cuadriculada
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER_LEFT);
        grid.setHgap(50);
        grid.setVgap(20);
        grid.setPadding(new Insets(50,50,50,50));
        
        //creacion del campo Rut
        StackPane checkRut = Elementos.checkRut();
        ImageView check = (ImageView)checkRut.getChildrenUnmodifiable().get(1);
        TextField rut = (TextField)checkRut.getChildrenUnmodifiable().get(0);
        CheckBox editar = new CheckBox();
        editar.setSelected(false);
        editar.setVisible(false);
        editar.setTooltip(new Tooltip("Modificar datos"));
        editar.setTranslateX(120);
        checkRut.getChildren().add(editar);        
        
        //se comprueba que el rut este registrado
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
        
        //creacion del campo que captura el nombre
        TextField nombre = Elementos.textfield("nombre", 240, 40);
        nombre.setDisable(true);
        nombre.setMouseTransparent(true);
        
        //creacion del campo que captura el apellido
        TextField apellido = Elementos.textfield("apellido", 240, 40);
        apellido.setDisable(true);
        apellido.setMouseTransparent(true);
        
        //creacion del campo que captura la fecha de nacimiento
        DatePicker nacimiento = Elementos.fecha("fecha de nacimiento");
        nacimiento.setDisable(true);
        nacimiento.setMouseTransparent(true);
        
        //creacion del campo que captura la hora de nacimiento
        Spinner<LocalTime> hora = Elementos.hora("hora del nacimiento");
        hora.setDisable(true);
        hora.setMouseTransparent(true);
        
        //creacion del campo que captura el sexo del individuo
        ToggleGroup sexo = new ToggleGroup();
        RadioButton f = new RadioButton("femenino");
        f.setToggleGroup(sexo);
        f.setDisable(true);
        f.setMouseTransparent(true);
        RadioButton m = new RadioButton("masculino");
        m.setToggleGroup(sexo);
        m.setDisable(true);
        m.setMouseTransparent(true);
        HBox sexoBox = new HBox(f, m);
        
        //creacion del campo que captura el identificador de la madre
        StackPane parienteMadre = new StackPane();
        TextField madre = Elementos.textfield("identificador madre", 240, 40);
        madre.setEditable(false);
        
        //creacion del campo que captura el identificador del padre
        StackPane parientePadre = new StackPane();
        TextField padre = Elementos.textfield("identificador padre", 240, 40);
        padre.setEditable(false);        
        
        //creacion del campo que captura la region de residencia actual
        ComboBox region = new ComboBox();
        region.setDisable(true);
        region.setMouseTransparent(true);
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
        
        //creacion del campo que captura la comuna de residencia actual
        ComboBox comuna = new ComboBox();
        comuna.setDisable(true);
        comuna.setMouseTransparent(true);
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
        
        //creacion del campo que captura los comentarios de nacimiento
        TextArea comentario = new TextArea();
        comentario.setDisable(true);
        comentario.setMouseTransparent(true);
        comentario.setWrapText(true);
        comentario.setPromptText("comentarios de nacimiento");
        comentario.setMaxSize(400, 200);
        
        //creacion del botones principales
        Button guardar = new Button("guardar");
        guardar.disableProperty().bind(editar.selectedProperty().not());
        Button salir = new Button("salir");                      
        
        //se evalua el estado del rut ingresado (valido y registrado)
        check.visibleProperty().addListener((obs, o, n) -> {
            //si es valido y esta registrado, se muestran los datos por pantalla y se permite la modificacion
            if(n.booleanValue()){        
                aux = (Chileno)poblacion.getPoblacion().get(rut.getText());
                
                editar.setVisible(true);
                
                nombre.setDisable(false);
                nombre.setText(aux.getNombre());
                
                apellido.setDisable(false);
                apellido.setText(aux.getApellido());
                
                nacimiento.setDisable(false);
                nacimiento.setValue(aux.getNacimiento());
                
                hora.setDisable(false);
                hora.getValueFactory().setValue(LocalTime.parse(aux.getHoraNacimiento()));
                
                f.setDisable(false);
                m.setDisable(false);
                if(aux.getSexo().equals(Sexo.FEMENINO))
                    f.setSelected(true);
                else 
                    m.setSelected(true);
                
                int casilla=0;
                region.setDisable(false);
                ObservableList<String>itemsRegion = region.getItems();
                for(String i : itemsRegion){
                    if(i.equals(aux.getRegion())){
                        region.getSelectionModel().select(casilla);
                        casilla = 0;
                        break;
                    }
                    casilla++;
                }
                
                comuna.setDisable(false);
                ObservableList<String>itemsComuna = comuna.getItems();
                for(String i : itemsComuna){
                    if(i.equals(aux.getComuna())){
                        comuna.getSelectionModel().select(casilla);
                        casilla = 0;
                        break;
                    }
                    casilla++;
                }
                
                comentario.setDisable(false);
                comentario.setText(aux.getComentarioNacimiento());

                
                if(aux.getParientes().size()== 1)
                    madre.setText(aux.getParientes().keySet().stream().findFirst().get());
                else if(aux.getParientes().size()> 1)
                    padre.setText(aux.getParientes().keySet().stream().skip(1).findFirst().get());
            }
            //si es invalido o no esta registrado, no se muestra nada por pantalla
            else{                
                editar.setVisible(false);
                
                nombre.setDisable(true);
                nombre.clear();
                
                apellido.setDisable(true);
                apellido.clear();
                
                nacimiento.setDisable(true);
                nacimiento.setValue(LocalDate.MIN);
                
                hora.setDisable(true);
                hora.getValueFactory().setValue(LocalTime.MIDNIGHT);
                
                f.setDisable(true);
                f.setSelected(false);
                
                m.setDisable(true);
                m.setSelected(false);
                
                region.setDisable(true);
                region.getSelectionModel().clearSelection();
                
                comuna.setDisable(true);
                comuna.getSelectionModel().clearSelection();
                
                madre.clear();
                padre.clear();
                
                comentario.setDisable(true);
                comentario.clear();
                
                aux = null;
            }
        });
        
        //se evalua el estado de la casilla que permite modificar datos de un usuario por su rut
        editar.selectedProperty().addListener((obs, o, n)->{
            //si la casilla esta tickeada, se permite la modificacion de datos
            if(n.booleanValue()){
                //navegacion con tabulador desactivada
                ventana.removeEventFilter(KeyEvent.KEY_PRESSED, filtroTab);
                
                nombre.setMouseTransparent(false);
                apellido.setMouseTransparent(false);
                nacimiento.setMouseTransparent(false);
                hora.setMouseTransparent(false);
                madre.setMouseTransparent(false);
                padre.setMouseTransparent(false);
                region.setMouseTransparent(false);
                comuna.setMouseTransparent(false);
                f.setMouseTransparent(false);
                m.setMouseTransparent(false);
                comentario.setMouseTransparent(false);
            }
            //si la casilla no esta tickeada, no se permite la modificacion de datos
            else{
                //navegacion con tabulador activada
                ventana.addEventFilter(KeyEvent.KEY_PRESSED, filtroTab);
                
                nombre.setMouseTransparent(true);
                apellido.setMouseTransparent(true);
                nacimiento.setMouseTransparent(true);
                hora.setMouseTransparent(true);
                madre.setMouseTransparent(true);
                padre.setMouseTransparent(true);
                region.setMouseTransparent(true);
                comuna.setMouseTransparent(true);
                f.setMouseTransparent(true);
                m.setMouseTransparent(true);
                comentario.setMouseTransparent(true);
            }
        });
        
        //si se clickea el boton guardar se sobreescriben los datos del usuario
        guardar.setOnMouseClicked(lambda -> {
            Chileno aux = (Chileno)poblacion.getPoblacion().get(rut.getText());
            //requisitos minimos
            aux.setNombre(nombre.getText());
            aux.setApellido(apellido.getText());
            aux.setRegion(region.getSelectionModel().getSelectedItem().toString());
            aux.setComuna(comuna.getSelectionModel().getSelectedItem().toString());
            aux.setSexo(f.isSelected()?Sexo.FEMENINO:Sexo.MASCULINO);
            aux.setNacimiento(nacimiento.getValue());
            aux.setHoraNacimiento(hora.getValue().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
            aux.setRut(rut.getText());
            aux.setEstadoCivil(EstadoCivil.HIJO);
            aux.setNacionalidades(Nacionalidad.CHILE);
            
            //requisitos opcionales
            if(!comentario.getText().isEmpty())
                aqui error de captura datos
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
            
            logReporte.appendText(
                    "["+horaActual+"] "+"datos del ciudadano con rut: "+aux.getRut()+" modificados\n"
            );
            Elementos.popMensaje("Operacion Exitosa!", 300, 100);
            
            rut.clear();
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
