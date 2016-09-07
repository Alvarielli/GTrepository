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
package datasources;

import static java.lang.Integer.valueOf;
import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import partidos.PartidoEliminatoriaTenis5Sets;

/**
 *
 * @author Alvarielli
 */
public class DataSourceEliminatoriasTenis5SetsResultados implements JRDataSource{

    private List<PartidoEliminatoriaTenis5Sets> partidosInforme = new ArrayList<>();
    private int indice = -1;
     
    @Override
    public boolean next() throws JRException
    {
        return ++indice < partidosInforme.size();
    }

    public void addPartido(PartidoEliminatoriaTenis5Sets partido)
    {
        this.partidosInforme.add(partido);
    }

    @Override
    public Object getFieldValue(JRField jrf) throws JRException {
        Object valor = null;  
        
        if("fecha".equals(jrf.getName())) { 
            valor = partidosInforme.get(indice).getFecha(); 
        }else if("hora".equals(jrf.getName())) { 
            valor = partidosInforme.get(indice).getHora(); 
        }else if("local".equals(jrf.getName())) { 
            valor = partidosInforme.get(indice).getLocal(); 
        }else if("visitante".equals(jrf.getName())) { 
            valor = partidosInforme.get(indice).getVisitante(); 
        }else if("set1l".equals(jrf.getName())) { 
            valor = valueOf(partidosInforme.get(indice).getSet1L());
            //si nos pasan un partido vacío convertimos el -1 en un hueco
            if(valor.toString().equalsIgnoreCase("-1")){
                valor = "";
            }
        }else if("set1L".equals(jrf.getName())) { 
            valor = valueOf(partidosInforme.get(indice).getSet1L());
            //si nos pasan un partido vacío convertimos el -1 en un hueco
            if(valor.toString().equalsIgnoreCase("-1")){
                valor = "";
            }
        }else if("set2L".equals(jrf.getName())) { 
            valor = valueOf(partidosInforme.get(indice).getSet2L());
            //si nos pasan un partido vacío convertimos el -1 en un hueco
            if(valor.toString().equalsIgnoreCase("-1")){
                valor = "";
            }
        }else if("set3L".equals(jrf.getName())) { 
            valor = valueOf(partidosInforme.get(indice).getSet3L());
            //si nos pasan un partido vacío convertimos el -1 en un hueco
            if(valor.toString().equalsIgnoreCase("-1")){
                valor = "";
            }
        }else if("set4L".equals(jrf.getName())) { 
            valor = valueOf(partidosInforme.get(indice).getSet4L());
            //si nos pasan un partido vacío convertimos el -1 en un hueco
            if(valor.toString().equalsIgnoreCase("-1")){
                valor = "";
            }
        }else if("set5L".equals(jrf.getName())) { 
            valor = valueOf(partidosInforme.get(indice).getSet5L());
            //si nos pasan un partido vacío convertimos el -1 en un hueco
            if(valor.toString().equalsIgnoreCase("-1")){
                valor = "";
            }
        }else if("set1V".equals(jrf.getName())) { 
            valor = valueOf(partidosInforme.get(indice).getSet1V());
            //si nos pasan un partido vacío convertimos el -1 en un hueco
            if(valor.toString().equalsIgnoreCase("-1")){
                valor = "";
            }
        }else if("set2V".equals(jrf.getName())) { 
            valor = valueOf(partidosInforme.get(indice).getSet2V());
            //si nos pasan un partido vacío convertimos el -1 en un hueco
            if(valor.toString().equalsIgnoreCase("-1")){
                valor = "";
            }
        }else if("set3V".equals(jrf.getName())) { 
            //si nos pasan un partido vacío convertimos el -1 en un hueco
            valor = valueOf(partidosInforme.get(indice).getSet3V());
            if(valor.toString().equalsIgnoreCase("-1")){
                valor = "";
            }
        }else if("set4V".equals(jrf.getName())) { 
            valor = valueOf(partidosInforme.get(indice).getSet4V());
            //si nos pasan un partido vacío convertimos el -1 en un hueco
            if(valor.toString().equalsIgnoreCase("-1")){
                valor = "";
            }
        }else if("set5V".equals(jrf.getName())) { 
            valor = valueOf(partidosInforme.get(indice).getSet5V());
            //si nos pasan un partido vacío convertimos el -1 en un hueco
            if(valor.toString().equalsIgnoreCase("-1")){
                valor = "";
            }
        }else if("ganador".equals(jrf.getName())) { 
            valor = partidosInforme.get(indice).getGanador(); 
        }else if("desempate".equals(jrf.getName())) { 
            valor = partidosInforme.get(indice).getDesempate(); 
        }
        return valor; 
    }
    
}
