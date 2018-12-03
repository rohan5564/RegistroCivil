
package colecciones;

import Enums.EstadoCivil;
import Enums.Visa;
import Excepciones.FormatoPasaporteException;
import java.time.LocalDate;
import Interfaces.RegistroPersonal;


public class Extranjero extends Ciudadano implements RegistroPersonal{
    
    private String pasaporte;
    private Visa tipoDeVisa;
    private LocalDate primeraVisa; //yo.setPrimeraVisa(LocalDate.of(YYYY,DD,MM))
    
    
    public Extranjero(){
        super();
        pasaporte = null;
        tipoDeVisa = null;
        primeraVisa = null;
    }

    public String getPasaporte() {
        return pasaporte;
    }

    public void setPasaporte(String pasaporte) {
        this.pasaporte = pasaporte;
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
    
    /**
     * permite comprobar que un pasaporte ingresado tenga un formato correcto para 
     * el registro civil
     * @param str pasaporte a comprobar
     * @return true si es un pasaporte valido, false en caso contrario
     * @throws FormatoPasaporteException si se ingresan caracteres no alfanumericos
     */
    public static boolean comprobarPasaporte(String str) throws FormatoPasaporteException{
        if(str.toUpperCase().matches(".*[^1234567890QWERTYUIOPASDFGHJKLZXCVBNM].*"))
            throw new FormatoPasaporteException();
        return str.length()>6;
    }
    
    
    /***************************************************************************
     *                  interfaz: RegistroPersonal                             *
     **************************************************************************/
    @Override
    public boolean registrarDefuncion(){
        return super.getDefuncion()!=null && primeraVisa != null;
    }
    
    @Override
    public boolean registrarMatrimonio(){
        return super.getParientes().buscarListaParentesco(EstadoCivil.CASADO).estaVacia()
                || super.getParientes().buscarListaParentesco(EstadoCivil.CASADO) == null;
    }
    
    /***************************************************************************
     *                  clase abstracta: Ciudadano                             *
     **************************************************************************/
    @Override
    public String mostrarIdentificador(){
        return pasaporte;
    }
    
}