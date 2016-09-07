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

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;


public class EquiposOpuestos extends javax.swing.JDialog {

    //Variables de clase
    private TorneoDialog torneoDialog;//Clase necesaria para acceder a la lista de equipos
    private ArrayList<String>listaEquiposTorneo = new ArrayList<>();
    //Creamos una lista de equipos para manejar los que incluimos
    private DefaultListModel modeloLista = new DefaultListModel();
    private DefaultListModel izquierdaLista = new DefaultListModel();
    private DefaultListModel derechaLista = new DefaultListModel();
    //DefaultCellRenderer cellRenderer;//Se necesita para centrar los componentes del JList
    private ArrayList<String> equiposOpIzq = new ArrayList();//Lista de equipos de la izquierda
    private ArrayList<String> equiposOpDcha = new ArrayList();//Lista de equipos de la derecha
    
    //Constructores de clase
    public EquiposOpuestos(java.awt.Dialog parent, boolean modal) {
        super(parent, modal);
        this.torneoDialog = (TorneoDialog) parent;//Lo convertimos para pasar los datos al TorneoDialog
        initComponents();
        //Código necesario para centrar el diálogo en pantalla
        setLocationRelativeTo(null);
        setTitle(java.util.ResourceBundle.getBundle("generador/Bundle").getString("ELEGIR LOS EQUIPOS QUE SERÁN OPUESTOS EN EL SORTEO"));
        getEquiposOpIzq().clear();//Vaciamos los arraylist de las listas laterales
        getEquiposOpDcha().clear();
        cargarListas();
    }
    
