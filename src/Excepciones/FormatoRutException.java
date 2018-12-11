
package Excepciones;


public class FormatoRutException extends Exception{
    private static String mensaje = "formato del rut ingresado es incorrecto";
    public FormatoRutException() {
        super(mensaje);
    }
    
    public static String getMensaje(){
        return mensaje;
    }
    /**
     * se verifica que no posea caracteres distintos a 0..9-K
     * @param rut rut a comprobar
     * @return true si es valido, false en caso contrario
     */
    public static boolean exception(String rut){
        return rut.matches(".*[^1234567890K].*");
    }
}
