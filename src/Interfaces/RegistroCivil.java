
package Interfaces;

import colecciones.Ciudadano;


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
}
