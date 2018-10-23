/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package colecciones;

import Enums.EstadoCivil;
import java.util.EnumMap;
import java.util.Map;

/**
 *
 * @author Jean
 */
public class Parientes {
    private Map<EstadoCivil, Ciudadano> personas;
    
    public Parientes(){
        personas = new EnumMap<>(EstadoCivil.class);
    }

    public Parientes(Map<EstadoCivil, Ciudadano> Parientes) {
        this.personas = Parientes;
    }

    public Map<EstadoCivil, Ciudadano> getPersonas() {
        return personas;
    }
    
    /**
     * permite agregar personas a un ciudadano mientras este no tenga algun otro
 parentesco registrado hacia un mismo individuo
     * @param pariente persona con quien se registra algun parentesco
     * @param estado relacion de parentesco entre las personas
     * @return true si puede agregar al pariente mientras cumpla con las condiciones
     * necesarias, false en caso contrario
     */
    public boolean agregarPariente(Ciudadano pariente, EstadoCivil estado){
        //se verifica que las personas solo posean un parentezco entre si
        if(personas.containsKey(estado) || personas.containsValue(pariente))
            return false;
        
        //se agrega el individuo con su correspondiente parentesco
        personas.put(estado, pariente);
        return true;
    }
    
}
