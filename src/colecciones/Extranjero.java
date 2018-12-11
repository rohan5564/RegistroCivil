
package colecciones;

import Enums.EstadoCivil;
import Enums.Visa;
import Excepciones.FormatoPasaporteException;
import java.time.LocalDate;
import Interfaces.RegistroPersonal;
import java.util.regex.Pattern;


public class Extranjero extends Ciudadano<Extranjero> implements RegistroPersonal{
    
    /**
     * PARTIR POR ATRIBUTOS PROPIOS DEL EXTRANJERO
     */
    public static class BuilderExtranjero extends Ciudadano.Builder<Extranjero>{
        private String pasaporte = null;
        private Visa tipoDeVisa = null;
        private LocalDate primeraVisa = null; //yo.setPrimeraVisa(LocalDate.of(YYYY,DD,MM))
        
        public BuilderExtranjero setPasaporte(String pasaporte) {
            this.pasaporte = pasaporte;
            return this;
        }
        
        public BuilderExtranjero setTipoDeVisa(Visa tipoDeVisa) {
            this.tipoDeVisa = tipoDeVisa;
            return this;
        }
    
        public BuilderExtranjero setPrimeraVisa(LocalDate primeraVisa) {
            this.primeraVisa = primeraVisa;
            return this;
        }
        
        @Override
        public Extranjero build(){
            return new Extranjero(this);
        }
                
    }
    private String pasaporte;
    private Visa tipoDeVisa;
    private LocalDate primeraVisa; //yo.setPrimeraVisa(LocalDate.of(YYYY,DD,MM))
    
    
    private Extranjero(final BuilderExtranjero builder){
        super(builder);
        this.pasaporte = builder.pasaporte;
        this.tipoDeVisa = builder.tipoDeVisa;
        this.primeraVisa = builder.primeraVisa;
    }

    public String getPasaporte() {
        return pasaporte;
    }

    public Visa getTipoDeVisa() {
        return tipoDeVisa;
    }
    
    public LocalDate getPrimeraVisa() {
        return primeraVisa;
    }
    
    /**
     * permite reemplazar el tipo de visa guardado o agregarlo si es 
     * que no existe
     * @param tipoDeVisa tipo de visa que reemplaza al existente
     */
    public void setTipoDeVisa(Visa tipoDeVisa) {
        this.tipoDeVisa = tipoDeVisa;
    }

    /**
     * permite reemplazar la fecha de la primera visa guardada o agregarlo si es 
     * que no existe
     * @param primeraVisa fecha de la primera visa que reemplaza al existente
     */
    public void setPrimeraVisa(LocalDate primeraVisa) {
        this.primeraVisa = primeraVisa;
    }
    
    /**
     * permite comprobar que un pasaporte ingresado tenga un formato correcto para 
     * el registro civil y sea de largo mayor a 6 caracteres
     * @param str pasaporte a comprobar
     * @return true si es un pasaporte valido, false en caso contrario
     * @throws FormatoPasaporteException si se ingresan caracteres no alfanumericos o si no
     * comienza con una letra
     */
    public static boolean comprobarPasaporte(String str) throws FormatoPasaporteException{
        str = str.toUpperCase();
        if(FormatoPasaporteException.exception(str))
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