
package Excepciones;

import java.util.regex.Pattern;


public class FormatoPasaporteException extends Exception{
    private static String mensaje = "formato del pasaporte ingresado invalido";
    public FormatoPasaporteException() {
        super(mensaje);
    }
    
    public static String getMensaje(){
        return mensaje;
    }
    
    public static boolean exception(String str){
        return str.matches(".*[^1234567890QWERTYUIOPASDFGHJKLZXCVBNM].*") || !Pattern.compile("^[A-Z]").matcher(str).find();
    }
}
