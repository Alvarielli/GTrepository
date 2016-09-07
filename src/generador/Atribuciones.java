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

import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

public class Atribuciones extends javax.swing.JDialog {

    ArrayList<JLabel> LabelImg = new ArrayList<>();
    ArrayList<JLabel> LabelAuthor = new ArrayList<>();
    ArrayList<JLabel> LabelLicense = new ArrayList<>();
    
    String[] imagenes = {"http://sportsbettingspot.com/olympico/", "http://www.iconarchive.com/show/new-zealand-icons-by-afterglow/Rugby-Ball-icon.html", "https://commons.wikimedia.org/wiki/File:Hockey_Stick_and_Puck.png", "https://www.iconfinder.com/icons/7159/board_game_chess_icon#size=128", "https://www.flickr.com/photos/seeminglee/8650764769/sizes/h/in/photostream/", "https://www.flickr.com/photos/viviannguyen/9035069817"
    , "https://www.flickr.com/photos/fortwainwright/8186877574", "https://www.flickr.com/photos/krb_events/13370719784", "https://www.flickr.com/photos/leandrociuffo/9279923456", "https://es.wikipedia.org/wiki/Campo_de_V%C3%B3ley_Playa_del_Parque_Chaoyang#mediaviewer/Archivo:Beach_Volleyball_Ground_2008_Olympics.jpg", "https://en.wikipedia.org/wiki/USTA_Billie_Jean_King_National_Tennis_Center#mediaviewer/File:National_Tennis_Center_outside_courts_and_stadium.jpg"
    , "https://www.flickr.com/photos/larrabetzutik/9045436026", "https://es.wikipedia.org/wiki/P%C3%A1del#mediaviewer/Archivo:Pala_de_padel.jpg", "https://www.flickr.com/photos/garyjd/8525229409", "https://www.flickr.com/photos/wikier/2427031297", "https://www.flickr.com/photos/vinqui/5836944966", "https://www.flickr.com/photos/jhderojas/516609552", "https://www.flickr.com/photos/kk/4371794179"
    , "https://www.flickr.com/photos/jikatu/6843622641/", "https://www.flickr.com/photos/krisandapril/51403649", "https://www.flickr.com/photos/55773111@N03/14300514316/in/photolist-nMFSFE-eSBdyP-9gcapv-eSBaQT-9gcp9c-9gbYeg-eSNBhW-eSNGp5-9gbMLn-eSNyLs-eSBcsg-eSBd8g-eSBbiD-eSNBvY-eSNFD9-9gcmvB-9gcDz4-9gcrDP-9gc9R2-eLvz89-eSNGum-9gcqP4-9gciDZ-9gcwcH-eLvzk7-9gf4mG-eSBbF4-nRdbvy-eSNyqN-9gbRXP-eLvxkN-9gcCLB-9gc7yk-eSBcKK-eSB7Pi-9gcc6R-eSB9ng-9gc3VV-9gbZWM-eSNAxd-eSNDC3-eSNAn1-eSBiDB-9gfqFj-eSNxom-9gbSUz-eSBbeD-eSNyd9-9gcALi-9gc96n"
            , "https://www.flickr.com/photos/gpoo/8644021864", "https://www.flickr.com/photos/klemencic/14995253585", "https://www.flickr.com/photos/antonystanley/7758890090"};
    
    String[] autores = {"http://sportsbettingspot.com/", "http://www.afterglow.ie/", "https://en.wikipedia.org/wiki/User:Jecowa", "https://www.kde-look.org/usermanager/search.php?username=Sephiroth6779", "https://www.flickr.com/photos/seeminglee/", "https://www.flickr.com/photos/viviannguyen/", "https://www.flickr.com/photos/fortwainwright/", "https://www.flickr.com/photos/krb_events/", "https://www.flickr.com/photos/leandrociuffo/"
    , "https://www.flickr.com/people/9017514@N05", "https://commons.wikimedia.org/w/index.php?title=User:Darylsam&action=edit&redlink=1", "https://www.flickr.com/photos/larrabetzutik/", "https://commons.wikimedia.org/w/index.php?title=User:Imageuser&action=edit&redlink=1", "https://www.flickr.com/photos/garyjd/", "https://www.flickr.com/photos/wikier/", "https://www.flickr.com/photos/vinqui/", "https://www.flickr.com/photos/jhderojas/"
    , "https://www.flickr.com/photos/kk/", "https://www.flickr.com/photos/jikatu/", "https://www.flickr.com/photos/krisandapril/", "https://www.flickr.com/photos/55773111@N03/", "https://www.flickr.com/photos/gpoo/", "https://www.flickr.com/photos/klemencic/", "https://www.flickr.com/photos/antonystanley/"};
    
    
    String[] licencias = {"https://creativecommons.org/licenses/by/3.0/", "http://www.iconarchive.com/show/new-zealand-icons-by-afterglow/Rugby-Ball-icon.html", "https://creativecommons.org/licenses/by-sa/3.0/", "http://www.gnu.org/copyleft/gpl.html", "https://creativecommons.org/licenses/by/2.0/", "https://creativecommons.org/licenses/by-sa/2.0/", "https://creativecommons.org/licenses/by-sa/2.0/", "https://creativecommons.org/licenses/by-sa/2.0/", "https://creativecommons.org/licenses/by/2.0/" 
    , "https://creativecommons.org/licenses/by/2.0/", "http://www.gnu.org/copyleft/fdl.html", "https://creativecommons.org/licenses/by/2.0/", "https://creativecommons.org/licenses/by/3.0/", "https://creativecommons.org/licenses/by-sa/2.0/", "https://creativecommons.org/licenses/by-sa/2.0/", "https://creativecommons.org/licenses/by-sa/2.0/", "https://creativecommons.org/licenses/by-sa/2.0/", "https://creativecommons.org/licenses/by/2.0/", "https://creativecommons.org/licenses/by/2.0/"
    , "https://creativecommons.org/licenses/by/2.0/", "https://creativecommons.org/licenses/by-sa/2.0/", "https://creativecommons.org/licenses/by-sa/2.0/", "https://creativecommons.org/licenses/by-sa/2.0/", "https://creativecommons.org/licenses/by-sa/2.0/", "https://creativecommons.org/licenses/by-sa/2.0/", "https://creativecommons.org/licenses/by-sa/2.0/"};
    
    /**
     * Creates new form Atribuciones
     * @param parent
     * @param modal
     */
    public Atribuciones(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);//Centra el JDialog
        setTitle(java.util.ResourceBundle.getBundle("generador/Bundle").getString("ACERCA DE GENERADOR DE TORNEOS"));
        /*JScrollPane scrollPane = new JScrollPane(PanelContenedor);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(50, 30, 300, 50);
        PanelContenedor.add(scrollPane);*/
        
        //Añadimos las etiquetas de Img al ArrayList
        LabelImg.add(LabelAttributionImg1);
        LabelImg.add(LabelAttributionImg2);
        LabelImg.add(LabelAttributionImg3);
        LabelImg.add(LabelAttributionImg4);
        LabelImg.add(LabelAttributionImg5);
        LabelImg.add(LabelAttributionImg6);
        LabelImg.add(LabelAttributionImg7);
        LabelImg.add(LabelAttributionImg8);
        LabelImg.add(LabelAttributionImg9);
        LabelImg.add(LabelAttributionImg10);
        LabelImg.add(LabelAttributionImg11);
        LabelImg.add(LabelAttributionImg12);
        LabelImg.add(LabelAttributionImg13);
        LabelImg.add(LabelAttributionImg14);
        LabelImg.add(LabelAttributionImg15);
        LabelImg.add(LabelAttributionImg16);
        LabelImg.add(LabelAttributionImg17);
        LabelImg.add(LabelAttributionImg18);
        LabelImg.add(LabelAttributionImg19);
        LabelImg.add(LabelAttributionImg20);
        LabelImg.add(LabelAttributionImg21);
        LabelImg.add(LabelAttributionImg22);
        LabelImg.add(LabelAttributionImg23);
        LabelImg.add(LabelAttributionImg24);
        
        //Añadimos las etiquetas de Author al ArrayList
        LabelAuthor.add(LabelAttributionAuthor1);
        LabelAuthor.add(LabelAttributionAuthor2);
        LabelAuthor.add(LabelAttributionAuthor3);
        LabelAuthor.add(LabelAttributionAuthor4);
        LabelAuthor.add(LabelAttributionAuthor5);
        LabelAuthor.add(LabelAttributionAuthor6);
        LabelAuthor.add(LabelAttributionAuthor7);
        LabelAuthor.add(LabelAttributionAuthor8);
        LabelAuthor.add(LabelAttributionAuthor9);
        LabelAuthor.add(LabelAttributionAuthor10);
        LabelAuthor.add(LabelAttributionAuthor11);
        LabelAuthor.add(LabelAttributionAuthor12);
        LabelAuthor.add(LabelAttributionAuthor13);
        LabelAuthor.add(LabelAttributionAuthor14);
        LabelAuthor.add(LabelAttributionAuthor15);
        LabelAuthor.add(LabelAttributionAuthor16);
        LabelAuthor.add(LabelAttributionAuthor17);
        LabelAuthor.add(LabelAttributionAuthor18);
        LabelAuthor.add(LabelAttributionAuthor19);
        LabelAuthor.add(LabelAttributionAuthor20);
        LabelAuthor.add(LabelAttributionAuthor21);
        LabelAuthor.add(LabelAttributionAuthor22);
        LabelAuthor.add(LabelAttributionAuthor23);
        LabelAuthor.add(LabelAttributionAuthor24);
        
