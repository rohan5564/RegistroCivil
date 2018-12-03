package colecciones;

import Enums.EstadoCivil;
import Excepciones.FormatoRutException;
import Excepciones.LongitudRutException;
import Interfaces.RegistroPersonal;

public class Chileno extends Ciudadano implements RegistroPersonal{
    
    private String rut; //verificador 0-9 / 'K'
    private String numeroDeDocumento; //PERMITE NULL
    private String direccion; //PERMITE NULL
    private String regionDeNacimiento;
    private String comunaDeNacimiento;

    public Chileno(){
        super();
        rut = null;
        numeroDeDocumento = null;
    }    
    
    public String getRut(){
        return rut;
    }
    
    public void setRut(String rut){
        this.rut = rut.toUpperCase();
    }

    public String getNumeroDeDocumento() {
        return numeroDeDocumento;
    }

    public void setNumeroDeDocumento(String numeroDeDocumento) {
        this.numeroDeDocumento = numeroDeDocumento;
    }
       
    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion.toUpperCase();
    }
    
    public String getRegionDeNacimiento() {
        return regionDeNacimiento;
    }

    public void setRegionDeNacimiento(String regionDeNacimiento) {
        this.regionDeNacimiento = regionDeNacimiento;
    }

    public String getComunaDeNacimiento() {
        return comunaDeNacimiento;
    }

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
        
        //se verifica que no posea un largo menor a 8 caracteres
        if(rut.length()<8 || rut.length()>9)
            throw new LongitudRutException();
        
        //se verifica que no posea caracteres distintos a 0..9-K
        if(rut.matches(".*[^1234567890K].*"))
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