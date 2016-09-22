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

/**
 * Válido para Tenis, Voleibol y Tenis de Mesa
 * @author Alvarielli
 */

import datasources.DataSourceEliminatoriasTenis5SetsResultados;
import datasources.DataSourceFutbolJornada;
import equipos.EquipoTenis;
import generador.Calendario;
import generador.EquipoLista;
import generador.Jornada;
import static generador.Principal.limitarCadena;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import static java.lang.String.valueOf;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRXmlDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import partidos.Partido;
import partidos.PartidoEliminatoria;
import partidos.PartidoEliminatoriaTenis5Sets;
import renders.RenderEliminatoriaTenis5Sets;

public class MetodosEliminatoriaTenis5Sets extends Metodos{
    
    //Le pasamos los valores de los objetos de Principal a esta clase
    private DefaultTableModel modelo;
    private int jornada;
    private JTable TableJornada;
    private JLabel LabelJornada;
    private JLabel LabelTotalJornadas;
    private JTextField TextFieldJornada;
    private Calendario calendario;
    private EquipoLista castEquipoLista;//EquipoLista con los Equipos ya casteados en Principal (seleccionarClaseEquipo)
    private boolean mensaje = false;//Se utiliza para que el programa muestre el mensaje de error al introducir datos sólo una vez
    private int anchoTabla = 1740;//Ponemos un valor fijo y nos evitamos problemas //TableJornada.getWidth();
    private int alturaColumna = 25;//Indica la altura de la columna
    private RenderEliminatoriaTenis5Sets render;
    private int[] num = {6, 4, 14, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 14, 6, 8, 8};// Definimos los anchos para las tablas en porcentaje (para que el usuario pueda modificarlas luego)
    private String[] columnas;
    
    //Variables añadidas para las eliminatorias
    private ArrayList<String> auxEquipos = new ArrayList<>();//creamos un arraylist para ir añadiendo eliminatorias
    private int numEliminatorias;
    private boolean tercerCuartoPuesto;
    private boolean idaVuelta;//Necesario para las actualizaciones de las eliminatorias
    private String[] nombresEliminatorias;//Guarda los nuevos nombres de las fases de las eliminatorias
    
    /*** Variables que necesitaremos para los archivos XML ***/
    private String nombreTorneo;
    private int tipoTorneo;
    private int deporte;
    private int num_jornadas;
    private int sets;
    private boolean sorteo;
    //Ruta para el calendario PDF
    private String rutaXMLCalendar = "";
    //HashMap de equipos sancionados (en eliminatorias no se utliza el HashMap)
    private HashMap<String, Integer> sancionados = new HashMap<>();
    
    /** Variables necesarias para el tamaño de las tablas **/
    private final int CODIGO_ANCHO = 330;
    private final int CODIGO_ALTO = 30;
    //Estas simplemente son necesarias para poder utilizarlas desde la interfaz
    private int[] numc;
    private String[] columnasClasf;
    private int alturaClasfColumna;
    
    //En el constructor de clase le pasamos todo lo requerido para esta clase desde Principal
    public MetodosEliminatoriaTenis5Sets(DefaultTableModel modelo, int jornada, JTable TableJornada,JLabel LabelJornada, JLabel LabelTotalJornadas, 
            JTextField TextFieldJornada, Calendario calendario, EquipoLista castEquipoLista, String nombreTorneo, int tipoTorneo, int deporte,
            int num_jornadas, boolean idaVuelta, int sets, boolean sorteo, boolean tercerCuartoPuesto){
    
        this.modelo = modelo;
        this.jornada = jornada;
        this.TableJornada = TableJornada;
        this.LabelJornada = LabelJornada;
        this.LabelTotalJornadas = LabelTotalJornadas;
        this.TextFieldJornada = TextFieldJornada;
        this.calendario = calendario;
        this.castEquipoLista = castEquipoLista;
        //y ahora inicializamos las variables del torneo       
        this.nombreTorneo = nombreTorneo;
        this.tipoTorneo = tipoTorneo;
        this.deporte = deporte;
        this.num_jornadas = num_jornadas;
        this.idaVuelta = idaVuelta;
        this.sets = sets;
        this.sorteo = sorteo;
        this.tercerCuartoPuesto = tercerCuartoPuesto;
    }
    
     /**
     * Carga los datos de la joranda pasada en la tabla
     * @param jornada
     */
    @Override
    public void cargarTabla(int jornada){
        /* Actualizamos las eliminatorias cada vez que cargamos la tabla*/
        setNumEliminatorias(calcularNumeroEliminatorias(castEquipoLista.getEquipos(), idaVuelta));//Las definimos aquí porque necesitamos que se carguen los equipos para calcular el número de eliminatorias
        if(idaVuelta){
            actualizarNuevaEliminatoriaIdaVuelta();
        }else{
            actualizarNuevaEliminatoria();//Lo hacemos aquí para que actualize la tabla desde la primera jornada
        }
        
        TableJornada.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);//Reasignamos el AutoAjustado
        //Textos para las columnas
        String[] columnasLocal = {"FECHA", "HORA", "LOCAL", "SET 1 (L)", "SET 1 (V)", "SET 2 (L)", "SET 2 (V)", "SET 3 (L)", "SET 3 (V)", "SET 4 (L)", "SET 4 (V)", "SET 5 (L)", "SET 5 (V)", "VISITANTE", "PISTA", "GANADOR", "DESEMPATE"};
        setColumnas(columnasLocal);
        //Indicamos que columnas serán editables
        final boolean[] canEdit = new boolean [] {true, true, false, true, true, true, true, true, true, true, true, true, true, false, true, true, true};
        //Asignamos los nuevos anchos al array (los del archivo.txt)
        setNum(getAnchoTabla(getNum().length, CODIGO_ANCHO)); 
        //Definimos los anchos de las columnas y los asignamos como tamaño preferente
         int[] anchos = {(anchoTabla*num[0])/100, (anchoTabla*num[1])/100, (anchoTabla*num[2])/100, (anchoTabla*num[3])/100, (anchoTabla*num[4])/100, (anchoTabla*num[5])/100, (anchoTabla*num[6])/100, (anchoTabla*num[7])/100, (anchoTabla*num[8])/100, (anchoTabla*num[9])/100, (anchoTabla*num[10])/100, 
             (anchoTabla*num[11])/100, (anchoTabla*num[12])/100, (anchoTabla*num[13])/100, (anchoTabla*num[14])/100, (anchoTabla*num[15])/100, (anchoTabla*num[16])/100};
        //Modelo de tabla por defecto
        setModelo(new DefaultTableModel(getColumnas(),0){
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        
        //Asignamos a nuestra variable global jornada el valor de la jornada pasada (pues el calculo se realiza en los botones de Principal)
        this.jornada = jornada;
        //En función de las características de la eliminatoria se escogerá un ArrayList (ida y vuelta, tercer y cuarto puesto... (10 rondas y 20 con la vuelta)
        if(idaVuelta == false){
            if(tercerCuartoPuesto == false){
                nombresEliminatorias = new String[] {"Final", "Semifinales", "Cuartos de Final", "Octavos de Final", "Dieciseisavos de Final", "Treintaidosavos de Final", "64avos de Final", "128avos de Final", "256avos de Final", "512avos de Final"};
            }else{           
                nombresEliminatorias = new String[] {"Final - 3º y 4º Puesto", "Semifinales", "Cuartos de Final", "Octavos de Final", "Dieciseisavos de Final", "Treintaidosavos de Final", "64avos de Final", "128avos de Final", "256avos de Final", "512avos de Final"};
            }//end if tercerCuartoPuesto
        }else{
            if(tercerCuartoPuesto == false){
                nombresEliminatorias = new String[] {"Final - Vuelta", "Final", "Semifinales - Vuelta", "Semifinales", "Cuartos de Final - Vuelta", "Cuartos de Final", "Octavos de Final - Vuelta", "Octavos de Final", "Dieciseisavos de Final - Vuelta", "Dieciseisavos de Final", "Treintaidosavos de Final - Vuelta", "Treintaidosavos de Final", "64avos de Final - Vuelta", "64avos de Final", "128avos de Final - Vuelta", "128avos de Final", "256avos de Final - Vuelta", "256avos de Final",  "512avos de Final - Vuelta", "512avos de Final"};          
            }else{   
                nombresEliminatorias = new String[] {"Final - 3º y 4º Puesto  - Vuelta", "Final - 3º y 4º Puesto", "Semifinales - Vuelta", "Semifinales", "Cuartos de Final - Vuelta", "Cuartos de Final", "Octavos de Final - Vuelta", "Octavos de Final", "Dieciseisavos de Final - Vuelta", "Dieciseisavos de Final", "Treintaidosavos de Final - Vuelta", "Treintaidosavos de Final", "64avos de Final - Vuelta", "64avos de Final", "128avos de Final - Vuelta", "128avos de Final", "256avos de Final - Vuelta", "256avos de Final",  "512avos de Final - Vuelta", "512avos de Final"}; 
            }//end if tercerCuartoPuesto
        }//end if nombresEliminatorias 
        //Lo primero de todo, cada vez que se carga el método que pinta la tabla, borramos las filas que pudiera haber escritas
        getTableJornada().removeAll();
        //modelo.getDataVector().removeAllElements();
        getTableJornada().repaint();//Repinta el elemento en cuestión repaint()???
        //Creamos una variable Jornada y obtenemos la jornada indicada de nuestro objeto calendario
        Jornada J = getCalendario().jornadas.get(jornada - 1);//-1 porque es un índice
        //Ahora usamos el for-each y obtenemos la lista de partidos de este objeto Jornada
        for (PartidoEliminatoria partido :(ArrayList<PartidoEliminatoria>) J.getListaPartidos())
                getModelo().addRow(partido.getTableRepresentation()); 
        
        //Indicamos a la tabla cual es su modelo y asignamos el ganador de la eliminatoria
        getTableJornada().setModel(getModelo());
        asignarGanador(TableJornada, TableJornada.getColumnModel().getColumn(15));
        //Le asignamos el render al calendario
        render = new RenderEliminatoriaTenis5Sets();
        getTableJornada().setDefaultRenderer(Object.class, render);
        //Le asignamos a la etiqueta Jornada su número correspondiente
        if(idaVuelta){
            getLabelJornada().setText(nombresEliminatorias[calendario.jornadas.size() - jornada]);
        }else{
            getLabelJornada().setText(nombresEliminatorias[calendario.jornadas.size() - jornada]);           
        }
        //getLabelJornada().setText("JORNADA " + jornada);
        //Asignamos el total de jornadas a su etiqueta
        getLabelTotalJornadas().setText("JORNADAS EN TOTAL: " + getCalendario().jornadas.size());
        //Asignamos la nueva altura (la del archivo.txt)
        setAlturaColumna(getAltoTabla(CODIGO_ALTO));
        TableJornada.setRowHeight(getAlturaColumna());//Método que cambia la altura de las filas del JTable (funciona una vez asignado el modelo) 
        for (int i = 0; i < getColumnas().length; i++) {
            TableJornada.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);           
        }
        //Indicamos que las columnas no se puedan mover
        TableJornada.getTableHeader().setReorderingAllowed(false);
    }//End of cargarTabla()
    