    //Con este contructor podemos utilizar la lista de equipos que recibimos desde TorneoDialog
     public EquiposOpuestos(TorneoDialog parent, boolean modal, ArrayList<String> listaEquiposTorneo) {
        super(parent, modal);
        this.torneoDialog = parent;//Lo convertimos para pasar los datos al TorneoDialog
        initComponents();
        //Código necesario para centrar el diálogo en pantalla
        setLocationRelativeTo(null);
        setTitle(java.util.ResourceBundle.getBundle("generador/Bundle").getString("ELEGIR LOS EQUIPOS QUE SERÁN OPUESTOS EN EL SORTEO"));
        this.listaEquiposTorneo = listaEquiposTorneo;
        getEquiposOpIzq().clear();//Vaciamos los arraylist de las listas laterales
        getEquiposOpDcha().clear();
        cargarListas();
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
      * Añade los elementos a la lista de equipos
      */
     public void cargarListas(){
        //Asignar valores negrita, center, este tipos de cosas, decoración
        ListEquiposEO1.setBackground(new Color(230,230,230));
        ListEquiposEO1.setForeground(new Color(65,65,65));
        ListEquiposEO1.setFont(new Font("Tahoma",Font.BOLD,14));
        ListEquiposEO2.setBackground(new Color(230,230,230));
        ListEquiposEO2.setForeground(new Color(65,65,65));
        ListEquiposEO2.setFont(new Font("Tahoma",Font.BOLD,14));
        ListEquiposEO3.setBackground(new Color(230,230,230));
        ListEquiposEO3.setForeground(new Color(65,65,65));
        ListEquiposEO3.setFont(new Font("Tahoma",Font.BOLD,14));
        //Necesario para modificar algunas opciones del JList
        /* CellRenderer = (DefaultListRenderer)ListEquipos.getCellRenderer();
        cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);*/
        //Enviamos los datos a nuestro JList
        this.ListEquiposEO1.setModel(getModeloLista());
        for (int i = 0; i < getListaEquiposTorneo().size(); i++) {
            getModeloLista().addElement(getListaEquiposTorneo().get(i));
        }
     }//End of cargarListas()
     
     /**
      * Añade el elemento a la lista de Equipos
     * @param participante
      */
     public void refrescarLista(String participante){
        if(participante.equalsIgnoreCase("")){
           //Si el participante está vacío no lo añadimos la JList
         }else{
            getModeloLista().addElement(participante);
            this.ListEquiposEO1.setModel(getModeloLista());
        }
     }//refrescarLista()
     
     /**
      * Añade el elemento a la lista de Equipos
     * @param participante
      */
     public void refrescarListaIzquierda(String participante){
         if(participante.equalsIgnoreCase("")){
           //Si el participante está vacío no lo añadimos la JList
         }else{
            getIzquierdaLista().addElement(participante);
            this.ListEquiposEO2.setModel(getIzquierdaLista());
         }
     }//refrescarListaIzquierda()
     
     /**
      * Añade el elemento a la lista de Equipos
     * @param participante
      */
     public void refrescarListaDerecha(String participante){
        if(participante.equalsIgnoreCase("")){
           //Si el participante está vacío no lo añadimos la JList
        }else{
         getDerechaLista().addElement(participante);
         this.ListEquiposEO3.setModel(getDerechaLista());
        }
     }//refrescarListaDerecha()
     
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

        jScrollPane3 = new javax.swing.JScrollPane();
        ListEquiposEO3 = new javax.swing.JList();
        jScrollPane2 = new javax.swing.JScrollPane();
        ListEquiposEO1 = new javax.swing.JList();
        jScrollPane4 = new javax.swing.JScrollPane();
        ListEquiposEO2 = new javax.swing.JList();
        ButtonAnadirDerecha = new javax.swing.JButton();
        ButtonAnadirIzquierda = new javax.swing.JButton();
        ButtonCancelarEO = new javax.swing.JButton();
        ButtonAceptarEO = new javax.swing.JButton();
        ButtonQuitarDerecha = new javax.swing.JButton();
        ButtonQuitarIzquierda = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        ListEquiposEO3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        ListEquiposEO3.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                ListEquiposEO3MouseMoved(evt);
            }
        });
        jScrollPane3.setViewportView(ListEquiposEO3);

        ListEquiposEO1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("generador/Bundle"); // NOI18N
        ListEquiposEO1.setToolTipText(bundle.getString("EquiposOpuestos.ListEquiposEO1.toolTipText")); // NOI18N
        ListEquiposEO1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                ListEquiposEO1MouseMoved(evt);
            }
        });
        jScrollPane2.setViewportView(ListEquiposEO1);

        ListEquiposEO2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        ListEquiposEO2.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                ListEquiposEO2MouseMoved(evt);
            }
        });
        jScrollPane4.setViewportView(ListEquiposEO2);

        ButtonAnadirDerecha.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/botonright.png"))); // NOI18N
        ButtonAnadirDerecha.setToolTipText(bundle.getString("EquiposOpuestos.ButtonAnadirDerecha.toolTipText")); // NOI18N
        ButtonAnadirDerecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonAnadirDerechaActionPerformed(evt);
            }
        });

        ButtonAnadirIzquierda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/botonleft.png"))); // NOI18N
        ButtonAnadirIzquierda.setToolTipText(bundle.getString("EquiposOpuestos.ButtonAnadirIzquierda.toolTipText")); // NOI18N
        ButtonAnadirIzquierda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonAnadirIzquierdaActionPerformed(evt);
            }
        });

        ButtonCancelarEO.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        ButtonCancelarEO.setText(bundle.getString("EquiposOpuestos.ButtonCancelarEO.text")); // NOI18N
        ButtonCancelarEO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonCancelarEOActionPerformed(evt);
            }
        });

        ButtonAceptarEO.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        ButtonAceptarEO.setText(bundle.getString("EquiposOpuestos.ButtonAceptarEO.text")); // NOI18N
        ButtonAceptarEO.setToolTipText(bundle.getString("EquiposOpuestos.ButtonAceptarEO.toolTipText")); // NOI18N
        ButtonAceptarEO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonAceptarEOActionPerformed(evt);
            }
        });

        ButtonQuitarDerecha.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/botonleft.png"))); // NOI18N
        ButtonQuitarDerecha.setToolTipText(bundle.getString("EquiposOpuestos.ButtonQuitarDerecha.toolTipText")); // NOI18N
        ButtonQuitarDerecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonQuitarDerechaActionPerformed(evt);
            }
        });

        ButtonQuitarIzquierda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/botonright.png"))); // NOI18N
        ButtonQuitarIzquierda.setToolTipText(bundle.getString("EquiposOpuestos.ButtonQuitarIzquierda.toolTipText")); // NOI18N
        ButtonQuitarIzquierda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonQuitarIzquierdaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(ButtonCancelarEO, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ButtonAceptarEO, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ButtonQuitarIzquierda, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ButtonAnadirIzquierda, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(ButtonQuitarDerecha, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ButtonAnadirDerecha, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
                            .addComponent(jScrollPane2)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(71, 71, 71)
                        .addComponent(ButtonAnadirIzquierda, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(ButtonQuitarIzquierda, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(71, 71, 71)
                        .addComponent(ButtonAnadirDerecha, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37)
                        .addComponent(ButtonQuitarDerecha, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ButtonCancelarEO, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ButtonAceptarEO, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ListEquiposEO1MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ListEquiposEO1MouseMoved
        //Evento del ratón que nos permite la posición de cada elemento de la lista de equipos
        //Obtenemos el índice del punto de la lista en el que se encuentra el ratón
        int indice = ListEquiposEO1.locationToIndex(evt.getPoint());
        if (indice >= 0) {
            ListEquiposEO1.setToolTipText(null);
            String text = (String)
            getModeloLista().getElementAt(indice);
            ListEquiposEO1.setToolTipText((indice + 1) + " - " + text);
        }
    }//GEN-LAST:event_ListEquiposEO1MouseMoved

    private void ListEquiposEO2MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ListEquiposEO2MouseMoved
        //Evento del ratón que nos permite la posición de cada elemento de la lista de equipos
        //Obtenemos el índice del punto de la lista en el que se encuentra el ratón
        int indice = ListEquiposEO2.locationToIndex(evt.getPoint());
        if (indice >= 0) {
            ListEquiposEO2.setToolTipText(null);
            String text = (String)
            getIzquierdaLista().getElementAt(indice);
            ListEquiposEO2.setToolTipText((indice + 1) + " - " + text);
        }
    }//GEN-LAST:event_ListEquiposEO2MouseMoved

    private void ButtonAnadirDerechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonAnadirDerechaActionPerformed
       int indice = ListEquiposEO1.getSelectedIndex();
       String auxiliar = "";//Guardaremos el nombre del equipo eliminado para añadirlo a la nueva lista
        if(indice >= 0){
            //Convertimos en cadena el Objeto valor pasado (que sabemos que es un nombre)
            String borrarParticipante = ListEquiposEO1.getSelectedValue().toString();
            auxiliar = borrarParticipante;
            boolean validarBorrar = borrarValidar(borrarParticipante, getListaEquiposTorneo());
            if(validarBorrar){
                getModeloLista().remove(indice);
            }//fin if
            getEquiposOpDcha().add(auxiliar);
        }else{
            JOptionPane.showMessageDialog(this, "No se ha seleccionado ningún participante.", "Error al mover equipo de lista", JOptionPane.WARNING_MESSAGE, null);
        }//fin if
        //Ahora lo añadimos a la lista de equipos del lado izquierdo (claves)
        refrescarListaDerecha(auxiliar);
    }//GEN-LAST:event_ButtonAnadirDerechaActionPerformed

    private void ButtonAnadirIzquierdaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonAnadirIzquierdaActionPerformed
       int indice = ListEquiposEO1.getSelectedIndex();
       String auxiliar = "";//Guardaremos el nombre del equipo eliminado para añadirlo a la nueva lista
        if(indice >= 0){
            //Convertimos en cadena el Objeto valor pasado (que sabemos que es un nombre)
            String borrarParticipante = ListEquiposEO1.getSelectedValue().toString();
            auxiliar = borrarParticipante;
                boolean validarBorrar = borrarValidar(borrarParticipante, getListaEquiposTorneo());
                if(validarBorrar){
                    getModeloLista().remove(indice);
                }//fin if
                getEquiposOpIzq().add(auxiliar);
        }else{
            JOptionPane.showMessageDialog(this, "No se ha seleccionado ningún participante.", "Error al mover equipo de lista", JOptionPane.WARNING_MESSAGE, null);
        }//fin if
        //Ahora lo añadimos a la lista de equipos del lado izquierdo (claves)
        refrescarListaIzquierda(auxiliar);
    }//GEN-LAST:event_ButtonAnadirIzquierdaActionPerformed

    private void ButtonCancelarEOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonCancelarEOActionPerformed
        //Borramos todos los equipos que habíamos añadido a los distintos ArrayList de Equipos
        getListaEquiposTorneo().clear();
        getEquiposOpIzq().clear();
        getEquiposOpDcha().clear();
        this.closeDialog(null);
    }//GEN-LAST:event_ButtonCancelarEOActionPerformed

    private void ButtonAceptarEOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonAceptarEOActionPerformed
        //En primer lugar comprobamos que hay el mismo número de equipos en una lista que en la otra y de ser así los enviamos los datos al TorneoDialog
        if(equiposOpDcha.size() != equiposOpIzq.size()){
            JOptionPane.showMessageDialog(this, "El número de equipos ha de ser el mismo en las listas izquierda y derecha", "Error al asignar equipos opuestos", JOptionPane.WARNING_MESSAGE, null);
        }else{
            torneoDialog.setDerechaEO(equiposOpDcha);
            torneoDialog.setIzquierdaEO(equiposOpIzq);            
            //((TorneoDialog)getParent()).setIzquierdaEO(equiposOpIzq);
            //Antes de salir borramos todos los equipos que habíamos añadido al arraylist principal
            getListaEquiposTorneo().clear();
            this.closeDialog(null);
        }
    }//GEN-LAST:event_ButtonAceptarEOActionPerformed

    private void ListEquiposEO3MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ListEquiposEO3MouseMoved
        //Evento del ratón que nos permite la posición de cada elemento de la lista de equipos
        //Obtenemos el índice del punto de la lista en el que se encuentra el ratón
        int indice = ListEquiposEO3.locationToIndex(evt.getPoint());
        if (indice >= 0) {
            ListEquiposEO3.setToolTipText(null);
            String text = (String)
            getDerechaLista().getElementAt(indice);
            ListEquiposEO3.setToolTipText((indice + 1) + " - " + text);
        }
    }//GEN-LAST:event_ListEquiposEO3MouseMoved

    private void ButtonQuitarDerechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonQuitarDerechaActionPerformed
       int indice = ListEquiposEO3.getSelectedIndex();
       String auxiliar = "";//Guardaremos el nombre del equipo eliminado para añadirlo a la nueva lista
        if(indice >= 0){
            //Convertimos en cadena el Objeto valor pasado (que sabemos que es un nombre)
            String borrarParticipante = ListEquiposEO3.getSelectedValue().toString();
            auxiliar = borrarParticipante;
            boolean validarBorrar = borrarValidar(borrarParticipante, getEquiposOpDcha());
            if(validarBorrar){
                getDerechaLista().remove(indice);
            }//fin if
            getListaEquiposTorneo().add(auxiliar);
        }else{
            JOptionPane.showMessageDialog(this, "No se ha seleccionado ningún participante.", "Error al mover equipo de lista", JOptionPane.WARNING_MESSAGE, null);
        }//fin if
        //Ahora lo añadimos a la lista de equipos del lado izquierdo (claves)
        refrescarLista(auxiliar);
    }//GEN-LAST:event_ButtonQuitarDerechaActionPerformed

    private void ButtonQuitarIzquierdaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonQuitarIzquierdaActionPerformed
       int indice = ListEquiposEO2.getSelectedIndex();
       String auxiliar = "";//Guardaremos el nombre del equipo eliminado para añadirlo a la nueva lista
        if(indice >= 0){
            //Convertimos en cadena el Objeto valor pasado (que sabemos que es un nombre)
            String borrarParticipante = ListEquiposEO2.getSelectedValue().toString();
            auxiliar = borrarParticipante;
            boolean validarBorrar = borrarValidar(borrarParticipante, getEquiposOpIzq());
            if(validarBorrar){
                getIzquierdaLista().remove(indice);
            }//fin if
            getListaEquiposTorneo().add(auxiliar);
        }else{
            JOptionPane.showMessageDialog(this, "No se ha seleccionado ningún participante.", "Error al mover equipo de lista", JOptionPane.WARNING_MESSAGE, null);
        }//fin if
        //Ahora lo añadimos a la lista de equipos del lado izquierdo (claves)
        refrescarLista(auxiliar);
    }//GEN-LAST:event_ButtonQuitarIzquierdaActionPerformed

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
            java.util.logging.Logger.getLogger(EquiposOpuestos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EquiposOpuestos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EquiposOpuestos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EquiposOpuestos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                EquiposOpuestos dialog = new EquiposOpuestos(new javax.swing.JDialog(), true);
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
    private javax.swing.JButton ButtonAceptarEO;
    private javax.swing.JButton ButtonAnadirDerecha;
    private javax.swing.JButton ButtonAnadirIzquierda;
    private javax.swing.JButton ButtonCancelarEO;
    private javax.swing.JButton ButtonQuitarDerecha;
    private javax.swing.JButton ButtonQuitarIzquierda;
    private javax.swing.JList ListEquiposEO1;
    private javax.swing.JList ListEquiposEO2;
    private javax.swing.JList ListEquiposEO3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    // End of variables declaration//GEN-END:variables

    /** MÉTODOS GETTERS AND SETTERS **/ 
    
    /**
     * @return the torneoDialog
     */
    public TorneoDialog getTorneoDialog() {
        return torneoDialog;
    }

    /**
     * @param torneoDialog the torneoDialog to set
     */
    public void setTorneoDialog(TorneoDialog torneoDialog) {
        this.torneoDialog = torneoDialog;
    }

    /**
     * @return the listaEquiposTorneo
     */
    public ArrayList<String> getListaEquiposTorneo() {
        return listaEquiposTorneo;
    }

    /**
     * @param listaEquiposTorneo the listaEquiposTorneo to set
     */
    public void setListaEquiposTorneo(ArrayList<String> listaEquiposTorneo) {
        this.listaEquiposTorneo = listaEquiposTorneo;
    }

    /**
     * @return the modeloLista
     */
    public DefaultListModel getModeloLista() {
        return modeloLista;
    }

    /**
     * @param modeloLista the modeloLista to set
     */
    public void setModeloLista(DefaultListModel modeloLista) {
        this.modeloLista = modeloLista;
    }

    /**
     * @return the izquierdaLista
     */
    public DefaultListModel getIzquierdaLista() {
        return izquierdaLista;
    }

    /**
     * @param izquierdaLista the izquierdaLista to set
     */
    public void setIzquierdaLista(DefaultListModel izquierdaLista) {
        this.izquierdaLista = izquierdaLista;
    }

    /**
     * @return the derechaLista
     */
    public DefaultListModel getDerechaLista() {
        return derechaLista;
    }

    /**
     * @param derechaLista the derechaLista to set
     */
    public void setDerechaLista(DefaultListModel derechaLista) {
        this.derechaLista = derechaLista;
    }

    /**
     * @return the equiposOpIzq
     */
    public ArrayList<String> getEquiposOpIzq() {
        return equiposOpIzq;
    }

    /**
     * @param equiposOpIzq the equiposOpIzq to set
     */
    public void setEquiposOpIzq(ArrayList<String> equiposOpIzq) {
        this.equiposOpIzq = equiposOpIzq;
    }

    /**
     * @return the equiposOpDcha
     */
    public ArrayList<String> getEquiposOpDcha() {
        return equiposOpDcha;
    }

    /**
     * @param equiposOpDcha the equiposOpDcha to set
     */
    public void setEquiposOpDcha(ArrayList<String> equiposOpDcha) {
        this.equiposOpDcha = equiposOpDcha;
    }
}
