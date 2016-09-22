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

import datasources.DataSourceAjedrezClasificacion;
import datasources.DataSourceAjedrezResultados;
import datasources.DataSourceFutbolJornada;
import generador.Calendario;
import equipos.EquipoAjedrez;
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
import partidos.Partido;
import partidos.PartidoAjedrez;
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
import renders.RenderAjedrezClasf;
import renders.RenderFutbol;
import java.util.Arrays;
import java.util.Map;

/**
 * En Ajedrez existe la regla de Bilbao, que puntua las partidas de Ajedrez como en el fútbol 3,1,0 (Usaremos la clásica 1, 0.5, 0)
 * @author Alvarielli
 */
public class MetodosAjedrez extends Metodos{
    
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
    private EquipoLista castEquipoLista;//EquipoLista con los Equipos ya casteados en Principal (seleccionarClaseEquipo)
    private boolean mensaje = false;//Se utiliza para que el programa muestre el mensaje de error al introducir datos sólo una vez
    private int checkSB;//Variable que comprueba que el total de equipos han jugado todas sus partidas (para proceder entonces a calcular el SB)
    private int anchoTabla = 1740;//Ponemos un valor fijo y nos evitamos problemas //TableJornada.getWidth();
    private int alturaColumna = 25;//Indica la altura de la columna
    private int alturaClasfColumna = 25;//Indica la altura de las filas de la columna en la tabla de clasificación
    private RenderFutbol render;//Podemos usar Futbol para el Calendario
    private RenderAjedrezClasf renderClasf;
    private int[] num = {12, 12, 24, 8, 8, 24, 12};// Definimos los anchos para las tablas en porcentaje (para que el usuario pueda modificarlas luego)
    private int[] numc = {10, 30, 10, 10, 10, 10, 10, 10};//Deben sumar 100 siempre
    private String[] columnas;
    private String[] columnasClasf;
    
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
    //Variable especifica para el ajedrez
    private int contadorPartidos = 0;//Guarda los partidos totales que se juegan en el torneo
    
    /** Variables necesarias para el tamaño de las tablas **/
    private final int CODIGO_ANCHO = 0;
    private final int CODIGO_ALTO = 0;
    private final int CODIGO_ANCHO_CLASF = 7;
    private final int CODIGO_ALTO_CLASF = 1;
    
