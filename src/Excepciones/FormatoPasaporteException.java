
package Excepciones;


public class FormatoPasaporteException extends Exception{
    private static String mensaje = "formato del pasaporte ingresado invalido";
    public FormatoPasaporteException() {
        super(mensaje);
    }
    
    public static String getMensaje(){
        return mensaje;
    }
}
