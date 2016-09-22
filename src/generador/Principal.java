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

import equipos.EquipoTenis;
import equipos.EquipoAjedrez;
import equipos.EquipoFutbol;
import equipos.Equipo;
import equipos.EquipoRugby;
import equipos.EquipoVoleibol;
import equipos.EquipoHockeyHielo;
import equipos.EquipoBasket;
import metodos.MetodosBalonmano;
import metodos.MetodosFutbol;
import metodos.Metodos;
import metodos.MetodosTenis3Sets;
import metodos.MetodosVoleibol;
import metodos.MetodosRugby;
import metodos.MetodosTenis5Sets;
import metodos.MetodosHockeyHielo;
import metodos.MetodosBeisbol;
import metodos.MetodosFutbolAmericano;
import metodos.MetodosBasket;
import metodos.MetodosTenisDeMesa;
import metodos.MetodosBadminton;
import metodos.MetodosAjedrez;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.OK_CANCEL_OPTION;
import static javax.swing.JOptionPane.QUESTION_MESSAGE;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import Atxy2k.CustomTextField.RestrictedTextField;//Librería para limitar el JTextField
import java.awt.Desktop;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import metodos.MetodosEliminatoriaAjedrez;
import metodos.MetodosEliminatoriaBeisbol;
import metodos.MetodosEliminatoriaFutbol;
import metodos.MetodosEliminatoriaTenis3Sets;
import metodos.MetodosEliminatoriaTenis5Sets;
import metodos.MetodosEliminatoriaVoleibol;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;//Para los errores de análisis(parser) de XML contra Schema XSD
import org.xml.sax.SAXParseException;
import partidos.Partido;
import partidos.PartidoAjedrez;
import partidos.PartidoBeisbol;
import partidos.PartidoEliminatoriaAjedrez;
import partidos.PartidoEliminatoriaBeisbol;
import partidos.PartidoEliminatoriaFutbol;
import partidos.PartidoEliminatoriaTenis3Sets;
import partidos.PartidoEliminatoriaTenis5Sets;
import partidos.PartidoHockeyHielo;
import partidos.PartidoRugby;
import partidos.PartidoTenis3Sets;
import partidos.PartidoTenis5Sets;
import static java.lang.String.valueOf;
import java.util.HashMap;

/**
 *
 * @author Alvarielli
 */
public class Principal extends javax.swing.JFrame {
    
    public static Properties fileProp = new Properties();//Para el archivo de propiedades
    private static Splash splash;
    private static String ruta = "";//Ruta que utilizamos para que el JFileChooser se acuerde de donde hemos guardados los archivo
    private static String rutaPDF = "";//Ruta que utilizamos para el JFileChooser con los informes
    private String rutaXMLCalendar = "";//Variable de ruta del archivo XML para el Calendario PDF
    private String nombreArchivoGlobal = "";//Guarda el nombre del archivo en uso
    private Image icon = new ImageIcon(getClass().getResource("/imagenes/logoapp.png")).getImage();
    private static ImageIcon[] deportesIcon = new ImageIcon[20];//Lo usaremos con el JComboBox para elegir la imagen que se muestra en función del deporte seleccionado
    private static ImageIcon[] deportesFondo = new ImageIcon[20];//Imágenes de Fondo de cada deporte
    private static int totalDeportesIcon = deportesIcon.length;
    private Dimension dim;//Se utiliza para ajustar el tamaño de pantalla
    private JScrollPane scroller;//Scroll para la pestaña Calendario
    private DefaultTableModel modelo, clasificacion;//DTM para las tablas del calendario y la clasificacion
    private String clipboard;//Guarda el valor del campo de la tabla seleccionado (simulando ser un portapapeles)
    private boolean checkClipboard;//Controla que se pulse el botón a la vez que se hace click en un campo de la tabla
    RestrictedTextField restricted;//restricción de caracteres para el JTextField (tb sólo números)
    Comparator<Integer> comparador;//Comparador que usamos para comparar los ptos de la clasificación
    
    //Creamos el objeto Calendario para que se creen las jornadas
    private Calendario calendario = new Calendario();//Guarda todas las jornadas con sus partidos (se obtiene de la clase generador al crear las jornadas)
    private Generador generador = new Generador();//Creamos una instancia de la clase generador para usarla en Principal
    private int jornada = 1;//Guarda el número de Jornada que se está utilizando (se inicializa en la primera)
    
    private ArrayList<String> equipoLista = new ArrayList<>();//Ahora esta vacío, pero lo llenamos con los equipos que nos pasen de TorneoDialog
    private EquipoLista castEquipoLista  = new EquipoLista();//Lista con los Equipos como Objetos Equipo
    private Equipo miEquipo;//Lo usaremos para inicializar los distintos tipos de  Clases Equipo
    private static Metodos deporte;//Creamos un objeto que utilice los métodos del deporte seleccionado
    private boolean tercerCuartoPuesto;
    private boolean idaVuelta;
    
    //Variables necesarias para el schema XSD
    // Constantes para validacion de Schemas   
    final String JAXP_SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";  
    final String JAXP_SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaSource";  
    final String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";  
    final String MY_SCHEMA = "src/schemaXSD/gtSchema.xsd ";// src/schemaXSD/gtSchema.xsd
    private boolean mensajeSchema = false;//Se utiliza para que el programa muestre el mensaje de error al introducir datos sólo una vez
    
    //Variables necesarias para equipos aplazados
    private ArrayList<Partido> partidosAplazadosPr = new ArrayList<>();
    //Variables para equipos sancionados
    private HashMap<String, Integer> sancionadosPr = new HashMap<>();
    private ArrayList<Equipo> listaEquiposAux = new ArrayList<>();
    private ArrayList<String> listaEquiposSancion = new ArrayList<>();
   
    /** El error java illegal stat of type expected ; or <identifier> se produce cuando metes en la clase principal un código que deber ir en el main() 
        Si la línea es una declaración tiene que estar dentro de un método (o bloque initialiser, o constructor) **/
    
