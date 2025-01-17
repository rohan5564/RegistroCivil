
package GUI_RegistroCivil;

import colecciones.Chileno;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utilidades.ArchivoProperties;
import colecciones.Poblacion;
import java.util.ArrayList;
import java.util.Iterator;
import javafx.animation.FadeTransition;
import javafx.beans.binding.Bindings;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import utilidades.ConexionBD;
import utilidades.Reporte;


public class Cantidad_Nacidos {
    private final String horaActual = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    private TextArea logReporte = Reporte.getInstancia().getLog();
    private ArchivoProperties prop = ArchivoProperties.getInstancia();
    private Poblacion poblacion = Poblacion.getInstancia();
    private Chileno aux;

    public Cantidad_Nacidos() {
    }
    
    public void cantidadNacidos(MouseEvent click){
        Stage ventana = new Stage();
        ventana.setX(150);
        ventana.setY(60);
        ventana.setResizable(false);
        ventana.initStyle(StageStyle.UTILITY);
        ventana.initModality(Modality.APPLICATION_MODAL);
        ventana.setMinHeight(620);
        ventana.setMinWidth(850);
        ventana.setMaxHeight(620);
        ventana.setMaxWidth(1050);
        GridPane grid = new GridPane();
        Scene scene = new Scene(grid);
        scene.getStylesheets().add(prop.getProp().getProperty("tema_actual"));
        ventana.setScene(scene);
        grid.setAlignment(Pos.CENTER_LEFT);
        grid.setHgap(50);
        grid.setVgap(20);
        grid.setPadding(new Insets(50,50,50,50));
        
        ObservableList<PieChart.Data> list = FXCollections.observableList(new ArrayList<PieChart.Data>());
        ArrayList<Regiones> arr = new ArrayList<>();
        
        ConexionBD.getInstancia().totalPorRegion().forEach((k,v)->{
            arr.add(new Regiones(k,v.intValue()));
        });
            
        arr.sort((Regiones o1, Regiones o2) -> {
            if(o1.getReg().equals(o2.getReg()))
                return 0;
            return o1.getReg().compareTo(o2.getReg())<0?-1:1;
        });
        
        Label legend = new Label("");
        legend.setVisible(false);
        legend.setFont(Font.font("Roboto", FontWeight.LIGHT, 20));
        StringBuilder datosLegend = new StringBuilder();
        
        for(Regiones i: arr){
            list.add(new PieChart.Data(i.getReg(), i.getNum()));
            datosLegend.append(i.getReg()+": "+i.getNum()+"\n");
        }
        legend.setText(datosLegend.toString());
        
        list.forEach(data ->
            data.nameProperty().bind(
                Bindings.concat(data.getName(), ": ", (int)data.getPieValue(), "")
            )
        );
        PieChart pieChart = new PieChart(list);
        pieChart.setTitle("Nacimientos registrados por region\n(total: "+poblacion.totalPoblacionBD()+" )");
        pieChart.setLabelsVisible(false);
        pieChart.setLegendVisible(false);
        pieChart.setClockwise(true);
        pieChart.setPrefHeight(900);
        
        Label valor = new Label("");
        valor.setMouseTransparent(true);
        valor.setTextFill(Color.WHITESMOKE);
        valor.setBackground(new Background(new BackgroundFill(Color.DARKKHAKI, new CornerRadii(1), new Insets(1))));
        
        for (PieChart.Data data : pieChart.getData()) {
            data.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent mouse) -> {
                valor.setTranslateX(mouse.getSceneX()-300);
                valor.setTranslateY(mouse.getSceneY()-300);
                data.getNode().setOnMouseEntered(lambda->{
                        data.getNode().setScaleX(1.15);
                        data.getNode().setScaleY(1.15);
                }); 
                data.getNode().setOnMouseExited(lambda->{
                        data.getNode().setScaleX(1);
                        data.getNode().setScaleY(1);
                }); 
                String tip = data.getName().substring(0, data.getName().indexOf(":"));
                valor.setText(tip);
            });
        }        
        StackPane grafico = new StackPane();
        grafico.getChildren().addAll(pieChart, valor);
        
        grid.add(grafico, 0, 0);
        grid.add(legend, 1, 0);
        
        FadeTransition fade = new FadeTransition(Duration.seconds(1));
        fade.setNode(grafico);
        fade.setOnFinished(lambda -> legend.setVisible(true));
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();

        ventana.show();
    }
}

class Regiones{
    private int num;
    private String reg;

    public Regiones() {
        num = 0;
        reg = null;
    }

    public Regiones(String reg,int num) {
        this.num = num;
        this.reg = reg;
    }
    
    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getReg() {
        return reg;
    }

    public void setReg(String reg) {
        this.reg = reg;
    }
    
    public void sumar(){
        num++;
    }
}