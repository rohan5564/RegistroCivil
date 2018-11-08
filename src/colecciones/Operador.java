
package colecciones;
 
import java.sql.Date;
import java.time.LocalDate;


public class Operador {

    private String usuario;
    private String contraseña;
    private String nombre;
    private String apellido;
    private String rut;
    private Date nacimiento;
            
            
    public Operador(){
    }
    
    public Operador(String usuario, String contraseña, String nombre, String apellido, String rut, LocalDate nacimiento) {
        this.usuario = usuario;
        this.contraseña = contraseña;
        this.nombre = nombre;
        this.apellido = apellido;
        this.rut = rut;
        this.nacimiento = Date.valueOf(nacimiento);
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public LocalDate getNacimiento() {
        return nacimiento.toLocalDate();
    }

    public void setNacimiento(Date nacimiento) {
        this.nacimiento = nacimiento;
    }

}
