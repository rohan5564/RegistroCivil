
package colecciones;

import Excepciones.FormatoPasaporteException;
import Excepciones.FormatoRutException;
import Excepciones.LongitudRutException;
import Interfaces.RegistroCivil;
import java.util.HashMap;
import java.util.Map;
import utilidades.ConexionBD;


public class Poblacion implements RegistroCivil{
    
    private Map<String, Ciudadano> poblacion;
    private static Poblacion singleton;
    
    private Poblacion() {
        this.poblacion = new HashMap<>();
    }
    
    public static Poblacion getInstancia(){
        if(singleton == null)
            singleton = new Poblacion();
        return singleton;
    }

    public Poblacion(HashMap<String, Ciudadano> poblacion) {
        this.poblacion = poblacion;
    }

    public void setPoblacion(Map<String, Ciudadano> poblacion) {
        this.poblacion = poblacion;
    }
    
    /**
     * permite mostrar si la poblacion esta vacia o no
     * @return true si esta vacia, false en caso contrario
     */
    public boolean estaVacia(){
        return poblacion == null || poblacion.isEmpty();
    }
    
    /**
     * permite contar la cantidad de registrados en el sistema
     * @return cantidad de registrados
     */
    public int totalPoblacion(){
        return poblacion.size();
    }
    
    /**
     * permite contar la cantidad de registrados en el sistema
     * @return cantidad de registrados
     */
    public int totalPoblacionBD(){
        return ConexionBD.getInstancia().totalChilenos();
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
    public void actualizarBase(){
        poblacion.forEach((k,v)->{
            if(v instanceof Chileno)
                ConexionBD.getInstancia().crearChileno((Chileno)v);
            else if(v instanceof Extranjero)
                ConexionBD.getInstancia().crearExtranjero((Extranjero)v);
        });
        poblacion.clear();
    }
    
    @Override
    public void registrarCiudadano(Ciudadano ciudadano){
        poblacion.put(ciudadano.mostrarIdentificador(), ciudadano);
    }
    
    @Override
    public boolean removerCiudadano(Ciudadano ciudadano){
        if(!poblacion.containsValue(ciudadano))
            return false;
        
        return getCiudadano(ciudadano.mostrarIdentificador()).desvincularDeParientes()
                && poblacion.remove(ciudadano.mostrarIdentificador()) != null;
    }
    
    @Override
    public boolean esRegistrable(String identificador){
        Ciudadano c = getCiudadanoBD(identificador);
        if(c!=null)
            registrarCiudadano(c);
        return !poblacion.containsKey(identificador);
    }
    
    @Override
    public Ciudadano getCiudadanoBD(String id){
        return ConexionBD.getInstancia().buscarCiudadano(id);
    }
    
    @Override
    public Chileno getChilenoBD(String id){
        return ConexionBD.getInstancia().buscarChileno(id);
    }
    
    @Override
    public Extranjero getExtranjeroBD(String id){
        return ConexionBD.getInstancia().buscarExtranjero(id);
    }
}
