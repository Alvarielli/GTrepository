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

import datasources.DataSourceBasketClasificacion;
import datasources.DataSourceBeisbolResultados;
import datasources.DataSourceFutbolJornada;
import generador.Calendario;
import equipos.EquipoBasket;
import generador.EquipoLista;
import generador.Jornada;
import static generador.Principal.limitarCadena;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import partidos.Partido;
import partidos.PartidoBeisbol;
import renders.RenderBeisbol;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import static java.lang.String.valueOf;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
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
import renders.RenderBeisbolClasf;
import renders.RenderCurling;
import java.util.Arrays;
import java.util.Map;


/**
 * También sirve para Curling
 * @author Alvarielli
 */
public class MetodosBeisbol extends Metodos{
    
    //Le pasamos los valores de los objetos de Principal a esta clase
    private DefaultTableModel modelo;
    private DefaultTableModel clasificacion;
    private int jornada;
    private JTable TableJornada;
    private JTable TableClasificacion;
    private JLabel LabelEquipoDescansa;
    private JLabel LabelJornada;
    private JLabel LabelTotalJornadas;
    private JTextField TextFieldJornada;
    private Calendario calendario;
    private EquipoLista <EquipoBasket> castEquipoLista;//EquipoLista con los Equipos ya casteados en Principal (seleccionarClaseEquipo)
    private boolean mensaje = false;//Se utiliza para que el programa muestre el mensaje de error al introducir datos sólo una vez
    private int anchoTabla = 1740;//Ponemos un valor fijo y nos evitamos problemas //TableJornada.getWidth();
    private int alturaColumna = 25;//Indica la altura de la columna
    private int alturaClasfColumna = 25;//Indica la altura de las filas de la columna en la tabla de clasificación
    private RenderBeisbol render;
    private RenderCurling renderC;
    private RenderBeisbolClasf renderClasf;
    private int[] num = {8, 6, 10, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 5, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 5, 10, 8};// Definimos los anchos para las tablas en porcentaje (para que el usuario pueda modificarlas luego)
    private int[] numc = {10, 40, 10, 10, 10, 10, 10};//Deben sumar 100 siempre
    private String[] columnas;
    private String[] columnasClasf;
    
    /**
     * Para el darle formato a la tabla usamos los DefaultTableCellRenderer, uno con la tabla de jornadas y otro con la de clasificación, además fijamos el ancho de columna
     * (si lo requerimos) y también su altura definiendo un array int[] anchos. Tras asignar el modelo declaramos el Rneder y lo asignamos. Al final del método aplicamos la altura
     * y los anchos a la tabla. Lo mismo para la tabla de clasificación, pero en el método cargarClasificación().  Asignamos el AutoAjustado necesario (cargarTabla() principio)
     */
    
     /*** Variables que necesitaremos para los archivos XML ***/
    private String nombreTorneo;
    private int tipoTorneo;
    private int deporte;
    private int num_jornadas;
    private boolean idaVuelta;
    private int sets;
    private boolean sorteo;
    private boolean tercerCuartoPuesto;
    //Ruta para el calendario PDF
    private String rutaXMLCalendar = "";
    //HashMap de equipos sancionados
    private HashMap<String, Integer> sancionados = new HashMap<>();
    
    /** Variables necesarias para el tamaño de las tablas **/
    private final int CODIGO_ANCHO = 66;
    private final int CODIGO_ALTO = 7;
    private final int CODIGO_ANCHO_CLASF = 97;
    private final int CODIGO_ALTO_CLASF = 8;

    
    //En el constructor de clase le pasamos todo lo requerido para esta clase desde Principal
    public MetodosBeisbol(DefaultTableModel modelo, DefaultTableModel clasificacion, int jornada, JTable TableJornada, JTable TableClasificacion, JLabel LabelEquipoDescansa,
            JLabel LabelJornada, JLabel LabelTotalJornadas, JTextField TextFieldJornada, Calendario calendario, EquipoLista castEquipoLista, String nombreTorneo, int tipoTorneo, int deporte,
            int num_jornadas, boolean idaVuelta, int sets, boolean sorteo, boolean tercerCuartoPuesto){
    
        this.modelo = modelo;
        this.clasificacion = clasificacion;
        this.jornada = jornada;
        this.TableJornada = TableJornada;
        this.TableClasificacion = TableClasificacion;
        this.LabelEquipoDescansa = LabelEquipoDescansa;
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
        //Mostramos la clasificacion (por seguridad)
        this.TableClasificacion.setVisible(true);
    }
    
    /**
     * GB o Game Behind calcula cuantos partidos de ventaja le lleva un equipo a otro de media (ya que se juegan muchos partidos de forma revuelta)
     * Si se han jugado los mismos partidos la diferencia es la victorias del primero - las victorias del rival que comparas (EJ 26 - 21 = 5)
     * Si no supongamos Rojos 15/4 Azules 9/10 --> GB = [(15 - 9) + (10 - 4)]/2 = 6
     */
        
     /**
     * Carga los datos de la joranda pasada en la tabla
     * @param jornada
     */
    @Override
    public void cargarTabla(int jornada){
        TableJornada.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);//Quitamos el AutoAjustado del tamaño de las columnas
        
