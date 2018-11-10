
package colecciones;

import Enums.EstadoCivil;
import Interfaces.Registro_Civil;

public class Chileno extends Ciudadano implements Registro_Civil{
    
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
     */
    public static boolean comprobarRut(String rut){
        rut = rut.toUpperCase();
        rut.replace(".","");
        rut.replace("-","");
        //se verifica que no posea caracteres distintos a 0..9-K
        if(rut.matches(".*[^1234567890K].*"))
            return false;
        int _rut = Integer.parseInt(rut.substring(0, rut.length()-1));
        char _verificador = rut.charAt(rut.length()-1);
        int total = 1, i = 0;
        while(_rut != 0){
            total = (total + _rut % 10 * (9 - i++ % 6)) % 11;
            _rut /= 10;
        }
        char verificador = (char)(total!=0 ? total+47 : 75);
        if(_verificador == verificador)
            return true;
        return false;
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
    
    /**
     * @return true si el Chileno puede registrarse, false en caso contrario
     */
    @Override
    public boolean registrar(){
        return getRequisitosMinimos() && comprobarRut(rut);
    }

    /**
     * @return true si puede registrarse la defuncion, false en caso contrario
     */
    @Override
    public boolean registrarDefuncion(){
        return super.getDefuncion()!=null;
    }

    /**
     * se rige bajo el principio de monogamia
     * @return true si puede registrarse un nuevo matrimonio con otra persona,
     * false en caso contrario
     */
    @Override
    public boolean registrarMatrimonio(){
        return super.getParientes().buscarListaParentesco(EstadoCivil.CASADO) == null
                || super.getParientes().buscarListaParentesco(EstadoCivil.CASADO).estaVacia();
    }
    
    /**
     * identificador por defecto para un chileno es el rut
     * @return identificador unico del chileno
     */
    @Override
    public String mostrarIdentificador(){
        return getRut();
    }
    
}