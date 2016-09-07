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
package renders;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Alvarielli
 */
public class RenderTenis3S extends DefaultTableCellRenderer{
    
 @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
         //Obtenemos valores de la clase padre
         super.getTableCellRendererComponent (table, value, isSelected, hasFocus, row, column);
      
        if (value instanceof String){
            if(column == 2 || column == 9){
                //Los equipos local y visitante van centrados y en negrita        
                this.setOpaque(true);//Para poder ver el fondo de otro color
                this.setHorizontalAlignment(SwingConstants.CENTER);
                this.setFont(new Font("Tahoma", Font.BOLD, 14));
                this.setForeground(new Color(0,0,0));
                this.setText((String)value); 
            }else if(column == 3 || column == 4 || column == 7 || column == 8){
                //Los resultados totales irán en azul y negrita
                this.setOpaque(true); 
                this.setHorizontalAlignment(SwingConstants.CENTER);
                this.setFont(new Font("Tahoma", Font.BOLD, 14));
                this.setForeground(new Color(0,0,119));
                this.setText((String)value);
                this.setBackground(new Color(0,0,0));
            }else if(column == 5 || column == 6){
                //Los resultados totales irán en azul y negrita
                this.setOpaque(true); 
                this.setHorizontalAlignment(SwingConstants.CENTER);
                this.setFont(new Font("Tahoma", Font.BOLD, 14));
                this.setForeground(new Color(180,0,0));
                this.setText((String)value);
            }else{   
                //Los campos de fecha hora y pista se quedan con el formato normal        
                this.setOpaque(true);
                this.setHorizontalAlignment(SwingConstants.LEFT);
                this.setFont(new Font("Tahoma", Font.PLAIN, 14));
                this.setForeground(new Color(0,0,0));
                this.setText((String)value);
            }
        }
        
        //asignamos los colores de las filas //new Color(57,105,138) para isSelected
        if(row % 2 == 0){
            this.setBackground(new Color(255,255,255));
            //Hacemos que se vea diferente el color del campo excepto cuando sea la fila seleccionada
            if (value instanceof String){
                if(column == 3 || column == 4 || column == 7 || column == 8){
                    this.setBackground(new Color(0,255,255));//En marroncito 255,239,213 y 242,226,200
                }
            }
        }else{
            this.setBackground(new Color(242,242,242));
            //Hacemos que se vea diferente el color del campo excepto cuando sea la fila seleccionada
            if (value instanceof String){
                if(column == 3 || column == 4 || column == 7 || column == 8){
                    this.setBackground(new Color(0,242,242));
                }
            }
        }
        
        //Pintar de otro color la fila seleccionada
        if(isSelected){
            this.setBackground(new Color(57,105,138)); 
        }
        
        
    
      return this;
      
      
   }//End getTableCellRendererComponent()
    
}//End class

