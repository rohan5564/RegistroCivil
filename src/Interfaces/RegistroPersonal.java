
package Interfaces;

public interface RegistroPersonal{
    /**
     * permite comprobar que no se ha la persona no se haya registrado previamente 
     * como difunto
     * @return true si puede registrarse la defuncion, false en caso contrario
     */
    boolean registrarDefuncion();
    
    /**
     * En Chile, los matrimonios se rige bajo el principio de la monogamia
     * @return true si puede registrarse un nuevo matrimonio con otra persona,
     * false en caso contrario
     */
    boolean registrarMatrimonio();
}