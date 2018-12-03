
package Excepciones;


public class FormatoRutException extends Exception{
    private static String mensaje = "formato del rut ingresado es incorrecto";
    public FormatoRutException() {
        super(mensaje);
    }
    
    public static String getMensaje(){
        return mensaje;
    }
}