        //Textos para las columnas
        String[] columnasLocal;
        if(deporte == 18){
            columnasLocal = new String[] {"FECHA", "HORA", "LOCAL", 
                "L1", "L2", "L3", "L4", "L5", "L6", "L7", "L8", "L9", "LEE", "LH", "LE", "RES.L",
                "V1", "V2", "V3", "V4", "V5", "V6", "V7", "V8", "V9", "VEE", "VH", "VE", "RES.V", 
                "VISITANTE", "CAMPO"};
                setColumnas(columnasLocal);
        }else if(deporte == 19){
            columnasLocal = new String[] {"FECHA", "HORA", "LOCAL", 
                "L1", "L2", "L3", "L4", "L5", "L6", "L7", "L8", "L9", "L10", "L11", "L12", "RES.L",
                "V1", "V2", "V3", "V4", "V5", "V6", "V7", "V8", "V9", "V10", "V11", "V12", "RES.V", 
                "VISITANTE", "PISTA",};
                setColumnas(columnasLocal);
        }
        //Indicamos que columnas serán editables
        final boolean[] canEdit = new boolean [] {true, true, false, 
                                                  true, true, true, true, true, true, true, true, true, true, true, true, true,  
                                                  true, true, true, true, true, true, true, true, true, true, true, true, true,
                                                  false, true};
        //Asignamos los nuevos anchos al array (los del archivo.txt)
        setNum(getAnchoTabla(getNum().length, CODIGO_ANCHO));
        //Definimos los anchos de las columnas y los asignamos como tamaño preferente
         int[] anchos = {(anchoTabla*num[0])/100, (anchoTabla*num[1])/100, (anchoTabla*num[2])/100, 
                        (anchoTabla*num[3])/100, (anchoTabla*num[4])/100, (anchoTabla*num[5])/100, (anchoTabla*num[6])/100, (anchoTabla*num[7])/100, (anchoTabla*num[8])/100, (anchoTabla*num[9])/100, (anchoTabla*num[10])/100, (anchoTabla*num[11])/100, (anchoTabla*num[12])/100, (anchoTabla*num[13])/100, (anchoTabla*num[14])/100,
                        (anchoTabla*num[15])/100, (anchoTabla*num[16])/100, (anchoTabla*num[17])/100, (anchoTabla*num[18])/100, (anchoTabla*num[19])/100, (anchoTabla*num[20])/100, (anchoTabla*num[21])/100, (anchoTabla*num[22])/100, (anchoTabla*num[23])/100, (anchoTabla*num[24])/100, (anchoTabla*num[25])/100, (anchoTabla*num[26])/100,
                        (anchoTabla*num[27])/100, (anchoTabla*num[28])/100, (anchoTabla*num[29])/100, (anchoTabla*num[30])/100};
         
        //Modelo de tabla por defecto
        setModelo(new DefaultTableModel(getColumnas(),0){
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {               
                return canEdit[columnIndex];
            }
        }); 
        
        
       
                              
        
        //Asignamos a nuestra variable global jornada el valor de la jornada pasada (pues el calculo se realiza en los botones de Principal)
        this.jornada = jornada;        
        //Lo primero de todo, cada vez que se carga el método que pinta la tabla, borramos las filas que pudiera haber escritas
        getTableJornada().removeAll();
        //modelo.getDataVector().removeAllElements();
        getTableJornada().repaint();//Repinta el elemento en cuestión repaint()???
        //Vaciamos la etiqueta del equipo que pueda estar descansando
        getLabelEquipoDescansa().setText("");
        //Creamos una variable Jornada y obtenemos la jornada indicada de nuestro objeto calendario
        Jornada J = getCalendario().jornadas.get(jornada - 1);//-1 porque es un índice
        //Ahora usamos el for-each y obtenemos la lista de partidos de este objeto Jornada
        for (Partido partido : (ArrayList<Partido>)J.getListaPartidos())
            //Si el local o el visitante descansan, lo añadimos a la etiqueta que lo muestra, en lugar de añadir la fila a la tabla
            if(partido.getLocal().equalsIgnoreCase("EquipoFantasma")){
                getLabelEquipoDescansa().setText(partido.getVisitante());
            }else if(partido.getVisitante().equalsIgnoreCase("EquipoFantasma")){
                getLabelEquipoDescansa().setText(partido.getLocal());
            }else{
                getModelo().addRow(partido.getTableRepresentation()); 
            }
        
