
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
import javafx.scene.input.MouseEvent;


public class MenuOpciones {
    
    public MenuOpciones() {
    }
    /**
     * Busca a un ciudanano
     * @param click 
     */
    public void buscarCiudadano(MouseEvent click){
        Buscar_Ciudadano accesoDirecto = new Buscar_Ciudadano();
        accesoDirecto.buscarCiudadano(click);
    }
    /**
     * Registra un nacimiento
     * @param click 
     */
    public void registrarNacimiento(MouseEvent click){
        Registrar_Nacimiento accesoDirecto = new Registrar_Nacimiento();
        accesoDirecto.registrarNacimiento(click);
    }
    /**
     * Mira la cantidad de nacidos
     * @param click 
     */
    public void cantidadNacidos(MouseEvent click){
        Poblacion poblacion = Poblacion.getInstancia();
        if(poblacion.getPoblacion() == null || poblacion.getPoblacion().isEmpty()){
            Elementos.popMensaje("Sin poblacion agregada", 350, 100);
            return;
        }
        Cantidad_Nacidos accesoDirecto = new Cantidad_Nacidos();
        accesoDirecto.cantidadNacidos(click);
    }
    /**
     * Mira la cantidad de extranjeros
     * @param click 
     */
    public void cantidadExtranjeros(MouseEvent click){
        Poblacion poblacion = Poblacion.getInstancia();
        if(poblacion.getExtranjeros() == null || poblacion.getExtranjeros().isEmpty()){
            Elementos.popMensaje("Sin poblacion agregada", 350, 100);
            return;
        }
        Cantidad_Extranjeros accesoDirecto = new Cantidad_Extranjeros();
        accesoDirecto.cantidadExtranjeros(click);
    }
    /**
     * Registra a una persona fallecida
     * @param click 
     */
    public void registrarDefuncion(MouseEvent click){
        Registrar_Defuncion accesoDirecto = new Registrar_Defuncion();
        accesoDirecto.registrarDefuncion(click);
    }
    /**
     * Registra a un matrimonio
     * @param click 
     */
    public void registrarMatrimonio(MouseEvent click){
        Registrar_Matrimonio accesoDirecto = new Registrar_Matrimonio();
        accesoDirecto.registrarMatrimonio(click);
    }
    /**
     * Registra a un extranjero
     * @param click 
     */
    public void registrarExtranjero(MouseEvent click){
        Registrar_Extranjero accesoDirecto = new Registrar_Extranjero();
        accesoDirecto.registrarExtranjero(click);
    }
    /**
     * Revisa si una persona a fallecido
     * @param click 
     */
    public void buscarDefuncion(MouseEvent click){
        /*Buscar_Defuncion accesoDirecto = new Buscar_Defuncion(logReporte, poblacion, prop);
        accesoDirecto.registrarMatrimonio(click);*/
        Buscar_Ciudadano accesoDirecto = new Buscar_Ciudadano();
        accesoDirecto.buscarCiudadano(click);
    }
    /**
     * Busca a un matrimonio
     * @param click 
     */
    public void buscarMatrimonio(MouseEvent click){
        /*Buscar_Matrimonio accesoDirecto = new Buscar_Matrimonio(logReporte, poblacion, prop);
        accesoDirecto.registrarMatrimonio(click);*/
        Buscar_Ciudadano accesoDirecto = new Buscar_Ciudadano();
        accesoDirecto.buscarCiudadano(click);
    }
}
