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

import Atxy2k.CustomTextField.RestrictedTextField;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

/**
 *
 * @author Hector
 */
public class Sancionados extends javax.swing.JDialog {
    
    //Variables de clase
    private Map<String, Integer> sancionados = new HashMap<>();
    private Map<String, Integer> sancionadosAux = new HashMap<>();//Si el usuario cancela sancionados queda como estaba
    private DefaultListModel modeloLista = new DefaultListModel();
    private DefaultListModel modeloSancLista = new DefaultListModel();
    private ArrayList<String>listaEquiposTorneo = new ArrayList<>();
    private RestrictedTextField restricted;//restricción de caracteres para el JTextField (tb sólo números)

    //Constructores de clase
    public Sancionados(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }
         
    /**
     * Creates new form Sancionados
     * @param parent
     * @param modal
     * @param listaEquiposTorneo
     * @param sancionados
     */
    public Sancionados(java.awt.Frame parent, boolean modal, ArrayList<String> listaEquiposTorneo, HashMap<String, Integer> sancionados) {
        super(parent, modal);
        initComponents();
        //Código necesario para centrar el diálogo en pantalla
        setLocationRelativeTo(null);
        setTitle(java.util.ResourceBundle.getBundle("generador/Bundle").getString("AÑADIR SANCIONES A LOS PARTICIPANTES"));
        modeloLista.clear();
        modeloSancLista.clear();
        this.sancionados = sancionados;
        this.listaEquiposTorneo = listaEquiposTorneo;
        //Limitamos el número de caracteres que acepta el JTextField y que sólo acepte números
        restricted = new RestrictedTextField(TextFieldNumeroSancion);
        restricted.setLimit(3);
        restricted.setOnlyNums(true);
        TextFieldNumeroSancion.setText("1");
        rellenarSancionadosAux(sancionados, sancionadosAux);
        cargarListas() ;
    }
    
     /**
      * Cierra la pantalla de equipos opuestos
        @param evt      
     */
     private void closeDialog(java.awt.event.WindowEvent evt){
         setVisible(false);
         dispose();
     }//End of dispose()
     
     /**
      * Método que rellena el HashMap auxiliar con los valores del auxiliar o viceversa
      * @param sancionados 
     * @param sancionadosAux 
      */
     public void rellenarSancionadosAux(HashMap<String, Integer> sancionados, Map<String, Integer> sancionadosAux){
         sancionadosAux.clear();
         for (Map.Entry<String, Integer> entry : sancionados.entrySet()) {   
             sancionadosAux.put(entry.getKey(), entry.getValue());
        }//end for HashMap
     }//end of rellenarSancionadosAux()
     
     /**
      * Añade los elementos a la lista de equipos
      */
     public void cargarListas(){
        //Asignar valores negrita, center, este tipos de cosas, decoración
        ListaEquiposMuestra.setBackground(new Color(230,230,230));
        ListaEquiposMuestra.setForeground(new Color(65,65,65));
        ListaEquiposMuestra.setFont(new Font("Tahoma",Font.BOLD,14));
        ListaEquiposSancionados.setBackground(new Color(230,230,230));
        ListaEquiposSancionados.setForeground(new Color(65,65,65));
        ListaEquiposSancionados.setFont(new Font("Tahoma",Font.BOLD,14));
        //Necesario para modificar algunas opciones del JList
        /* CellRenderer = (DefaultListRenderer)ListEquipos.getCellRenderer();
        cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);*/
        //Enviamos los datos a nuestro JList
        ListaEquiposMuestra.setModel(modeloLista);
        ListaEquiposSancionados.setModel(modeloSancLista);
        for (int i = 0; i < listaEquiposTorneo.size(); i++) {
            modeloLista.addElement(listaEquiposTorneo.get(i));
        }
        //Añadimos los sancionados de haberlos
        for (Map.Entry<String, Integer> entry : sancionados.entrySet()) {
           modeloSancLista.addElement(entry.getKey() + " -" + entry.getValue() + " puntos");
        }//end for HashMap
        this.ListaEquiposMuestra.setModel(modeloLista);
        this.ListaEquiposSancionados.setModel(modeloSancLista);
     }//End of cargarListas()
         
