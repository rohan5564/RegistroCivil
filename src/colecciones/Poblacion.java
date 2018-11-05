/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package colecciones;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Jean
 */
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
    
    public boolean removerCiudadano(Ciudadano ciudadano){
        if(!poblacion.containsValue(ciudadano))
            return false;
        ciudadano.getParientes().getPersonas().forEach((estado, lista) -> {
            lista.forEach(persona -> {
                persona.getEstadoCivil().remove(estado);
            });
        });
        ciudadano.getParientes().getPersonas().clear();
        
        if(ciudadano instanceof Chileno)
            return removerChileno((Chileno)ciudadano);
        else 
            return removerExtranjero((Extranjero)ciudadano);
    }
    
    private boolean removerChileno(Chileno chileno){
        return chileno.getParientes().getPersonas().isEmpty() && poblacion.remove(chileno.getRut(), chileno);
    }
    
    private boolean removerExtranjero(Extranjero extranjero){
        return extranjero.getParientes().getPersonas().isEmpty() && poblacion.remove(extranjero.getPasaporte(), extranjero);
    }
}
