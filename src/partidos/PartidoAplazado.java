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
 * La función de este tipo de Partido es mostrar sus características en la tabla de partidos aplazados (funciona como clase adaptadora)
 * @author Alvarielli
 */
public class PartidoAplazado extends Partido{
    
    //Variables de clase
    private String aplazar;
    private int jornada;
    
    //Constructor de clase
    public PartidoAplazado(String local, String visitante){
        //Cuando iniciamos un equipo tiene 0 en todo
        super(local, visitante);
        this.jornada = 1;//valor inicial
        this.fecha = "";
        this.hora = "";
        this.pista = "";
        this.aplazar= "NO";
    }
    
    //Contructor de clase para cada partido al abrir archivo
    public PartidoAplazado(int jornada, String fecha, String hora, String local, String visitante, String pista, String aplazar){
        super(local, visitante);
        this.jornada = jornada;
        this.fecha = fecha;
        this.hora = hora;
        this.pista = pista;
        this.aplazar = aplazar;
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
     * @return the aplazar
     */
    public String getAplazar() {
        return aplazar;
    }

    /**
     * @param aplazar the aplazar to set
     */
    public void setAplazar(String aplazar) {
        this.aplazar = aplazar;
    }

    /**
     * @return the jornada
     */
    public int getJornada() {
        return jornada;
    }

    /**
     * @param jornada the jornada to set
     */
    public void setJornada(int jornada) {
        this.jornada = jornada;
    }
    
        /**
     * Método que se utiliza para rellenar las celdas de la tabla de jornadas
     * @return 
     */
    @Override
    public String[] getTableRepresentation(){        
        String[] rep = new String[7];
        rep[0] = valueOf(jornada);
        rep[1] = fecha;
        rep[2] = hora;
        rep[3] = local;
        rep[4] = visitante;
        rep[5] = pista;
        rep[6] = aplazar;
        return rep;
    }
    
    
    
}
