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


import equipos.EquipoHockeyHielo;
import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

/**
 * También para fútbol americano
 * @author Alvarielli
 */

public class DataSourceHHieloClasificacion implements JRDataSource{

    private List<EquipoHockeyHielo> equiposInforme = new ArrayList<>();
    private int indice = -1;
     
    @Override
    public boolean next() throws JRException
    {
        return ++indice < equiposInforme.size();
    }

    public void addEquipo(EquipoHockeyHielo equipo)
    {
        this.equiposInforme.add(equipo);
    }

    @Override
    public Object getFieldValue(JRField jrf) throws JRException {
        Object valor = null;  

        if("posicion".equals(jrf.getName())) { 
            valor = equiposInforme.get(indice).getPosicion() + 1;//Debemos sumar 1 a la posición ya que viene como índice 
        }else if("nombre".equals(jrf.getName())) { 
            valor = equiposInforme.get(indice).getNombre(); 
        }else if("pj".equals(jrf.getName())) { 
            valor = equiposInforme.get(indice).getPj(); 
        }else if("pg".equals(jrf.getName())) { 
            valor = equiposInforme.get(indice).getPg(); 
        }else if("pgp".equals(jrf.getName())) { 
            valor = equiposInforme.get(indice).getPgp();
        }else if("ppp".equals(jrf.getName())) { 
            valor = equiposInforme.get(indice).getPpp();
        }else if("pp".equals(jrf.getName())) { 
            valor = equiposInforme.get(indice).getPp();
        }else if("gf".equals(jrf.getName())) { 
            valor = equiposInforme.get(indice).getGf();
        }else if("gc".equals(jrf.getName())) { 
            valor = equiposInforme.get(indice).getGc();
        }else if("ptos".equals(jrf.getName())) { 
            valor = equiposInforme.get(indice).getPtos();
        }
        return valor; 
    } 
}
