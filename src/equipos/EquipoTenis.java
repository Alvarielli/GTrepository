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
 * Válido también para Badminton, Tenis de Mesa, Frontenis, Padel, Voley Playa
 * @author Alvarielli
 */
public class EquipoTenis extends Equipo {
       
    //Variables para la clase equipo
    private int posicion;//posicion en el ArrayList para la clasificación que se asignará desde fuera
    private int pj; //Partidos Jugados
    private int pg; //Partidos Ganados;
    private int pp; //Partidos Perdidos
    private int sf; //Sets a favor
    private int sc; //Sets en contra
    private int jf; //Juegos a favor
    private int jc; //Juegos en contra
    
    //Constructor de clase
    public EquipoTenis(int numero, String nombre){
        //Cuando iniciamos un equipo tiene 0 en todo
        super(numero, nombre);
        this.posicion = super.posicion;
        this.pj = 0;
        this.pg = 0;
        this.pp = 0;
        this.sf = 0;
        this.sc = 0;
        this.jf = 0;
        this.jc = 0;
    }
    
    //Constructor de clase al abrir un archivo
    public EquipoTenis(int numero, String nombre, int posicion, int pj, int pg, int pp, int sf, int sc, int jf, int jc){
        //Cuando iniciamos un equipo tiene 0 en todo
        super(numero, nombre);
        this.posicion = posicion;
        this.pj = pj;
        this.pg = pg;
        this.pp = pp;
        this.sf = sf;
        this.sc = sc;
        this.jf = jf;
        this.jc = jc;
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
     * @return the jf
     */
    public int getJf() {
        return jf;
    }

    /**
     * @param jf the jf to set
     */
    public void setJf(int jf) {
        this.jf = jf;
    }

    /**
     * @return the jc
     */
    public int getJc() {
        return jc;
    }

    /**
     * @param jc the jc to set
     */
    public void setJc(int jc) {
        this.jc = jc;
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
        rep[4] = valueOf(pp);
        rep[5] = valueOf(sf);
        rep[6] = valueOf(sc);
        rep[7] = valueOf(jf);
        rep[8] = valueOf(jc);
        return rep;
    }
}
