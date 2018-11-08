
package colecciones;

import Enums.EstadoCivil;
import Enums.Visa;
import Interfaces.Registro_Civil;
import java.time.LocalDate;


public class Extranjero extends Ciudadano implements Registro_Civil{
    
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
     * @return true si el extranjero puede registrarse, false en caso contrario
     */
    @Override
    public boolean registrar(){
        return super.getRequisitosMinimos() && pasaporte != null;
    }

    /**
     * @return true si puede registrarse la defuncion, false en caso contrario
     */
    @Override
    public boolean registrarDefuncion(){
        return super.getDefuncion()!=null && primeraVisa != null;
    }

    /**
     * se rige bajo el principio de monogamia, del mismo modo que un chileno
     * @return true si puede registrarse un nuevo matrimonio con otra persona,
     * false en caso contrario
     */
    @Override
    public boolean registrarMatrimonio(){
        return super.getParientes().buscarListaParentesco(EstadoCivil.CASADO).estaVacia()
                || super.getParientes().buscarListaParentesco(EstadoCivil.CASADO) == null;
    }
    
    /**
     * identificador por defecto para un extranjero es el pasapoprte
     * @return identificador unico del extranjero en chile
     */
    @Override
    public String mostrarIdentificador(){
        return pasaporte;
    }
    
}