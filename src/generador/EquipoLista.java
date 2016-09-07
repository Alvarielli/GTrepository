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
 *
 * @author Hector
 */

/**
 * Hacemos que la clase EquipoLista sea una clase genérica, así le podremos pasar
 * diferentes tipos de objetos Equipo, EquipoBasket, etc. para que funcione con
 * los distintos deportes. <T> se usa por convenio para objetos
 * @author Alvarielli
 * @param <T> 
 */
public class EquipoLista<T> {
     //Creamos un ArrayList con los participantes del torneo
    private ArrayList <T> equipos = new ArrayList<>();       

    /**
     * @return the equipos
     */
    public ArrayList <T> getEquipos() {
        return equipos;
    }

    /**
     * @param equipos the equipos to set
     */
    public void setEquipos(ArrayList <T> equipos) {
        this.equipos = equipos;
    }
    
}
