/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades;

import GUI_RegistroCivil.Buscar_Ciudadano;
import GUI_RegistroCivil.Cantidad_Nacidos;
import GUI_RegistroCivil.Elementos;
import GUI_RegistroCivil.Registrar_Defuncion;
import GUI_RegistroCivil.Registrar_Matrimonio;
import GUI_RegistroCivil.Registrar_Nacimiento;
import colecciones.Poblacion;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Jean
 */
public class MenuOpciones {
    private final String horaActual = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    private TextArea logReporte;
    private ArchivoProperties prop;
    private Poblacion poblacion;
    
    public MenuOpciones(TextArea logReporte, Poblacion poblacion, ArchivoProperties prop) {
        this.logReporte = logReporte;
        this.poblacion = poblacion;
        this.prop = prop;
    }
    
    public void datosPersona(MouseEvent click){
        Buscar_Ciudadano accesoDirecto = new Buscar_Ciudadano(logReporte, poblacion, prop);
        accesoDirecto.buscarNacimiento(click);
    }
    
    public void registrarNacimiento(MouseEvent click){
        Registrar_Nacimiento accesoDirecto = new Registrar_Nacimiento(logReporte, poblacion, prop);
        accesoDirecto.registrarNacimiento(click);
    }
    
    public void cantidadNacidos(MouseEvent click){
        if(poblacion.getPoblacion().isEmpty()){
            Elementos.popMensaje("Sin poblacion agregada", 350, 100);
            return;
        }
        Cantidad_Nacidos accesoDirecto = new Cantidad_Nacidos(logReporte, poblacion, prop);
        accesoDirecto.cantidadNacidos(click);
    }
    
    public void registrarDefuncion(MouseEvent click){
        Registrar_Defuncion accesoDirecto = new Registrar_Defuncion(logReporte, poblacion, prop);
        accesoDirecto.registrarDefuncion(click);
    }
    
    public void registrarMatrimonio(MouseEvent click){
        Registrar_Matrimonio accesoDirecto = new Registrar_Matrimonio(logReporte, poblacion, prop);
        accesoDirecto.registrarMatrimonio(click);
    }
    
    
    
    public void buscarDefuncion(MouseEvent click){
        /*Buscar_Defuncion accesoDirecto = new Buscar_Defuncion(logReporte, poblacion, prop);
        accesoDirecto.registrarMatrimonio(click);*/
        Buscar_Ciudadano accesoDirecto = new Buscar_Ciudadano(logReporte, poblacion, prop);
        accesoDirecto.buscarNacimiento(click);
    }
    
    public void buscarMatrimonio(MouseEvent click){
        /*Buscar_Matrimonio accesoDirecto = new Buscar_Matrimonio(logReporte, poblacion, prop);
        accesoDirecto.registrarMatrimonio(click);*/
        Buscar_Ciudadano accesoDirecto = new Buscar_Ciudadano(logReporte, poblacion, prop);
        accesoDirecto.buscarNacimiento(click);
    }
}
