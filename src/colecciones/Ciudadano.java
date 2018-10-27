/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package colecciones;

import Enums.EstadoCivil;
import Enums.Nacionalidad;
import Enums.Sexo;
import Interfaces.Personas;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

//import utilidades.XMLAdaptarClases;

/**
 *
 * @author Jean
 */

public abstract class Ciudadano implements Personas {
    
    private String nombre;
    private String apellido; 
    private Sexo sexo;
    private LocalDate nacimiento; //yo.setNacimiento(LocalDate.of(1996,8,7))
    private String horaNacimiento;
    private String comentarioNacimiento; //PERMITE NULL
    private LocalDate defuncion; //PERMITE NULL
    private String horaDefuncion; //PERMITE NULL
    private String comentarioDefuncion; //PERMITE NULL
    private String pasaporte; //PERMITE NULL
    private String profesion; //PERMITE NULL
    private ArrayList<EstadoCivil> estadoCivil;
    private ArrayList<Nacionalidad> nacionalidades;
    private Parientes parientes;
    
    /*
        No-Nulos: nombre, apellido, sexo, region, nacimiento, defuncion, estadoCivil
        nulos: direccion, defuncion, pasaporte, profesion
        */
    
    ////////////////////////////////////////////////////////////////////////////
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
        pasaporte = null;
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
    
    public String getPasaporte(){
        return pasaporte;
    }
    
    public void setPasaporte(String pasaporte){
        this.pasaporte = pasaporte.toUpperCase();
    }
    
    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion.toUpperCase();
    }
    
    
    public ArrayList<EstadoCivil> getEstadoCivil(){
        return estadoCivil;
    }
    
    public void setEstadoCivil(ArrayList<EstadoCivil> estadoCivil){
        this.estadoCivil.addAll(estadoCivil);
    }
    
    public ArrayList<Nacionalidad> getNacionalidades() {
        return nacionalidades;
    }

    public void setNacionalidades(ArrayList<Nacionalidad> nacionalidades) {
        this.nacionalidades.addAll(nacionalidades);
    }
    
    public Parientes getParientes() {
        return parientes;
    }

    public void setParientes(Parientes parientes) {
        this.parientes = parientes;
    }
    
    ////////////////////////////////////////////////////////////////////////////
    //sobrecarga setter
    public void setEstadoCivil(EstadoCivil estadoCivil){
        this.estadoCivil.add(estadoCivil);
    }
    
    public void setNacionalidades(Nacionalidad nacionalidad) {
        this.nacionalidades.add(nacionalidad);
    }
    
    ////////////////////////////////////////////////////////////////////////////
    
    @Override
    abstract public boolean registrarNacimiento();

    @Override
    abstract public boolean registrarDefuncion();
    
    @Override
    abstract public boolean registrarMatrimonio();

    @Override
    abstract public boolean modificarDatos();
    
    abstract public String mostrarIdentificador();
            
    public int getEdad(){
        if (defuncion == null)
            return Period.between(nacimiento, LocalDate.now()).getYears();
        return Period.between(nacimiento, defuncion).getYears();
    }
    
    public boolean getRequisitosMinimos(){
        return nombre != null
                && apellido != null
                && sexo != null
                && nacimiento != null
                && horaNacimiento != null
                && estadoCivil != null;
    }
    
}