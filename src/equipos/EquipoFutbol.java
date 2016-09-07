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
 * Válido también para fútbol sala, balomnano, waterpolo, hockey patines, hockey hierba, fútbol americano
 * @author Alvarielli
 */

public class EquipoFutbol extends Equipo{
    
    //Variables para la clase equipo
    private int posicion;//posicion en el ArrayList para la clasificación que se asignará desde fuera
    private int pj; //Partidos Jugados
    private int pg; //Partidos Ganados;
    private int pe; //Partidos Empatados;
    private int pp; //Partidos Perdidos
    private int gf; //Goles a favor
    private int gc; //Goles en contra
    private int ptos; //Puntos que suma el equipo
    
    //Constructor de clase
    public EquipoFutbol(int numero, String nombre){
        //Cuando iniciamos un equipo tiene 0 en todo
        super(numero, nombre);
        this.posicion = super.posicion;
        this.pj = 0;
        this.pg = 0;
        this.pe = 0;
        this.pp = 0;
        this.gf = 0;
        this.gc = 0;
        this.ptos = 0;
    }
    
    //Constructor de clase para abrir archivo
    public EquipoFutbol(int numero, String nombre, int posicion, int pj, int pg, int pe, int pp, int gf, int gc, int ptos){
        super(numero, nombre);//si no dejo este da error con Equipo(abstracta)
        this.numero = numero;
        this.nombre = nombre;
        this.posicion = posicion;
        this.pj = pj;
        this.pg = pg;
        this.pe = pe;
        this.pp = pp;
        this.gf = gf;
        this.gc = gc;
        this.ptos = ptos;
    }

    /**
     * @return the nombre
     */
    @Override
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    @Override
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the pj
     */
    public int getPj() {
        return pj;
    }

    /**
     * @param pj the pj to set
     */
    public void setPj(int pj) {
        this.pj = pj;
    }

    /**
     * @return the pg
     */
    public int getPg() {
        return pg;
    }

    /**
     * @param pg the pg to set
     */
    public void setPg(int pg) {
        this.pg = pg;
    }

    /**
     * @return the pe
     */
    public int getPe() {
        return pe;
    }

    /**
     * @param pe the pe to set
     */
    public void setPe(int pe) {
        this.pe = pe;
    }

    /**
     * @return the pp
     */
    public int getPp() {
        return pp;
    }

    /**
     * @param pp the pp to set
     */
    public void setPp(int pp) {
        this.pp = pp;
    }

    /**
     * @return the gf
     */
    public int getGf() {
        return gf;
    }

    /**
     * @param gf the gf to set
     */
    public void setGf(int gf) {
        this.gf = gf;
    }

    /**
     * @return the gc
     */
    public int getGc() {
        return gc;
    }

    /**
     * @param gc the gc to set
     */
    public void setGc(int gc) {
        this.gc = gc;
    }

    /**
     * @return the ptos
     */
    public int getPtos() {
        return ptos;
    }

    /**
     * @param ptos the ptos to set
     */
    public void setPtos(int ptos) {
        this.ptos = ptos;
    }
       
    /**
     * @return the numero
     */
    @Override
    public int getNumero() {
        return numero;
    }

    /**
     * @param numero the numero to set
     */
    @Override
    public void setNumero(int numero) {
        this.numero = numero;
    }
        
    /**
     * @return the posicion
     */
    @Override
    public int getPosicion() {
        return posicion;
    }

    /**
     * @param posicion the posicion to set
     */
    @Override
    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }
    
    /**
     * Devuelve la representación de la clasificación para cada filas.
     * @return Array de strings con los valores de una fila
     */
    @Override
    public String[] representarEquipo(){
        String[] rep = new String[9];
        rep[0] = valueOf(posicion + 1);//Sumamos 1 porque los índices empiezan en 0
        rep[1] = nombre;
        rep[2] = valueOf(pj);
        rep[3] = valueOf(pg);
        rep[4] = valueOf(pe);
        rep[5] = valueOf(pp);
        rep[6] = valueOf(gf);
        rep[7] = valueOf(gc);
        rep[8] = valueOf(ptos);
        return rep;
    }

}
