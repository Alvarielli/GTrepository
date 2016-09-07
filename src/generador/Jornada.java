/*
        GNU GENERAL PUBLIC LICENSE (GPL)
            Version 3, 29 June 2007
        Copyright (C) Héctor Álvarez Castellano

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>
*/
package generador;

import java.util.ArrayList;

/**
 * Hacemos que la clase Jornada sea una clase genérica, así le podremos pasar
 * diferentes tipos de objetos Partido para que funcione con
 * los distintos deportes. <T> se usa por convenio para objetos
 * @author Alvarielli
 * @param <T> 
 */
public class Jornada<T> {
    
    //Creamos un ArrayList con los partidos de cada jornada
    public ArrayList <T> partidos = new ArrayList<>();
    //Guarda el número de jornada
    private int num_jornada;
    
    public Jornada(int num_jornada){
        this.num_jornada = num_jornada;
    }
    
    /**
     * Método para añadir un partido al ArrayList de partidos de la joranda correspondiente
     * @param partido 
     */
    public void añadirPartido(T partido){
        partidos.add(partido);
    }
    
    /**
     * Devuelve el ArrayList de partidos de la joranda correspondiente
     * @return 
     */
    public ArrayList<T> getListaPartidos(){
        return partidos;
    }

    /**
     * @return the num_jornada
     */
    public int getNum_jornada() {
        return num_jornada;
    }

    /**
     * @param num_jornada the num_jornada to set
     */
    public void setNum_jornada(int num_jornada) {
        this.num_jornada = num_jornada;
    }
}
