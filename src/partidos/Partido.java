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
public class Partido {
    
    //Variables que requiere cada partido
    protected String fecha; //Guarda el día del partido
    protected String hora; //Guarda la hora del partido
    protected String local; //Nombre del equipo local
    protected int golesL; //Goles del equipo local en el resultado
    protected int golesV;//Goles del equipo visitante en el resultado
    protected String visitante; //Nombre del equipo visitante
    protected String pista;//Pista en la que se jugará el partido
    
    //Contructor de clase para cada partido
    public Partido(String local, String visitante){
        this.fecha = "";
        this.hora = "";
        this.local = local;
        this.golesL = -1;
        this.golesV = -1;
        this.visitante = visitante;
        this.pista = "";
    }
    
    //Contructor de clase para cada partido al abrir archivo
    public Partido(String fecha, String hora, String local, int golesL, int golesV, String visitante, String pista){
        this.fecha = fecha;
        this.hora = hora;
        this.local = local;
        this.golesL = golesL;
        this.golesV = golesV;
        this.visitante = visitante;
        this.pista = pista;
    }
    
    /**
     * @return the fecha
     */
    public String getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the hora
     */
    public String getHora() {
        return hora;
    }

    /**
     * @param hora the hora to set
     */
    public void setHora(String hora) {
        this.hora = hora;
    }

    /**
     * @return the local
     */
    public String getLocal() {
        return local;
    }

    /**
     * @param local the local to set
     */
    public void setLocal(String local) {
        this.local = local;
    }

    /**
     * @return the golesL
     */
    public int getGolesL() {
        return golesL;
    }

    /**
     * @param golesL the golesL to set
     */
    public void setGolesL(int golesL) {
        this.golesL = golesL;
    }

    /**
     * @return the golesV
     */
    public int getGolesV() {
        return golesV;
    }

    /**
     * @param golesV the golesV to set
     */
    public void setGolesV(int golesV) {
        this.golesV = golesV;
    }

    /**
     * @return the visitante
     */
    public String getVisitante() {
        return visitante;
    }

    /**
     * @param visitante the visitante to set
     */
    public void setVisitante(String visitante) {
        this.visitante = visitante;
    }

    /**
     * @return the pista
     */
    public String getPista() {
        return pista;
    }

    /**
     * @param pista the pista to set
     */
    public void setPista(String pista) {
        this.pista = pista;
    }   
    
    /**
     * Método que se utiliza para rellenar las celdas de la tabla de jornadas
     * @return 
     */
    public String[] getTableRepresentation(){        
        String[] rep = new String[7];
        rep[0] = fecha;
        rep[1] = hora;
        rep[2] = local;
        //Utilizamos el operador ternario para en caso de tner un valor nulo (-1) nos muestre la casilla vacía (por comodidad al rellenar)
        rep[3] = (golesL == -1) ? "" : valueOf(golesL);
        rep[4] = (golesV == -1) ? "" : valueOf(golesV);
        rep[5] = visitante;
        rep[6] = pista;
        return rep;
    }
}
