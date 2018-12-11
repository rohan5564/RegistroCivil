
package Interfaces;

import colecciones.Chileno;
import colecciones.Ciudadano;
import colecciones.Extranjero;


public interface RegistroCivil {
    /**
     * se comprueba si un identificador es registrable  o si ya existe una 
     * persona con tal identificador
     * @param identificador identificador a buscar
     * @return true si puede registrarse, false si ya existe un registro con el mismo identificador
     */
    boolean esRegistrable(String identificador);
    
    /**
     * busca en el registro civil al ciudadano ingresado para ver si ya se encuentra
     * registrado (segun su identificador) o si es posible registrarlo
     * @param ciudadano ciudadano que se busca en el registro civil
     */
    void registrarCiudadano(Ciudadano ciudadano);
    
    /**
     * busca en el registro civil al ciudadano ingresado para ver si ya se encuentra
     * registrado (segun su identificador), si lo encuentra procede a eliminarlo
     * @param ciudadano ciudano a desvincular del registro civil
     * @return true si se pudo remover al ciudadano, false en caso contrario
     */
    boolean removerCiudadano(Ciudadano ciudadano);
    
    /**
     * busca en el registro civil al ciudadano y lo retorna
     * @param id identificador del cuidadano
     * @return ciudadano a buscar, null en caso contrario
     */
    Ciudadano getCiudadanoBD(String id);
    
    /**
     * busca en el registro civil al chileno y lo retorna
     * @param id rut del chileno
     * @return chileno a buscar, null en caso contrario
     */
    Chileno getChilenoBD(String id);
    
    /**
     * busca en el registro civil al extranjero y lo retorna
     * @param id pasaporte del extranjero
     * @return extranjero a buscar, null en caso contrario
     */
    Extranjero getExtranjeroBD(String id);
    
    /**
     * permite una correcta actualizacion de los datos almacenados en el equipo hacia
     * la base de datos
     */
    void actualizarBase();
}
