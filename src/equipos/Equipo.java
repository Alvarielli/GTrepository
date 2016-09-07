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
package equipos;

import static java.lang.String.valueOf;

/**
 *
 * @author Alvarielli
 */

//Creamos esta clase abstracta como base para el resto de clases Equipo de cada Deporte
public abstract class Equipo {
    
    //Variables para la clase equipo
    protected String nombre; //Nombre del equipo
    protected int numero;//Número en el sorteo (en el que se ha insertado, se podrá utilizar para equipos opuestos)
    protected int posicion;//posicion en el ArrayList para la clasificación que se asignará desde fuera
    
    //Constructor de clase
    public Equipo(int numero, String nombre){
        //Cuando iniciamos un equipo tiene 0 en todo
        this.numero = numero;
        this.nombre = nombre;
        this.posicion = 0;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
       
    /**
     * @return the numero
     */
    public int getNumero() {
        return numero;
    }

    /**
     * @param numero the numero to set
     */
    public void setNumero(int numero) {
        this.numero = numero;
    }
        
    /**
     * @return the posicion
     */
    public int getPosicion() {
        return posicion;
    }

    /**
     * @param posicion the posicion to set
     */
    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }
    
    /**
     * Devuelve la representación de la clasificación para cada filas.
     * @return Array de strings con los valores de una fila
     */
    public String[] representarEquipo(){
        String[] rep = new String[9];
        rep[0] = valueOf(posicion + 1);//Sumamos 1 porque los índices empiezan en 0
        rep[1] = nombre;
        return rep;
    }

}
