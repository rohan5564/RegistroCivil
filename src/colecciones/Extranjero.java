/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package colecciones;

import Enums.EstadoCivil;
import Enums.Visa;
import Interfaces.Registro_Civil;
import java.time.LocalDate;

/**
 *
 * @author Jean
 */
public class Extranjero extends Ciudadano implements Registro_Civil{
    /**
     * @see http://www.extranjeria.gob.cl/nacionalizacion/
     */    
    private Visa tipoDeVisa;
    private LocalDate primeraVisa; //yo.setPrimeraVisa(LocalDate.of(YYYY,DD,MM))
    
    public Extranjero(){
        super();
        tipoDeVisa = null;
        primeraVisa = null;
    }

    public Visa getTipoDeVisa() {
        return tipoDeVisa;
    }

    public void setTipoDeVisa(Visa tipoDeVisa) {
        this.tipoDeVisa = tipoDeVisa;
    }

    public LocalDate getPrimeraVisa() {
        return primeraVisa;
    }

    public void setPrimeraVisa(LocalDate primeraVisa) {
        this.primeraVisa = primeraVisa;
    }
    
    /*funciones a implementar en el programa
    */
    @Override
    public boolean registrarNacimiento(){
        return super.getRequisitosMinimos();
    }

    @Override
    public boolean registrarDefuncion(){
        return super.getDefuncion()!=null;
    }

    @Override
    public boolean registrarMatrimonio(){
        return super.getParientes().buscarListaParentesco(EstadoCivil.CASADO).isEmpty()
                || super.getParientes().buscarListaParentesco(EstadoCivil.CASADO) == null;
    }
    
    @Override
    public String mostrarIdentificador(){
        return getPasaporte();
    }
    
}