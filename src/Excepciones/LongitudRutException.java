
package Excepciones;


public class LongitudRutException extends Exception{
    private static String mensaje = "longitud del rut invalido";
    public LongitudRutException() {
        super(mensaje);
    }
    
    public static String getMensaje(){
        return mensaje;
    }
}