        //Indicamos a la tabla cual es su modelo
        getTableJornada().setModel(getModelo());
        //Le asignamos el render al calendario
        if(deporte == 18){
            render = new RenderBeisbol();
            getTableJornada().setDefaultRenderer(Object.class, render);
        }else if(deporte == 19){
            renderC = new RenderCurling();
            getTableJornada().setDefaultRenderer(Object.class, renderC);
        }
        //Le asignamos a la etiqueta Jornada su número correspondiente
        getLabelJornada().setText("JORNADA " + jornada);
        //Asignamos el total de jornadas a su etiqueta
        getLabelTotalJornadas().setText("JORNADAS EN TOTAL: " + getCalendario().jornadas.size());
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
     * Utilizamos la misma tabla de calendario que el fútbol, de ahí que se llamen setGolesL y setGolesV
     */
    @Override
    public void guardarTabla(){
       //Creamos una variable Jornada y obtenemos la jornada indicada de nuestro objeto calendario
       Jornada J = getCalendario().jornadas.get(getJornada() - 1);//coge la jornada como variable global, -1 porque es un índice
       int filas =  getTableJornada().getRowCount();//número de filas en la tabla
       String descansa = getLabelEquipoDescansa().getText();
       //Por cada fila guardo los datos en el partido correspondiente del ArrayList 
       for (int i = 0; i < filas; i++) {
           
           //Obtenemos el partido en el ArrayList y le asignamos los nuevos datos
            PartidoBeisbol P = (PartidoBeisbol) J.partidos.get(i);
            P.setFecha(limitarCadena(getModelo().getValueAt(i,0).toString().trim(), 30, false));//el toString() para que lo coja como una cadena
            P.setHora(limitarCadena(getModelo().getValueAt(i,1).toString().trim(), 30, false));
            P.setLocal(limitarCadena(getModelo().getValueAt(i,2).toString().trim(), 40, false));
            P.setRL1(validarEnteros(getModelo().getValueAt(i, 3).toString().trim()));
            P.setRL2(validarEnteros(getModelo().getValueAt(i, 4).toString().trim()));
            P.setRL3(validarEnteros(getModelo().getValueAt(i, 5).toString().trim()));
            P.setRL4(validarEnteros(getModelo().getValueAt(i, 6).toString().trim()));
            P.setRL5(validarEnteros(getModelo().getValueAt(i, 7).toString().trim()));
            P.setRL6(validarEnteros(getModelo().getValueAt(i, 8).toString().trim()));
            P.setRL7(validarEnteros(getModelo().getValueAt(i, 9).toString().trim()));
            P.setRL8(validarEnteros(getModelo().getValueAt(i, 10).toString().trim()));
            P.setRL9(validarEnteros(getModelo().getValueAt(i, 11).toString().trim()));
            P.setRL10(validarEnteros(getModelo().getValueAt(i, 12).toString().trim()));
            P.setRL11(validarEnteros(getModelo().getValueAt(i, 13).toString().trim()));
            P.setRL12(validarEnteros(getModelo().getValueAt(i, 14).toString().trim()));
            P.setGolesL(validarEnteros(getModelo().getValueAt(i, 15).toString().trim()));
            P.setRV1(validarEnteros(getModelo().getValueAt(i, 16).toString().trim()));
            P.setRV2(validarEnteros(getModelo().getValueAt(i, 17).toString().trim()));
            P.setRV3(validarEnteros(getModelo().getValueAt(i, 18).toString().trim()));
            P.setRV4(validarEnteros(getModelo().getValueAt(i, 19).toString().trim()));
            P.setRV5(validarEnteros(getModelo().getValueAt(i, 20).toString().trim()));
            P.setRV6(validarEnteros(getModelo().getValueAt(i, 21).toString().trim()));
            P.setRV7(validarEnteros(getModelo().getValueAt(i, 22).toString().trim()));
            P.setRV8(validarEnteros(getModelo().getValueAt(i, 23).toString().trim()));
            P.setRV9(validarEnteros(getModelo().getValueAt(i, 24).toString().trim()));
            P.setRV10(validarEnteros(getModelo().getValueAt(i, 25).toString().trim()));
            P.setRV11(validarEnteros(getModelo().getValueAt(i,26).toString().trim()));
            P.setRV12(validarEnteros(getModelo().getValueAt(i,27).toString().trim()));//Se llaman goles porque la tabla de calendario se mantienen intacta (pero son ptos)
            P.setGolesV(validarEnteros(getModelo().getValueAt(i,28).toString().trim()));
            P.setVisitante(limitarCadena(getModelo().getValueAt(i,29).toString().trim(), 40, false));
            P.setPista(limitarCadena(getModelo().getValueAt(i,30).toString().trim(), 30, false));

            //Si los resultados arrojan un empate avisamos al usuario de su error (lo cargará al usar las flechas de jornada) Son iguales y no están vacías
            if(P.getGolesL() == P.getGolesV() && !getModelo().getValueAt(i,15).toString().equalsIgnoreCase("") && !getModelo().getValueAt(i,28).toString().equalsIgnoreCase("")){
                P.setGolesL(-1);
                P.setGolesV(-1);
                //Mostramos un mensaje de error en caso de que el usuario coloque un empate en la tabla
                if(!this.mensaje){//if esclusivamente para mostrar o no el mensaje
                     JOptionPane.showMessageDialog(TableJornada, "El resultado no es válido, los equipos no pueden empatar", "Error al insertar los datos de la tabla", JOptionPane.ERROR_MESSAGE, null);
                     this.mensaje = true;
                 }
            }
        }//End for
       //Tras acabar el for y recorrer todos los resultados ponemos el mensaje como false de nuevo
        this.mensaje = false;
       if(!descansa.equalsIgnoreCase("")){
           //Si hay algún equipo descansando lo añadimos a la lista
           //Una vez creado el calendario no importa si el EquipoFantasma se añade como Local o Visitante
           Partido P = (Partido) J.partidos.get(filas);//Lo añadimos tras la carga del resto de filas en la última posición (filas es la última)
           //Partido partido = new Partido("", "", descansa, -1, -1, "EquipoFantasma", "");
            //Añadimos el partido a la Jornada
           //J.añadirPartido(partido);
           P.setLocal(descansa);
           P.setVisitante("EquipoFantasma");
       }
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
                    JOptionPane.showMessageDialog(TableJornada, "Los Puntos deben contener números enteros no negativos", "Error al insertar los datos de la tabla", JOptionPane.ERROR_MESSAGE, null);
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
                    JOptionPane.showMessageDialog(TableJornada, "Los Puntos deben contener números enteros no negativos", "Error al insertar los datos de la tabla", JOptionPane.ERROR_MESSAGE, null);
                    this.mensaje = true;
                }
                numero = -1;
                return numero;
            }
        }//End if de chequeo de cadena
            return -1;//Devolvemos el número que será tratado por nuestro programa
    }//End of validarEnteros()

    /**
     * Método que carga la clasificación de los participantes
     */
    @Override
     public void cargarClasificacion(){
        //Textos para las columnas
         String[] columnasLocal;
         if(deporte == 18){
            columnasLocal = new String[] {"POSICIÓN","COMPETIDOR", "PJ", "PG", "PP", "CF", "CC"};
            setColumnasClasf(columnasLocal);
        }else if(deporte == 19){
            columnasLocal = new String[] {"POSICIÓN","COMPETIDOR", "PJ", "PG", "PP", "PF", "PC"};
            setColumnasClasf(columnasLocal);
        }
        //Indicamos que las columnas de la clasificación no serán editables
        final boolean[] canEdit = new boolean [] {false, false, false, false, false, false, false};
        //Asignamos los nuevos anchos al array (los del archivo.txt)
        setNumc(getAnchoTabla(getNumc().length, CODIGO_ANCHO_CLASF));
                //Asignamos los anchos a las columnas
        int[] anchos = {(anchoTabla*numc[0])/100, (anchoTabla*numc[1])/100, (anchoTabla*numc[2])/100, (anchoTabla*numc[3])/100, (anchoTabla*numc[4])/100, (anchoTabla*numc[5])/100, (anchoTabla*numc[6])/100};
        //Modelo de tabla clasificación por defecto
        setClasificacion(new DefaultTableModel(getColumnasClasf(),0){
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        
        //Lo primero de todo, cada vez que se carga el método que pinta la tabla, borramos las filas que pudiera haber escritas
        getTableClasificacion().removeAll();
        getTableClasificacion().repaint();//Repinta el elemento en cuestión repaint()???
        //Ahora usamos el for-each y obtenemos la lista de partidos de este objeto Jornada (Casteamos la lista como EquipoBasket para que los reconozca como tales)
        for (EquipoBasket equipo: (ArrayList<EquipoBasket>)getCastEquipoLista().getEquipos())
            //Si el local o el visitante descansan ignoramos la acción
            if(equipo.getNombre().equalsIgnoreCase("EquipoFantasma")){
                //No hagas nada pues no este equipo es el comodín para los torneos impares
            }else{
                getClasificacion().addRow(equipo.representarEquipo()); 
            }     
        //Indicamos a la tabla cual es su modelo
        getTableClasificacion().setModel(getClasificacion());
        //Le asignamos el render al calendario
        renderClasf = new RenderBeisbolClasf();
        getTableClasificacion().setDefaultRenderer(Object.class, renderClasf);
        //Asignamos la nueva altura (la del archivo.txt)
        setAlturaClasfColumna(getAltoTabla(CODIGO_ALTO_CLASF));
        TableClasificacion.setRowHeight(getAlturaClasfColumna());//Método que cambia la altura de las filas del JTable (funciona una vez asignado el modelo) 
        for (int i = 0; i < getColumnasClasf().length; i++) {
            TableClasificacion.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);           
        }
        //Indicamos que las columnas no se puedan mover
        TableClasificacion.getTableHeader().setReorderingAllowed(false);
    }//End of cargarTablaClasificacion()
     
     /**
      * Método que realiza el cálculo de las estadísticas de cada equipo recorriendo todas las jornadas 
      * y los partidos de cada jornada, sumando lo que corresponda a cada uno de ellos
      */
    @Override
     public void calcularClasificacion(){
         //Creamos unas variables locales String e int para recibir los datos de la tabla
         String local, visitante;
         int golesL, golesV;
         //Lo primero de todo guardamos los datos de la tabla actual por si los han introducido y no se habían guardado
         guardarTabla();
         //Antes de calcular los datos borramos la clasificacion (puede que hubieramos calculado antes, entoncen se sumaría)
         borrarClasificacion();
         //Hacemos un for para cargar todas las jornadas existentes una a una y vamos obteniendo y calculando los datos
         for (int j = 0; j < getCalendario().jornadas.size(); j++) {
            Jornada J = getCalendario().jornadas.get(j);//Indicamos que jornada recibe con cada viaje
            int filas =  getTableJornada().getRowCount();//número de filas en la tabla
            //Por cada fila guardo los datos en el partido correspondiente del ArrayList 
            for (int i = 0; i < filas; i++) {
                //Obtenemos el partido en el ArrayList y sus datos correspondientes
                 Partido P = (Partido) J.partidos.get(i);
                 local = P.getLocal();
                 golesL = P.getGolesL();
                 golesV = P.getGolesV();
                 visitante = P.getVisitante();

                 //Ahora realizaremos los cálculos y asignaremos los puntos
                 for (EquipoBasket equipo: (ArrayList<EquipoBasket>)getCastEquipoLista().getEquipos()){                    
                     //Asignamos los datos al equipo (Si coincide el nombre y los goles son válidos)
                     asignarDatos(equipo, local, golesL, golesV, visitante);
                     this.mensaje = false;//Lo utilizamos para mostrar el mensaje de error en los empates
                 }
             }//End for i
         }//End for j
         //Finalmente procedemos a aplicar las posibles sanciones en la clasificación 
         //Recorremos el HashMap fuera del for de jornadas y aplicamos las sanciones a los equipos (Si coincide el nombre del equipo le restamos los puntos indicados)
         for (EquipoBasket equipo: (ArrayList<EquipoBasket>)getCastEquipoLista().getEquipos()){
            for (Map.Entry<String, Integer> entry : getSancionados().entrySet()) {
                if(entry.getKey().equalsIgnoreCase(equipo.getNombre())){
                    equipo.setPg(equipo.getPg() - entry.getValue());
                }
            }//end for HashMap
         }//end for lista de equipos
     }//End calcularClasificacion()
     
     /**
      * Método que pone a 0 los datos de los equipos para no duplicar los datos al pulsar el botón Actualizar Clasificación
      */
    @Override
     public void borrarClasificacion(){
         for (EquipoBasket equipo: (ArrayList<EquipoBasket>)getCastEquipoLista().getEquipos()){ 
             //Ponemos a 0 todos los marcadores en todos los equipos (para no duplicar datos)
                equipo.setPj(0);
                equipo.setPg(0);
                equipo.setPp(0);
                equipo.setPf(0);
                equipo.setPc(0);
         }//end for
     }//End of borrarClasificacion()
     
     /**
      * Método que asigna la puntuación, goles y partidos... al equipo pasado
      * El equipo ha de coincidir con el local o el visitante, de lo contario se dejará pasar
      * Si los ptos son -1 o null se ignorará la información, por lo que no se contará ese partido
      * @param equipo
      * @param local
      * @param PtosL
      * @param PtosV
      * @param visitante 
      */
     public void asignarDatos(EquipoBasket equipo, String local, int PtosL, int PtosV, String visitante){
         //Primero comprobamos que los resultados son válidos, si es -1
         if(PtosL < 0 || PtosV < 0){
             //Ignoramos el cálculo
         }else{
             //Ahora tenemos que establecer la diferencia de ptos. que acumula cada equipo
             int dif;//Declaramos las variable auxiliares
            if(equipo.getNombre().equalsIgnoreCase(local)){
               //Calculamos la diferencia de ptos
               dif = PtosL - PtosV;
               //Si gana el equipo local lo sumamos a partidos ganados
               if(PtosL > PtosV){
                   equipo.setPg(equipo.getPg() + 1);
               }else if(PtosL < PtosV){
                   //Sumamos a partidos perdidos
                   equipo.setPp(equipo.getPp() + 1);
               }else{
                   //Mostramos un mensaje de error en caso de que el usuario coloque un empate en la tabla
                   if(!this.mensaje){//if esclusivamente para mostrar o no el mensaje
                        JOptionPane.showMessageDialog(TableJornada, "El resultado no es válido, los equipos no pueden empatar", "Error al insertar los datos de la tabla", JOptionPane.ERROR_MESSAGE, null);
                        this.mensaje = true;
                    }
               }
               //Sumamos los goles a favor y en contra y el partido jugado
               equipo.setPj(equipo.getPj() + 1);
               equipo.setPf(equipo.getPf() + PtosL);
               equipo.setPc(equipo.getPc() + PtosV);
           }else if(equipo.getNombre().equalsIgnoreCase(visitante)){//End if local 
               //Calculamos la diferencia de ptos
               dif = PtosV - PtosL;
               //Si gana el equipo visitante le sumamos la victoria
               if(PtosV > PtosL){
                   equipo.setPg(equipo.getPg() + 1);
               }else if (PtosV < PtosL){
                   //Sumamos la derrota
                   equipo.setPp(equipo.getPp() + 1);
               }else{
                   //Mostramos un mensaje de error en caso de que el usuario coloque un empate en la tabla
                   if(!this.mensaje){//if esclusivamente para mostrar o no el mensaje
                        JOptionPane.showMessageDialog(TableJornada, "El resultado no es válido, los equipos no pueden empatar", "Error al insertar los datos de la tabla", JOptionPane.ERROR_MESSAGE, null);
                        this.mensaje = true;
                    }
               }
               //Sumamos los goles a favor y en contra y el partido jugado
               equipo.setPj(equipo.getPj() + 1);
               equipo.setPf(equipo.getPf() + PtosV);
               equipo.setPc(equipo.getPc() + PtosL);
           }else{//End if visitante
               //Si el equipo no está en el partido no cambiamos nada
           }//End if equipos no seleccionados
         }//End if comprobación de resultado válido
     }
     
     /**
      * Método que ordena el ArrayList con el que mostraremos la clasificación
      * Ordenamos en orden inverso a prioridad, primero por puntos a favor, luego por diferencia de puntos, y por último por victorias
      */
    @Override
     public void ordenarClasificacion(){
         //Le indicamos al comparador que el tipo de objeto a ordenar son Equipos
        Collections.sort(getCastEquipoLista().getEquipos(), new Comparator<EquipoBasket>() {
            @Override
            public int compare(EquipoBasket e1, EquipoBasket e2) {
                    // Aqui esta el truco, ahora comparamos p2 con p1 y no al reves como antes
                    return new Integer(e2.getPf()).compareTo(e1.getPf());
            }                                        
        });
         
         //Le indicamos al comparador que el tipo de objeto a ordenar son Equipos
        Collections.sort(getCastEquipoLista().getEquipos(), new Comparator<EquipoBasket>() {
            @Override
            public int compare(EquipoBasket e1, EquipoBasket e2) {
                    // Aqui esta el truco, ahora comparamos p2 con p1 y no al reves como antes
                    return new Integer(e2.getPf() - e2.getPc()).compareTo(e1.getPf() - e1.getPc());
            }                                        
        });
        
         //Le indicamos al comparador que el tipo de objeto a ordenar son Equipos
        Collections.sort(getCastEquipoLista().getEquipos(), new Comparator<EquipoBasket>() {
            @Override
            /*Este trozo de código lo que hace es que a número igual de partidos ganados coloca encima a los equipos que 
              hayan jugado menos partidos (porque tienen oportunidad de sumar una victoria) y el resto de criterios de 
              desempate se mantiene */
            public int compare(EquipoBasket e1, EquipoBasket e2) {
                    /*if(e2.getPg() == e1.getPg()){
                        //Ahora es e1 antes que e2 porque queremos que coloque más arriba el que menos partidos ha jugado
                        return new Integer(e1.getPj()).compareTo(new Integer(e2.getPj()));
                    }*/
                    // Aqui esta el truco, ahora comparamos p2 con p1 y no al reves como antes
                    return new Integer(e2.getPg()).compareTo(e1.getPg());
            }                                        
        });
        
        int k=0;//Índice que usaremos para asignar posiciones a los equipos
        for (EquipoBasket equipo: (ArrayList<EquipoBasket>)getCastEquipoLista().getEquipos()){
            //Para las listas impares, si el nombre del equipo es EquipoFantasma lo ignoramos
            if(equipo.getNombre().equalsIgnoreCase("EquipoFantasma")){
                //Ignoramos la acción
            }else{
                //Le asignamos al equipo su posición en la tabla clasificación, que será la del ArrayList una vez ordenado
                equipo.setPosicion(k);//Asignamos la posición
                k++;//sumamos 1 al índice
            }
        }
     }//End ordenarClasificacion()
     
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
        if(sancionados.size() > 0){//Si hay sancionados procedemos a guardarlos            
            for (Map.Entry<String, Integer> entry : getSancionados().entrySet()) {
                Element xml_sancionado = new Element("sancionado");
                //Añadimos el nombre del sancionado
                Element xml_nombreSancionado = new Element("nombreSancionado");
                xml_nombreSancionado.setText(entry.getKey());
                xml_sancionado.addContent(xml_nombreSancionado);
                //Añadimos los puntos de sanción
                Element xml_sancion = new Element("sancion");
                xml_sancion.setText(Integer.toString(entry.getValue()));
                xml_sancionado.addContent(xml_sancion);
                //Añadimos el sancionado a la lista de sanciones
                xml_sanciones.addContent(xml_sancionado);
            }//end for HashMap
        }//end if
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
                for (PartidoBeisbol partido :(ArrayList<PartidoBeisbol>) J.getListaPartidos()){ 
                    
                    //Creamos un elemento partido por cada partido en la lista
                    Element xml_partido = new Element("partido");
                    
                    xml_partido.addContent(new Element("fecha").setText(partido.getFecha()));
                    xml_partido.addContent(new Element("hora").setText(partido.getHora()));
                    xml_partido.addContent(new Element("local").setText(partido.getLocal()));
                    xml_partido.addContent(new Element("RL1").setText(Integer.toString(partido.getRL1())));
                    xml_partido.addContent(new Element("RL2").setText(Integer.toString(partido.getRL2())));
                    xml_partido.addContent(new Element("RL3").setText(Integer.toString(partido.getRL3())));
                    xml_partido.addContent(new Element("RL4").setText(Integer.toString(partido.getRL4())));
                    xml_partido.addContent(new Element("RL5").setText(Integer.toString(partido.getRL5())));
                    xml_partido.addContent(new Element("RL6").setText(Integer.toString(partido.getRL6())));
                    xml_partido.addContent(new Element("RL7").setText(Integer.toString(partido.getRL7())));
                    xml_partido.addContent(new Element("RL8").setText(Integer.toString(partido.getRL8())));
                    xml_partido.addContent(new Element("RL9").setText(Integer.toString(partido.getRL9())));
                    xml_partido.addContent(new Element("RL10").setText(Integer.toString(partido.getRL10())));
                    xml_partido.addContent(new Element("RL11").setText(Integer.toString(partido.getRL11())));
                    xml_partido.addContent(new Element("RL12").setText(Integer.toString(partido.getRL12())));
                    xml_partido.addContent(new Element("golesL").setText((Integer.toString(partido.getGolesL()))));//Convertimos los valores enteros a String
                    xml_partido.addContent(new Element("RV1").setText(Integer.toString(partido.getRV1())));
                    xml_partido.addContent(new Element("RV2").setText(Integer.toString(partido.getRV2())));
                    xml_partido.addContent(new Element("RV3").setText(Integer.toString(partido.getRV3())));
                    xml_partido.addContent(new Element("RV4").setText(Integer.toString(partido.getRV4())));
                    xml_partido.addContent(new Element("RV5").setText(Integer.toString(partido.getRV5())));
                    xml_partido.addContent(new Element("RV6").setText(Integer.toString(partido.getRV6())));
                    xml_partido.addContent(new Element("RV7").setText(Integer.toString(partido.getRV7())));
                    xml_partido.addContent(new Element("RV8").setText(Integer.toString(partido.getRV8())));
                    xml_partido.addContent(new Element("RV9").setText(Integer.toString(partido.getRV9())));
                    xml_partido.addContent(new Element("RV10").setText(Integer.toString(partido.getRV10())));
                    xml_partido.addContent(new Element("RV11").setText(Integer.toString(partido.getRV11())));
                    xml_partido.addContent(new Element("RV12").setText(Integer.toString(partido.getRV12())));
                    xml_partido.addContent(new Element("golesV").setText((Integer.toString(partido.getGolesV()))));
                    xml_partido.addContent(new Element("visitante").setText(partido.getVisitante()));
                    xml_partido.addContent(new Element("pista").setText(partido.getPista()));      
                    
                    xml_jornada.addContent(xml_partido);
                }           
                //Añadimos la jornada al documento XML
                xml_calendario.addContent(xml_jornada); 
  
        }      
        doc.getRootElement().addContent(xml_calendario);
        
        //Añadimos ahora los datos de la clasificación
        num_equipos = castEquipoLista.getEquipos().size();
        
        Element xml_clasificacion = new Element("clasificacion");
        
        for (EquipoBasket equipo: (ArrayList<EquipoBasket>)getCastEquipoLista().getEquipos())
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
                xml_equipo.addContent(new Element("pf").setText(Integer.toString(equipo.getPf())));
                xml_equipo.addContent(new Element("pc").setText(Integer.toString(equipo.getPc())));
                xml_equipo.addContent(new Element("dif").setText(Integer.toString(equipo.getDif())));
                
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
       String jornadaText = valueOf(jornada);//Pasamos a String el número de jornada
       String descansa = "";//Obtenemos el nombre del equipo que descansa        
       DataSourceFutbolJornada datasource = new DataSourceFutbolJornada();  
       if(aplazados){
            //Añadimos los partidos aplazados al datasource
             for (int i = 0; i < partidosAplazados.size(); i++) {
                Partido P = (Partido) partidosAplazados.get(i);
                datasource.addPartido(P);
             }
             jornadaText = "Aplz";//Cambiamo el nombre de la jornada a Aplazados
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
        
        File archivoInforme = new File("src/informes/futbolJornada.jasper");
        JasperReport reporte;
        parametros.put("nombreTorneo", nombreTorneo);
        parametros.put("jornada", jornadaText);
        //Si el número de equipos es impar añadimos el texto DESCANSA: y el nombre del equipo que descansa al informe, de lo contrario mejor que no aparezca
        if(castEquipoLista.getEquipos().size() % 2 != 0){
             if(aplazados){
                 parametros.put("equipoDescansa", "");
             }else{
                parametros.put("equipoDescansa", descansa);
             }
         }else{
             parametros.put("equipoDescansa", "");
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
            Logger.getLogger(MetodosBeisbol.class.getName()).log(Level.SEVERE, null, ex);
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
       String jornadaText = valueOf(jornada);//Pasamos a String el número de jornada
       String descansa = "";//Obtenemos el nombre del equipo que descansa        
       DataSourceBeisbolResultados datasource = new DataSourceBeisbolResultados();  
       if(aplazados){
            //Añadimos los partidos aplazados al datasource
             for (int i = 0; i < partidosAplazados.size(); i++) {
                PartidoBeisbol P = (PartidoBeisbol) partidosAplazados.get(i);
                datasource.addPartido(P);
             }
             jornadaText = "Aplz";//Cambiamo el nombre de la jornada a Aplazados
       }else{
            Jornada J = getCalendario().jornadas.get(getJornada() - 1);//coge la jornada como variable global, -1 porque es un índice
             //Añadimos los partidos de la jornada al datasource
                 for (int i = 0; i < J.getListaPartidos().size(); i++) {
                     PartidoBeisbol P = (PartidoBeisbol) J.partidos.get(i);
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
        
        File archivoInforme;
        if(deporte == 18){
            archivoInforme = new File("src/informes/beisbolResultados.jasper");
        }else{
            archivoInforme = new File("src/informes/curlingResultados.jasper");
        }
            
        JasperReport reporte;
        parametros.put("nombreTorneo", nombreTorneo);
        parametros.put("jornada", jornadaText);
        //Si el número de equipos es impar añadimos el texto DESCANSA: y el nombre del equipo que descansa al informe, de lo contrario mejor que no aparezca
        if(castEquipoLista.getEquipos().size() % 2 != 0){
             if(aplazados){
                 parametros.put("equipoDescansa", "");
             }else{
                parametros.put("equipoDescansa", descansa);
             }
         }else{
             parametros.put("equipoDescansa", "");
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
            Logger.getLogger(MetodosBeisbol.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//End informeResultados()
    
    /**
     * Genera un informe de la clasificación actual en PDF
     * @param rutaPDF
     * @param nombreArchivo
     */
    @Override
    public void informeClasificacion(String rutaPDF, String nombreArchivo){
        //lo primero de todo nos aseguramos de que ha guardado los datos de la tabla
       this.guardarTabla();
       ArrayList<EquipoBasket> equipos = castEquipoLista.getEquipos();
        DataSourceBasketClasificacion datasource = new DataSourceBasketClasificacion();//Beisbol y Curling utilizan las clasificación del Basket  
        //Añadimos los partidos de la jornada al datasource
        for (EquipoBasket equipo : equipos) {
            EquipoBasket E = (EquipoBasket) equipo;
            //Comprobamos si descansa algún equipo y guardamos su nombre en la variable indicada
            if(E.getNombre().equalsIgnoreCase("EquipoFantasma")){
                //Ignoramos al EquipoFantasma
            }else if(E.getNombre().equalsIgnoreCase("EquipoFantasma")){
                //Ignoramos al EquipoFantasma
            }else{
                datasource.addEquipo(E);
            }
        }  
         File archivoInforme;
        if(deporte == 18){
            archivoInforme = new File("src/informes/beisbolClasificacion.jasper");
        }else{
            archivoInforme = new File("src/informes/curlingClasificacion.jasper");
        }
        JasperReport reporte;
        //Creamos un HashMap para poder utilizar los parámetros en el informe
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("nombreTorneo", nombreTorneo);
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
            JOptionPane.showMessageDialog(null, "<html>El documento <b>" + nombreArchivo + "</b> se ha creado correctamente</html>", "Se creó un documento con éxito", JOptionPane.INFORMATION_MESSAGE, null);
        } catch (JRException ex) {
            Logger.getLogger(MetodosBeisbol.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        String jornadaText = valueOf(jornada);//Pasamos a String el número de jornada
        int numero_jornadas = calendario.jornadas.size();
        int num_partidos = calendario.jornadas.get(0).partidos.size();//la jornada 0 es de ejemplo, solo hace falta para conseguir el número de partidos(igual en todas las jornadas)
        File archivoInforme;//archivo.jasper
        JRXmlDataSource datasource;
        try {
            //Pasamos la ruta del archivo XML y la expresión XPath que ha de utilizar el informe
            datasource = new JRXmlDataSource(rutaXMLCalendar, "torneo/calendario/jornada/partido");
            jornada = 1;//Ponemos la jornada en la primera para que el while actue con todas las jornadas
            //Según el número de partidos que tenga cada jornada cargaremos el .jasper normal o el especial para grandes torneos
            if(num_partidos > 40){//40 es el limite de campos que le hemos puesto al page break en el futbolCalendario.jasper
                archivoInforme = new File("src/informes/futbolCalendarioBIG.jasper");
            }else{
                archivoInforme = new File("src/informes/futbolCalendario.jasper");
            }
            JasperReport reporte;
            //Creamos un HashMap para poder utilizar los parámetros en el informe
            HashMap<String, Object> parametros = new HashMap<>();
            /* En esta ocasión el nombre del torneo, el equipo que descansa y el valor DESCANSA los recibe del archivo XML por lo que no utilizaremos esos parámetros */
            parametros.put("jornada", jornadaText);
            //parametros.put("SUBREPORT_DIR", subreporte);//Añadimos la ruta del subreporte (subCalendario.jasper ya viene añadido en el informe) (Finalmente no usaremos subreporte)
            parametros.put("num_jornadas", numero_jornadas);
            parametros.put("num_partidos", num_partidos);

            reporte = (JasperReport) JRLoader.loadObject(archivoInforme);
            JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parametros, datasource);
            //jasperPrint.addPage(jasperPrint.getPages().get(jornada - 1));
            JRExporter exporter = new JRPdfExporter();  
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_FILE, new java.io.File(rutaPDF + "/" + nombreArchivo));
            exporter.exportReport();
            JOptionPane.showMessageDialog(null, "<html>El documento <b>" + nombreArchivo + "</b> se ha creado correctamente</html>", "Se creó un documento con éxito", JOptionPane.INFORMATION_MESSAGE, null);
            } catch (JRException ex) {
                Logger.getLogger(MetodosBeisbol.class.getName()).log(Level.SEVERE, null, ex);
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

            File archivoInforme = new File("src/informes/futbolJornada.jasper");
            JasperReport reporte;
            //Creamos un HashMap para poder utilizar los parámetros en el informe
            HashMap<String, Object> parametros = new HashMap<>();
            String jornadaText = valueOf(jornada);//Pasamos a String el número de jornada
            parametros.put("nombreTorneo", nombreTorneo);
            parametros.put("jornada", jornadaText);
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
                Logger.getLogger(MetodosBeisbol.class.getName()).log(Level.SEVERE, null, ex);
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
            DataSourceBeisbolResultados datasource = new DataSourceBeisbolResultados();  
            //Añadimos los partidos de la jornada al datasource
            for (int i = 0; i < J.getListaPartidos().size(); i++) {
                PartidoBeisbol P = (PartidoBeisbol) J.partidos.get(i);
                //Comprobamos si descansa algún equipo y guardamos su nombre en la variable indicada
                if(P.getLocal().equalsIgnoreCase("EquipoFantasma")){
                    descansa = P.getVisitante();
                }else if(P.getVisitante().equalsIgnoreCase("EquipoFantasma")){
                    descansa = P.getLocal();
                }else{
                  datasource.addPartido(P);
                } 
            }  
             File archivoInforme;
            if(deporte == 18){
                archivoInforme = new File("src/informes/beisbolResultados.jasper");
            }else{
                archivoInforme = new File("src/informes/curlingResultados.jasper");
            }
            JasperReport reporte;
            //Creamos un HashMap para poder utilizar los parámetros en el informe
            HashMap<String, Object> parametros = new HashMap<>();
            String jornadaText = valueOf(jornada);//Pasamos a String el número de jornada
            parametros.put("nombreTorneo", nombreTorneo);
            parametros.put("jornada", jornadaText);
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
                Logger.getLogger(MetodosBeisbol.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(MetodosBeisbol.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MetodosBeisbol.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(MetodosBeisbol.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MetodosBeisbol.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(MetodosBeisbol.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MetodosBeisbol.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Finalmente cargamos los datos modificados
        this.cargarTabla(jornada);
        almacen.clear();
    }//End of setDatosTabla()*/
    
    /**
     * Método que modifica los datos del tamaño de la tabla de clasificación en el archivo datosTablas.txt (Igual que setDatosTabla() pero con los códigos de clasificación)
     * @param valores
     * @param alturaClasf
     */
    @Override
    public void setDatosTablaClasf(int[] valores, int alturaClasf){
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
              if(cadena.startsWith("anchoW"+Integer.toString(i+CODIGO_ANCHO_CLASF)) && i < valores.length){
                  aux = cadena.split("=");
                  aux[1] = valueOf(valores[i]);
                  //Guardamos en el ArrayList las líneas modificadas del ancho
                  almacen.add(aux[0] + "=" + aux[1]);
                  i++;
                  Arrays.fill(aux, null);//vaciamos el array auxiliar
              }else if(cadena.startsWith("altoH"+Integer.toString(CODIGO_ALTO_CLASF))){
                aux = cadena.split("=");
                aux[1] = valueOf(alturaClasf);
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
            Logger.getLogger(MetodosBeisbol.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MetodosBeisbol.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Finalmente cargamos los datos modificados
        almacen.clear();
        this.cargarClasificacion();
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
     * @return the clasificacion
     */
    @Override
    public DefaultTableModel getClasificacion() {
        return clasificacion;
    }

    /**
     * @param clasificacion the clasificacion to set
     */
    @Override
    public void setClasificacion(DefaultTableModel clasificacion) {
        this.clasificacion = clasificacion;
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
     * @return the TableClasificacion
     */
    @Override
    public JTable getTableClasificacion() {
        return TableClasificacion;
    }

    /**
     * @param TableClasificacion the TableClasificacion to set
     */
    @Override
    public void setTableClasificacion(JTable TableClasificacion) {
        this.TableClasificacion = TableClasificacion;
    }

    /**
     * @return the LabelEquipoDescansa
     */
    @Override
    public JLabel getLabelEquipoDescansa() {
        return LabelEquipoDescansa;
    }

    /**
     * @param LabelEquipoDescansa the LabelEquipoDescansa to set
     */
    @Override
    public void setLabelEquipoDescansa(JLabel LabelEquipoDescansa) {
        this.LabelEquipoDescansa = LabelEquipoDescansa;
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
     * @return the castEquipoLista
     */
    @Override
    public EquipoLista getCastEquipoLista() {
        return castEquipoLista;
    }

    /**
     * @param castEquipoLista the equipoLista to set
     */
    @Override
    public void setCastEquipoLista(EquipoLista castEquipoLista) {
        this.castEquipoLista = castEquipoLista;
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
