/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 *
 * @author Jean
 */
public class ArchivoProperties {
    final private String actual = new String("");
    final private String claro = new String("Resources/tema.css");
    final private String oscuro = new String("Resources/tema_oscuro.css");
    final private String archivo = new String("config.properties");
    final private Rectangle2D resolucion = Screen.getPrimary().getVisualBounds();//resolucion pantalla
    private Properties prop;
    
    public ArchivoProperties(){
    }

    public Properties getProp() {
        return prop;
    }
    
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
    
    public void modificarTema(Tema o){
        OutputStream out = null;
        try {
            File f = new File(archivo);
            if(f.exists()){
                prop.load(new FileReader(f));
                prop.setProperty("tema_actual", o==Tema.ACTUAL?actual:o==Tema.CLARO?claro:oscuro);
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
