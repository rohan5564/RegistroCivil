
package colecciones;

import java.util.ArrayList;
import java.util.List;


public class ListadoParientes {
    
    private List<String> listadoParientes = null;

    public ListadoParientes(){
        listadoParientes = new ArrayList<>();
    }
    
    public ListadoParientes(List<String> listadoParientes) {
        this.listadoParientes = listadoParientes;
    }

    public List<String> getListadoParientes() {
        return listadoParientes;
    }

    public void setListadoParientes(List<String> listadoParientes) {
        this.listadoParientes = listadoParientes;
    }
    
    /**
     * @return tamaño del listado de parientes
     */
    public int totalPorParentesco(){
        return listadoParientes.size();
    }
    
    /**
     * @param persona persona a buscar
     * @return true si encuentra a la persona, false caso contrario
     */
    public boolean contienePersona(String persona){
        return listadoParientes.contains(persona);
    }
    
    /**
     * @param personas listado de personas
     * @return true si contiene a todo el listado, false caso contrario
     */
    public boolean contieneListadoPersonas(List<String> personas){
        return listadoParientes.containsAll(personas);
    }
    
    /**
     * @param persona persona a agregar
     * @return true si agrega, false caso contrario
     */
    public boolean agregar(String persona){
        return listadoParientes.add(persona);
    }
    
    /**
     * @param persona listado de personas
     * @return  true si agrega al listado, false caso contrario
     */
    public boolean agregarListado(List<String> persona){
        return listadoParientes.addAll(persona);
    }
    
    /**
     * @param persona persona a borrar
     * @return true si elimina a la persona del listado, false caso contrario
     */
    public boolean borrar(String persona){
        return listadoParientes.remove(persona);
    }
    
    /**
     * @param persona listado de personas
     * @return  true si borra las personas, false en caso contrario
     */
    public boolean borrarListado(List<String> persona){
        return listadoParientes.removeAll(persona);
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
        return listadoParientes.get(index);
    }
    
}