        //Añadimos las etiquetas de License al ArrayList
        LabelLicense.add(LabelAttributionLicense1);
        LabelLicense.add(LabelAttributionLicense2);
        LabelLicense.add(LabelAttributionLicense3);
        LabelLicense.add(LabelAttributionLicense4);
        LabelLicense.add(LabelAttributionLicense5);
        LabelLicense.add(LabelAttributionLicense6);
        LabelLicense.add(LabelAttributionLicense7);
        LabelLicense.add(LabelAttributionLicense8);
        LabelLicense.add(LabelAttributionLicense9);
        LabelLicense.add(LabelAttributionLicense10);
        LabelLicense.add(LabelAttributionLicense11);
        LabelLicense.add(LabelAttributionLicense12);
        LabelLicense.add(LabelAttributionLicense13);
        LabelLicense.add(LabelAttributionLicense14);
        LabelLicense.add(LabelAttributionLicense15);
        LabelLicense.add(LabelAttributionLicense16);
        LabelLicense.add(LabelAttributionLicense17);
        LabelLicense.add(LabelAttributionLicense18);
        LabelLicense.add(LabelAttributionLicense19);
        LabelLicense.add(LabelAttributionLicense20);
        LabelLicense.add(LabelAttributionLicense21);
        LabelLicense.add(LabelAttributionLicense22);
        LabelLicense.add(LabelAttributionLicense23);
        LabelLicense.add(LabelAttributionLicense24);
        
