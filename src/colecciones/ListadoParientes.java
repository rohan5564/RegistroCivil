
package colecciones;

import Enums.EstadoCivil;
import java.util.List;


public class ListadoParientes {
    private List<Ciudadano> listadoParientes;

    public ListadoParientes(List<Ciudadano> listadoParientes) {
        this.listadoParientes = listadoParientes;
    }
    
    public List<Ciudadano> getListadoParientes() {
        return listadoParientes;
    }

    public void setListadoParientes(List<Ciudadano> listadoParientes) {
        this.listadoParientes = listadoParientes;
    }
    
    public int totalPorParentesco(){
        return listadoParientes.size();
    }
    
    public boolean contienePersona(Ciudadano persona){
        return listadoParientes.contains(persona);
    }
    
    public boolean contieneListadoPersonas(List<Ciudadano> personas){
        return listadoParientes.containsAll(personas);
    }
    
    public boolean agregar(Ciudadano persona){
        return listadoParientes.add(persona);
    }
    
    public boolean agregarListado(List<Ciudadano> persona){
        return listadoParientes.addAll(persona);
    }
    
    public boolean borrar(Ciudadano persona){
        return listadoParientes.remove(persona);
    }
    
    public boolean borrarListado(List<Ciudadano> persona){
        return listadoParientes.removeAll(persona);
    }
    
    public boolean existe(){
        return listadoParientes != null;
    }
    
    public boolean estaVacia(){
        return listadoParientes.isEmpty();
    }
    
    public void removerEstadoATodos(EstadoCivil estado){
        listadoParientes.forEach(persona->{
            persona.getEstadoCivil().remove(estado);
        });
    }
    
    public Ciudadano obtenerCiudadano(int index){
        return listadoParientes.get(index);
    }
}
