
package colecciones;

import Enums.EstadoCivil;
import Excepciones.CantidadParentescoException;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;


public class Parientes {
    
    private Map<EstadoCivil, ListadoParientes> personas;
    
    /**
     * crea un nuevo mapa enumerado por estados civiles
     */
    public Parientes(){
        personas = new EnumMap<>(EstadoCivil.class);
    }

    public Map<EstadoCivil, ListadoParientes> getPersonas() {
        return personas;
    }

    public void setPersonas(Map<EstadoCivil, ListadoParientes> personas) {
        this.personas = personas;
    }

    /**
     * 
     * @param estado
     * @param index
     * @return 
     */
    public String ObtenerCiudadanoPorEstado(EstadoCivil estado, int index){
        return personas.get(estado).obtenerCiudadano(index);
    }
    
    /**
     * permite mostrar si existe algun listado de parientes por un determinado estado
     * civil
     * @param estado estado civil a buscar
     * @return true si el estado no registra un listado de parientes
     */
    public boolean estadoEstaVacio(EstadoCivil estado){
        return personas.get(estado)==null || personas.get(estado).estaVacia();
    }
            
    /**
     * permite mostrar si existe o no un estado civil dentro de la coleccion de
     * parientes
     * @param estado estado civil a buscar
     * @return true si hay registro del estado civil, false en caso contrario
     */
    public boolean existeEstado(EstadoCivil estado){
        return personas.containsKey(estado);
    }
    
    /**
     * inicializa el mapa enumerado por estados civiles
     * @param Parientes mapa de parientes
     */
    public Parientes(Map<EstadoCivil, ListadoParientes> Parientes) {
        this.personas = Parientes;
    }

    
    /**
     * @return true si no hay parientes, false en caso contrario
     */
    public boolean estaVacia(){
        return personas == null || personas.isEmpty();
    }
    /**
     * permite buscar un listado de personas que registren un parentesco especifico
     * con el ciudadano
     * @param estado relacion de parentesco entre las personas
     * @since Entrega B
     * @return lista de parientes
     */
    public ListadoParientes buscarListaParentesco(EstadoCivil estado){
        return personas.get(estado);
    }
    
    /**
     * recorre el listado de parientes para contar el total
     * @return total de parientes
     */
    public int totalParientes(){
        int total = 0;
        for(Map.Entry<EstadoCivil, ListadoParientes> lista : personas.entrySet()){
            total+= lista.getValue().totalPorParentesco();
        }
        return total;
    }
    /**
     * permite encontrar el parentesco entre el ciudadano escojido y el ciudadano
     * ingresado por parametro. Por ejemplo, si "persona1" es padre y "persona2" es hijo,
     * el resultado de la operacion:
     * <pre><code>
     *  persona1.buscarPariente(persona2);
     * </code></pre>
     * es
     * <pre><code>
     *  EstadoCivil.<b>HIJO</b>
     * </code></pre>
     * @param persona identificador del ciudadano registrado como pariente
     * @since Entrega B
     * @return estado civil del ciudadano respecto a la persona ingresada por parametro
     */
    public EstadoCivil buscarPariente(String persona){
        for(Map.Entry<EstadoCivil, ListadoParientes> lista : personas.entrySet()){
            if(lista.getValue().contienePersona(persona))
                return lista.getKey();
        }                
        return null;
    }
    
    
    /**
     * permite relacionar una persona a un ciudadano de acuerdo al parentesco deseado. En caso de ser matrimonio solo se
     * permite una pareja por ciudadano
     * @param pariente persona con quien se registra algun parentesco
     * @param estado relacion de parentesco entre las personas
     * @since Entrega b
     * @return true si puede agregar al pariente mientras cumpla con las condiciones
     * @throws CantidadParentescoException cantidad de parientes por estado en especifico
     * necesarias, false en caso contrario
     */
    public boolean agregarPariente(String pariente, EstadoCivil estado)throws CantidadParentescoException{
        if(personas.containsKey(estado)){
            if(CantidadParentescoException.exception(personas, pariente, estado))
                throw new CantidadParentescoException();
            else{
                personas.get(estado).agregar(pariente);
                return true;
            }
        }
        return personas.put(estado, new ListadoParientes(pariente))==null;
    }
    
    /**
     * permite quitar un pariente de una persona segun el parentesco indicado
     * @param pariente pariente a remover 
     * @return 1 si encuentra al pariente y lo remueve con exito, 0 si la lista
     * que contiene al estado civil de parentesco queda vacia, -1 si no elimina nada
     */
    public int removerPariente(String pariente){
        for(Map.Entry<EstadoCivil, ListadoParientes> lista : personas.entrySet()){
            if(lista.getValue().borrar(pariente)){
                if(lista.getValue().estaVacia()){
                    return 0;
                }
                return 1;
            }
        }
        return -1;
    }
    
    /**
     * permite quitar el listado de parientes segun el tipo de estado civil especificado
     * @param estado estado a remover 
     * @return true si elimina los parientes asociados al estado, false en caso contrario
     */
    public boolean removerParientes(EstadoCivil estado){
        return personas.remove(estado)!=null;
    }
    
    /**
     * permite la eliminacion de todos los parientes de un sujeto
     * @return true si elimina todos los parientes, false en caso contrario
     */
    public boolean removerParientes(){
        personas.clear();        
        return personas.isEmpty() || personas==null;
    }
    
    /**
     * permite la modificacion de parentesco una personas respecto al individuo
     * @param persona persona con que se cambiara el parentesco
     * @param antiguo estado civil actual
     * @param nuevo nuevo estado civil a asignar
     * @return true si puede mover al pariente de un parentesco a otro, false en caso contrario
     */
    public boolean cambiarParentesco(String persona, EstadoCivil antiguo, EstadoCivil nuevo){
        //la persona debe estar registrada en el mapa de parientes con el estado civil indicado
        if(!personas.containsKey(antiguo) && !personas.get(antiguo).contienePersona(persona))
            return false;
        
        personas.get(antiguo).borrar(persona);
        if(personas.containsKey(nuevo))
            personas.get(nuevo).agregar(persona);
        else
            personas.put(nuevo, new ListadoParientes(persona));
        
        return true;
    }
    
    /**
     * permite la modificacion del parentesco de un grupo de personas
     * @param listaPersonas grupo de personas con que se cambiara el parentesco
     * @param antiguo estado civil actual
     * @param nuevo nuevo estado civil a asignar
     * @return true si puede mover al listado de parientes de un parentesco a otro, false en caso contrario
     */
    public boolean cambiarParentesco(List<String> listaPersonas, EstadoCivil antiguo, EstadoCivil nuevo){
        //las personas deben estar registradas en el mapa de parientes con el estado civil indicado
        if(!personas.containsKey(antiguo) && !personas.get(antiguo).contieneListadoPersonas(listaPersonas))
            return false;
        
        personas.get(antiguo).borrarListado(listaPersonas);
        if(personas.containsKey(nuevo))
            personas.get(nuevo).agregarListado(listaPersonas);
        else
            personas.put(nuevo, new ListadoParientes(listaPersonas));
        
        return true;
    }
    
}