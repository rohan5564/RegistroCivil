
package colecciones;

import Enums.EstadoCivil;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Table(name = "poblacion")
public class Poblacion {
    
    @OneToMany(mappedBy = "poblacion", cascade = CascadeType.ALL)
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
    
    private boolean removerChileno(Chileno chileno){
        return chileno.getParientes().estaVacia() && poblacion.remove(chileno.getRut())!=null;
    }
    
    private boolean removerExtranjero(Extranjero extranjero){
        return extranjero.getParientes().estaVacia() && poblacion.remove(extranjero.getPasaporte())!=null;
    }
}
