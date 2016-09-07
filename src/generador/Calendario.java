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
 * @author Alvarielli
 */
public class Calendario {
     //Creamos un ArrayList con las jornadas del torneo
     public ArrayList <Jornada> jornadas = new ArrayList<>();
    
    /**
     * Método para añadir una jornada al ArrayList del Calendario
     * @param jornada 
     */
    public void añadirJornada(Jornada jornada){
        jornadas.add(jornada);
    }
    
    /**
     * Devuelve el ArrayList de jornadas del torneo correspondiente
     * @return 
     */
    public ArrayList<Jornada> getCalendario(){
        return jornadas;
    }
}
