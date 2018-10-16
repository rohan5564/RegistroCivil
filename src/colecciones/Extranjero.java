/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package colecciones;

import Enums.Visa;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Jean
 */
public class Extranjero extends Ciudadano {
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
        return true;
    }

    @Override
    public boolean registrarDefuncion(){
        return true;
    }

    @Override
    public boolean registrarMatrimonio(){
        return true;
    }
    
    @Override
    public boolean modificarDatos(){
        return true;
    }
    
    public void agregarParientes(ArrayList<String> identificador, ArrayList<Ciudadano> pariente){
        HashMap<String, Ciudadano> parientes = super.getParientes();
        for(int i = 0; i < pariente.size(); i++){
            parientes.put(identificador.get(i), pariente.get(i));
        }
    }
}