
package Excepciones;

import Enums.EstadoCivil;
import colecciones.ListadoParientes;
import java.util.Map;


public class CantidadParentescoException extends Exception{
    private static String mensaje = "Excede la cantidad de parientes posibles de acuerdo al estado civil";
    public CantidadParentescoException() {
        super(mensaje);
    }
    
    public static String getMensaje(){
        return mensaje;
    }
    
    /**
     * se verifica que se posea solo un matrimonio, un padre y una madre
     * @param personas todos los parientes a analizar
     * @param pariente pariente buscado
     * @param estado estado del pariente
     * @return true si es valido, false en caso contrario
     */
    public static boolean exception(Map<EstadoCivil, ListadoParientes> personas, String pariente, EstadoCivil estado){
        return (estado == EstadoCivil.CASADO || estado == EstadoCivil.MADRE || estado == EstadoCivil.PADRE)
                    && personas.get(estado).totalPorParentesco()>1;
    }
}
