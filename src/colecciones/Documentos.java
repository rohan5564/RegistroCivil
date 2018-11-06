
package colecciones;

import java.util.LinkedHashMap;
import java.util.Map;


interface Documentos { //generar PDF's para imprimir
    void nacimiento();
    void matrimonio();
    void defuncion();
    void antecedentes();
}

interface Antecedentes {
    void agregarAntecedente(String rut, String contenido);
    void modificarAntecedente(String rut, int NumeroAntecedente); //Integer.parseInt() para buscar en el LinkedHashMap
    void eliminarAntecedente(String rut, int NumeroAntecedente); //Integer.parseInt() para buscar en el LinkedHashMap
    LinkedHashMap<Integer, String> antecedentes(String rutInfractor); //LinkedHashMap, posible HBOX para mostrar en GUI. string a Integer
    int totalAntecedentes(Map<Integer, String> antecedentes);
}