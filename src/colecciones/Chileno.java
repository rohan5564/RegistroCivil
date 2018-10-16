/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package colecciones;

import Enums.EstadoCivil;
import Interfaces.Chile;
import Interfaces.Chile.REGIONES;
import java.util.ArrayList;
import java.util.HashMap;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jean
 */

@XmlRootElement
public class Chileno extends Ciudadano{
    private String rut; //verificador 0-9 / 'K'
    private int numeroDeDocumento; //PERMITE 0
    private String direccion; //PERMITE NULL
    private String region;
    private String comuna;

    public Chileno(){
        super();
        rut = null;
        numeroDeDocumento = 0;
    }    
    
    @XmlElement
    public String getRut(){
        return rut;
    }
    
    public void setRut(String rut){
        this.rut = rut.toUpperCase();
    }

    @XmlElement
    public int getNumeroDeDocumento() {
        return numeroDeDocumento;
    }

    public void setNumeroDeDocumento(int numeroDeDocumento) {
        this.numeroDeDocumento = numeroDeDocumento;
    }
       
    @XmlElement
    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion.toUpperCase();
    }
    
    @XmlElement
    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @XmlElement
    public String getComuna() {
        return comuna;
    }

    public void setComuna(String comuna) {
        this.comuna = comuna;
    }
    
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
   
    /*funciones a implementar en el programa
    */
    
    @Override
    public boolean getRequisitosMinimos(){
        if(super.getNombre() != null
                && super.getApellido() != null
                && super.getSexo() != null
                && region != null
                && comuna != null
                && super.getNacimiento() != null
                && super.getHoraNacimiento() != null
                && super.getEstadoCivil() != null
                && rut != null
                )
            return true;
        return false;
    }
    
    @Override
    public boolean registrarNacimiento(){
        return getRequisitosMinimos() && comprobarRut(rut);
    }

    @Override
    public boolean registrarDefuncion(){
        return super.getDefuncion()!=null || super.getHoraDefuncion()!=null;
    }

    @Override
    public boolean registrarMatrimonio(){
        for(EstadoCivil estado:super.getEstadoCivil()){
            if(estado.equals(EstadoCivil.CASADO))
                return false;
        }
        return true;
    }
    
    @Override
    public boolean modificarDatos(){
        return false;
    }
    
    @Override
    public void agregarParientes(ArrayList<String> identificador, ArrayList<Ciudadano> pariente){
        HashMap<String, Ciudadano> parientes = super.getParientes();
        for(int i = 0; i < pariente.size(); i++){
            parientes.put(identificador.get(i), pariente.get(i));
        }
    }
    
}