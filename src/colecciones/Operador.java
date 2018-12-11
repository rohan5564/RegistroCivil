
package colecciones;
 
import java.sql.Date;
import java.time.LocalDate;


public class Operador {

    private String usuario;
    private String contraseña;
    private String region;
    private String correo;
    private static Operador op;
            
    private Operador(){
    }
    
    public static Operador getInstancia(){
        if(op == null)
            op = new Operador();
        return op;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

}
