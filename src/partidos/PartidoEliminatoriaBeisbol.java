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
public class PartidoEliminatoriaBeisbol extends PartidoEliminatoria{
    
    //Variables de clase
    private int RL1;
    private int RV1;
    private int RL2;
    private int RV2;
    private int RL3;
    private int RV3;
    private int RL4;
    private int RV4;
    private int RL5;
    private int RV5;
    private int RL6;
    private int RV6;
    private int RL7;
    private int RV7;
    private int RL8;
    private int RV8;
    private int RL9;
    private int RV9;
    private int RL10;
    private int RV10;
    private int RL11;
    private int RV11;
    private int RL12;
    private int RV12;
    
    public PartidoEliminatoriaBeisbol(String local, String visitante){
        //Cuando iniciamos un equipo tiene 0 en todo
        super(local, visitante);
        this.ganador = super.getGanador();
        this.desempate = super.getDesempate();
        this.fecha = "";
        this.hora = "";
        this.golesL = -1;
        this.golesV = -1;
        this.pista = "";
        //Creamos las casillas de los sets vacías
        this.RL1 = -1;
        this.RV1 = -1;
        this.RL2 = -1;
        this.RV2 = -1; 
        this.RL3 = -1;
        this.RV3 = -1;
        this.RL4 = -1;
        this.RV4 = -1; 
        this.RL5 = -1;
        this.RV5 = -1;
        this.RL6 = -1;
        this.RV6 = -1;
        this.RL7 = -1;
        this.RV7 = -1; 
        this.RL8 = -1;
        this.RV8 = -1;
        this.RL9 = -1;
        this.RV9 = -1; 
        this.RL10 = -1;
        this.RV10 = -1;
        this.RL11 = -1;
        this.RV11 = -1;
        this.RL12 = -1;
        this.RV12 = -1;
    }
    
