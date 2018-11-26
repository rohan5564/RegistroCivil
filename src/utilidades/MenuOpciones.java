
package utilidades;

import GUI_RegistroCivil.Buscar_Ciudadano;
import GUI_RegistroCivil.Cantidad_Extranjeros;
import GUI_RegistroCivil.Cantidad_Nacidos;
import GUI_RegistroCivil.Elementos;
import GUI_RegistroCivil.Registrar_Defuncion;
import GUI_RegistroCivil.Registrar_Extranjero;
import GUI_RegistroCivil.Registrar_Matrimonio;
import GUI_RegistroCivil.Registrar_Nacimiento;
import colecciones.Poblacion;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;


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
    /**
     * Busca a un ciudanano
     * @param click 
     */
    public void buscarCiudadano(MouseEvent click){
        Buscar_Ciudadano accesoDirecto = new Buscar_Ciudadano(logReporte, poblacion, prop);
        accesoDirecto.buscarCiudadano(click);
    }
    /**
     * Registra un nacimiento
     * @param click 
     */
    public void registrarNacimiento(MouseEvent click){
        Registrar_Nacimiento accesoDirecto = new Registrar_Nacimiento(logReporte, poblacion, prop);
        accesoDirecto.registrarNacimiento(click);
    }
    /**
     * Mira la cantidad de nacidos
     * @param click 
     */
    public void cantidadNacidos(MouseEvent click){
        if(poblacion.getPoblacion() == null || poblacion.getPoblacion().isEmpty()){
            Elementos.popMensaje("Sin poblacion agregada", 350, 100);
            return;
        }
        Cantidad_Nacidos accesoDirecto = new Cantidad_Nacidos(logReporte, poblacion, prop);
        accesoDirecto.cantidadNacidos(click);
    }
    /**
     * Mira la cantidad de extranjeros
     * @param click 
     */
    public void cantidadExtranjeros(MouseEvent click){
        if(poblacion.getExtranjeros() == null || poblacion.getExtranjeros().isEmpty()){
            Elementos.popMensaje("Sin poblacion agregada", 350, 100);
            return;
        }
        Cantidad_Extranjeros accesoDirecto = new Cantidad_Extranjeros(logReporte, poblacion, prop);
        accesoDirecto.cantidadExtranjeros(click);
    }
    /**
     * Registra a una persona fallecida
     * @param click 
     */
    public void registrarDefuncion(MouseEvent click){
        Registrar_Defuncion accesoDirecto = new Registrar_Defuncion(logReporte, poblacion, prop);
        accesoDirecto.registrarDefuncion(click);
    }
    /**
     * Registra a un matrimonio
     * @param click 
     */
    public void registrarMatrimonio(MouseEvent click){
        Registrar_Matrimonio accesoDirecto = new Registrar_Matrimonio(logReporte, poblacion, prop);
        accesoDirecto.registrarMatrimonio(click);
    }
    /**
     * Registra a un extranjero
     * @param click 
     */
    public void registrarExtranjero(MouseEvent click){
        Registrar_Extranjero accesoDirecto = new Registrar_Extranjero(logReporte, poblacion, prop);
        accesoDirecto.registrarExtranjero(click);
    }
    /**
     * Revisa si una persona a fallecido
     * @param click 
     */
    public void buscarDefuncion(MouseEvent click){
        /*Buscar_Defuncion accesoDirecto = new Buscar_Defuncion(logReporte, poblacion, prop);
        accesoDirecto.registrarMatrimonio(click);*/
        Buscar_Ciudadano accesoDirecto = new Buscar_Ciudadano(logReporte, poblacion, prop);
        accesoDirecto.buscarCiudadano(click);
    }
    /**
     * Busca a un matrimonio
     * @param click 
     */
    public void buscarMatrimonio(MouseEvent click){
        /*Buscar_Matrimonio accesoDirecto = new Buscar_Matrimonio(logReporte, poblacion, prop);
        accesoDirecto.registrarMatrimonio(click);*/
        Buscar_Ciudadano accesoDirecto = new Buscar_Ciudadano(logReporte, poblacion, prop);
        accesoDirecto.buscarCiudadano(click);
    }
}
