/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI_RegistroCivil;

import Enums.EstadoCivil;
import Enums.Sexo;
import colecciones.Chileno;
import colecciones.Ciudadano;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * ventana que permite la modificacion de datos para los ruts registrados
 * @since Entrega A
 */
public class Buscar_Ciudadano {
    private final String horaActual = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    private TextArea logReporte;
    private ArchivoProperties prop;
    private Poblacion poblacion;
    private Chileno aux;

    public Buscar_Ciudadano(TextArea logReporte, Poblacion poblacion, ArchivoProperties prop) {
        this.logReporte = logReporte;
        this.poblacion = poblacion;
        this.prop = prop;
    }
    
    public void buscarCiudadano(MouseEvent click){
        //creacion de ventana
        Stage ventana = new Stage();
        ventana.setX(370);
        ventana.setY(80);
        ventana.setResizable(false);
        ventana.initStyle(StageStyle.UTILITY);
        ventana.initModality(Modality.APPLICATION_MODAL);
        ventana.setMinHeight(620);
        ventana.setMinWidth(950);
        ventana.setMaxHeight(620);
        ventana.setMaxWidth(950);
        
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
        verificarIdentificador(rut, editar, check);
        
        //creacion del campo que captura el nombre
        TextField nombre = Elementos.textfield("nombre", 200, 40);
        nombre.setDisable(true);
        nombre.setMouseTransparent(true);
        
        //creacion del campo que captura el apellido
        TextField apellido = Elementos.textfield("apellido", 200, 40);
        apellido.setDisable(true);
        apellido.setMouseTransparent(true);        
        
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
        
        //creacion del campo que captura la fecha de nacimiento
        DatePicker fechaNacimiento = Elementos.fecha("fecha de nacimiento");
        fechaNacimiento.setDisable(true);
        fechaNacimiento.setMouseTransparent(true);
        
        //creacion del campo que captura la fecha de defuncion
        DatePicker fechaDefuncion = Elementos.fecha("fecha de muerte");
        fechaDefuncion.setDisable(true);
        fechaDefuncion.setMouseTransparent(true);
        
        //creacion del campo que captura la hora de nacimiento
        Spinner<LocalTime> horaNacimiento = Elementos.hora("hora del nacimiento");
        horaNacimiento.setDisable(true);
        horaNacimiento.setMouseTransparent(true);
        
        //creacion del campo que captura la hora de defuncion
        Spinner<LocalTime> horaDefuncion = Elementos.hora("hora del muerte");
        horaDefuncion.setDisable(true);
        horaDefuncion.setMouseTransparent(true);
        
        //creacion del campo que captura el identificador de la madre
        StackPane parienteMadre = Elementos.checkPariente("rut de la madre");
        TextField madre = (TextField)parienteMadre.getChildren().get(0);
        parienteMadre.setDisable(true);   
        parienteMadre.setMouseTransparent(true);
        CheckBox extMadre = (CheckBox)parienteMadre.getChildrenUnmodifiable().get(1);
        ImageView checkMadre = (ImageView)parienteMadre.getChildrenUnmodifiable().get(2);
        ImageView markMadre = (ImageView)parienteMadre.getChildrenUnmodifiable().get(3);
        List<String> promptTextMadre = new ArrayList(Arrays.asList("Rut de la madre", "Pasaporte de la madre"));
        verificarIdentificadorPariente(madre, rut, extMadre, checkMadre, markMadre, promptTextMadre);
        
        //creacion del campo que captura el identificador del padre
        StackPane parientePadre = Elementos.checkPariente("rut del padre");
        TextField padre = (TextField)parientePadre.getChildren().get(0);
        parientePadre.setDisable(true);   
        parientePadre.setMouseTransparent(true);
        CheckBox extPadre = (CheckBox)parientePadre.getChildrenUnmodifiable().get(1);
        ImageView checkPadre = (ImageView)parientePadre.getChildrenUnmodifiable().get(2);
        ImageView markPadre = (ImageView)parientePadre.getChildrenUnmodifiable().get(3);
        List<String> promptTextPadre = new ArrayList(Arrays.asList("Rut del padre", "Pasaporte del padre"));
        verificarIdentificadorPariente(padre, rut, extPadre, checkPadre, markPadre, promptTextPadre);
        
        //creacion del boton que abre la lista de parientes
        Button parientes = new Button("Parientes");
        parientes.setDisable(true);
        parientes.setMouseTransparent(true);
        
        //creacion del campo que captura los comentarios de nacimiento
        TextArea comentarioNacimiento = new TextArea();
        comentarioNacimiento.setDisable(true);
        comentarioNacimiento.setMouseTransparent(true);
        comentarioNacimiento.setWrapText(true);
        comentarioNacimiento.setPromptText("comentarios de nacimiento");
        comentarioNacimiento.setMinSize(300, 200);
        
        //creacion del campo que captura los comentarios de defuncion
        TextArea comentarioDefuncion = new TextArea();
        comentarioDefuncion.setDisable(true);
        comentarioDefuncion.setMouseTransparent(true);
        comentarioDefuncion.setWrapText(true);
        comentarioDefuncion.setPromptText("comentarios de defuncion");
        comentarioDefuncion.setMinSize(300, 200);
        
        //creacion del campo que captura la profesion
        TextField profesion = Elementos.textfield("profesion", 200, 40);
        profesion.setDisable(true);
        profesion.setMouseTransparent(true);
        
        //creacion del campo que captura la region de residencia actual
        ComboBox region = new ComboBox();
        region.setDisable(true);
        region.setMouseTransparent(true);
        region.setPromptText(" Region");
        region.setMaxSize(120, 40);
        region.setMinSize(120, 40);
        ArrayList<String> regiones = new ArrayList<>();
        for(Chile.REGIONES i: Chile.REGIONES.values()){
            regiones.add(i.getNombre());
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
        
        //campo de region permite visualizar y rellenar el campo de comunas
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
        
        //creacion del botones principales
        Button guardar = new Button("guardar");
        guardar.disableProperty().bind(editar.selectedProperty().not());           
        Button borrar = new Button("borrar");
        borrar.disableProperty().bind(check.visibleProperty().not());
        Button salir = new Button("salir");  
        HBox barra = new HBox(20, guardar, borrar, salir);
        barra.setAlignment(Pos.CENTER);
        
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
                
                profesion.setDisable(false);
                if(aux.getProfesion()!=null)
                    profesion.setText(aux.getProfesion());
                
                fechaNacimiento.setDisable(false);
                fechaNacimiento.setValue(aux.getNacimiento());
                
                fechaDefuncion.setDisable(false);
                if(aux.getDefuncion()!=null)
                    fechaDefuncion.setValue(aux.getDefuncion());
                
                horaNacimiento.setDisable(false);
                horaNacimiento.getValueFactory().setValue(LocalTime.parse(aux.getHoraNacimiento()));
                
                horaDefuncion.setDisable(false);
                if(aux.getHoraDefuncion()!=null)
                    horaDefuncion.getValueFactory().setValue(LocalTime.parse(aux.getHoraDefuncion()));
                
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
                
                comentarioNacimiento.setDisable(false);
                comentarioNacimiento.setText(aux.getComentarioNacimiento());
                
                comentarioDefuncion.setDisable(false);
                comentarioDefuncion.setText(aux.getComentarioDefuncion());

                parienteMadre.setDisable(false);
                if(aux.getParientes().getPersonas().containsKey(EstadoCivil.MADRE)){
                    Ciudadano mama = aux.getParientes().getPersonas().get(EstadoCivil.MADRE).get(0);
                    madre.setText(mama.mostrarIdentificador());
                }
                
                parientePadre.setDisable(false);
                if(aux.getParientes().getPersonas().containsKey(EstadoCivil.PADRE)){
                    Ciudadano papa = aux.getParientes().getPersonas().get(EstadoCivil.PADRE).get(0);
                    padre.setText(papa.mostrarIdentificador());
                }
                
                Map<?,?> listaPersonas = aux.getParientes().getPersonas();
                if(listaPersonas == null || listaPersonas.isEmpty())
                    parientes.setDisable(true);
                else
                    parientes.setDisable(false);
            }
            //si es invalido o no esta registrado, no se muestra nada por pantalla
            else{                
                editar.setVisible(false);
                
                nombre.setDisable(true);
                nombre.clear();
                
                apellido.setDisable(true);
                apellido.clear();
                
                profesion.setDisable(true);
                profesion.clear();
                
                fechaNacimiento.setDisable(true);
                fechaNacimiento.setValue(LocalDate.MIN);
                
                fechaDefuncion.setDisable(true);
                fechaDefuncion.setValue(LocalDate.MIN);
                
                horaNacimiento.setDisable(true);
                horaNacimiento.getValueFactory().setValue(LocalTime.MIDNIGHT);
                
                horaDefuncion.setDisable(true);
                horaDefuncion.getValueFactory().setValue(LocalTime.MIDNIGHT);
                
                f.setDisable(true);
                f.setSelected(false);
                
                m.setDisable(true);
                m.setSelected(false);
                
                region.setDisable(true);
                region.getSelectionModel().clearSelection();
                
                comuna.setDisable(true);
                comuna.getSelectionModel().clearSelection();
                
                parienteMadre.setDisable(true);
                madre.clear();
                
                parientePadre.setDisable(true);
                padre.clear();
                
                parientes.setDisable(true);
                
                comentarioNacimiento.setDisable(true);
                comentarioNacimiento.clear();
                
                comentarioDefuncion.setDisable(true);
                comentarioDefuncion.clear();
                
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
                profesion.setMouseTransparent(false);
                fechaNacimiento.setMouseTransparent(false);
                fechaDefuncion.setMouseTransparent(false);
                horaNacimiento.setMouseTransparent(false);
                horaDefuncion.setMouseTransparent(false);
                parienteMadre.setMouseTransparent(false);
                parientePadre.setMouseTransparent(false);
                parientes.setMouseTransparent(false);
                region.setMouseTransparent(false);
                comuna.setMouseTransparent(false);
                f.setMouseTransparent(false);
                m.setMouseTransparent(false);
                comentarioNacimiento.setMouseTransparent(false);
                comentarioDefuncion.setMouseTransparent(false);
            }
            //si la casilla no esta tickeada, no se permite la modificacion de datos
            else{
                //navegacion con tabulador activada
                ventana.addEventFilter(KeyEvent.KEY_PRESSED, filtroTab);
                
                nombre.setMouseTransparent(true);
                apellido.setMouseTransparent(true);
                profesion.setMouseTransparent(true);
                fechaNacimiento.setMouseTransparent(true);
                fechaDefuncion.setMouseTransparent(true);                
                horaNacimiento.setMouseTransparent(true);
                horaDefuncion.setMouseTransparent(true);
                parienteMadre.setMouseTransparent(true);
                parientePadre.setMouseTransparent(true);
                parientes.setMouseTransparent(true);
                region.setMouseTransparent(true);
                comuna.setMouseTransparent(true);
                f.setMouseTransparent(true);
                m.setMouseTransparent(true);
                comentarioNacimiento.setMouseTransparent(true);
                comentarioDefuncion.setMouseTransparent(true);
            }
        });
        
        //si se clickea el boton hijos, mostrara una nuevaa ventana con el listado
        parientes.setOnMouseClicked(lambda -> verParientes(aux));
        
        //si se clickea el boton salir, la ventana se cierra
        salir.setOnMouseClicked(lambda -> ventana.close());
        
        //si se clickea el boton borrar, se eliminan el rut y los datos del ciudadano
        borrar.setOnMouseClicked(lambda -> {
            Chileno aux = (Chileno)poblacion.getPoblacion().get(rut.getText());
            ventanaBorrar(rut, aux, 300, 100);
        });
        
        //si se clickea el boton guardar, se sobreescriben los datos del usuario
        guardar.setOnMouseClicked(lambda -> {
            Chileno aux = (Chileno)poblacion.getPoblacion().get(rut.getText());
            
            //requisitos minimos
            aux.setNombre(nombre.getText());
            aux.setApellido(apellido.getText());
            aux.setRegion(region.getSelectionModel().getSelectedItem().toString());
            aux.setComuna(comuna.getSelectionModel().getSelectedItem().toString());
            aux.setSexo(f.isSelected()?Sexo.FEMENINO:Sexo.MASCULINO);
            aux.setNacimiento(fechaNacimiento.getValue());
            aux.setHoraNacimiento(horaNacimiento.getValue().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
            aux.setRut(rut.getText());
            
            //requisitos opcionales
            aux.setProfesion(profesion.getText());
            aux.setDefuncion(fechaDefuncion.getValue());
            aux.setHoraDefuncion(horaDefuncion.getValue().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
            
            if(comentarioNacimiento.getText() != null){
                if(comentarioNacimiento.getText().length()>1)
                    aux.setComentarioNacimiento(comentarioNacimiento.getText());
                else
                    aux.setComentarioNacimiento(null);
            }
            
            if(comentarioDefuncion.getText() != null){
                if(comentarioDefuncion.getText().length()>1)
                    aux.setComentarioDefuncion(comentarioDefuncion.getText());
                else
                    aux.setComentarioDefuncion(null);
            }
            
            Ciudadano mama = poblacion.getPoblacion().get(madre.getText());
            if(mama != null){
                mama.setEstadoCivil(EstadoCivil.MADRE);
                aux.getParientes().agregarPariente(mama, EstadoCivil.MADRE);
                mama.getParientes().agregarPariente(aux, EstadoCivil.HIJO);
            }
            
            Ciudadano papa = poblacion.getPoblacion().get(padre.getText());
            if(papa != null){
                papa.setEstadoCivil(EstadoCivil.PADRE);
                aux.getParientes().agregarPariente(papa, EstadoCivil.PADRE);
                papa.getParientes().agregarPariente(aux, EstadoCivil.HIJO);
            }
            
            //se registra informacion en el historial, terminando la operacion de forma exitosa
            logReporte.appendText(
                    "["+horaActual+"] "+"datos del ciudadano con rut: "+aux.getRut()+" modificados\n"
            );
            Elementos.popMensaje("Operacion Exitosa!", 300, 100);
            
            //resetea la ventana
            rut.clear();
        });
        
        //se agregan objetos visuales a la cuadricula
        grid.add(checkRut,0,0);
        grid.add(nombre,0,1);
        grid.add(apellido,0,2);
        grid.add(ciudadOrigen,0,3);
        grid.add(sexoBox, 0,4);
        grid.add(fechaNacimiento,0,5);
        grid.add(horaNacimiento, 0,6);
        parienteMadre.setTranslateX(-33);
        grid.add(parienteMadre, 0,8);
        parientePadre.setTranslateX(-33);
        grid.add(parientePadre, 0,9);
        
        
        grid.add(profesion, 1, 1);
        grid.add(fechaDefuncion,1,2);
        grid.add(horaDefuncion, 1,3);
        grid.add(parientes, 1, 4);
        
        GridPane.setRowSpan(comentarioNacimiento, 3);
        grid.add(comentarioNacimiento, 2, 1);
        GridPane.setRowSpan(comentarioDefuncion, 3);
        grid.add(comentarioDefuncion, 2, 5);
        grid.add(barra,2,9);
        
        //se añade la escena final a la ventana y se muestra por pantalla
        Scene scene = new Scene(grid);
        scene.getStylesheets().add(prop.getProp().getProperty("tema_actual"));
        ventana.setScene(scene);
        ventana.show();
    }
    
    private void verificarIdentificador(TextField id,
            CheckBox chkbox, ImageView check){
        //se comprueba que el rut este registrado
        id.textProperty().addListener((observable, o, n)->{
            //entrada invalida
            if(n.length()<8){
                check.setVisible(false);
                chkbox.setVisible(false);
            }
            //se comprueba que sea un rut valido y registrado
            else if(Chileno.comprobarRut(n)){
                if(poblacion.getPoblacion().containsKey(n)){
                    chkbox.setVisible(true);
                    check.setVisible(true);
                }
                else{
                    chkbox.setVisible(false);
                    check.setVisible(false);
                }
            }
            //entrada invalida
            else if(!Chileno.comprobarRut(n) || Chileno.comprobarRut(o)){
                check.setVisible(false);
                chkbox.setVisible(false);
            }
        });
    }
    
    private void verificarIdentificadorPariente(TextField idPariente, TextField id,
            CheckBox chkbox, ImageView check, ImageView mark, List<String> promptText){
        idPariente.textProperty().addListener((observable, o, n)->{
            //si pariente es chileno
            if(!chkbox.isSelected()){
                //entrada invalida
                if(n.length()<8){
                    check.setVisible(false);
                    mark.setVisible(false);
                }
                //se comprueba que sea un rut valido y registrado
                else if(Chileno.comprobarRut(n)){
                    if(!n.equals(id.getText()) && poblacion.getPoblacion().containsKey(n)){
                        mark.setVisible(false);
                        check.setVisible(true);
                    }
                    else{
                        check.setVisible(false);
                        mark.setVisible(true);
                    }
                }
                //entrada invalida
                else if(!Chileno.comprobarRut(n) || Chileno.comprobarRut(o)){
                    check.setVisible(false);
                    mark.setVisible(false);
                }
            }
            //si pariente no es chileno, se acepta una cadena > 7
            else{
                //entrada invalida
                if(n.length()<8){
                    check.setVisible(false);
                    mark.setVisible(false);
                }
                //acepta todo
                else{
                    check.setVisible(true);
                }
            }
        });
        
        chkbox.selectedProperty().addListener((obs, o , n) -> {
            if(n || o)
                idPariente.clear();
            if(n)
                idPariente.setPromptText(promptText.get(1));
            else
                idPariente.setPromptText(promptText.get(0));
        });
    }
    
    private void ventanaBorrar(TextField rut, Ciudadano ciudadano, double largo, double ancho){
        Stage popup = new Stage();
        popup.setResizable(false);
        popup.initStyle(StageStyle.UTILITY);
        popup.setAlwaysOnTop(true);
        popup.initModality(Modality.APPLICATION_MODAL);
        GridPane pop = new GridPane();
        pop.setHgap(5);
        pop.setVgap(5);
        pop.setAlignment(Pos.CENTER);
        pop.setPrefHeight(100);
        pop.setPrefWidth(100);        
        Label txt = new Label("¿realmente desea eliminar rut\n\ty datos de usuario?");
        txt.setFont(Font.font("bold", FontWeight.LIGHT, 12));
        pop.add(txt,0,0);
        StackPane botones = new StackPane();
        Button ok = new Button("confirmar");
        ok.setTranslateX(-50);
        Button cancelar = new Button("cancelar");
        cancelar.setTranslateX(50);
        botones.getChildren().addAll(ok, cancelar);
        pop.add(botones, 0, 1);
        
        ok.setOnMouseClicked(lambda -> {
            if(!poblacion.getPoblacion().get(ciudadano.mostrarIdentificador()).desvincularDeParientes()){
                Elementos.popMensaje("error de operacion", 300, 100);
                rut.clear();
            }
            else{
                logReporte.appendText(
                        "["+horaActual+"] "+"rut "+rut.getText()+" eliminado\n"
                );
                rut.clear();
                Stage exito = Elementos.popMensajeStage("Operacion Exitosa!", largo, ancho);
                popup.setScene(exito.getScene());
                ((Button)exito.getUserData()).setOnMouseClicked(alpha -> popup.close());
            }
        });
        
        cancelar.addEventHandler(MouseEvent.MOUSE_CLICKED, alpha -> popup.close());
        
        cancelar.addEventHandler(KeyEvent.KEY_PRESSED, alpha -> {
            if(alpha.getCode() == KeyCode.ENTER ){
                popup.close();
            }
        });
        
        Scene scene = new Scene(pop, largo, ancho);
        scene.getStylesheets().add(prop.getProp().getProperty("tema_actual"));
        popup.setScene(scene);
        popup.show();
    }
    
    private void verParientes(Ciudadano ciudadano){
        Buscar_Ciudadano_Parientes ventana = new Buscar_Ciudadano_Parientes(ciudadano, prop);
        ventana.abrirVentana();
    }
}
