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
package partidos;

import static java.lang.String.valueOf;

/**
 *
 * @author Alvarielli
 */
public class PartidoTenis5Sets extends Partido{
    
    //Variables de clase
    private int set1L;
    private int set1V;
    private int set2L;
    private int set2V;
    private int set3L;
    private int set3V;
    private int set4L;
    private int set4V;
    private int set5L;
    private int set5V;
    
    public PartidoTenis5Sets(String local, String visitante){
        super(local, visitante);
        this.fecha = "";
        this.hora = "";
        this.pista = "";
        //Creamos las casillas de los sets vacías
        this.set1L = -1;
        this.set1V = -1;
        this.set2L = -1;
        this.set2V = -1; 
        this.set3L = -1;
        this.set3V = -1;
        this.set4L = -1;
        this.set4V = -1; 
        this.set5L = -1;
        this.set5V = -1;

    }
    
     //Contructor de clase para cada partido al abrir archivo
    public PartidoTenis5Sets(String fecha, String hora, String local, int set1L, int set1V, int set2L, int set2V, int set3L, int set3V, 
            int set4L, int set4V, int set5L, int set5V, String visitante, String pista){
        super(local, visitante);
        this.fecha = fecha;
        this.hora = hora;
        this.pista = pista;
        this.set1L = set1L;
        this.set1V = set1V;
        this.set2L = set2L;
        this.set2V = set2V; 
        this.set3L = set3L;
        this.set3V = set3V; 
        this.set4L = set4L;
        this.set4V = set4V; 
        this.set5L = set5L;
        this.set5V = set5V; 
    }

    @Override
    public String getFecha() {
        return super.getFecha(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getHora() {
        return super.getHora(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getLocal() {
        return super.getLocal(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getVisitante() {
        return super.getVisitante(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getPista() {
        return super.getPista(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setFecha(String fecha) {
        super.setFecha(fecha); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setHora(String hora) {
        super.setHora(hora); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setLocal(String local) {
        super.setLocal(local); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setVisitante(String visitante) {
        super.setVisitante(visitante); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setPista(String pista) {
        super.setPista(pista); //To change body of generated methods, choose Tools | Templates.
    }
    
        /**
     * @return the set1L
     */
    public int getSet1L() {
        return set1L;
    }

    /**
     * @param set1L the set1L to set
     */
    public void setSet1L(int set1L) {
        this.set1L = set1L;
    }

    /**
     * @return the set1V
     */
    public int getSet1V() {
        return set1V;
    }

    /**
     * @param set1V the set1V to set
     */
    public void setSet1V(int set1V) {
        this.set1V = set1V;
    }

    /**
     * @return the set2L
     */
    public int getSet2L() {
        return set2L;
    }

    /**
     * @param set2L the set2L to set
     */
    public void setSet2L(int set2L) {
        this.set2L = set2L;
    }

    /**
     * @return the set2V
     */
    public int getSet2V() {
        return set2V;
    }

    /**
     * @param set2V the set2V to set
     */
    public void setSet2V(int set2V) {
        this.set2V = set2V;
    }

    /**
     * @return the set3L
     */
    public int getSet3L() {
        return set3L;
    }

    /**
     * @param set3L the set3L to set
     */
    public void setSet3L(int set3L) {
        this.set3L = set3L;
    }

    /**
     * @return the set3V
     */
    public int getSet3V() {
        return set3V;
    }

    /**
     * @param set3V the set3V to set
     */
    public void setSet3V(int set3V) {
        this.set3V = set3V;
    }
    
    /**
     * @return the set4L
     */
    public int getSet4L() {
        return set4L;
    }

    /**
     * @param set4L the set4L to set
     */
    public void setSet4L(int set4L) {
        this.set4L = set4L;
    }

    /**
     * @return the set4V
     */
    public int getSet4V() {
        return set4V;
    }

    /**
     * @param set4V the set4V to set
     */
    public void setSet4V(int set4V) {
        this.set4V = set4V;
    }

    /**
     * @return the set5L
     */
    public int getSet5L() {
        return set5L;
    }

    /**
     * @param set5L the set5L to set
     */
    public void setSet5L(int set5L) {
        this.set5L = set5L;
    }

    /**
     * @return the set5V
     */
    public int getSet5V() {
        return set5V;
    }

    /**
     * @param set5V the set5V to set
     */
    public void setSet5V(int set5V) {
        this.set5V = set5V;
    }
    
    /**
     * Método que se utiliza para rellenar las celdas de la tabla de jornadas
     * @return 
     */
    @Override
    public String[] getTableRepresentation(){        
        String[] rep = new String[15];
        rep[0] = fecha;
        rep[1] = hora;
        rep[2] = local;
        //Utilizamos el operador ternario para en caso de tner un valor nulo (-1) nos muestre la casilla vacía (por comodidad al rellenar)
        rep[3] = (set1L == -1) ? "" : valueOf(set1L);
        rep[4] = (set1V == -1) ? "" : valueOf(set1V);
        rep[5] = (set2L == -1) ? "" : valueOf(set2L);
        rep[6] = (set2V == -1) ? "" : valueOf(set2V);
        rep[7] = (set3L == -1) ? "" : valueOf(set3L);
        rep[8] = (set3V == -1) ? "" : valueOf(set3V);
        rep[9] = (set4L == -1) ? "" : valueOf(set4L);
        rep[10] = (set4V == -1) ? "" : valueOf(set4V);
        rep[11] = (set5L == -1) ? "" : valueOf(set5L);
        rep[12] = (set5V == -1) ? "" : valueOf(set5V);
        rep[13] = visitante;
        rep[14] = pista;
        return rep;
    }

    
}
