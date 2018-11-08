/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI_RegistroCivil;

import Enums.EstadoCivil;
import Enums.Sexo;
import colecciones.Ciudadano;
import java.util.LinkedList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
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
        Label titulo = new Label("Parientes de "+ciudadano.getNombre()+" "+ciudadano.getApellido());
        titulo.setFont(Font.font("monospaced", FontWeight.EXTRA_LIGHT, 18));
        
        grid.add(titulo, 0, 0);
        grid.add(desplegarTabla(), 0, 1);
        
        Scene scene = new Scene(grid);
        scene.getStylesheets().add(prop.getProp().getProperty("tema_actual"));
        ventana.setScene(scene);
        ventana.show();
    }
    
    private TableView desplegarTabla(){
        
        class Listado{
            EstadoCivil estado;
            Ciudadano ciudadano;

            public Listado(EstadoCivil estado, Ciudadano ciudadano) {
                this.estado = estado;
                this.ciudadano = ciudadano;
            }
            
        }
        
        TableColumn<Listado, String> nombre = new TableColumn<>("nombre");
        nombre.setMinWidth(100);
        nombre.setCellValueFactory((TableColumn.CellDataFeatures<Listado, String> mapaPorPersona) -> {
            return new SimpleStringProperty(mapaPorPersona.getValue().ciudadano.getNombre()); 
        });
        
        TableColumn<Listado, String> apellido = new TableColumn("apellido");
        apellido.setMinWidth(100);
        apellido.setCellValueFactory((TableColumn.CellDataFeatures<Listado, String> mapaPorPersona) -> {
            return new SimpleStringProperty(mapaPorPersona.getValue().ciudadano.getApellido()); 
        });
        
        TableColumn<Listado, String> parentesco = new TableColumn<>("parentesco");
        parentesco.setMinWidth(100);
        parentesco.setCellValueFactory((TableColumn.CellDataFeatures<Listado, String> mapaPorPersona) -> {
            return new SimpleStringProperty(
                mapaPorPersona.getValue().ciudadano.getSexo().equals(Sexo.FEMENINO)?
                    mapaPorPersona.getValue().estado.getNombreFemenino(): mapaPorPersona.getValue().estado.getNombreMasculino()
            ); 
        });
        
        TableColumn<Listado, String> identificador = new TableColumn<>("Rut");
        identificador.setMinWidth(100);
        identificador.setCellValueFactory((TableColumn.CellDataFeatures<Listado, String> mapaPorPersona) -> {
            return new SimpleStringProperty(
                mapaPorPersona.getValue().ciudadano.mostrarIdentificador()
            ); 
        });
        
        List<Listado> mapaDeParientes = new LinkedList<>();
        //Map<EstadoCivil, Ciudadano> mapaDeParientes = new EnumMap<>(EstadoCivil.class);
        ciudadano.getParientes().getPersonas().forEach((llave, valor) -> {
            valor.getListadoParientes().forEach(persona -> mapaDeParientes.add(new Listado(llave, persona)));
        });
        
        ObservableList<Listado> listadoParientes = 
                FXCollections.observableArrayList(mapaDeParientes);
        TableView tabla = new TableView(listadoParientes);
        tabla.getColumns().setAll(identificador, apellido, nombre, parentesco);
        tabla.setEditable(false);
        return tabla;
    }
    
}
