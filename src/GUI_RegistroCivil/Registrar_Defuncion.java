
package GUI_RegistroCivil;

import Enums.EstadoCivil;
import Excepciones.CantidadParentescoException;
import Excepciones.FormatoPasaporteException;
import Excepciones.FormatoRutException;
import Excepciones.LongitudRutException;
import colecciones.Chileno;
import colecciones.Ciudadano;
import colecciones.Extranjero;
import colecciones.Poblacion;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javafx.beans.binding.BooleanBinding;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.control.PopOver;
import utilidades.ArchivoProperties;
import utilidades.ConexionBD;
import utilidades.Reporte;


public class Registrar_Defuncion {
    private final String horaActual = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    private TextArea logReporte = Reporte.getInstancia().getLog();
    private ArchivoProperties prop = ArchivoProperties.getInstancia();
    private PopOver tooltip;
    private Poblacion poblacion = Poblacion.getInstancia();
    private Ciudadano aux;
    
    public Registrar_Defuncion() {
    }
    
    public void registrarDefuncion(MouseEvent click){
        
        Stage ventana = new Stage();
        ventana.setX(370);
        ventana.setY(80);
        ventana.setResizable(false);
        ventana.initStyle(StageStyle.UTILITY);
        ventana.initModality(Modality.APPLICATION_MODAL);
        ventana.setWidth(600);
        ventana.setHeight(650);
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(20);
        grid.setVgap(20);
        grid.setPadding(new Insets(50,50,50,50));

        Button guardar = new Button("guardar");
        guardar.setDisable(true);
        Button cancelar = new Button("cancelar");
        cancelar.setOnMouseClicked(lambda->ventana.close());
        
        StackPane checkRut = Elementos.checkRut();
        TextField rut = (TextField)checkRut.getChildrenUnmodifiable().get(0);
        ImageView check = (ImageView)checkRut.getChildrenUnmodifiable().get(1);
        ImageView mark = (ImageView)checkRut.getChildrenUnmodifiable().get(2);
        ImageView error = (ImageView)checkRut.getChildrenUnmodifiable().get(3);
        GridPane registro = registroDatos(guardar, rut);
        rut.textProperty().addListener((observable, o, n)->{
            checkearExistente(check, mark, error, o, n);
        });
        
        mark.visibleProperty().addListener((obserbable, o, n)->tooltip.show(mark));
        
        Label persona = new Label("");
        persona.setFont(Font.font("bold", FontWeight.NORMAL, 22));
        
        check.visibleProperty().addListener((obs, o, n)->{
            if(n.booleanValue()){
                aux = poblacion.getCiudadano(rut.getText());
                if(aux == null)
                    aux = ConexionBD.getInstancia().buscarCiudadano(rut.getText());
                persona.setText(aux.getNombre()+" "+aux.getApellido());
                registro.setVisible(true);
            }
            else{
                registro.setVisible(false);
                aux = null;
                persona.setText("");
            }
        });
        
        HBox barra = new HBox(20, guardar, cancelar);
        barra.setAlignment(Pos.CENTER);
        
        grid.add(checkRut,0,0);
        grid.add(persona, 0, 1);
        grid.add(registro, 0, 2);
        grid.add(barra,0,3);
        
        Scene scene = new Scene(grid);
        scene.getStylesheets().add(prop.getProp().getProperty("tema_actual"));
        ventana.setScene(scene);
        ventana.show();
    }
    
    public GridPane registroDatos(Button guardar, TextField rut){
        GridPane datos = new GridPane();
        datos.setHgap(10);
        datos.setVgap(10);
        datos.setPadding(new Insets(5,5,5,5));
        datos.setAlignment(Pos.TOP_CENTER);
        StackPane fecha = new StackPane();
        DatePicker dia = new DatePicker();
        dia.setMaxSize(200, 40);
        dia.setPromptText("fecha de muerte");
        dia.setTranslateX(-100);
        Spinner<LocalTime> hora = Elementos.hora("hora de muerte");
        hora.setTranslateX(100);
        hora.setTranslateY(5);
        fecha.getChildren().addAll(dia, hora);
        
        TextArea comentario = new TextArea();
        comentario.setWrapText(true);
        comentario.setPromptText("comentarios");
        comentario.setMinSize(200, 300);
        datos.add(fecha, 0, 0);
        datos.add(comentario, 0, 1);
        datos.setVisible(false);
        
        BooleanBinding validacion = 
                dia.valueProperty().isNull().or(
                hora.valueProperty().isNull()
                );
        
        guardar.disableProperty().bind(validacion);
        guardar.setOnMouseClicked(lambda -> {
            aux.setDefuncion(dia.getValue());
            aux.setHoraDefuncion(hora.getValue().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
            aux.setComentarioDefuncion(comentario.getText());
            try{
                if(aux.getParientes().buscarListaParentesco(EstadoCivil.CASADO)!=null){
                    Ciudadano conyuge = poblacion.getCiudadano(aux.getParientes().ObtenerCiudadanoPorEstado(EstadoCivil.CASADO, 0));
                    conyuge.agregarEstadoCivil(EstadoCivil.VIUDO);
                    conyuge.getParientes().agregarPariente(aux.mostrarIdentificador(), EstadoCivil.VIUDO);
                }
                logReporte.appendText(
                        "["+horaActual+"]"+aux.getNombre().toLowerCase()+" "+aux.getApellido().toLowerCase()+
                        ", id: "+aux.mostrarIdentificador()+" QDEP\n");
                Elementos.popMensaje("Operacion Exitosa!", 300, 100);
            }catch(CantidadParentescoException e){
                Elementos.notificar("Error", CantidadParentescoException.getMensaje()).showError();
            }finally{
                rut.clear();
                datos.setVisible(false);
            }
        });       
        return datos;
    }
    
    private void checkearExistente(ImageView check, ImageView mark, ImageView error, String o, String n){
        try{
            if(Chileno.comprobarRut(n) || Extranjero.comprobarPasaporte(n)){
                Ciudadano ciudadano = poblacion.getCiudadano(n);
                if(ciudadano!=null){
                    if(ciudadano.getDefuncion()==null){
                        check.setVisible(true);
                        mark.setVisible(false);
                        error.setVisible(false);
                    }
                    else{
                        check.setVisible(false);
                        mark.setVisible(true);
                        error.setVisible(false);
                        tooltip = Elementos.popTip("ciudadano ya fallecido");
                    }
                }
                else{
                    mark.setVisible(false);
                    check.setVisible(false);
                    error.setVisible(true);
                    tooltip = Elementos.popTip("identificador no registrado");
                }
            }
            else{
                check.setVisible(false);
                mark.setVisible(false);
                error.setVisible(false);
            }
        }catch(FormatoRutException | FormatoPasaporteException | LongitudRutException e){
            check.setVisible(false);
            mark.setVisible(false);
            error.setVisible(false);
        }
    }
}