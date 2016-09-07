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
 * Partido base para las eliminatorias de Fútbol
 * @author Alvarielli
 */
public class PartidoEliminatoriaFutbol extends PartidoEliminatoria{
    
    //Variables de clase (se podrían usar los de la superclase también)
    private int golesL;
    private int golesV;
    
    //Constructor de clase
    public PartidoEliminatoriaFutbol(String local, String visitante){
        //Cuando iniciamos un equipo tiene 0 en todo
        super(local, visitante);
        this.fecha = super.getFecha();
        this.hora = super.getHora();
        this.golesL = -1;//super.getGolesL();//Hereda de Partido (clase abuela)
        this.golesV = -1;//super.getGolesV();//Hereda de Partido (clase abuela)
        this.pista = super.getPista(); 
        this.ganador = super.getGanador();
        this.desempate = super.getDesempate();
    }
    
    //Contructor de clase para cada partido al abrir archivo
    public PartidoEliminatoriaFutbol(String fecha, String hora, String local, int golesL, int golesV, String visitante, String pista, String ganador, String desempate){
        super(local, visitante);
        this.fecha = fecha;
        this.hora = hora;
        this.golesL = golesL;
        this.golesV = golesV;
        this.pista = pista;
        this.ganador = ganador;
        this.desempate = desempate;
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
     * @return the ganador
     */
    @Override
    public String getGanador() {
        return super.getGanador();
    }

    /**
     * @param ganador the ganador to set
     */
    @Override
    public void setGanador(String ganador) {
        super.setGanador(ganador);
    }

    /**
     * @return the desempate
     */
    @Override
    public String getDesempate() {
        return super.getDesempate();
    }

    /**
     * @param desempate the desempate to set
     */
    @Override
    public void setDesempate(String desempate) {
        super.setDesempate(desempate);
    }

    /**
     * @return the golesL
     */
    @Override
    public int getGolesL() {
        return golesL;
    }

    /**
     * @param golesL the golesL to set
     */
    @Override
    public void setGolesL(int golesL) {
        this.golesL = golesL;
    }

    /**
     * @return the golesV
     */
    @Override
    public int getGolesV() {
        return golesV;
    }

    /**
     * @param golesV the golesV to set
     */
    @Override
    public void setGolesV(int golesV) {
        this.golesV = golesV;
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
        rep[3] = (getGolesL() == -1) ? "" : valueOf(getGolesL());
        rep[4] = (getGolesV() == -1) ? "" : valueOf(getGolesV());
        rep[5] = visitante;
        rep[6] = pista;
        rep[7] = ganador;
        rep[8] = desempate;
        return rep;
    }
}

