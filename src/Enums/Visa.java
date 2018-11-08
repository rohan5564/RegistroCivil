
package Enums;


public enum Visa{
    //http://www.extranjeria.gob.cl/visas/
    TEMPORAL("temporal"), CONTRATO("contrato"), 
    ESTUDIANTE("estudiante"), TRIPULANTE("tripulante");
    
    private String nombre;
        
    private Visa(String nombre){
        this.nombre = nombre;
    }
        
    public String getNombre(){
        return nombre;
    }
}
