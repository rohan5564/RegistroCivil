
package GUI_RegistroCivil;

import Enums.EstadoCivil;
import Excepciones.CantidadParentescoException;
import colecciones.Ciudadano;
import colecciones.Poblacion;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
import utilidades.Reporte;


public class Registrar_Matrimonio {
    private final String horaActual = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    private TextArea logReporte = Reporte.getInstancia().getLog();
    private ArchivoProperties prop = ArchivoProperties.getInstancia();
    private Poblacion poblacion = Poblacion.getInstancia();
    private PopOver tooltip;
    private Ciudadano aux1;
    private Ciudadano aux2;

    public Registrar_Matrimonio() {
    }
    
    public void registrarMatrimonio(MouseEvent click){
        Stage ventana = new Stage();
        ventana.setX(370);
        ventana.setY(80);
        ventana.setResizable(false);
        ventana.initStyle(StageStyle.UTILITY);
        ventana.initModality(Modality.APPLICATION_MODAL);
        ventana.setWidth(600);
        ventana.setHeight(450);
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setHgap(30);
        grid.setVgap(20);
        grid.setPadding(new Insets(50,50,50,50));
        
        Button guardar = new Button("guardar");
        guardar.setDisable(true);
        Button cancelar = new Button("cancelar");
        HBox barra = new HBox(20, guardar, cancelar);
        
        Label lblPersona1 = new Label("Esposo");
        lblPersona1.setFont(Font.font("times new roman", FontWeight.LIGHT, 20));
        Label estadoPersona1 = new Label("");
        estadoPersona1.setFont(Font.font("times new roman", FontWeight.LIGHT, 24));
        StackPane persona1 = Elementos.checkRut();
        TextField rutPersona1 = (TextField)persona1.getChildrenUnmodifiable().get(0);
        rutPersona1.setMinWidth(220);
        ImageView checkPersona1 = (ImageView)persona1.getChildrenUnmodifiable().get(1);
        ImageView markPersona1 = (ImageView)persona1.getChildrenUnmodifiable().get(2);
        ImageView errorPersona1 = (ImageView)persona1.getChildrenUnmodifiable().get(3);
        
        Label lblPersona2 = new Label("Esposa");
        lblPersona2.setFont(Font.font("times new roman", FontWeight.LIGHT, 20));
        Label estadoPersona2 = new Label("");
        estadoPersona2.setFont(Font.font("times new roman", FontWeight.LIGHT, 24));
        StackPane persona2 = Elementos.checkRut();
        TextField rutPersona2 = (TextField)persona2.getChildrenUnmodifiable().get(0);
        rutPersona2.setMinWidth(220);
        rutPersona2.setDisable(true);
        ImageView checkPersona2 = (ImageView)persona2.getChildrenUnmodifiable().get(1);
        ImageView markPersona2 = (ImageView)persona2.getChildrenUnmodifiable().get(2);
        ImageView errorPersona2 = (ImageView)persona2.getChildrenUnmodifiable().get(3);
        
        rutPersona1.textProperty().addListener((observable, o, n)->{
            rutPersona2.clear();
            if(!rutPersona2.getText().equals(rutPersona1.getText()))
                checkearRut(checkPersona1, markPersona1, errorPersona1, o, n);
        });
        markPersona1.visibleProperty().addListener((obs, o, n)->{
            if(n.booleanValue()){
                estadoPersona1.setText("Estado: en matrimonio");
            }
            else{
                estadoPersona1.setText("");
            }
        });
        checkPersona1.visibleProperty().addListener((obs, o, n)->{
            if(n.booleanValue()){
                estadoPersona1.setText("Estado: Disponible");
                aux1 = poblacion.getCiudadano(rutPersona1.getText());
                rutPersona2.setDisable(false);
            }
            else{
                estadoPersona1.setText("");
                rutPersona2.setDisable(true);
            }
        });
        
        rutPersona2.textProperty().addListener((observable, o, n)->{
            if(!rutPersona2.getText().equals(rutPersona1.getText()))
                checkearRut(checkPersona2, markPersona2, errorPersona2, o, n);
        });
        markPersona2.visibleProperty().addListener((obs, o, n)->{
            if(n.booleanValue()){
                estadoPersona2.setText("Estado: en matrimonio");
            }
            else{
                estadoPersona2.setText("");
            }
        });
        checkPersona2.visibleProperty().addListener((obs, o, n)->{
            if(n.booleanValue()){
                estadoPersona2.setText("Estado: Disponible");
                aux2 = poblacion.getCiudadano(rutPersona2.getText());
                guardar.setDisable(false);
            }
            else{
                estadoPersona2.setText("");
                guardar.setDisable(true);
            }
        });
        
        
        cancelar.setOnMouseClicked(lambda -> ventana.close());
        guardar.setOnMouseClicked(lambda -> {
            try{
                aux1.getParientes().agregarPariente(aux2.mostrarIdentificador(), EstadoCivil.CASADO);
                aux1.getEstadoCivil().add(EstadoCivil.CASADO);
                aux2.getParientes().agregarPariente(aux1.mostrarIdentificador(), EstadoCivil.CASADO);
                aux2.getEstadoCivil().add(EstadoCivil.CASADO);
                logReporte.appendText(
                        "["+horaActual+"] "+aux1.getNombre().toLowerCase()+" "+aux1.getApellido().toLowerCase()+" y "+
                        aux2.getNombre().toLowerCase()+" "+aux2.getApellido().toLowerCase()+" se unieron en matrimonio\n");            
                
                Elementos.popMensaje("Operacion exitosa!", 300, 100);
            }catch(CantidadParentescoException e){
                Elementos.notificar("Error", CantidadParentescoException.getMensaje()).showError();
            }finally{
                rutPersona1.clear();
                rutPersona2.clear();
                checkPersona1.setVisible(false);
                checkPersona2.setVisible(false);
                markPersona1.setVisible(false);
                markPersona2.setVisible(false);
                errorPersona1.setVisible(false);
                errorPersona2.setVisible(false);
                estadoPersona1.setText("");
                estadoPersona2.setText("");
                guardar.setDisable(true);
            }
        });
        
        grid.add(lblPersona1, 0, 0);
        grid.add(persona1, 0, 1);
        grid.add(estadoPersona1, 0, 2);
        grid.add(lblPersona2, 1, 0);
        grid.add(persona2, 1, 1);
        grid.add(estadoPersona2, 1, 2);
        barra.setTranslateY(100);
        grid.add(barra, 1, 3);
        
        Scene scene = new Scene(grid);
        scene.getStylesheets().add(prop.getProp().getProperty("tema_actual"));
        ventana.setScene(scene);
        ventana.show();
    }
    
    private void checkearRut(ImageView check, ImageView mark, ImageView error, String o, String n){
        Ciudadano conyuge = poblacion.getCiudadano(n);
        if(conyuge!=null && conyuge.getDefuncion()==null){
            if(conyuge.getParientes().estadoEstaVacio(EstadoCivil.CASADO)){
                check.setVisible(true);
                mark.setVisible(false);
                error.setVisible(false);               
            }
            else{
                check.setVisible(false);
                mark.setVisible(true);
                error.setVisible(false);
            }
        }            
        else{
            check.setVisible(false);
            mark.setVisible(false);
            error.setVisible(false);
        }
    }
    
}