    /**
     * Recoge los datos que se están mostrando en la tabla y los pasa a su ArrayList correspondiente
     */
    @Override
    public void guardarTabla(){
       //Creamos una variable Jornada y obtenemos la jornada indicada de nuestro objeto calendario
       Jornada J = getCalendario().jornadas.get(getJornada() - 1);//coge la jornada como variable global, -1 porque es un índice
       int filas =  getTableJornada().getRowCount();//número de filas en la tabla
       //Por cada fila guardo los datos en el partido correspondiente del ArrayList 
       for (int i = 0; i < filas; i++) {
           //Obtenemos el partido en el ArrayList y le asignamos los nuevos datos
            PartidoEliminatoriaTenis5Sets P = (PartidoEliminatoriaTenis5Sets) J.partidos.get(i);
            P.setFecha(limitarCadena(getModelo().getValueAt(i,0).toString().trim(), 30, false));//el toString() para que lo coja como una cadena
            P.setHora(limitarCadena(getModelo().getValueAt(i,1).toString().trim(), 30, false));
            P.setLocal(limitarCadena(getModelo().getValueAt(i,2).toString().trim(), 40, false));
            P.setSet1L(validarEnteros(getModelo().getValueAt(i,3).toString().trim()));
            P.setSet1V(validarEnteros(getModelo().getValueAt(i,4).toString().trim()));
            P.setSet2L(validarEnteros(getModelo().getValueAt(i,5).toString().trim()));
            P.setSet2V(validarEnteros(getModelo().getValueAt(i,6).toString().trim()));
            P.setSet3L(validarEnteros(getModelo().getValueAt(i,7).toString().trim()));
            P.setSet3V(validarEnteros(getModelo().getValueAt(i,8).toString().trim()));
            P.setSet4V(validarEnteros(getModelo().getValueAt(i,9).toString().trim()));
            P.setSet4L(validarEnteros(getModelo().getValueAt(i,10).toString().trim()));
            P.setSet5V(validarEnteros(getModelo().getValueAt(i,11).toString().trim()));
            P.setSet5L(validarEnteros(getModelo().getValueAt(i,12).toString().trim()));
            P.setVisitante(limitarCadena(getModelo().getValueAt(i,13).toString().trim(), 40, false));
            P.setPista(limitarCadena(getModelo().getValueAt(i,14).toString().trim(), 30, false));
            P.setGanador(limitarCadena(getModelo().getValueAt(i,15).toString().trim(), 30, false));
            P.setDesempate(limitarCadena(getModelo().getValueAt(i,16).toString().trim(), 30, false));
            
            //Si los resultados arrojan un empate avisamos al usuario de su error (lo cargará al usar las flechas de jornada) Son iguales y no están vacías
            if(P.getSet1L() == P.getSet1V() && !getModelo().getValueAt(i,3).toString().equalsIgnoreCase("") && !getModelo().getValueAt(i,4).toString().equalsIgnoreCase("")){
                P.setSet1L(-1);
                P.setSet1V(-1);
                //Mostramos un mensaje de error en caso de que el usuario coloque un empate en la tabla
                if(!this.mensaje){//if esclusivamente para mostrar o no el mensaje
                     JOptionPane.showMessageDialog(TableJornada, "El resultado no es válido, los participantes no pueden empatar en ningún set", "Error al insertar los datos de la tabla", JOptionPane.ERROR_MESSAGE, null);
                     this.mensaje = true;
                 }
            }
            if(P.getSet2L() == P.getSet2V() && !getModelo().getValueAt(i,5).toString().equalsIgnoreCase("") && !getModelo().getValueAt(i,6).toString().equalsIgnoreCase("")){
                P.setSet2L(-1);
                P.setSet2V(-1);
                //Mostramos un mensaje de error en caso de que el usuario coloque un empate en la tabla
                if(!this.mensaje){//if esclusivamente para mostrar o no el mensaje
                     JOptionPane.showMessageDialog(TableJornada, "El resultado no es válido, los participantes no pueden empatar en ningún set", "Error al insertar los datos de la tabla", JOptionPane.ERROR_MESSAGE, null);
                     this.mensaje = true;
                 }
            }
            if(P.getSet3L() == P.getSet3V() && !getModelo().getValueAt(i,7).toString().equalsIgnoreCase("") && !getModelo().getValueAt(i,8).toString().equalsIgnoreCase("")){
                P.setSet3L(-1);
                P.setSet3V(-1);
                //Mostramos un mensaje de error en caso de que el usuario coloque un empate en la tabla
                if(!this.mensaje){//if esclusivamente para mostrar o no el mensaje
                     JOptionPane.showMessageDialog(TableJornada, "El resultado no es válido, los participantes no pueden empatar en ningún set", "Error al insertar los datos de la tabla", JOptionPane.ERROR_MESSAGE, null);
                     this.mensaje = true;
                 }
            }
            if(P.getSet4L() == P.getSet4V() && !getModelo().getValueAt(i,9).toString().equalsIgnoreCase("") && !getModelo().getValueAt(i,10).toString().equalsIgnoreCase("")){
                P.setSet4L(-1);
                P.setSet4V(-1);
                //Mostramos un mensaje de error en caso de que el usuario coloque un empate en la tabla
                if(!this.mensaje){//if esclusivamente para mostrar o no el mensaje
                     JOptionPane.showMessageDialog(TableJornada, "El resultado no es válido, los participantes no pueden empatar en ningún set", "Error al insertar los datos de la tabla", JOptionPane.ERROR_MESSAGE, null);
                     this.mensaje = true;
                 }
            }
            if(P.getSet5L() == P.getSet5V() && !getModelo().getValueAt(i,11).toString().equalsIgnoreCase("") && !getModelo().getValueAt(i,12).toString().equalsIgnoreCase("")){
                P.setSet5L(-1);
                P.setSet5V(-1);
                //Mostramos un mensaje de error en caso de que el usuario coloque un empate en la tabla
                if(!this.mensaje){//if esclusivamente para mostrar o no el mensaje
                     JOptionPane.showMessageDialog(TableJornada, "El resultado no es válido, los participantes no pueden empatar en ningún set", "Error al insertar los datos de la tabla", JOptionPane.ERROR_MESSAGE, null);
                     this.mensaje = true;
                 }
            }
        }//end for
       //Tras acabar el for y recorrer todos los resultados ponemos el mensaje como false de nuevo
        this.mensaje = false;
    }//End of guardarTabla() 
    