        LabelAttributionImg1.setCursor(new Cursor(Cursor.HAND_CURSOR));//Ponemos que salga la mano en la etiqueta del enlace
        LabelAttributionAuthor1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        LabelAttributionLicense1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        LabelAttributionImg2.setCursor(new Cursor(Cursor.HAND_CURSOR));
        LabelAttributionAuthor2.setCursor(new Cursor(Cursor.HAND_CURSOR));
        LabelAttributionLicense2.setCursor(new Cursor(Cursor.HAND_CURSOR));
        LabelAttributionImg3.setCursor(new Cursor(Cursor.HAND_CURSOR));
        LabelAttributionAuthor3.setCursor(new Cursor(Cursor.HAND_CURSOR));
        LabelAttributionLicense3.setCursor(new Cursor(Cursor.HAND_CURSOR));
        LabelAttributionImg4.setCursor(new Cursor(Cursor.HAND_CURSOR));
        LabelAttributionAuthor4.setCursor(new Cursor(Cursor.HAND_CURSOR));
        LabelAttributionLicense4.setCursor(new Cursor(Cursor.HAND_CURSOR));   
        LabelAttributionImg5.setCursor(new Cursor(Cursor.HAND_CURSOR));
        LabelAttributionAuthor5.setCursor(new Cursor(Cursor.HAND_CURSOR));
        LabelAttributionLicense5.setCursor(new Cursor(Cursor.HAND_CURSOR));
        LabelAttributionImg6.setCursor(new Cursor(Cursor.HAND_CURSOR));
        LabelAttributionAuthor6.setCursor(new Cursor(Cursor.HAND_CURSOR));
        LabelAttributionLicense6.setCursor(new Cursor(Cursor.HAND_CURSOR));
        LabelAttributionImg7.setCursor(new Cursor(Cursor.HAND_CURSOR));//Ponemos que salga la mano en la etiqueta del enlace
        LabelAttributionAuthor7.setCursor(new Cursor(Cursor.HAND_CURSOR));
        LabelAttributionLicense7.setCursor(new Cursor(Cursor.HAND_CURSOR));
        LabelAttributionImg8.setCursor(new Cursor(Cursor.HAND_CURSOR));
        LabelAttributionAuthor8.setCursor(new Cursor(Cursor.HAND_CURSOR));
        LabelAttributionLicense8.setCursor(new Cursor(Cursor.HAND_CURSOR));
        LabelAttributionImg9.setCursor(new Cursor(Cursor.HAND_CURSOR));
        LabelAttributionAuthor9.setCursor(new Cursor(Cursor.HAND_CURSOR));
        LabelAttributionLicense9.setCursor(new Cursor(Cursor.HAND_CURSOR));
        LabelAttributionImg10.setCursor(new Cursor(Cursor.HAND_CURSOR));
        LabelAttributionAuthor10.setCursor(new Cursor(Cursor.HAND_CURSOR));
        LabelAttributionLicense10.setCursor(new Cursor(Cursor.HAND_CURSOR));   
        LabelAttributionImg11.setCursor(new Cursor(Cursor.HAND_CURSOR));
        LabelAttributionAuthor11.setCursor(new Cursor(Cursor.HAND_CURSOR));
        LabelAttributionLicense11.setCursor(new Cursor(Cursor.HAND_CURSOR));
        LabelAttributionImg12.setCursor(new Cursor(Cursor.HAND_CURSOR));
        LabelAttributionAuthor12.setCursor(new Cursor(Cursor.HAND_CURSOR));
        LabelAttributionLicense12.setCursor(new Cursor(Cursor.HAND_CURSOR));
         LabelAttributionImg13.setCursor(new Cursor(Cursor.HAND_CURSOR));//Ponemos que salga la mano en la etiqueta del enlace
        LabelAttributionAuthor13.setCursor(new Cursor(Cursor.HAND_CURSOR));
        LabelAttributionLicense13.setCursor(new Cursor(Cursor.HAND_CURSOR));
        LabelAttributionImg14.setCursor(new Cursor(Cursor.HAND_CURSOR));
        LabelAttributionAuthor14.setCursor(new Cursor(Cursor.HAND_CURSOR));
        LabelAttributionLicense14.setCursor(new Cursor(Cursor.HAND_CURSOR));
        LabelAttributionImg15.setCursor(new Cursor(Cursor.HAND_CURSOR));
        LabelAttributionAuthor15.setCursor(new Cursor(Cursor.HAND_CURSOR));
        LabelAttributionLicense15.setCursor(new Cursor(Cursor.HAND_CURSOR));
        LabelAttributionImg16.setCursor(new Cursor(Cursor.HAND_CURSOR));
        LabelAttributionAuthor16.setCursor(new Cursor(Cursor.HAND_CURSOR));
        LabelAttributionLicense16.setCursor(new Cursor(Cursor.HAND_CURSOR));   
        LabelAttributionImg17.setCursor(new Cursor(Cursor.HAND_CURSOR));
        LabelAttributionAuthor17.setCursor(new Cursor(Cursor.HAND_CURSOR));
        LabelAttributionLicense17.setCursor(new Cursor(Cursor.HAND_CURSOR));
        LabelAttributionImg18.setCursor(new Cursor(Cursor.HAND_CURSOR));
        LabelAttributionAuthor18.setCursor(new Cursor(Cursor.HAND_CURSOR));
        LabelAttributionLicense18.setCursor(new Cursor(Cursor.HAND_CURSOR));
         LabelAttributionImg19.setCursor(new Cursor(Cursor.HAND_CURSOR));//Ponemos que salga la mano en la etiqueta del enlace
        LabelAttributionAuthor19.setCursor(new Cursor(Cursor.HAND_CURSOR));
        LabelAttributionLicense19.setCursor(new Cursor(Cursor.HAND_CURSOR));
        LabelAttributionImg20.setCursor(new Cursor(Cursor.HAND_CURSOR));
        LabelAttributionAuthor20.setCursor(new Cursor(Cursor.HAND_CURSOR));
        LabelAttributionLicense20.setCursor(new Cursor(Cursor.HAND_CURSOR));
        LabelAttributionImg21.setCursor(new Cursor(Cursor.HAND_CURSOR));
        LabelAttributionAuthor21.setCursor(new Cursor(Cursor.HAND_CURSOR));
        LabelAttributionLicense21.setCursor(new Cursor(Cursor.HAND_CURSOR));
        LabelAttributionImg22.setCursor(new Cursor(Cursor.HAND_CURSOR));
        LabelAttributionAuthor22.setCursor(new Cursor(Cursor.HAND_CURSOR));
        LabelAttributionLicense22.setCursor(new Cursor(Cursor.HAND_CURSOR));   
        LabelAttributionImg23.setCursor(new Cursor(Cursor.HAND_CURSOR));
        LabelAttributionAuthor23.setCursor(new Cursor(Cursor.HAND_CURSOR));
        LabelAttributionLicense23.setCursor(new Cursor(Cursor.HAND_CURSOR));
        LabelAttributionImg24.setCursor(new Cursor(Cursor.HAND_CURSOR));
        LabelAttributionAuthor24.setCursor(new Cursor(Cursor.HAND_CURSOR));
        LabelAttributionLicense24.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        //Recorremos los arrays y asignamos los enlaces correspondientes
        for (int i = 0; i < imagenes.length; i++) {
            irEnlace(LabelImg.get(i) ,imagenes[i]);
            irEnlace(LabelAuthor.get(i), autores[i]);
            irEnlace(LabelLicense.get(i), licencias[i]);
        }      
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        PanelContenedor = new javax.swing.JPanel();
        LabelAttributionImg6 = new javax.swing.JLabel();
        LabelAttributionBy6 = new javax.swing.JLabel();
        LabelAttributionMod22 = new javax.swing.JLabel();
        LabelAttributionImg23 = new javax.swing.JLabel();
        LabelAttributionBy23 = new javax.swing.JLabel();
        LabelAttributionAuthor23 = new javax.swing.JLabel();
        LabelAttributionUnder23 = new javax.swing.JLabel();
        LabelAttributionLicense23 = new javax.swing.JLabel();
        LabelAttributionMod23 = new javax.swing.JLabel();
        LabelAttributionImg24 = new javax.swing.JLabel();
        LabelAttributionAuthor10 = new javax.swing.JLabel();
        LabelAttributionBy24 = new javax.swing.JLabel();
        LabelAttributionUnder10 = new javax.swing.JLabel();
        LabelAttributionAuthor24 = new javax.swing.JLabel();
        LabelAttributionLicense10 = new javax.swing.JLabel();
        LabelAttributionImg11 = new javax.swing.JLabel();
        LabelAttributionBy11 = new javax.swing.JLabel();
        LabelAttributionAuthor11 = new javax.swing.JLabel();
        LabelAttributionUnder11 = new javax.swing.JLabel();
        LabelAttributionAuthor6 = new javax.swing.JLabel();
        LabelAttributionLicense11 = new javax.swing.JLabel();
        LabelAttributionUnder6 = new javax.swing.JLabel();
        LabelAttributionImg12 = new javax.swing.JLabel();
        LabelAttributionLicense6 = new javax.swing.JLabel();
        LabelAttributionBy12 = new javax.swing.JLabel();
        LabelAttributionImg7 = new javax.swing.JLabel();
        LabelAttributionBy7 = new javax.swing.JLabel();
        LabelAttributionAuthor7 = new javax.swing.JLabel();
        LabelAttributionUnder7 = new javax.swing.JLabel();
        LabelAttributionLicense7 = new javax.swing.JLabel();
        LabelAttributionImg8 = new javax.swing.JLabel();
        LabelAttributionBy8 = new javax.swing.JLabel();
        LabelAttributionAuthor12 = new javax.swing.JLabel();
        LabelAttributionUnder12 = new javax.swing.JLabel();
        LabelAttributionLicense12 = new javax.swing.JLabel();
        LabelAttributionImg13 = new javax.swing.JLabel();
        LabelAttributionBy13 = new javax.swing.JLabel();
        LabelAttributionAuthor13 = new javax.swing.JLabel();
        LabelAttributionUnder13 = new javax.swing.JLabel();
        LabelAttributionLicense13 = new javax.swing.JLabel();
        LabelAttributionMod5 = new javax.swing.JLabel();
        LabelAttributionMod6 = new javax.swing.JLabel();
        LabelAttributionMod7 = new javax.swing.JLabel();
        LabelAttributionMod8 = new javax.swing.JLabel();
        LabelAttributionMod9 = new javax.swing.JLabel();
        LabelAttributionMod10 = new javax.swing.JLabel();
        LabelAttributionMod11 = new javax.swing.JLabel();
        LabelAttributionMod12 = new javax.swing.JLabel();
        LabelAttributionMod13 = new javax.swing.JLabel();
        LabelAttributionImg14 = new javax.swing.JLabel();
        LabelAttributionBy14 = new javax.swing.JLabel();
        LabelAttributionAuthor14 = new javax.swing.JLabel();
        LabelAttributionUnder14 = new javax.swing.JLabel();
        LabelAttributionLicense14 = new javax.swing.JLabel();
        LabelAttributionMod14 = new javax.swing.JLabel();
        LabelAttributionImg15 = new javax.swing.JLabel();
        LabelAttributionBy15 = new javax.swing.JLabel();
        LabelAttributionAuthor15 = new javax.swing.JLabel();
        LabelAttributionUnder15 = new javax.swing.JLabel();
        LabelAttributionLicense15 = new javax.swing.JLabel();
        LabelAttributionMod15 = new javax.swing.JLabel();
        LabelAttributionImg16 = new javax.swing.JLabel();
        LabelAttributionBy16 = new javax.swing.JLabel();
        LabelAttributionAuthor16 = new javax.swing.JLabel();
        LabelAttributionUnder16 = new javax.swing.JLabel();
        LabelAttributionLicense16 = new javax.swing.JLabel();
        LabelAttributionMod16 = new javax.swing.JLabel();
        LabelAttributionImg17 = new javax.swing.JLabel();
        LabelAttributionBy17 = new javax.swing.JLabel();
        LabelAttributionAuthor17 = new javax.swing.JLabel();
        LabelAttributionUnder17 = new javax.swing.JLabel();
        LabelAttributionLicense17 = new javax.swing.JLabel();
        LabelAttributionMod17 = new javax.swing.JLabel();
        LabelAttributionImg18 = new javax.swing.JLabel();
        LabelAttributionBy18 = new javax.swing.JLabel();
        LabelAttributionAuthor18 = new javax.swing.JLabel();
        LabelAttributionUnder18 = new javax.swing.JLabel();
        LabelAttributionLicense18 = new javax.swing.JLabel();
        LabelAttributionMod18 = new javax.swing.JLabel();
        LabelAttributionImg19 = new javax.swing.JLabel();
        LabelAttributionUnder24 = new javax.swing.JLabel();
        LabelAttributionBy19 = new javax.swing.JLabel();
        LabelAttributionLicense24 = new javax.swing.JLabel();
        LabelAttributionAuthor19 = new javax.swing.JLabel();
        LabelAttributionMod24 = new javax.swing.JLabel();
        LabelIconos = new javax.swing.JLabel();
        LabelAttributionImg1 = new javax.swing.JLabel();
        LabelAttributionAuthor1 = new javax.swing.JLabel();
        LabelAttributionBy1 = new javax.swing.JLabel();
        LabelAttributionUnder1 = new javax.swing.JLabel();
        LabelAttributionLicense1 = new javax.swing.JLabel();
        LabelAttributionImg2 = new javax.swing.JLabel();
        LabelAttributionBy2 = new javax.swing.JLabel();
        LabelAttributionAuthor2 = new javax.swing.JLabel();
        LabelAttributionUnder19 = new javax.swing.JLabel();
        LabelAttributionLicense19 = new javax.swing.JLabel();
        LabelAttributionMod19 = new javax.swing.JLabel();
        LabelAttributionImg20 = new javax.swing.JLabel();
        LabelAttributionBy20 = new javax.swing.JLabel();
        LabelAttributionAuthor20 = new javax.swing.JLabel();
        LabelAttributionUnder20 = new javax.swing.JLabel();
        LabelAttributionLicense20 = new javax.swing.JLabel();
        LabelAttributionMod20 = new javax.swing.JLabel();
        LabelAttributionImg21 = new javax.swing.JLabel();
        LabelAttributionUnder2 = new javax.swing.JLabel();
        LabelAttributionLicense2 = new javax.swing.JLabel();
        LabelAttributionImg3 = new javax.swing.JLabel();
        LabelAttributionBy3 = new javax.swing.JLabel();
        LabelAttributionAuthor3 = new javax.swing.JLabel();
        LabelAttributionUnder3 = new javax.swing.JLabel();
        LabelAttributionLicense3 = new javax.swing.JLabel();
        LabelAttributionImg4 = new javax.swing.JLabel();
        LabelAttributionBy4 = new javax.swing.JLabel();
        LabelAttributionAuthor4 = new javax.swing.JLabel();
        LabelAttributionBy21 = new javax.swing.JLabel();
        LabelAttributionAuthor21 = new javax.swing.JLabel();
        LabelAttributionUnder21 = new javax.swing.JLabel();
        LabelAttributionLicense21 = new javax.swing.JLabel();
        LabelAttributionMod21 = new javax.swing.JLabel();
        LabelAttributionImg22 = new javax.swing.JLabel();
        LabelAttributionBy22 = new javax.swing.JLabel();
        LabelAttributionAuthor22 = new javax.swing.JLabel();
        LabelAttributionAuthor8 = new javax.swing.JLabel();
        LabelAttributionUnder22 = new javax.swing.JLabel();
        LabelAttributionUnder8 = new javax.swing.JLabel();
        LabelAttributionLicense22 = new javax.swing.JLabel();
        LabelAttributionLicense8 = new javax.swing.JLabel();
        LabelAttributionImg9 = new javax.swing.JLabel();
        LabelAttributionBy9 = new javax.swing.JLabel();
        LabelAttributionAuthor9 = new javax.swing.JLabel();
        LabelAttributionUnder9 = new javax.swing.JLabel();
        LabelAttributionUnder4 = new javax.swing.JLabel();
        LabelAttributionLicense9 = new javax.swing.JLabel();
        LabelAttributionLicense4 = new javax.swing.JLabel();
        LabelAttributionImg10 = new javax.swing.JLabel();
        LabelFondos = new javax.swing.JLabel();
        LabelAttributionBy10 = new javax.swing.JLabel();
        LabelAttributionImg5 = new javax.swing.JLabel();
        LabelAttributionBy5 = new javax.swing.JLabel();
        LabelAttributionAuthor5 = new javax.swing.JLabel();
        LabelAttributionUnder5 = new javax.swing.JLabel();
        LabelAttributionLicense5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        LabelAttributionImg6.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionImg6.setForeground(new java.awt.Color(0, 0, 255));
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("generador/Bundle"); // NOI18N
        LabelAttributionImg6.setText(bundle.getString("Atribuciones.LabelAttributionImg6.text")); // NOI18N

        LabelAttributionBy6.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionBy6.setText(bundle.getString("Atribuciones.LabelAttributionBy6.text")); // NOI18N

        LabelAttributionMod22.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionMod22.setText(bundle.getString("Atribuciones.LabelAttributionMod22.text")); // NOI18N

        LabelAttributionImg23.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionImg23.setForeground(new java.awt.Color(0, 0, 255));
        LabelAttributionImg23.setText(bundle.getString("Atribuciones.LabelAttributionImg23.text")); // NOI18N

        LabelAttributionBy23.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionBy23.setText(bundle.getString("Atribuciones.LabelAttributionBy23.text")); // NOI18N

        LabelAttributionAuthor23.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionAuthor23.setForeground(new java.awt.Color(0, 0, 255));
        LabelAttributionAuthor23.setText(bundle.getString("Atribuciones.LabelAttributionAuthor23.text")); // NOI18N

        LabelAttributionUnder23.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionUnder23.setText(bundle.getString("Atribuciones.LabelAttributionUnder23.text")); // NOI18N

