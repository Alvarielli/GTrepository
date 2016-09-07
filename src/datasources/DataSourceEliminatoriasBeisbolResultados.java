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
import partidos.PartidoEliminatoriaBeisbol;

/**
 * 
 * @author Alvarielli
 */

//También es válido para Curling
public class DataSourceEliminatoriasBeisbolResultados implements JRDataSource{

    private List<PartidoEliminatoriaBeisbol> partidosInforme = new ArrayList<>();
    private int indice = -1;
     
    @Override
    public boolean next() throws JRException
    {
        return ++indice < partidosInforme.size();
    }

    public void addPartido(PartidoEliminatoriaBeisbol partido)
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
        } else if("visitante".equals(jrf.getName())) { 
            valor = partidosInforme.get(indice).getVisitante(); 
        }else if("golesL".equals(jrf.getName())) { 
            valor = valueOf(partidosInforme.get(indice).getGolesL());
            //si nos pasan un partido vacío convertimos el -1 en un hueco
            if(valor.toString().equalsIgnoreCase("-1")){
                valor = "";
            }
        }else if("golesV".equals(jrf.getName())) { 
            valor = valueOf(partidosInforme.get(indice).getGolesV());
            //si nos pasan un partido vacío convertimos el -1 en un hueco
            if(valor.toString().equalsIgnoreCase("-1")){
                valor = "";
            }
        }else if("RL1".equals(jrf.getName())) { 
            valor = valueOf(partidosInforme.get(indice).getRL1());
            //si nos pasan un partido vacío convertimos el -1 en un hueco
            if(valor.toString().equalsIgnoreCase("-1")){
                valor = "";
            }
        }else if("RV1".equals(jrf.getName())) { 
            valor = valueOf(partidosInforme.get(indice).getRV1());
            //si nos pasan un partido vacío convertimos el -1 en un hueco
            if(valor.toString().equalsIgnoreCase("-1")){
                valor = "";
            }
        }else if("RL2".equals(jrf.getName())) { 
            valor = valueOf(partidosInforme.get(indice).getRL2());
            //si nos pasan un partido vacío convertimos el -1 en un hueco
            if(valor.toString().equalsIgnoreCase("-1")){
                valor = "";
            }
        }else if("RV2".equals(jrf.getName())) { 
            valor = valueOf(partidosInforme.get(indice).getRV2());
            //si nos pasan un partido vacío convertimos el -1 en un hueco
            if(valor.toString().equalsIgnoreCase("-1")){
                valor = "";
            }
        }else if("RL3".equals(jrf.getName())) { 
            valor = valueOf(partidosInforme.get(indice).getRL3());
            //si nos pasan un partido vacío convertimos el -1 en un hueco
            if(valor.toString().equalsIgnoreCase("-1")){
                valor = "";
            }
        }else if("RV3".equals(jrf.getName())) { 
            valor = valueOf(partidosInforme.get(indice).getRV3());
            //si nos pasan un partido vacío convertimos el -1 en un hueco
            if(valor.toString().equalsIgnoreCase("-1")){
                valor = "";
            }
        }else if("RL4".equals(jrf.getName())) { 
            //si nos pasan un partido vacío convertimos el -1 en un hueco
            valor = valueOf(partidosInforme.get(indice).getRL4());
            if(valor.toString().equalsIgnoreCase("-1")){
                valor = "";
            }
        }else if("RV4".equals(jrf.getName())) { 
            valor = valueOf(partidosInforme.get(indice).getRV4());
            //si nos pasan un partido vacío convertimos el -1 en un hueco
            if(valor.toString().equalsIgnoreCase("-1")){
                valor = "";
            }
        }else if("RL5".equals(jrf.getName())) { 
            valor = valueOf(partidosInforme.get(indice).getRL5());
            //si nos pasan un partido vacío convertimos el -1 en un hueco
            if(valor.toString().equalsIgnoreCase("-1")){
                valor = "";
            }
        }else if("RV5".equals(jrf.getName())) { 
            valor = valueOf(partidosInforme.get(indice).getRV5());
            //si nos pasan un partido vacío convertimos el -1 en un hueco
            if(valor.toString().equalsIgnoreCase("-1")){
                valor = "";
            }
        }else if("RL6".equals(jrf.getName())) { 
            valor = valueOf(partidosInforme.get(indice).getRL6());
            //si nos pasan un partido vacío convertimos el -1 en un hueco
            if(valor.toString().equalsIgnoreCase("-1")){
                valor = "";
            }
        }else if("RV6".equals(jrf.getName())) { 
            valor = valueOf(partidosInforme.get(indice).getRV6());
            //si nos pasan un partido vacío convertimos el -1 en un hueco
            if(valor.toString().equalsIgnoreCase("-1")){
                valor = "";
            }
        }else if("RL7".equals(jrf.getName())) { 
            valor = valueOf(partidosInforme.get(indice).getRL7());
            //si nos pasan un partido vacío convertimos el -1 en un hueco
            if(valor.toString().equalsIgnoreCase("-1")){
                valor = "";
            }
        }else if("RV7".equals(jrf.getName())) { 
            //si nos pasan un partido vacío convertimos el -1 en un hueco
            valor = valueOf(partidosInforme.get(indice).getRV7());
            if(valor.toString().equalsIgnoreCase("-1")){
                valor = "";
            }
        }else if("RL8".equals(jrf.getName())) { 
            valor = valueOf(partidosInforme.get(indice).getRL8());
            //si nos pasan un partido vacío convertimos el -1 en un hueco
            if(valor.toString().equalsIgnoreCase("-1")){
                valor = "";
            }
        }else if("RV8".equals(jrf.getName())) { 
            valor = valueOf(partidosInforme.get(indice).getRV8());
            //si nos pasan un partido vacío convertimos el -1 en un hueco
            if(valor.toString().equalsIgnoreCase("-1")){
                valor = "";
            }
        }else if("RL9".equals(jrf.getName())) { 
            valor = valueOf(partidosInforme.get(indice).getRL9());
            //si nos pasan un partido vacío convertimos el -1 en un hueco
            if(valor.toString().equalsIgnoreCase("-1")){
                valor = "";
            }
        }else if("RV9".equals(jrf.getName())) { 
            valor = valueOf(partidosInforme.get(indice).getRV9());
            //si nos pasan un partido vacío convertimos el -1 en un hueco
            if(valor.toString().equalsIgnoreCase("-1")){
                valor = "";
            }
        }else if("RL10".equals(jrf.getName())) { 
            valor = valueOf(partidosInforme.get(indice).getRL10());
            //si nos pasan un partido vacío convertimos el -1 en un hueco
            if(valor.toString().equalsIgnoreCase("-1")){
                valor = "";
            }
        }else if("RV10".equals(jrf.getName())) { 
            valor = valueOf(partidosInforme.get(indice).getRV10());
            //si nos pasan un partido vacío convertimos el -1 en un hueco
            if(valor.toString().equalsIgnoreCase("-1")){
                valor = "";
            }
        }else if("RL11".equals(jrf.getName())) { 
            //si nos pasan un partido vacío convertimos el -1 en un hueco
            valor = valueOf(partidosInforme.get(indice).getRL11());
            if(valor.toString().equalsIgnoreCase("-1")){
                valor = "";
            }
        }else if("RV11".equals(jrf.getName())) { 
            valor = valueOf(partidosInforme.get(indice).getRV11());
            //si nos pasan un partido vacío convertimos el -1 en un hueco
            if(valor.toString().equalsIgnoreCase("-1")){
                valor = "";
            }
        }else if("RL12".equals(jrf.getName())) { 
            //si nos pasan un partido vacío convertimos el -1 en un hueco
            valor = valueOf(partidosInforme.get(indice).getRL12());
            if(valor.toString().equalsIgnoreCase("-1")){
                valor = "";
            }
        }else if("RV12".equals(jrf.getName())) { 
            valor = valueOf(partidosInforme.get(indice).getRV12());
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
    }//End getFieldValue()
    
}//End class

