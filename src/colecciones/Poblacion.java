
package colecciones;

import java.util.HashMap;
import java.util.Map;


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
     * @param ciudadano ciudano a eliminar
     * @return se remueve a la persona ingresada
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
     * @return retorna true, si puede removerse, si no null
     */
    private boolean removerChileno(Chileno chileno){
        return chileno.getParientes().estaVacia() && poblacion.remove(chileno.getRut())!=null;
    }
    
    /**
     * 
     * @param extranjero
     * @return returna true si puede removerse, si no null
     */
    private boolean removerExtranjero(Extranjero extranjero){
        return extranjero.getParientes().estaVacia() && poblacion.remove(extranjero.getPasaporte())!=null;
    }
    
    /**
     * 
     * @return la cantidad de extranjeros encontrados
     */
    public Map<String, Extranjero> getExtranjeros(){
        Map<String, Extranjero> total = null;
        for(Map.Entry<String, Ciudadano> lista : poblacion.entrySet()){
            if(lista.getValue() instanceof Extranjero){
                if(total == null)
                    total = new HashMap<>();
                total.put(lista.getKey(), (Extranjero)lista.getValue());
            }
        }
        return total;
    }
    
    /**
     * 
     * @return la cantidad de chilenos encontrados 
     */
    public Map<String, Chileno> getChilenos(){
        Map<String, Chileno> total = null;
        for(Map.Entry<String, Ciudadano> lista : poblacion.entrySet()){
            if(lista.getValue() instanceof Chileno){
                if(total == null)
                    total = new HashMap<>();
                total.put(lista.getKey(), (Chileno)lista.getValue());
            }
        }
        return total;
    }
}