    /**
     * Creates new form Principal
     */
    public Principal() {
        initComponents();
        //Obtenemos el tamaño de pantalla del monitor y hacemos que la aplicación se adapte
        dim = super.getToolkit().getScreenSize();
        setSize(dim);
        setTitle("Generador de Torneos");
        setIconImage(icon);     
        //Limitamos el número de caracteres que acepta el JTextField y que sólo acepte números
        restricted = new RestrictedTextField(TextFieldJornada);
        restricted.setLimit(3);
        restricted.setOnlyNums(true);
        PanelBotones.setVisible(false);
        ButtonClasificacion.setVisible(false);
        recolocarLabelImagenFondo();
        //deporte.cargarClasificacion();//La cargamos al principio vacía para que se vean los campos
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pestanas = new javax.swing.JTabbedPane();
        panelCalendario = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TableJornada = new JTable();
        PanelBotones = new javax.swing.JPanel();
        BotonJornada = new javax.swing.JButton();
        TextFieldJornada = new javax.swing.JTextField();
        BotonIzda = new javax.swing.JButton();
        BotonDcha = new javax.swing.JButton();
        LabelTotalJornadas = new javax.swing.JLabel();
        LabelDescansa = new javax.swing.JLabel();
        LabelEquipoDescansa = new javax.swing.JLabel();
        PanelCalendarioDcha = new javax.swing.JPanel();
        PanelCalendarioIzda = new javax.swing.JPanel();
        PanelCalendarioSuperior = new javax.swing.JPanel();
        LabelJornada = new javax.swing.JLabel();
        panelClasificacion = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TableClasificacion = new javax.swing.JTable();
        PanelClasificacionSuperior = new javax.swing.JPanel();
        PanelClasificacionBotones = new javax.swing.JPanel();
        ButtonClasificacion = new javax.swing.JButton();
        PanelClasificacionIzda = new javax.swing.JPanel();
        PanelClasificacionDcha = new javax.swing.JPanel();
        PanelTitulo = new javax.swing.JPanel();
        LabelNombreTorneo = new javax.swing.JLabel();
        LabelImagen = new javax.swing.JLabel();
        LabelImagenFondo = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        MenuArchivo = new javax.swing.JMenu();
        nuevoTorneo = new javax.swing.JMenuItem();
        abrirTorneo = new javax.swing.JMenuItem();
        guardar = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        salir = new javax.swing.JMenuItem();
        MenuInformes = new javax.swing.JMenu();
        informeJornada = new javax.swing.JMenuItem();
        informeResultados = new javax.swing.JMenuItem();
        informeClasificacion = new javax.swing.JMenuItem();
        informeCalendario = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        informeTodasJornadas = new javax.swing.JMenuItem();
        informeTodosResultados = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        informeJornadaAplazados = new javax.swing.JMenuItem();
        informeResultadosAplazados = new javax.swing.JMenuItem();
        MenuOpciones = new javax.swing.JMenu();
        tablaMod = new javax.swing.JMenuItem();
        tablaModClasf = new javax.swing.JMenuItem();
        aplicarSanciones = new javax.swing.JMenuItem();
        MenuAyuda = new javax.swing.JMenu();
        AcercaDe = new javax.swing.JMenuItem();
        Atribuciones = new javax.swing.JMenuItem();
        Manual = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        //Añado el tamaño que quiero para el TabbedPane
        pestanas.setPreferredSize(new Dimension(800, 600));
        pestanas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pestanasMouseClicked(evt);
            }
        });

        scroller = new JScrollPane(panelCalendario);
        panelCalendario.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        panelCalendario.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        panelCalendario.setLayout(new java.awt.BorderLayout());

        TableJornada.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("generador/Bundle"); // NOI18N
        TableJornada.setToolTipText(bundle.getString("Principal.TableJornada.toolTipText")); // NOI18N
        TableJornada.setRowHeight(25);
        TableJornada.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableJornadaMouseClicked(evt);
            }
        });
        TableJornada.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TableJornadaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TableJornadaKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(TableJornada);

        panelCalendario.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        BotonJornada.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        BotonJornada.setText(bundle.getString("Principal.BotonJornada.text")); // NOI18N
        BotonJornada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonJornadaActionPerformed(evt);
            }
        });

        TextFieldJornada.setBackground(new java.awt.Color(200, 200, 200));
        TextFieldJornada.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        TextFieldJornada.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        TextFieldJornada.setText(bundle.getString("Principal.TextFieldJornada.text")); // NOI18N
        TextFieldJornada.setToolTipText("Indique la jornada que desea consultar");
        TextFieldJornada.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TextFieldJornadaKeyPressed(evt);
            }
        });

        BotonIzda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/botonleft.png"))); // NOI18N
        BotonIzda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonIzdaActionPerformed(evt);
            }
        });

        BotonDcha.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/botonright.png"))); // NOI18N
        BotonDcha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonDchaActionPerformed(evt);
            }
        });

        LabelTotalJornadas.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        LabelTotalJornadas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        LabelDescansa.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        LabelDescansa.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LabelDescansa.setText(bundle.getString("Principal.LabelDescansa.text")); // NOI18N

        LabelEquipoDescansa.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        LabelEquipoDescansa.setForeground(new java.awt.Color(0, 0, 119));

        javax.swing.GroupLayout PanelBotonesLayout = new javax.swing.GroupLayout(PanelBotones);
        PanelBotones.setLayout(PanelBotonesLayout);
        PanelBotonesLayout.setHorizontalGroup(
            PanelBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelBotonesLayout.createSequentialGroup()
                .addContainerGap(2987, Short.MAX_VALUE)
                .addComponent(BotonIzda, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(BotonJornada, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(TextFieldJornada, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(BotonDcha, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(LabelDescansa, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LabelEquipoDescansa, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LabelTotalJornadas, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(2988, Short.MAX_VALUE))
        );
        PanelBotonesLayout.setVerticalGroup(
            PanelBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelBotonesLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(PanelBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(LabelDescansa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(TextFieldJornada, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                    .addComponent(BotonJornada, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                    .addComponent(BotonIzda, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                    .addComponent(BotonDcha, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                    .addComponent(LabelEquipoDescansa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(LabelTotalJornadas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(49, Short.MAX_VALUE))
        );

        TextFieldJornada.getAccessibleContext().setAccessibleDescription(bundle.getString("Principal.TextFieldJornada.AccessibleContext.accessibleDescription")); // NOI18N

        panelCalendario.add(PanelBotones, java.awt.BorderLayout.PAGE_END);

        PanelCalendarioDcha.setMinimumSize(new java.awt.Dimension(100, 0));

        javax.swing.GroupLayout PanelCalendarioDchaLayout = new javax.swing.GroupLayout(PanelCalendarioDcha);
        PanelCalendarioDcha.setLayout(PanelCalendarioDchaLayout);
        PanelCalendarioDchaLayout.setHorizontalGroup(
            PanelCalendarioDchaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        PanelCalendarioDchaLayout.setVerticalGroup(
            PanelCalendarioDchaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 987, Short.MAX_VALUE)
        );

        panelCalendario.add(PanelCalendarioDcha, java.awt.BorderLayout.EAST);

        PanelCalendarioIzda.setMinimumSize(new java.awt.Dimension(100, 0));

        javax.swing.GroupLayout PanelCalendarioIzdaLayout = new javax.swing.GroupLayout(PanelCalendarioIzda);
        PanelCalendarioIzda.setLayout(PanelCalendarioIzdaLayout);
        PanelCalendarioIzdaLayout.setHorizontalGroup(
            PanelCalendarioIzdaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        PanelCalendarioIzdaLayout.setVerticalGroup(
            PanelCalendarioIzdaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 987, Short.MAX_VALUE)
        );

        panelCalendario.add(PanelCalendarioIzda, java.awt.BorderLayout.WEST);

        PanelCalendarioSuperior.setMinimumSize(new java.awt.Dimension(50, 0));

        LabelJornada.setBackground(new java.awt.Color(0, 204, 204));
        LabelJornada.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        LabelJornada.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LabelJornada.setText(bundle.getString("Principal.LabelJornada.text")); // NOI18N
        LabelJornada.setToolTipText(bundle.getString("Principal.LabelJornada.toolTipText")); // NOI18N

        javax.swing.GroupLayout PanelCalendarioSuperiorLayout = new javax.swing.GroupLayout(PanelCalendarioSuperior);
        PanelCalendarioSuperior.setLayout(PanelCalendarioSuperiorLayout);
        PanelCalendarioSuperiorLayout.setHorizontalGroup(
            PanelCalendarioSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelCalendarioSuperiorLayout.createSequentialGroup()
                .addContainerGap(3154, Short.MAX_VALUE)
                .addComponent(LabelJornada, javax.swing.GroupLayout.PREFERRED_SIZE, 800, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(3154, Short.MAX_VALUE))
        );
        PanelCalendarioSuperiorLayout.setVerticalGroup(
            PanelCalendarioSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelCalendarioSuperiorLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(LabelJornada, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        panelCalendario.add(PanelCalendarioSuperior, java.awt.BorderLayout.NORTH);

        pestanas.addTab(bundle.getString("Principal.panelCalendario.TabConstraints.tabTitle"), panelCalendario); // NOI18N

        panelClasificacion.setLayout(new java.awt.BorderLayout());

        TableClasificacion.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        TableClasificacion.setRowHeight(25);
        jScrollPane2.setViewportView(TableClasificacion);

        panelClasificacion.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        PanelClasificacionSuperior.setMinimumSize(new java.awt.Dimension(0, 50));

        javax.swing.GroupLayout PanelClasificacionSuperiorLayout = new javax.swing.GroupLayout(PanelClasificacionSuperior);
        PanelClasificacionSuperior.setLayout(PanelClasificacionSuperiorLayout);
        PanelClasificacionSuperiorLayout.setHorizontalGroup(
            PanelClasificacionSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 7108, Short.MAX_VALUE)
        );
        PanelClasificacionSuperiorLayout.setVerticalGroup(
            PanelClasificacionSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        panelClasificacion.add(PanelClasificacionSuperior, java.awt.BorderLayout.NORTH);
        PanelClasificacionSuperior.getAccessibleContext().setAccessibleName(bundle.getString("Principal.PanelClasificacionSuperior.AccessibleContext.accessibleName")); // NOI18N

        ButtonClasificacion.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        ButtonClasificacion.setText(bundle.getString("Principal.ButtonClasificacion.text")); // NOI18N
        ButtonClasificacion.setToolTipText(bundle.getString("Principal.ButtonClasificacion.toolTipText")); // NOI18N
        ButtonClasificacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonClasificacionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelClasificacionBotonesLayout = new javax.swing.GroupLayout(PanelClasificacionBotones);
        PanelClasificacionBotones.setLayout(PanelClasificacionBotonesLayout);
        PanelClasificacionBotonesLayout.setHorizontalGroup(
            PanelClasificacionBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelClasificacionBotonesLayout.createSequentialGroup()
                .addContainerGap(3419, Short.MAX_VALUE)
                .addComponent(ButtonClasificacion, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(3419, Short.MAX_VALUE))
        );
        PanelClasificacionBotonesLayout.setVerticalGroup(
            PanelClasificacionBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelClasificacionBotonesLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(ButtonClasificacion, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50))
        );

        panelClasificacion.add(PanelClasificacionBotones, java.awt.BorderLayout.SOUTH);

        PanelClasificacionIzda.setMinimumSize(new java.awt.Dimension(100, 0));

        javax.swing.GroupLayout PanelClasificacionIzdaLayout = new javax.swing.GroupLayout(PanelClasificacionIzda);
        PanelClasificacionIzda.setLayout(PanelClasificacionIzdaLayout);
        PanelClasificacionIzdaLayout.setHorizontalGroup(
            PanelClasificacionIzdaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        PanelClasificacionIzdaLayout.setVerticalGroup(
            PanelClasificacionIzdaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1004, Short.MAX_VALUE)
        );

        panelClasificacion.add(PanelClasificacionIzda, java.awt.BorderLayout.WEST);

        PanelClasificacionDcha.setMinimumSize(new java.awt.Dimension(100, 0));

        javax.swing.GroupLayout PanelClasificacionDchaLayout = new javax.swing.GroupLayout(PanelClasificacionDcha);
        PanelClasificacionDcha.setLayout(PanelClasificacionDchaLayout);
        PanelClasificacionDchaLayout.setHorizontalGroup(
            PanelClasificacionDchaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        PanelClasificacionDchaLayout.setVerticalGroup(
            PanelClasificacionDchaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1004, Short.MAX_VALUE)
        );

        panelClasificacion.add(PanelClasificacionDcha, java.awt.BorderLayout.EAST);

        pestanas.addTab(bundle.getString("Principal.panelClasificacion.TabConstraints.tabTitle"), panelClasificacion); // NOI18N

        getContentPane().add(pestanas, java.awt.BorderLayout.CENTER);

        PanelTitulo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        LabelNombreTorneo.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        LabelNombreTorneo.setText(bundle.getString("Principal.LabelNombreTorneo.text")); // NOI18N
        PanelTitulo.add(LabelNombreTorneo, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 20, 890, 128));

        LabelImagen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/question.png"))); // NOI18N
        PanelTitulo.add(LabelImagen, new org.netbeans.lib.awtextra.AbsoluteConstraints(31, 22, -1, -1));
        PanelTitulo.add(LabelImagenFondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 11, 4880, 162));

        getContentPane().add(PanelTitulo, java.awt.BorderLayout.PAGE_START);

        MenuArchivo.setText(bundle.getString("Principal.MenuArchivo.text")); // NOI18N
        MenuArchivo.setMargin(new java.awt.Insets(0, 0, 0, 5));

        nuevoTorneo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        nuevoTorneo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/New.png"))); // NOI18N
        nuevoTorneo.setText(bundle.getString("Principal.nuevoTorneo.text")); // NOI18N
        nuevoTorneo.setMargin(new java.awt.Insets(3, 3, 3, 3));
        nuevoTorneo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        nuevoTorneo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nuevoTorneoActionPerformed(evt);
            }
        });
        MenuArchivo.add(nuevoTorneo);

        abrirTorneo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        abrirTorneo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Open.png"))); // NOI18N
        abrirTorneo.setText(bundle.getString("Principal.abrirTorneo.text")); // NOI18N
        abrirTorneo.setMargin(new java.awt.Insets(3, 3, 0, 3));
        abrirTorneo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                abrirTorneoActionPerformed(evt);
            }
        });
        MenuArchivo.add(abrirTorneo);

        guardar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, java.awt.event.InputEvent.CTRL_MASK));
        guardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Save.png"))); // NOI18N
        guardar.setText(bundle.getString("Principal.guardar.text")); // NOI18N
        guardar.setMargin(new java.awt.Insets(3, 3, 0, 3));
        guardar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, java.awt.event.InputEvent.CTRL_MASK));
        guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guardarActionPerformed(evt);
            }
        });
        MenuArchivo.add(guardar);
        MenuArchivo.add(jSeparator1);

        salir.setText(bundle.getString("Principal.salir.text")); // NOI18N
        salir.setMargin(new java.awt.Insets(3, 3, 3, 3));
        salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salirActionPerformed(evt);
            }
        });
        MenuArchivo.add(salir);

        jMenuBar1.add(MenuArchivo);

        MenuInformes.setText(bundle.getString("Principal.MenuInformes.text")); // NOI18N
        MenuInformes.setToolTipText(bundle.getString("Principal.MenuInformes.toolTipText")); // NOI18N

        informeJornada.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/pdf.png"))); // NOI18N
        informeJornada.setText(bundle.getString("Principal.informeJornada.text")); // NOI18N
        informeJornada.setMargin(new java.awt.Insets(3, 3, 3, 3));
        informeJornada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                informeJornadaActionPerformed(evt);
            }
        });
        MenuInformes.add(informeJornada);

        informeResultados.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/pdf.png"))); // NOI18N
        informeResultados.setText(bundle.getString("Principal.informeResultados.text")); // NOI18N
        informeResultados.setMargin(new java.awt.Insets(3, 3, 3, 3));
        informeResultados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                informeResultadosActionPerformed(evt);
            }
        });
        MenuInformes.add(informeResultados);

        informeClasificacion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/pdf.png"))); // NOI18N
        informeClasificacion.setText(bundle.getString("Principal.informeClasificacion.text")); // NOI18N
        informeClasificacion.setMargin(new java.awt.Insets(3, 3, 3, 3));
        informeClasificacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                informeClasificacionActionPerformed(evt);
            }
        });
        MenuInformes.add(informeClasificacion);

        informeCalendario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/pdf.png"))); // NOI18N
        informeCalendario.setText(bundle.getString("Principal.informeCalendario.text")); // NOI18N
        informeCalendario.setMargin(new java.awt.Insets(3, 3, 3, 3));
        informeCalendario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                informeCalendarioActionPerformed(evt);
            }
        });
        MenuInformes.add(informeCalendario);
        MenuInformes.add(jSeparator2);

        informeTodasJornadas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/pdf.png"))); // NOI18N
        informeTodasJornadas.setText(bundle.getString("Principal.informeTodasJornadas.text")); // NOI18N
        informeTodasJornadas.setToolTipText(bundle.getString("Principal.informeTodasJornadas.toolTipText")); // NOI18N
        informeTodasJornadas.setMargin(new java.awt.Insets(3, 3, 3, 3));
        informeTodasJornadas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                informeTodasJornadasActionPerformed(evt);
            }
        });
        MenuInformes.add(informeTodasJornadas);

        informeTodosResultados.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/pdf.png"))); // NOI18N
        informeTodosResultados.setText(bundle.getString("Principal.informeTodosResultados.text")); // NOI18N
        informeTodosResultados.setToolTipText(bundle.getString("Principal.informeTodosResultados.toolTipText")); // NOI18N
        informeTodosResultados.setMargin(new java.awt.Insets(3, 3, 3, 3));
        informeTodosResultados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                informeTodosResultadosActionPerformed(evt);
            }
        });
        MenuInformes.add(informeTodosResultados);
        MenuInformes.add(jSeparator3);

        informeJornadaAplazados.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/pdf.png"))); // NOI18N
        informeJornadaAplazados.setText(bundle.getString("Principal.informeJornadaAplazados.text")); // NOI18N
        informeJornadaAplazados.setToolTipText(bundle.getString("Principal.informeJornadaAplazados.toolTipText")); // NOI18N
        informeJornadaAplazados.setMargin(new java.awt.Insets(3, 3, 3, 3));
        informeJornadaAplazados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                informeJornadaAplazadosActionPerformed(evt);
            }
        });
        MenuInformes.add(informeJornadaAplazados);

        informeResultadosAplazados.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/pdf.png"))); // NOI18N
        informeResultadosAplazados.setText(bundle.getString("Principal.informeResultadosAplazados.text")); // NOI18N
        informeResultadosAplazados.setToolTipText(bundle.getString("Principal.informeResultadosAplazados.toolTipText")); // NOI18N
        informeResultadosAplazados.setMargin(new java.awt.Insets(3, 3, 3, 3));
        informeResultadosAplazados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                informeResultadosAplazadosActionPerformed(evt);
            }
        });
        MenuInformes.add(informeResultadosAplazados);

        jMenuBar1.add(MenuInformes);

        MenuOpciones.setText(bundle.getString("Principal.MenuOpciones.text")); // NOI18N

        tablaMod.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, java.awt.event.InputEvent.CTRL_MASK));
        tablaMod.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/tablas_mod.png"))); // NOI18N
        tablaMod.setText(bundle.getString("Principal.tablaMod.text")); // NOI18N
        tablaMod.setToolTipText(bundle.getString("Principal.tablaMod.toolTipText")); // NOI18N
        tablaMod.setMargin(new java.awt.Insets(3, 3, 3, 3));
        tablaMod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tablaModActionPerformed(evt);
            }
        });
        MenuOpciones.add(tablaMod);

        tablaModClasf.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        tablaModClasf.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/tablas_mod.png"))); // NOI18N
        tablaModClasf.setText(bundle.getString("Principal.tablaModClasf.text")); // NOI18N
        tablaModClasf.setToolTipText(bundle.getString("Principal.tablaModClasf.toolTipText")); // NOI18N
        tablaModClasf.setMargin(new java.awt.Insets(3, 3, 3, 3));
        tablaModClasf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tablaModClasfActionPerformed(evt);
            }
        });
        MenuOpciones.add(tablaModClasf);

        aplicarSanciones.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        aplicarSanciones.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/sancionados.png"))); // NOI18N
        aplicarSanciones.setText(bundle.getString("Principal.aplicarSanciones.text")); // NOI18N
        aplicarSanciones.setToolTipText(bundle.getString("Principal.aplicarSanciones.toolTipText")); // NOI18N
        aplicarSanciones.setMargin(new java.awt.Insets(3, 3, 3, 3));
        aplicarSanciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aplicarSancionesActionPerformed(evt);
            }
        });
        MenuOpciones.add(aplicarSanciones);

        jMenuBar1.add(MenuOpciones);

        MenuAyuda.setText(bundle.getString("Principal.MenuAyuda.text")); // NOI18N

        AcercaDe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/info.png"))); // NOI18N
        AcercaDe.setText(bundle.getString("Principal.AcercaDe.text")); // NOI18N
        AcercaDe.setToolTipText(bundle.getString("Principal.AcercaDe.toolTipText")); // NOI18N
        AcercaDe.setMargin(new java.awt.Insets(3, 3, 3, 3));
        AcercaDe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AcercaDeActionPerformed(evt);
            }
        });
        MenuAyuda.add(AcercaDe);

        Atribuciones.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/camera.png"))); // NOI18N
        Atribuciones.setText(bundle.getString("Principal.Atribuciones.text")); // NOI18N
        Atribuciones.setToolTipText(bundle.getString("Principal.Atribuciones.toolTipText")); // NOI18N
        Atribuciones.setMargin(new java.awt.Insets(3, 3, 3, 3));
        Atribuciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AtribucionesActionPerformed(evt);
            }
        });
        MenuAyuda.add(Atribuciones);

        Manual.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/pdf.png"))); // NOI18N
        Manual.setText(bundle.getString("Principal.Manual.text")); // NOI18N
        Manual.setMargin(new java.awt.Insets(3, 3, 3, 3));
        Manual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ManualActionPerformed(evt);
            }
        });
        MenuAyuda.add(Manual);

        jMenuBar1.add(MenuAyuda);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void nuevoTorneoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nuevoTorneoActionPerformed
        if(castEquipoLista.getEquipos().size() > 0){
           int confirmacion =  JOptionPane.showConfirmDialog(this, "Si crea un nuevo torneo se perderán los datos actuales no guardados, ¿Está seguro de que desea continuar?", "Mensaje de aviso", OK_CANCEL_OPTION, QUESTION_MESSAGE, null); 
            //Comprobamos si pulso OK o Cancelar
           if(JOptionPane.OK_OPTION == confirmacion){
            //Si se confirma borramos los equipos y las jornadas para poder insertar unos nuevos (no podemos pasarlo a Torneo Dialog porque esa clase no contiene las variables necesarias)
            sancionadosPr.clear();
            listaEquiposSancion.clear();
            equipoLista.clear();
            calendario.jornadas.clear();
            LabelTotalJornadas.setText("");
            LabelEquipoDescansa.setText("");
            LabelNombreTorneo.setText("Crea o selecciona un torneo");
            LabelImagen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/question.png")));
            LabelImagenFondo.setIcon(null);//Quitamos la imagen de fondo
            castEquipoLista.getEquipos().clear();//Vacía la tabla de clasificación al vaciar los equipos que ya estaban guardados
            TableJornada.setVisible(false);
            TableClasificacion.setVisible(false);//Reinstauramos la posibilidad de ver la tabla por si el usuario carga una Liga tras una Eliminatoria
            idaVuelta = false;//Lo ponemos a false
            tercerCuartoPuesto = false;//Lo ponemos a false
            jornada = 1;//Ponemos la jornada en la primera al cargar un torneo nuevo   
            this.setTitle(java.util.ResourceBundle.getBundle("generador/Bundle").getString("GENERADOR DE TORNEOS"));
            
            //El segundo parámetro a "true" indica que el diálogo va a ser modal
            TorneoDialog altaDialog = new TorneoDialog(this, true);
            altaDialog.setVisible(true);
           }
        }else{
            //El segundo parámetro a "true" indica que el diálogo va a ser modal
            TorneoDialog altaDialog = new TorneoDialog(this, true);
            altaDialog.setVisible(true);
        }
    }//GEN-LAST:event_nuevoTorneoActionPerformed

    private void guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardarActionPerformed
        if(castEquipoLista.getEquipos().size() > 0){
            File archivo;//Creamos un archivo para poder pasar el filtro(ya que en realidad le da el formato en Metodos)
            JFileChooser fileChooser;
            FileNameExtensionFilter filter = new FileNameExtensionFilter("XML", "xml");
            //Establecemos la ruta ya guardada en propiedades como predeterminada
            ruta = fileProp.getProperty("rutaXML", ruta);
            System.out.println(ruta);
            if(ruta.equalsIgnoreCase("")){
                fileChooser = new JFileChooser();
            }else{
                fileChooser = new JFileChooser(ruta);
            }

            try{
                fileChooser.setToolTipText("Los caracteres / \\ : * < > | no están permitidos");
                if(fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){                   
                    deporte.guardarTabla();//Nos aseguramos de que los datos de la tabla esten guardados
                    ruta = valueOf(fileChooser.getCurrentDirectory());//obtenemos la ruta del archivo (el directorio)
                    String nombreArchivo = fileChooser.getSelectedFile().getName();//obtiene el nombre del archivo (el que le damos)
                    //Si el archivo no tiene más de 3 caracteres significa que no tiene el .xml añadido, así que, lo añadimos (cuando este añadido tendrá más de 3 caracteres y se saltará esta parte del if)
                    if(nombreArchivo.length() <= 3){
                        //si el archivo simplemente no tiene definida extensión se la añadimos
                        nombreArchivo = fileChooser.getSelectedFile().getName()+".xml";//obtiene el nombre del archivo (el que le damos) 
                        archivo = new File(nombreArchivo);//Inicializamos el archivo auxiliar
                        fileChooser.setSelectedFile(archivo);
                    }else if(nombreArchivo.substring(nombreArchivo.length() - 4).equalsIgnoreCase(".xml")){
                        nombreArchivo = fileChooser.getSelectedFile().getName();//obtiene el nombre del archivo (el que le damos)  
                        archivo = new File(nombreArchivo);//Inicializamos el archivo auxiliar
                    }else{
                        //si el archivo simplemente no tiene definida extensión se la añadimos
                        nombreArchivo = fileChooser.getSelectedFile().getName()+".xml";//obtiene el nombre del archivo (el que le damos) 
                        archivo = new File(nombreArchivo);//Inicializamos el archivo auxiliar
                        fileChooser.setSelectedFile(archivo);
                    }       
                    fileChooser.setFileFilter(filter);//Asignamos el filtro
                    //Una vez asignado el filtro, si el arhivo es XML (devuelve true) realizamos la operación, de lo contrario mostramos un Mensaje
                    if(filter.accept(fileChooser.getSelectedFile())){
                        File directorio = fileChooser.getCurrentDirectory();
                        //Comprobamos si ya existe ese archivo en el directorio y en caso de ello avisamos al usuario de si quiere sobreescribirlo                   
                        if(directorio.exists()){//Comprueba la existencia del directorio
                            File[] ficheros = directorio.listFiles();
                            int sobreescritura;
                            for (File fichero : ficheros) {
                                if (fichero.getName().equalsIgnoreCase(nombreArchivo)) { 
                                    //El html permite utilizar las etiquetas comunes para mostrar el texto de los componentes en distintos formatos (tamaños, bold, italic, underline, listas, etc.)
                                   sobreescritura =  JOptionPane.showConfirmDialog(null, "<html>El archivo <b>" + nombreArchivo + "</b> ya existe, ¿desea sobreescribirlo?</html>", "Advertencia",JOptionPane.OK_CANCEL_OPTION);
                                   if(JOptionPane.OK_OPTION == sobreescritura){
                                       deporte.guardarXML(ruta, filtroCaracteres(nombreArchivo));
                                       rutaXMLCalendar = ruta+ "/" + nombreArchivo;//guardamos la ruta en esta variable global para el CalendarioPDF
                                       fileProp.setProperty("rutaXML", ruta);//Asignamos la nueva ruta al archivo de propiedades
                                       guardarPropiedades();
                                       //Si el torneo no tiene nombre le damos el del archivo al grabarlo
                                       if(nombreArchivoGlobal.equalsIgnoreCase("Nuevo Torneo")){
                                           nombreArchivoGlobal = nombreArchivo;
                                           this.setTitle("Generador de Torneos" + " - " + nombreArchivoGlobal);
                                       }
                                       return;//salimos del metodo
                                   }else{
                                       //Cancelamos la acción de sobreescribir
                                       fileChooser.cancelSelection();
                                       return;//salimos del método (si no lo guarda abajo)
                                   }//end if
                                }//end if comprobacion nombre ficheros
                            }//end for loop
                        }else{
                            JOptionPane.showMessageDialog(null, "No se encontró el directorio seleccionado", "Error al buscar el directorio",JOptionPane.ERROR_MESSAGE);
                        }//end if
                        //Comprueba si el archivo existe y  lo elimina
                        if(archivo.exists()){
                            archivo.delete();
                        }
                        deporte.guardarXML(ruta, filtroCaracteres(nombreArchivo));
                        rutaXMLCalendar = ruta+ "/" + nombreArchivo;//guardamos la ruta en esta variable global para el CalendarioPDF
                        fileProp.setProperty("rutaXML", ruta);//Asignamos la nueva ruta al archivo de propiedades
                        guardarPropiedades();
                        //Si el torneo no tiene nombre le damos el del archivo al grabarlo
                        if(nombreArchivoGlobal.equalsIgnoreCase("Nuevo Torneo")){
                            nombreArchivoGlobal = nombreArchivo;
                            this.setTitle("Generador de Torneos" + " - " + nombreArchivoGlobal);
                        }
                    }else{
                        JOptionPane.showMessageDialog(null, "El archivo debe tener formato XML", "Advertencia",JOptionPane.WARNING_MESSAGE);
                    }
                }//end if aceptar JFileChooser
            }catch (HeadlessException hlex){
                JOptionPane.showMessageDialog(null, "El archivo no se ha guardado", "Advertencia",JOptionPane.WARNING_MESSAGE);
            }
            //deporte.guardarXML();
        }else{//if que comprueba si hay un torneo cargado
            JOptionPane.showMessageDialog(null, "No hay ni ningún torneo cargado todavía", "Advertencia",JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_guardarActionPerformed

    private void salirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salirActionPerformed
        int opcionSalir;//variable para guardar la opcion al salir 
        if(castEquipoLista.getEquipos().size() > 0){
            opcionSalir =  JOptionPane.showConfirmDialog(null, "Hay un torneo abierto, ¿Desea guardar el torneo antes de salir?", "Guardar antes de salir",JOptionPane.YES_NO_CANCEL_OPTION);
            if(JOptionPane.YES_OPTION == opcionSalir){
                guardar.doClick();//Simula que pulsamos en guardar
                 System.exit(0);
            }else if(JOptionPane.NO_OPTION == opcionSalir){
                System.exit(0);
            }//end if
        }else{
            System.exit(0);
        }//end if
    }//GEN-LAST:event_salirActionPerformed

    private void abrirTorneoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_abrirTorneoActionPerformed
        if(castEquipoLista.getEquipos().size() > 0){
           int confirmacion =  JOptionPane.showConfirmDialog(this, "Si abre un nuevo torneo se perderán los datos actuales no guardados, ¿Está seguro de que desea continuar?", "Mensaje de aviso", OK_CANCEL_OPTION, QUESTION_MESSAGE, null); 
            //Comprobamos si pulso OK o Cancelar
           if(JOptionPane.OK_OPTION == confirmacion){
            //Ahora tras borrar todos los datos anteriores abrimos el selector de archivos con JFileChooser
           abrirArchivo();
           }//end if JOptionPane
        }else{
            abrirArchivo();
        }//en if equipolista.size
    }//GEN-LAST:event_abrirTorneoActionPerformed

    private void TableJornadaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TableJornadaKeyReleased
        //Cuando se suelte el botón dejará de escribrir el valor copiado
        if( evt.getKeyCode() == KeyEvent.VK_ALT){
            checkClipboard = false;
        }
    }//GEN-LAST:event_TableJornadaKeyReleased

    private void TableJornadaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TableJornadaKeyPressed
        //Comprueba si se está pulsando la tecla en la tabla
        if( evt.getKeyCode() == KeyEvent.VK_ALT){//Elegimos la tecla Alt porque Mayúsculas y Ctrl ya tienen utilidad definida en la tabla
            //Si se pulsa el botón decimos que se ha pulsado
            checkClipboard = true;
        }
    }//GEN-LAST:event_TableJornadaKeyPressed

    private void TableJornadaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableJornadaMouseClicked
        clickarTabla(evt);
    }//GEN-LAST:event_TableJornadaMouseClicked

    private void BotonIzdaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonIzdaActionPerformed
         //Lo primero de todo guardamos los datos que tenga asignados la tabla
        deporte.guardarTabla();
        //Restamos 1 a la jornada actual
        jornada = jornada - 1;
        //Si la jornada es 0 saltamos a la última de todas
        if(jornada == 0){
            jornada = calendario.jornadas.size();
        }
        deporte.cargarTabla(jornada);
    }//GEN-LAST:event_BotonIzdaActionPerformed

    private void BotonDchaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonDchaActionPerformed
        //Lo primero de todo guardamos los datos que tenga asignados la tabla
        deporte.guardarTabla();
        //Sumamos 1 a la jornada actual
        jornada = jornada + 1;
        //Si la suma da un número mayor que el total de jornadas pasamos a la jornada 1
        if(jornada > calendario.jornadas.size()){
            jornada = 1;
        }       
        deporte.cargarTabla(jornada);
    }//GEN-LAST:event_BotonDchaActionPerformed

    private void BotonJornadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonJornadaActionPerformed
       deporte.mostrarJornada();
       jornada = deporte.getJornada();
    }//GEN-LAST:event_BotonJornadaActionPerformed

    private void TextFieldJornadaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextFieldJornadaKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
          deporte.mostrarJornada();
          jornada = deporte.getJornada();
        }
    }//GEN-LAST:event_TextFieldJornadaKeyPressed

    private void ButtonClasificacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonClasificacionActionPerformed
       //En primer lugar calculamos la clasificación
        deporte.calcularClasificacion();
        //Seguido la ordenamos para mostrarla correctamente
        deporte.ordenarClasificacion();
        //Por útlimo la cargamos en la tabla
        deporte.cargarClasificacion();
    }//GEN-LAST:event_ButtonClasificacionActionPerformed

    private void AcercaDeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AcercaDeActionPerformed
        //El segundo parámetro a "true" indica que el diálogo va a ser modal
            AcercaDe  acercaDeDialog = new AcercaDe(this, true);
            acercaDeDialog.setVisible(true);
    }//GEN-LAST:event_AcercaDeActionPerformed

    private void pestanasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pestanasMouseClicked
        /** Usando este método ya no hace falta pulsar el botón de Actualizar Clasificación (a veces falla)**/
        //En primer lugar calculamos la clasificación
        /*deporte.calcularClasificacion();
        //Seguido la ordenamos para mostrarla correctamente
        deporte.ordenarClasificacion();
        //Por útlimo la cargamos en la tabla
        deporte.cargarClasificacion();*/
    }//GEN-LAST:event_pestanasMouseClicked

    private void informeJornadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_informeJornadaActionPerformed
         generarInforme(0, false);                      
    }//GEN-LAST:event_informeJornadaActionPerformed

    private void informeResultadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_informeResultadosActionPerformed
        generarInforme(1, false);
    }//GEN-LAST:event_informeResultadosActionPerformed

    private void informeClasificacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_informeClasificacionActionPerformed
       generarInforme(2, false);
    }//GEN-LAST:event_informeClasificacionActionPerformed

    private void informeCalendarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_informeCalendarioActionPerformed
        if(castEquipoLista.getEquipos().size() > 0){
            int sobreescritura;
            sobreescritura =  JOptionPane.showConfirmDialog(null, "Para realizar esta acción es necesario guardar los datos ¿Desea guardar este torneo?", "Advertencia",JOptionPane.OK_CANCEL_OPTION);
            if(JOptionPane.OK_OPTION == sobreescritura){
                guardar.doClick();//guardamos el archivoXML
                deporte.getRutaXMLCalendar(rutaXMLCalendar);
                generarInforme(3, false);
            }
        }else{//if que comprueba si hay un torneo cargado
            JOptionPane.showMessageDialog(null, "No hay ni ningún torneo cargado todavía", "Advertencia",JOptionPane.WARNING_MESSAGE);
        }//end if
    }//GEN-LAST:event_informeCalendarioActionPerformed

    private void informeTodasJornadasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_informeTodasJornadasActionPerformed
        generarInforme(4, false);
    }//GEN-LAST:event_informeTodasJornadasActionPerformed

    private void informeTodosResultadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_informeTodosResultadosActionPerformed
        generarInforme(5, false);
    }//GEN-LAST:event_informeTodosResultadosActionPerformed

    private void tablaModActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tablaModActionPerformed
        if(castEquipoLista.getEquipos().size() > 0){
            //El segundo parámetro a "true" indica que el diálogo va a ser modal
            ModTablasDialog mtDialog = new ModTablasDialog(this, true, deporte.getTipoTorneo(), deporte.getNum(), deporte.getAlturaColumna(), deporte.getColumnas());
            mtDialog.setVisible(true);
        }else{//if que comprueba si hay un torneo cargado
            JOptionPane.showMessageDialog(null, "No hay ni ningún torneo cargado todavía", "Advertencia",JOptionPane.WARNING_MESSAGE);
        }               
    }//GEN-LAST:event_tablaModActionPerformed

    private void tablaModClasfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tablaModClasfActionPerformed
        if(castEquipoLista.getEquipos().size() > 0){
            if(deporte.getTipoTorneo() != 1){
                JOptionPane.showMessageDialog(null, "Este torneo no tiene tabla de clasificación", "Advertencia",JOptionPane.WARNING_MESSAGE);
            }else{
                //El segundo parámetro a "true" indica que el diálogo va a ser modal
                ModTablasClasfDialog mtDialog = new ModTablasClasfDialog(this, true, deporte.getTipoTorneo(), deporte.getNumc(), deporte.getAlturaClasfColumna(), deporte.getColumnasClasf());
                mtDialog.setVisible(true);
            }
        }else{//if que comprueba si hay un torneo cargado
            JOptionPane.showMessageDialog(null, "No hay ni ningún torneo cargado todavía", "Advertencia",JOptionPane.WARNING_MESSAGE);
        }   
    }//GEN-LAST:event_tablaModClasfActionPerformed

    private void informeJornadaAplazadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_informeJornadaAplazadosActionPerformed
        if(castEquipoLista.getEquipos().size() > 0){
            SeleccionAplazados seleccionAplazadosDialog = new SeleccionAplazados(this, true, calendario);
            seleccionAplazadosDialog.setVisible(true);
            //Sólo si se han aplazado partidos cargamos el método generarInforme()
            if(partidosAplazadosPr.size() > 0){
                generarInforme(0, true);//Si son aplazados el valor booleano será verdadero
            }
            //Una vez generado el informe vaciamos el arrayList para que no aparezca la pantalla de guardar archivo al cancelar la próxima vez
            partidosAplazadosPr.clear();
        }else{//if que comprueba si hay un torneo cargado
            JOptionPane.showMessageDialog(null, "No hay ni ningún torneo cargado todavía", "Advertencia",JOptionPane.WARNING_MESSAGE);
        } 
    }//GEN-LAST:event_informeJornadaAplazadosActionPerformed

    private void informeResultadosAplazadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_informeResultadosAplazadosActionPerformed
        if(castEquipoLista.getEquipos().size() > 0){
            SeleccionAplazados seleccionAplazadosDialog = new SeleccionAplazados(this, true, calendario);
            seleccionAplazadosDialog.setVisible(true);
            //Sólo si se han aplazado partidos cargamos el método generarInforme()
            if(partidosAplazadosPr.size() > 0){
                generarInforme(1, true);//Si son aplazados el valor booleano será verdadero
            }
            //Una vez generado el informe vaciamos el arrayList para que no aparezca la pantalla de guardar archivo al cancelar la próxima vez
            partidosAplazadosPr.clear();
        }else{//if que comprueba si hay un torneo cargado
            JOptionPane.showMessageDialog(null, "No hay ni ningún torneo cargado todavía", "Advertencia",JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_informeResultadosAplazadosActionPerformed

    private void aplicarSancionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aplicarSancionesActionPerformed
         if(castEquipoLista.getEquipos().size() > 0){
            if(deporte.getTipoTorneo() == 1){//Si es liga podremos sancionar, en eliminatorias no
                //Pasamos los equipos de castEquipoLista a un arraylist especial para la ocasión
                listaEquiposAux = deporte.getCastEquipoLista().getEquipos();//Recuperamos los equipos de métodos, para que la clase de equipo quede definida
                 for (int i = 0; i < castEquipoLista.getEquipos().size(); i++) {
                     listaEquiposSancion.add(listaEquiposAux.get(i).getNombre());
                 }           
                Sancionados sancionadosDialog = new Sancionados(this, true, listaEquiposSancion, getSancionadosPr());
                sancionadosDialog.setVisible(true);
                deporte.setSancionados(sancionadosPr);
            }else{
                JOptionPane.showMessageDialog(null, "Esta opción no se encuentra disponible para las eliminatorias", "Advertencia",JOptionPane.WARNING_MESSAGE);
            }//end if tipoTorneo
        }else{//if que comprueba si hay un torneo cargado
            JOptionPane.showMessageDialog(null, "No hay ni ningún torneo cargado todavía", "Advertencia",JOptionPane.WARNING_MESSAGE);
        } 
    }//GEN-LAST:event_aplicarSancionesActionPerformed

    private void AtribucionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AtribucionesActionPerformed
         //El segundo parámetro a "true" indica que el diálogo va a ser modal
            Atribuciones  acercaDeDialog = new Atribuciones(this, true);
            acercaDeDialog.setVisible(true);
    }//GEN-LAST:event_AtribucionesActionPerformed

    private void ManualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ManualActionPerformed
        try {
         File path = new File ("src/documentos/Manual.pdf");
         Desktop.getDesktop().open(path);
        }catch (IOException ex) {
            ex.getMessage();
        }
    }//GEN-LAST:event_ManualActionPerformed
  
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                //Añado estas tres líneas para cambiar los colores de la interfaz
                UIManager.put("nimbusBase", new Color(143,143,143));
                UIManager.put("nimbusBlueGrey", new Color(200,200,200));
                UIManager.put("control", new Color(200,255,255));
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
            //javax.swing.UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); //Obtendrá el look and feel predeterminado del sistema operativo que use el cliente
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                splash = new Splash();
                            java.awt.EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    splash.toFront();
                    splash.repaint();
                }
                });
                new Principal().setVisible(true);
                //splash.toFront();//traemos al frente a la ventana splash
                //Cargamos las imágenes de los deportes en el array al arrancar la aplicación (De lo contrario no funcionará)
                for(int i=0; i<totalDeportesIcon; i++){
                    //Ajustamos la imagen usando la clase Icon, obtiene la imagen de ImageIcon y la escala al tamaño del visor
                    //Estilo suave (SMOOTH)
                    deportesIcon[i] = new ImageIcon(getClass().getResource("/imagenes/deportes/"+i+".png"));
                    deportesFondo[i] = new ImageIcon(getClass().getResource("/imagenes/fondos/"+i+".png"));
                }
                //Cargamos el archivo properties
                lectorPropiedades();
                //Cargamos las rutas predeterminadas
                ruta = fileProp.getProperty("rutaXML", ruta);
                rutaPDF = fileProp.getProperty("rutaPDF");
            }
        });
    }
                        
    /**
     * Método que asigna una imagen a la etiqueta del deporte en función de la selección realizada en el JComboBox
     * @param opcion 
     */
    public void asignarDeporteIcono(int opcion){
            Icon icono = new ImageIcon(deportesIcon[opcion].getImage().getScaledInstance(LabelImagen.getWidth(),LabelImagen.getHeight(),Image.SCALE_SMOOTH));
            Icon fondo = new ImageIcon(deportesFondo[opcion].getImage()); 
            LabelImagen.setIcon(icono);
            LabelImagenFondo.setIcon(fondo);
    }
    
    /**
     * Método que asigna los datos del torneo generado a sus correspondientes componentes de la ventana Principal y además genera las tablas del calendario
     * @param torneo 
     */
    public void generarCalendario(Torneo torneo){
        //Añadimos el texto de Nuevo Torneo al title
        nombreArchivoGlobal = "Nuevo Torneo";
        this.setTitle("Generador de Torneos" + " - " + nombreArchivoGlobal);
        //Nos aseguramos de que se esté mostrando la tabla del calendario
        TableJornada.setVisible(true);
        //Cambiamos el nombre por el indicado en le JTextField nombre Torneo
        LabelNombreTorneo.setText(limitarCadena(torneo.getNombre(), 50, false));
        //Asignamos al icono el deporte indicado
        asignarDeporteIcono(torneo.getDeporte());
        //Le asignamos a la etiqueta Jornada su número correspondiente
        LabelJornada.setText("JORNADA " + jornada);
        //Llenamos el equipoLista de Principal con los equipos del ArrayList pasados
        equipoLista = torneo.getEquipoLista();
        //Obtenemos el número de jornadas que se van a disputar
        if(torneo.getTipoTorneo() == 1){
            int jornadas = torneo.getParticipantes() - 1; //La ida y vuelta se gestiona al crear las jornadas
            //Si hay equipos opuestos los pasamos a la clase generador
            if(torneo.getDerechaEO() != null){
                generador.setDerechaEOGen(torneo.getDerechaEO());
                generador.setIzquierdaEOGen(torneo.getIzquierdaEO());
            }
            //Creamos las jornadas
            generador.crearJornadas(jornadas, equipoLista, torneo.getIdaVuelta(), torneo.getDeporte(), torneo.getSets());
            //Una vez creadas las jornadas pedimos que nos devuelva el Calendario con las jornadas y sus respectivos partidos
            calendario = generador.obtenerCalendario();
            //pintarCalendario(calendario);
            jornada = 1;//Cargamos la primera de forma obligatoria (por si se han pulsado los botones anteriormente)
            //Añadimos los dato del tipo de deporte que utilizaremos
            seleccionarClaseEquipo(torneo.getNombre(), torneo.getTipoTorneo(),torneo.getDeporte(), calendario.jornadas.size(), torneo.getIdaVuelta(), torneo.getSets(), torneo.getSorteo(), torneo.getTercerCuartoPuesto());
            deporte.cargarTabla(jornada);
            //Al cargar un torneo hacemos visible el panel de botones
            PanelBotones.setVisible(true);
            ButtonClasificacion.setVisible(true);
            //Al cargar la liga le decimos que nos actualize la clasificación (para que aparezcan ya en tabla clasificatoria los equipos a 0)
            deporte.calcularClasificacion();
            deporte.ordenarClasificacion();
            deporte.cargarClasificacion();
        }else if(torneo.getTipoTorneo() == 2){
            int jornadas = calcularNumeroEliminatorias(equipoLista); //La ida y vuelta se gestiona al crear las jornadas
            //Creamos las jornadas
            generador.crearEliminatorias(jornadas, equipoLista, torneo.getIdaVuelta(), torneo.getDeporte(), torneo.getSorteo(), torneo.getTercerCuartoPuesto(), torneo.getSets());
            tercerCuartoPuesto = torneo.getTercerCuartoPuesto();
            idaVuelta = torneo.getIdaVuelta();
            //Una vez creadas las jornadas pedimos que nos devuelva el Calendario con las jornadas y sus respectivos partidos
            calendario = generador.obtenerCalendario();
            //pintarCalendario(calendario);
            jornada = 1;//Cargamos la primera de forma obligatoria (por si se han pulsado los botones anteriormente)

            //Añadimos los dato del tipo de deporte que utilizaremos
            seleccionarClaseEquipo(torneo.getNombre(), torneo.getTipoTorneo(),torneo.getDeporte(), calendario.jornadas.size(), torneo.getIdaVuelta(), torneo.getSets(), torneo.getSorteo(), torneo.getTercerCuartoPuesto());

            deporte.cargarTabla(jornada);
            //Al cargar un torneo hacemos visible el panel de botones
            PanelBotones.setVisible(true);
            LabelDescansa.setVisible(false);
            TableClasificacion.setVisible(false);
        }else{
            JOptionPane.showMessageDialog(this, "El tipo de torneo seleccionado no es válido", "Error al seleccionar el tipo de torneo", JOptionPane.ERROR_MESSAGE, null);
        }
    }
    
    /**
     * Selecciona el tipo de Equipo en función del deporte escogido
     * @param nombre
     * @param tipoDeporte
     * @param tipoTorneo 
     * @param num_jornadas
     * @param idaVuelta
     * @param sets 
     * @param sorteo
     * @param tercerCuartoPuesto
     */
    public void seleccionarClaseEquipo(String nombre, int tipoTorneo, int tipoDeporte, int num_jornadas, boolean idaVuelta,  int sets,  boolean sorteo, boolean tercerCuartoPuesto){
        //creamos la lista de Equipos de la clase correspondiente
        //castEquipoLista = new EquipoLista();
        
        if(tipoTorneo == 1){
        //Ahora utilizaremos un switch para asignar a cada deporte sus Equipos y sus Métodos correspondientes
        switch(tipoDeporte){
            case 0:
                //Creamos Equipos de Fubtol para futbol          
                for (int i = 0; i < equipoLista.size(); i++) {
                    miEquipo = new EquipoFutbol((i+1), equipoLista.get(i));
                    castEquipoLista.getEquipos().add(miEquipo);
                }
                //Añadimos los datos al tipo de deporte que utilizaremos
                deporte = new MetodosFutbol(modelo, clasificacion, jornada, TableJornada, TableClasificacion, LabelEquipoDescansa,
                    LabelJornada, LabelTotalJornadas, TextFieldJornada, calendario, castEquipoLista, nombre, tipoTorneo, tipoDeporte,
                num_jornadas, idaVuelta, sets, sorteo, tercerCuartoPuesto);
                break;
            case 1:
                //Creamos Equipos de Bakset para basket   
                for (int i = 0; i < equipoLista.size(); i++) {
                    miEquipo = new EquipoBasket((i+1), equipoLista.get(i));
                    castEquipoLista.getEquipos().add(miEquipo);
                 }
                //Añadimos los datos al tipo de deporte que utilizaremos
                deporte = new MetodosBasket(modelo, clasificacion, jornada, TableJornada, TableClasificacion, LabelEquipoDescansa,
                    LabelJornada, LabelTotalJornadas, TextFieldJornada, calendario, castEquipoLista, nombre, tipoTorneo, tipoDeporte,
                num_jornadas, idaVuelta, sets, sorteo, tercerCuartoPuesto);
                break;
            case 2:
                //Creamos Equipos de Tenis para Tenis  
                for (int i = 0; i < equipoLista.size(); i++) {
                    miEquipo = new EquipoTenis((i+1), equipoLista.get(i));
                    castEquipoLista.getEquipos().add(miEquipo);
                 }
                if(sets == 3){
                    //Añadimos los datos al tipo de deporte que utilizaremos
                    deporte = new MetodosTenis3Sets(modelo, clasificacion, jornada, TableJornada, TableClasificacion, LabelEquipoDescansa,
                        LabelJornada, LabelTotalJornadas, TextFieldJornada, calendario, castEquipoLista, nombre, tipoTorneo, tipoDeporte,
                num_jornadas, idaVuelta, sets, sorteo, tercerCuartoPuesto);
                }else if(sets == 5){
                    //Añadimos los datos al tipo de deporte que utilizaremos
                    deporte = new MetodosTenis5Sets(modelo, clasificacion, jornada, TableJornada, TableClasificacion, LabelEquipoDescansa,
                        LabelJornada, LabelTotalJornadas, TextFieldJornada, calendario, castEquipoLista, nombre, tipoTorneo, tipoDeporte,
                num_jornadas, idaVuelta, sets, sorteo, tercerCuartoPuesto);
                }else{
                    System.out.println("Error al cargar los métodos del Tenis");
                }
                break;
            case 3:
                //Creamos Equipos de Futbol para Futbol Sala   
                for (int i = 0; i < equipoLista.size(); i++) {
                    miEquipo = new EquipoFutbol((i+1), equipoLista.get(i));
                    castEquipoLista.getEquipos().add(miEquipo);
                 }
                //Añadimos los datos al tipo de deporte que utilizaremos
                deporte = new MetodosFutbol(modelo, clasificacion, jornada, TableJornada, TableClasificacion, LabelEquipoDescansa,
                    LabelJornada, LabelTotalJornadas, TextFieldJornada, calendario, castEquipoLista, nombre, tipoTorneo, tipoDeporte,
                num_jornadas, idaVuelta, sets, sorteo, tercerCuartoPuesto);
                break;
            case 4:
                //Creamos Equipos de Futbol para Balonmano  
                for (int i = 0; i < equipoLista.size(); i++) {
                    miEquipo = new EquipoFutbol((i+1), equipoLista.get(i));
                    castEquipoLista.getEquipos().add(miEquipo);
                 }
                //Añadimos los datos al tipo de deporte que utilizaremos
                deporte = new MetodosBalonmano(modelo, clasificacion, jornada, TableJornada, TableClasificacion, LabelEquipoDescansa,
                    LabelJornada, LabelTotalJornadas, TextFieldJornada, calendario, castEquipoLista, nombre, tipoTorneo, tipoDeporte,
                num_jornadas, idaVuelta, sets, sorteo, tercerCuartoPuesto);
                break;
            case 5:
                //Creamos Equipos de Futbol para Waterpolo   
                for (int i = 0; i < equipoLista.size(); i++) {
                    miEquipo = new EquipoFutbol((i+1), equipoLista.get(i));
                    castEquipoLista.getEquipos().add(miEquipo);
                 }
                //Para Waterpolo utilizamos los Métodos del Balonmano
                deporte = new MetodosBalonmano(modelo, clasificacion, jornada, TableJornada, TableClasificacion, LabelEquipoDescansa,
                    LabelJornada, LabelTotalJornadas, TextFieldJornada, calendario, castEquipoLista, nombre, tipoTorneo, tipoDeporte,
                num_jornadas, idaVuelta, sets, sorteo, tercerCuartoPuesto);
                break;
            case 6:
                //Creamos Equipos de Futbol para Hockey Patines   
                for (int i = 0; i < equipoLista.size(); i++) {
                    miEquipo = new EquipoFutbol((i+1), equipoLista.get(i));
                    castEquipoLista.getEquipos().add(miEquipo);
                 }
                //Para Hockey Patines utilizamos los Métodos del Futbol
                deporte = new MetodosFutbol(modelo, clasificacion, jornada, TableJornada, TableClasificacion, LabelEquipoDescansa,
                    LabelJornada, LabelTotalJornadas, TextFieldJornada, calendario, castEquipoLista, nombre, tipoTorneo, tipoDeporte,
                num_jornadas, idaVuelta, sets, sorteo, tercerCuartoPuesto);
                break;
            case 7:
                //Creamos Equipos de Futbol para Hockey Hierba  
                for (int i = 0; i < equipoLista.size(); i++) {
                    miEquipo = new EquipoFutbol((i+1), equipoLista.get(i));
                    castEquipoLista.getEquipos().add(miEquipo);
                 }
                //Para Hockey Hierba utilizamos los Métodos del Balonmano
                deporte = new MetodosBalonmano(modelo, clasificacion, jornada, TableJornada, TableClasificacion, LabelEquipoDescansa,
                    LabelJornada, LabelTotalJornadas, TextFieldJornada, calendario, castEquipoLista, nombre, tipoTorneo, tipoDeporte,
                num_jornadas, idaVuelta, sets, sorteo, tercerCuartoPuesto);
                break;
            case 8:
                //Creamos Equipos de HockeyHielo para HockeyHielo   
                for (int i = 0; i < equipoLista.size(); i++) {
                    miEquipo = new EquipoHockeyHielo((i+1), equipoLista.get(i));
                    castEquipoLista.getEquipos().add(miEquipo);
                 }
                //Para Hockey Hielo utilizamos los Métodos del Hockey Hielo
                deporte = new MetodosHockeyHielo(modelo, clasificacion, jornada, TableJornada, TableClasificacion, LabelEquipoDescansa,
                    LabelJornada, LabelTotalJornadas, TextFieldJornada, calendario, castEquipoLista, nombre, tipoTorneo, tipoDeporte,
                num_jornadas, idaVuelta, sets, sorteo, tercerCuartoPuesto);
                break;
            case 9:
                //Creamos Equipos de Rubgy para Rugby  
                for (int i = 0; i < equipoLista.size(); i++) {
                    miEquipo = new EquipoRugby((i+1), equipoLista.get(i));
                    castEquipoLista.getEquipos().add(miEquipo);
                 }
                //Para Rugby utilizamos los Métodos del Rugby
                deporte = new MetodosRugby(modelo, clasificacion, jornada, TableJornada, TableClasificacion, LabelEquipoDescansa,
                    LabelJornada, LabelTotalJornadas, TextFieldJornada, calendario, castEquipoLista, nombre, tipoTorneo, tipoDeporte,
                num_jornadas, idaVuelta, sets, sorteo, tercerCuartoPuesto);
                break;
            case 10:
                //Creamos Equipos de Tenis para Badminton 
                for (int i = 0; i < equipoLista.size(); i++) {
                    miEquipo = new EquipoTenis((i+1), equipoLista.get(i));
                    castEquipoLista.getEquipos().add(miEquipo);
                 }
                //Para Badminton utilizamos los Métodos del Bádminton
                deporte = new MetodosBadminton(modelo, clasificacion, jornada, TableJornada, TableClasificacion, LabelEquipoDescansa,
                    LabelJornada, LabelTotalJornadas, TextFieldJornada, calendario, castEquipoLista, nombre, tipoTorneo, tipoDeporte,
                num_jornadas, idaVuelta, sets, sorteo, tercerCuartoPuesto);
                break;
            case 11:
                //Creamos Equipos de Tenis para Frontenis 
                for (int i = 0; i < equipoLista.size(); i++) {
                    miEquipo = new EquipoTenis((i+1), equipoLista.get(i));
                    castEquipoLista.getEquipos().add(miEquipo);
                 }
                //Para Frontenis utilizamos los Métodos del Tenis a 3 sets
                deporte = new MetodosTenis3Sets(modelo, clasificacion, jornada, TableJornada, TableClasificacion, LabelEquipoDescansa,
                    LabelJornada, LabelTotalJornadas, TextFieldJornada, calendario, castEquipoLista, nombre, tipoTorneo, tipoDeporte,
                num_jornadas, idaVuelta, sets, sorteo, tercerCuartoPuesto);
                break;
            case 12:
                //Creamos Equipos de Tenis para Tenis de Mesa 
                for (int i = 0; i < equipoLista.size(); i++) {
                    miEquipo = new EquipoTenis((i+1), equipoLista.get(i));
                    castEquipoLista.getEquipos().add(miEquipo);
                 }
                //Para Tenis de Mesa  utilizamos los Métodos del Tenis de Mesa
                deporte = new MetodosTenisDeMesa(modelo, clasificacion, jornada, TableJornada, TableClasificacion, LabelEquipoDescansa,
                    LabelJornada, LabelTotalJornadas, TextFieldJornada, calendario, castEquipoLista, nombre, tipoTorneo, tipoDeporte,
                num_jornadas, idaVuelta, sets, sorteo, tercerCuartoPuesto);
                break;
            case 13:
                //Creamos Equipos de Tenis para Pádel
                for (int i = 0; i < equipoLista.size(); i++) {
                    miEquipo = new EquipoTenis((i+1), equipoLista.get(i));
                    castEquipoLista.getEquipos().add(miEquipo);
                 }
                //Para Pádel utilizamos los Métodos del Tenis a 3 sets (Usa las mismas normas que el tenis según worldpadeltour.com/paginas/REGLAMENTO_FIP.pdf)
                deporte = new MetodosTenis3Sets(modelo, clasificacion, jornada, TableJornada, TableClasificacion, LabelEquipoDescansa,
                    LabelJornada, LabelTotalJornadas, TextFieldJornada, calendario, castEquipoLista, nombre, tipoTorneo, tipoDeporte,
                num_jornadas, idaVuelta, sets, sorteo, tercerCuartoPuesto);
                break;
            case 14:
                //Creamos Equipos de Fútbol para Fútbol Americano
                for (int i = 0; i < equipoLista.size(); i++) {
                    miEquipo = new EquipoFutbol((i+1), equipoLista.get(i));
                    castEquipoLista.getEquipos().add(miEquipo);
                 }
                //Para Fútbol Americano utilizamos los Métodos del Fútbol Americano
                deporte = new MetodosFutbolAmericano(modelo, clasificacion, jornada, TableJornada, TableClasificacion, LabelEquipoDescansa,
                    LabelJornada, LabelTotalJornadas, TextFieldJornada, calendario, castEquipoLista, nombre, tipoTorneo, tipoDeporte,
                num_jornadas, idaVuelta, sets, sorteo, tercerCuartoPuesto);
                break;
            case 15:
                //Creamos Equipos de Voleibol para Voleibol
                for (int i = 0; i < equipoLista.size(); i++) {
                    miEquipo = new EquipoVoleibol((i+1), equipoLista.get(i));
                    castEquipoLista.getEquipos().add(miEquipo);
                 }
                //Para Voleibol utilizamos los Métodos de Voleibol
                deporte = new MetodosVoleibol(modelo, clasificacion, jornada, TableJornada, TableClasificacion, LabelEquipoDescansa,
                    LabelJornada, LabelTotalJornadas, TextFieldJornada, calendario, castEquipoLista, nombre, tipoTorneo, tipoDeporte,
                num_jornadas, idaVuelta, sets, sorteo, tercerCuartoPuesto);
                break;
            case 16:
                //Creamos Equipos de Tenis para Voley Playa
                for (int i = 0; i < equipoLista.size(); i++) {
                    miEquipo = new EquipoTenis((i+1), equipoLista.get(i));
                    castEquipoLista.getEquipos().add(miEquipo);
                 }
                //Para Voley Playa utilizamos los Métodos de Tenis a 3 sets
                deporte = new MetodosTenis3Sets(modelo, clasificacion, jornada, TableJornada, TableClasificacion, LabelEquipoDescansa,
                    LabelJornada, LabelTotalJornadas, TextFieldJornada, calendario, castEquipoLista, nombre, tipoTorneo, tipoDeporte,
                num_jornadas, idaVuelta, sets, sorteo, tercerCuartoPuesto);
                break;
            case 17:
                //Creamos Equipos de Ajedrez para el Ajedrez
                for (int i = 0; i < equipoLista.size(); i++) {
                    miEquipo = new EquipoAjedrez((i+1), equipoLista.get(i));
                    castEquipoLista.getEquipos().add(miEquipo);
                 }
                //Para Ajedrez utilizamos los Métodos del Ajedrez
                deporte = new MetodosAjedrez(modelo, clasificacion, jornada, TableJornada, TableClasificacion, LabelEquipoDescansa,
                    LabelJornada, LabelTotalJornadas, TextFieldJornada, calendario, castEquipoLista, nombre, tipoTorneo, tipoDeporte,
                num_jornadas, idaVuelta, sets, sorteo, tercerCuartoPuesto);
                break;
            case 18:
                //Creamos Equipos de Basket para el Beisbol
                for (int i = 0; i < equipoLista.size(); i++) {
                    miEquipo = new EquipoBasket((i+1), equipoLista.get(i));
                    castEquipoLista.getEquipos().add(miEquipo);
                 }
                //Para Beisbol utilizamos los Métodos del Beisbol (El de Basket con pequeñas modificaciones)
                deporte = new MetodosBeisbol(modelo, clasificacion, jornada, TableJornada, TableClasificacion, LabelEquipoDescansa,
                    LabelJornada, LabelTotalJornadas, TextFieldJornada, calendario, castEquipoLista, nombre, tipoTorneo, tipoDeporte,
                num_jornadas, idaVuelta, sets, sorteo, tercerCuartoPuesto);
                break;
            case 19:
                //Creamos Equipos de Basket para el Curling
                for (int i = 0; i < equipoLista.size(); i++) {
                    miEquipo = new EquipoBasket((i+1), equipoLista.get(i));
                    castEquipoLista.getEquipos().add(miEquipo);
                 }
                //Para Curling utilizamos los Métodos del Beisbol (El de Basket con pequeñas modificaciones)
                deporte = new MetodosBeisbol(modelo, clasificacion, jornada, TableJornada, TableClasificacion, LabelEquipoDescansa,
                    LabelJornada, LabelTotalJornadas, TextFieldJornada, calendario, castEquipoLista, nombre, tipoTorneo, tipoDeporte,
                num_jornadas, idaVuelta, sets, sorteo, tercerCuartoPuesto);
                break;
            default:
                JOptionPane.showMessageDialog(this, "No se ha seleccionado un deporte válido", "Error al seleccionar un deporte", JOptionPane.ERROR_MESSAGE, null);
        }//End of switch
        //Si el torneo es liga finalmente añadimos también los sancionados
        deporte.setSancionados(sancionadosPr);
        }else if(tipoTorneo == 2){
            /****************** SWITTCH PARA LAS ELIMINATORIAS ********************/
        //Ahora utilizaremos un switch para asignar a cada deporte sus Equipos y sus Métodos correspondientes
        switch(tipoDeporte){
            case 0:
            case 1:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 14:
                //Creamos Equipos de Fubtol para fútbol, basket, fútbol sala, balonmano, waterpolo, hockey patines, hockey hierba, fútbol americano         
                for (int i = 0; i < equipoLista.size(); i++) {
                    miEquipo = new EquipoFutbol((i+1), equipoLista.get(i));
                    castEquipoLista.getEquipos().add(miEquipo);
                }
                //Añadimos los datos al tipo de deporte que utilizaremos
                deporte = new MetodosEliminatoriaFutbol(modelo, jornada, TableJornada, LabelJornada, LabelTotalJornadas, TextFieldJornada, calendario, castEquipoLista, nombre, tipoTorneo, tipoDeporte,
                            num_jornadas, idaVuelta, sets, sorteo, tercerCuartoPuesto);
                break;
            case 2:
                //Creamos Equipos de Tenis para Tenis  
                for (int i = 0; i < equipoLista.size(); i++) {
                    miEquipo = new EquipoTenis((i+1), equipoLista.get(i));
                    castEquipoLista.getEquipos().add(miEquipo);
                 }
                if(sets == 3){
                    //Añadimos los datos al tipo de deporte que utilizaremos
                    deporte = new MetodosEliminatoriaTenis3Sets(modelo, jornada, TableJornada, LabelJornada, LabelTotalJornadas, TextFieldJornada, calendario, castEquipoLista, nombre, tipoTorneo, tipoDeporte,
                            num_jornadas, idaVuelta, sets, sorteo, tercerCuartoPuesto);
                }else if(sets == 5){
                    //Añadimos los datos al tipo de deporte que utilizaremos
                    deporte = new MetodosEliminatoriaTenis5Sets(modelo, jornada, TableJornada, LabelJornada, LabelTotalJornadas, TextFieldJornada, calendario, castEquipoLista, nombre, tipoTorneo, tipoDeporte,
                            num_jornadas, idaVuelta, sets, sorteo, tercerCuartoPuesto);
                }else{
                    System.out.println("Error al cargar los métodos del Tenis");
                }
                break;
            case 10:
            case 11:
            case 13:
            case 16:
                //Creamos Equipos de Tenis para Tenis  
                for (int i = 0; i < equipoLista.size(); i++) {
                    miEquipo = new EquipoTenis((i+1), equipoLista.get(i));
                    castEquipoLista.getEquipos().add(miEquipo);
                 }
                    //Añadimos los datos al tipo de deporte que utilizaremos
                    deporte = new MetodosEliminatoriaTenis3Sets(modelo, jornada, TableJornada, LabelJornada, LabelTotalJornadas, TextFieldJornada, calendario, castEquipoLista, nombre, tipoTorneo, tipoDeporte,
                            num_jornadas, idaVuelta, sets, sorteo, tercerCuartoPuesto);
                break;
            case 12:
                //Creamos Equipos de Tenis para Tenis  
                for (int i = 0; i < equipoLista.size(); i++) {
                    miEquipo = new EquipoTenis((i+1), equipoLista.get(i));
                    castEquipoLista.getEquipos().add(miEquipo);
                 }
                    //Añadimos los datos al tipo de deporte que utilizaremos
                    deporte = new MetodosEliminatoriaTenis5Sets(modelo, jornada, TableJornada, LabelJornada, LabelTotalJornadas, TextFieldJornada, calendario, castEquipoLista, nombre, tipoTorneo, tipoDeporte,
                            num_jornadas, idaVuelta, sets, sorteo, tercerCuartoPuesto);
                break;
            case 15:
                //Creamos Equipos de Voleibol para Voleibol  
                for (int i = 0; i < equipoLista.size(); i++) {
                    miEquipo = new EquipoVoleibol((i+1), equipoLista.get(i));
                    castEquipoLista.getEquipos().add(miEquipo);
                 }
                    //Añadimos los datos al tipo de deporte que utilizaremos
                    deporte = new MetodosEliminatoriaVoleibol(modelo, jornada, TableJornada, LabelJornada, LabelTotalJornadas, TextFieldJornada, calendario, castEquipoLista, nombre, tipoTorneo, tipoDeporte,
                            num_jornadas, idaVuelta, sets, sorteo, tercerCuartoPuesto);
                break;
            case 17:
                //Creamos Equipos de Ajedrez para Ajedrez
                for (int i = 0; i < equipoLista.size(); i++) {
                    miEquipo = new EquipoAjedrez((i+1), equipoLista.get(i));
                    castEquipoLista.getEquipos().add(miEquipo);
                 }
                //Para Ajedrez utilizamos sus métodos de Ajedrez
                deporte = new MetodosEliminatoriaAjedrez(modelo, jornada, TableJornada, LabelJornada, LabelTotalJornadas, TextFieldJornada, calendario, castEquipoLista, nombre, tipoTorneo, tipoDeporte,
                            num_jornadas, idaVuelta, sets, sorteo, tercerCuartoPuesto);
                break;
            case 18:
            case 19:
                //Creamos Equipos de Basket para el Beisbol
                for (int i = 0; i < equipoLista.size(); i++) {
                    miEquipo = new EquipoBasket((i+1), equipoLista.get(i));
                    castEquipoLista.getEquipos().add(miEquipo);
                 }
                //Añadimos los datos al tipo de deporte que utilizaremos
                    deporte = new MetodosEliminatoriaBeisbol(modelo, jornada, TableJornada, LabelJornada, LabelTotalJornadas, TextFieldJornada, calendario, castEquipoLista, nombre, tipoTorneo, tipoDeporte,
                            num_jornadas, idaVuelta, sets, sorteo, tercerCuartoPuesto);
                break;
            default:
                JOptionPane.showMessageDialog(this, "No se ha seleccionado un deporte válido", "Error al seleccionar un deporte", JOptionPane.ERROR_MESSAGE, null);
        }//End of switch
        }

    }//End of seleccionarClaseEquipo()
    
    /**
    * Método que recoge la celda pulsada de la Tabla de jornadas, y además permite rellenar de forma rápida la tabla
    * @param evt 
    */
    public void clickarTabla(java.awt.event.MouseEvent evt){
        int fila = TableJornada.rowAtPoint(evt.getPoint());
        int columna = TableJornada.columnAtPoint(evt.getPoint());
        if ((fila > -1) && (columna > -1)){
            //Si el campo de la tabla está vacío o es nulo pintamos lo que haya en el portapapeles, si no, obtenemos el valor y lo pasamos al portapapeles
            if(deporte.getModelo().getValueAt(fila, columna).toString().equalsIgnoreCase("") || deporte.getModelo().getValueAt(fila, columna) == null){
                if(checkClipboard){
                    deporte.getModelo().setValueAt(clipboard, fila, columna);
                    checkClipboard = false;//Reseteamos el control para tener que volver a pulsarlo para que funcione
                }
            }else{
                if(checkClipboard){
                    clipboard = deporte.getModelo().getValueAt(fila,columna).toString();//Object convertido a cadena
                    checkClipboard = false;//Reseteamos el control para tener que volver a pulsarlo para que funcione
                }
            }
        }
    }//End of clickarTabla()
    
    /**
     * Método que al iniciar la aplicación reasigna la posición de la etiqueta de Imagen de Fondo para que quede mejor centrada
     */
    private void recolocarLabelImagenFondo(){
        //Obtiene el tamaño de la pantalla
        dim = Toolkit.getDefaultToolkit().getScreenSize();
        //obtiene la resolucion de la pantalla en PPP (Puntos por pulgada)
        //int sr = Toolkit.getDefaultToolkit().getScreenResolution();
        //muestra la informacion por la consola de java
        //System.out.println("Tamaño de pantalla: " + d.width + "x" + d.height + ", definición: " + sr + " ppp");
        if(dim.width >= 1600){
            //Si el ancho de la resolución de pantalla es 1600 o superior cambiamos la posición de la etiqueta
            PanelTitulo.remove(LabelImagenFondo);//Borramos el componente y lo volvemos a crear en la nueva posición
            PanelTitulo.add(LabelImagenFondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 11, 4530, 162));
        }
    }//End recolocarLabelImagenFondo
    
    /****************** MÉTODOS PARA LAS ELIMINATORIAS ********************/
    
    /**
     * Calcula el número de jornadas que se utilizarán en la eliminatoria
     * @param equipos
     * @return 
     */
    public int calcularNumeroEliminatorias(ArrayList<String> equipos){
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
        return jornadas;
    }
    
    /****************** MÉTODOS XML ********************/
    
      /**
     * Método que realiza las operaciones necesarias para cargar el archivo a abrir
     */
    private void abrirArchivo(){
     File archivoAbierto;
            JFileChooser fileChooser;
            FileNameExtensionFilter filter = new FileNameExtensionFilter("XML", "xml");
            //Establecemos la ruta ya guardada en propiedades como predeterminada
            if(ruta.equalsIgnoreCase("")){
                fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
            }else{
                fileChooser = new JFileChooser(ruta);
            }
            fileChooser.setFileFilter(filter);//Asignamos el filtro
            int respuesta = fileChooser.showOpenDialog(this);
            //Si le damos a ok seleccionamos el archivo
            if(respuesta == JFileChooser.APPROVE_OPTION){
                ruta = valueOf(fileChooser.getCurrentDirectory());//obtenemos la ruta del archivo (el directorio)
                archivoAbierto = fileChooser.getSelectedFile();
                //Comprobamos is el archivo existe
                if(archivoAbierto.exists()){
                    //Comprobamos si el archivo pasa el filtro XML
                    if(filter.accept(fileChooser.getSelectedFile())){
                        mensajeSchema = false;//Lo primero permitimos que se vuelvan a mostrar mensajes de error de XML mal formado
                        //****** Validamos el arhcivo XML contra el esquema XSD (Creando la factoria e indicando que hay validacion)  *******/
                        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
                        factory.setNamespaceAware(true);  
                        factory.setValidating(true); 

                        try {
                            //Configurando el Schema de validacion
                            factory.setAttribute(JAXP_SCHEMA_LANGUAGE, W3C_XML_SCHEMA);
                            factory.setAttribute(JAXP_SCHEMA_SOURCE, new File(MY_SCHEMA));
                            // Parseando el documento
                            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
                            documentBuilder.setErrorHandler(new ParserErrorHandler(mensajeSchema));
                            //hay que añadir org.w3c.dom. a Document para diferenciarlo del paquete de JDOM
                            org.w3c.dom.Document parse = documentBuilder.parse(archivoAbierto);
                            //Añadimos el nombre del archivo al title del programa
                            nombreArchivoGlobal = archivoAbierto.getName();
                            this.setTitle("Generador de Torneos" + " - " + nombreArchivoGlobal);
                        }catch(SAXParseException saxpe){
                            if(!this.mensajeSchema){//if esclusivamente para mostrar o no el mensaje
                                JOptionPane.showMessageDialog(null, "El formato XML del archivo no es válido", "Error al abrir el archivo",JOptionPane.ERROR_MESSAGE);
                                this.mensajeSchema = true;
                            }//end if
                            System.out.println(saxpe.getMessage());
                            return;
                        } catch (SAXException saxEx){
                            JOptionPane.showMessageDialog(null, "El formato XML del archivo no es válido", "Error al abrir el archivo",JOptionPane.ERROR_MESSAGE);
                            System.out.println(saxEx.getMessage());
                            return;
                        } catch (ParserConfigurationException parserEx){
                            JOptionPane.showMessageDialog(null, "El formato XML del archivo no es válido", "Error al abrir el archivo",JOptionPane.ERROR_MESSAGE);
                            System.out.println(parserEx.getMessage());
                            return;
                        } catch (IllegalArgumentException | IOException ex) { 
                            JOptionPane.showMessageDialog(null, "El formato XML del archivo no es válido", "Error al abrir el archivo",JOptionPane.ERROR_MESSAGE);
                            System.out.println(ex.getMessage());
                            return; 
                        }                 
                        //Si se confirma borramos los equipos y las jornadas para poder insertar unos nuevos
                        sancionadosPr.clear();
                        listaEquiposSancion.clear();
                        equipoLista.clear();
                        calendario.jornadas.clear();
                        LabelTotalJornadas.setText("");
                        LabelEquipoDescansa.setText("");
                        castEquipoLista.getEquipos().clear();//Vacía la tabla de clasificación al vaciar los equipos que ya estaban guardados
                        TableJornada.setVisible(true);
                        TableClasificacion.setVisible(true);//Reinstauramos la posibilidad de ver la tabla por si el usuario carga una Liga tras una Eliminatoria
                        idaVuelta = false;//Lo ponemos a false
                        tercerCuartoPuesto = false;//Lo ponemos a false
                        jornada = 1;//Ponemos la jornada en la primera al cargar un torneo nuevo
                        cargarXML(archivoAbierto);
                    }else{
                        JOptionPane.showMessageDialog(null, "El archivo debe tener formato XML", "Error al abrir el archivo",JOptionPane.ERROR_MESSAGE);
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "No se encontró el archivo seleccionado", "Error al buscar el archivo",JOptionPane.ERROR_MESSAGE);
                }
                
            }//end if Approve_option
    }//End of abrirArchivo()
    
    /**
     * Método que maneja la lectura de datos XML al abrir un archivo
     * @param archivoAbierto 
     */
    public void cargarXML(File archivoAbierto){
        //Se crea un SAXBuilder para poder parsear el archivo
        SAXBuilder builder = new SAXBuilder();
        try {
            //Se crea el documento a traves del archivo
            Document document = (Document) builder.build(archivoAbierto);

            //Se obtiene la raiz del documento (torneo)
            Element xml_torneo = document.getRootElement();
            //Obtenemos los elementos principales del torneo
            Element xml_nombreTorneo = xml_torneo.getChild("nombreTorneo");
            String nombreTorneoXML = xml_nombreTorneo.getTextTrim();

            Element xml_tipoTorneo = xml_torneo.getChild("tipoTorneo");
            int tipoTorneoXML = Integer.parseInt(xml_tipoTorneo.getText());

            Element xml_deporte = xml_torneo.getChild("deporte");
            int deporteXML = Integer.parseInt(xml_deporte.getText());/* **** Tal vez haya que sobreescirbir los atributos comunes *** */

            Element xml_num_jornadas = xml_torneo.getChild("jornadas");
            int num_jornadasXML = Integer.parseInt(xml_num_jornadas.getText());

            Element xml_idaVuelta = xml_torneo.getChild("idaVuelta");
            boolean idaVueltaXML = Boolean.parseBoolean(xml_idaVuelta.getText());

            Element xml_sets = xml_torneo.getChild("sets");
            int setsXML = Integer.parseInt(xml_sets.getText());

            Element xml_sorteo = xml_torneo.getChild("sorteo");
            boolean sorteoXML = Boolean.parseBoolean(xml_sorteo.getText());

            Element xml_tercerCuartoPuesto = xml_torneo.getChild("tercerCuartoPuesto");
            boolean tercerCuartoPuestoXML = Boolean.parseBoolean(xml_tercerCuartoPuesto.getText());
            
            Element xml_sanciones = xml_torneo.getChild("sanciones");
            
            //Si comprobamos que hay sancionados recorremos el HashMap y los añadimos al HashMap de Principal y de los Metodos correspondientes
            if(xml_sanciones.getContentSize() > 0){
                //obtenemos los equipos sancionados y su correspondiente sanción
                List listaSancionados = xml_sanciones.getChildren("sancionado");//Lista de equipos sancionados
                for (int i = 0; i < xml_sanciones.getChildren().size(); i++) {
                    Element xml_sancionado = (Element)listaSancionados.get(i);
                    Element xml_nombreSancionado = xml_sancionado.getChild("nombreSancionado");
                    String sancionadoXML = xml_nombreSancionado.getTextTrim();
                    Element xml_sancion = xml_sancionado.getChild("sancion");
                    int sancionXML = Integer.parseInt(xml_sancion.getText());
                    sancionadosPr.put(sancionadoXML, sancionXML);
                }//end for                  
                    //deporte.setSancionados(sancionadosPr);
            }//end if

            //Ahora obtendremos el calendario del torneo
            Element xml_calendario = xml_torneo.getChild("calendario");
            //creamos una lista de jornadas (para que pueda avanzar por ellas)
            List listaJornadas = xml_calendario.getChildren("jornada");
            //recorremos las distintas jornadas del calendario
            for (int i = 0; i < num_jornadasXML; i++){
                //Obtenemos cada jornada
                Element xml_jornada = (Element)listaJornadas.get(i);//ahora podrá recorrer la lista de jor
                int xml_jornada_num = Integer.parseInt(xml_jornada.getAttributeValue("numero"));
                //Añadimos los datos a la jornada
                Jornada jor = new Jornada(i+1);//+1 porque el indice empieza en 0
                int num_partidos = xml_jornada.getChildren().size();//obtenemos el número de partidos que tiene cada jornada
                //Creamos ahora la lista que almacenará los partidos de cada jornada
                List listaPartidos = xml_jornada.getChildren("partido");
                
                //recorremos los partidos de cada jornada mientras queden jornadas               
                for (int j = 0; j < num_partidos; j++) {                   
                    Element xml_partido = (Element)listaPartidos.get(j);//se castean los Objetos como Elementos
                    //Añadimos los datos obtenidos del archivo XML al partido y este a la jornada actual con el método seleccionarClasePartidoXML
                    if(tipoTorneoXML == 1){
                        seleccionarClasePartidoXML(xml_partido, deporteXML, setsXML, jor);
                    }else if(tipoTorneoXML == 2){//Eliminatorias
                        seleccionarClasePartidoEliminatoriaXML(xml_partido, deporteXML, setsXML, jor);
                    }else{
                        JOptionPane.showMessageDialog(this, "El tipo de torneo pasado no existe", "Error al cargar un torneo", JOptionPane.ERROR_MESSAGE, null);
                    }
                }//end for partidos
                //Añadimos todas las jorandas al calendario
                jor.setNum_jornada(xml_jornada_num);//añade el número de jornada
                calendario.añadirJornada(jor);
            }//end for jornadas
            
            //Añadimos la clasificacion (las eliminatorias no mostraran nada)
            //Obtenemos ahora los datos de la clasificacion
            Element xml_clasificacion = xml_torneo.getChild("clasificacion");
            int num_equipos = xml_clasificacion.getChildren().size();
            //Creamos una lista que almacene todos los equipos
            List listaEquipos = xml_clasificacion.getChildren("equipo");
            //recorremos los distintos equipos
            for (int i = 0; i < num_equipos; i++) {
                Element xml_equipo = (Element)listaEquipos.get(i);
                //Obtenemos los datos del equipo y los añadimos a la Lista de equipos
                seleccionarClaseEquipoXML(xml_equipo, deporteXML);
            }
                
            //Finalmente Seleccionamos la clase de Métodos correspondiente al equipo con el método que ya habíamos creado (calendario y castEquipoLista son variables goblales)
            seleccionarClaseEquipo(nombreTorneoXML, tipoTorneoXML, deporteXML, num_jornadasXML, idaVueltaXML, setsXML, sorteoXML, tercerCuartoPuestoXML);
            //Una vez seleccionados los metodos correspondientes al deporte cargamos todas las tablas y paneles necesarios ***
            if(tipoTorneoXML == 1){//liga
                //Calculamos las propiedades de tamaño y longitud de las tablas (accedemos al archivo propiedades)
                deporte.cargarTabla(jornada);
                //Al cargar un torneo hacemos visible el panel de botones
                PanelBotones.setVisible(true);
                ButtonClasificacion.setVisible(true);
                //Al cargar la liga le decimos que nos actualize la clasificación (para que aparezcan ya en tabla clasificatoria los equipos a 0)
                deporte.calcularClasificacion();
                deporte.ordenarClasificacion();
                deporte.cargarClasificacion();
            }else if(tipoTorneoXML == 2){//eliminatoria
                deporte.cargarTabla(jornada);
                //Al cargar un torneo hacemos visible el panel de botones
                PanelBotones.setVisible(true);
                LabelDescansa.setVisible(false);
                TableClasificacion.setVisible(false);
            }else{
                 JOptionPane.showMessageDialog(this, "No se ha podido cargar el torneo correctamente", "Error al cargar un torneo", JOptionPane.ERROR_MESSAGE, null);
            }//end if tipoTorneo
            //Asignamos los datos correspodientes a cada torneo
            LabelNombreTorneo.setText(nombreTorneoXML);
            asignarDeporteIcono(deporteXML);
        }catch (NumberFormatException | IOException | JDOMException exc ) {
            //Este fallo se puede dar si el archivo XML es manipulado para solventar el Schema XSD faltando datos opcionales en otros deportes que en el deporte cargado sean necesarios (Se perderán los datos)
            JOptionPane.showMessageDialog(this, "No se ha podido cargar el torneo correctamente", "Error al cargar un torneo", JOptionPane.ERROR_MESSAGE, null);
            System.out.println( exc.getMessage() );
        }
    }//End of cargarXML
    
    /**
     * Método que selecciona el tipo de partido necesario para cargar el XML correspondiente y lo añade a la jornada
     * @param xml_partido
     * @param deporteXML
     * @param setsXML
     * @param jor
     */
    public void seleccionarClasePartidoXML(Element xml_partido, int deporteXML, int setsXML, Jornada jor){
        
        switch(deporteXML){
            
            case 2:
                if(setsXML == 3){
                    String fechaTenis = xml_partido.getChildTextTrim("fecha");
                    String horaTenis = xml_partido.getChildTextTrim("hora");
                    String localTenis = xml_partido.getChildTextTrim("local");
                    int set1LTenis = Integer.parseInt(xml_partido.getChildTextTrim("set1L"));
                    int set1VTenis = Integer.parseInt(xml_partido.getChildTextTrim("set1V"));
                    int set2LTenis = Integer.parseInt(xml_partido.getChildTextTrim("set2L"));
                    int set2VTenis = Integer.parseInt(xml_partido.getChildTextTrim("set2V"));
                    int set3LTenis = Integer.parseInt(xml_partido.getChildTextTrim("set3L"));
                    int set3VTenis = Integer.parseInt(xml_partido.getChildTextTrim("set3V"));
                    String visitanteTenis = xml_partido.getChildTextTrim("visitante");
                    String pistaTenis = xml_partido.getChildTextTrim("pista");
                    //Añadimos los datos al partido, y el partido a la jornada actual
                    Partido partidoTenis = new PartidoTenis3Sets(fechaTenis, horaTenis, localTenis, set1LTenis, set1VTenis, set2LTenis, set2VTenis, set3LTenis, set3VTenis, visitanteTenis, pistaTenis);
                    jor.añadirPartido(partidoTenis);
                }else if(setsXML == 5){
                    String fechaTenis = xml_partido.getChildTextTrim("fecha");
                    String horaTenis = xml_partido.getChildTextTrim("hora");
                    String localTenis = xml_partido.getChildTextTrim("local");
                    int set1LTenis = Integer.parseInt(xml_partido.getChildTextTrim("set1L"));
                    int set1VTenis = Integer.parseInt(xml_partido.getChildTextTrim("set1V"));
                    int set2LTenis = Integer.parseInt(xml_partido.getChildTextTrim("set2L"));
                    int set2VTenis = Integer.parseInt(xml_partido.getChildTextTrim("set2V"));
                    int set3LTenis = Integer.parseInt(xml_partido.getChildTextTrim("set3L"));
                    int set3VTenis = Integer.parseInt(xml_partido.getChildTextTrim("set3V"));
                    int set4LTenis = Integer.parseInt(xml_partido.getChildTextTrim("set4L"));
                    int set4VTenis = Integer.parseInt(xml_partido.getChildTextTrim("set4V"));
                    int set5LTenis = Integer.parseInt(xml_partido.getChildTextTrim("set5L"));
                    int set5VTenis = Integer.parseInt(xml_partido.getChildTextTrim("set5V"));
                    String visitanteTenis = xml_partido.getChildTextTrim("visitante");
                    String pistaTenis = xml_partido.getChildTextTrim("pista");
                    //Añadimos los datos al partido, y el partido a la jornada actual
                    Partido partidoTenis = new PartidoTenis5Sets(fechaTenis, horaTenis, localTenis, set1LTenis, set1VTenis, set2LTenis, set2VTenis, set3LTenis, set3VTenis, 
                            set4LTenis, set4VTenis, set5LTenis, set5VTenis, visitanteTenis, pistaTenis);
                    jor.añadirPartido(partidoTenis);
                }else{
                    System.out.println("Error al crear partidos de Tenis con los sets");
                }
                break;
            case 8:
                String fechaHH = xml_partido.getChildTextTrim("fecha");
                String horaHH = xml_partido.getChildTextTrim("hora");
                String localHH = xml_partido.getChildTextTrim("local");
                int golesLHH = Integer.parseInt(xml_partido.getChildTextTrim("golesL"));
                int golesVHH = Integer.parseInt(xml_partido.getChildTextTrim("golesV"));
                String visitanteHH = xml_partido.getChildTextTrim("visitante");
                String pistaHH = xml_partido.getChildTextTrim("pista");
                String prorrogaHH = xml_partido.getChildTextTrim("prorroga");
                //Añadimos los datos al partido, y el partido a la jornada actual
                Partido partidoHH = new PartidoHockeyHielo(fechaHH, horaHH, localHH, golesLHH, golesVHH, visitanteHH, pistaHH, prorrogaHH);
                jor.añadirPartido(partidoHH);
               break;
            case 9:
                String fechaRugby = xml_partido.getChildTextTrim("fecha");
                String horaRugby = xml_partido.getChildTextTrim("hora");
                String localRugby = xml_partido.getChildTextTrim("local");
                int golesLRugby = Integer.parseInt(xml_partido.getChildTextTrim("golesL"));
                int golesVRugby = Integer.parseInt(xml_partido.getChildTextTrim("golesV"));
                String visitanteRugby = xml_partido.getChildTextTrim("visitante");
                int triesLRugby = Integer.parseInt(xml_partido.getChildTextTrim("triesL"));
                int triesVRugby = Integer.parseInt(xml_partido.getChildTextTrim("triesV"));
                String pistaRugby = xml_partido.getChildTextTrim("pista");             
                //Añadimos los datos al partido, y el partido a la jornada actual
                Partido partidoRugby = new PartidoRugby(fechaRugby, horaRugby, localRugby, golesLRugby, golesVRugby, visitanteRugby, triesLRugby, triesVRugby, pistaRugby);
                jor.añadirPartido(partidoRugby);
               break;
            case 10:
            case 11:
            case 13:
            case 16:
                String fechaT3sets = xml_partido.getChildTextTrim("fecha");
                String horaT3sets = xml_partido.getChildTextTrim("hora");
                String localT3sets = xml_partido.getChildTextTrim("local");
                int set1LT3sets = Integer.parseInt(xml_partido.getChildTextTrim("set1L"));
                int set1VT3sets = Integer.parseInt(xml_partido.getChildTextTrim("set1V"));
                int set2LT3sets = Integer.parseInt(xml_partido.getChildTextTrim("set2L"));
                int set2VT3sets = Integer.parseInt(xml_partido.getChildTextTrim("set2V"));
                int set3LT3sets = Integer.parseInt(xml_partido.getChildTextTrim("set3L"));
                int set3VT3sets = Integer.parseInt(xml_partido.getChildTextTrim("set3V"));
                String visitanteT3sets = xml_partido.getChildTextTrim("visitante");
                String pistaT3sets = xml_partido.getChildTextTrim("pista");
                //Añadimos los datos al partido, y el partido a la jornada actual
                Partido partidoT3sets = new PartidoTenis3Sets(fechaT3sets, horaT3sets, localT3sets, set1LT3sets, set1VT3sets, set2LT3sets, set2VT3sets, set3LT3sets, set3VT3sets, visitanteT3sets, pistaT3sets);
                jor.añadirPartido(partidoT3sets);
                break;
            case 12:
            case 15:
                String fechaT5sets = xml_partido.getChildTextTrim("fecha");
                String horaT5sets = xml_partido.getChildTextTrim("hora");
                String localT5sets = xml_partido.getChildTextTrim("local");
                int set1LT5sets = Integer.parseInt(xml_partido.getChildTextTrim("set1L"));
                int set1VT5sets = Integer.parseInt(xml_partido.getChildTextTrim("set1V"));
                int set2LT5sets = Integer.parseInt(xml_partido.getChildTextTrim("set2L"));
                int set2VT5sets = Integer.parseInt(xml_partido.getChildTextTrim("set2V"));
                int set3LT5sets = Integer.parseInt(xml_partido.getChildTextTrim("set3L"));
                int set3VT5sets = Integer.parseInt(xml_partido.getChildTextTrim("set3V"));
                int set4LT5sets = Integer.parseInt(xml_partido.getChildTextTrim("set4L"));
                int set4VT5sets = Integer.parseInt(xml_partido.getChildTextTrim("set4V"));
                int set5LT5sets = Integer.parseInt(xml_partido.getChildTextTrim("set5L"));
                int set5VT5sets = Integer.parseInt(xml_partido.getChildTextTrim("set5V"));
                String visitanteT5sets = xml_partido.getChildTextTrim("visitante");
                String pistaT5sets = xml_partido.getChildTextTrim("pista");
                //Añadimos los datos al partido, y el partido a la jornada actual
                Partido partidoT5sets = new PartidoTenis5Sets(fechaT5sets, horaT5sets, localT5sets, set1LT5sets, set1VT5sets, set2LT5sets, set2VT5sets, set3LT5sets, set3VT5sets,
                        set4LT5sets, set4VT5sets, set5LT5sets, set5VT5sets, visitanteT5sets, pistaT5sets);
                jor.añadirPartido(partidoT5sets);
                break;
            case 17:
                String fechaChess = xml_partido.getChildTextTrim("fecha");
                String horaChess = xml_partido.getChildTextTrim("hora");
                String localChess = xml_partido.getChildTextTrim("local");
                double blancasChess = Double.parseDouble(xml_partido.getChildTextTrim("blancas"));
                double negrasChess = Double.parseDouble(xml_partido.getChildTextTrim("negras"));
                String visitanteChess = xml_partido.getChildTextTrim("visitante");
                String pistaChess = xml_partido.getChildTextTrim("pista");
                //Añadimos los datos al partido, y el partido a la jornada actual
                Partido partidoChess = new PartidoAjedrez(fechaChess, horaChess, localChess, blancasChess, negrasChess, visitanteChess, pistaChess);
                jor.añadirPartido(partidoChess);
                break;
            case 18:
            case 19:
                String fechaBB = xml_partido.getChildTextTrim("fecha");
                String horaBB = xml_partido.getChildTextTrim("hora");
                String localBB = xml_partido.getChildTextTrim("local");
                int RL1BB = Integer.parseInt(xml_partido.getChildTextTrim("RL1"));
                int RL2BB = Integer.parseInt(xml_partido.getChildTextTrim("RL2"));
                int RL3BB = Integer.parseInt(xml_partido.getChildTextTrim("RL3"));
                int RL4BB = Integer.parseInt(xml_partido.getChildTextTrim("RL4"));
                int RL5BB = Integer.parseInt(xml_partido.getChildTextTrim("RL5"));
                int RL6BB = Integer.parseInt(xml_partido.getChildTextTrim("RL6"));
                int RL7BB = Integer.parseInt(xml_partido.getChildTextTrim("RL7"));
                int RL8BB = Integer.parseInt(xml_partido.getChildTextTrim("RL8"));
                int RL9BB = Integer.parseInt(xml_partido.getChildTextTrim("RL9"));
                int RL10BB = Integer.parseInt(xml_partido.getChildTextTrim("RL10"));
                int RL11BB = Integer.parseInt(xml_partido.getChildTextTrim("RL11"));
                int RL12BB = Integer.parseInt(xml_partido.getChildTextTrim("RL12"));
                int golesLBB = Integer.parseInt(xml_partido.getChildTextTrim("golesL"));
                int RV1BB = Integer.parseInt(xml_partido.getChildTextTrim("RV1"));
                int RV2BB = Integer.parseInt(xml_partido.getChildTextTrim("RV2"));
                int RV3BB = Integer.parseInt(xml_partido.getChildTextTrim("RV3"));
                int RV4BB = Integer.parseInt(xml_partido.getChildTextTrim("RV4"));
                int RV5BB = Integer.parseInt(xml_partido.getChildTextTrim("RV5"));
                int RV6BB = Integer.parseInt(xml_partido.getChildTextTrim("RV6"));
                int RV7BB = Integer.parseInt(xml_partido.getChildTextTrim("RV7"));
                int RV8BB = Integer.parseInt(xml_partido.getChildTextTrim("RV8"));
                int RV9BB = Integer.parseInt(xml_partido.getChildTextTrim("RV9"));
                int RV10BB = Integer.parseInt(xml_partido.getChildTextTrim("RV10"));
                int RV11BB = Integer.parseInt(xml_partido.getChildTextTrim("RV11"));
                int RV12BB = Integer.parseInt(xml_partido.getChildTextTrim("RV12"));
                int golesVBB = Integer.parseInt(xml_partido.getChildTextTrim("golesV"));
                String visitanteBB = xml_partido.getChildTextTrim("visitante");
                String pistaBB = xml_partido.getChildTextTrim("pista");
                //Añadimos los datos al partido, y el partido a la jornada actual
                Partido partidoBB = new PartidoBeisbol(fechaBB, horaBB, localBB, RL1BB, RL2BB, RL3BB, RL4BB, RL5BB, RL6BB, RL7BB, RL8BB, RL9BB, RL10BB, RL11BB, RL12BB, golesLBB, 
                       RV1BB, RV2BB, RV3BB, RV4BB, RV5BB, RV6BB, RV7BB, RV8BB, RV9BB, RV10BB, RV11BB, RV12BB, golesVBB, visitanteBB, pistaBB);
                jor.añadirPartido(partidoBB);
                break;
            default:
                String fecha = xml_partido.getChildTextTrim("fecha");
                String hora = xml_partido.getChildTextTrim("hora");
                String local = xml_partido.getChildTextTrim("local");
                int golesL = Integer.parseInt(xml_partido.getChildTextTrim("golesL"));
                int golesV = Integer.parseInt(xml_partido.getChildTextTrim("golesV"));
                String visitante = xml_partido.getChildTextTrim("visitante");
                String pista = xml_partido.getChildTextTrim("pista");
                //Añadimos los datos al partido, y el partido a la jornada actual
                Partido partido = new Partido(fecha, hora, local, golesL, golesV, visitante, pista);
                jor.añadirPartido(partido);
        }//End switch
        
    }
    
    /**
     *  Método que selecciona el tipo de equipo necesario para cargar el XML correspondiente y lo añade a la Lista de Equipos (castEquipoLista)
     * @param xml_equipo
     * @param deporteXML
     */
    public void seleccionarClaseEquipoXML(Element xml_equipo, int deporteXML){
        switch(deporteXML){
            case 1:
            case 18:
            case 19:
                int numeroB = Integer.parseInt(xml_equipo.getChildTextTrim("numero"));
                String teamB = xml_equipo.getChildTextTrim("team");//es el nombre del equipo
                int posicionB = Integer.parseInt(xml_equipo.getChildTextTrim("posicion"));
                int pjB = Integer.parseInt(xml_equipo.getChildTextTrim("pj"));
                int pgB = Integer.parseInt(xml_equipo.getChildTextTrim("pg"));
                int ppB = Integer.parseInt(xml_equipo.getChildTextTrim("pp"));
                int pfB = Integer.parseInt(xml_equipo.getChildTextTrim("pf"));
                int pcB = Integer.parseInt(xml_equipo.getChildTextTrim("pc"));
                int difB = Integer.parseInt(xml_equipo.getChildTextTrim("dif"));
                //Añadimos los datos del equipo a la Lista de equipos
                Equipo equipoB = new EquipoBasket(numeroB, teamB, posicionB, pjB, pgB, ppB, pfB, pcB, difB);
                castEquipoLista.getEquipos().add(equipoB);
                break;
            case 2:
            case 10:
            case 11:
            case 12:
            case 13:
            case 16:
                int numeroTenis = Integer.parseInt(xml_equipo.getChildTextTrim("numero"));
                String teamTenis = xml_equipo.getChildTextTrim("team");//es el nombre del equipo
                int posicionTenis = Integer.parseInt(xml_equipo.getChildTextTrim("posicion"));
                int pjTenis = Integer.parseInt(xml_equipo.getChildTextTrim("pj"));
                int pgTenis = Integer.parseInt(xml_equipo.getChildTextTrim("pg"));
                int ppTenis = Integer.parseInt(xml_equipo.getChildTextTrim("pp"));
                int sfTenis = Integer.parseInt(xml_equipo.getChildTextTrim("sf"));
                int scTenis = Integer.parseInt(xml_equipo.getChildTextTrim("sc"));
                int jfTenis = Integer.parseInt(xml_equipo.getChildTextTrim("jf"));
                int jcTenis = Integer.parseInt(xml_equipo.getChildTextTrim("jc"));
                //Añadimos los datos del equipo a la Lista de equipos
                Equipo equipoTenis = new EquipoTenis(numeroTenis, teamTenis, posicionTenis, pjTenis, pgTenis, ppTenis, sfTenis, scTenis, jfTenis, jcTenis);
                castEquipoLista.getEquipos().add(equipoTenis);
                break;
            case 8:
                int numeroHH = Integer.parseInt(xml_equipo.getChildTextTrim("numero"));
                String teamHH = xml_equipo.getChildTextTrim("team");//es el nombre del equipo
                int posicionHH = Integer.parseInt(xml_equipo.getChildTextTrim("posicion"));
                int pjHH = Integer.parseInt(xml_equipo.getChildTextTrim("pj"));
                int pgHH = Integer.parseInt(xml_equipo.getChildTextTrim("pg"));
                int pgpHH = Integer.parseInt(xml_equipo.getChildTextTrim("pgp"));
                int pppHH = Integer.parseInt(xml_equipo.getChildTextTrim("ppp"));
                int ppHH = Integer.parseInt(xml_equipo.getChildTextTrim("pp"));
                int gfHH = Integer.parseInt(xml_equipo.getChildTextTrim("gf"));
                int gcHH = Integer.parseInt(xml_equipo.getChildTextTrim("gc"));
                int ptosHH = Integer.parseInt(xml_equipo.getChildTextTrim("ptos"));
                //Añadimos los datos del equipo a la Lista de equipos
                Equipo equipoHH = new EquipoHockeyHielo(numeroHH, teamHH, posicionHH, pjHH, pgHH, pgpHH, pppHH, ppHH, gfHH, gcHH, ptosHH);
                castEquipoLista.getEquipos().add(equipoHH);
                break;
            case 9:
                int numeroRugby = Integer.parseInt(xml_equipo.getChildTextTrim("numero"));
                String teamRugby = xml_equipo.getChildTextTrim("team");//es el nombre del equipo
                int posicionRugby = Integer.parseInt(xml_equipo.getChildTextTrim("posicion"));
                int pjRugby = Integer.parseInt(xml_equipo.getChildTextTrim("pj"));
                int pgRugby = Integer.parseInt(xml_equipo.getChildTextTrim("pg"));
                int peRugby = Integer.parseInt(xml_equipo.getChildTextTrim("pe"));
                int ppRugby = Integer.parseInt(xml_equipo.getChildTextTrim("pp"));
                int pfRugby = Integer.parseInt(xml_equipo.getChildTextTrim("pf"));
                int pcRugby = Integer.parseInt(xml_equipo.getChildTextTrim("pc"));
                int tfRugby = Integer.parseInt(xml_equipo.getChildTextTrim("tf"));
                int tcRugby = Integer.parseInt(xml_equipo.getChildTextTrim("tc"));
                int ptosRugby = Integer.parseInt(xml_equipo.getChildTextTrim("ptos"));
                //Añadimos los datos del equipo a la Lista de equipos
                Equipo equipoRugby = new EquipoRugby(numeroRugby, teamRugby, posicionRugby, pjRugby, pgRugby, peRugby, ppRugby, pfRugby, pcRugby, tfRugby, tcRugby, ptosRugby);
                castEquipoLista.getEquipos().add(equipoRugby);
                break;
            case 15:
                int numeroVol = Integer.parseInt(xml_equipo.getChildTextTrim("numero"));
                String teamVol = xml_equipo.getChildTextTrim("team");//es el nombre del equipo
                int posicionVol = Integer.parseInt(xml_equipo.getChildTextTrim("posicion"));
                int pjVol = Integer.parseInt(xml_equipo.getChildTextTrim("pj"));
                int pgVol = Integer.parseInt(xml_equipo.getChildTextTrim("pg"));
                int ppVol = Integer.parseInt(xml_equipo.getChildTextTrim("pp"));
                int sfVol = Integer.parseInt(xml_equipo.getChildTextTrim("sf"));
                int scVol = Integer.parseInt(xml_equipo.getChildTextTrim("sc"));
                int pfVol = Integer.parseInt(xml_equipo.getChildTextTrim("pf"));
                int pcVol = Integer.parseInt(xml_equipo.getChildTextTrim("pc"));
                int ptosVol = Integer.parseInt(xml_equipo.getChildTextTrim("ptos"));
                //Añadimos los datos del equipo a la Lista de equipos
                Equipo equipoVol = new EquipoVoleibol(numeroVol, teamVol, posicionVol, pjVol, pgVol, ppVol, sfVol, scVol, pfVol, pcVol, ptosVol);
                castEquipoLista.getEquipos().add(equipoVol);
                break;
            case 17:
                int numeroChess = Integer.parseInt(xml_equipo.getChildTextTrim("numero"));
                String teamChess = xml_equipo.getChildTextTrim("team");//es el nombre del equipo
                int posicionChess = Integer.parseInt(xml_equipo.getChildTextTrim("posicion"));
                int pjChess = Integer.parseInt(xml_equipo.getChildTextTrim("pj"));
                int pgChess = Integer.parseInt(xml_equipo.getChildTextTrim("pg"));
                int peChess = Integer.parseInt(xml_equipo.getChildTextTrim("pe"));
                int ppChess = Integer.parseInt(xml_equipo.getChildTextTrim("pp"));
                double ptosChess = Double.parseDouble(xml_equipo.getChildTextTrim("ptosAj"));
                double sbChess = Double.parseDouble(xml_equipo.getChildTextTrim("sb"));
                //Añadimos los datos del equipo a la Lista de equipos
                Equipo equipoChess = new EquipoAjedrez(numeroChess, teamChess, posicionChess, pjChess, pgChess, peChess, ppChess, ptosChess, sbChess);
                castEquipoLista.getEquipos().add(equipoChess);
                break;              
            default:
                int numero = Integer.parseInt(xml_equipo.getChildTextTrim("numero"));
                String team = xml_equipo.getChildTextTrim("team");//es el nombre del equipo
                int posicion = Integer.parseInt(xml_equipo.getChildTextTrim("posicion"));
                int pj = Integer.parseInt(xml_equipo.getChildTextTrim("pj"));
                int pg = Integer.parseInt(xml_equipo.getChildTextTrim("pg"));
                int pe = Integer.parseInt(xml_equipo.getChildTextTrim("pe"));
                int pp = Integer.parseInt(xml_equipo.getChildTextTrim("pp"));
                int gf = Integer.parseInt(xml_equipo.getChildTextTrim("gf"));
                int gc = Integer.parseInt(xml_equipo.getChildTextTrim("gc"));
                int ptos = Integer.parseInt(xml_equipo.getChildTextTrim("ptos"));
                //Añadimos los datos del equipo a la Lista de equipos
                Equipo equipo = new EquipoFutbol(numero, team, posicion, pj, pg, pe, pp, gf, gc, ptos);
                castEquipoLista.getEquipos().add(equipo);
        }//End switch
 
    }//End seleccionarClaseEquipoXML()
    
    /**
     * Método que seleccionará el tipo de partido utilizado en función del deporte escogido para las eliminatorias (no las ligas)
     * @param xml_partido
     * @param deporteXML
     * @param setsXML
     * @param jor 
     */
    public void seleccionarClasePartidoEliminatoriaXML(Element xml_partido, int deporteXML, int setsXML, Jornada jor){
    
        switch(deporteXML){
            
            case 2:
                if(setsXML == 3){
                    String fechaTenis = xml_partido.getChildTextTrim("fecha");
                    String horaTenis = xml_partido.getChildTextTrim("hora");
                    String localTenis = xml_partido.getChildTextTrim("local");
                    int set1LTenis = Integer.parseInt(xml_partido.getChildTextTrim("set1L"));
                    int set1VTenis = Integer.parseInt(xml_partido.getChildTextTrim("set1V"));
                    int set2LTenis = Integer.parseInt(xml_partido.getChildTextTrim("set2L"));
                    int set2VTenis = Integer.parseInt(xml_partido.getChildTextTrim("set2V"));
                    int set3LTenis = Integer.parseInt(xml_partido.getChildTextTrim("set3L"));
                    int set3VTenis = Integer.parseInt(xml_partido.getChildTextTrim("set3V"));
                    String visitanteTenis = xml_partido.getChildTextTrim("visitante");
                    String pistaTenis = xml_partido.getChildTextTrim("pista");
                    String ganadorTenis = xml_partido.getChildTextTrim("ganador");
                    String desempateTenis = xml_partido.getChildTextTrim("desempate");
                    //Añadimos los datos al partido, y el partido a la jornada actual
                    Partido partidoTenis = new PartidoEliminatoriaTenis3Sets(fechaTenis, horaTenis, localTenis, set1LTenis, set1VTenis, set2LTenis, set2VTenis, set3LTenis, set3VTenis, visitanteTenis, pistaTenis, ganadorTenis, desempateTenis);
                    jor.añadirPartido(partidoTenis);
                }else if(setsXML == 5){
                    String fechaTenis = xml_partido.getChildTextTrim("fecha");
                    String horaTenis = xml_partido.getChildTextTrim("hora");
                    String localTenis = xml_partido.getChildTextTrim("local");
                    int set1LTenis = Integer.parseInt(xml_partido.getChildTextTrim("set1L"));
                    int set1VTenis = Integer.parseInt(xml_partido.getChildTextTrim("set1V"));
                    int set2LTenis = Integer.parseInt(xml_partido.getChildTextTrim("set2L"));
                    int set2VTenis = Integer.parseInt(xml_partido.getChildTextTrim("set2V"));
                    int set3LTenis = Integer.parseInt(xml_partido.getChildTextTrim("set3L"));
                    int set3VTenis = Integer.parseInt(xml_partido.getChildTextTrim("set3V"));
                    int set4LTenis = Integer.parseInt(xml_partido.getChildTextTrim("set4L"));
                    int set4VTenis = Integer.parseInt(xml_partido.getChildTextTrim("set4V"));
                    int set5LTenis = Integer.parseInt(xml_partido.getChildTextTrim("set5L"));
                    int set5VTenis = Integer.parseInt(xml_partido.getChildTextTrim("set5V"));
                    String visitanteTenis = xml_partido.getChildTextTrim("visitante");
                    String pistaTenis = xml_partido.getChildTextTrim("pista");
                    String ganadorTenis = xml_partido.getChildTextTrim("ganador");
                    String desempateTenis = xml_partido.getChildTextTrim("desempate");
                    //Añadimos los datos al partido, y el partido a la jornada actual
                    Partido partidoTenis = new PartidoEliminatoriaTenis5Sets(fechaTenis, horaTenis, localTenis, set1LTenis, set1VTenis, set2LTenis, set2VTenis, set3LTenis, set3VTenis, 
                            set4LTenis, set4VTenis, set5LTenis, set5VTenis, visitanteTenis, pistaTenis, ganadorTenis, desempateTenis);
                    jor.añadirPartido(partidoTenis);
                }else{
                    System.out.println("Error al crear partidos de Tenis con los sets");
                }
                break;
            case 10:
            case 11:
            case 13:
            case 16:
                String fechaT3sets = xml_partido.getChildTextTrim("fecha");
                String horaT3sets = xml_partido.getChildTextTrim("hora");
                String localT3sets = xml_partido.getChildTextTrim("local");
                int set1LT3sets = Integer.parseInt(xml_partido.getChildTextTrim("set1L"));
                int set1VT3sets = Integer.parseInt(xml_partido.getChildTextTrim("set1V"));
                int set2LT3sets = Integer.parseInt(xml_partido.getChildTextTrim("set2L"));
                int set2VT3sets = Integer.parseInt(xml_partido.getChildTextTrim("set2V"));
                int set3LT3sets = Integer.parseInt(xml_partido.getChildTextTrim("set3L"));
                int set3VT3sets = Integer.parseInt(xml_partido.getChildTextTrim("set3V"));
                String visitanteT3sets = xml_partido.getChildTextTrim("visitante");
                String pistaT3sets = xml_partido.getChildTextTrim("pista");
                String ganadorT3sets = xml_partido.getChildTextTrim("ganador");
                String desempateT3sets = xml_partido.getChildTextTrim("desempate");
                //Añadimos los datos al partido, y el partido a la jornada actual
                Partido partidoT3sets = new PartidoEliminatoriaTenis3Sets(fechaT3sets, horaT3sets, localT3sets, set1LT3sets, set1VT3sets, set2LT3sets, set2VT3sets, set3LT3sets, set3VT3sets, visitanteT3sets, pistaT3sets, ganadorT3sets, desempateT3sets);
                jor.añadirPartido(partidoT3sets);
                break;
            case 12:
            case 15:
                String fechaT5sets = xml_partido.getChildTextTrim("fecha");
                String horaT5sets = xml_partido.getChildTextTrim("hora");
                String localT5sets = xml_partido.getChildTextTrim("local");
                int set1LT5sets = Integer.parseInt(xml_partido.getChildTextTrim("set1L"));
                int set1VT5sets = Integer.parseInt(xml_partido.getChildTextTrim("set1V"));
                int set2LT5sets = Integer.parseInt(xml_partido.getChildTextTrim("set2L"));
                int set2VT5sets = Integer.parseInt(xml_partido.getChildTextTrim("set2V"));
                int set3LT5sets = Integer.parseInt(xml_partido.getChildTextTrim("set3L"));
                int set3VT5sets = Integer.parseInt(xml_partido.getChildTextTrim("set3V"));
                int set4LT5sets = Integer.parseInt(xml_partido.getChildTextTrim("set4L"));
                int set4VT5sets = Integer.parseInt(xml_partido.getChildTextTrim("set4V"));
                int set5LT5sets = Integer.parseInt(xml_partido.getChildTextTrim("set5L"));
                int set5VT5sets = Integer.parseInt(xml_partido.getChildTextTrim("set5V"));
                String visitanteT5sets = xml_partido.getChildTextTrim("visitante");
                String pistaT5sets = xml_partido.getChildTextTrim("pista");
                String ganadorT5sets = xml_partido.getChildTextTrim("ganador");
                String desempateT5sets = xml_partido.getChildTextTrim("desempate");
                //Añadimos los datos al partido, y el partido a la jornada actual
                Partido partidoT5sets = new PartidoEliminatoriaTenis5Sets(fechaT5sets, horaT5sets, localT5sets, set1LT5sets, set1VT5sets, set2LT5sets, set2VT5sets, set3LT5sets, set3VT5sets,
                        set4LT5sets, set4VT5sets, set5LT5sets, set5VT5sets, visitanteT5sets, pistaT5sets, ganadorT5sets, desempateT5sets);
                jor.añadirPartido(partidoT5sets);
                break;
            case 17:
                String fechaChess = xml_partido.getChildTextTrim("fecha");
                String horaChess = xml_partido.getChildTextTrim("hora");
                String localChess = xml_partido.getChildTextTrim("local");
                double blancasChess = Double.parseDouble(xml_partido.getChildTextTrim("blancas"));
                double negrasChess = Double.parseDouble(xml_partido.getChildTextTrim("negras"));
                String visitanteChess = xml_partido.getChildTextTrim("visitante");
                String pistaChess = xml_partido.getChildTextTrim("pista");
                String ganadorChess = xml_partido.getChildTextTrim("ganador");
                String desempateChess = xml_partido.getChildTextTrim("desempate");
                //Añadimos los datos al partido, y el partido a la jornada actual
                Partido partidoChess = new PartidoEliminatoriaAjedrez(fechaChess, horaChess, localChess, blancasChess, negrasChess, visitanteChess, pistaChess, ganadorChess, desempateChess);
                jor.añadirPartido(partidoChess);
                break;
            case 18:
            case 19:
                String fechaBB = xml_partido.getChildTextTrim("fecha");
                String horaBB = xml_partido.getChildTextTrim("hora");
                String localBB = xml_partido.getChildTextTrim("local");
                int RL1BB = Integer.parseInt(xml_partido.getChildTextTrim("RL1"));
                int RL2BB = Integer.parseInt(xml_partido.getChildTextTrim("RL2"));
                int RL3BB = Integer.parseInt(xml_partido.getChildTextTrim("RL3"));
                int RL4BB = Integer.parseInt(xml_partido.getChildTextTrim("RL4"));
                int RL5BB = Integer.parseInt(xml_partido.getChildTextTrim("RL5"));
                int RL6BB = Integer.parseInt(xml_partido.getChildTextTrim("RL6"));
                int RL7BB = Integer.parseInt(xml_partido.getChildTextTrim("RL7"));
                int RL8BB = Integer.parseInt(xml_partido.getChildTextTrim("RL8"));
                int RL9BB = Integer.parseInt(xml_partido.getChildTextTrim("RL9"));
                int RL10BB = Integer.parseInt(xml_partido.getChildTextTrim("RL10"));
                int RL11BB = Integer.parseInt(xml_partido.getChildTextTrim("RL11"));
                int RL12BB = Integer.parseInt(xml_partido.getChildTextTrim("RL12"));
                int golesLBB = Integer.parseInt(xml_partido.getChildTextTrim("golesL"));
                int RV1BB = Integer.parseInt(xml_partido.getChildTextTrim("RV1"));
                int RV2BB = Integer.parseInt(xml_partido.getChildTextTrim("RV2"));
                int RV3BB = Integer.parseInt(xml_partido.getChildTextTrim("RV3"));
                int RV4BB = Integer.parseInt(xml_partido.getChildTextTrim("RV4"));
                int RV5BB = Integer.parseInt(xml_partido.getChildTextTrim("RV5"));
                int RV6BB = Integer.parseInt(xml_partido.getChildTextTrim("RV6"));
                int RV7BB = Integer.parseInt(xml_partido.getChildTextTrim("RV7"));
                int RV8BB = Integer.parseInt(xml_partido.getChildTextTrim("RV8"));
                int RV9BB = Integer.parseInt(xml_partido.getChildTextTrim("RV9"));
                int RV10BB = Integer.parseInt(xml_partido.getChildTextTrim("RV10"));
                int RV11BB = Integer.parseInt(xml_partido.getChildTextTrim("RV11"));
                int RV12BB = Integer.parseInt(xml_partido.getChildTextTrim("RV12"));
                int golesVBB = Integer.parseInt(xml_partido.getChildTextTrim("golesV"));
                String visitanteBB = xml_partido.getChildTextTrim("visitante");
                String pistaBB = xml_partido.getChildTextTrim("pista");
                String ganadorBB = xml_partido.getChildTextTrim("ganador");
                String desempateBB = xml_partido.getChildTextTrim("desempate");
                //Añadimos los datos al partido, y el partido a la jornada actual
                Partido partidoBB = new PartidoEliminatoriaBeisbol(fechaBB, horaBB, localBB, RL1BB, RL2BB, RL3BB, RL4BB, RL5BB, RL6BB, RL7BB, RL8BB, RL9BB, RL10BB, RL11BB, RL12BB, golesLBB, 
                       RV1BB, RV2BB, RV3BB, RV4BB, RV5BB, RV6BB, RV7BB, RV8BB, RV9BB, RV10BB, RV11BB, RV12BB, golesVBB, visitanteBB, pistaBB, ganadorBB, desempateBB);
                jor.añadirPartido(partidoBB);
                break;
            default:
                String fecha = xml_partido.getChildTextTrim("fecha");
                String hora = xml_partido.getChildTextTrim("hora");
                String local = xml_partido.getChildTextTrim("local");
                int golesL = Integer.parseInt(xml_partido.getChildTextTrim("golesL"));
                int golesV = Integer.parseInt(xml_partido.getChildTextTrim("golesV"));
                String visitante = xml_partido.getChildTextTrim("visitante");
                String pista = xml_partido.getChildTextTrim("pista");
                String ganador = xml_partido.getChildTextTrim("ganador");
                String desempate = xml_partido.getChildTextTrim("desempate");
                //Añadimos los datos al partido, y el partido a la jornada actual
                Partido partido = new PartidoEliminatoriaFutbol(fecha, hora, local, golesL, golesV, visitante, pista, ganador, desempate);
                jor.añadirPartido(partido);
        }//End switch
    }//End seleccionarClasePartidoEliminatoriaXML()
    
    /**
     * Método que valida la longitud de la cadena (estático para utilizar también en las clases Métodos)
     * @param cadena
     * @param limite
     * @param mensaje
     * @return 
     */
    public static String limitarCadena(String cadena, int limite, boolean mensaje){
        //Si esta bien devolvemos la cadena, y si excede el límite la devuelve hasta el límite
        if(cadena.length() > limite){
            if(mensaje){
                JOptionPane.showMessageDialog(null, "La cadena excede el número de caracteres permitidos. Máximo " + limite + " caracteres", "Error al ingresar un dato",JOptionPane.WARNING_MESSAGE);
            }
            cadena = cadena.substring(0, limite);
            return cadena;
        }else{
            return cadena;
        }
    }
    
    /**
     * Método que decide que informe generar en función del número pasado
     * @param numeroInforme 
     * @param aplazados 
     */
    public void generarInforme(int numeroInforme, boolean aplazados){
        if(castEquipoLista.getEquipos().size() > 0){
            if(deporte.getTipoTorneo() == 1){
                File archivo;//Creamos un archivo para poder pasar el filtro(ya que en realidad le da el formato en Metodos)
                JFileChooser fileChooser;
                FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF", "pdf");
                if(rutaPDF.equalsIgnoreCase("")){
                    fileChooser = new JFileChooser();
                }else{
                    fileChooser = new JFileChooser(rutaPDF);
                }
                //Añadimos el filtro PDF
                fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("PDF", "pdf"));
                //Modificamos alguna propiedad del JFileChooser
                fileChooser.setDialogTitle("Escoja el nombre y la ubicación de su nuevo informe");
                //fileChooser.setApproveButtonText("Crear Informe");//No me lo carga
                fileChooser.setApproveButtonToolTipText("Guardará el informe de nombre dado en la ubicación seleccionada");

                try{
                    if(fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
                        rutaPDF = valueOf(fileChooser.getCurrentDirectory());//obtenemos la ruta del archivo (el directorio)
                        fileProp.setProperty("rutaPDF", rutaPDF);//Asignamos la nueva ruta al archivo de propiedades
                        guardarPropiedades();
                        String nombreArchivo = fileChooser.getSelectedFile().getName();//obtiene el nombre del archivo (el que le damos)
                        //Si el archivo no tiene más de 3 caracteres significa que no tiene el .xml añadido, así que, lo añadimos (cuando este añadido tendrá más de 3 caracteres y se saltará esta parte del if)
                        if(nombreArchivo.length() <= 3){
                            //si el archivo simplemente no tiene definida extensión se la añadimos
                            nombreArchivo = fileChooser.getSelectedFile().getName()+".pdf";//obtiene el nombre del archivo (el que le damos) 
                            archivo = new File(nombreArchivo);//Inicializamos el archivo auxiliar
                            fileChooser.setSelectedFile(archivo);
                        }else if(nombreArchivo.substring(nombreArchivo.length() - 4).equalsIgnoreCase(".pdf")){
                            nombreArchivo = fileChooser.getSelectedFile().getName();//obtiene el nombre del archivo (el que le damos)  
                            archivo = new File(nombreArchivo);//Inicializamos el archivo auxiliar
                        }else{
                            //si el archivo simplemente no tiene definida extensión se la añadimos
                            nombreArchivo = fileChooser.getSelectedFile().getName()+".pdf";//obtiene el nombre del archivo (el que le damos) 
                            archivo = new File(nombreArchivo);//Inicializamos el archivo auxiliar
                            fileChooser.setSelectedFile(archivo);
                        }    
                        fileChooser.setFileFilter(filter);//Asignamos el filtro
                        //Una vez asignado el filtro, si el arhivo es PDF (devuelve true) realizamos la operación, de lo contrario mostramos un Mensaje
                        if(filter.accept(fileChooser.getSelectedFile())){
                            File directorio = fileChooser.getCurrentDirectory();
                            //Comprobamos si ya existe ese archivo en el directorio y en caso de ello avisamos al usuario de si quiere sobreescribirlo                   
                            if(directorio.exists()){//Comprueba la existencia del directorio
                                File[] ficheros = directorio.listFiles();
                                int sobreescritura;
                                for (File fichero : ficheros) {
                                    if (fichero.getName().equalsIgnoreCase(nombreArchivo)) { 
                                        //El html permite utilizar las etiquetas comunes para mostrar el texto de los componentes en distintos formatos (tamaños, bold, italic, underline, listas, etc.)
                                       sobreescritura =  JOptionPane.showConfirmDialog(null, "<html>El archivo <b>" + nombreArchivo + "</b> ya existe, ¿desea sobreescribirlo?</html>", "Advertencia",JOptionPane.OK_CANCEL_OPTION);
                                       if(JOptionPane.OK_OPTION == sobreescritura){
                                           //Creamos el informe que nos han pasado según el número de informe
                                            switch(numeroInforme){
                                                case 0:
                                                    if(aplazados){
                                                        deporte.informeJornada(rutaPDF, filtroCaracteres(nombreArchivo), aplazados, getPartidosAplazadosPr());
                                                    }else{
                                                        deporte.informeJornada(rutaPDF, filtroCaracteres(nombreArchivo), aplazados, getPartidosAplazadosPr());
                                                    }
                                                    break;
                                                case 1:
                                                    if(aplazados){
                                                        deporte.informeResultados(rutaPDF, filtroCaracteres(nombreArchivo), aplazados, getPartidosAplazadosPr());
                                                    }else{
                                                        deporte.informeResultados(rutaPDF, filtroCaracteres(nombreArchivo), aplazados, getPartidosAplazadosPr());
                                                    }
                                                    break;
                                                case 2:
                                                    deporte.informeClasificacion(rutaPDF, filtroCaracteres(nombreArchivo));
                                                    break;
                                                case 3:
                                                    deporte.informeCalendario(rutaPDF, filtroCaracteres(nombreArchivo));
                                                    break;
                                                case 4:
                                                    deporte.informeTodasJornadas(rutaPDF, filtroCaracteres(nombreArchivo));
                                                    break;
                                                case 5:
                                                    deporte.informeTodosResultados(rutaPDF, filtroCaracteres(nombreArchivo));
                                                    break;
                                                default:
                                                    deporte.informeJornada(rutaPDF, nombreArchivo, aplazados, getPartidosAplazadosPr());
                                            }//End switch
                                           return;//salimos del metodo
                                       }else{
                                           //Cancelamos la acción de sobreescribir
                                           fileChooser.cancelSelection();
                                           return;//salimos del método (si no lo guarda abajo)
                                       }//end if
                                    }//end if comprobacion nombre ficheros
                                }//end for loop
                            }else{
                                JOptionPane.showMessageDialog(null, "No se encontró el directorio seleccionado", "Error al buscar el directorio",JOptionPane.ERROR_MESSAGE);
                            }//end if
                            //Comprueba si el archivo existe y  lo elimina
                            if(archivo.exists()){
                                archivo.delete();
                            }
                            //Creamos el informe que nos han pasado según el número de informe
                            switch(numeroInforme){
                                case 0:
                                    if(aplazados){
                                        deporte.informeJornada(rutaPDF, filtroCaracteres(nombreArchivo), aplazados, getPartidosAplazadosPr());
                                    }else{
                                        deporte.informeJornada(rutaPDF, filtroCaracteres(nombreArchivo), aplazados, getPartidosAplazadosPr());
                                    }
                                    break;
                                case 1:
                                    if(aplazados){
                                        deporte.informeResultados(rutaPDF, filtroCaracteres(nombreArchivo), aplazados, getPartidosAplazadosPr());
                                    }else{
                                        deporte.informeResultados(rutaPDF, filtroCaracteres(nombreArchivo), aplazados, getPartidosAplazadosPr());
                                    }
                                    break;
                                case 2:
                                    deporte.informeClasificacion(rutaPDF, nombreArchivo);
                                    break;
                                case 3:
                                    deporte.informeCalendario(rutaPDF, nombreArchivo);
                                    break;
                                case 4:
                                    deporte.informeTodasJornadas(rutaPDF, nombreArchivo);
                                    break;
                                case 5:
                                    deporte.informeTodosResultados(rutaPDF, nombreArchivo);
                                    break;
                                default:
                                    deporte.informeJornada(rutaPDF, nombreArchivo, aplazados, getPartidosAplazadosPr());
                            }//End switch
                        }else{
                            JOptionPane.showMessageDialog(null, "El archivo debe tener formato PDF", "Advertencia",JOptionPane.WARNING_MESSAGE);
                        }
                    }//end if aceptar JFileChooser
                }catch (HeadlessException hlex){
                    JOptionPane.showMessageDialog(null, "El archivo no se ha guardado", "Advertencia",JOptionPane.WARNING_MESSAGE);
                }
                //deporte.guardarXML();
                }else if(deporte.getTipoTorneo() == 2){
                    //Si es clasificación mostramos el mensaje de que no se puede generar una clasificación en eliminatorias
                    if(numeroInforme == 2){
                        JOptionPane.showMessageDialog(null, "Las eliminatorias no utilizan tabla de clasificación", "Advertencia, selección incorrecta",JOptionPane.WARNING_MESSAGE); 
                    }else{
                        File archivo;//Creamos un archivo para poder pasar el filtro(ya que en realidad le da el formato en Metodos)
                        JFileChooser fileChooser;
                        FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF", "pdf");
                        if(rutaPDF.equalsIgnoreCase("")){
                            fileChooser = new JFileChooser();
                        }else{
                            fileChooser = new JFileChooser(rutaPDF);
                        }
                        //Añadimos el filtro PDF
                        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("PDF", "pdf"));
                        //Modificamos alguna propiedad del JFileChooser
                        fileChooser.setDialogTitle("Escoja el nombre y la ubicación de su nuevo informe");
                        //fileChooser.setApproveButtonText("Crear Informe");//No me lo carga
                        fileChooser.setApproveButtonToolTipText("Guardará el informe de nombre dado en la ubicación seleccionada");

                        try{
                            if(fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
                                rutaPDF = valueOf(fileChooser.getCurrentDirectory());//obtenemos la ruta del archivo (el directorio)
                                fileProp.setProperty("rutaPDF", rutaPDF);//Asignamos la nueva ruta al archivo de propiedades
                                guardarPropiedades();
                                String nombreArchivo = fileChooser.getSelectedFile().getName();//obtiene el nombre del archivo (el que le damos)
                                //Si el archivo no tiene más de 3 caracteres significa que no tiene el .xml añadido, así que, lo añadimos (cuando este añadido tendrá más de 3 caracteres y se saltará esta parte del if)
                                if(nombreArchivo.length() <= 3){
                                    //si el archivo simplemente no tiene definida extensión se la añadimos
                                    nombreArchivo = fileChooser.getSelectedFile().getName()+".pdf";//obtiene el nombre del archivo (el que le damos) 
                                    archivo = new File(nombreArchivo);//Inicializamos el archivo auxiliar
                                    fileChooser.setSelectedFile(archivo);
                                }else if(nombreArchivo.substring(nombreArchivo.length() - 4).equalsIgnoreCase(".pdf")){
                                    nombreArchivo = fileChooser.getSelectedFile().getName();//obtiene el nombre del archivo (el que le damos)  
                                    archivo = new File(nombreArchivo);//Inicializamos el archivo auxiliar
                                }else{
                                    //si el archivo simplemente no tiene definida extensión se la añadimos
                                    nombreArchivo = fileChooser.getSelectedFile().getName()+".pdf";//obtiene el nombre del archivo (el que le damos) 
                                    archivo = new File(nombreArchivo);//Inicializamos el archivo auxiliar
                                    fileChooser.setSelectedFile(archivo);
                                }    
                                fileChooser.setFileFilter(filter);//Asignamos el filtro
                                //Una vez asignado el filtro, si el arhivo es PDF (devuelve true) realizamos la operación, de lo contrario mostramos un Mensaje
                                if(filter.accept(fileChooser.getSelectedFile())){
                                    File directorio = fileChooser.getCurrentDirectory();
                                    //Comprobamos si ya existe ese archivo en el directorio y en caso de ello avisamos al usuario de si quiere sobreescribirlo                   
                                    if(directorio.exists()){//Comprueba la existencia del directorio
                                        File[] ficheros = directorio.listFiles();
                                        int sobreescritura;
                                        for (File fichero : ficheros) {
                                            if (fichero.getName().equalsIgnoreCase(nombreArchivo)) { 
                                                //El html permite utilizar las etiquetas comunes para mostrar el texto de los componentes en distintos formatos (tamaños, bold, italic, underline, listas, etc.)
                                               sobreescritura =  JOptionPane.showConfirmDialog(null, "<html>El archivo <b>" + nombreArchivo + "</b> ya existe, ¿desea sobreescribirlo?</html>", "Advertencia",JOptionPane.OK_CANCEL_OPTION);
                                               if(JOptionPane.OK_OPTION == sobreescritura){
                                                   //Creamos el informe que nos han pasado según el número de informe
                                                    switch(numeroInforme){
                                                        case 0:
                                                            if(aplazados){
                                                                deporte.informeJornada(rutaPDF, filtroCaracteres(nombreArchivo), aplazados, getPartidosAplazadosPr());
                                                            }else{
                                                                deporte.informeJornada(rutaPDF, filtroCaracteres(nombreArchivo), aplazados, getPartidosAplazadosPr());
                                                            }
                                                            break;
                                                        case 1:
                                                            if(aplazados){
                                                                deporte.informeResultados(rutaPDF, filtroCaracteres(nombreArchivo), aplazados, getPartidosAplazadosPr());
                                                            }else{
                                                                deporte.informeResultados(rutaPDF, filtroCaracteres(nombreArchivo), aplazados, getPartidosAplazadosPr());
                                                            }
                                                            break;
                                                        case 2:
                                                            JOptionPane.showMessageDialog(null, "Las eliminatorias no utilizan tabla de clasificación", "Advertencia, selección incorrecta",JOptionPane.WARNING_MESSAGE);
                                                            break;
                                                        case 3:
                                                            deporte.informeCalendario(rutaPDF, filtroCaracteres(nombreArchivo));
                                                            break;
                                                        case 4:
                                                            deporte.informeTodasJornadas(rutaPDF, filtroCaracteres(nombreArchivo));
                                                            break;
                                                        case 5:
                                                            deporte.informeTodosResultados(rutaPDF, filtroCaracteres(nombreArchivo));
                                                            break;
                                                        default:
                                                            deporte.informeJornada(rutaPDF, nombreArchivo, aplazados, getPartidosAplazadosPr());
                                                    }//End switch
                                                   return;//salimos del metodo
                                               }else{
                                                   //Cancelamos la acción de sobreescribir
                                                   fileChooser.cancelSelection();
                                                   return;//salimos del método (si no lo guarda abajo)
                                               }//end if
                                            }//end if comprobacion nombre ficheros
                                        }//end for loop
                                    }else{
                                        JOptionPane.showMessageDialog(null, "No se encontró el directorio seleccionado", "Error al buscar el directorio",JOptionPane.ERROR_MESSAGE);
                                    }//end if
                                    //Comprueba si el archivo existe y  lo elimina
                                    if(archivo.exists()){
                                        archivo.delete();
                                    }
                                    //Creamos el informe que nos han pasado según el número de informe
                                    switch(numeroInforme){
                                        case 0:
                                            if(aplazados){
                                                deporte.informeJornada(rutaPDF, filtroCaracteres(nombreArchivo), aplazados, getPartidosAplazadosPr());
                                            }else{
                                                deporte.informeJornada(rutaPDF, filtroCaracteres(nombreArchivo), aplazados, getPartidosAplazadosPr());
                                            }
                                            break;
                                        case 1:
                                            if(aplazados){
                                                deporte.informeResultados(rutaPDF, filtroCaracteres(nombreArchivo), aplazados, getPartidosAplazadosPr());
                                            }else{
                                                deporte.informeResultados(rutaPDF, filtroCaracteres(nombreArchivo), aplazados, getPartidosAplazadosPr());
                                            }
                                            break;
                                        case 2:
                                                JOptionPane.showMessageDialog(null, "Las eliminatorias no utilizan tabla de clasificación", "Advertencia, selección incorrecta",JOptionPane.WARNING_MESSAGE);
                                            break;
                                        case 3:
                                            deporte.informeCalendario(rutaPDF, nombreArchivo);
                                            break;
                                        case 4:
                                            deporte.informeTodasJornadas(rutaPDF, nombreArchivo);
                                            break;
                                        case 5:
                                            deporte.informeTodosResultados(rutaPDF, nombreArchivo);
                                            break;
                                        default:
                                            deporte.informeJornada(rutaPDF, nombreArchivo, aplazados, getPartidosAplazadosPr());
                                    }//End switch
                                }else{
                                    JOptionPane.showMessageDialog(null, "El archivo debe tener formato PDF", "Advertencia",JOptionPane.WARNING_MESSAGE);
                                }
                            }//end if aceptar JFileChooser
                        }catch (HeadlessException hlex){
                            JOptionPane.showMessageDialog(null, "El archivo no se ha guardado", "Advertencia",JOptionPane.WARNING_MESSAGE);
                        }
                    }//end if numeroInforme == 2
                }else{
                    JOptionPane.showMessageDialog(null, "Error al cargar el torneo, no existe el tipo correspondiente", "Advertencia",JOptionPane.ERROR_MESSAGE);
                }
        }else{//if que comprueba si hay un torneo cargado
            JOptionPane.showMessageDialog(null, "No hay ni ningún torneo cargado todavía", "Advertencia",JOptionPane.WARNING_MESSAGE);
        }               
    }//End of generarInforme()
    
    /**
     * Método que comprueba que los caracteres insertados para generar un archivo son válidos en el SO (son / \ : * " < > |)
     * JFilechooser no deja guardar un archivo con * y guarda los que contienen / y/o \ con el resto de caracteres válidos,
     * el resto los sustituye correctamente
     * @param nombreArchivo
     * @return 
     */
    public String filtroCaracteres(String nombreArchivo){
       
        //Esta es una lista de los caracteres que no permitiremos utilizar en los nombres de archivos
        try{
            String[] NOChars = {"/", "\"", "*", ":", "<", ">", "|", "\\"};
            //Recorremos la cadena en busca de estos caracteres y los sustituimos por _
            for (String NOChar : NOChars) {
                nombreArchivo = nombreArchivo.replace(NOChar, "_");
            }
            return nombreArchivo;
        }catch(Exception e){
            System.out.println(e.getMessage());
            return nombreArchivo;
        }
    }//End of filtroCaracteres()
    
    /**
     * Método que lee el archivo de propiedades para asignar datos o mostrar los datos guardados
     */
    public static void lectorPropiedades(){
        try {
            FileReader reader = new FileReader("propiedades.properties");
            fileProp.load(reader);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//End of lectorPropiedades()
    
    /**
     * Método que guarda en el archivo de propiedades los nuevos datos
     */
    public static void guardarPropiedades(){
        try {
            FileWriter writer = new FileWriter("propiedades.properties");
            fileProp.store(writer, "Archivo de propiedades");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//End of guardarPropiedades()
    
                    /***** MÉTODOS GETTERS AND SETTERS ****/
    
    /**
     * Obtendremos el deporte ya instanciado para ModTablasDialog (static)
     * @return 
     */
    public static Metodos getMetodosDeporte(){
        return deporte;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem AcercaDe;
    private javax.swing.JMenuItem Atribuciones;
    private javax.swing.JButton BotonDcha;
    private javax.swing.JButton BotonIzda;
    private javax.swing.JButton BotonJornada;
    private javax.swing.JButton ButtonClasificacion;
    private javax.swing.JLabel LabelDescansa;
    private javax.swing.JLabel LabelEquipoDescansa;
    private javax.swing.JLabel LabelImagen;
    private javax.swing.JLabel LabelImagenFondo;
    public javax.swing.JLabel LabelJornada;
    private javax.swing.JLabel LabelNombreTorneo;
    private javax.swing.JLabel LabelTotalJornadas;
    private javax.swing.JMenuItem Manual;
    private javax.swing.JMenu MenuArchivo;
    private javax.swing.JMenu MenuAyuda;
    private javax.swing.JMenu MenuInformes;
    private javax.swing.JMenu MenuOpciones;
    private javax.swing.JPanel PanelBotones;
    private javax.swing.JPanel PanelCalendarioDcha;
    private javax.swing.JPanel PanelCalendarioIzda;
    private javax.swing.JPanel PanelCalendarioSuperior;
    private javax.swing.JPanel PanelClasificacionBotones;
    private javax.swing.JPanel PanelClasificacionDcha;
    private javax.swing.JPanel PanelClasificacionIzda;
    private javax.swing.JPanel PanelClasificacionSuperior;
    private javax.swing.JPanel PanelTitulo;
    public javax.swing.JTable TableClasificacion;
    public javax.swing.JTable TableJornada;
    private javax.swing.JTextField TextFieldJornada;
    private javax.swing.JMenuItem abrirTorneo;
    private javax.swing.JMenuItem aplicarSanciones;
    private javax.swing.JMenuItem guardar;
    private javax.swing.JMenuItem informeCalendario;
    private javax.swing.JMenuItem informeClasificacion;
    private javax.swing.JMenuItem informeJornada;
    private javax.swing.JMenuItem informeJornadaAplazados;
    private javax.swing.JMenuItem informeResultados;
    private javax.swing.JMenuItem informeResultadosAplazados;
    private javax.swing.JMenuItem informeTodasJornadas;
    private javax.swing.JMenuItem informeTodosResultados;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JMenuItem nuevoTorneo;
    private javax.swing.JPanel panelCalendario;
    private javax.swing.JPanel panelClasificacion;
    private javax.swing.JTabbedPane pestanas;
    private javax.swing.JMenuItem salir;
    private javax.swing.JMenuItem tablaMod;
    private javax.swing.JMenuItem tablaModClasf;
    // End of variables declaration//GEN-END:variables
    /**
     * @return the partidosAplazados
     */
    public ArrayList<Partido> getPartidosAplazadosPr() {
        return partidosAplazadosPr;
    }

    /**
     * @param partidosAplazadosPr the partidosAplazados to set
     */
    public void setPartidosAplazadosPr(ArrayList<Partido> partidosAplazadosPr) {
        this.partidosAplazadosPr = partidosAplazadosPr;
    }
// End of variables declaration                   

    /**
     * @return the sancionadosPr
     */
    public HashMap<String, Integer> getSancionadosPr() {
        return sancionadosPr;
    }

    /**
     * @param sancionadosPr the sancionadosPr to set
     */
    public void setSancionadosPr(HashMap<String, Integer> sancionadosPr) {
        this.sancionadosPr = sancionadosPr;
    }

    /**
     * Clase que maneja los errores que pueda tener el archivo XML  al contrastar con el Schema XSD
     */
    private static class ParserErrorHandler implements ErrorHandler {

        boolean mensajeSchema;
        
        public ParserErrorHandler(boolean mensajeSchema) {
            this.mensajeSchema = mensajeSchema;
        }

        @Override
        public void warning(SAXParseException saxpe) throws SAXException {
            throw saxpe;//Lanzamos la excepción para que la capture en abrirArchivo()
        }

        @Override
        public void error(SAXParseException saxpe) throws SAXException {       
            throw saxpe;//Lanzamos la excepción para que la capture en abrirArchivo()
        }

        @Override
        public void fatalError(SAXParseException saxpe) throws SAXException {
            throw saxpe;//Lanzamos la excepción para que la capture en abrirArchivo()
        }
    }

}