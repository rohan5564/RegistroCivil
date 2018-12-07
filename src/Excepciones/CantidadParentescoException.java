
package Excepciones;


public class CantidadParentescoException extends Exception{
    private static String mensaje = "Excede la cantidad de parientes posibles de acuerdo al estado civil";
    public CantidadParentescoException() {
        super(mensaje);
    }
    
    public static String getMensaje(){
        return mensaje;
    }
}
