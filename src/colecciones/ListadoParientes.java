
package colecciones;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ListadoParientes {
    
    private ArrayList<String> listadoParientes = null;

    public ListadoParientes(){
        listadoParientes = new ArrayList<>();
    }
    
    public ListadoParientes(String listadoParientes) {
        this.listadoParientes = new ArrayList(Arrays.asList(listadoParientes));
    }
    
    public ListadoParientes(List<String> listadoParientes) {
        this.listadoParientes = new ArrayList(Arrays.asList(listadoParientes));
    }

    public List<String> getListadoParientes() {
        return listadoParientes;
    }

    public void setListadoParientes(ArrayList<String> listadoParientes) {
        this.listadoParientes = listadoParientes;
    }
    
    /**
     * @return tamaño del listado de parientes
     */
    public int totalPorParentesco(){
        return existe()?listadoParientes.size():0;
    }
    
    /**
     * @param persona persona a buscar
     * @return true si encuentra a la persona, false caso contrario
     */
    public boolean contienePersona(String persona){
        return existe()?listadoParientes.contains(persona):false;
    }
    
    /**
     * @param personas listado de personas
     * @return true si contiene a todo el listado, false caso contrario
     */
    public boolean contieneListadoPersonas(List<String> personas){
        return existe()?listadoParientes.containsAll(personas):false;
    }
    
    /**
     * @param persona persona a agregar
     * @return true si agrega, false caso contrario
     */
    public boolean agregar(String persona){
        return existe()?listadoParientes.add(persona):false;
    }
    
    /**
     * @param persona listado de personas
     * @return  true si agrega al listado, false caso contrario
     */
    public boolean agregarListado(List<String> persona){
        return existe()?listadoParientes.addAll(persona):false;
    }
    
    /**
     * @param persona persona a borrar
     * @return true si elimina a la persona del listado, false caso contrario
     */
    public boolean borrar(String persona){
        return existe()?listadoParientes.remove(persona):false;
    }
    
    /**
     * @param persona listado de personas
     * @return  true si borra las personas, false en caso contrario
     */
    public boolean borrarListado(List<String> persona){
        return existe()?listadoParientes.removeAll(persona):false;
    }
    
    /**
     * @return true si no es null, false caso contrario
     */
    public boolean existe(){
        return listadoParientes != null;
    }
    /**
     * @return true si no hay parientes, false caso contrario
     */
    public boolean estaVacia(){
        return !existe() || listadoParientes.isEmpty();
    }
    
    /**
     * 
     * @param index posición del pariente a obtener
     * @return Ciudadano buscado 
     */
    public String obtenerCiudadano(int index){
        return existe()?listadoParientes.get(index):null;
    }
    
}