        LabelAttributionLicense23.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionLicense23.setForeground(new java.awt.Color(0, 0, 255));
        LabelAttributionLicense23.setText(bundle.getString("Atribuciones.LabelAttributionLicense23.text")); // NOI18N

        LabelAttributionMod23.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionMod23.setText(bundle.getString("Atribuciones.LabelAttributionMod23.text")); // NOI18N

        LabelAttributionImg24.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionImg24.setForeground(new java.awt.Color(0, 0, 255));
        LabelAttributionImg24.setText(bundle.getString("Atribuciones.LabelAttributionImg24.text")); // NOI18N

        LabelAttributionAuthor10.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionAuthor10.setForeground(new java.awt.Color(0, 0, 255));
        LabelAttributionAuthor10.setText(bundle.getString("Atribuciones.LabelAttributionAuthor10.text")); // NOI18N

        LabelAttributionBy24.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionBy24.setText(bundle.getString("Atribuciones.LabelAttributionBy24.text")); // NOI18N

        LabelAttributionUnder10.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionUnder10.setText(bundle.getString("Atribuciones.LabelAttributionUnder10.text")); // NOI18N

        LabelAttributionAuthor24.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionAuthor24.setForeground(new java.awt.Color(0, 0, 255));
        LabelAttributionAuthor24.setText(bundle.getString("Atribuciones.LabelAttributionAuthor24.text")); // NOI18N

        LabelAttributionLicense10.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionLicense10.setForeground(new java.awt.Color(0, 0, 255));
        LabelAttributionLicense10.setText(bundle.getString("Atribuciones.LabelAttributionLicense10.text")); // NOI18N

        LabelAttributionImg11.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionImg11.setForeground(new java.awt.Color(0, 0, 255));
        LabelAttributionImg11.setText(bundle.getString("Atribuciones.LabelAttributionImg11.text")); // NOI18N

        LabelAttributionBy11.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionBy11.setText(bundle.getString("Atribuciones.LabelAttributionBy11.text")); // NOI18N

        LabelAttributionAuthor11.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionAuthor11.setForeground(new java.awt.Color(0, 0, 255));
        LabelAttributionAuthor11.setText(bundle.getString("Atribuciones.LabelAttributionAuthor11.text")); // NOI18N

        LabelAttributionUnder11.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionUnder11.setText(bundle.getString("Atribuciones.LabelAttributionUnder11.text")); // NOI18N

        LabelAttributionAuthor6.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionAuthor6.setForeground(new java.awt.Color(0, 0, 255));
        LabelAttributionAuthor6.setText(bundle.getString("Atribuciones.LabelAttributionAuthor6.text")); // NOI18N

        LabelAttributionLicense11.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionLicense11.setForeground(new java.awt.Color(0, 0, 255));
        LabelAttributionLicense11.setText(bundle.getString("Atribuciones.LabelAttributionLicense11.text")); // NOI18N

        LabelAttributionUnder6.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionUnder6.setText(bundle.getString("Atribuciones.LabelAttributionUnder6.text")); // NOI18N

        LabelAttributionImg12.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionImg12.setForeground(new java.awt.Color(0, 0, 255));
        LabelAttributionImg12.setText(bundle.getString("Atribuciones.LabelAttributionImg12.text")); // NOI18N

        LabelAttributionLicense6.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionLicense6.setForeground(new java.awt.Color(0, 0, 255));
        LabelAttributionLicense6.setText(bundle.getString("Atribuciones.LabelAttributionLicense6.text")); // NOI18N

        LabelAttributionBy12.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionBy12.setText(bundle.getString("Atribuciones.LabelAttributionBy12.text")); // NOI18N

        LabelAttributionImg7.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionImg7.setForeground(new java.awt.Color(0, 0, 255));
        LabelAttributionImg7.setText(bundle.getString("Atribuciones.LabelAttributionImg7.text")); // NOI18N

        LabelAttributionBy7.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionBy7.setText(bundle.getString("Atribuciones.LabelAttributionBy7.text")); // NOI18N

        LabelAttributionAuthor7.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionAuthor7.setForeground(new java.awt.Color(0, 0, 255));
        LabelAttributionAuthor7.setText(bundle.getString("Atribuciones.LabelAttributionAuthor7.text")); // NOI18N

        LabelAttributionUnder7.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionUnder7.setText(bundle.getString("Atribuciones.LabelAttributionUnder7.text")); // NOI18N

        LabelAttributionLicense7.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionLicense7.setForeground(new java.awt.Color(0, 0, 255));
        LabelAttributionLicense7.setText(bundle.getString("Atribuciones.LabelAttributionLicense7.text")); // NOI18N

        LabelAttributionImg8.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionImg8.setForeground(new java.awt.Color(0, 0, 255));
        LabelAttributionImg8.setText(bundle.getString("Atribuciones.LabelAttributionImg8.text")); // NOI18N

        LabelAttributionBy8.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionBy8.setText(bundle.getString("Atribuciones.LabelAttributionBy8.text")); // NOI18N

        LabelAttributionAuthor12.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionAuthor12.setForeground(new java.awt.Color(0, 0, 255));
        LabelAttributionAuthor12.setText(bundle.getString("Atribuciones.LabelAttributionAuthor12.text")); // NOI18N

        LabelAttributionUnder12.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionUnder12.setText(bundle.getString("Atribuciones.LabelAttributionUnder12.text")); // NOI18N

        LabelAttributionLicense12.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionLicense12.setForeground(new java.awt.Color(0, 0, 255));
        LabelAttributionLicense12.setText(bundle.getString("Atribuciones.LabelAttributionLicense12.text")); // NOI18N

        LabelAttributionImg13.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionImg13.setForeground(new java.awt.Color(0, 0, 255));
        LabelAttributionImg13.setText(bundle.getString("Atribuciones.LabelAttributionImg13.text")); // NOI18N

        LabelAttributionBy13.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionBy13.setText(bundle.getString("Atribuciones.LabelAttributionBy13.text")); // NOI18N

        LabelAttributionAuthor13.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionAuthor13.setForeground(new java.awt.Color(0, 0, 255));
        LabelAttributionAuthor13.setText(bundle.getString("Atribuciones.LabelAttributionAuthor13.text")); // NOI18N

        LabelAttributionUnder13.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionUnder13.setText(bundle.getString("Atribuciones.LabelAttributionUnder13.text")); // NOI18N

        LabelAttributionLicense13.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionLicense13.setForeground(new java.awt.Color(0, 0, 255));
        LabelAttributionLicense13.setText(bundle.getString("Atribuciones.LabelAttributionLicense13.text")); // NOI18N

        LabelAttributionMod5.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionMod5.setText(bundle.getString("Atribuciones.LabelAttributionMod5.text")); // NOI18N

        LabelAttributionMod6.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionMod6.setText(bundle.getString("Atribuciones.LabelAttributionMod6.text")); // NOI18N

        LabelAttributionMod7.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionMod7.setText(bundle.getString("Atribuciones.LabelAttributionMod7.text")); // NOI18N

        LabelAttributionMod8.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionMod8.setText(bundle.getString("Atribuciones.LabelAttributionMod8.text")); // NOI18N

        LabelAttributionMod9.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionMod9.setText(bundle.getString("Atribuciones.LabelAttributionMod9.text")); // NOI18N

        LabelAttributionMod10.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionMod10.setText(bundle.getString("Atribuciones.LabelAttributionMod10.text")); // NOI18N

        LabelAttributionMod11.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionMod11.setText(bundle.getString("Atribuciones.LabelAttributionMod11.text")); // NOI18N

        LabelAttributionMod12.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionMod12.setText(bundle.getString("Atribuciones.LabelAttributionMod12.text")); // NOI18N

        LabelAttributionMod13.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionMod13.setText(bundle.getString("Atribuciones.LabelAttributionMod13.text")); // NOI18N

        LabelAttributionImg14.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionImg14.setForeground(new java.awt.Color(0, 0, 255));
        LabelAttributionImg14.setText(bundle.getString("Atribuciones.LabelAttributionImg14.text")); // NOI18N

        LabelAttributionBy14.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionBy14.setText(bundle.getString("Atribuciones.LabelAttributionBy14.text")); // NOI18N

        LabelAttributionAuthor14.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionAuthor14.setForeground(new java.awt.Color(0, 0, 255));
        LabelAttributionAuthor14.setText(bundle.getString("Atribuciones.LabelAttributionAuthor14.text")); // NOI18N

        LabelAttributionUnder14.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionUnder14.setText(bundle.getString("Atribuciones.LabelAttributionUnder14.text")); // NOI18N

        LabelAttributionLicense14.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionLicense14.setForeground(new java.awt.Color(0, 0, 255));
        LabelAttributionLicense14.setText(bundle.getString("Atribuciones.LabelAttributionLicense14.text")); // NOI18N

        LabelAttributionMod14.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionMod14.setText(bundle.getString("Atribuciones.LabelAttributionMod14.text")); // NOI18N

        LabelAttributionImg15.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionImg15.setForeground(new java.awt.Color(0, 0, 255));
        LabelAttributionImg15.setText(bundle.getString("Atribuciones.LabelAttributionImg15.text")); // NOI18N

        LabelAttributionBy15.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionBy15.setText(bundle.getString("Atribuciones.LabelAttributionBy15.text")); // NOI18N

        LabelAttributionAuthor15.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionAuthor15.setForeground(new java.awt.Color(0, 0, 255));
        LabelAttributionAuthor15.setText(bundle.getString("Atribuciones.LabelAttributionAuthor15.text")); // NOI18N

