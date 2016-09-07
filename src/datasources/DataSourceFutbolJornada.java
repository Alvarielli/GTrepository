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

import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import partidos.Partido;

/**
 *
 * @author Alvarielli
 */
public class DataSourceFutbolJornada implements JRDataSource{

    private List<Partido> partidosInforme = new ArrayList<>();
    private int indice = -1;
     
    @Override
    public boolean next() throws JRException
    {
        return ++indice < partidosInforme.size();
    }

    public void addPartido(Partido partido)
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
        }else if("pista".equals(jrf.getName())) { 
            valor = partidosInforme.get(indice).getPista();
        }
        
        return valor; 
    }
    
}