    //En el constructor de clase le pasamos todo lo requerido para esta clase desde Principal
    public MetodosAjedrez(DefaultTableModel modelo, DefaultTableModel clasificacion, int jornada, JTable TableJornada, JTable TableClasificacion, JLabel LabelEquipoDescansa,
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
     * Carga los datos de la joranda pasada en la tabla
     * @param jornada
     */
    @Override
    public void cargarTabla(int jornada){
        TableJornada.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);//Reasignamos el AutoAjustado
        //Textos para las columnas
        String[] columnasLocal = {"FECHA", "HORA", "EQUIPO LOCAL", "RES.L", "RES.V", "EQUIPO VISITANTE", "MESA"};
        setColumnas(columnasLocal);
        //Indicamos que columnas serán editables
        final boolean[] canEdit = new boolean [] {true, true, false, true, true, false, true};
        //Asignamos los nuevos anchos al array (los del archivo.txt)
        setNum(getAnchoTabla(getNum().length, CODIGO_ANCHO));  
        //Definimos los anchos de las columnas y los asignamos como tamaño preferente
         int[] anchos = {(anchoTabla*num[0])/100, (anchoTabla*num[1])/100, (anchoTabla*num[2])/100, (anchoTabla*num[3])/100, (anchoTabla*num[4])/100, (anchoTabla*num[5])/100, (anchoTabla*num[6])/100};
        //Modelo de tabla por defecto
        setModelo(new DefaultTableModel(columnas,0){
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
        for (PartidoAjedrez partido :(ArrayList<PartidoAjedrez>) J.getListaPartidos())
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
        render = new RenderFutbol();
        getTableJornada().setDefaultRenderer(Object.class, render);
        //Le asignamos a la etiqueta Jornada su número correspondiente
        getLabelJornada().setText("JORNADA " + jornada);
        //Asignamos el total de jornadas a su etiqueta
        getLabelTotalJornadas().setText("JORNADAS EN TOTAL: " + getCalendario().jornadas.size());
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
        //Variables que usaremos como filtro de resultados posibles
        double casillaBlancas = -1, casillaNegras = -1;
       //Creamos una variable Jornada y obtenemos la jornada indicada de nuestro objeto calendario
       Jornada J = getCalendario().jornadas.get(getJornada() - 1);//coge la jornada como variable global, -1 porque es un índice
       int filas =  getTableJornada().getRowCount();//número de filas en la tabla
       String descansa = getLabelEquipoDescansa().getText();
       //Por cada fila guardo los datos en el partido correspondiente del ArrayList 
       for (int i = 0; i < filas; i++) {
           //Obtenemos el partido en el ArrayList y le asignamos los nuevos datos
            PartidoAjedrez P = (PartidoAjedrez) J.partidos.get(i);
            P.setFecha(limitarCadena(getModelo().getValueAt(i,0).toString().trim(), 50, false));//el toString() para que lo coja como una cadena
            P.setHora(limitarCadena(getModelo().getValueAt(i,1).toString().trim(), 50, false));
            P.setLocal(limitarCadena(getModelo().getValueAt(i,2).toString().trim(), 40, false));
            P.setBlancas(validarDoubles(getModelo().getValueAt(i,3).toString().trim()));
            P.setNegras(validarDoubles(getModelo().getValueAt(i,4).toString().trim()));
            P.setVisitante(limitarCadena(getModelo().getValueAt(i,5).toString().trim(), 40, false));
            P.setPista(limitarCadena(getModelo().getValueAt(i,6).toString().trim(), 50, false));
            //Realizamos la comprobación en base a lo insertado en el equipo local
            if(!getModelo().getValueAt(i,3).toString().equalsIgnoreCase("0.5")){
               casillaBlancas = Math.round(P.getBlancas());//obtenemos los valores que quedaron asignados a cada casillero y los redondeamos para evitar que se salten el filtro
            }
            if(!getModelo().getValueAt(i,4).toString().equalsIgnoreCase("0.5")){
               casillaNegras = Math.round(P.getNegras());//obtenemos los valores que quedaron asignados a cada casillero y los redondeamos para evitar que se salten el filtro
               P.setNegras(casillaNegras);
            }
            if(casillaBlancas == 1 && !(casillaNegras == 0)){
                //Mostramos un mensaje de error en caso de que el usuario no asigne una puntuación válida
                if(!this.mensaje){//if esclusivamente para mostrar o no el mensaje
                     JOptionPane.showMessageDialog(TableJornada, "Los resultados válidos son: 1 - 0 , 0 - 1, 0.5 - 0.5", "Error al insertar los datos de la tabla", JOptionPane.ERROR_MESSAGE, null);
                     this.mensaje = true;
                 }
                P.setNegras(-1);
                P.setBlancas(-1);
            }else if(casillaBlancas == 0 && !(casillaNegras == 1)){
                if(!this.mensaje){//if esclusivamente para mostrar o no el mensaje
                     JOptionPane.showMessageDialog(TableJornada, "Los resultados válidos son: 1 - 0 , 0 - 1, 0.5 - 0.5", "Error al insertar los datos de la tabla", JOptionPane.ERROR_MESSAGE, null);
                     this.mensaje = true;
                 }
                P.setNegras(-1);
                P.setBlancas(-1);
            }else if(getModelo().getValueAt(i,3).toString().equalsIgnoreCase("0.5") && !(getModelo().getValueAt(i,4).toString().equalsIgnoreCase("0.5"))){
                if(!this.mensaje){//if esclusivamente para mostrar o no el mensaje
                     JOptionPane.showMessageDialog(TableJornada, "Los resultados válidos son: 1 - 0 , 0 - 1, 0.5 - 0.5", "Error al insertar los datos de la tabla", JOptionPane.ERROR_MESSAGE, null);
                     this.mensaje = true;
                 }
                P.setNegras(-1);
                P.setBlancas(-1);
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
     * Recibe un valor en formato cadena y comprueba que son un número entero positivo
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
     * Método especial para el ajedrez, para comprobar que los números que se incluyen son 1, 0.5, o 0
     * @param cadena
     * @return 
     */
    public double validarDoubles(String cadena){
        double numero;
        if(!cadena.equalsIgnoreCase("")){
        //Si la casilla está vacía ignoramos el chequeo, pues no hay información que comprobar
            try{
               //Casteamos la cadena pasada
              numero = Double.parseDouble(cadena);


              if(!(numero == 0 || numero == 1 || numero == 0.5)){
                //Si lo recibido es un número negativo
                if(!this.mensaje){//if esclusivamente para mostrar o no el mensaje
                    JOptionPane.showMessageDialog(TableJornada, "Los números válidos son 1, 0.5, 0", "Error al insertar los datos de la tabla", JOptionPane.ERROR_MESSAGE, null);
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
                    JOptionPane.showMessageDialog(TableJornada, "Los números válidos son 1, 0.5, 0", "Error al insertar los datos de la tabla", JOptionPane.ERROR_MESSAGE, null);
                    this.mensaje = true;
                }
                numero = -1;
                return numero;
            }
        }//End if de chequeo de cadena
            return -1;//Devolvemos el número que será tratado por nuestro programa
    }//End of validarDoubles()

    /**
     * Método que carga la clasificación de los participantes
     */
    @Override
     public void cargarClasificacion(){
        //Textos para las columnas
        String[] columnasLocal = {"POS","COMPETIDOR", "PJ", "PG", "PE", "PP", "PTOS", "SB"};
        setColumnasClasf(columnasLocal);
        //Indicamos que las columnas de la clasificación no serán editables
        final boolean[] canEdit = new boolean [] {false, false, false, false, false, false, false, false};
        //Asignamos los nuevos anchos al array (los del archivo.txt)
        setNumc(getAnchoTabla(getNumc().length, CODIGO_ANCHO_CLASF));
        //Asignamos los anchos a las columnas
        int[] anchos = {(anchoTabla*getNumc()[0])/100, (anchoTabla*getNumc()[1])/100, (anchoTabla*getNumc()[2])/100, (anchoTabla*getNumc()[3])/100, (anchoTabla*getNumc()[4])/100, (anchoTabla*getNumc()[5])/100, (anchoTabla*getNumc()[6])/100, (anchoTabla*getNumc()[7])/100};
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
        //Ahora usamos el for-each y obtenemos la lista de partidos de este objeto Jornada (Casteamos la lista como EquipoFutbol para que los reconozca como tales)
        for (EquipoAjedrez equipo: (ArrayList<EquipoAjedrez>)getCastEquipoLista().getEquipos())
            //Si el local o el visitante descansan ignoramos la acción
            if(equipo.getNombre().equalsIgnoreCase("EquipoFantasma")){
                //No hagas nada pues no este equipo es el comodín para los torneos impares
            }else{
                getClasificacion().addRow(equipo.representarEquipo()); 
            }     
        //Indicamos a la tabla cual es su modelo
        getTableClasificacion().setModel(getClasificacion());
        //Le asignamos el render a la clasificación
        renderClasf = new RenderAjedrezClasf();
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
         double blancas, negras;
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
                 PartidoAjedrez P = (PartidoAjedrez) J.partidos.get(i);
                 local = P.getLocal();
                 blancas = P.getBlancas();
                 negras = P.getNegras();
                 visitante = P.getVisitante();

                 //Ahora realizaremos los cálculos y asignaremos los puntos
                 for (EquipoAjedrez equipo: (ArrayList<EquipoAjedrez>)getCastEquipoLista().getEquipos()){                    
                    //Asignamos los datos al equipo (Si coincide el nombre y los goles son válidos)
                    asignarDatos(equipo, local, blancas, negras, visitante);//Casteamos el Objeto recibido como Equipo de Futbol                       
                }
             }//End for i
         }//End for j        
         //Calculamos el SB de los equipos
         calcularSB();
         contadorPartidos = 0;
         //Finalmente procedemos a aplicar las posibles sanciones en la clasificación 
         //Recorremos el HashMap fuera del for de jornadas y aplicamos las sanciones a los equipos (Si coincide el nombre del equipo le restamos los puntos indicados)
         for (EquipoAjedrez equipo: (ArrayList<EquipoAjedrez>)getCastEquipoLista().getEquipos()){
            for (Map.Entry<String, Integer> entry : getSancionados().entrySet()) {
                if(entry.getKey().equalsIgnoreCase(equipo.getNombre())){
                    equipo.setPtos(equipo.getPtos() - entry.getValue());
                }
            }//end for HashMap
         }//end for lista de equipos
     }//End calcularClasificacion()
     
     /**
      * Método que pone a 0 los datos de los equipos para no duplicar los datos al pulsar el botón Actualizar Clasificación
      */
    @Override
     public void borrarClasificacion(){
         for (EquipoAjedrez equipo: (ArrayList<EquipoAjedrez>)getCastEquipoLista().getEquipos()){ 
             //Ponemos a 0 todos los marcadores en todos los equipos (para no duplicar datos)              
                equipo.setPj(0);
                equipo.setPg(0);
                equipo.setPe(0);
                equipo.setPp(0);
                equipo.setPtos(0);
                equipo.setSb(0);
         }//end for
     }//End of borrarClasificacion()
     
     /**
      * Método que asigna la puntuación, goles y partidos... al equipo pasado
      * El equipo ha de coincidir con el local o el visitante, de lo contario se dejará pasar
      * Si los goles son -1 o null se ignorará la información, por lo que no se contará ese partido
      * @param equipo
      * @param local
      * @param blancas
      * @param negras
      * @param visitante 
      */
     public void asignarDatos(EquipoAjedrez equipo, String local, double blancas, double negras, String visitante){
         //Primero comprobamos que los resultados son válidos, si es -1
         if(blancas < 0 || negras < 0){
             //Ignoramos el cálculo
         }else{
            if(equipo.getNombre().equalsIgnoreCase(local)){
               //Si gana el equipo local le sumamos 3 puntos
               if(blancas > negras){
                   equipo.setPtos(equipo.getPtos() + 1);
                   equipo.setPg(equipo.getPg() + 1);
               }else if (blancas < negras){
                   //No sumamos ningún punto
                   equipo.setPp(equipo.getPp() + 1);
               }else if(blancas == negras){
                   //Al empatar sumamos 1 pto
                   equipo.setPtos(equipo.getPtos() + 0.5);
                   equipo.setPe(equipo.getPe() + 1);
               }else{
                   System.out.println("Error al asignar la puntuación, la combinación es ilógica");
               }
               //Sumamos el partido jugado
               equipo.setPj(equipo.getPj() + 1);
           }else if(equipo.getNombre().equalsIgnoreCase(visitante)){//End if local   
               //Si gana el equipo visitante le sumamos 3 puntos
               if(negras > blancas){
                   equipo.setPtos(equipo.getPtos() + 1);
                   equipo.setPg(equipo.getPg() + 1);
               }else if (negras < blancas){
                   //No sumamos ningún punto
                   equipo.setPp(equipo.getPp() + 1);
               }else if(negras == blancas){
                   //Al empatar sumamos 1 pto
                   equipo.setPtos(equipo.getPtos() + 0.5);
                   equipo.setPe(equipo.getPe() + 1);
               }else{
                   System.out.println("Error al asignar la puntuación, la combinación es ilógica");
               }
               //Sumamos el partido jugado
               equipo.setPj(equipo.getPj() + 1);
           }else{//End if visitante
               //Si el equipo no está en el partido no cambiamos nada
           }//End if equipos no seleccionados
            /* Si el número de partidos jugados del equipo es igual al de jorandas totales o en caso de equipos impares
               al de jornada totales - 2 caculará el SB (aunque si el resto de equipos no ha jugado todos sus partidos no funcionará)*/ 
        }//End if
    }//End asignarDatos()
     
     /**
      * Método que ordena el ArrayList con el que mostraremos la clasificación
      * Ordenamos en orden inverso a prioridad, primero por la puntuación Sonneborn-Berger, luego por número de victorias y por último por puntos
      * Falta añadir Enfrentamiento Directo (ED) (H2H head to head)
      */
    @Override
     public void ordenarClasificacion(){           
         /**
          * El SB se calcula al final del campeonato y es la suma de los puntos totales de los rivales por cada victoria sobre ellos y la mitad de puntos totales por cada empate
          */
         //Le indicamos al comparador que el tipo de objeto a ordenar son Equipos
        Collections.sort(getCastEquipoLista().getEquipos(), new Comparator<EquipoAjedrez>() {
            @Override
            public int compare(EquipoAjedrez e1, EquipoAjedrez e2) {
                    // Aqui esta el truco, ahora comparamos p2 con p1 y no al reves como antes
                    return new Double(e2.getSb()).compareTo(e1.getSb());
            }                                        
        });
         
         //Le indicamos al comparador que el tipo de objeto a ordenar son Equipos
        Collections.sort(getCastEquipoLista().getEquipos(), new Comparator<EquipoAjedrez>() {
            @Override
            public int compare(EquipoAjedrez e1, EquipoAjedrez e2) {
                    // Aqui esta el truco, ahora comparamos p2 con p1 y no al reves como antes
                    return new Integer(e2.getPg()).compareTo(e1.getPg());
            }                                        
        });
        
        Collections.sort(getCastEquipoLista().getEquipos(), new Comparator<EquipoAjedrez>() {
            @Override
            public int compare(EquipoAjedrez e1, EquipoAjedrez e2) {
                    // Aqui esta el truco, ahora comparamos p2 con p1 y no al reves como antes
                    return new Double(e2.getPtos()).compareTo(e1.getPtos());
            }                                        
        });
        
        int k=0;//Índice que usaremos para asignar posiciones a los equipos
        for (EquipoAjedrez equipo: (ArrayList<EquipoAjedrez>)getCastEquipoLista().getEquipos()){
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
      * Calcula el SB al final del torneo
      */
     private void calcularSB(){
         
         int indice = 0;//Indice para los arrays auxiliares
         String[] rivales = new String[castEquipoLista.getEquipos().size()];//Guardamos los nombres de los rivales con su puntuación en dos arrays
         double[] puntos = new double[castEquipoLista.getEquipos().size()];//Guardamos de forma auxiliar los puntos del oponente
         double[] sb = new double[castEquipoLista.getEquipos().size()];//Guardamos de forma auxiliar los puntos del oponente
         int[] partidosJugados = new int[castEquipoLista.getEquipos().size()];//Guardamos los partidos jugados por cada jugador
         double total = 0;//Variable que guarda la puntuación total
         int partidosPorJornada;
         
         //Le damos el valor adecuado a la variable partidosPorJornada
         if(castEquipoLista.getEquipos().size() % 2 != 0){
             partidosPorJornada = calendario.jornadas.get(0).getListaPartidos().size() - 1;
         }else{
             partidosPorJornada = calendario.jornadas.get(0).getListaPartidos().size();
         }
         
         //Si el número de jugadores con todas las partidas jugadas es similar al número total de equipos, podemos calcular el SB              
        for (EquipoAjedrez equipo: (ArrayList<EquipoAjedrez>)getCastEquipoLista().getEquipos()){ 
           rivales[indice] = equipo.getNombre();
           puntos[indice] = equipo.getPtos();
           partidosJugados[indice] = equipo.getPj();
           indice++;
        }//End for
        
        indice = 0;//reseteamos el índice
        for (EquipoAjedrez equipo: (ArrayList<EquipoAjedrez>)getCastEquipoLista().getEquipos()){
            //Hacemos un for para que recorra cada jornada
            for (int jor = 0; jor < calendario.getCalendario().size(); jor++) {
                //Creamos una variable Jornada y obtenemos la jornada indicada de nuestro objeto calendario
                Jornada J = getCalendario().jornadas.get(jor);//coge la jornada como variable global, -1 porque es un índice
                int filas =  getTableJornada().getRowCount();//número de filas en la tabla  
                //Por cada fila guardo los datos en el partido correspondiente del ArrayList (partidos de una jornada)
                for (int i = 0; i < filas; i++) {
                    contadorPartidos++;
                    //Obtenemos el partido en el ArrayList y le asignamos los nuevos datos                  
                     PartidoAjedrez P = (PartidoAjedrez) J.partidos.get(i);
                     //Mientras en el local coincida con el equipo que juega ese partido
                     if(P.getLocal().equalsIgnoreCase(equipo.getNombre())){
                           for (int j = 0; j < castEquipoLista.getEquipos().size(); j++) {
                               if(P.getVisitante().equalsIgnoreCase(rivales[j])){
                                   total += (P.getBlancas() * puntos[j]);
                               }
                           }//end for                      
                     }else if(P.getVisitante().equalsIgnoreCase(equipo.getNombre())){
                         for (int j = 0; j < castEquipoLista.getEquipos().size(); j++) {
                            if(P.getLocal().equalsIgnoreCase(rivales[j])){
                                total += (P.getNegras() * puntos[j]);
                            }
                         }//end for
                     }
                }
            }//End for jornadas
            //Guardamos el total en el array sb y sumamos uno al indice para que el siguente vaya en la siguiente casilla
            sb[indice] = total;
            indice++;
            total = 0;
            //Esta condicion comprueba que sea el último partido y sólo entonces ejecutamos el código interior ()
            if(contadorPartidos == (calendario.jornadas.size() * partidosPorJornada * castEquipoLista.getEquipos().size())){
                //Calculamos el número de partidos correctos para el calculo posterior
                if(idaVuelta){//Si se descansa y hay ida y vuelta
                    if(castEquipoLista.getEquipos().size() % 2 != 0){
                        if(coincideNumero(partidosJugados, (calendario.jornadas.size() - 2))){
                           indice = 0;
                           for (EquipoAjedrez equipoAj: (ArrayList<EquipoAjedrez>)getCastEquipoLista().getEquipos()){
                                equipoAj.setSb(sb[indice]);
                                indice++;
                           }
                        }else{
                            equipo.setSb(0.0);//Si no se han jugado todos los partidos, asignamos 0 al sb
                        }
                    }else{//if %2
                        if(coincideNumero(partidosJugados, (calendario.jornadas.size()))){
                           indice = 0;
                           for (EquipoAjedrez equipoAj: (ArrayList<EquipoAjedrez>)getCastEquipoLista().getEquipos()){
                                equipoAj.setSb(sb[indice]);
                                indice++;
                           }
                        }else{
                            equipo.setSb(0.0);//Si no se han jugado todos los partidos, asignamos 0 al sb
                        }
                    }//end if %2
                }else{//if idavuelta
                    if(castEquipoLista.getEquipos().size() % 2 != 0){
                        if(coincideNumero(partidosJugados, (calendario.jornadas.size() - 1))){
                           indice = 0;
                           for (EquipoAjedrez equipoAj: (ArrayList<EquipoAjedrez>)getCastEquipoLista().getEquipos()){
                                equipoAj.setSb(sb[indice]);
                                indice++;
                           }
                        }else{
                            equipo.setSb(0.0);//Si no se han jugado todos los partidos, asignamos 0 al sb
                        }
                    }else{//if %2
                        if(coincideNumero(partidosJugados, (calendario.jornadas.size()))){
                           indice = 0;
                           for (EquipoAjedrez equipoAj: (ArrayList<EquipoAjedrez>)getCastEquipoLista().getEquipos()){
                                equipoAj.setSb(sb[indice]);
                                indice++;
                           }
                        }else{
                            equipo.setSb(0.0);//Si no se han jugado todos los partidos, asignamos 0 al sb
                        }
                    }//end if pares/impares
                }//end if idavuelta
            }//end if contadorPartidos
        }//End for Equipos
               
    }//End of calcularSB()
     
    /**
     * Método que comprueba si todos los valores de un array son idénticos a un número entero
     * @param arrayInt
     * @param entero
     * @return 
     */
     public boolean coincideNumero(int[] arrayInt, int entero){
            int contador = 0;
         for (int i = 0; i < arrayInt.length; i++) {        
              if(arrayInt[i] == entero){
                  contador++;
                  //Si llegamos al final del contador devolvemos el true
                  if(contador == arrayInt.length){
                      return true;
                  }
              }else{
                  return false;
              }
         }
         //Si no coincide el array devolvemos false
         return false;
     }//End of coincideNumero()
     
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
                for (PartidoAjedrez partido :(ArrayList<PartidoAjedrez>) J.getListaPartidos()){ 
                    
                    //Creamos un elemento partido por cada partido en la lista
                    Element xml_partido = new Element("partido");
                    
                    xml_partido.addContent(new Element("fecha").setText(partido.getFecha()));
                    xml_partido.addContent(new Element("hora").setText(partido.getHora()));
                    xml_partido.addContent(new Element("local").setText(partido.getLocal()));
                    xml_partido.addContent(new Element("blancas").setText((Double.toString(partido.getBlancas()))));//Convertimos los valores enteros a String
                    xml_partido.addContent(new Element("negras").setText((Double.toString(partido.getNegras()))));
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
        
        for (EquipoAjedrez equipo: (ArrayList<EquipoAjedrez>)getCastEquipoLista().getEquipos())
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
                xml_equipo.addContent(new Element("pe").setText(Integer.toString(equipo.getPe())));
                xml_equipo.addContent(new Element("pp").setText(Integer.toString(equipo.getPp())));
                xml_equipo.addContent(new Element("ptosAj").setText(Double.toString(equipo.getPtos())));
                xml_equipo.addContent(new Element("sb").setText(Double.toString(equipo.getSb())));
                
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
        //Añadimos los partidos de la jornada al datasource
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
        
        File archivoInforme = new File("src/informes/futbolJornadaMesa.jasper");
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
            Logger.getLogger(MetodosAjedrez.class.getName()).log(Level.SEVERE, null, ex);
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
        DataSourceAjedrezResultados datasource = new DataSourceAjedrezResultados();  
       //Añadimos los partidos de la jornada al datasource
        if(aplazados){
             //Añadimos los partidos aplazados al datasource
              for (int i = 0; i < partidosAplazados.size(); i++) {
                 PartidoAjedrez P = (PartidoAjedrez) partidosAplazados.get(i);
                 datasource.addPartido(P);
              }
              jornadaText = "Aplz";//Cambiamo el nombre de la jornada a Aplazados
         }else{
             Jornada J = getCalendario().jornadas.get(getJornada() - 1);//coge la jornada como variable global, -1 porque es un índice
              //Añadimos los partidos de la jornada al datasource
                  for (int i = 0; i < J.getListaPartidos().size(); i++) {
                      PartidoAjedrez P = (PartidoAjedrez) J.partidos.get(i);
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
        
        File archivoInforme = new File("src/informes/ajedrezResultados.jasper");
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
            Logger.getLogger(MetodosAjedrez.class.getName()).log(Level.SEVERE, null, ex);
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
       ArrayList<EquipoAjedrez> equipos = castEquipoLista.getEquipos();
        DataSourceAjedrezClasificacion datasource = new DataSourceAjedrezClasificacion();  
        //Añadimos los partidos de la jornada al datasource
        for (EquipoAjedrez equipo : equipos) {
            EquipoAjedrez E = (EquipoAjedrez) equipo;
            //Comprobamos si descansa algún equipo y guardamos su nombre en la variable indicada
            if(E.getNombre().equalsIgnoreCase("EquipoFantasma")){
                //Ignoramos al EquipoFantasma
            }else if(E.getNombre().equalsIgnoreCase("EquipoFantasma")){
                //Ignoramos al EquipoFantasma
            }else{
                datasource.addEquipo(E);
            }
        }  
        
        File archivoInforme = new File("src/informes/ajedrezClasificacion.jasper");
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
            Logger.getLogger(MetodosAjedrez.class.getName()).log(Level.SEVERE, null, ex);
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
                archivoInforme = new File("src/informes/ajedrezCalendarioBIG.jasper");
            }else{
                archivoInforme = new File("src/informes/ajedrezCalendario.jasper");
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
                Logger.getLogger(MetodosAjedrez.class.getName()).log(Level.SEVERE, null, ex);
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

            File archivoInforme = new File("src/informes/futbolJornadaMesa.jasper");
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
                Logger.getLogger(MetodosAjedrez.class.getName()).log(Level.SEVERE, null, ex);
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
            DataSourceAjedrezResultados datasource = new DataSourceAjedrezResultados();  
            //Añadimos los partidos de la jornada al datasource
            for (int i = 0; i < J.getListaPartidos().size(); i++) {
                PartidoAjedrez P = (PartidoAjedrez) J.partidos.get(i);
                //Comprobamos si descansa algún equipo y guardamos su nombre en la variable indicada
                if(P.getLocal().equalsIgnoreCase("EquipoFantasma")){
                    descansa = P.getVisitante();
                }else if(P.getVisitante().equalsIgnoreCase("EquipoFantasma")){
                    descansa = P.getLocal();
                }else{
                  datasource.addPartido(P);
                } 
            }  

            File archivoInforme = new File("src/informes/ajedrezResultados.jasper");
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
                Logger.getLogger(MetodosAjedrez.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(MetodosAjedrez.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MetodosAjedrez.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(MetodosAjedrez.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MetodosAjedrez.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(MetodosAjedrez.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MetodosAjedrez.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Finalmente cargamos los datos modificados
        almacen.clear();
        this.cargarTabla(jornada);
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
            Logger.getLogger(MetodosAjedrez.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MetodosAjedrez.class.getName()).log(Level.SEVERE, null, ex);
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
