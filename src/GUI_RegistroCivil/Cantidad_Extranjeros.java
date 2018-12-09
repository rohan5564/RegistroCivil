
package GUI_RegistroCivil;

import colecciones.Chileno;
import colecciones.Extranjero;
import colecciones.Poblacion;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import javafx.animation.FadeTransition;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import utilidades.ArchivoProperties;
import utilidades.Reporte;


public class Cantidad_Extranjeros {
    private final String horaActual = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    private TextArea logReporte = Reporte.getInstancia().getLog();
    private ArchivoProperties prop = ArchivoProperties.getInstancia();
    private Poblacion poblacion = Poblacion.getInstancia();
    private Chileno aux;

    public Cantidad_Extranjeros() {
    }
    
    public void cantidadExtranjeros(MouseEvent click){
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
        ArrayList<Visas> arr = new ArrayList<>();
        
        for(Extranjero c : poblacion.getExtranjeros().values()) {
            Extranjero ext = (Extranjero)c;
            Iterator<Visas> it = arr.iterator();
            int flag = 0;
            while(it.hasNext()){
                Visas visa = it.next();
                if(visa.getVisa().equals(ext.getTipoDeVisa().getNombre())){
                    visa.sumar();
                    flag = 1;
                }
            }
            if(flag == 0){
                Visas aux = new Visas();
                aux.setVisa(ext.getTipoDeVisa().getNombre());
                aux.setNum(1);
                arr.add(aux);
            }
        }
        arr.sort((Visas o1, Visas o2) -> {
            if(o1.getVisa().equals(o2.getVisa()))
                return 0;
            return o1.getVisa().compareTo(o2.getVisa())<0?-1:1;
        });
        
        Label legend = new Label("");
        legend.setVisible(false);
        legend.setFont(Font.font("Roboto", FontWeight.LIGHT, 20));
        StringBuilder datosLegend = new StringBuilder();
        
        for(Visas i: arr){
            list.add(new PieChart.Data(i.getVisa(), i.getNum()));
            datosLegend.append(i.getVisa()+": "+i.getNum()+"\n");
        }
        legend.setText(datosLegend.toString());
        
        list.forEach(data ->
            data.nameProperty().bind(
                Bindings.concat(data.getName(), ": ", (int)data.getPieValue(), "")
            )
        );
        PieChart pieChart = new PieChart(list);
        pieChart.setTitle("Extranjeros registrados por tipo de visa\n");
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

class Visas{
    private int num;
    private String visa;

    public Visas() {
        num = 0;
        visa = null;
    }

    public Visas(int num, String visa) {
        this.num = num;
        this.visa = visa;
    }
    
    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getVisa() {
        return visa;
    }

    public void setVisa(String visa) {
        this.visa =visa;
    }
    
    public void sumar(){
        num++;
    }
}
