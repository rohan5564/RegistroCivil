
package colecciones;

import Enums.EstadoCivil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;


public class Parientes {
    private Map<EstadoCivil, List<Ciudadano>> personas;
    
    /**
     * crea un nuevo mapa enumerado por estados civiles
     */
    public Parientes(){
        personas = new EnumMap<>(EstadoCivil.class);
    }

    /**
     * inicializa el mapa enumerado por estados civiles
     * @param Parientes mapa de parientes
     */
    public Parientes(Map<EstadoCivil, List<Ciudadano>> Parientes) {
        this.personas = Parientes;
    }

    /**
     * @return mapa enumerado por estados civiles, que por cada estado contiene
     * una lista de ciudadanos con un mismo parentesco con el individuo
     */
    public Map<EstadoCivil, List<Ciudadano>> getPersonas() {
        return personas;
    }
    
    /**
     * permite buscar un listado de personas que registren un parentesco especifico
     * con el ciudadano
     * @param estado relacion de parentesco entre las personas
     * @since Entrega B
     * @return lista de parientes
     */
    public List<Ciudadano> buscarListaParentesco(EstadoCivil estado){
        return personas.get(estado);
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
     * @param persona ciudadano registrado como pariente
     * @since Entrega B
     * @return estado civil del ciudadano respecto a la persona ingresada por parametro
     */
    public EstadoCivil buscarPariente(Ciudadano persona){
        for(Map.Entry<EstadoCivil, List<Ciudadano>> lista : personas.entrySet()){
            for(Ciudadano pariente : lista.getValue()){
                if(pariente.equals(persona))
                    return lista.getKey();
            }
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
     * necesarias, false en caso contrario
     */
    public boolean agregarPariente(Ciudadano pariente, EstadoCivil estado){
        if(personas.containsKey(estado)){
            if((estado == EstadoCivil.CASADO || estado == EstadoCivil.MADRE || estado == EstadoCivil.PADRE)
                    && personas.get(estado).size()>1)
                return false;
            personas.get(estado).add(pariente);
        }
        else
            personas.put(estado, new ArrayList(Arrays.asList(pariente)));
        return true;
    }
    
    /**
     * permite quitar un pariente de una persona y su respectivo parentesco
     * @param pariente pariente a remover 
     * @param estado estado a remover para el ciudadano que invoco el metodo, debe
     * crear la variable previamente con un valor null
     * @return 1 si encuentra al pariente y lo remueve con exito, 0 si la lista
     * que contiene al estado civil de parentesco queda vacia, -1 si no elimina nada
     */
    public int removerPariente(Ciudadano pariente, EstadoCivil estado){
        for(Map.Entry<EstadoCivil, List<Ciudadano>> lista : personas.entrySet()){
            if(lista.getValue().remove(pariente)){
                if(lista.getValue().isEmpty()){
                    pariente.getEstadoCivil().remove(lista.getKey());
                    estado = lista.getKey().getRelacion();
                    return 0;
                }
                return 1;
            }
        }
        return -1;
    }
    
    /**
     * permite la eliminacion de todos los parientes de un sujeto
     * @return true si elimina todos los parientes, false en caso contrario
     */
    public boolean removerParientes(){
        personas.forEach((estado, lista) -> {
            lista.forEach(persona -> {
                persona.getEstadoCivil().remove(estado);
            });
        });
        personas.clear();        
        return personas.isEmpty();
    }
    
    /**
     * permite la modificacion de parentesco de un grupo de personas
     * respecto al individuo
     * @param persona persona con que se cambiara el parentesco
     * @param antiguo estado civil actual
     * @param nuevo nuevo estado civil a asignar
     * @return 
     */
    public boolean cambiarParentesco(Ciudadano persona, EstadoCivil antiguo, EstadoCivil nuevo){
        //la persona debe estar registrada en el mapa de parientes con el estado civil indicado
        if(!personas.containsKey(antiguo) && !personas.get(antiguo).contains(persona))
            return false;
        
        if(personas.containsKey(nuevo)){
            personas.get(antiguo).remove(persona);
            personas.get(nuevo).add(persona);
        }
        else
            personas.put(nuevo, new ArrayList<>(Arrays.asList(persona)));
        return true;
    }
    
    /**
     * permite la modificacion de parentesco de un grupo de personas
     * respecto al individuo
     * @param listaPersonas grupo de personas con que se cambiara el parentesco
     * @param antiguo estado civil actual
     * @param nuevo nuevo estado civil a asignar
     * @return 
     */
    public boolean cambiarParentesco(List<Ciudadano> listaPersonas, EstadoCivil antiguo, EstadoCivil nuevo){
        //las personas deben estar registradas en el mapa de parientes con el estado civil indicado
        if(!personas.containsKey(antiguo) && !personas.get(antiguo).containsAll(listaPersonas))
            return false;
        
        if(personas.containsKey(nuevo)){
            personas.get(antiguo).removeAll(listaPersonas);
            personas.get(nuevo).addAll(listaPersonas);
        }
        else
            personas.put(nuevo, new ArrayList<>(listaPersonas));
        return true;
    }
    
}
