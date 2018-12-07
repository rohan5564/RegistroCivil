
package GUI_RegistroCivil;

import Enums.EstadoCivil;
import Excepciones.CantidadParentescoException;
import Excepciones.FormatoRutException;
import Excepciones.LongitudRutException;
import colecciones.Chileno;
import colecciones.ListadoParientes;
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
import utilidades.ArchivoProperties;


public class Registrar_Matrimonio {
    private final String horaActual = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    private TextArea logReporte;
    private ArchivoProperties prop;
    private Poblacion poblacion = Poblacion.getInstancia();
    private Chileno aux1;
    private Chileno aux2;

    public Registrar_Matrimonio(TextArea logReporte, ArchivoProperties prop) {
        this.logReporte = logReporte;
        this.poblacion = poblacion;
    }
    
    public void registrarMatrimonio(MouseEvent click){
        Stage ventana = new Stage();
        ventana.setX(370);
        ventana.setY(80);
        ventana.setResizable(false);
        ventana.initStyle(StageStyle.UTILITY);
        ventana.initModality(Modality.APPLICATION_MODAL);
        ventana.setWidth(600);
        ventana.setHeight(650);
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setHgap(30);
        grid.setVgap(20);
        grid.setPadding(new Insets(50,50,50,50));
        
        Button guardar = new Button("guardar");
        guardar.setDisable(true);
        Button cancelar = new Button("cancelar");
        HBox barra = new HBox(20, guardar, cancelar);
        
        Label lblEsposo = new Label("Esposo");
        lblEsposo.setFont(Font.font("times new roman", FontWeight.LIGHT, 20));
        Label estadoEsposo = new Label("");
        estadoEsposo.setFont(Font.font("times new roman", FontWeight.LIGHT, 24));
        StackPane esposo = Elementos.checkRut();
        TextField rutEsposo = (TextField)esposo.getChildrenUnmodifiable().get(0);
        rutEsposo.setMinWidth(220);
        ImageView checkEsposo = (ImageView)esposo.getChildrenUnmodifiable().get(1);
        ImageView markEsposo = (ImageView)esposo.getChildrenUnmodifiable().get(2);
        ImageView errorEsposo = (ImageView)esposo.getChildrenUnmodifiable().get(3);
        
        Label lblEsposa = new Label("Esposa");
        lblEsposa.setFont(Font.font("times new roman", FontWeight.LIGHT, 20));
        Label estadoEsposa = new Label("");
        estadoEsposa.setFont(Font.font("times new roman", FontWeight.LIGHT, 24));
        StackPane esposa = Elementos.checkRut();
        TextField rutEsposa = (TextField)esposa.getChildrenUnmodifiable().get(0);
        rutEsposa.setMinWidth(220);
        rutEsposa.setDisable(true);
        ImageView checkEsposa = (ImageView)esposa.getChildrenUnmodifiable().get(1);
        ImageView markEsposa = (ImageView)esposo.getChildrenUnmodifiable().get(2);
        ImageView errorEsposa = (ImageView)esposo.getChildrenUnmodifiable().get(3);
        
        rutEsposo.textProperty().addListener((observable, o, n)->{
            checkearRut(checkEsposo, markEsposo, errorEsposo, o, n);
        });
        markEsposo.visibleProperty().addListener((obs, o, n)->{
            if(n.booleanValue()){
                estadoEsposo.setText("Estado: Casado");
            }
            else{
                estadoEsposo.setText("");
            }
        });
        checkEsposo.visibleProperty().addListener((obs, o, n)->{
            try{
                if(n.booleanValue()){
                    estadoEsposo.setText("Estado: Disponible");
                    aux1 = poblacion.getChileno(rutEsposo.getText());
                }
                else{
                    estadoEsposo.setText("");
                }
            }catch(FormatoRutException | LongitudRutException e){
                e.printStackTrace();
            }
        });
        
        rutEsposa.textProperty().addListener((observable, o, n)->{
            checkearRut(checkEsposa, markEsposa, errorEsposa, o, n);
        });
        markEsposa.visibleProperty().addListener((obs, o, n)->{
            if(n.booleanValue()){
                estadoEsposa.setText("Estado: Casada");
            }
            else{
                estadoEsposa.setText("");
            }
        });
        checkEsposa.visibleProperty().addListener((obs, o, n)->{
            try{
                if(n.booleanValue()){
                    estadoEsposa.setText("Estado: Disponible");
                    aux2 = poblacion.getChileno(rutEsposa.getText());
                }
                else{
                    estadoEsposa.setText("");
                }
            }catch(FormatoRutException | LongitudRutException e){
                e.printStackTrace();
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
                Elementos.notificar("Error", CantidadParentescoException.getMensaje());
            }finally{
                rutEsposo.clear();
                rutEsposa.clear();
                guardar.setDisable(true);
            }
        });
        
        grid.add(lblEsposo, 0, 0);
        grid.add(esposo, 0, 1);
        grid.add(estadoEsposo, 0, 2);
        grid.add(lblEsposa, 1, 0);
        grid.add(esposa, 1, 1);
        grid.add(estadoEsposa, 1, 2);
        barra.setTranslateY(100);
        grid.add(barra, 1, 3);
        
        Scene scene = new Scene(grid);
        scene.getStylesheets().add(prop.getProp().getProperty("tema_actual"));
        ventana.setScene(scene);
        ventana.show();
    }
    
    private void checkearRut(ImageView check, ImageView mark, ImageView error, String o, String n){
        try{
            if(Chileno.comprobarRut(n) && poblacion.getChileno(n)!=null && poblacion.getChileno(n).getDefuncion()==null){
                ListadoParientes listEsposa = poblacion.getChileno(n).getParientes().buscarListaParentesco(EstadoCivil.CASADO);
                if(!listEsposa.existe() || listEsposa.estaVacia()){
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
            /*else if(!Chileno.comprobarRut(n) || Chileno.comprobarRut(o)){
                check.setVisible(false);
                mark.setVisible(false);
                error.setVisible(false);
            }*/
        }catch(LongitudRutException e){
            check.setVisible(false);
            mark.setVisible(false);
            error.setVisible(true);
        }catch(FormatoRutException e){
            check.setVisible(false);
            mark.setVisible(false);
            error.setVisible(true);
        }
    }
    
}