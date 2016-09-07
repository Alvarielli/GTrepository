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
public class PartidoAjedrez extends Partido{
        
    //Variables de clase
    private double blancas;
    private double negras;
    
    //Constructor de clase
    public PartidoAjedrez(String local, String visitante){
        //Cuando iniciamos un equipo tiene 0 en todo
        super(local, visitante);
        this.fecha = "";
        this.hora = "";
        this.blancas = -1;
        this.negras = -1;
        this.pista = "";     
    }
   
    //Contructor de clase para cada partido al abrir archivo
    public PartidoAjedrez(String fecha, String hora, String local, double blancas, double negras, String visitante, String pista){
        super(local, visitante);
        this.fecha = fecha;
        this.hora = hora;
        this.blancas = blancas;
        this.negras = negras;
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
     * @return the blancas
     */
    public double getBlancas() {
        return blancas;
    }

    /**
     * @param blancas the blancas to set
     */
    public void setBlancas(double blancas) {
        this.blancas = blancas;
    }

    /**
     * @return the Negras
     */
    public double getNegras() {
        return negras;
    }

    /**
     * @param negras the negras to set
     */
    public void setNegras(double negras) {
        this.negras = negras;
    }
   
    /**
     * Método que se utiliza para rellenar las celdas de la tabla de jornadas
     * @return 
     */
    @Override
    public String[] getTableRepresentation(){        
        String[] rep = new String[7];
        rep[0] = fecha;
        rep[1] = hora;
        rep[2] = local;
        //Utilizamos el operador ternario para en caso de tner un valor nulo (-1) nos muestre la casilla vacía (por comodidad al rellenar)
        rep[3] = (blancas == -1) ? "" : valueOf(blancas);
        rep[4] = (negras == -1) ? "" : valueOf(negras);
        rep[5] = visitante;
        rep[6] = pista;
        return rep;
    }

    
}
