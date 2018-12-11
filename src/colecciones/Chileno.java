package colecciones;

import Enums.EstadoCivil;
import Excepciones.FormatoRutException;
import Excepciones.LongitudRutException;
import Interfaces.RegistroPersonal;

public class Chileno extends Ciudadano<Chileno> implements RegistroPersonal{
    
    /**
     * PARTIR POR ATRIBUTOS PROPIOS DEL CIUDADANO CHILENO
     */
    public static class BuilderChileno extends Ciudadano.Builder<Chileno>{
        private String rut = null; //verificador 0-9 / 'K'
        private String numeroDeDocumento = null; //PERMITE NULL
        private String direccion = null; //PERMITE NULL
        private String regionDeNacimiento = null;
        private String comunaDeNacimiento = null;
        
        public BuilderChileno setRut(String rut){
            this.rut = rut.toUpperCase();
            return this;
        }
        public BuilderChileno setNumeroDeDocumento(String numeroDeDocumento) {
            if(numeroDeDocumento!=null)
                this.numeroDeDocumento = numeroDeDocumento;
            return this;
        }
        public BuilderChileno setDireccion(String direccion) {
            if(direccion!=null)
                this.direccion = direccion.toUpperCase();
            return this;
        }
        public BuilderChileno setRegionDeNacimiento(String regionDeNacimiento) {
            this.regionDeNacimiento = regionDeNacimiento;
            return this;
        }
        public BuilderChileno setComunaDeNacimiento(String comunaDeNacimiento) {
            this.comunaDeNacimiento = comunaDeNacimiento;
            return this;
        }
        
        @Override
        public Chileno build(){
            return new Chileno(this);
        }
    }
    
    private String rut; //verificador 0-9 / 'K'
    private String numeroDeDocumento; //PERMITE NULL
    private String direccion; //PERMITE NULL
    private String regionDeNacimiento;
    private String comunaDeNacimiento;

    
    private Chileno(final BuilderChileno builder){
        super(builder);
        this.rut = builder.rut;
        this.numeroDeDocumento = builder.numeroDeDocumento;
        this.direccion = builder.direccion;
        this.regionDeNacimiento = builder.regionDeNacimiento;
        this.comunaDeNacimiento = builder.comunaDeNacimiento;
    }    
    
    public String getRut(){
        return rut;
    }

    public String getNumeroDeDocumento() {
        return numeroDeDocumento;
    }
       
    public String getDireccion() {
        return direccion;
    }
        
    public String getRegionDeNacimiento() {
        return regionDeNacimiento;
    }

    public String getComunaDeNacimiento() {
        return comunaDeNacimiento;
    }

    /**
     * permite reemplazar el numero de documento guardado o agregarlo si es 
     * que no existe
     * @param numeroDeDocumento numero de documento que reemplaza al existente
     */
    public void setNumeroDeDocumento(String numeroDeDocumento) {
        this.numeroDeDocumento = numeroDeDocumento;
    }

    /**
     * permite reemplazar la direccion guardada o agregarlo si es 
     * que no existe
     * @param direccion direccion que reemplaza al existente
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * permite reemplazar la region de nacimiento guardada o agregarlo si es 
     * que no existe
     * @param regionDeNacimiento region de nacimiento que reemplaza al existente
     */
    public void setRegionDeNacimiento(String regionDeNacimiento) {
        this.regionDeNacimiento = regionDeNacimiento;
    }

    /**
     * permite reemplazar la comuna de nacimiento guardada o agregarlo si es 
     * que no existe
     * @param comunaDeNacimiento comuna de nacimiento que reemplaza al existente
     */
    public void setComunaDeNacimiento(String comunaDeNacimiento) {
        this.comunaDeNacimiento = comunaDeNacimiento;
    }
    
    /**
     * permite comprobar si un rut ingresado es valido, para ello solo puede poseer 
     * numeros y el caracter 'K' y tambien debe cumplir con el algoritmo ingresado
     * @param rut rut a comprobar     
     * @return true si es un rut valido, false en caso contrario
     * @throws FormatoRutException
     * @throws LongitudRutException 
     */
    public static boolean comprobarRut(String rut) throws FormatoRutException, LongitudRutException{
        rut = rut.toUpperCase().replace(".","").replace("-","");
        
        if(LongitudRutException.exception(rut))
            throw new LongitudRutException();
        
        if(FormatoRutException.exception(rut))
            throw new FormatoRutException();
        
        //se separa el rut y el verificador
        int _rut = Integer.parseInt(rut.substring(0, rut.length()-1));
        char verificador = rut.charAt(rut.length()-1);
        
        //algoritmo para comprobar el digito verificador
        int total = 1, i = 0;
        while(_rut != 0){
            total = (total+_rut%10*(9-i++%6)) % 11;
            _rut /= 10;
        }
        char _verificador = (char)(total!=0 ? total+47 : 75);
        
        //se comprueba el rut en base al digito verificador ingresado con el calculado
        return _verificador==verificador;
    }
    
    /**
     * comprueba que el Chileno cumpla los requisitos minimos de cuanto a su 
     * informacion para ingresarlo al sistema
     * @return true si cumple con los requisitos de ingreso minimos, false en 
     * caso contrario
     */
    @Override
    public boolean getRequisitosMinimos(){
        return super.getNombre() != null
                && super.getApellido() != null
                && super.getSexo() != null
                && regionDeNacimiento != null
                && comunaDeNacimiento != null
                && super.getNacimiento() != null
                && super.getHoraNacimiento() != null
                && super.getEstadoCivil() != null
                && rut != null;
    }

    /***************************************************************************
     *                  interfaz: RegistroPersonal                             *
     **************************************************************************/
    @Override
    public boolean registrarDefuncion(){
        return super.getDefuncion()!=null;
    }

    @Override
    public boolean registrarMatrimonio(){
        return super.getParientes().buscarListaParentesco(EstadoCivil.CASADO) == null
                || super.getParientes().buscarListaParentesco(EstadoCivil.CASADO).estaVacia();
    }
    
    /***************************************************************************
     *                  clase abstracta: Ciudadano                             *
     **************************************************************************/
    @Override
    public String mostrarIdentificador(){
        return getRut();
    }
}