
package Enums;


public enum Visa{
    TEMPORAL("temporal"), CONTRATO("contrato"), 
    ESTUDIANTE("estudiante"), TRIPULANTE("tripulante");
    
    private String nombre;
        
    private Visa(String nombre){
        this.nombre = nombre;
    }
        
    /**
     * @see http://www.extranjeria.gob.cl/visas/
     */
    public String getNombre(){
        return nombre;
    }
    
    public static Visa valorDe(String str){
        return Visa.valueOf(str.toUpperCase());
    }
}
