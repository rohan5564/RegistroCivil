
package Enums;


public enum EstadoCivil{
    //https://es.wikipedia.org/wiki/Estado_civil_(Chile)
    HIJO("hijo", "Hija"), PADRE("padre", "padre"), MADRE("madre", "madre"), SOLTERO("soltero", "soltera"), 
    CASADO("casado", "casada"), VIUDO("viudo", "viuda"), DIVORCIADO("divorciado", "divorciada"), 
    SEPARADO("separado", "separada"), CONVIVIENTE("conviviente", "conviviente");
        
    static{
        HIJO.relacion = HIJO;
        PADRE.relacion = HIJO;
        MADRE.relacion = HIJO;
        SOLTERO.relacion = SOLTERO;
        CASADO.relacion = CASADO;
        VIUDO.relacion = DIVORCIADO.relacion = SEPARADO.relacion = CASADO;
        CONVIVIENTE.relacion = CONVIVIENTE;
    }   
    
    private String nombreMasculino;
    private String nombreFemenino;
    private EstadoCivil relacion;

    private EstadoCivil(String nombreMasculino, String nombreFemenino){
        this.nombreMasculino = nombreMasculino;
        this.nombreFemenino = nombreFemenino;
        
    }
    
    public EstadoCivil getRelacion(){
        return relacion;
    }
    
    public String getNombreMasculino(){
        return nombreMasculino;
    }

    public String getNombreFemenino(){
        return nombreFemenino;
    }
}
