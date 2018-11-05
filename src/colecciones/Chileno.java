/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package colecciones;

import Enums.EstadoCivil;
import Interfaces.Registro_Civil;

/**
 *
 * @author Jean
 */

public class Chileno extends Ciudadano implements Registro_Civil{
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
    
    public String getRut(){
        return rut;
    }
    
    public void setRut(String rut){
        this.rut = rut.toUpperCase();
    }

    public int getNumeroDeDocumento() {
        return numeroDeDocumento;
    }

    public void setNumeroDeDocumento(int numeroDeDocumento) {
        this.numeroDeDocumento = numeroDeDocumento;
    }
       
    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion.toUpperCase();
    }
    
    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

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
    
    @Override
    public boolean getRequisitosMinimos(){
        return super.getNombre() != null
                && super.getApellido() != null
                && super.getSexo() != null
                && region != null
                && comuna != null
                && super.getNacimiento() != null
                && super.getHoraNacimiento() != null
                && super.getEstadoCivil() != null
                && rut != null;
    }
    
    @Override
    public boolean registrarNacimiento(){
        return getRequisitosMinimos() && comprobarRut(rut);
    }

    @Override
    public boolean registrarDefuncion(){
        return super.getDefuncion()!=null;
    }

    @Override
    public boolean registrarMatrimonio(){
        return super.getParientes().buscarListaParentesco(EstadoCivil.CASADO) == null
                || super.getParientes().buscarListaParentesco(EstadoCivil.CASADO).isEmpty();
    }
    
    @Override
    public String mostrarIdentificador(){
        return getRut();
    }
    
}