
package colecciones;

import Enums.EstadoCivil;
import Enums.Nacionalidad;
import Enums.Sexo;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public abstract class Ciudadano<T extends Ciudadano<T>>{
    
    /**
     * solo agregar datos de un ciudadano, da igual si es Chileno o Extranjero
     * @param <T> Tipo de ciudadano
     */
    public static abstract class Builder<T>{
        private String nombre = null;
        private String apellido = null;
        private Sexo sexo = null;
        private LocalDate nacimiento = null; //yo.setNacimiento(LocalDate.of(1996,8,7))
        private String horaNacimiento = null;
        private String comentarioNacimiento = null; //PERMITE NULL
        private LocalDate defuncion = null; //PERMITE NULL
        private String horaDefuncion = null; //PERMITE NULL
        private String comentarioDefuncion = null; //PERMITE NULL
        private String profesion = null; //PERMITE NULL
        private List<EstadoCivil> estadoCivil = new ArrayList<>();
        private List<Nacionalidad> nacionalidades = new ArrayList<>();
        private Parientes parientes = new Parientes();

        public Builder<T> setNombre(String nombre) {
            this.nombre = nombre.toUpperCase();
            return this;
        }

        public Builder<T> setApellido(String apellido) {
            this.apellido = apellido.toUpperCase();
            return this;
        }

        public Builder<T> setSexo(Sexo sexo) {
            this.sexo = sexo;
            return this;
        }

        public Builder<T> setNacimiento(LocalDate nacimiento) {
            this.nacimiento = nacimiento;
            return this;
        }

        public Builder<T> setHoraNacimiento(String horaNacimiento) {
            this.horaNacimiento = horaNacimiento;
            return this;
        }

        public Builder<T> setComentarioNacimiento(String comentarioNacimiento) {
            this.comentarioNacimiento = comentarioNacimiento;
            return this;
        }

        public Builder<T> setDefuncion(LocalDate defuncion) {
            this.defuncion = defuncion;
            return this;
        }

        public Builder<T> setHoraDefuncion(String horaDefuncion) {
            this.horaDefuncion = horaDefuncion;
            return this;
        }

        public Builder<T> setComentarioDefuncion(String comentarioDefuncion) {
            this.comentarioDefuncion = comentarioDefuncion;
            return this;
        }

        public Builder<T> setProfesion(String profesion) {
            this.profesion = profesion;
            return this;
        }
        
        public Builder<T> setEstadoCivil(List<EstadoCivil> estadoCivil){
            this.estadoCivil.addAll(estadoCivil);
            return this;
        }
    
        public Builder<T> setEstadoCivil(EstadoCivil estadoCivil){
            if(!this.estadoCivil.contains(estadoCivil))
                this.estadoCivil.add(estadoCivil);
            return this;
        }

        public Builder<T> setNacionalidades(List<Nacionalidad> nacionalidades) {
            this.nacionalidades.addAll(nacionalidades);
            return this;
        }

        public Builder<T> setNacionalidades(Nacionalidad nacionalidad) {
            this.nacionalidades.add(nacionalidad);
            return this;
        }
        
        public Builder<T> setParientes(Parientes parientes) {
            this.parientes = parientes;
            return this;
        }
            
        public abstract T build();
    }
    
    
    /************************************************************************************
    *   No-Nulos: nombre, apellido, sexo, region, nacimiento, defuncion, estadoCivil    *
    *                                                                                   *
    *   nulos: direccion, defuncion, pasaporte, profesion                               *
    ************************************************************************************/
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
    private List<EstadoCivil> estadoCivil = new ArrayList<>();
    private List<Nacionalidad> nacionalidades = new ArrayList<>();
    private Parientes parientes = new Parientes();
    
    /**
     * solo agregar datos de un ciudadano, da igual si es Chileno o Extranjero
     * @param builder builder del ciudadano
     */
    public Ciudadano(final Builder<T> builder){
        this.apellido = builder.apellido;
        this.comentarioDefuncion = builder.comentarioDefuncion;
        this.comentarioNacimiento = builder.comentarioNacimiento;
        this.defuncion = builder.defuncion;
        this.estadoCivil = builder.estadoCivil;
        this.horaDefuncion = builder.horaDefuncion;
        this.horaNacimiento = builder.horaNacimiento;
        this.nacimiento = builder.nacimiento;
        this.nacionalidades = builder.nacionalidades;
        this.nombre = builder.nombre;
        this.parientes = builder.parientes;
        this.profesion = builder.profesion;
        this.sexo = builder.sexo;
    }
    
    /**
     * permite mostrar el identificador unico de un ciudadano. En caso de ser
     * chileno corresponde al Rut, si es extranjero corresponde al pasaporte
     * @return identificador del ciudadano
     */
    abstract public String mostrarIdentificador();
    
    public String getNombre() {
        return nombre;
    }
    
    public String getApellido() {
        return apellido;
    }
    
    public Sexo getSexo() {
        return sexo;
    }

    public LocalDate getNacimiento() {
        return nacimiento;
    }
    
    public String getHoraNacimiento() {
        return horaNacimiento;
    }

    public String getComentarioNacimiento() {
        return comentarioNacimiento;
    }
        
    public LocalDate getDefuncion() {
        return defuncion;
    }
        
    public String getHoraDefuncion() {
        return horaDefuncion;
    }

    public String getComentarioDefuncion() {
        return comentarioDefuncion;
    }

    public String getProfesion() {
        return profesion;
    }
        
    public List<EstadoCivil> getEstadoCivil(){
        return estadoCivil;
    }
    
    
    public List<Nacionalidad> getNacionalidades() {
        return nacionalidades;
    }
    
    public Parientes getParientes() {
        return parientes;
    }

    /**
     * permite reemplazar el nombre guardado o agregarlo si es 
     * que no existe
     * @param nombre nombre que reemplaza al existente
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * permite reemplazar el apellido guardado o agregarlo si es 
     * que no existe
     * @param apellido apellido que reemplaza al existente
     */
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    /**
     * permite reemplazar el sexo guardado o agregarlo si es 
     * que no existe
     * @param sexo sexo que reemplaza al existente
     */
    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    /**
     * permite reemplazar la fecha de nacimiento guardada o agregarlo si es 
     * que no existe
     * @param nacimiento fecha de nacimiento que reemplaza al existente
     */
    public void setNacimiento(LocalDate nacimiento) {
        this.nacimiento = nacimiento;
    }

    /**
     * permite reemplazar la hora de nacimiento guardada o agregarlo si es 
     * que no existe
     * @param horaNacimiento hora de nacimiento que reemplaza al existente
     */
    public void setHoraNacimiento(String horaNacimiento) {
        this.horaNacimiento = horaNacimiento;
    }

    /**
     * permite reemplazar el comentario de nacimiento guardado o agregarlo si es 
     * que no existe
     * @param comentarioNacimiento comentario de nacimiento que reemplaza al existente
     */
    public void setComentarioNacimiento(String comentarioNacimiento) {
        this.comentarioNacimiento = comentarioNacimiento;
    }
    
    /**
     * permite reemplazar el dia de defuncion guardado o agregarlo si es 
     * que no existe
     * @param defuncion fecha de defuncion que reemplaza al existente
     */
    public void setDefuncion(LocalDate defuncion) {
        this.defuncion = defuncion;
    }

    /**
     * permite reemplazar la hora de defuncion guardad o agregarla si es 
     * que no existe
     * @param horaDefuncion hora de defuncion que reemplaza al existente
     */
    public void setHoraDefuncion(String horaDefuncion) {
        this.horaDefuncion = horaDefuncion;
    }

    /**
     * permite reemplazar el comentario de defuncion guardado o agregarlo si es 
     * que no existe
     * @param comentarioDefuncion comentario de defuncion que reemplaza al existente
     */
    public void setComentarioDefuncion(String comentarioDefuncion) {
        this.comentarioDefuncion = comentarioDefuncion;
    }

    /**
     * permite reemplazar la profesion guardada o agregarlo si es 
     * que no existe
     * @param profesion profesion que reemplaza a la existente
     */
    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }
    
    /**
     * permite agregar un listado de estados civiles
     * @param estadoCivil List<> de estados a agregar
     */
    public void agregarEstadoCivil(List<EstadoCivil> estadoCivil){
        this.estadoCivil.addAll(estadoCivil);
    }
    
    /**
     * permite agregar un estado civil
     * @param estadoCivil estado a agregar
     */
    public void agregarEstadoCivil(EstadoCivil estadoCivil){
        if(!this.estadoCivil.contains(estadoCivil))
            this.estadoCivil.add(estadoCivil);           
    }
    
    /**
     * permite agregar un listado de nacionalidades
     * @param nacionalidades List<> de nacionalidades a agregar
     */
    public void agregarNacionalidades(List<Nacionalidad> nacionalidades) {
        this.nacionalidades.addAll(nacionalidades);
    }

    /**
     * permite agregar una nacionalidad
     * @param nacionalidad nacionalidad a agregar
     */
    public void agregarNacionalidades(Nacionalidad nacionalidad) {
        this.nacionalidades.add(nacionalidad);
    }
    
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
            for(String cadaPariente : listaParientes.getValue().getListadoParientes()){
                Ciudadano parienteActual = Poblacion.getInstancia().getCiudadano(cadaPariente);
                int estados = parienteActual.getParientes().removerPariente(mostrarIdentificador());
                if(estados == 0){
                    parienteActual.getEstadoCivil().remove(listaParientes.getKey());
                    resultado = true;
                }
                else if(estados == 1){
                    resultado = true;
                }
            }
        }
        
        return resultado;
    }
}