    /**
     * Muestra la joranda indicada en el JTextField
     */
    @Override
    public void mostrarJornada(){
         //Lo primero de todo guardamos los datos que tenga asignados la tabla
        guardarTabla();
        //Obtenemos el valor del TextField para buscar la jornada
        setJornada(validarEnteros(getTextFieldJornada().getText()));
        //Si la jornada es mayor que el total de jornadas lo igualamos a éste y si es 0 lo pasamos a la jornada 1
        if(getJornada() > getCalendario().jornadas.size()){
            setJornada(getCalendario().jornadas.size());
        }else if(getJornada() <= 0){
            setJornada(1);
        }
        cargarTabla(getJornada());
    }
    
    /**
     * Recibe los goles en formato cadena y comprueba que son un número entero positivo
     * @param cadena
     * @return 
     */
    @Override
    public int validarEnteros(String cadena){
        int numero;
        if(!cadena.equalsIgnoreCase("")){
        //Si la casilla está vacía ignoramos el chequeo, pues no hay información que comprobar
            try{
               //Casteamos la cadena pasada
              numero = Integer.parseInt(cadena);


              if(numero < 0){
                //Si lo recibido es un número negativo
                if(!this.mensaje){//if esclusivamente para mostrar o no el mensaje
                    JOptionPane.showMessageDialog(TableJornada, "Los Goles deben contener números enteros no negativos", "Error al insertar los datos de la tabla", JOptionPane.ERROR_MESSAGE, null);
                    this.mensaje = true;
                }
                numero = -1;
                return numero;
              }else{
                return numero;
              }
            }catch(NumberFormatException nfe){
                //Si lo recibido no es un número entero guardamos un -1 y mostramos un mensaje de error
                if(!this.mensaje){//if esclusivamente para mostrar o no el mensaje
                    JOptionPane.showMessageDialog(TableJornada, "Los Goles deben contener números enteros no negativos", "Error al insertar los datos de la tabla", JOptionPane.ERROR_MESSAGE, null);
                    this.mensaje = true;
                }
                numero = -1;
                return numero;
            }
        }//End if de chequeo de cadena
            return -1;//Devolvemos el número que será tratado por nuestro programa
    }//End of validarEnteros()
    
    /**
      * Método que asigna un JComboBox a la columna del ganador
      * @param tabla
      * @param columna 
      */
     public void asignarGanador(JTable tabla, TableColumn columna){
         String[] valores = {"LOCAL", "VISITANTE"};
         JComboBox combo = new JComboBox(valores);
         columna.setCellEditor(new DefaultCellEditor(combo));
     }
     
     /**
      * Método que se utiliza para actualizar los ganadores de las eliminatorias en función de si el usuario marca LOCAL o VISITANTE
      */
     public void actualizarNuevaEliminatoria(){
         int jor = 1;
        //Mientras haya eliminatorias vamos creando jornadas
        while(jor < getNumEliminatorias()){
            getAuxEquipos().clear();//vaciamos el arrayList auxiliar
            Jornada Jsiguiente = getCalendario().jornadas.get(jor);//Una más que la jornada (sería -1 pues es un array)
            //Comprobamos la jornada actual y añadimos los equipos ganadores al nuevo ArrayList con el que formaremos la siguiente ronda
            Jornada J = getCalendario().jornadas.get(jor - 1);
            //Definimos el tamaño del Arraylist en caso de que la Eliminatoria sea la última y la opción tercerCuartoPuesto sea verdadera para poder añadir los equipos con posición 2,3
            for (PartidoEliminatoria partido :(ArrayList<PartidoEliminatoria>) J.getListaPartidos())
                //Si el local o el visitante no tienen rival les asignamos como ganadores
                if(partido.getGanador().equalsIgnoreCase("LOCAL")){
                    getAuxEquipos().add(partido.getLocal());
                   //Si hay tercer y cuarto puesto y son semifinales añadimos también al Arraylist el equipo derrotado
                   if(jor == (getNumEliminatorias()-1)  && isTercerCuartoPuesto() == true){
                        getAuxEquipos().add(partido.getVisitante());
                   }
                }else if(partido.getGanador().equalsIgnoreCase("VISITANTE")){
                    //Si hay tercer y cuarto puesto y son semifinales añadimos también al Arraylist el equipo derrotado
                    getAuxEquipos().add(partido.getVisitante());
                   if(jor == (getNumEliminatorias()-1)  && isTercerCuartoPuesto() == true){
                        getAuxEquipos().add(partido.getLocal());
                   }
                }else{
                    //getModelo().addRow(partido.getTableRepresentation()); 
                }
            int indice = 0;//índice auxiliar que nos ayudará a poner cada equipo en su hueco correspondiente
            for (PartidoEliminatoria partidoSig :(ArrayList<PartidoEliminatoria>) Jsiguiente.getListaPartidos()){
                //Si hay tercer y cuarto puesto y son semifinales modificamos la forma de recoger los equipos, pues ahora están también los que han perdido en el ArrayList
                if(jor == (getNumEliminatorias()-1)  && isTercerCuartoPuesto() == true){
                    partidoSig.setLocal(getAuxEquipos().get(indice));
                    partidoSig.setVisitante(getAuxEquipos().get(indice+2));
                    indice+=1;
                }else{
                    partidoSig.setLocal(getAuxEquipos().get(indice));
                    partidoSig.setVisitante(getAuxEquipos().get(indice+1));
                    indice+=2;
                }
            }
            jor++;//Añadimos una jornada para que pueda salir del bucle
        }//End while
     }//End of calcularNuevaEliminatoria()    

    /**
     * Calcula el número de jornadas que se utilizarán en la eliminatoria
     * @param equipos
     * @param idaVuelta
     * @return 
     */
    public int calcularNumeroEliminatorias(ArrayList<String> equipos, boolean idaVuelta){
        boolean salida = true;//true para que siga con los ciclos
        int jornadas = 1;//Definimos la jornada y vamos sumando conforme a lo que toque
        int indice = 1;
        do{
           
           if(equipos.size() > Math.pow(2, indice)){//2 es la base
               //Añadimos una jornada y aumentamos el indice en 1
               jornadas++;
               indice++;
           }else{
               //salimos del bucle
               salida = false;
           }             
        }while(salida);
        
        //Si hay ida y vuelta devolvemos el doble de jornadas
        if(idaVuelta){
            return (jornadas*2);
        }else{
            return jornadas;
        }
        
    }
    