    //Contructor de clase para cada partido al abrir archivo
    public PartidoEliminatoriaBeisbol(String fecha, String hora, String local, int RL1, int RL2, int RL3, int RL4, int RL5, int RL6, int RL7, int RL8, int RL9,
             int RL10, int RL11, int RL12, int golesL, int RV1, int RV2, int RV3, int RV4, int RV5, int RV6, int RV7, int RV8, int RV9,
             int RV10, int RV11, int RV12, int golesV, String visitante, String pista, String ganador, String desempate){
        
        super(local, visitante);
        this.fecha = fecha;
        this.hora = hora;
        this.golesL = golesL;
        this.golesV = golesV;
        this.pista = pista;
        //Creamos las casillas de los sets vacías
        this.RL1 = RL1;
        this.RV1 = RV1;
        this.RL2 = RL2;
        this.RV2 = RV2; 
        this.RL3 = RL3;
        this.RV3 = RV3;
        this.RL4 = RL4;
        this.RV4 = RV4; 
        this.RL5 = RL5;
        this.RV5 = RV5;
        this.RL6 = RL6;
        this.RV6 = RV6;
        this.RL7 = RL7;
        this.RV7 = RV7; 
        this.RL8 = RL8;
        this.RV8 = RV8;
        this.RL9 = RL9;
        this.RV9 = RV9; 
        this.RL10 = RL10;
        this.RV10 = RV10;
        this.RL11 = RL11;
        this.RV11 = RV11;
        this.RL12 = RL12;
        this.RV12 = RV12;
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
    public int getGolesL() {
        return super.getGolesL(); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public int getGolesV() {
        return super.getGolesV(); //To change body of generated methods, choose Tools | Templates.
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
    public void setGolesV(int golesV) {
        super.setGolesV(golesV); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void setGolesL(int golesL) {
        super.setGolesL(golesL); //To change body of generated methods, choose Tools | Templates.
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
     * @return the RL1
     */
    public int getRL1() {
        return RL1;
    }

    /**
     * @param RL1 the RL1 to set
     */
    public void setRL1(int RL1) {
        this.RL1 = RL1;
    }

    /**
     * @return the RV1
     */
    public int getRV1() {
        return RV1;
    }

    /**
     * @param RV1 the RV1 to set
     */
    public void setRV1(int RV1) {
        this.RV1 = RV1;
    }

    /**
     * @return the RL2
     */
    public int getRL2() {
        return RL2;
    }

    /**
     * @param RL2 the RL2 to set
     */
    public void setRL2(int RL2) {
        this.RL2 = RL2;
    }

    /**
     * @return the RV2
     */
    public int getRV2() {
        return RV2;
    }

    /**
     * @param RV2 the RV2 to set
     */
    public void setRV2(int RV2) {
        this.RV2 = RV2;
    }

    /**
     * @return the RL3
     */
    public int getRL3() {
        return RL3;
    }

    /**
     * @param RL3 the RL3 to set
     */
    public void setRL3(int RL3) {
        this.RL3 = RL3;
    }

    /**
     * @return the RV3
     */
    public int getRV3() {
        return RV3;
    }

    /**
     * @param RV3 the RV3 to set
     */
    public void setRV3(int RV3) {
        this.RV3 = RV3;
    }

    /**
     * @return the RL4
     */
    public int getRL4() {
        return RL4;
    }

    /**
     * @param RL4 the RL4 to set
     */
    public void setRL4(int RL4) {
        this.RL4 = RL4;
    }

    /**
     * @return the RV4
     */
    public int getRV4() {
        return RV4;
    }

    /**
     * @param RV4 the RV4 to set
     */
    public void setRV4(int RV4) {
        this.RV4 = RV4;
    }

    /**
     * @return the RL5
     */
    public int getRL5() {
        return RL5;
    }

    /**
     * @param RL5 the RL5 to set
     */
    public void setRL5(int RL5) {
        this.RL5 = RL5;
    }

    /**
     * @return the RV5
     */
    public int getRV5() {
        return RV5;
    }

    /**
     * @param RV5 the RV5 to set
     */
    public void setRV5(int RV5) {
        this.RV5 = RV5;
    }

    /**
     * @return the RL6
     */
    public int getRL6() {
        return RL6;
    }

    /**
     * @param RL6 the RL6 to set
     */
    public void setRL6(int RL6) {
        this.RL6 = RL6;
    }

    /**
     * @return the RV6
     */
    public int getRV6() {
        return RV6;
    }

    /**
     * @param RV6 the RV6 to set
     */
    public void setRV6(int RV6) {
        this.RV6 = RV6;
    }

    /**
     * @return the RL7
     */
    public int getRL7() {
        return RL7;
    }

    /**
     * @param RL7 the RL7 to set
     */
    public void setRL7(int RL7) {
        this.RL7 = RL7;
    }

    /**
     * @return the RV7
     */
    public int getRV7() {
        return RV7;
    }

    /**
     * @param RV7 the RV7 to set
     */
    public void setRV7(int RV7) {
        this.RV7 = RV7;
    }

    /**
     * @return the RL8
     */
    public int getRL8() {
        return RL8;
    }

    /**
     * @param RL8 the RL8 to set
     */
    public void setRL8(int RL8) {
        this.RL8 = RL8;
    }

    /**
     * @return the RV8
     */
    public int getRV8() {
        return RV8;
    }

    /**
     * @param RV8 the RV8 to set
     */
    public void setRV8(int RV8) {
        this.RV8 = RV8;
    }

    /**
     * @return the RL9
     */
    public int getRL9() {
        return RL9;
    }

    /**
     * @param RL9 the RL9 to set
     */
    public void setRL9(int RL9) {
        this.RL9 = RL9;
    }

    /**
     * @return the RV9
     */
    public int getRV9() {
        return RV9;
    }

    /**
     * @param RV9 the RV9 to set
     */
    public void setRV9(int RV9) {
        this.RV9 = RV9;
    }

    /**
     * @return the RL10
     */
    public int getRL10() {
        return RL10;
    }

    /**
     * @param RL10 the RL10 to set
     */
    public void setRL10(int RL10) {
        this.RL10 = RL10;
    }

    /**
     * @return the RV10
     */
    public int getRV10() {
        return RV10;
    }

    /**
     * @param RV10 the RV10 to set
     */
    public void setRV10(int RV10) {
        this.RV10 = RV10;
    }

    /**
     * @return the RL11
     */
    public int getRL11() {
        return RL11;
    }

    /**
     * @param RL11 the RL11 to set
     */
    public void setRL11(int RL11) {
        this.RL11 = RL11;
    }

    /**
     * @return the RV11
     */
    public int getRV11() {
        return RV11;
    }

    /**
     * @param RV11 the RV11 to set
     */
    public void setRV11(int RV11) {
        this.RV11 = RV11;
    }
    /**
     * @return the RL12
     */
    public int getRL12() {
        return RL12;
    }

    /**
     * @param RL12 the RL12 to set
     */
    public void setRL12(int RL12) {
        this.RL12 = RL12;
    }

    /**
     * @return the RV12
     */
    public int getRV12() {
        return RV12;
    }

    /**
     * @param RV12 the RV12 to set
     */
    public void setRV12(int RV12) {
        this.RV12 = RV12;
    }
     
    /**
     * Método que se utiliza para rellenar las celdas de la tabla de jornadas
     * @return 
     */
    @Override
    public String[] getTableRepresentation(){        
        String[] rep = new String[33];
        rep[0] = fecha;
        rep[1] = hora;
        rep[2] = local;
        //Utilizamos el operador ternario para en caso de tner un valor nulo (-1) nos muestre la casilla vacía (por comodidad al rellenar)
        rep[3] = (RL1 == -1) ? "" : valueOf(RL1);
        rep[4] = (RL2 == -1) ? "" : valueOf(RL2);
        rep[5] = (RL3 == -1) ? "" : valueOf(RL3);
        rep[6] = (RL4 == -1) ? "" : valueOf(RL4);
        rep[7] = (RL5 == -1) ? "" : valueOf(RL5);
        rep[8] = (RL6 == -1) ? "" : valueOf(RL6);
        rep[9] = (RL7 == -1) ? "" : valueOf(RL7);
        rep[10] = (RL8 == -1) ? "" : valueOf(RL8);
        rep[11] = (RL9 == -1) ? "" : valueOf(RL9);
        rep[12] = (RL10 == -1) ? "" : valueOf(RL10);
        rep[13] = (RL11 == -1) ? "" : valueOf(RL11);
        rep[14] = (RL12 == -1) ? "" : valueOf(RL12);
        rep[15] = (golesL == -1) ? "" : valueOf(golesL);
        rep[16] = (RV1 == -1) ? "" : valueOf(RV1);      
        rep[17] = (RV2 == -1) ? "" : valueOf(RV2);        
        rep[18] = (RV3 == -1) ? "" : valueOf(RV3);       
        rep[19] = (RV4 == -1) ? "" : valueOf(RV4);       
        rep[20] = (RV5 == -1) ? "" : valueOf(RV5);       
        rep[21] = (RV6 == -1) ? "" : valueOf(RV6);       
        rep[22] = (RV7 == -1) ? "" : valueOf(RV7);       
        rep[23] = (RV8 == -1) ? "" : valueOf(RV8);        
        rep[24] = (RV9 == -1) ? "" : valueOf(RV9);       
        rep[25] = (RV10== -1) ? "" : valueOf(RV10);      
        rep[26] = (RV11 == -1) ? "" : valueOf(RV11);
        rep[27] = (RV12 == -1) ? "" : valueOf(RV12);
        rep[28] = (golesV == -1) ? "" : valueOf(golesV);
        rep[29] = visitante;
        rep[30] = pista;
        rep[31] = ganador;
        rep[32] = desempate;
        return rep;
    }   
}
