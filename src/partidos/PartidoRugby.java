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
public class PartidoRugby extends Partido{
     //Variables de clase
    private int triesL;
    private int triesV;
    
    //Constructor de clase
    public PartidoRugby(String local, String visitante){
        //Cuando iniciamos un equipo tiene 0 en todo
        super(local, visitante);
        this.fecha = "";
        this.hora = "";
        this.golesL = -1;
        this.golesV = -1;
        this.pista = "";
        this.triesL = -1;//Creamos las casillas de los tries vacías
        this.triesV = -1;       
    }
    
    //Contructor de clase para cada partido al abrir archivo
    public PartidoRugby(String fecha, String hora, String local, int golesL, int golesV, String visitante, int triesL, int triesV, String pista){
        super(local, visitante);
        this.fecha = fecha;
        this.hora = hora;
        this.golesL = golesL;
        this.golesV = golesV;
        this.triesL = triesL;
        this.triesV = triesV;
        this.pista = pista;
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
    public int getGolesL() {
        return super.getGolesL(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getGolesV() {
        return super.getGolesV(); //To change body of generated methods, choose Tools | Templates.
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
    public void setGolesL(int golesL) {
        super.setGolesL(golesL); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setGolesV(int golesV) {
        super.setGolesV(golesV); //To change body of generated methods, choose Tools | Templates.
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
     * @return the triesL
     */
    public int getTriesL() {
        return triesL;
    }

    /**
     * @param triesL the triesL to set
     */
    public void setTriesL(int triesL) {
        this.triesL = triesL;
    }

    /**
     * @return the triesV
     */
    public int getTriesV() {
        return triesV;
    }

    /**
     * @param triesV the triesV to set
     */
    public void setTriesV(int triesV) {
        this.triesV = triesV;
    }
    
    /**
     * Método que se utiliza para rellenar las celdas de la tabla de jornadas
     * @return 
     */
    @Override
    public String[] getTableRepresentation(){        
        String[] rep = new String[9];
        rep[0] = fecha;
        rep[1] = hora;
        rep[2] = local;
        //Utilizamos el operador ternario para en caso de tner un valor nulo (-1) nos muestre la casilla vacía (por comodidad al rellenar)
        rep[3] = (golesL == -1) ? "" : valueOf(golesL);
        rep[4] = (golesV == -1) ? "" : valueOf(golesV);
        rep[5] = visitante;
        rep[6] = (triesL == -1) ? "" : valueOf(triesL);
        rep[7] = (triesV == -1) ? "" : valueOf(triesV);
        rep[8] = pista;
        return rep;
    }

    
}