      /**
     * Comprueba si el valor seleccionado de la lista (su nombre) coincide con el nombre de un elemento del ArrayList equipos y de ser así lo elimina
     * @param nombre
     * @param participantes
     * @return 
     */
    public boolean borrarValidar(String nombre, ArrayList<String> participantes){
        if(nombre.equalsIgnoreCase("")){
            JOptionPane.showMessageDialog(this, "No se seleccionó ningún texto.", "Error al mover un equipo de lista", JOptionPane.WARNING_MESSAGE, null);
            return false;
        }else{
            //Recorremos el ArrayList con un Iterador para comprobar que el nombre del equipo no está repetido
            Iterator<String> it = participantes.iterator();
            while(it.hasNext()){
               if(it.next().equalsIgnoreCase(nombre)){
                   it.remove();//Elimina el elemento que tiene seleccionado en este momento
                   return true;
               }              
           }//fin while
           JOptionPane.showMessageDialog(this, "No se encontró el participante seleccionado", "Error al mover un equipo de lista", JOptionPane.WARNING_MESSAGE, null);
           return false;
        }
    }//fin validarParticpante()

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ButtonCancelarSancionados = new javax.swing.JButton();
        ButtonAceptarSancionados = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        ListaEquiposMuestra = new javax.swing.JList();
        jScrollPane5 = new javax.swing.JScrollPane();
        ListaEquiposSancionados = new javax.swing.JList();
        ButtonAnadirSancionado = new javax.swing.JButton();
        ButtonQuitarSancionado = new javax.swing.JButton();
        TextFieldNumeroSancion = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        ButtonCancelarSancionados.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("generador/Bundle"); // NOI18N
        ButtonCancelarSancionados.setText(bundle.getString("Sancionados.ButtonCancelarSancionados.text_1")); // NOI18N
        ButtonCancelarSancionados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonCancelarSancionadosActionPerformed(evt);
            }
        });

        ButtonAceptarSancionados.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        ButtonAceptarSancionados.setText(bundle.getString("Sancionados.ButtonAceptarSancionados.text")); // NOI18N
        ButtonAceptarSancionados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonAceptarSancionadosActionPerformed(evt);
            }
        });

        ListaEquiposMuestra.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        ListaEquiposMuestra.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                ListaEquiposMuestraMouseMoved(evt);
            }
        });
        jScrollPane7.setViewportView(ListaEquiposMuestra);

        ListaEquiposSancionados.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        ListaEquiposSancionados.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                ListaEquiposSancionadosMouseMoved(evt);
            }
        });
        jScrollPane5.setViewportView(ListaEquiposSancionados);

        ButtonAnadirSancionado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/plus.png"))); // NOI18N
        ButtonAnadirSancionado.setToolTipText(bundle.getString("Sancionados.ButtonAnadirSancionado.toolTipText")); // NOI18N
        ButtonAnadirSancionado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonAnadirSancionadoActionPerformed(evt);
            }
        });

        ButtonQuitarSancionado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/minus.png"))); // NOI18N
        ButtonQuitarSancionado.setToolTipText(bundle.getString("Sancionados.ButtonQuitarSancionado.toolTipText")); // NOI18N
        ButtonQuitarSancionado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonQuitarSancionadoActionPerformed(evt);
            }
        });

        TextFieldNumeroSancion.setBackground(new java.awt.Color(200, 200, 200));
        TextFieldNumeroSancion.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        TextFieldNumeroSancion.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        TextFieldNumeroSancion.setText(bundle.getString("Sancionados.TextFieldNumeroSancion.text")); // NOI18N
        TextFieldNumeroSancion.setToolTipText("Indique el número de puntos de sanción");
        TextFieldNumeroSancion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TextFieldNumeroSancionKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(223, 223, 223)
                .addComponent(ButtonCancelarSancionados, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 82, Short.MAX_VALUE)
                .addComponent(ButtonAceptarSancionados, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(209, 209, 209))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ButtonAnadirSancionado, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ButtonQuitarSancionado, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TextFieldNumeroSancion, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(52, 52, 52)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addComponent(ButtonAnadirSancionado, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(TextFieldNumeroSancion, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(ButtonQuitarSancionado, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ButtonCancelarSancionados, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ButtonAceptarSancionados, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ButtonCancelarSancionadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonCancelarSancionadosActionPerformed
        //Borramos todos los equipos que habíamos añadido a los distintos ArrayList de Equipos
        rellenarSancionadosAux((HashMap<String, Integer>) sancionadosAux, sancionados);
        listaEquiposTorneo.clear();
        ListaEquiposSancionados.removeAll();
        ListaEquiposMuestra.removeAll();
        this.closeDialog(null);
    }//GEN-LAST:event_ButtonCancelarSancionadosActionPerformed

    private void ButtonAceptarSancionadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonAceptarSancionadosActionPerformed
        ((Principal)getParent()).setSancionadosPr((HashMap<String, Integer>) sancionados);
        //Antes de salir borramos todos los equipos que habíamos añadido al arraylist principal
        listaEquiposTorneo.clear();
        ListaEquiposSancionados.removeAll();
        ListaEquiposMuestra.removeAll();
        this.closeDialog(null);
    }//GEN-LAST:event_ButtonAceptarSancionadosActionPerformed

    private void ListaEquiposSancionadosMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ListaEquiposSancionadosMouseMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_ListaEquiposSancionadosMouseMoved

    private void ButtonAnadirSancionadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonAnadirSancionadoActionPerformed
        int indice = ListaEquiposMuestra.getSelectedIndex();
        int sancion = 0;
        String participante = "";
        if(indice >= 0){
            //si no hay un valor escrito cargamos un 1 por defecto (al menos un punto se querrá restar)
            if(TextFieldNumeroSancion.getText().isEmpty()){
                TextFieldNumeroSancion.setText("1");
            }
            sancion = Integer.parseInt(TextFieldNumeroSancion.getText());//Obtenemos los puntos de sanción
            participante = ListaEquiposMuestra.getSelectedValue().toString();//Guardaremos el nombre del equipo eliminado para añadirlo a la nueva lista
            for (int i = 0; i < modeloSancLista.size(); i++) {
                //Si la lista ya contiene este participante lo borramos y luego lo añadimos con la nueva sanción
                if(modeloSancLista.get(i).toString().contains(participante)){
                    modeloSancLista.remove(i);
                }//end if
            }//end for
            modeloSancLista.addElement(participante + " -" + sancion + " puntos");
            sancionados.put(participante, sancion);
        }else{
            JOptionPane.showMessageDialog(this, "No se ha seleccionado ningún participante.", "Error al seleccionar equipo", JOptionPane.WARNING_MESSAGE, null);
        }//fin if
    }//GEN-LAST:event_ButtonAnadirSancionadoActionPerformed

    private void ButtonQuitarSancionadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonQuitarSancionadoActionPerformed
        int indice = ListaEquiposSancionados.getSelectedIndex();
        String[] auxiliar;
        if(indice >= 0){
            //Convertimos en cadena el Objeto valor pasado (que sabemos que es un nombre)
           String participante = ListaEquiposSancionados.getSelectedValue().toString();
           auxiliar = participante.split("-");
           auxiliar[0] = auxiliar[0].trim();//Hace falta para borrar el espacio que queda antes del guión
           for (int i = 0; i < modeloSancLista.size(); i++) {
                //Si la lista ya contiene este participante lo borramos y luego lo añadimos con la nueva sanción
                if(modeloSancLista.get(i).toString().contains(auxiliar[0])){
                    modeloSancLista.remove(i);
                }//end if
            }//end for
          if(sancionados.containsKey(auxiliar[0])){
            sancionados.remove(auxiliar[0]);//Quitar por clave String
           }
        }else{
            JOptionPane.showMessageDialog(this, "No se ha seleccionado ningún participante.", "Error al seleccionar equipo", JOptionPane.WARNING_MESSAGE, null);
        }//fin if
        //Ahora lo añadimos a la lista de equipos del lado izquierdo (claves)
    }//GEN-LAST:event_ButtonQuitarSancionadoActionPerformed

    private void TextFieldNumeroSancionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextFieldNumeroSancionKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            ButtonAnadirSancionado.doClick();//Si pulsamos enter hacemos que equivalga a puslar el botón de añadir sanción
        }
    }//GEN-LAST:event_TextFieldNumeroSancionKeyPressed

    private void ListaEquiposMuestraMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ListaEquiposMuestraMouseMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_ListaEquiposMuestraMouseMoved

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
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Sancionados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Sancionados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Sancionados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Sancionados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Sancionados dialog = new Sancionados(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ButtonAceptarSancionados;
    private javax.swing.JButton ButtonAnadirSancionado;
    private javax.swing.JButton ButtonCancelarSancionados;
    private javax.swing.JButton ButtonQuitarSancionado;
    private javax.swing.JList ListaEquiposMuestra;
    private javax.swing.JList ListaEquiposSancionados;
    private javax.swing.JTextField TextFieldNumeroSancion;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane7;
    // End of variables declaration//GEN-END:variables
}
