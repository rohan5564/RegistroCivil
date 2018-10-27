/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package colecciones;

import Enums.EstadoCivil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Jean
 */
public class Parientes {
    private Map<EstadoCivil, List<Ciudadano>> personas;
    
    public Parientes(){
        personas = new EnumMap<>(EstadoCivil.class);
    }

    public Parientes(Map<EstadoCivil, List<Ciudadano>> Parientes) {
        this.personas = Parientes;
    }

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
    public List<Ciudadano> buscarPariente(EstadoCivil estado){
        return personas.get(estado);
    }
    
    public List<EstadoCivil> buscarParentesco(Ciudadano persona){
        for(Map.Entry<EstadoCivil, List<Ciudadano>> lista : personas.entrySet()){
            for(Ciudadano pariente : lista.getValue()){
                if(pariente.equals(persona))
                    return pariente.getEstadoCivil();
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
        //si no se registra un parentezco con el estado civil ingresado, se agrega
        if(!personas.containsKey(estado))
            personas.put(estado, new ArrayList<>(Arrays.asList(pariente)));
        else{
            //solo se permite una madre, un padre y un conyuge
            if(estado == EstadoCivil.CASADO || estado == EstadoCivil.MADRE || estado == EstadoCivil.PADRE)
                return false;
            personas.get(estado).add(pariente);
        }
        return true;
    }
    
}
