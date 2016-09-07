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
package generador;

import java.util.ArrayList;

/**
 *
 * @author Alvarielli
 */
public class Torneo {
    
    //Variables de la clase Torneo
    private String nombre; //Nombre del torneo y del archivo que lo guardará
    private int participantes; //Número de participantes
    private int deporte; //Guarda el deporte escogido de un JComboBox
    private int tipoTorneo; //Contiene el tipo de torneo
    private boolean idaVuelta; //Contiene si es ida o ida y vuelta
    private ArrayList<String> equipoLista;//Contiene el ArrayList de equipos del torneo
    private int sets;//Contiene el número de sets a jugar (si es requerido)
    private boolean sorteo;
    private boolean tercerCuartoPuesto;
    private ArrayList<String> izquierdaEO;//recupera el valor de la lista izquierda de equipos opuestos
    private ArrayList<String> derechaEO;//recupera el valor de la lista derecha de equipos opuestos
    
    //Definimos un array con los deportes, cada uno en la posición del índice en el ComboBox del formulario
    //private String[] deportes = {"Futbol", "Baloncesto", "Tenis"};
    
    //Constructor de clase
    public Torneo(String nombre, int participantes, int deporte, int tipoTorneo, boolean idaVuelta, ArrayList<String> equipoLista){
        this.nombre = nombre;
        this.participantes = participantes;
        this.deporte = deporte;
        this.tipoTorneo = tipoTorneo;
        this.idaVuelta = idaVuelta;
        this.equipoLista = equipoLista;
    }
    //Constructor que incluye sets
    public Torneo(String nombre, int participantes, int deporte, int tipoTorneo, boolean idaVuelta, ArrayList<String> equipoLista, int sets){
        this.nombre = nombre;
        this.participantes = participantes;
        this.deporte = deporte;
        this.tipoTorneo = tipoTorneo;
        this.idaVuelta = idaVuelta;
        this.equipoLista = equipoLista;
        this.sets = sets;
    }
    
     //Constructor que incluye eliminatorias
    public Torneo(String nombre, int participantes, int deporte, int tipoTorneo, boolean idaVuelta, ArrayList<String> equipoLista, boolean sorteo, boolean tercerCuartoPuesto ){
        this.nombre = nombre;
        this.participantes = participantes;
        this.deporte = deporte;
        this.tipoTorneo = tipoTorneo;
        this.idaVuelta = idaVuelta;
        this.equipoLista = equipoLista;
        this.sorteo = sorteo;
        this.tercerCuartoPuesto = tercerCuartoPuesto;
    }
    
     //Constructor que incluye eliminatorias y sets
    public Torneo(String nombre, int participantes, int deporte, int tipoTorneo, boolean idaVuelta, ArrayList<String> equipoLista, boolean sorteo, boolean tercerCuartoPuesto, int sets){
        this.nombre = nombre;
        this.participantes = participantes;
        this.deporte = deporte;
        this.tipoTorneo = tipoTorneo;
        this.idaVuelta = idaVuelta;
        this.equipoLista = equipoLista;
        this.sorteo = sorteo;
        this.tercerCuartoPuesto = tercerCuartoPuesto;
        this.sets = sets;
    }
    
     //Constructor que incluye equipos opuestos
    public Torneo(String nombre, int participantes, int deporte, int tipoTorneo, boolean idaVuelta, ArrayList<String> equipoLista, ArrayList<String> izquierdaEO, ArrayList<String> derechaEO){
        this.nombre = nombre;
        this.participantes = participantes;
        this.deporte = deporte;
        this.tipoTorneo = tipoTorneo;
        this.idaVuelta = idaVuelta;
        this.equipoLista = equipoLista;
        this.izquierdaEO = izquierdaEO;
        this.derechaEO = derechaEO;
    }
    
     //Constructor que incluye equipos opuestos y sets
    public Torneo(String nombre, int participantes, int deporte, int tipoTorneo, boolean idaVuelta, ArrayList<String> equipoLista, int sets, ArrayList<String> izquierdaEO, ArrayList<String> derechaEO){
        this.nombre = nombre;
        this.participantes = participantes;
        this.deporte = deporte;
        this.tipoTorneo = tipoTorneo;
        this.idaVuelta = idaVuelta;
        this.equipoLista = equipoLista;
        this.izquierdaEO = izquierdaEO;
        this.derechaEO = derechaEO;
        this.sets = sets;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the participantes
     */
    public int getParticipantes() {
        return participantes;
    }

    /**
     * @param participantes the participantes to set
     */
    public void setParticipantes(int participantes) {
        this.participantes = participantes;
    }

    /**
     * @return the tipoTorneo
     */
    public int getTipoTorneo() {
        return tipoTorneo;
    }

    /**
     * @param tipoTorneo the tipoTorneo to set
     */
    public void setTipoTorneo(int tipoTorneo) {
        this.tipoTorneo = tipoTorneo;
    }

    /**
     * @return the idaVuelta
     */
    public boolean getIdaVuelta() {
        return idaVuelta;
    }

    /**
     * @param idaVuelta the idaVuelta to set
     */
    public void setIdaVuelta(boolean idaVuelta) {
        this.idaVuelta = idaVuelta;
    }

    /**
     * @return the deporte
     */
    public int getDeporte() {
        return deporte;
    }

    /**
     * @param deporte the deporte to set
     */
    public void setDeporte(int deporte) {
        this.deporte = deporte;
    }

    /**
     * @return the equipoLista
     */
    public ArrayList<String> getEquipoLista() {
        return equipoLista;
    }

    /**
     * @param equipoLista the equipoLista to set
     */
    public void setEquipoLista(ArrayList<String> equipoLista) {
        this.equipoLista = equipoLista;
    }

    /**
     * @return the sets
     */
    public int getSets() {
        return sets;
    }

    /**
     * @param sets the sets to set
     */
    public void setSets(int sets) {
        this.sets = sets;
    }
    
    /**
     * @return the sorteo
     */
    public boolean getSorteo() {
        return sorteo;
    }

    /**
     * @param sorteo the sorteo to set
     */
    public void setSorteo(boolean sorteo) {
        this.sorteo = sorteo;
    }

    
    /**
     * @return the tercerCuartoPuesto
     */
    public boolean getTercerCuartoPuesto() {
        return tercerCuartoPuesto;
    }

    /**
     * @param tercerCuartoPuesto the tercerCuartoPuesto to set
     */
    public void setTercerCuartoPuesto(boolean tercerCuartoPuesto) {
        this.tercerCuartoPuesto = tercerCuartoPuesto;
    }

    /**
     * @return the izquierdaEO
     */
    public ArrayList<String> getIzquierdaEO() {
        return izquierdaEO;
    }

    /**
     * @param izquierdaEO the izquierdaEO to set
     */
    public void setIzquierdaEO(ArrayList<String> izquierdaEO) {
        this.izquierdaEO = izquierdaEO;
    }

    /**
     * @return the derechaEO
     */
    public ArrayList<String> getDerechaEO() {
        return derechaEO;
    }

    /**
     * @param derechaEO the derechaEO to set
     */
    public void setDerechaEO(ArrayList<String> derechaEO) {
        this.derechaEO = derechaEO;
    }

        
}
