/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import colecciones.Ciudadano;
import java.util.ArrayList;

/**
 *
 * @author Jean
 */
public interface Personas {
    boolean registrarNacimiento();
    boolean registrarDefuncion();
    boolean registrarMatrimonio();
    boolean modificarDatos();
    int getEdad();
    void agregarParientes(ArrayList<String> identificador, ArrayList<Ciudadano> pariente);
}