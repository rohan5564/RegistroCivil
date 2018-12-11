
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
        
        public static Sexo valorDe(String str){
            return Sexo.valueOf(str.toUpperCase());
        }
}
