
package colecciones;

import java.util.HashMap;
import java.util.Map;
import utilidades.ConexionBD;


public class Poblacion {
    
    private Map<String, Ciudadano> poblacion;

    public Poblacion() {
        this.poblacion = new HashMap<>();
    }

    public Poblacion(HashMap<String, Ciudadano> poblacion) {
        this.poblacion = poblacion;
    }

    public Map<String, Ciudadano> getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(Map<String, Ciudadano> poblacion) {
        this.poblacion = poblacion;
    }
    
    /**
     * 
     * @param ciudadano
     * @return 
     */
    public boolean removerCiudadano(Ciudadano ciudadano){
        if(!poblacion.containsValue(ciudadano))
            return false;
        
        if(ciudadano instanceof Chileno)
            return ciudadano.getParientes().removerParientes() && removerChileno((Chileno)ciudadano);
        else 
            return ciudadano.getParientes().removerParientes() && removerExtranjero((Extranjero)ciudadano);
    }
    
    /**
     * 
     * @param chileno
     * @return 
     */
    private boolean removerChileno(Chileno chileno){
        return chileno.getParientes().estaVacia() && poblacion.remove(chileno.getRut())!=null;
    }
    
    /**
     * 
     * @param extranjero
     * @return 
     */
    private boolean removerExtranjero(Extranjero extranjero){
        return extranjero.getParientes().estaVacia() && poblacion.remove(extranjero.getPasaporte())!=null;
    }
    
    public static void cargarBD(ConexionBD conexion){
        cargarlos
    }
}
