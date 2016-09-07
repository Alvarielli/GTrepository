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

import equipos.Equipo;
import static java.lang.String.valueOf;

/**
 *
 * @author Alvarielli
 */
public class EquipoVoleibol extends Equipo{
    
    //Variables para la clase equipo
    private int posicion;//posicion en el ArrayList para la clasificación que se asignará desde fuera
    private int pj; //Partidos Jugados
    private int pg; //Partidos Ganados;
    private int pp; //Partidos Perdidos
    private int sf; //Sets a favor
    private int sc; //Sets en contra
    private int pf; //Puntos a favor
    private int pc; //Puntos en contra
    private int ptos; //Puntos en la general
    
    //Constructor de clase
    public EquipoVoleibol(int numero, String nombre){
        //Cuando iniciamos un equipo tiene 0 en todo
        super(numero, nombre);
        this.posicion = super.posicion;
        this.pj = 0;
        this.pg = 0;
        this.pp = 0;
        this.sf = 0;
        this.sc = 0;
        this.pf = 0;
        this.pc = 0;
        this.ptos = 0;
    }
    
    //Constructor de clase al abrir un archivo
    public EquipoVoleibol(int numero, String nombre, int posicion, int pj, int pg, int pp, int sf, int sc, int pf, int pc, int ptos){
        //Cuando iniciamos un equipo tiene 0 en todo
        super(numero, nombre);
        this.posicion = posicion;
        this.pj = pj;
        this.pg = pg;
        this.pp = pp;
        this.sf = sf;
        this.sc = sc;
        this.pf = pf;
        this.pc = pc;
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
     * @return the sf
     */
    public int getSf() {
        return sf;
    }

    /**
     * @param sf the sf to set
     */
    public void setSf(int sf) {
        this.sf = sf;
    }

    /**
     * @return the sc
     */
    public int getSc() {
        return sc;
    }

    /**
     * @param sc the sc to set
     */
    public void setSc(int sc) {
        this.sc = sc;
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
        String[] rep = new String[10];
        rep[0] = valueOf(posicion + 1);//Sumamos 1 porque los índices empiezan en 0
        rep[1] = nombre;
        rep[2] = valueOf(pj);
        rep[3] = valueOf(pg);
        rep[4] = valueOf(pp);
        rep[5] = valueOf(sf);
        rep[6] = valueOf(sc);
        rep[7] = valueOf(pf);
        rep[8] = valueOf(pc);
        rep[9] = valueOf(ptos);
        return rep;
    }
}
