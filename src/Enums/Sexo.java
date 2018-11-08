
package Enums;


public enum Sexo{
        FEMENINO("femenino"), MASCULINO("masculino");
        
        private String nombre;
        
        private Sexo(String nombre){
            this.nombre = nombre;
        }
        
        public String getNombre(){
            return nombre;
        }
}
