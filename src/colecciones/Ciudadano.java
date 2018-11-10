
package colecciones;

import Enums.EstadoCivil;
import Enums.Nacionalidad;
import Enums.Sexo;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public abstract class Ciudadano{
    
    private String nombre;
    private String apellido; 
    private Sexo sexo;
    private LocalDate nacimiento; //yo.setNacimiento(LocalDate.of(1996,8,7))
    private String horaNacimiento;
    private String comentarioNacimiento; //PERMITE NULL
    private LocalDate defuncion; //PERMITE NULL
    private String horaDefuncion; //PERMITE NULL
    private String comentarioDefuncion; //PERMITE NULL
    private String profesion; //PERMITE NULL
    private List<EstadoCivil> estadoCivil;
    private List<Nacionalidad> nacionalidades;
    private Parientes parientes;
    
    /************************************************************************************
    *   No-Nulos: nombre, apellido, sexo, region, nacimiento, defuncion, estadoCivil    *
    *                                                                                   *
    *   nulos: direccion, defuncion, pasaporte, profesion                               *
    ************************************************************************************/
    
    public Ciudadano() {
        nombre = null;
        apellido = null;
        sexo = null;
        nacimiento = null;
        horaNacimiento = null;
        comentarioNacimiento = null;
        defuncion = null;
        horaDefuncion = null;
        comentarioDefuncion = null;
        profesion = null;
        estadoCivil = new ArrayList<>();
        nacionalidades = new ArrayList<>();
        parientes = new Parientes();
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre.toUpperCase();
    }
    
    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido.toUpperCase();
    }
    
    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }
    
    public LocalDate getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(LocalDate nacimiento) {
        this.nacimiento = nacimiento;
    }
    
    public String getHoraNacimiento() {
        return horaNacimiento;
    }

    public void setHoraNacimiento(String horaNacimiento) {
        this.horaNacimiento = horaNacimiento;
    }

    public String getComentarioNacimiento() {
        return comentarioNacimiento;
    }
    
    public void setComentarioNacimiento(String comentarioNacimiento) {
        this.comentarioNacimiento = comentarioNacimiento;
    }
    
    public LocalDate getDefuncion() {
        return defuncion;
    }
    
    public void setDefuncion(LocalDate defuncion) {
        this.defuncion = defuncion;
    }
    
    public String getHoraDefuncion() {
        return horaDefuncion;
    }

    public void setHoraDefuncion(String horaDefuncion) {
        this.horaDefuncion = horaDefuncion;
    }

    public String getComentarioDefuncion() {
        return comentarioDefuncion;
    }

    public void setComentarioDefuncion(String comentarioDefuncion) {
        this.comentarioDefuncion = comentarioDefuncion;
    }
    
    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion.toUpperCase();
    }
    
    
    public List<EstadoCivil> getEstadoCivil(){
        return estadoCivil;
    }
    
    public void setEstadoCivil(List<EstadoCivil> estadoCivil){
        this.estadoCivil.addAll(estadoCivil);
    }
    
    public void setEstadoCivil(EstadoCivil estadoCivil){
        if(!this.estadoCivil.contains(estadoCivil))
            this.estadoCivil.add(estadoCivil);
    }
    
    public List<Nacionalidad> getNacionalidades() {
        return nacionalidades;
    }

    public void setNacionalidades(List<Nacionalidad> nacionalidades) {
        this.nacionalidades.addAll(nacionalidades);
    }
    
    public void setNacionalidades(Nacionalidad nacionalidad) {
        this.nacionalidades.add(nacionalidad);
    }
    
    public Parientes getParientes() {
        return parientes;
    }

    public void setParientes(Parientes parientes) {
        this.parientes = parientes;
    }
    
    /**
     * permite mostrar el identificador unico de un ciudadano. En caso de ser
     * chileno corresponde al Rut, si es extranjero corresponde al pasaporte
     * @return identificador del ciudadano
     */
    abstract public String mostrarIdentificador();
            
    /**
     * permite calcular la edad del ciudadano, partiendo desde su nacimiento hasta hoy
     * o el dia de su muerte
     * @return edad del ciudadano
     */
    public int getEdad(){
        if (defuncion == null)
            return Period.between(nacimiento, LocalDate.now()).getYears();
        return Period.between(nacimiento, defuncion).getYears();
    }
    
    /**
     * permite definir los requisitos minimos en los registro que debe poseer un ciudadano,
     * sin importar si es chileno o extranjero
     * @return true si cumple con los requisitos, false en caso contrario
     */
    public boolean getRequisitosMinimos(){
        return nombre != null
                && apellido != null
                && sexo != null
                && nacimiento != null
                && horaNacimiento != null
                && estadoCivil != null;
    }
    
    /**
     * permite desvincular al ciudadano de sus parientes
     * @return true si puede completar la accion de forma exitosa, false en caso 
     * contrario
     */
    public boolean desvincularDeParientes(){
        boolean resultado = false;
        for(Map.Entry<EstadoCivil, ListadoParientes> listaParientes : parientes.getPersonas().entrySet()){
            for(Ciudadano cadaPariente : listaParientes.getValue().getListadoParientes()){
                EstadoCivil estadoABorrar = null;
                int estados = cadaPariente.getParientes().removerPariente(this, estadoABorrar);
                if(estados == 0){
                    if(estadoABorrar==EstadoCivil.HIJO){
                        if(cadaPariente.getEstadoCivil().contains(EstadoCivil.MADRE))
                            cadaPariente.getEstadoCivil().remove(EstadoCivil.MADRE);
                        else
                            cadaPariente.getEstadoCivil().remove(EstadoCivil.PADRE);
                    }
                    else
                        cadaPariente.getEstadoCivil().remove(estadoABorrar);
                    resultado = true;
                    break;
                }
                else if(estados == 1){
                    resultado = true;
                    break;
                }
            }
        }
        
        return resultado;
    }
    
}