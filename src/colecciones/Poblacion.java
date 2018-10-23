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
    
}
