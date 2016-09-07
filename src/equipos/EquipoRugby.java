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
public class EquipoRugby extends Equipo{
    
    //Variables para la clase equipo
    private int posicion;//posicion en el ArrayList para la clasificación que se asignará desde fuera
    private int pj; //Partidos Jugados
    private int pg; //Partidos Ganados;
    private int pe; //Partidos Empatados;
    private int pp; //Partidos Perdidos
    private int pf; //Puntos a favor
    private int pc; //Puntos en contra
    private int tf; //Tries a favor
    private int tc; //Tries en contra
    //Bonus de Puntos que se añaden al total (+1 al marcar 4 o + tries) (+1 al perder de 7 o menos ptos)
    private int ptos; //Puntos que suma el equipo
    
    //Constructor de clase
    public EquipoRugby(int numero, String nombre){
        //Cuando iniciamos un equipo tiene 0 en todo
        super(numero, nombre);
        this.posicion = super.posicion;
        this.pj = 0;
        this.pg = 0;
        this.pe = 0;
        this.pp = 0;
        this.pf = 0;
        this.pc = 0;
        this.tf = 0;
        this.tc = 0;
        this.ptos = 0;
    }
    
    //Constructor de clase al abrir un archivo
    public EquipoRugby(int numero, String nombre, int posicion, int pj, int pg, int pe, int pp, int pf, int pc, int tf, int tc, int ptos){
        //Cuando iniciamos un equipo tiene 0 en todo
        super(numero, nombre);
        this.posicion = posicion;
        this.pj = pj;
        this.pg = pg;
        this.pe = pe;
        this.pp = pp;
        this.pf = pf;
        this.pc = pc;
        this.tf = tf;
        this.tc = tc;
        this.ptos = ptos;
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
     * @return the pf
     */
    public int getPf() {
        return pf;
    }

    /**
     * @param pf the pf to set
     */
    public void setPf(int pf) {
        this.pf = pf;
    }

    /**
     * @return the pc
     */
    public int getPc() {
        return pc;
    }

    /**
     * @param pc the pc to set
     */
    public void setPc(int pc) {
        this.pc = pc;
    }

    /**
     * @return the tf
     */
    public int getTf() {
        return tf;
    }

    /**
     * @param tf the tf to set
     */
    public void setTf(int tf) {
        this.tf = tf;
    }

    /**
     * @return the tc
     */
    public int getTc() {
        return tc;
    }

    /**
     * @param tc the tc to set
     */
    public void setTc(int tc) {
        this.tc = tc;
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
     * Devuelve la representación de la clasificación para cada filas.
     * @return Array de strings con los valores de una fila
     */
    @Override
    public String[] representarEquipo(){
        String[] rep = new String[11];
        rep[0] = valueOf(posicion + 1);//Sumamos 1 porque los índices empiezan en 0
        rep[1] = nombre;
        rep[2] = valueOf(pj);
        rep[3] = valueOf(pg);
        rep[4] = valueOf(pe);
        rep[5] = valueOf(pp);
        rep[6] = valueOf(pf);
        rep[7] = valueOf(pc);
        rep[8] = valueOf(tf);
        rep[9] = valueOf(tc);
        rep[10] = valueOf(ptos);
        return rep;
    }
}
