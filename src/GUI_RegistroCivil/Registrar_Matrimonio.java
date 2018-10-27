/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI_RegistroCivil;

import Enums.EstadoCivil;
import colecciones.Chileno;
import colecciones.Ciudadano;
import colecciones.Poblacion;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
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

/**
 *
 * @author Jean
 */
public class Registrar_Matrimonio {
    private final String horaActual = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    private TextArea logReporte;
    private ArchivoProperties prop;
    private Poblacion poblacion;
    private Chileno aux1;
    private Chileno aux2;

    public Registrar_Matrimonio(TextArea logReporte, Poblacion poblacion, ArchivoProperties prop) {
        this.logReporte = logReporte;
        this.poblacion = poblacion;
        this.prop = prop;
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
        
        rutEsposo.textProperty().addListener((observable, o, n)->{
            if(n.length()<8){
                checkEsposo.setVisible(false);
                markEsposo.setVisible(false);
            }
            else if(Chileno.comprobarRut(n) && poblacion.getPoblacion().containsKey(n)
                    && poblacion.getPoblacion().get(n).getDefuncion()==null){
                if(poblacion.getPoblacion().get(n).getParientes().buscarPariente(EstadoCivil.CASADO)==null){
                    markEsposo.setVisible(false);
                    checkEsposo.setVisible(true);
                }
                else{
                    checkEsposo.setVisible(false);
                    markEsposo.setVisible(true);
                }
            }            
            else if(!Chileno.comprobarRut(n) || Chileno.comprobarRut(o)){
                checkEsposo.setVisible(false);
                markEsposo.setVisible(false);
            }
        });
        markEsposo.visibleProperty().addListener((obs, o, n)->{
            if(n.booleanValue()){
                aux1 = (Chileno)poblacion.getPoblacion().get(rutEsposo.getText());
                if(aux1 == null)
                    lblEsposo.setText("Esposo");
                else{
                    lblEsposo.setText("Esposo: "+ aux1.getNombre() +"");
                    if(!aux1.registrarMatrimonio()){
                        rutEsposa.setDisable(true);
                        estadoEsposo.setText("Estado: Casado");
                    }
                    else{
                        rutEsposa.setDisable(false);
                        estadoEsposo.setText("Estado: Disponible");
                    }
                }
            }
            else{
                rutEsposa.setDisable(true);
                lblEsposo.setText("Esposo");
                estadoEsposo.setText("");
                aux1 = null;
            }
        });
        checkEsposo.visibleProperty().addListener((obs, o, n)->{
            if(n.booleanValue()){
                aux1 = (Chileno)poblacion.getPoblacion().get(rutEsposo.getText());
                if(aux1 == null)
                    lblEsposo.setText("Esposo");
                else{
                    lblEsposo.setText("Esposo: "+ aux1.getNombre() +"");
                    if(!aux1.registrarMatrimonio()){
                        rutEsposa.setDisable(true);
                        estadoEsposo.setText("Estado: Casado");
                    }
                    else{
                        rutEsposa.setDisable(false);
                        estadoEsposo.setText("Estado: Disponible");
                    }
                }
            }
            else{
                rutEsposa.setDisable(true);
                lblEsposo.setText("Esposo");
                estadoEsposo.setText("");
                aux1 = null;
            }
        });
        
        rutEsposa.textProperty().addListener((observable, o, n)->{
            if(n.length()<8){
                checkEsposa.setVisible(false);
                markEsposa.setVisible(false);
            }
            else if(Chileno.comprobarRut(n) && poblacion.getPoblacion().containsKey(n)
                    && poblacion.getPoblacion().get(n).getDefuncion()==null){
                if(poblacion.getPoblacion().get(n).getParientes().buscarPariente(EstadoCivil.CASADO)==null){
                    markEsposa.setVisible(false);
                    checkEsposa.setVisible(true);
                }
                else{
                    checkEsposa.setVisible(false);
                    markEsposa.setVisible(true);
                }
            }            
            else if(!Chileno.comprobarRut(n) || Chileno.comprobarRut(o)){
                checkEsposa.setVisible(false);
                markEsposa.setVisible(false);
            }
        });
        markEsposa.visibleProperty().addListener((obs, o, n)->{
            if(n.booleanValue()){
                aux2 = (Chileno)poblacion.getPoblacion().get(rutEsposa.getText());
                if(aux2 == null)
                    lblEsposa.setText("Esposa");
                else{
                    lblEsposa.setText("Esposa: "+ aux2.getNombre() +"");
                    if(!aux2.registrarMatrimonio()){
                        guardar.setDisable(true);
                        estadoEsposa.setText("Estado: Casada");
                    }
                    else{
                        guardar.setDisable(false);
                        estadoEsposa.setText("Estado: Disponible");
                    }
                }
            }
            else{
                guardar.setDisable(false);
                lblEsposa.setText("Esposa");
                estadoEsposa.setText("");
                aux2 = null;
            }
        });
        checkEsposa.visibleProperty().addListener((obs, o, n)->{
            if(n.booleanValue()){
                aux2 = (Chileno)poblacion.getPoblacion().get(rutEsposa.getText());
                if(aux2 == null)
                    lblEsposa.setText("Esposa");
                else{
                    lblEsposa.setText("Esposa: "+ aux2.getNombre() +"");
                    if(!aux2.registrarMatrimonio()){
                        guardar.setDisable(true);
                        estadoEsposa.setText("Estado: Casada");
                    }
                    else{
                        guardar.setDisable(false);
                        estadoEsposa.setText("Estado: Disponible");
                    }
                }
            }
            else{
                guardar.setDisable(false);
                lblEsposa.setText("Esposa");
                estadoEsposa.setText("");
                aux2 = null;
            }
        });
        
        
        cancelar.setOnMouseClicked(lambda -> ventana.close());
        guardar.setOnMouseClicked(lambda -> {
            logReporte.appendText(
                    "["+horaActual+"] "+aux1.getNombre().toLowerCase()+" "+aux1.getApellido().toLowerCase()+" y "+
                    aux2.getNombre().toLowerCase()+" "+aux2.getApellido().toLowerCase()+"se unieron en matrimonio\n");            
            aux1.getParientes().agregarPariente(aux2, EstadoCivil.CASADO);
            aux1.getEstadoCivil().add(EstadoCivil.CASADO);
            aux2.getParientes().agregarPariente(aux1, EstadoCivil.CASADO);
            aux2.getEstadoCivil().add(EstadoCivil.CASADO);
            rutEsposo.clear();
            rutEsposa.clear();
            Elementos.popMensaje("Operacion exitosa!", 300, 100);
            guardar.setDisable(true);
            
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
}
