
package colecciones;
 
import java.sql.Date;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "usuarios")
public class Operador {
    
    @Id
    @Column(name = "usuario")
    private String usuario;
    
    @Column(name = "contraseña")
    private String contraseña;
    
    @Column(name = "nombre")
    private String nombre;
    
    @Column(name = "apellido")
    private String apellido;
    
    @Column(name = "rut")
    private String rut;
    
    @Column(name = "fechaNacimiento")
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
