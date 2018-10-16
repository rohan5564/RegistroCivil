/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package colecciones;

import colecciones.Ciudadano;
import java.util.ArrayList;

/**
 *
 * @author Jean
 */

public class Oficinas {
    
    private String ciudad;
    private int codigoComuna;
    private ArrayList<Ciudadano> personasInscritos;

    ////////////////////////////////////////////////////////////////////////////
    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public int getCodigoComuna() {
        return codigoComuna;
    }

    public void setCodigoComuna(int codigoComuna) {
        this.codigoComuna = codigoComuna;
    }

    public ArrayList<Ciudadano> getPersonasInscritos() {
        return personasInscritos;
    }

    public void setPersonasInscritos(ArrayList<Ciudadano> personasInscritos) {
        this.personasInscritos = personasInscritos;
    }
    ////////////////////////////////////////////////////////////////////////////
}