    public void actualizarNuevaEliminatoriaIdaVuelta(){ 
        int jor = 1;
        //System.out.println(calendario.getCalendario().size());
        //Mientras haya eliminatorias vamos creando jornadas
        while(jor < getNumEliminatorias()){
            getAuxEquipos().clear();//vaciamos el arrayList auxiliar
            Jornada Jsiguiente = getCalendario().jornadas.get(jor);//Una más que la jornada (sería -1 pues es un array)
            //Comprobamos la jornada actual y añadimos los equipos ganadores al nuevo ArrayList con el que formaremos la siguiente ronda
            Jornada J = getCalendario().jornadas.get(jor - 1);
            if(jor%2 != 0){
                //Ignoramos instrucciones con jornada impar
            }else{
            //Definimos el tamaño del Arraylist en caso de que la Eliminatoria sea la última y la opción tercerCuartoPuesto sea verdadera para poder añadir los equipos con posición 2,3
            for (PartidoEliminatoria partido :(ArrayList<PartidoEliminatoria>) J.getListaPartidos())
                //Si el local o el visitante no tienen rival les asignamos como ganadores
                if(partido.getGanador().equalsIgnoreCase("LOCAL")){
                    getAuxEquipos().add(partido.getLocal());
                   //Si hay tercer y cuarto puesto y son semifinales añadimos también al Arraylist el equipo derrotado
                   if(jor == (getNumEliminatorias()-2)  && isTercerCuartoPuesto() == true){
                        getAuxEquipos().add(partido.getVisitante());
                   }
                }else if(partido.getGanador().equalsIgnoreCase("VISITANTE")){
                    //Si hay tercer y cuarto puesto y son semifinales añadimos también al Arraylist el equipo derrotado
                    getAuxEquipos().add(partido.getVisitante());
                   if(jor == (getNumEliminatorias()-2)  && isTercerCuartoPuesto() == true){
                        getAuxEquipos().add(partido.getLocal());
                   }
                }else{
                    //getModelo().addRow(partido.getTableRepresentation()); 
                }
            int indice = 0;//índice auxiliar que nos ayudará a poner cada equipo en su hueco correspondiente
            for (PartidoEliminatoria partidoSig :(ArrayList<PartidoEliminatoria>) Jsiguiente.getListaPartidos()){
                //Si hay tercer y cuarto puesto y son semifinales modificamos la forma de recoger los equipos, pues ahora están también los que han perdido en el ArrayList
                    if(jor == (getNumEliminatorias()-2)  && isTercerCuartoPuesto() == true){
                        partidoSig.setLocal(getAuxEquipos().get(indice));
                        partidoSig.setVisitante(getAuxEquipos().get(indice+2));
                        indice+=1;
                    }else{
                        partidoSig.setLocal(getAuxEquipos().get(indice));
                        partidoSig.setVisitante(getAuxEquipos().get(indice+1));
                        indice+=2;
                    }
            }
            
            //Comprobamos la jornada actual y añadimos los equipos ganadores al nuevo ArrayList con el que formaremos la siguiente ronda
            Jornada J2 = getCalendario().jornadas.get(jor);
            Jsiguiente = getCalendario().jornadas.get(jor+1);
            getAuxEquipos().clear();//vaciamos el arrayList auxiliar
            //Definimos el tamaño del Arraylist en caso de que la Eliminatoria sea la última y la opción tercerCuartoPuesto sea verdadera para poder añadir los equipos con posición 2,3
            for (PartidoEliminatoria partido :(ArrayList<PartidoEliminatoria>) J2.getListaPartidos()){
                    getAuxEquipos().add(partido.getVisitante());
                    getAuxEquipos().add(partido.getLocal());
            }
            indice = 0;//índice auxiliar que nos ayudará a poner cada equipo en su hueco correspondiente
            for (PartidoEliminatoria partidoSig :(ArrayList<PartidoEliminatoria>) Jsiguiente.getListaPartidos()){
                //Si hay tercer y cuarto puesto y son semifinales modificamos la forma de recoger los equipos, pues ahora están también los que han perdido en el ArrayList
                if(jor == (getNumEliminatorias()-2)  && isTercerCuartoPuesto() == true){
                    //Para IdayVuelta es diferente, invertimos el +1 y el +2
                    partidoSig.setLocal(getAuxEquipos().get(indice));
                    partidoSig.setVisitante(getAuxEquipos().get(indice+1));
                    indice+=2;
                }else{
                    partidoSig.setLocal(getAuxEquipos().get(indice));
                    partidoSig.setVisitante(getAuxEquipos().get(indice+1));
                    indice+=2;
                }
            }
            }//if jor%2
            jor++;//Añadimos una jornada para que pueda salir del bucle
        }//End while
     }//End of calcularNuevaEliminatoria()   
     
     /**
      * Método que guarda los datos en un archivo XML
      * @param ruta
      * @param nombreArchivo
      */
     @Override
     public void guardarXML(String ruta, String nombreArchivo){
         
        //Variables auxiliaes
        int num_partidos;
        int num_equipos;
        
         try {
 
        Document doc = new Document();
        Element xml_torneo = new Element("torneo");
        doc.setRootElement(xml_torneo);
        
        //Elementos principales del torneo
        Element xml_nombreTorneo = new Element("nombreTorneo");
        xml_nombreTorneo.setText(nombreTorneo);
        doc.getRootElement().addContent(xml_nombreTorneo);
        
        Element xml_tipoTorneo = new Element("tipoTorneo");
        xml_tipoTorneo.setText(Integer.toString(tipoTorneo));
        doc.getRootElement().addContent(xml_tipoTorneo);
        
        Element xml_deporte = new Element("deporte");
        xml_deporte.setText(Integer.toString(deporte));
        doc.getRootElement().addContent(xml_deporte);
        
        Element xml_num_jornadas = new Element("jornadas");
        xml_num_jornadas.setText(Integer.toString(num_jornadas));
        doc.getRootElement().addContent(xml_num_jornadas);
        
        Element xml_idaVuelta = new Element("idaVuelta");
        xml_idaVuelta.setText(Boolean.toString(idaVuelta));
        doc.getRootElement().addContent(xml_idaVuelta);
        
        Element xml_sets = new Element("sets");
        xml_sets.setText(Integer.toString(sets));
        doc.getRootElement().addContent(xml_sets);
        
        Element xml_sorteo = new Element("sorteo");
        xml_sorteo.setText(Boolean.toString(sorteo));
        doc.getRootElement().addContent(xml_sorteo);
        
        Element xml_tercerCuartoPuesto = new Element("tercerCuartoPuesto");
        xml_tercerCuartoPuesto.setText(Boolean.toString(tercerCuartoPuesto));
        doc.getRootElement().addContent(xml_tercerCuartoPuesto);
        
        Element xml_sanciones = new Element("sanciones");   
        doc.getRootElement().addContent(xml_sanciones);
   
        //Calendario y jornadas con sus partidos
        Element xml_calendario = new Element("calendario");       
        for (int i = 0; i < num_jornadas; i++) {          
            //Recorremos todos los partidos del objeto jornada (número i) para obtener los datos de cada partido y asginarlos a los elementos
            Jornada J = getCalendario().jornadas.get(i);//i porque es un índice
            
                Element xml_jornada = new Element("jornada");
                //Añadimos como atributo el número de jornada                
                xml_jornada.setAttribute(new Attribute("numero", Integer.toString(i+1)));
               
                //Ahora usamos el for-each y obtenemos la lista de partidos de este objeto Jornada
                for (PartidoEliminatoriaTenis5Sets partido :(ArrayList<PartidoEliminatoriaTenis5Sets>) J.getListaPartidos()){ 
                    
                    //Creamos un elemento partido por cada partido en la lista
                    Element xml_partido = new Element("partido");
                    
                    xml_partido.addContent(new Element("fecha").setText(partido.getFecha()));
                    xml_partido.addContent(new Element("hora").setText(partido.getHora()));
                    xml_partido.addContent(new Element("local").setText(partido.getLocal()));
                    xml_partido.addContent(new Element("set1L").setText((Integer.toString(partido.getSet1L()))));//Convertimos los valores enteros a String
                    xml_partido.addContent(new Element("set1V").setText((Integer.toString(partido.getSet1V()))));
                    xml_partido.addContent(new Element("set2L").setText((Integer.toString(partido.getSet2L()))));
                    xml_partido.addContent(new Element("set2V").setText((Integer.toString(partido.getSet2V()))));
                    xml_partido.addContent(new Element("set3L").setText((Integer.toString(partido.getSet3L()))));
                    xml_partido.addContent(new Element("set3V").setText((Integer.toString(partido.getSet3V()))));
                    xml_partido.addContent(new Element("set4L").setText((Integer.toString(partido.getSet4L()))));
                    xml_partido.addContent(new Element("set4V").setText((Integer.toString(partido.getSet4V()))));
                    xml_partido.addContent(new Element("set5L").setText((Integer.toString(partido.getSet5L()))));
                    xml_partido.addContent(new Element("set5V").setText((Integer.toString(partido.getSet5V()))));
                    xml_partido.addContent(new Element("visitante").setText(partido.getVisitante()));
                    xml_partido.addContent(new Element("pista").setText(partido.getPista()));
                    xml_partido.addContent(new Element("ganador").setText(partido.getGanador()));
                    xml_partido.addContent(new Element("desempate").setText(partido.getDesempate()));  
                    
                    xml_jornada.addContent(xml_partido);
                }           
                //Añadimos la jornada al documento XML
                xml_calendario.addContent(xml_jornada); 
  
        }      
        doc.getRootElement().addContent(xml_calendario);
        
        //Añadimos ahora los datos de la clasificación
        num_equipos = castEquipoLista.getEquipos().size();
        
        Element xml_clasificacion = new Element("clasificacion");
        
        for (EquipoTenis equipo: (ArrayList<EquipoTenis>)getCastEquipoLista().getEquipos())
            //Si el local o el visitante descansan ignoramos la acción
            if(equipo.getNombre().equalsIgnoreCase("EquipoFantasma")){
                //No hagas nada pues no este equipo es el comodín para los torneos impares
            }else{
                Element xml_equipo = new Element("equipo");
                
                xml_equipo.addContent(new Element("numero").setText(Integer.toString(equipo.getNumero())));
                xml_equipo.addContent(new Element("team").setText(equipo.getNombre()));
                xml_equipo.addContent(new Element("posicion").setText(Integer.toString(equipo.getPosicion()+1)));
                xml_equipo.addContent(new Element("pj").setText(Integer.toString(equipo.getPj())));
                xml_equipo.addContent(new Element("pg").setText(Integer.toString(equipo.getPg())));
                xml_equipo.addContent(new Element("pp").setText(Integer.toString(equipo.getPp())));
                xml_equipo.addContent(new Element("sf").setText(Integer.toString(equipo.getSf())));
                xml_equipo.addContent(new Element("sc").setText(Integer.toString(equipo.getSc())));
                xml_equipo.addContent(new Element("jf").setText(Integer.toString(equipo.getJf())));
                xml_equipo.addContent(new Element("jc").setText(Integer.toString(equipo.getJc())));
                
                xml_clasificacion.addContent(xml_equipo);
            }     
        
        
        doc.getRootElement().addContent(xml_clasificacion);
 
        // new XMLOutputter().output(doc, System.out);
        XMLOutputter xmlOutput = new XMLOutputter();
 
        // display nice nice
        xmlOutput.setFormat(Format.getPrettyFormat());
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(ruta+"/"+nombreArchivo),"UTF8"));//Lo utiliamos para asignar utf-8 (así funciona)
        
