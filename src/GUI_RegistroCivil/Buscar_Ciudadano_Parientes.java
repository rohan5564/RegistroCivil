/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI_RegistroCivil;

import Enums.EstadoCivil;
import Enums.Sexo;
import colecciones.Ciudadano;
import colecciones.Parientes;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import static java.util.Collections.list;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Callback;
import sun.plugin2.jvm.RemoteJVMLauncher.CallBack;
import utilidades.ArchivoProperties;

/**
 *
 * @author Jean
 */
public class Buscar_Ciudadano_Parientes {
    private Ciudadano ciudadano;
    private ArchivoProperties prop;
    
    public Buscar_Ciudadano_Parientes(Ciudadano ciudadano, ArchivoProperties prop){
        this.ciudadano = ciudadano;
        this.prop = prop;
    }

    public Ciudadano getCiudadano() {
        return ciudadano;
    }

    public void setCiudadano(Ciudadano ciudadano) {
        this.ciudadano = ciudadano;
    }
    
    public void abrirVentana(){
        Stage ventana = new Stage();
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        Label titulo = new Label("Parientes");
        titulo.setFont(Font.font("monospaced", FontWeight.MEDIUM, 20));
        
        grid.add(titulo, 0, 0);
        grid.add(desplegarTabla(), 0, 1);
        
        Scene scene = new Scene(grid);
        scene.getStylesheets().add(prop.getProp().getProperty("tema_actual"));
        ventana.setScene(scene);
        ventana.show();
    }
    
    private TableView desplegarTabla(){
        
        ObservableList<Map> parientes = FXCollections.observableArrayList(ciudadano.getParientes().getPersonas());
        
        TableColumn<Map.Entry<EstadoCivil, Ciudadano>, String> nombre = new TableColumn<>("nombre");
        nombre.setMinWidth(100);
        nombre.setCellValueFactory((TableColumn.CellDataFeatures<Map.Entry<EstadoCivil, Ciudadano>, String> mapaPorPersona) -> {
            return new SimpleStringProperty(mapaPorPersona.getValue().getValue().getNombre()); 
        });
        
        TableColumn<Map.Entry<EstadoCivil, Ciudadano>, String> apellido = new TableColumn("apellido");
        apellido.setMinWidth(100);
        apellido.setCellValueFactory((TableColumn.CellDataFeatures<Map.Entry<EstadoCivil, Ciudadano>, String> mapaPorPersona) -> {
            return new SimpleStringProperty(mapaPorPersona.getValue().getValue().getApellido()); 
        });
        
        TableColumn<Map.Entry<EstadoCivil, Ciudadano>, String> parentesco = new TableColumn<>("parentesco");
        parentesco.setMinWidth(100);
        parentesco.setCellValueFactory((TableColumn.CellDataFeatures<Map.Entry<EstadoCivil, Ciudadano>, String> mapaPorPersona) -> {
            return new SimpleStringProperty(
                mapaPorPersona.getValue().getValue().getSexo().equals(Sexo.FEMENINO)?
                    mapaPorPersona.getValue().getKey().getNombreFemenino(): mapaPorPersona.getValue().getKey().getNombreMasculino()
            ); 
        });
        
        TableColumn<Map.Entry<EstadoCivil, Ciudadano>, String> identificador = new TableColumn<>("Rut");
        identificador.setMinWidth(100);
        identificador.setCellValueFactory((TableColumn.CellDataFeatures<Map.Entry<EstadoCivil, Ciudadano>, String> mapaPorPersona) -> {
            return new SimpleStringProperty(
                mapaPorPersona.getValue().getValue().mostrarIdentificador()
            ); 
        });
                
        posible bug(no guarda mas de 1 hijo por persona)
        Map<EstadoCivil, Ciudadano> mapaDeParientes = new EnumMap<>(EstadoCivil.class);
        ciudadano.getParientes().getPersonas().forEach((llave, valor) -> {
            valor.forEach(persona -> mapaDeParientes.put(llave, persona));
        });
        
        ObservableList<Map.Entry<EstadoCivil, Ciudadano>> listadoParientes = 
                FXCollections.observableArrayList(mapaDeParientes.entrySet());
        TableView tabla = new TableView(listadoParientes);
        tabla.getColumns().setAll(identificador, apellido, nombre, parentesco);
        tabla.setEditable(false);
        return tabla;
    }
    
}