        LabelAttributionUnder15.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionUnder15.setText(bundle.getString("Atribuciones.LabelAttributionUnder15.text")); // NOI18N

        LabelAttributionLicense15.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionLicense15.setForeground(new java.awt.Color(0, 0, 255));
        LabelAttributionLicense15.setText(bundle.getString("Atribuciones.LabelAttributionLicense15.text")); // NOI18N

        LabelAttributionMod15.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionMod15.setText(bundle.getString("Atribuciones.LabelAttributionMod15.text")); // NOI18N

        LabelAttributionImg16.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionImg16.setForeground(new java.awt.Color(0, 0, 255));
        LabelAttributionImg16.setText(bundle.getString("Atribuciones.LabelAttributionImg16.text")); // NOI18N

        LabelAttributionBy16.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionBy16.setText(bundle.getString("Atribuciones.LabelAttributionBy16.text")); // NOI18N

        LabelAttributionAuthor16.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionAuthor16.setForeground(new java.awt.Color(0, 0, 255));
        LabelAttributionAuthor16.setText(bundle.getString("Atribuciones.LabelAttributionAuthor16.text")); // NOI18N

        LabelAttributionUnder16.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionUnder16.setText(bundle.getString("Atribuciones.LabelAttributionUnder16.text")); // NOI18N

        LabelAttributionLicense16.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionLicense16.setForeground(new java.awt.Color(0, 0, 255));
        LabelAttributionLicense16.setText(bundle.getString("Atribuciones.LabelAttributionLicense16.text")); // NOI18N

        LabelAttributionMod16.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionMod16.setText(bundle.getString("Atribuciones.LabelAttributionMod16.text")); // NOI18N

        LabelAttributionImg17.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionImg17.setForeground(new java.awt.Color(0, 0, 255));
        LabelAttributionImg17.setText(bundle.getString("Atribuciones.LabelAttributionImg17.text")); // NOI18N

        LabelAttributionBy17.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionBy17.setText(bundle.getString("Atribuciones.LabelAttributionBy17.text")); // NOI18N

        LabelAttributionAuthor17.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionAuthor17.setForeground(new java.awt.Color(0, 0, 255));
        LabelAttributionAuthor17.setText(bundle.getString("Atribuciones.LabelAttributionAuthor17.text")); // NOI18N

        LabelAttributionUnder17.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionUnder17.setText(bundle.getString("Atribuciones.LabelAttributionUnder17.text")); // NOI18N

        LabelAttributionLicense17.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionLicense17.setForeground(new java.awt.Color(0, 0, 255));
        LabelAttributionLicense17.setText(bundle.getString("Atribuciones.LabelAttributionLicense17.text")); // NOI18N

        LabelAttributionMod17.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionMod17.setText(bundle.getString("Atribuciones.LabelAttributionMod17.text")); // NOI18N

        LabelAttributionImg18.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionImg18.setForeground(new java.awt.Color(0, 0, 255));
        LabelAttributionImg18.setText(bundle.getString("Atribuciones.LabelAttributionImg18.text")); // NOI18N

        LabelAttributionBy18.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionBy18.setText(bundle.getString("Atribuciones.LabelAttributionBy18.text")); // NOI18N

        LabelAttributionAuthor18.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionAuthor18.setForeground(new java.awt.Color(0, 0, 255));
        LabelAttributionAuthor18.setText(bundle.getString("Atribuciones.LabelAttributionAuthor18.text")); // NOI18N

        LabelAttributionUnder18.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionUnder18.setText(bundle.getString("Atribuciones.LabelAttributionUnder18.text")); // NOI18N

        LabelAttributionLicense18.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionLicense18.setForeground(new java.awt.Color(0, 0, 255));
        LabelAttributionLicense18.setText(bundle.getString("Atribuciones.LabelAttributionLicense18.text")); // NOI18N

        LabelAttributionMod18.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionMod18.setText(bundle.getString("Atribuciones.LabelAttributionMod18.text")); // NOI18N

        LabelAttributionImg19.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionImg19.setForeground(new java.awt.Color(0, 0, 255));
        LabelAttributionImg19.setText(bundle.getString("Atribuciones.LabelAttributionImg19.text")); // NOI18N

        LabelAttributionUnder24.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionUnder24.setText(bundle.getString("Atribuciones.LabelAttributionUnder24.text")); // NOI18N

        LabelAttributionBy19.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionBy19.setText(bundle.getString("Atribuciones.LabelAttributionBy19.text")); // NOI18N

        LabelAttributionLicense24.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionLicense24.setForeground(new java.awt.Color(0, 0, 255));
        LabelAttributionLicense24.setText(bundle.getString("Atribuciones.LabelAttributionLicense24.text")); // NOI18N

        LabelAttributionAuthor19.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionAuthor19.setForeground(new java.awt.Color(0, 0, 255));
        LabelAttributionAuthor19.setText(bundle.getString("Atribuciones.LabelAttributionAuthor19.text")); // NOI18N

        LabelAttributionMod24.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionMod24.setText(bundle.getString("Atribuciones.LabelAttributionMod24.text")); // NOI18N

        LabelIconos.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        LabelIconos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LabelIconos.setText(bundle.getString("Atribuciones.LabelIconos.text")); // NOI18N

        LabelAttributionImg1.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionImg1.setForeground(new java.awt.Color(0, 0, 255));
        LabelAttributionImg1.setText(bundle.getString("Atribuciones.LabelAttributionImg1.text")); // NOI18N

        LabelAttributionAuthor1.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionAuthor1.setForeground(new java.awt.Color(0, 0, 255));
        LabelAttributionAuthor1.setText(bundle.getString("Atribuciones.LabelAttributionAuthor1.text")); // NOI18N

        LabelAttributionBy1.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionBy1.setText(bundle.getString("Atribuciones.LabelAttributionBy1.text")); // NOI18N

        LabelAttributionUnder1.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionUnder1.setText(bundle.getString("Atribuciones.LabelAttributionUnder1.text")); // NOI18N

        LabelAttributionLicense1.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionLicense1.setForeground(new java.awt.Color(0, 0, 255));
        LabelAttributionLicense1.setText(bundle.getString("Atribuciones.LabelAttributionLicense1.text")); // NOI18N

        LabelAttributionImg2.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionImg2.setForeground(new java.awt.Color(0, 0, 255));
        LabelAttributionImg2.setText(bundle.getString("Atribuciones.LabelAttributionImg2.text")); // NOI18N

        LabelAttributionBy2.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionBy2.setText(bundle.getString("Atribuciones.LabelAttributionBy2.text")); // NOI18N

        LabelAttributionAuthor2.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionAuthor2.setForeground(new java.awt.Color(0, 0, 255));
        LabelAttributionAuthor2.setText(bundle.getString("Atribuciones.LabelAttributionAuthor2.text")); // NOI18N

        LabelAttributionUnder19.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionUnder19.setText(bundle.getString("Atribuciones.LabelAttributionUnder19.text")); // NOI18N

        LabelAttributionLicense19.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionLicense19.setForeground(new java.awt.Color(0, 0, 255));
        LabelAttributionLicense19.setText(bundle.getString("Atribuciones.LabelAttributionLicense19.text")); // NOI18N

        LabelAttributionMod19.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionMod19.setText(bundle.getString("Atribuciones.LabelAttributionMod19.text")); // NOI18N

        LabelAttributionImg20.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionImg20.setForeground(new java.awt.Color(0, 0, 255));
        LabelAttributionImg20.setText(bundle.getString("Atribuciones.LabelAttributionImg20.text")); // NOI18N

        LabelAttributionBy20.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionBy20.setText(bundle.getString("Atribuciones.LabelAttributionBy20.text")); // NOI18N

        LabelAttributionAuthor20.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionAuthor20.setForeground(new java.awt.Color(0, 0, 255));
        LabelAttributionAuthor20.setText(bundle.getString("Atribuciones.LabelAttributionAuthor20.text")); // NOI18N

        LabelAttributionUnder20.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionUnder20.setText(bundle.getString("Atribuciones.LabelAttributionUnder20.text")); // NOI18N

        LabelAttributionLicense20.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionLicense20.setForeground(new java.awt.Color(0, 0, 255));
        LabelAttributionLicense20.setText(bundle.getString("Atribuciones.LabelAttributionLicense20.text")); // NOI18N

        LabelAttributionMod20.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionMod20.setText(bundle.getString("Atribuciones.LabelAttributionMod20.text")); // NOI18N

        LabelAttributionImg21.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionImg21.setForeground(new java.awt.Color(0, 0, 255));
        LabelAttributionImg21.setText(bundle.getString("Atribuciones.LabelAttributionImg21.text")); // NOI18N

        LabelAttributionUnder2.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionUnder2.setText(bundle.getString("Atribuciones.LabelAttributionUnder2.text")); // NOI18N

        LabelAttributionLicense2.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionLicense2.setForeground(new java.awt.Color(0, 0, 255));
        LabelAttributionLicense2.setText(bundle.getString("Atribuciones.LabelAttributionLicense2.text")); // NOI18N

        LabelAttributionImg3.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionImg3.setForeground(new java.awt.Color(0, 0, 255));
        LabelAttributionImg3.setText(bundle.getString("Atribuciones.LabelAttributionImg3.text")); // NOI18N

        LabelAttributionBy3.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionBy3.setText(bundle.getString("Atribuciones.LabelAttributionBy3.text")); // NOI18N

        LabelAttributionAuthor3.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionAuthor3.setForeground(new java.awt.Color(0, 0, 255));
        LabelAttributionAuthor3.setText(bundle.getString("Atribuciones.LabelAttributionAuthor3.text")); // NOI18N

