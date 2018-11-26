
package colecciones;

import Enums.EstadoCivil;
import java.util.ArrayList;
import java.util.List;


public class ListadoParientes {
    
    private List<Ciudadano> listadoParientes = null;

    public ListadoParientes(){
        listadoParientes = new ArrayList<>();
    }
    
    public ListadoParientes(List<Ciudadano> listadoParientes) {
        this.listadoParientes = listadoParientes;
    }
    
    public List<Ciudadano> getListadoParientes() {
        return listadoParientes;
    }

    public void setListadoParientes(List<Ciudadano> listadoParientes) {
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
    public boolean contienePersona(Ciudadano persona){
        return listadoParientes.contains(persona);
    }
    
    /**
     * @param personas listado de personas
     * @return true si contiene a todo el listado, false caso contrario
     */
    public boolean contieneListadoPersonas(List<Ciudadano> personas){
        return listadoParientes.containsAll(personas);
    }
    
    /**
     * @param persona persona a agregar
     * @return true si agrega, false caso contrario
     */
    public boolean agregar(Ciudadano persona){
        return listadoParientes.add(persona);
    }
    
    /**
     * @param persona listado de personas
     * @return  true si agrega al listado, false caso contrario
     */
    public boolean agregarListado(List<Ciudadano> persona){
        return listadoParientes.addAll(persona);
    }
    
    /**
     * @param persona persona a borrar
     * @return true si elimina a la persona del listado, false caso contrario
     */
    public boolean borrar(Ciudadano persona){
        return listadoParientes.remove(persona);
    }
    
    /**
     * @param persona listado de personas
     * @return  true si borra las personas, false en caso contrario
     */
    public boolean borrarListado(List<Ciudadano> persona){
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
        return listadoParientes.isEmpty();
    }
    
    /**
     * 
     * @param estado remueve el estados a todos
     */
    public void removerEstadoATodos(EstadoCivil estado){
        listadoParientes.forEach(persona->{
            persona.getEstadoCivil().remove(estado);
        });
    }
    /**
     * 
     * @param index posición del pariente a obtener
     * @return Ciudadano buscado 
     */
    public Ciudadano obtenerCiudadano(int index){
        return listadoParientes.get(index);
    }
}
