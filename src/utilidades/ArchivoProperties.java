
package utilidades;

import Enums.Tema;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;


public class ArchivoProperties {
    final private String tabla = "base_de_datos";
    final private String ip = "localhost";
    final private String port = "3306";
    final private String user = "root";
    final private String pass = "";
    final private String actual = "";
    final private String claro = "Resources/tema.css";
    final private String oscuro = "Resources/tema_oscuro.css";
    final private String archivo = "config.properties";
    final private Rectangle2D resolucion = Screen.getPrimary().getVisualBounds();//resolucion pantalla
    private Properties prop;
    private static ArchivoProperties archivoProp;
    
    private ArchivoProperties(){
    }

    public String getIp() {
        return ip;
    }

    public String getUser() {
        return user;
    }

    public String getPass() {
        return pass;
    }

    
    
    public static ArchivoProperties getInstancia(){
        if(archivoProp == null)
            archivoProp = new ArchivoProperties();
        return archivoProp;
    }
    
    public Properties getProp() {
        return prop;
    }
    
    /**
     * permite crear y/o cargar el archivo .properties que contiene algunos datos de configuraciones
     * que cargara el programa
     */
    public void crear(){
        OutputStream out = null;
        try {
            prop = new Properties();
            File f = new File(archivo);
            if(f.exists()){
                //System.out.println(f.getAbsolutePath());
                prop.load(new FileInputStream(f));
            }
            else{
                prop.setProperty("tabla", tabla);
                prop.setProperty("ip", ip);
                prop.setProperty("port", port);
                prop.setProperty("user", user);
                prop.setProperty("pass", pass);
                prop.setProperty("tema_actual", actual);
                prop.setProperty("tema_claro", claro);
                prop.setProperty("tema_oscuro", oscuro);  
                if((int)resolucion.getHeight()!=768 && (int)resolucion.getWidth()!=1366){
                    prop.setProperty("ancho_pantalla", String.valueOf(1280));
                    prop.setProperty("largo_pantalla", String.valueOf(720));
                    prop.setProperty("tamanho_por_defecto", "falso");
                }
                else {
                    prop.setProperty("ancho_pantalla", String.valueOf(resolucion.getWidth()));  
                    prop.setProperty("largo_pantalla", String.valueOf(resolucion.getHeight()));  
                    prop.setProperty("tamanho_por_defecto", "verdad");  
                }
                f.createNewFile();
                out = new FileOutputStream(f);
                prop.store(out, null);           
            }
        }catch(Exception e) {
            e.printStackTrace();
        }finally {
            if(out != null){
                try {
                    out.close();
                }catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    /**
     * permite modificar el tema del programa por el que se ingrese como parametro
     * @param tema tema que se modificara en el archivo .properties
     */
    public void modificarTema(Tema tema){
        OutputStream out = null;
        try {
            File f = new File(archivo);
            if(f.exists()){
                prop.load(new FileReader(f));
                prop.setProperty("tema_actual", tema==Tema.ACTUAL?actual:tema==Tema.CLARO?claro:oscuro);
            }
            out = new FileOutputStream(f);
            prop.store(out, null);
        }catch(Exception e) {
            e.printStackTrace();
        }finally {
            if(out != null){
                try {
                    out.close();
                }catch(IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
