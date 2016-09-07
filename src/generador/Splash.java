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

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.Timer;


/**
 *
 * @author Alvarielli
 */
public class Splash extends javax.swing.JFrame{

    private Timer timer; //Contador por tiempo para la barra de progreso
    private ActionListener listener;
    private Dimension dim;//Se utiliza para ajustar el tamaño de pantalla
    private Image image = new ImageIcon(getClass().getResource("/imagenes/splash.png")).getImage();
    private int x,y;

    /**
     * Creates new form Splash
     */
    public Splash(){
        //Debemos usar las dimensiones de la imagen y la clase Dimension para que funcione correctamente
        y = image.getHeight(this);
        x = image.getWidth(this);
        dim = new Dimension(x,y);
        setPreferredSize(dim);
        setSize(dim);
        setUndecorated(true);//Oculta la barra de título y el marco
        setLocationRelativeTo(null);
        setVisible(true);
        listener = new ActionListener(){
            //sobreescribimos el método abstracto
            @Override
            public void actionPerformed(ActionEvent event){
                if(barraProgreso.getValue() < 100){
                    barraProgreso.setValue(barraProgreso.getValue()+5);
                }else{
                    timer.stop();
                    setVisible(false);
                }
            }
        };
        timer = new Timer(65, listener);//definimos los parámetros del timer (en milisegundos)
        initComponents();
        /* Además marcamos el JFrame como undecorated (en initComponents se puede ver setUndecorated(true); */
        timer.start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        barraProgreso = new javax.swing.JProgressBar();
        labelFondo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        barraProgreso.setStringPainted(true);
        getContentPane().add(barraProgreso);
        barraProgreso.setBounds(170, 500, 460, 17);

        labelFondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/splash.png"))); // NOI18N
        getContentPane().add(labelFondo);
        labelFondo.setBounds(0, 0, 800, 600);

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(Splash.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Splash.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Splash.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Splash.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Splash().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar barraProgreso;
    private javax.swing.JLabel labelFondo;
    // End of variables declaration//GEN-END:variables


}
