
package colecciones;

import Excepciones.FormatoPasaporteException;
import Excepciones.FormatoRutException;
import Excepciones.LongitudRutException;
import Interfaces.RegistroCivil;
import java.util.HashMap;
import java.util.Map;


public class Poblacion implements RegistroCivil{
    
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
     * @return extranjeros registrados
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
     * @return chilenos registrados
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
    
    /**
     * permite buscar un extranjero chileno entre los registrados
     * @param identificador pasaporte del extranjero a buscar
     * @return objeto Extranjero si es encontrado, null si no existe como ciudadano registrado
     * @throws FormatoPasaporteException si se ingresa un pasaporte con formato invalido
     */
    public Extranjero getExtranjero(String identificador) throws FormatoPasaporteException{
        return Extranjero.comprobarPasaporte(identificador)?(Extranjero)poblacion.get(identificador):null;
    }
    
    /**
     * permite buscar un ciudadano chileno entre los registrados
     * @param identificador rut del chileno a buscar
     * @return objeto Chileno si es encontrado, null si no existe como ciudadano registrado
     * @throws FormatoRutException si se ingresa un rut con formato invalido
     * @throws LongitudRutException si se ingresa un rut con longitud invalida
     */
    public Chileno getChileno(String identificador) throws FormatoRutException, LongitudRutException{
        return Chileno.comprobarRut(identificador)?(Chileno)poblacion.get(identificador):null;
    }
    
    /**
     * permite buscar un ciudadano entre los registrados
     * @param identificador identificador del ciudadano a buscar
     * @return objeto Ciudadano si es encontrado, null si no existe como ciudadano registrado
     */
    public Ciudadano getCiudadano(String identificador){
        return poblacion.get(identificador);
    }
    /***************************************************************************
     *                      interfaz: RegistroCivil                            *
     **************************************************************************/
    @Override
    public void registrarCiudadano(Ciudadano ciudadano){
        poblacion.put(ciudadano.mostrarIdentificador(), ciudadano);
    }
    
    @Override
    public boolean removerCiudadano(Ciudadano ciudadano){
        if(!poblacion.containsValue(ciudadano))
            return false;
        
        if(ciudadano instanceof Chileno)
            return ciudadano.getParientes().removerParientes() && removerChileno((Chileno)ciudadano);
        else 
            return ciudadano.getParientes().removerParientes() && removerExtranjero((Extranjero)ciudadano);
    }
    
    @Override
    public boolean esRegistrable(String identificador){
        return !poblacion.containsKey(identificador);
    }
}