        LabelAttributionUnder3.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionUnder3.setText(bundle.getString("Atribuciones.LabelAttributionUnder3.text")); // NOI18N

        LabelAttributionLicense3.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionLicense3.setForeground(new java.awt.Color(0, 0, 255));
        LabelAttributionLicense3.setText(bundle.getString("Atribuciones.LabelAttributionLicense3.text")); // NOI18N

        LabelAttributionImg4.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionImg4.setForeground(new java.awt.Color(0, 0, 255));
        LabelAttributionImg4.setText(bundle.getString("Atribuciones.LabelAttributionImg4.text")); // NOI18N

        LabelAttributionBy4.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionBy4.setText(bundle.getString("Atribuciones.LabelAttributionBy4.text")); // NOI18N

        LabelAttributionAuthor4.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionAuthor4.setForeground(new java.awt.Color(0, 0, 255));
        LabelAttributionAuthor4.setText(bundle.getString("Atribuciones.LabelAttributionAuthor4.text")); // NOI18N

        LabelAttributionBy21.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionBy21.setText(bundle.getString("Atribuciones.LabelAttributionBy21.text")); // NOI18N

        LabelAttributionAuthor21.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionAuthor21.setForeground(new java.awt.Color(0, 0, 255));
        LabelAttributionAuthor21.setText(bundle.getString("Atribuciones.LabelAttributionAuthor21.text")); // NOI18N

        LabelAttributionUnder21.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionUnder21.setText(bundle.getString("Atribuciones.LabelAttributionUnder21.text")); // NOI18N

        LabelAttributionLicense21.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionLicense21.setForeground(new java.awt.Color(0, 0, 255));
        LabelAttributionLicense21.setText(bundle.getString("Atribuciones.LabelAttributionLicense21.text")); // NOI18N

        LabelAttributionMod21.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionMod21.setText(bundle.getString("Atribuciones.LabelAttributionMod21.text")); // NOI18N

        LabelAttributionImg22.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionImg22.setForeground(new java.awt.Color(0, 0, 255));
        LabelAttributionImg22.setText(bundle.getString("Atribuciones.LabelAttributionImg22.text")); // NOI18N

        LabelAttributionBy22.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionBy22.setText(bundle.getString("Atribuciones.LabelAttributionBy22.text")); // NOI18N

        LabelAttributionAuthor22.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionAuthor22.setForeground(new java.awt.Color(0, 0, 255));
        LabelAttributionAuthor22.setText(bundle.getString("Atribuciones.LabelAttributionAuthor22.text")); // NOI18N

        LabelAttributionAuthor8.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionAuthor8.setForeground(new java.awt.Color(0, 0, 255));
        LabelAttributionAuthor8.setText(bundle.getString("Atribuciones.LabelAttributionAuthor8.text")); // NOI18N

        LabelAttributionUnder22.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionUnder22.setText(bundle.getString("Atribuciones.LabelAttributionUnder22.text")); // NOI18N

        LabelAttributionUnder8.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionUnder8.setText(bundle.getString("Atribuciones.LabelAttributionUnder8.text")); // NOI18N

        LabelAttributionLicense22.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionLicense22.setForeground(new java.awt.Color(0, 0, 255));
        LabelAttributionLicense22.setText(bundle.getString("Atribuciones.LabelAttributionLicense22.text")); // NOI18N

        LabelAttributionLicense8.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionLicense8.setForeground(new java.awt.Color(0, 0, 255));
        LabelAttributionLicense8.setText(bundle.getString("Atribuciones.LabelAttributionLicense8.text")); // NOI18N

        LabelAttributionImg9.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionImg9.setForeground(new java.awt.Color(0, 0, 255));
        LabelAttributionImg9.setText(bundle.getString("Atribuciones.LabelAttributionImg9.text")); // NOI18N

        LabelAttributionBy9.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionBy9.setText(bundle.getString("Atribuciones.LabelAttributionBy9.text")); // NOI18N

        LabelAttributionAuthor9.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionAuthor9.setForeground(new java.awt.Color(0, 0, 255));
        LabelAttributionAuthor9.setText(bundle.getString("Atribuciones.LabelAttributionAuthor9.text")); // NOI18N

        LabelAttributionUnder9.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionUnder9.setText(bundle.getString("Atribuciones.LabelAttributionUnder9.text")); // NOI18N

        LabelAttributionUnder4.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionUnder4.setText(bundle.getString("Atribuciones.LabelAttributionUnder4.text")); // NOI18N

        LabelAttributionLicense9.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionLicense9.setForeground(new java.awt.Color(0, 0, 255));
        LabelAttributionLicense9.setText(bundle.getString("Atribuciones.LabelAttributionLicense9.text")); // NOI18N

        LabelAttributionLicense4.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionLicense4.setForeground(new java.awt.Color(0, 0, 255));
        LabelAttributionLicense4.setText(bundle.getString("Atribuciones.LabelAttributionLicense4.text")); // NOI18N

        LabelAttributionImg10.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionImg10.setForeground(new java.awt.Color(0, 0, 255));
        LabelAttributionImg10.setText(bundle.getString("Atribuciones.LabelAttributionImg10.text")); // NOI18N

        LabelFondos.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        LabelFondos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LabelFondos.setText(bundle.getString("Atribuciones.LabelFondos.text")); // NOI18N

        LabelAttributionBy10.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionBy10.setText(bundle.getString("Atribuciones.LabelAttributionBy10.text")); // NOI18N

        LabelAttributionImg5.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionImg5.setForeground(new java.awt.Color(0, 0, 255));
        LabelAttributionImg5.setText(bundle.getString("Atribuciones.LabelAttributionImg5.text")); // NOI18N

        LabelAttributionBy5.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionBy5.setText(bundle.getString("Atribuciones.LabelAttributionBy5.text")); // NOI18N

        LabelAttributionAuthor5.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionAuthor5.setForeground(new java.awt.Color(0, 0, 255));
        LabelAttributionAuthor5.setText(bundle.getString("Atribuciones.LabelAttributionAuthor5.text")); // NOI18N

        LabelAttributionUnder5.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionUnder5.setText(bundle.getString("Atribuciones.LabelAttributionUnder5.text")); // NOI18N

        LabelAttributionLicense5.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LabelAttributionLicense5.setForeground(new java.awt.Color(0, 0, 255));
        LabelAttributionLicense5.setText(bundle.getString("Atribuciones.LabelAttributionLicense5.text")); // NOI18N

