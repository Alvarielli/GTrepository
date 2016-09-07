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
package metodos;

import generador.Calendario;
import generador.EquipoLista;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import partidos.Partido;

/**
 * Clase abstracta que actua como base para las clases de Métodos de los distintos deportes
 * @author Alvarielli
 */
public abstract class Metodos {
            
    public abstract void cargarTabla(int jornada);
  
    public abstract void guardarTabla();

    public abstract void mostrarJornada();

     public abstract void cargarClasificacion();

     public abstract void calcularClasificacion();
     
     public abstract void borrarClasificacion();
     
     //public abstract void asignarDatos(); Cada deporte recibe una parametrización distinta

     public abstract void ordenarClasificacion();
          
     public abstract void guardarXML(String ruta, String nombreArchivo);
     
     public abstract void informeJornada(String rutaPDF, String nombreArchivo, boolean aplazados, ArrayList<Partido> partidosAplazados);
     
     public abstract void informeResultados(String rutaPDF, String nombreArchivo, boolean aplazados, ArrayList<Partido> partidosAplazados);
     
     public abstract void informeClasificacion(String rutaPDF, String nombreArchivo);
     
     public abstract void informeCalendario(String rutaPDF, String nombreArchivo);
     
     public abstract void getRutaXMLCalendar(String rutaXMLCalendar);
     
     public abstract void informeTodasJornadas(String rutaPDF, String nombreArchivo);
     
     public abstract void informeTodosResultados(String rutaPDF, String nombreArchivo);
     
     public abstract int[] getAnchoTabla(int tamaño, int codigo);
     
     public abstract int getAltoTabla(int codigo);
     
     public abstract void setDatosTabla(int[] valores, int altura);
     
     public abstract void setDatosTablaClasf(int[] valores, int alturaClasf);
     
    /**
     * Recibe los goles en formato cadena y comprueba que son un número entero positivo
     * @param cadena
     * @return 
     */
    public int validarEnteros(String cadena){
        int numero;
        try{
           //Casteamos la cadena pasada
          numero = Integer.parseInt(cadena);
          if(numero < 0){
            //Si lo recibido es un número negativo
            //JOptionPane.showMessageDialog(this, "Los Goles deben contener números enteros no negativos", "Error al insertar los datos de la tabla", JOptionPane.ERROR_MESSAGE, null);
            numero = -1;
            return numero;
          }else{
            return numero;
          }
        }catch(NumberFormatException nfe){
            //Si lo recibido no es un número entero guardamos un -1 y mostramos un mensaje de error
            //JOptionPane.showMessageDialog(this, "Los Goles deben contener números enteros no negativos", "Error al insertar los datos de la tabla", JOptionPane.ERROR_MESSAGE, null);
            numero = -1;
            return numero;
        }
    }//End of validarEnteros()
    
    /**
     * MÉTODOS GETTERS AND SETTERS
     * @return 
     */

     public abstract DefaultTableModel getModelo();
     
     public abstract void setModelo(DefaultTableModel modelo);

    public abstract DefaultTableModel getClasificacion();

    public abstract void setClasificacion(DefaultTableModel clasificacion);

    public abstract int getJornada();

    public abstract void setJornada(int jornada);

    public abstract JTable getTableJornada();

    public abstract void setTableJornada(JTable TableJornada);

    public abstract JTable getTableClasificacion();

    public abstract void setTableClasificacion(JTable TableClasificacion);

    public abstract JLabel getLabelEquipoDescansa();

    public abstract void setLabelEquipoDescansa(JLabel LabelEquipoDescansa);

    public abstract JLabel getLabelJornada();

    public abstract void setLabelJornada(JLabel LabelJornada);

    public abstract JLabel getLabelTotalJornadas();

    public abstract void setLabelTotalJornadas(JLabel LabelTotalJornadas);

    public abstract JTextField getTextFieldJornada();

    public abstract void setTextFieldJornada(JTextField TextFieldJornada);

    public abstract Calendario getCalendario();

    public abstract void setCalendario(Calendario calendario);

    public abstract EquipoLista getCastEquipoLista();

    public abstract void setCastEquipoLista(EquipoLista castEquipoLista);
    
    public abstract String getNombreTorneo();

    public abstract void setNombreTorneo(String nombre_torneo);
    
    public abstract int getTipoTorneo();

    public abstract void setTipoTorneo(int tipo_torneo);
    
    public abstract int getDeporte();

    public abstract void setDeporte(int deporte);
    
    public abstract int getNum_jornadas();

    public abstract void setNum_jornadas(int num_jornadas);
    
    public abstract boolean isIdaVuelta();

    public abstract void setIdaVuelta(boolean idaVuelta);
    
    public abstract int getSets();

    public abstract void setSets(int sets);
    
    public abstract boolean isSorteo();

    public abstract void setSorteo(boolean sorteo);
    
    public abstract boolean isTercerCuartoPuesto();

    public abstract void setTercerCuartoPuesto(boolean tercerCuartoPuesto);
    
    public abstract int[] getNum();

    public abstract void setNum(int[] num);
    
    public abstract int[] getNumc();

    public abstract void setNumc(int[] num);
       
    public abstract int getAlturaColumna();

    public abstract void setAlturaColumna(int altura);
    
    public abstract int getAlturaClasfColumna();

    public abstract void setAlturaClasfColumna(int altura);
       
    public abstract String[] getColumnas();

    public abstract void setColumnas(String[] columnas);
    
    public abstract String[] getColumnasClasf();

    public abstract void setColumnasClasf(String[] columnas);
    
    public abstract HashMap<String, Integer> getSancionados();

    public abstract void setSancionados(HashMap<String, Integer> sancionados);

 }