        //Creamos el archivo xml con FileWriter(el formato se supone que ya viene dado)
        //xmlOutput.output(doc, new FileWriter(ruta+"/"+nombreArchivo)); //"competiciones/torneo.xml"
        xmlOutput.output(doc, out);//resuelve los problemas de encoding utf-8 que se daban fuera de Netbeans
 
        JOptionPane.showMessageDialog(null, "<html>Archivo <b>" + nombreArchivo + "</b> guardado con éxito</html>", "Guardar Archivo", JOptionPane.INFORMATION_MESSAGE, null);
      } catch (IOException io) {
        System.out.println(io.getMessage());
      }
     
    }//End of guardarXML()
    
    /**
     * Genera un informe de la jornada actual en PDF
     * @param rutaPDF
     * @param nombreArchivo
     * @param aplazados
     * @param partidosAplazados
     */
    @Override
    public void informeJornada(String rutaPDF, String nombreArchivo, boolean aplazados, ArrayList<Partido> partidosAplazados){
       //lo primero de todo nos aseguramos de que ha guardado los datos de la tabla
       this.guardarTabla();
       //Creamos un HashMap para poder utilizar los parámetros en el informe
       HashMap<String, Object> parametros = new HashMap<>();
       String descansa = "";//Obtenemos el nombre del equipo que descansa        
       DataSourceFutbolJornada datasource = new DataSourceFutbolJornada();  
       if(aplazados){
            //Añadimos los partidos aplazados al datasource
             for (int i = 0; i < partidosAplazados.size(); i++) {
                Partido P = (Partido) partidosAplazados.get(i);
                datasource.addPartido(P);
             }
        }else{
            Jornada J = getCalendario().jornadas.get(getJornada() - 1);//coge la jornada como variable global, -1 porque es un índice
             //Añadimos los partidos de la jornada al datasource
                 for (int i = 0; i < J.getListaPartidos().size(); i++) {
                     Partido P = (Partido) J.partidos.get(i);
                     //Comprobamos si descansa algún equipo y guardamos su nombre en la variable indicada
                     if(P.getLocal().equalsIgnoreCase("EquipoFantasma")){
                         descansa = P.getVisitante();
                     }else if(P.getVisitante().equalsIgnoreCase("EquipoFantasma")){
                         descansa = P.getLocal();
                     }else{
                       datasource.addPartido(P);
                     } 
                 }  
        }//end if aplazados  
        
        File archivoInforme = new File("src/informes/eliFutbolJornadaPista.jasper");
        JasperReport reporte;
        parametros.put("nombreTorneo", nombreTorneo);
        if(aplazados){
            parametros.put("jornada", "Partidos Aplazados");
        }else{
            parametros.put("jornada", nombresEliminatorias[calendario.jornadas.size() - jornada]);
        }
        try {
            reporte = (JasperReport) JRLoader.loadObject(archivoInforme);
            JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parametros, datasource);  
            JRExporter exporter = new JRPdfExporter();  
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            //En función del nombre del archivo lo nombramos de una forma o de otra
            if(nombreArchivo.equalsIgnoreCase("")){
                exporter.setParameter(JRExporterParameter.OUTPUT_FILE, new java.io.File(rutaPDF + "/" + nombreTorneo + "_JOR" + jornada + ".pdf")); 
            }else{
                exporter.setParameter(JRExporterParameter.OUTPUT_FILE, new java.io.File(rutaPDF + "/" + nombreArchivo));
            }
            exporter.exportReport();
            if(aplazados){
                JOptionPane.showMessageDialog(null, "<html>El documento de partidos aplazados <b>" + nombreArchivo + "</b> se ha creado correctamente</html>", "Se creó un documento con éxito", JOptionPane.INFORMATION_MESSAGE, null);
             }else{
                JOptionPane.showMessageDialog(null, "<html>El documento <b>" + nombreArchivo + "</b> se ha creado correctamente</html>", "Se creó un documento con éxito", JOptionPane.INFORMATION_MESSAGE, null);
             }
        } catch (JRException ex) {
            Logger.getLogger(MetodosEliminatoriaTenis5Sets.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//End of informeJornada()
    
    /**
     * Genera un informe de resultados de la jornada actual en PDF
     * @param rutaPDF
     * @param nombreArchivo
     * @param aplazados
     * @param partidosAplazados
     */
    @Override
    public void informeResultados(String rutaPDF, String nombreArchivo, boolean aplazados, ArrayList<Partido> partidosAplazados){
        //lo primero de todo nos aseguramos de que ha guardado los datos de la tabla
       this.guardarTabla();
       //Creamos un HashMap para poder utilizar los parámetros en el informe
       HashMap<String, Object> parametros = new HashMap<>();
       String descansa = "";//Obtenemos el nombre del equipo que descansa        
       DataSourceEliminatoriasTenis5SetsResultados datasource = new DataSourceEliminatoriasTenis5SetsResultados();  
       if(aplazados){
            //Añadimos los partidos aplazados al datasource
             for (int i = 0; i < partidosAplazados.size(); i++) {
                PartidoEliminatoriaTenis5Sets P = (PartidoEliminatoriaTenis5Sets) partidosAplazados.get(i);
                datasource.addPartido(P);
             }
       }else{
            Jornada J = getCalendario().jornadas.get(getJornada() - 1);//coge la jornada como variable global, -1 porque es un índice
             //Añadimos los partidos de la jornada al datasource
                 for (int i = 0; i < J.getListaPartidos().size(); i++) {
                     PartidoEliminatoriaTenis5Sets P = (PartidoEliminatoriaTenis5Sets) J.partidos.get(i);
                     //Comprobamos si descansa algún equipo y guardamos su nombre en la variable indicada
                     if(P.getLocal().equalsIgnoreCase("EquipoFantasma")){
                         descansa = P.getVisitante();
                     }else if(P.getVisitante().equalsIgnoreCase("EquipoFantasma")){
                         descansa = P.getLocal();
                     }else{
                       datasource.addPartido(P);
                     } 
                 }  
       }//end if aplazados 
        
        File archivoInforme = new File("src/informes/eliTenis5SResultados.jasper");
        JasperReport reporte;
        parametros.put("nombreTorneo", nombreTorneo);
        if(aplazados){
            parametros.put("jornada", "Partidos Aplazados");
        }else{
            parametros.put("jornada", nombresEliminatorias[calendario.jornadas.size() - jornada]);
        }
        try {
            reporte = (JasperReport) JRLoader.loadObject(archivoInforme);
            JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parametros, datasource);  
            JRExporter exporter = new JRPdfExporter();  
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            //En función del nombre del archivo lo nombramos de una forma o de otra
            if(nombreArchivo.equalsIgnoreCase("")){
                exporter.setParameter(JRExporterParameter.OUTPUT_FILE, new java.io.File(rutaPDF + "/" + nombreTorneo + "_RES" + jornada + ".pdf")); 
            }else{
                exporter.setParameter(JRExporterParameter.OUTPUT_FILE, new java.io.File(rutaPDF + "/" + nombreArchivo));
            }
            exporter.exportReport();
            if(aplazados){
                JOptionPane.showMessageDialog(null, "<html>El documento de partidos aplazados <b>" + nombreArchivo + "</b> se ha creado correctamente</html>", "Se creó un documento con éxito", JOptionPane.INFORMATION_MESSAGE, null);
             }else{
                JOptionPane.showMessageDialog(null, "<html>El documento <b>" + nombreArchivo + "</b> se ha creado correctamente</html>", "Se creó un documento con éxito", JOptionPane.INFORMATION_MESSAGE, null);
             }
        } catch (JRException ex) {
            Logger.getLogger(MetodosEliminatoriaTenis5Sets.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//End informeResultados()
    
    /**
     * Genera un informe de la clasificación actual en PDF
     * @param rutaPDF
     * @param nombreArchivo
     */
    @Override
    public void informeClasificacion(String rutaPDF, String nombreArchivo){
        //Las eliminatorias no utilizan clasificación
    }//End informeClasificacion()
    
     /**
     * Genera un informe del calendario del torneo en PDF
     * @param rutaPDF
     * @param nombreArchivo
     */
    @Override
    public void informeCalendario(String rutaPDF, String nombreArchivo){
        //lo primero de todo nos aseguramos de que ha guardado los datos de la tabla
        this.guardarTabla();
        int jornadaActual = jornada;//guardamos el valor de la jornada actual en una variable auxiliar
        int numero_jornadas = calendario.jornadas.size();
        int num_partidos = calendario.jornadas.get(0).partidos.size();//la jornada 0 es de ejemplo, solo hace falta para conseguir el número de partidos(igual en todas las jornadas)
        File archivoInforme;//archivo.jasper
        JRXmlDataSource datasource;
        //Variables necesarias para mostrar la vuelta de la ronda final
        int opcion;
        ArrayList<String> neAList = new ArrayList<>();
        Boolean mostrar =  true;
        try {
            /*** Evitamos que muestre la jornada de vuelta de la última ronda si así lo desea el usuario ***/
            if(idaVuelta){//si hay vuelta consideramos la opción (de lo contrario no)
                opcion =  JOptionPane.showConfirmDialog(null, "<html>¿Desea que el calendario muestre la <b>jornada de vuelta de la ronda final</b>?</html>", "Advertencia",JOptionPane.YES_NO_OPTION);
                if(JOptionPane.YES_OPTION == opcion){
                    for(int i = 1; i <= calendario.jornadas.size(); i++){// i=1 para que la resta de 0 y utilice ese índice
                        neAList.add(nombresEliminatorias[calendario.jornadas.size() - i]);
                    }
                }else{
                    //si no vamos a mostrar la vuelta modificamos el array nombresEliminatorias[]
                    if(tercerCuartoPuesto){
                        nombresEliminatorias = new String[] {"Final - 3º y 4º Puesto", "Semifinales - Vuelta", "Semifinales", "Cuartos de Final - Vuelta", "Cuartos de Final", "Octavos de Final - Vuelta", "Octavos de Final", "Dieciseisavos de Final - Vuelta", "Dieciseisavos de Final", "Treintaidosavos de Final - Vuelta", "Treintaidosavos de Final", "64avos de Final - Vuelta", "64avos de Final", "128avos de Final - Vuelta", "128avos de Final", "256avos de Final - Vuelta", "256avos de Final",  "512avos de Final - Vuelta", "512avos de Final"}; 
                    }else{
                        nombresEliminatorias = new String[] {"Final", "Semifinales - Vuelta", "Semifinales", "Cuartos de Final - Vuelta", "Cuartos de Final", "Octavos de Final - Vuelta", "Octavos de Final", "Dieciseisavos de Final - Vuelta", "Dieciseisavos de Final", "Treintaidosavos de Final - Vuelta", "Treintaidosavos de Final", "64avos de Final - Vuelta", "64avos de Final", "128avos de Final - Vuelta", "128avos de Final", "256avos de Final - Vuelta", "256avos de Final",  "512avos de Final - Vuelta", "512avos de Final"};          
                    }//end if
                    for(int i = 1; i <= calendario.jornadas.size() - 1; i++){// i=1 para que la resta de 0 y utilice ese índice
                        neAList.add(nombresEliminatorias[calendario.jornadas.size() - (i + 1)]);
                    }//end for
                    mostrar = false;
                }//end if
            }else{
                for(int i = 1; i <= calendario.jornadas.size(); i++){// i=1 para que la resta de 0 y utilice ese índice
                        neAList.add(nombresEliminatorias[calendario.jornadas.size() - i]);
                    }
            }//end if idaVuelta
            
            //Pasamos la ruta del archivo XML y la expresión XPath que ha de utilizar el informe
            datasource = new JRXmlDataSource(rutaXMLCalendar, "torneo/calendario/jornada/partido");
            jornada = 1;//Ponemos la jornada en la primera para que el while actue con todas las jornadas
            //Según el número de partidos que tenga cada jornada cargaremos el .jasper normal o el especial para grandes torneos
            if(num_partidos > 40){//40 es el limite de campos que le hemos puesto al page break en el futbolCalendario.jasper
                if(mostrar){
                    archivoInforme = new File("src/informes/eliTenisCalendarioBIG.jasper");
                }else{
                    archivoInforme = new File("src/informes/eliTenisCalendarioBIGNo.jasper");//No muestra la vuelta de la ronda final
                }//end if
            }else{
                if(mostrar){
                    archivoInforme = new File("src/informes/eliTenisCalendario.jasper");
                }else{
                    archivoInforme = new File("src/informes/eliTenisCalendarioNo.jasper");//No muestra la vuelta de la ronda final
                }//end if              
            }
            JasperReport reporte;
        
            //Creamos un HashMap para poder utilizar los parámetros en el informe
             HashMap<String, Object> parametros = new HashMap<>();
            parametros.put("nombreTorneo", nombreTorneo);
            parametros.put("listaJornadas", neAList);
            parametros.put("num_partidos", num_partidos);
            parametros.put("num_jornadas", num_jornadas);

            reporte = (JasperReport) JRLoader.loadObject(archivoInforme);
            JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parametros, datasource);
            //jasperPrint.addPage(jasperPrint.getPages().get(jornada - 1));
            JRExporter exporter = new JRPdfExporter();  
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_FILE, new java.io.File(rutaPDF + "/" + nombreArchivo));
            exporter.exportReport();
            JOptionPane.showMessageDialog(null, "<html>El documento <b>" + nombreArchivo + "</b> se ha creado correctamente</html>", "Se creó un documento con éxito", JOptionPane.INFORMATION_MESSAGE, null);
            } catch (JRException ex) {
                Logger.getLogger(MetodosEliminatoriaTenis5Sets.class.getName()).log(Level.SEVERE, null, ex);
            }
        //Al terminar el ciclo le devolvemos a la jornada su valor actual
        jornada = jornadaActual;
    }//End informeCalendario()
    
    /**
     * Método que recoge de principal el valor de la ruta del archivo XML que se utilizara para el calendario PDF (así evitamos incongruencias entre archivos)
     * @param rutaXMLCalendar
     */
    @Override
    public void getRutaXMLCalendar(String rutaXMLCalendar){
        this.rutaXMLCalendar = rutaXMLCalendar;
    }//end getRutaXMLCalendar();
    
    /**
     * Genera los informes de todas las jornadas del torneo en PDF
     * @param rutaPDF
     * @param nombreArchivo
     */
    @Override
    public void informeTodasJornadas(String rutaPDF, String nombreArchivo){
        //lo primero de todo nos aseguramos de que ha guardado los datos de la tabla
        this.guardarTabla();
        int jornadaActual = jornada;//guardamos el valor de la jornada actual en una variable auxiliar
        jornada = 1;//Ponemos la jornada en la primera para que el while actue con todas las jornadas
        String descansa = "";//Obtenemos el nombre del equipo que descansa 
        do{//repetimos el ciclo mientras queden jornadas en el torneo
            Jornada J = getCalendario().jornadas.get(getJornada() - 1);//coge la jornada como variable global, -1 porque es un índice    
            DataSourceFutbolJornada datasource = new DataSourceFutbolJornada();  
            //Añadimos los partidos de la jornada al datasource
            for (int i = 0; i < J.getListaPartidos().size(); i++) {
                Partido P = (Partido) J.partidos.get(i);
                //Comprobamos si descansa algún equipo y guardamos su nombre en la variable indicada
                if(P.getLocal().equalsIgnoreCase("EquipoFantasma")){
                    descansa = P.getVisitante();
                }else if(P.getVisitante().equalsIgnoreCase("EquipoFantasma")){
                    descansa = P.getLocal();
                }else{
                  datasource.addPartido(P);
                } 
            }  

            File archivoInforme = new File("src/informes/eliFutbolJornadaPista.jasper");
            JasperReport reporte;
            //Creamos un HashMap para poder utilizar los parámetros en el informe
            HashMap<String, Object> parametros = new HashMap<>();
            parametros.put("nombreTorneo", nombreTorneo);
            parametros.put("jornada", nombresEliminatorias[calendario.jornadas.size() - jornada]);
            //Si el número de equipos es impar añadimos el texto DESCANSA: y el nombre del equipo que descansa al informe, de lo contrario mejor que no aparezca
            if(castEquipoLista.getEquipos().size() % 2 != 0){
                parametros.put("equipoDescansa", descansa);
            }else{
                parametros.put("equipoDescansa", "");
            }
            try {
                reporte = (JasperReport) JRLoader.loadObject(archivoInforme);
                JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parametros, datasource);  
                JRExporter exporter = new JRPdfExporter();  
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_FILE, new java.io.File(rutaPDF + "/" + "JOR" + jornada + "_" + nombreArchivo));
                exporter.exportReport();
            } catch (JRException ex) {
                Logger.getLogger(MetodosEliminatoriaTenis5Sets.class.getName()).log(Level.SEVERE, null, ex);
            }
            jornada++;
        }while(jornada <= calendario.jornadas.size());
        //Al terminar el ciclo le devolvemos a la jornada su valor actual
        jornada = jornadaActual;
        JOptionPane.showMessageDialog(null, "<html>Los documentos <b>" + nombreArchivo + "</b> se han creado correctamente</html>", "Se crearon documentos con éxito", JOptionPane.INFORMATION_MESSAGE, null);
    }//End informeTodasJornadas()
    
    /**
     * Genera los informes de todos los resultados del torneo en PDF
     * @param rutaPDF
     * @param nombreArchivo
     */
    @Override
    public void informeTodosResultados(String rutaPDF, String nombreArchivo){
        //lo primero de todo nos aseguramos de que ha guardado los datos de la tabla
        this.guardarTabla();
        int jornadaActual = jornada;//guardamos el valor de la jornada actual en una variable auxiliar
        jornada = 1;//Ponemos la jornada en la primera para que el while actue con todas las jornadas
        String descansa = "";//Obtenemos el nombre del equipo que descansa 
        do{//repetimos el ciclo mientras queden jornadas en el torneo
            Jornada J = getCalendario().jornadas.get(getJornada() - 1);//coge la jornada como variable global, -1 porque es un índice    
            DataSourceEliminatoriasTenis5SetsResultados datasource = new DataSourceEliminatoriasTenis5SetsResultados();  
            //Añadimos los partidos de la jornada al datasource
            for (int i = 0; i < J.getListaPartidos().size(); i++) {
                PartidoEliminatoriaTenis5Sets P = (PartidoEliminatoriaTenis5Sets) J.partidos.get(i);
                //Comprobamos si descansa algún equipo y guardamos su nombre en la variable indicada
                if(P.getLocal().equalsIgnoreCase("EquipoFantasma")){
                    descansa = P.getVisitante();
                }else if(P.getVisitante().equalsIgnoreCase("EquipoFantasma")){
                    descansa = P.getLocal();
                }else{
                  datasource.addPartido(P);
                } 
            }  

            File archivoInforme = new File("src/informes/eliTenis5SResultados.jasper");
            JasperReport reporte;
            //Creamos un HashMap para poder utilizar los parámetros en el informe
            HashMap<String, Object> parametros = new HashMap<>();
            parametros.put("nombreTorneo", nombreTorneo);
            parametros.put("jornada", nombresEliminatorias[calendario.jornadas.size() - jornada]);
            //Si el número de equipos es impar añadimos el texto DESCANSA: y el nombre del equipo que descansa al informe, de lo contrario mejor que no aparezca
            if(castEquipoLista.getEquipos().size() % 2 != 0){
                parametros.put("equipoDescansa", descansa);
            }else{
                parametros.put("equipoDescansa", "");
            }
            try {
                reporte = (JasperReport) JRLoader.loadObject(archivoInforme);
                JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parametros, datasource);  
                JRExporter exporter = new JRPdfExporter();  
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_FILE, new java.io.File(rutaPDF + "/" + "RES" + jornada + "_" + nombreArchivo));
                exporter.exportReport();
            } catch (JRException ex) {
                Logger.getLogger(MetodosEliminatoriaTenis5Sets.class.getName()).log(Level.SEVERE, null, ex);
            }
            jornada++;
        }while(jornada <= calendario.jornadas.size());
        //Al terminar el ciclo le devolvemos a la jornada su valor actual
        jornada = jornadaActual;
        JOptionPane.showMessageDialog(null, "<html>Los documentos <b>" + nombreArchivo + "</b> se han creado correctamente</html>", "Se crearon documentos con éxito", JOptionPane.INFORMATION_MESSAGE, null);
    }//End informeTodosResultados()
    
    /**
     * Recupera el valor de los anchos de la tabla en el archivo datosTablas.txt
     * @param tamaño
     * @param codigo
     * @return 
     */
    @Override
    public int[] getAnchoTabla(int tamaño, int codigo){
        String cadena;
        String[] aux;//guarda el split de la linea
        int[] numAnchos = new int[tamaño];
        int i = 0;
        try {
            FileReader reader = new FileReader("datosTablas.txt");
            BufferedReader buffer = new BufferedReader(reader);
            while((cadena = buffer.readLine())!=null) {
              //Si coincide con el codigo es que estamos en la sección correcta del archivo (y mientras el contador no sobrepase el tamaño de la tabla)
              if(cadena.contains("anchoW"+Integer.toString(i+codigo)) && i < tamaño){
                  aux = cadena.split("=");
                  numAnchos[i] = Integer.parseInt(aux[1]);
                  i++;
                  Arrays.fill(aux, null);//vaciamos el array auxiliar
              }else{
                  //si no hay coincidencia ignoramos la información
              }
            }
            buffer.close();
            return numAnchos;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MetodosEliminatoriaTenis5Sets.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MetodosEliminatoriaTenis5Sets.class.getName()).log(Level.SEVERE, null, ex);
        }
        return numAnchos;
    }//End of getAnchoTabla()
    
    /**
     * Recupera el valor del alto de las filas de la tabla en el archivo datosTablas.txt
     * @param codigo
     * @return 
     */
    @Override
    public int getAltoTabla(int codigo){
        String alto;
        String[] aux;//guarda el split de la linea
        int numAlto = 0;
        int i = 0;
        try {
            FileReader reader = new FileReader("datosTablas.txt");
            BufferedReader buffer = new BufferedReader(reader);
            while((alto = buffer.readLine())!=null) {
              //Si coincide con el codigo es que estamos en la sección correcta del archivo (y mientras el contador no sobrepase el tamaño de la tabla)
              if(alto.contains("altoH"+Integer.toString(codigo))){
                  aux = alto.split("=");
                  numAlto = Integer.parseInt(aux[1]);
              }else{
                  //si no hay coincidencia ignoramos la información
              }
            }
            buffer.close();
            return numAlto;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MetodosEliminatoriaTenis5Sets.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MetodosEliminatoriaTenis5Sets.class.getName()).log(Level.SEVERE, null, ex);
        }
        return numAlto;
    }//End of getAltoTabla()
    
    /**
     * Método que modifica los datos del tamaño de la tabla en el archivo datosTablas.txt
     * @param valores
     * @param altura
     */
    @Override
    public void setDatosTabla(int[] valores, int altura){
        String cadena;
        String[] aux;//guarda el split de la linea
        int i = 0;
        FileWriter writer;
        PrintWriter pw;
        ArrayList<String> almacen = new ArrayList<>();
        try {
            FileReader reader = new FileReader("datosTablas.txt");
            BufferedReader buffer = new BufferedReader(reader);
            //RandomAccessFile raf = new RandomAccessFile("datosTablas.txt", "rw");//rw es lectura/escritura (finalmente no utilizaremos el raf)
            //Leemos el texto hasta que haya coincidencia
            while((cadena = buffer.readLine())!=null) {
              cadena = cadena.trim();//limpiamos los posibles espacios
              //Si coincide con el codigo es que estamos en la sección correcta del archivo (y mientras el contador no sobrepase el tamaño de la tabla)
              if(cadena.startsWith("anchoW"+Integer.toString(i+CODIGO_ANCHO)) && i < valores.length){
                  aux = cadena.split("=");
                  aux[1] = valueOf(valores[i]);
                  //Guardamos en el ArrayList las líneas modificadas del ancho
                  almacen.add(aux[0] + "=" + aux[1]);
                  i++;
                  Arrays.fill(aux, null);//vaciamos el array auxiliar
              }else if(cadena.startsWith("altoH"+Integer.toString(CODIGO_ALTO))){
                aux = cadena.split("=");
                aux[1] = valueOf(altura);
                //Guardamos en el ArrayList la línea modificadas del alto
                almacen.add(aux[0] + "=" + aux[1]);
              }else{
                  //Guardamos en el ArrayList las líneas que quedan intactas
                almacen.add(cadena);
              }//end if          
            }//end while
            //reseteamos el indice y reescribimos todo el fichero usando el ArrayList
            buffer.close();
            //Hay que declarar aquí los writer porque si lo hacemos antes nos borra el fichero y no lo podemos leer (se encontrará vacío)
            writer = new FileWriter("datosTablas.txt");
            pw = new PrintWriter(writer);
            for(i = 0; i < almacen.size(); i++){
               pw.println(almacen.get(i));
            }
           writer.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MetodosEliminatoriaTenis5Sets.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MetodosEliminatoriaTenis5Sets.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Finalmente cargamos los datos modificados
        almacen.clear();
        this.cargarTabla(jornada);
    }//End of setDatosTabla()
    
    /**
     * En Eliminatorias este método es inútil, solo esta para poder usarlo con la interfaz
     * @param valores
     * @param alturaClasf 
     */
   @Override
    public void setDatosTablaClasf(int[] valores, int alturaClasf) {
        //No hacemos nada pues no hay clasificación en una eliminatoria
    }//End of setDatosTablaClasf()  
    
    /**
      *  MÉTODOS GETTERS AND SETTERS
      */

    /**
     * @return the modelo
     */
    @Override
    public DefaultTableModel getModelo() {
        return modelo;
    }

    /**
     * @param modelo the modelo to set
     */
    @Override
    public void setModelo(DefaultTableModel modelo) {
        this.modelo = modelo;
    }

    /**
     * @return the jornada
     */
    @Override
    public int getJornada() {
        return jornada;
    }

    /**
     * @param jornada the jornada to set
     */
    @Override
    public void setJornada(int jornada) {
        this.jornada = jornada;
    }

    /**
     * @return the TableJornada
     */
    @Override
    public JTable getTableJornada() {
        return TableJornada;
    }

    /**
     * @param TableJornada the TableJornada to set
     */
    @Override
    public void setTableJornada(JTable TableJornada) {
        this.TableJornada = TableJornada;
    }

    /**
     * @return the LabelJornada
     */
    @Override
    public JLabel getLabelJornada() {
        return LabelJornada;
    }

    /**
     * @param LabelJornada the LabelJornada to set
     */
    @Override
    public void setLabelJornada(JLabel LabelJornada) {
        this.LabelJornada = LabelJornada;
    }

    /**
     * @return the LabelTotalJornadas
     */
    @Override
    public JLabel getLabelTotalJornadas() {
        return LabelTotalJornadas;
    }

    /**
     * @param LabelTotalJornadas the LabelTotalJornadas to set
     */
    @Override
    public void setLabelTotalJornadas(JLabel LabelTotalJornadas) {
        this.LabelTotalJornadas = LabelTotalJornadas;
    }

    /**
     * @return the TextFieldJornada
     */
    @Override
    public JTextField getTextFieldJornada() {
        return TextFieldJornada;
    }

    /**
     * @param TextFieldJornada the TextFieldJornada to set
     */
    @Override
    public void setTextFieldJornada(JTextField TextFieldJornada) {
        this.TextFieldJornada = TextFieldJornada;
    }

    /**
     * @return the calendario
     */
    @Override
    public Calendario getCalendario() {
        return calendario;
    }

    /**
     * @param calendario the calendario to set
     */
    @Override
    public void setCalendario(Calendario calendario) {
        this.calendario = calendario;
    }

    /**
     * @return the equipoLista
     */
    @Override
    public EquipoLista getCastEquipoLista() {
        return castEquipoLista;
    }

    /**
     * @param equipoLista the equipoLista to set
     */
    @Override
    public void setCastEquipoLista(EquipoLista equipoLista) {
        this.castEquipoLista = equipoLista;
    }

    @Override
    public void cargarClasificacion() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void calcularClasificacion() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void borrarClasificacion() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void ordenarClasificacion() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DefaultTableModel getClasificacion() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setClasificacion(DefaultTableModel clasificacion) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public JTable getTableClasificacion() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setTableClasificacion(JTable TableClasificacion) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public JLabel getLabelEquipoDescansa() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setLabelEquipoDescansa(JLabel LabelEquipoDescansa) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @return the auxEquipos
     */
    public ArrayList<String> getAuxEquipos() {
        return auxEquipos;
    }

    /**
     * @param auxEquipos the auxEquipos to set
     */
    public void setAuxEquipos(ArrayList<String> auxEquipos) {
        this.auxEquipos = auxEquipos;
    }

    /**
     * @return the numEliminatorias
     */
    public int getNumEliminatorias() {
        return numEliminatorias;
    }

    /**
     * @param numEliminatorias the numEliminatorias to set
     */
    public void setNumEliminatorias(int numEliminatorias) {
        this.numEliminatorias = numEliminatorias;
    }

    /**
     * @return the tercerCuartoPuesto
     */
    @Override
    public boolean isTercerCuartoPuesto() {
        return tercerCuartoPuesto;
    }

    /**
     * @param tercerCuartoPuesto the tercerCuartoPuesto to set
     */
    @Override
    public void setTercerCuartoPuesto(boolean tercerCuartoPuesto) {
        this.tercerCuartoPuesto = tercerCuartoPuesto;
    }

    /**
     * @return the idaVuelta
     */
    @Override
    public boolean isIdaVuelta() {
        return idaVuelta;
    }

    /**
     * @param idaVuelta the idaVuelta to set
     */
    @Override
    public void setIdaVuelta(boolean idaVuelta) {
        this.idaVuelta = idaVuelta;
    }
    
     /**
     * @return the nombreTorneo
     */
    @Override
    public String getNombreTorneo() {
        return nombreTorneo;
    }

    /**
     * @param nombreTorneo the nombreTorneo to set
     */
    @Override
    public void setNombreTorneo(String nombreTorneo) {
        this.nombreTorneo = nombreTorneo;
    }

    /**
     * @return the tipoTorneo
     */
    @Override
    public int getTipoTorneo() {
        return tipoTorneo;
    }

    /**
     * @param tipoTorneo the tipoTorneo to set
     */
    @Override
    public void setTipoTorneo(int tipoTorneo) {
        this.tipoTorneo = tipoTorneo;
    }   

    /**
     * @return the deporte
     */
    @Override
    public int getDeporte() {
        return deporte;
    }

    /**
     * @param deporte the deporte to set
     */
    @Override
    public void setDeporte(int deporte) {
        this.deporte = deporte;
    }

    /**
     * @return the num_jornadas
     */
    @Override
    public int getNum_jornadas() {
        return num_jornadas;
    }

    /**
     * @param num_jornadas the num_jornadas to set
     */
    @Override
    public void setNum_jornadas(int num_jornadas) {
        this.num_jornadas = num_jornadas;
    }

    /**
     * @return the sets
     */
    @Override
    public int getSets() {
        return sets;
    }

    /**
     * @param sets the sets to set
     */
    @Override
    public void setSets(int sets) {
        this.sets = sets;
    }

    /**
     * @return the sorteo
     */
    @Override
    public boolean isSorteo() {
        return sorteo;
    }

    /**
     * @param sorteo the sorteo to set
     */
    @Override
    public void setSorteo(boolean sorteo) {
        this.sorteo = sorteo;
    }
       
    /**
     * @return the alturaColumna
     */
    @Override
    public int getAlturaColumna() {
        return alturaColumna;
    }

    /**
     * @param alturaColumna the alturaColumna to set
     */
    @Override
    public void setAlturaColumna(int alturaColumna) {
        this.alturaColumna = alturaColumna;
    }

    /**
     * @return the alturaClasfColumna
     */
    @Override
    public int getAlturaClasfColumna() {
        return alturaClasfColumna;
    }

    /**
     * @param alturaClasfColumna the alturaClasfColumna to set
     */
    @Override
    public void setAlturaClasfColumna(int alturaClasfColumna) {
        this.alturaClasfColumna = alturaClasfColumna;
    }

    /**
     * @return the num
     */
    @Override
    public int[] getNum() {
        return num;
    }

    /**
     * @param num the num to set
     */
    @Override
    public void setNum(int[] num) {
        this.num = num;
    }

    /**
     * @return the numc
     */
    @Override
    public int[] getNumc() {
        return numc;
    }

    /**
     * @param numc the numc to set
     */
    @Override
    public void setNumc(int[] numc) {
        this.numc = numc;
    }
    
    /**
     * @return the columnasClasf
     */
    @Override
    public String[] getColumnas() {
        return columnas;
    }

    /**
     * @param columnas the columnas to set
     */
    @Override
    public void setColumnas(String[] columnas) {
        this.columnas = columnas;
    }

    /**
     * @return the columnasClasf
     */
    @Override
    public String[] getColumnasClasf() {
        return columnasClasf;
    }

    /**
     * @param columnasClasf the columnasClasf to set
     */
    @Override
    public void setColumnasClasf(String[] columnasClasf) {
        this.columnasClasf = columnasClasf;
    }

    /**
     * @return the sancionados
     */
    @Override
    public HashMap<String, Integer> getSancionados() {
        return sancionados;
    }

    /**
     * @param sancionados the sancionados to set
     */
    @Override
    public void setSancionados(HashMap<String, Integer> sancionados) {
        this.sancionados = sancionados;
    }

}