        javax.swing.GroupLayout PanelContenedorLayout = new javax.swing.GroupLayout(PanelContenedor);
        PanelContenedor.setLayout(PanelContenedorLayout);
        PanelContenedorLayout.setHorizontalGroup(
            PanelContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelContenedorLayout.createSequentialGroup()
                .addGroup(PanelContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelContenedorLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(PanelContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanelContenedorLayout.createSequentialGroup()
                                .addComponent(LabelAttributionImg24)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(LabelAttributionBy24)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(LabelAttributionAuthor24)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(LabelAttributionUnder24)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(LabelAttributionLicense24)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(LabelAttributionMod24))
                            .addGroup(PanelContenedorLayout.createSequentialGroup()
                                .addComponent(LabelAttributionImg23)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(LabelAttributionBy23)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(LabelAttributionAuthor23)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(LabelAttributionUnder23)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(LabelAttributionLicense23)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(LabelAttributionMod23))
                            .addGroup(PanelContenedorLayout.createSequentialGroup()
                                .addComponent(LabelAttributionImg22)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(LabelAttributionBy22)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(LabelAttributionAuthor22)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(LabelAttributionUnder22)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(LabelAttributionLicense22)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(LabelAttributionMod22))
                            .addGroup(PanelContenedorLayout.createSequentialGroup()
                                .addComponent(LabelAttributionImg21)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(LabelAttributionBy21)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(LabelAttributionAuthor21)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(LabelAttributionUnder21)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(LabelAttributionLicense21)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(LabelAttributionMod21))
                            .addGroup(PanelContenedorLayout.createSequentialGroup()
                                .addComponent(LabelAttributionImg20)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(LabelAttributionBy20)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(LabelAttributionAuthor20)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(LabelAttributionUnder20)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(LabelAttributionLicense20)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(LabelAttributionMod20))
                            .addGroup(PanelContenedorLayout.createSequentialGroup()
                                .addComponent(LabelAttributionImg19)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(LabelAttributionBy19)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(LabelAttributionAuthor19)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(LabelAttributionUnder19)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(LabelAttributionLicense19)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(LabelAttributionMod19))
                            .addGroup(PanelContenedorLayout.createSequentialGroup()
                                .addComponent(LabelAttributionImg18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(LabelAttributionBy18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(LabelAttributionAuthor18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(LabelAttributionUnder18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(LabelAttributionLicense18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(LabelAttributionMod18))
                            .addGroup(PanelContenedorLayout.createSequentialGroup()
                                .addComponent(LabelAttributionImg17)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(LabelAttributionBy17)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(LabelAttributionAuthor17)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(LabelAttributionUnder17)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(LabelAttributionLicense17)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(LabelAttributionMod17))
                            .addGroup(PanelContenedorLayout.createSequentialGroup()
                                .addComponent(LabelAttributionImg14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(LabelAttributionBy14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(LabelAttributionAuthor14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(LabelAttributionUnder14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(LabelAttributionLicense14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(LabelAttributionMod14))
                            .addGroup(PanelContenedorLayout.createSequentialGroup()
                                .addComponent(LabelAttributionImg13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(LabelAttributionBy13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(LabelAttributionAuthor13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(LabelAttributionUnder13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(LabelAttributionLicense13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(LabelAttributionMod13))
                            .addGroup(PanelContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PanelContenedorLayout.createSequentialGroup()
                                    .addComponent(LabelAttributionImg12)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(LabelAttributionBy12)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(LabelAttributionAuthor12)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(LabelAttributionUnder12)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(LabelAttributionLicense12)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(LabelAttributionMod12))
                                .addGroup(PanelContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(PanelContenedorLayout.createSequentialGroup()
                                        .addComponent(LabelAttributionImg5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(LabelAttributionBy5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(LabelAttributionAuthor5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(LabelAttributionUnder5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(LabelAttributionLicense5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(LabelAttributionMod5))
                                    .addGroup(PanelContenedorLayout.createSequentialGroup()
                                        .addComponent(LabelAttributionImg6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(LabelAttributionBy6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(LabelAttributionAuthor6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(LabelAttributionUnder6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(LabelAttributionLicense6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(LabelAttributionMod6))
                                    .addGroup(PanelContenedorLayout.createSequentialGroup()
                                        .addComponent(LabelAttributionImg7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(LabelAttributionBy7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(LabelAttributionAuthor7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(LabelAttributionUnder7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(LabelAttributionLicense7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(LabelAttributionMod7))
                                    .addGroup(PanelContenedorLayout.createSequentialGroup()
                                        .addComponent(LabelAttributionImg8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(LabelAttributionBy8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(LabelAttributionAuthor8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(LabelAttributionUnder8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(LabelAttributionLicense8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(LabelAttributionMod8))
                                    .addGroup(PanelContenedorLayout.createSequentialGroup()
                                        .addComponent(LabelAttributionImg9)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(LabelAttributionBy9)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(LabelAttributionAuthor9)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(LabelAttributionUnder9)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(LabelAttributionLicense9)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(LabelAttributionMod9))
                                    .addGroup(PanelContenedorLayout.createSequentialGroup()
                                        .addComponent(LabelAttributionImg10)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(LabelAttributionBy10)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(LabelAttributionAuthor10)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(LabelAttributionUnder10)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(LabelAttributionLicense10)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(LabelAttributionMod10))
                                    .addGroup(PanelContenedorLayout.createSequentialGroup()
                                        .addComponent(LabelAttributionImg11)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(LabelAttributionBy11)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(LabelAttributionAuthor11)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(LabelAttributionUnder11)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(LabelAttributionLicense11)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(LabelAttributionMod11))
                                    .addGroup(PanelContenedorLayout.createSequentialGroup()
                                        .addGap(325, 325, 325)
                                        .addComponent(LabelFondos, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(PanelContenedorLayout.createSequentialGroup()
                                        .addComponent(LabelAttributionImg2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(LabelAttributionBy2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(LabelAttributionAuthor2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(LabelAttributionUnder2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(LabelAttributionLicense2))
                                    .addGroup(PanelContenedorLayout.createSequentialGroup()
                                        .addComponent(LabelAttributionImg3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(LabelAttributionBy3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(LabelAttributionAuthor3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(LabelAttributionUnder3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(LabelAttributionLicense3))
                                    .addGroup(PanelContenedorLayout.createSequentialGroup()
                                        .addComponent(LabelAttributionImg1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(LabelAttributionBy1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(LabelAttributionAuthor1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(LabelAttributionUnder1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(LabelAttributionLicense1))
                                    .addGroup(PanelContenedorLayout.createSequentialGroup()
                                        .addComponent(LabelAttributionImg4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(LabelAttributionBy4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(LabelAttributionAuthor4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(LabelAttributionUnder4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(LabelAttributionLicense4))))
                            .addGroup(PanelContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PanelContenedorLayout.createSequentialGroup()
                                    .addComponent(LabelAttributionImg16)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(LabelAttributionBy16)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(LabelAttributionAuthor16)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(LabelAttributionUnder16)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(LabelAttributionLicense16)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(LabelAttributionMod16))
                                .addGroup(PanelContenedorLayout.createSequentialGroup()
                                    .addComponent(LabelAttributionImg15)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(LabelAttributionBy15)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(LabelAttributionAuthor15)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(LabelAttributionUnder15)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(LabelAttributionLicense15)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(LabelAttributionMod15)))))
                    .addGroup(PanelContenedorLayout.createSequentialGroup()
                        .addGap(361, 361, 361)
                        .addComponent(LabelIconos, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        PanelContenedorLayout.setVerticalGroup(
            PanelContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelContenedorLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(LabelIconos)
                .addGap(11, 11, 11)
                .addGroup(PanelContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LabelAttributionImg1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(LabelAttributionAuthor1)
                    .addComponent(LabelAttributionBy1, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelAttributionUnder1)
                    .addComponent(LabelAttributionLicense1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LabelAttributionImg2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(LabelAttributionAuthor2)
                    .addComponent(LabelAttributionBy2, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelAttributionUnder2)
                    .addComponent(LabelAttributionLicense2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LabelAttributionImg3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(LabelAttributionAuthor3)
                    .addComponent(LabelAttributionBy3, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelAttributionUnder3)
                    .addComponent(LabelAttributionLicense3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LabelAttributionImg4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(LabelAttributionAuthor4)
                    .addComponent(LabelAttributionBy4, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelAttributionUnder4)
                    .addComponent(LabelAttributionLicense4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LabelFondos)
                .addGap(13, 13, 13)
                .addGroup(PanelContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LabelAttributionImg5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(LabelAttributionAuthor5)
                    .addComponent(LabelAttributionBy5, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelAttributionUnder5)
                    .addComponent(LabelAttributionLicense5)
                    .addComponent(LabelAttributionMod5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LabelAttributionImg6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(LabelAttributionAuthor6)
                    .addComponent(LabelAttributionBy6, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelAttributionUnder6)
                    .addComponent(LabelAttributionLicense6)
                    .addComponent(LabelAttributionMod6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LabelAttributionImg7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(LabelAttributionAuthor7)
                    .addComponent(LabelAttributionBy7, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelAttributionUnder7)
                    .addComponent(LabelAttributionLicense7)
                    .addComponent(LabelAttributionMod7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LabelAttributionImg8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(LabelAttributionAuthor8)
                    .addComponent(LabelAttributionBy8, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelAttributionUnder8)
                    .addComponent(LabelAttributionLicense8)
                    .addComponent(LabelAttributionMod8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LabelAttributionImg9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(LabelAttributionAuthor9)
                    .addComponent(LabelAttributionBy9, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelAttributionUnder9)
                    .addComponent(LabelAttributionLicense9)
                    .addComponent(LabelAttributionMod9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LabelAttributionImg10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(LabelAttributionAuthor10)
                    .addComponent(LabelAttributionBy10, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelAttributionUnder10)
                    .addComponent(LabelAttributionLicense10)
                    .addComponent(LabelAttributionMod10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LabelAttributionImg11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(LabelAttributionAuthor11)
                    .addComponent(LabelAttributionBy11, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelAttributionUnder11)
                    .addComponent(LabelAttributionLicense11)
                    .addComponent(LabelAttributionMod11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LabelAttributionImg12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(LabelAttributionAuthor12)
                    .addComponent(LabelAttributionBy12, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelAttributionUnder12)
                    .addComponent(LabelAttributionLicense12)
                    .addComponent(LabelAttributionMod12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LabelAttributionImg13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(LabelAttributionAuthor13)
                    .addComponent(LabelAttributionBy13, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelAttributionUnder13)
                    .addComponent(LabelAttributionLicense13)
                    .addComponent(LabelAttributionMod13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LabelAttributionImg14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(LabelAttributionAuthor14)
                    .addComponent(LabelAttributionBy14, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelAttributionUnder14)
                    .addComponent(LabelAttributionLicense14)
                    .addComponent(LabelAttributionMod14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LabelAttributionImg15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(LabelAttributionAuthor15)
                    .addComponent(LabelAttributionBy15, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelAttributionUnder15)
                    .addComponent(LabelAttributionLicense15)
                    .addComponent(LabelAttributionMod15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LabelAttributionImg16)
                    .addComponent(LabelAttributionAuthor16)
                    .addComponent(LabelAttributionBy16, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelAttributionUnder16)
                    .addComponent(LabelAttributionLicense16)
                    .addComponent(LabelAttributionMod16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LabelAttributionImg17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(LabelAttributionAuthor17)
                    .addComponent(LabelAttributionBy17, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelAttributionUnder17)
                    .addComponent(LabelAttributionLicense17)
                    .addComponent(LabelAttributionMod17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LabelAttributionImg18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(LabelAttributionAuthor18)
                    .addComponent(LabelAttributionBy18, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelAttributionUnder18)
                    .addComponent(LabelAttributionLicense18)
                    .addComponent(LabelAttributionMod18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LabelAttributionImg19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(LabelAttributionAuthor19)
                    .addComponent(LabelAttributionBy19, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelAttributionUnder19)
                    .addComponent(LabelAttributionLicense19)
                    .addComponent(LabelAttributionMod19))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LabelAttributionImg20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(LabelAttributionAuthor20)
                    .addComponent(LabelAttributionBy20, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelAttributionUnder20)
                    .addComponent(LabelAttributionLicense20)
                    .addComponent(LabelAttributionMod20))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LabelAttributionImg21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(LabelAttributionAuthor21)
                    .addComponent(LabelAttributionBy21, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelAttributionUnder21)
                    .addComponent(LabelAttributionLicense21)
                    .addComponent(LabelAttributionMod21))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LabelAttributionImg22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(LabelAttributionAuthor22)
                    .addComponent(LabelAttributionBy22, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelAttributionUnder22)
                    .addComponent(LabelAttributionLicense22)
                    .addComponent(LabelAttributionMod22))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LabelAttributionImg23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(LabelAttributionAuthor23)
                    .addComponent(LabelAttributionBy23, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelAttributionUnder23)
                    .addComponent(LabelAttributionLicense23)
                    .addComponent(LabelAttributionMod23))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LabelAttributionImg24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(LabelAttributionAuthor24)
                    .addComponent(LabelAttributionBy24, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelAttributionUnder24)
                    .addComponent(LabelAttributionLicense24)
                    .addComponent(LabelAttributionMod24))
                .addContainerGap())
        );

        jScrollPane1.setViewportView(PanelContenedor);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 766, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

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
            java.util.logging.Logger.getLogger(Atribuciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Atribuciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Atribuciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Atribuciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Atribuciones dialog = new Atribuciones(new javax.swing.JFrame(), true);
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
    
    /**
     * Método que maneja el funcionamiento del enlace
     * @param LabelEnlace 
     */
     private void irEnlace(JLabel LabelEnlace, final String enlace) {
        LabelEnlace.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI(enlace));
                } catch (URISyntaxException | IOException ex) {
                    //Aquí haría lo que fuera si hay un error
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LabelAttributionAuthor1;
    private javax.swing.JLabel LabelAttributionAuthor10;
    private javax.swing.JLabel LabelAttributionAuthor11;
    private javax.swing.JLabel LabelAttributionAuthor12;
    private javax.swing.JLabel LabelAttributionAuthor13;
    private javax.swing.JLabel LabelAttributionAuthor14;
    private javax.swing.JLabel LabelAttributionAuthor15;
    private javax.swing.JLabel LabelAttributionAuthor16;
    private javax.swing.JLabel LabelAttributionAuthor17;
    private javax.swing.JLabel LabelAttributionAuthor18;
    private javax.swing.JLabel LabelAttributionAuthor19;
    private javax.swing.JLabel LabelAttributionAuthor2;
    private javax.swing.JLabel LabelAttributionAuthor20;
    private javax.swing.JLabel LabelAttributionAuthor21;
    private javax.swing.JLabel LabelAttributionAuthor22;
    private javax.swing.JLabel LabelAttributionAuthor23;
    private javax.swing.JLabel LabelAttributionAuthor24;
    private javax.swing.JLabel LabelAttributionAuthor3;
    private javax.swing.JLabel LabelAttributionAuthor4;
    private javax.swing.JLabel LabelAttributionAuthor5;
    private javax.swing.JLabel LabelAttributionAuthor6;
    private javax.swing.JLabel LabelAttributionAuthor7;
    private javax.swing.JLabel LabelAttributionAuthor8;
    private javax.swing.JLabel LabelAttributionAuthor9;
    private javax.swing.JLabel LabelAttributionBy1;
    private javax.swing.JLabel LabelAttributionBy10;
    private javax.swing.JLabel LabelAttributionBy11;
    private javax.swing.JLabel LabelAttributionBy12;
    private javax.swing.JLabel LabelAttributionBy13;
    private javax.swing.JLabel LabelAttributionBy14;
    private javax.swing.JLabel LabelAttributionBy15;
    private javax.swing.JLabel LabelAttributionBy16;
    private javax.swing.JLabel LabelAttributionBy17;
    private javax.swing.JLabel LabelAttributionBy18;
    private javax.swing.JLabel LabelAttributionBy19;
    private javax.swing.JLabel LabelAttributionBy2;
    private javax.swing.JLabel LabelAttributionBy20;
    private javax.swing.JLabel LabelAttributionBy21;
    private javax.swing.JLabel LabelAttributionBy22;
    private javax.swing.JLabel LabelAttributionBy23;
    private javax.swing.JLabel LabelAttributionBy24;
    private javax.swing.JLabel LabelAttributionBy3;
    private javax.swing.JLabel LabelAttributionBy4;
    private javax.swing.JLabel LabelAttributionBy5;
    private javax.swing.JLabel LabelAttributionBy6;
    private javax.swing.JLabel LabelAttributionBy7;
    private javax.swing.JLabel LabelAttributionBy8;
    private javax.swing.JLabel LabelAttributionBy9;
    private javax.swing.JLabel LabelAttributionImg1;
    private javax.swing.JLabel LabelAttributionImg10;
    private javax.swing.JLabel LabelAttributionImg11;
    private javax.swing.JLabel LabelAttributionImg12;
    private javax.swing.JLabel LabelAttributionImg13;
    private javax.swing.JLabel LabelAttributionImg14;
    private javax.swing.JLabel LabelAttributionImg15;
    private javax.swing.JLabel LabelAttributionImg16;
    private javax.swing.JLabel LabelAttributionImg17;
    private javax.swing.JLabel LabelAttributionImg18;
    private javax.swing.JLabel LabelAttributionImg19;
    private javax.swing.JLabel LabelAttributionImg2;
    private javax.swing.JLabel LabelAttributionImg20;
    private javax.swing.JLabel LabelAttributionImg21;
    private javax.swing.JLabel LabelAttributionImg22;
    private javax.swing.JLabel LabelAttributionImg23;
    private javax.swing.JLabel LabelAttributionImg24;
    private javax.swing.JLabel LabelAttributionImg3;
    private javax.swing.JLabel LabelAttributionImg4;
    private javax.swing.JLabel LabelAttributionImg5;
    private javax.swing.JLabel LabelAttributionImg6;
    private javax.swing.JLabel LabelAttributionImg7;
    private javax.swing.JLabel LabelAttributionImg8;
    private javax.swing.JLabel LabelAttributionImg9;
    private javax.swing.JLabel LabelAttributionLicense1;
    private javax.swing.JLabel LabelAttributionLicense10;
    private javax.swing.JLabel LabelAttributionLicense11;
    private javax.swing.JLabel LabelAttributionLicense12;
    private javax.swing.JLabel LabelAttributionLicense13;
    private javax.swing.JLabel LabelAttributionLicense14;
    private javax.swing.JLabel LabelAttributionLicense15;
    private javax.swing.JLabel LabelAttributionLicense16;
    private javax.swing.JLabel LabelAttributionLicense17;
    private javax.swing.JLabel LabelAttributionLicense18;
    private javax.swing.JLabel LabelAttributionLicense19;
    private javax.swing.JLabel LabelAttributionLicense2;
    private javax.swing.JLabel LabelAttributionLicense20;
    private javax.swing.JLabel LabelAttributionLicense21;
    private javax.swing.JLabel LabelAttributionLicense22;
    private javax.swing.JLabel LabelAttributionLicense23;
    private javax.swing.JLabel LabelAttributionLicense24;
    private javax.swing.JLabel LabelAttributionLicense3;
    private javax.swing.JLabel LabelAttributionLicense4;
    private javax.swing.JLabel LabelAttributionLicense5;
    private javax.swing.JLabel LabelAttributionLicense6;
    private javax.swing.JLabel LabelAttributionLicense7;
    private javax.swing.JLabel LabelAttributionLicense8;
    private javax.swing.JLabel LabelAttributionLicense9;
    private javax.swing.JLabel LabelAttributionMod10;
    private javax.swing.JLabel LabelAttributionMod11;
    private javax.swing.JLabel LabelAttributionMod12;
    private javax.swing.JLabel LabelAttributionMod13;
    private javax.swing.JLabel LabelAttributionMod14;
    private javax.swing.JLabel LabelAttributionMod15;
    private javax.swing.JLabel LabelAttributionMod16;
    private javax.swing.JLabel LabelAttributionMod17;
    private javax.swing.JLabel LabelAttributionMod18;
    private javax.swing.JLabel LabelAttributionMod19;
    private javax.swing.JLabel LabelAttributionMod20;
    private javax.swing.JLabel LabelAttributionMod21;
    private javax.swing.JLabel LabelAttributionMod22;
    private javax.swing.JLabel LabelAttributionMod23;
    private javax.swing.JLabel LabelAttributionMod24;
    private javax.swing.JLabel LabelAttributionMod5;
    private javax.swing.JLabel LabelAttributionMod6;
    private javax.swing.JLabel LabelAttributionMod7;
    private javax.swing.JLabel LabelAttributionMod8;
    private javax.swing.JLabel LabelAttributionMod9;
    private javax.swing.JLabel LabelAttributionUnder1;
    private javax.swing.JLabel LabelAttributionUnder10;
    private javax.swing.JLabel LabelAttributionUnder11;
    private javax.swing.JLabel LabelAttributionUnder12;
    private javax.swing.JLabel LabelAttributionUnder13;
    private javax.swing.JLabel LabelAttributionUnder14;
    private javax.swing.JLabel LabelAttributionUnder15;
    private javax.swing.JLabel LabelAttributionUnder16;
    private javax.swing.JLabel LabelAttributionUnder17;
    private javax.swing.JLabel LabelAttributionUnder18;
    private javax.swing.JLabel LabelAttributionUnder19;
    private javax.swing.JLabel LabelAttributionUnder2;
    private javax.swing.JLabel LabelAttributionUnder20;
    private javax.swing.JLabel LabelAttributionUnder21;
    private javax.swing.JLabel LabelAttributionUnder22;
    private javax.swing.JLabel LabelAttributionUnder23;
    private javax.swing.JLabel LabelAttributionUnder24;
    private javax.swing.JLabel LabelAttributionUnder3;
    private javax.swing.JLabel LabelAttributionUnder4;
    private javax.swing.JLabel LabelAttributionUnder5;
    private javax.swing.JLabel LabelAttributionUnder6;
    private javax.swing.JLabel LabelAttributionUnder7;
    private javax.swing.JLabel LabelAttributionUnder8;
    private javax.swing.JLabel LabelAttributionUnder9;
    private javax.swing.JLabel LabelFondos;
    private javax.swing.JLabel LabelIconos;
    private javax.swing.JPanel PanelContenedor;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
