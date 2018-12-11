
package Excepciones;


public class LongitudRutException extends Exception{
    private static String mensaje = "longitud del rut invalido";
    public LongitudRutException() {
        super(mensaje);
    }
    
    public static String getMensaje(){
        return mensaje;
    }
    
    /**
     * se verifica que no posea un largo menor a 7 caracteres o mayor a 9
     * @param pasaporte pasaporte a comprobar
     * @return true si es valido, false en caso contrario
     */
    public static boolean exception(String pasaporte){
        return pasaporte.length()<7 || pasaporte.length()>9;
    }
}
