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

import partidos.PartidoRugby;
import partidos.PartidoTenis5Sets;
import partidos.PartidoHockeyHielo;
import partidos.PartidoBeisbol;
import partidos.PartidoAjedrez;
import partidos.Partido;
import partidos.PartidoTenis3Sets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import partidos.PartidoEliminatoria;
import partidos.PartidoEliminatoriaAjedrez;
import partidos.PartidoEliminatoriaBeisbol;
import partidos.PartidoEliminatoriaFutbol;
import partidos.PartidoEliminatoriaTenis3Sets;
import partidos.PartidoEliminatoriaTenis5Sets;

/**
 *
 * @author Hector
 */
public class Generador {   
    
      private ArrayList<String> equipos = new ArrayList<>();//Obtenemos los nombres del ArrayList de Equipos para usarlos para crear las jornadas
      private static Calendario calendario = new Calendario();
      private static ArrayList<String> izquierdaEOGen = new ArrayList<>();
      private static ArrayList<String> derechaEOGen = new ArrayList<>();
      
      //Variables necesaria para los equipos opuestos
      private static String equipoOpuesto;
      private static ArrayList<String> equiposOpuestosUtilizados = new ArrayList<>();//arraylist que guarda los equipos opuestos utilizados
      private static String[] columnaAux1;
      private static String[] columnaAux2;
    
    //En lugar de recibir  un ArrayList de Equipos lo recibe de Strings, pues aun no sabe de que deporte va a ser el Equipo (sólo tenemos su nombre de momento)
    public void crearJornadas(int jornadas, ArrayList<String> teams, boolean idaVuelta, int deporte, int sets){
        
        //Al crear un nuevo calendario de Jornadas vaciamos los equipos y el calendario para asegurarnos de que no se repiten datos (de que es nuevo)
        equipos.clear();
        calendario.jornadas.clear();
        
         //Si los equipos son impares añadimos un equipo fantasma
        if(teams.size() % 2 != 0){
            equipos.add("EquipoFantasma");
        }                 
          
        //Recorremos el ArrayList de equipos y añadimos los nombres de estos al ArrayList de Strings
        Iterator<String> it = teams.iterator();
        while(it.hasNext()){
            String elemento = it.next();
            equipos.add(elemento);
        }//End while
        
        //Variable que guarda el número de equipos
         int num_equipos = equipos.size();
        
        //Ahora procedemos a ordenarlos de forma aleatoria
        Collections.shuffle(equipos);
        
        //Creamos las jornadas de liga y sus partidos
        int num_jornadas = num_equipos - 1;
              
        /*Creamos dos arrays que guarden las posiciones del sorteo, serán dos
          columnas, una contendrá de la mitad al final y la otra del principio a la mitad
          colocados al revés
        */
        String[] columna2 = new String[num_equipos];
        //Rellenamos al revés la columna 1
        String[] columna1 = new String[num_equipos];
        //Iniciamos las columnas auxiliares
        columnaAux1 = new String[num_equipos];
        columnaAux2 = new String[num_equipos];
        //Coloca a los equipos en las columnas (si hay equipos opuestos utiliza otro método)
        if(izquierdaEOGen.size() > 0){
            colocarPosicionesEquiposOpuestos(columna1, columna2, num_equipos, equipos);
        }else{
            colocarPosiciones(columna1, columna2, num_equipos, equipos);
        }
        //Mostramos el calendario resultante
        rellenarCalendario(columna1, columna2, num_jornadas, num_equipos, idaVuelta, equipos, deporte, sets);
    }//End crearJornadas()    
    
    /**
     * Columna1 IZDA y Columna2 DCHA
     * Este método se encarga de reasignar las posiciones de los equipos en el calendario para que jueguen todos contra todos
     * Los parámetros columna contienen los equipos de cada columna, jornada el número de jornada actual y num_equipos el número total de equipos
     * num_equipos es necesario para usarlo en el índice de columna1[] y 2, ya que en éstos no se puede usar columna.length
     * 
     * Las jornadas pares tienen una solución y las impares otra, actuamos tras la jornada 1
     * 
     * Las pares cogen el último equipo de la columna izda. y los pasan a la primera de la derecha el resto de partidos
     * pasan debajo en el mismo orden, a su vez, todos los partidos de la columna izda. pasan a la columna dcha.
     * 
     * Las impares mantienen en su posición al primer equipo de la columna izda. y al último de la dcha. El primero de la dcha.
     * pasa a ser el último de la izda. (como en la jornada primera) y el resto de la columna derecha (entre el primero y el último)
     * pasan en similar posición a la columna izda. , mientras que los equipos de la columna izda. menos el primero pasan a la columna
     * dcha. más una posición hacia arriba (el índice - 1)
     * 
     * Cuando los equipos son impares y descansan, se produce el efecto de que un equipo juega 3 veces seguidas en casa (y luego fuera) o viceversa, pero eso
     * ocurre porque en lo jornada de descanso se simula que juega fuera (y luego en casa) contra el equipo fantasma (Ver Tarazona 2013/2014 jornadas 16 - 19)
     * 
     * Para 4 o 3 equipos (+ 1 fantasma) hay que utilizar el calendario "Champions" (con 3 da el pego por los descansos, pero no funciona en teoría)
     * 
     * @param columna1
     * @param columna2
     * @param jornada
     * @param num_equipos 
     */
    public static void cambiarPosiciones(String[] columna1, String[] columna2, int jornada, int num_equipos, int num_jornadas){
        String aux;//Variable auxiliar que guarda un equipo
        String reloj_aux; //Variable auxiliar para jornadas impares
        String reloj_aux2; //Segunda variable auxiliar para jornadas impares
        String[] columna_aux = new String[columna1.length]; //Columna auxiliar para el intercambio de columnas
        jornada = jornada + 1; //Indicamos que será el calendario para la siguente jornada              
        
        //para la ÚLTIMA JORNADA (la última la ponemos antes para revertir los cambios realizados en la penúltima antes de mover las columnas)
        if(jornada == num_jornadas){
            //Revertimos en primer lugar los cambios de la jornada 18
            aux = columna1[0];
            columna1[0] = columna2[0];
            columna2[0] = aux;
        }
            //LA JORNADA ES PAR **********************************************************************************************
                if(jornada % 2 == 0){
                    //Recogemos los valores auxiliares, el equipo final de la columna 2 y la columna1 entera
                    aux = columna2[(num_equipos/2) - 1];//-1 porque los usamos de índice y num_equipos porque no se puede usar columna2.length en la misma columna
                    //Copiamos el array columna1 en el array auxiliar
                    System.arraycopy(columna1, 0, columna_aux, 0, (columna1.length - 1));
                    //Pasamos todos los equipos de la columna2 menos el último a un puesto después de la columna1
                    for (int i = (columna2.length - 2); i >= 1; i--) {
                          columna1[i] = columna2[i-1];
                    }
                    //Procedemos como tercer paso al intercambio de columnas                   
                    System.arraycopy(columna_aux, 0, columna2, 0, (columna1.length - 1));
                    //Asignamos el valor auxiliar al primer valor de la columna
                    columna1[0] = aux;
            // LA JORNADA ES IMPAR ********************************************************************************************
                }else{
                    //Guardamos los valores que mantienen posición
                    aux = columna2[0];
                    reloj_aux = columna1[(num_equipos/2) - 1];//-1 porque los usamos de índicce
                    reloj_aux2 = columna1[0];//Equipo que va cambiando de esquina
                    //Representamos el giro contrario a las agujas del reloj
                    
                    //Guardamos la columna1 en la columna auxiliar
                    System.arraycopy(columna1, 0, columna_aux, 0, (columna1.length - 1));
                    //Establecemos los primeros valores de la columna1 (El último es un auxiliar (de ahí el -1))
                    for (int i = 0; i < columna1.length - 1; i++) {
                        columna1[i] = columna2[i+1];
                    }
                    //Ahora establecemos los valores de la columna2
                    System.arraycopy(columna_aux, 1, columna2, 1, (columna1.length - 1));
                    //Finalmente asignamos los valores auxiliares
                    columna2[0] = aux;
                    columna1[(num_equipos/2) - 1] = reloj_aux;
                    columna2[(num_equipos/2) - 1] = reloj_aux2;
                }//End jornada % 2 == 0
                
        /*Las jornadas de la segunda vuelta se confeccionan intercambiando las columnas de las correspondientes jornadas de la primera vuelta. 
          Para evitar que un equipo juegue tres veces seguidas en su casa, el equipo comodín se intercambia con su contrario en las 
          dos últimas jornadas de la primera vuelta.
        */
                //para la JORNADA PENÚLTIMA
        if(jornada == (num_jornadas - 1)){
            //Invertimos el partido del equipo comodín (si le tocaba fuera, le toca en casa)
            aux = columna1[0];
            columna1[0] = columna2[0];
            columna2[0] = aux;
            //para la ÚLTIMA JORNADA (cambios de la penultima revertidos en la parte de arriba del método)
        }else if(jornada == num_jornadas){
            //Invertimos el partido del equipo comodín, esta vez en el final de la tabla
            aux = columna1[(num_equipos/2) - 1];
            columna1[(num_equipos/2) - 1] = columna2[(num_equipos/2) - 1];
            columna2[(num_equipos/2) - 1] = aux;
        }
    }//End cambiarPosiciones
    
    /**
     * Método que se encarga de colocar a los equipos en sus posiciones iniciales (una vez hecho el shuffle)
     * La columna1 va invertida, de ahí la necesidad del índice para colocar bien los equipos
     * @param columna1
     * @param columna2
     * @param num_equipos
     * @param equipos 
     */
    private static void colocarPosiciones(String[] columna1, String[] columna2, int num_equipos, ArrayList<String> equipos){
        for (int i = num_equipos/2; i < num_equipos; i++) {
            //Para que empiece la columna2 en el índice 0 le restamos a éste el
            //número de equipos / 2 y el índice irá creciendo de 1 en 1
            columna2[i - (num_equipos/2)] = equipos.get(i);
        }           
        
        //Índice auxiliar para invertir la selección de equipos
        int indice_aux = 0;
        //Rellenamos al revés la columna 1
        for (int i = (num_equipos/2 - 1); i >= 0; i--) {
            columna1[indice_aux] = equipos.get(i);
            ++indice_aux;
        }
    }//End colocarPosiciones()
    
    /**
     * Método que se encarga de colocar a los equipos en sus posiciones iniciales (una vez hecho el shuffle)
     * La columna1 va invertida, de ahí la necesidad del índice para colocar bien los equipos
     * @param columna1
     * @param columna2
     * @param num_equipos
     * @param equipos 
     */
    private static void colocarPosicionesEquiposOpuestos(String[] columna1, String[] columna2, int num_equipos, ArrayList<String> equipos){
        String equipoOpuesto;
        int numeroAleatorio;
        ArrayList<String> equiposOpuestosUtilizados = new ArrayList<>();//arraylist que guarda los equipos opuestos utilizados
        int[] numerosAleatoriosUtilizados = new int[num_equipos/2];
        /**
         * Lo primero de todo es colocar los equipos opuestos en sus posiciones correspondientes sin alterar el espíritu del sorteo
         */
        //En primer lugar rellenamos el array de -1 para que no nos diga que el 0 ya ha aparecido
        for (int i = 0; i < numerosAleatoriosUtilizados.length; i++) {
            numerosAleatoriosUtilizados[i] = -1;
        }//end for
        //Recorremos el ArrayList en busca de equipos opuestos
        for (int i = 0; i < izquierdaEOGen.size(); i++) {
            //Obtenemos el equipo opuesto de la lista y lo colocamos en un lugar aleatorio de la primera columna
            equipoOpuesto = izquierdaEOGen.get(i);
            equiposOpuestosUtilizados.add(equipoOpuesto);
            //Con este do/while y el método comprobarArrayEnteros() nos aseguramos de que no se repite el número aleatorio
            do{
               numeroAleatorio = (int) (Math.random()* num_equipos/2);//Si son 10 serían del 0 al 9 (Random mantiene vivo el sorteo en las posiciones)
            }while(comprobarArrayEnteros(numerosAleatoriosUtilizados, numeroAleatorio));
            numerosAleatoriosUtilizados[i] = numeroAleatorio;//Si no estaba en el array añadimos el número aleatorio a la lista
            columna1[numeroAleatorio] = equipoOpuesto;
            //Ahora en función del número que ha salido colocaremos su opuesto en su posición correspondiente en la columna 2    
            if(numeroAleatorio == (num_equipos/2 - 1)){
                columna2[0] = derechaEOGen.get(i);
                equiposOpuestosUtilizados.add(derechaEOGen.get(i));
            }else if(numeroAleatorio == (num_equipos/2 - 2)){
                columna2[(num_equipos/2) - 1] = derechaEOGen.get(i);
                equiposOpuestosUtilizados.add(derechaEOGen.get(i));
            }else{
                columna2[((num_equipos/2 - 1) - numeroAleatorio) - 1] = derechaEOGen.get(i);
                equiposOpuestosUtilizados.add(derechaEOGen.get(i));
            }
        }//end for
        
        int j = 0;//Variable necesaria para poder separar el índice de las casillas del índice del arraylist de equipos
        for (int i = 0; i < num_equipos; i++) {
            //Para que empiece la columna2 en el índice 0 le restamos a éste el
            //número de equipos / 2 y el índice irá creciendo de 1 en 1 
            if(j < num_equipos){//si j ya tiene el valor de num_equipos ignoramos el while
                while(equiposOpuestosUtilizados.contains(equipos.get(j))){
                    j++;//Si el equipo actual está en la lista de utilizados añadimos uno al índice j
                    if(j == num_equipos){break;}//salimos del while si ya están todos los equipos comprobados                                 
                }//end while
            }//end if
            //Rellenamos la columna 1 o la 2 en función de la posición del índice si ha pasado o no de la mitad
            if(i <= (num_equipos/2 - 1)){
                if(columna1[i] == null){
                    columna1[i] = equipos.get(j);  
                }else{
                    j--;//Quitando uno que se añadirá después hacemos que guarde el equipo si la casilla está llena
                }
            }else{
                if(columna2[i - (num_equipos/2)] == null){
                    columna2[i - (num_equipos/2)] = equipos.get(j);  
                }else{
                    j--;//Quitando uno que se añadirá después hacemos que guarde el equipo si la casilla está llena
                }
            }          
            //Mientras j sea menor que el número total de equipos añadimos +1
            //if(j < num_equipos){
             j++;//Añadimos uno al final para apuntar al siguiente equipo
            //}
        }
        System.arraycopy(columna1, 0, columnaAux1, 0, (columna1.length - 1));
        System.arraycopy(columna2, 0, columnaAux2, 0, (columna2.length - 1));
    }//End colocarPosicionesEquiposOpuestos()
    
    /**
     * Método que utilizamos para comprobar si un número se encuentra dentro de un array (para los números aleatorios al asignar los equipos opuestos)
     * @param enteros
     * @param numero
     * @return 
     */
    public static boolean comprobarArrayEnteros(int[] enteros, int numero){
        for (int i = 0; i < enteros.length; i++) {
            if(enteros[i] == numero){
                   return true;
            }else{
                //no hacemos nada porque queremos que siga comprobando el resto de números
            }//end if
        }//end for
        //Si al final del for no ha encontrado el número devolvemos el false
        return false;
    }//End of comprobarArrayEnteros()
       
    /**
     * Método que muestra las jornadas del calendario (incluidos los equipos que descansan) y que recibie la variable idaVuelta para saber si hay ida y vuelta,
     * y que al utilizar los métodos colocarPosiciones y cambiarPosiciones requiere los distintos parámetros que se ven cómo el ArrayList equipos, el número de
     * equipos, el número de jornadas, etc. (Si no hay vuelta se puede obviar algún parámetro, pues no se usará el método colocarPosiciones() ).
     * @param columna1
     * @param columna2
     * @param num_jornadas
     * @param num_equipos
     * @param idaVuelta
     * @param equipos 
     */
    private static void rellenarCalendario(String[] columna1, String[] columna2, int num_jornadas, int num_equipos, boolean idaVuelta, ArrayList<String> equipos, int deporte, int sets){
        
        //Si el número de equipos es 3 o 4 utilizamos el método adecuado para esta excepción (idaVuelta tiene que ser true)
        if((num_equipos == 3 || num_equipos == 4) && idaVuelta == true){
            //Indicamos 6 porque sabemos que son 6 jornadas
             for (int i = 0; i < 6; i++) {
                 int jornada = i+1;//Sumamos una jornada
                //Creamos una jornada nueva
                Jornada nuevaJornada = new Jornada(jornada);
                for (int j = 0; j < 2; j++) {  
                    //Método que crea el partido en función del deporte pasado y lo añade a la nuevaJornada
                    seleccionarTipoPartido(deporte, columna1, columna2, j, nuevaJornada, sets);
                }
                calendario.añadirJornada(nuevaJornada);

                //Cambiamos las posiciones asignadas con el método de torneos de 3 y 4 equipos
                cambiarPosicionesTorneo4(columna1, columna2, jornada, num_equipos, 6);
             }
        }else{
            //Mientras haya jornadas...
            for (int i = 0; i < num_jornadas; i++) {
                int jornada = i+1;//Sumamos una jornada
                //Creamos una jornada nueva
                Jornada nuevaJornada = new Jornada(jornada);
                //Carga tantos partidos como sea necesario
                for (int j = 0; j < (num_jornadas+1)/2; j++) {               
                    //Método que crea el partido en función del deporte pasado y lo añade a la nuevaJornada
                    seleccionarTipoPartido(deporte, columna1, columna2, j, nuevaJornada, sets);               
                }            
                calendario.añadirJornada(nuevaJornada);

                //Cambiamos las posiciones asignadas
                cambiarPosiciones(columna1, columna2, jornada, num_equipos, num_jornadas);  
            }

            //Si hay ida y vuelta
            if(idaVuelta){
                //Devolvemos a los equipos a sus posiciones iniciales e invertimos las columnas
                    if(izquierdaEOGen.size() > 0){
                        //Ignoramos el método colocarPosiciones, pues vamos a utilizar los arrays auxiliares
                    }else{
                        colocarPosiciones(columna1, columna2, num_equipos, equipos);                
                    }
                //}
                //Añadimos aquí el resto de jornadas de la vuelta
                for (int i = 0; i < num_jornadas; i++) {
                    int jornada = i+1;
                    //Sumamos al número de jornadas cada jornada
                    //Añadimos una nueva Jornada
                    Jornada nuevaJornada = new Jornada(jornada + num_jornadas);
                    //Si el torneo cuenta con más de 3 o 4 equipos aplicamos lo habitual
                    if(num_equipos != 3 || num_equipos != 4){                   
                        //Invertimos las columnas en el for
                        for (int j = 0; j < (num_jornadas+1)/2; j++) {
                           //Método que crea el partido en función del deporte pasado y lo añade a la nuevaJornada
                           if(izquierdaEOGen.size() > 0){
                                seleccionarTipoPartido(deporte, columnaAux2, columnaAux1, j, nuevaJornada, sets);
                            }else{
                                seleccionarTipoPartido(deporte, columna2, columna1, j, nuevaJornada, sets);               
                            }
                        }//end for
                    }else{
                        //En caso de calendario champions invertimos la salida de los equipos
                        for (int j = 0; j < (num_jornadas+1)/2; j++) {
                            //Método que crea el partido en función del deporte pasado y lo añade a la nuevaJornada
                            if(izquierdaEOGen.size() > 0){
                                seleccionarTipoPartido(deporte, columnaAux1, columnaAux2, j, nuevaJornada, sets);
                            }else{
                                seleccionarTipoPartido(deporte, columna1, columna2, j, nuevaJornada, sets);               
                            }
                        }
                    }
                    //Añadimos la jornada al Calendario (método de la clase Calendario)
                    calendario.añadirJornada(nuevaJornada);

                    //Cambiamos las posiciones asignadas
                    if(izquierdaEOGen.size() > 0){
                        cambiarPosiciones(columnaAux1, columnaAux2, jornada, num_equipos, num_jornadas); 
                    }else{
                        cambiarPosiciones(columna1, columna2, jornada, num_equipos, num_jornadas);                
                    }                
                }
            }//Fin del if de idaVuelta
        }//Fin del if de torneo de 4
    }//End mostrarCalendario()
    
    public Calendario obtenerCalendario(){
        return calendario;
    }    
    
    /**
     * Asigna las posiciones de los equipos en el calendario cuando son de 3 a 4 equipos y con ida y vuelta para que no se repitan 3 partidos seguidos en casa o fuera (calendario Champions). 
     * La razón por la que se asignan los equipos de forma manual en el array es porque este calendario no parece responder a ningún tipo de fórmula matemática 
     * @param columna1
     * @param columna2
     * @param jornada
     * @param num_equipos
     * @param num_jornadas 
     */
    public static void cambiarPosicionesTorneo4(String[] columna1, String[] columna2, int jornada, int num_equipos, int num_jornadas){
        //Declaramos un grupo de variables String, una por cada uno de los 4 equipos 
        String A, B, C, D;
        jornada = jornada + 1; //Indicamos que será el calendario para la siguente jornada         
        
        //Los equipos vienen colocados en la jornada 1 como haya decretado el sorteo
        if(jornada == 2){
            //Recordar que la columna 2 es la de los equipos locales
            A = columna2[0];
            B = columna1[0];
            C = columna2[1];
            D = columna1[1];
            //Guardamos los valores actuales en las variables y los asignamos a sus nuevas posiciones
            columna2[0] = D;
            columna1[0] = A;
            columna2[1] = B;
            columna1[1] = C;
        }else if(jornada == 3){
            //Recordar que la columna 2 es la de los equipos locales
            A = columna1[0];
            B = columna2[1];
            C = columna1[1];
            D = columna2[0];
            //Guardamos los valores actuales en las variables y los asignamos a sus nuevas posiciones
            columna2[0] = D;
            columna1[0] = B;
            columna2[1] = C;
            columna1[1] = A;
        }else if(jornada == 4){
           //Recordar que la columna 2 es la de los equipos locales
            A = columna1[1];
            B = columna1[0];
            C = columna2[1];
            D = columna2[0];
            //Guardamos los valores actuales en las variables y los asignamos a sus nuevas posiciones
            columna2[0] = A;
            columna1[0] = C;
            columna2[1] = B;
            columna1[1] = D; 
        }else if(jornada == 5){
            //Recordar que la columna 2 es la de los equipos locales
            A = columna2[0];
            B = columna2[1];
            C = columna1[0];
            D = columna1[1];
            //Guardamos los valores actuales en las variables y los asignamos a sus nuevas posiciones
            columna2[0] = D;
            columna1[0] = C;
            columna2[1] = B;
            columna1[1] = A;        
        }else if(jornada == 6){
            //Recordar que la columna 2 es la de los equipos locales
            A = columna1[1];
            B = columna2[1];
            C = columna1[0];
            D = columna2[0];
            //Guardamos los valores actuales en las variables y los asignamos a sus nuevas posiciones
            columna2[0] = A;
            columna1[0] = D;
            columna2[1] = C;
            columna1[1] = B; 
        }
        
    }//End cambiarPosicionesTorneo4
    
/**
     * Método que elige el tipo de partido a crear (por las diferentes columnas de la tabla)
     * en función del deporte que le sea pasado
     * @param deporte 
     * @param columna1 
     * @param columna2 
     * @param j 
     * @param nuevaJornada 
     * @param sets 
     */
    public static void seleccionarTipoPartido(int deporte, String[] columna1, String[]columna2, int j, Jornada nuevaJornada, int sets){
        switch(deporte){
            case 2:
                if(sets == 3){
                    PartidoTenis3Sets partidoT3s = new PartidoTenis3Sets(columna2[j], columna1[j]);
                    //Añadimos el partido a la Jornada
                    nuevaJornada.añadirPartido(partidoT3s);
                }else if(sets == 5){
                    PartidoTenis5Sets partidoT5s = new PartidoTenis5Sets(columna2[j], columna1[j]);
                    //Añadimos el partido a la Jornada
                    nuevaJornada.añadirPartido(partidoT5s);
                }else{
                    System.out.println("Error al crear partidos de Tenis con los sets");
                }
                break;
            case 8:
               PartidoHockeyHielo partidoHH = new PartidoHockeyHielo(columna2[j], columna1[j]);
                //Añadimos el partido a la Jornada
                nuevaJornada.añadirPartido(partidoHH);  
               break;
            case 9:
               PartidoRugby partidoRu = new PartidoRugby(columna2[j], columna1[j]);
                //Añadimos el partido a la Jornada
                nuevaJornada.añadirPartido(partidoRu);  
               break;
            case 10:
            case 11:
            case 13:
            case 16:
                PartidoTenis3Sets partidoT3s = new PartidoTenis3Sets(columna2[j], columna1[j]);
                //Añadimos el partido a la Jornada
                nuevaJornada.añadirPartido(partidoT3s);
                break;
            case 12:
            case 15:
                PartidoTenis5Sets partidoT5s = new PartidoTenis5Sets(columna2[j], columna1[j]);
                //Añadimos el partido a la Jornada
                nuevaJornada.añadirPartido(partidoT5s);
                break;
            case 17:
                PartidoAjedrez partidoChess = new PartidoAjedrez(columna2[j], columna1[j]);
                //Añadimos el partido a la Jornada
                nuevaJornada.añadirPartido(partidoChess);
                break;
            case 18:
            case 19:
                PartidoBeisbol partidoBb= new PartidoBeisbol(columna2[j], columna1[j]);
                //Añadimos el partido a la Jornada
                nuevaJornada.añadirPartido(partidoBb);
                break;
            default:
                Partido partido = new Partido(columna2[j], columna1[j]);
                //Añadimos el partido a la Jornada
                nuevaJornada.añadirPartido(partido);  
        }
    }
    
   /****************** MÉTODOS PARA LAS ELIMINATORIAS ********************/
     
   /**
    * 
    * @param jornadas
    * @param teams
    * @param idaVuelta
    * @param deporte
     * @param sorteo
     * @param tercerCuartoPuesto
    * @param sets 
    */
    public void crearEliminatorias(int jornadas, ArrayList<String> teams, boolean idaVuelta, int deporte, boolean sorteo, boolean tercerCuartoPuesto, int sets){

        //Al crear un nuevo calendario de Jornadas vaciamos los equipos y el calendario para asegurarnos de que no se repiten datos (de que es nuevo)
        equipos.clear();
        calendario.jornadas.clear();                  
          
        //Recorremos el ArrayList de equipos y añadimos los nombres de estos al ArrayList de Strings
        Iterator<String> it = teams.iterator();
        while(it.hasNext()){
            String elemento = it.next();       
            equipos.add(elemento);
        }//End while

        //Realizamo o no el sorteo en función de lo escogido por el usuario
        if(sorteo){        
            Collections.shuffle(equipos);
        }
        
        //Ahora añadimos los equipos de relleno
        int equiposRelleno = (int)Math.pow(2, jornadas) - teams.size();
        //Obtenemos el número de equipos que faltan para rellenar las eliminatorias con Equipos Fantasma
        for (int i = 0; i < equiposRelleno; i++) {          
            equipos.add("");//Equivale al EquipoFantasma de las ligas
        } 
        
        //Variable que guarda el número de equipos
         int num_equipos = equipos.size();
                   
        /*Creamos dos arrays que guarden las posiciones del sorteo, serán dos
          columnas, una contendrá de la mitad al final y la otra del principio a la mitad
          colocados al revés
        */
        String [] columna2 = new String[num_equipos/2];
        //Rellenamos al revés la columna 1
        String [] columna1 = new String[num_equipos/2]; 
        
        //Coloca a los equipos en las columnas
        colocarPosicionesEliminatoria(columna1, columna2, num_equipos, equipos);
        //Mostramos el calendario resultante
        rellenarCalendarioEliminatoria(columna1, columna2, jornadas, num_equipos, idaVuelta, equipos, deporte, tercerCuartoPuesto, sets);
    }
    
    /**
     * Método que elige el tipo de partido a crear (por las diferentes columnas de la tabla)
     * en función del deporte que le sea pasado
     * @param deporte 
     * @param columna1 
     * @param columna2 
     * @param j 
     * @param nuevaJornada 
     * @param sets 
     */
    public static void seleccionarTipoPartidoEliminatoria(int deporte, String[] columna1, String[]columna2, int j, Jornada nuevaJornada, int sets){
        switch(deporte){
            case 2:
                if(sets == 3){
                    PartidoEliminatoriaTenis3Sets partidoT3s = new PartidoEliminatoriaTenis3Sets(columna2[j], columna1[j]);
                    //Añadimos el partido a la Jornada
                    nuevaJornada.añadirPartido(partidoT3s);
                }else if(sets == 5){
                    PartidoEliminatoriaTenis5Sets partidoT5s = new PartidoEliminatoriaTenis5Sets(columna2[j], columna1[j]);
                    //Añadimos el partido a la Jornada
                    nuevaJornada.añadirPartido(partidoT5s);
                }else{
                    System.out.println("Error al crear partidos de Tenis con los sets");
                }
                break;
            case 10:
            case 11:
            case 13:
            case 16:
                PartidoEliminatoriaTenis3Sets partidoT3s = new PartidoEliminatoriaTenis3Sets(columna2[j], columna1[j]);
                //Añadimos el partido a la Jornada
                nuevaJornada.añadirPartido(partidoT3s);
                break;
            case 12:
            case 15:
                PartidoEliminatoriaTenis5Sets partidoT5s = new PartidoEliminatoriaTenis5Sets(columna2[j], columna1[j]);
                //Añadimos el partido a la Jornada
                nuevaJornada.añadirPartido(partidoT5s);
                break;
            case 17:
                PartidoEliminatoriaAjedrez partidoChess = new PartidoEliminatoriaAjedrez(columna2[j], columna1[j]);
                //Añadimos el partido a la Jornada
                nuevaJornada.añadirPartido(partidoChess);
                break;
            case 18:
            case 19:
                PartidoEliminatoriaBeisbol partidoBb= new PartidoEliminatoriaBeisbol(columna2[j], columna1[j]);
                //Añadimos el partido a la Jornada
                nuevaJornada.añadirPartido(partidoBb);
                break;
            default:
                PartidoEliminatoria partido = new PartidoEliminatoriaFutbol(columna2[j], columna1[j]);
                //Añadimos el partido a la Jornada
                nuevaJornada.añadirPartido(partido);  
        }
    }
    
    /**
     * Método que muestra las jornadas del calendario (incluidos los equipos que descansan) y que recibie la variable idaVuelta para saber si hay ida y vuelta,
     * y que al utilizar los métodos colocarPosiciones y cambiarPosiciones requiere los distintos parámetros que se ven cómo el ArrayList equipos, el número de
     * equipos, el número de jornadas, etc. (Si no hay vuelta se puede obviar algún parámetro, pues no se usará el método colocarPosiciones() ).
     * @param columna1
     * @param columna2
     * @param num_jornadas
     * @param num_equipos
     * @param idaVuelta
     * @param equipos 
     */
    private static void rellenarCalendarioEliminatoria(String[] columna1, String[] columna2, int num_jornadas, int num_equipos, boolean idaVuelta, ArrayList<String> equipos, int deporte, boolean tercerCuartoPuesto, int sets){      
        ArrayList<String> auxEquipos = new ArrayList<>();
         int jornada = 1;//Añadimos la primera jornada
        //Creamos una jornada nueva
        Jornada nuevaJornada = new Jornada(jornada);
        //Carga tantos partidos como sea necesario
        for (int j = 0; j < (num_equipos)/2; j++) {               
            //Método que crea el partido en función del deporte pasado y lo añade a la nuevaJornada
            seleccionarTipoPartidoEliminatoria(deporte, columna1, columna2, j, nuevaJornada, sets);                               
        }            
        calendario.añadirJornada(nuevaJornada);
        
        //Si no hay rival asignamos como ganador al participante sin rival
        Jornada J = calendario.jornadas.get(jornada - 1);//jornada 1
        for (PartidoEliminatoria partido :(ArrayList<PartidoEliminatoria>) J.getListaPartidos())
            //Si el local o el visitante no tienen rival les asignamos como ganadores
            if(partido.getLocal().equalsIgnoreCase("")){
               partido.setGanador("VISITANTE");
            }else if(partido.getVisitante().equalsIgnoreCase("")){
                partido.setGanador("LOCAL"); 
            }else{
                //getModelo().addRow(partido.getTableRepresentation()); 
        }
        
        //Mientras haya eliminatorias vamos creando jornadas
        while(jornada < num_jornadas){
            auxEquipos.clear();//vaciamos el arrayList auxiliar
            //Comprobamos la jornada actual y añadimos los equipos ganadores al nuevo ArrayList con el que formaremos la siguiente ronda
            Jornada J2 = calendario.jornadas.get(jornada - 1);          
            for (PartidoEliminatoria partido :(ArrayList<PartidoEliminatoria>) J2.getListaPartidos())
                //Si el local o el visitante no tienen rival les asignamos como ganadores
                if(partido.getGanador().equalsIgnoreCase("LOCAL")){
                   auxEquipos.add(partido.getLocal()); 
                }else if(partido.getGanador().equalsIgnoreCase("VISITANTE")){
                    auxEquipos.add(partido.getVisitante());
                }else{
                    //getModelo().addRow(partido.getTableRepresentation()); 
                }
            
            //creamos las columnas en función del tamaño del ArrayList auxiliar
            String[] auxColumna1 = new String[auxEquipos.size()/2];
            String[] auxColumna2 = new String[auxEquipos.size()/2];
            int indice = 0;//índice auxiliar que nos ayudará a poner cada equipo en su hueco correspondiente
            //Rellenamos las columnas con los equipos del ArrayList auxiliar
             for (int i = 0; i < auxColumna2.length; i++) {
                    auxColumna2[i] = auxEquipos.get(indice);
                    indice+=2;
             }
             indice = 1;//vaciamos el indice acabado el for
             for (int i = 0; i < auxColumna1.length; i++) {
                    auxColumna1[i] = auxEquipos.get(indice);
                    indice+=2;  
             }
             indice = 0;//vaciamos el indice acabado el for
             
            //Creamos una nueva joranda y la añadimos al calendario
            Jornada Jadd = new Jornada(jornada + 1);       
            //Añadimos los partidos a la Jornada
            for (int j = 0; j < auxEquipos.size()/2; j++) {
                seleccionarTipoPartidoEliminatoria(deporte, auxColumna1, auxColumna2, j, Jadd, sets); 
                /*PartidoEliminatoria partido = new PartidoEliminatoriaFutbol(auxColumna2[j], auxColumna1[j]);
                Jadd.añadirPartido(partido);*/
                if(num_jornadas == (jornada+1) && tercerCuartoPuesto == true){
                    seleccionarTipoPartidoEliminatoria(deporte, columna1, auxColumna2, j, Jadd, sets); 
                    /*PartidoEliminatoria partidotycp = new PartidoEliminatoriaFutbol(auxColumna2[j], auxColumna1[j]);
                    Jadd.añadirPartido(partidotycp);*/
                }
            }        
            
            //Si ya se han añadido todas las eliminatorias, cada vez que cargamos la tabla sustituimos la jornada en lugar de añadirla
            if(calendario.jornadas.size() == num_jornadas){
                //calendario.getCalendario().remove(jornada);
                calendario.getCalendario().set(jornada, Jadd);
            }else{
                calendario.añadirJornada(Jadd); //añadimos la jornada al calendario;
            }
            jornada++;//Añadimos una jornada para que pueda salir del bucle
        }//End while
        
        if(idaVuelta){         
            //Creamos un ArrayList auxiliar para guardar las jornadas a reordenar y otro con las que ya contiene
            ArrayList<Jornada> jornadas =  calendario.getCalendario();
            ArrayList<Jornada> auxJornadas = new ArrayList<>();   
            //Creamos una jornada nueva
            Jornada nuevaJornadaIV = new Jornada(jornada);
            //Carga tantos partidos como sea necesario
            for (int j = 0; j < (num_equipos)/2; j++) {               
                //Método que crea el partido en función del deporte pasado y lo añade a la nuevaJornada
                seleccionarTipoPartidoEliminatoria(deporte, columna2, columna1, j, nuevaJornadaIV, sets);//Se invierten las columnas para conseguir los partidos en la posición inversa                             
            }            
            calendario.añadirJornada(nuevaJornadaIV);

            //Si no hay rival asignamos como ganador al participante sin rival
            Jornada JIV = calendario.jornadas.get(jornada - 1);//jornada 1
            for (PartidoEliminatoria partido :(ArrayList<PartidoEliminatoria>) JIV.getListaPartidos())
                //Si el local o el visitante no tienen rival les asignamos como ganadores
                if(partido.getLocal().equalsIgnoreCase("")){
                   partido.setGanador("VISITANTE");
                }else if(partido.getVisitante().equalsIgnoreCase("")){
                    partido.setGanador("LOCAL"); 
                }else{
                    //getModelo().addRow(partido.getTableRepresentation()); 
            }

            jornada++;//Añadimos uno al contador de jornadas para que ingrese una nueva jornada
            num_jornadas  *= 2;//Doblamos el número de jornadas totales
            while(jornada < num_jornadas){
                auxEquipos.clear();//vaciamos el arrayList auxiliar
                //Comprobamos la jornada actual y añadimos los equipos ganadores al nuevo ArrayList con el que formaremos la siguiente ronda
                Jornada J2 = calendario.jornadas.get(jornada - 1);          
                for (PartidoEliminatoria partido :(ArrayList<PartidoEliminatoria>) J2.getListaPartidos())
                    //Si el local o el visitante no tienen rival les asignamos como ganadores
                    if(partido.getGanador().equalsIgnoreCase("LOCAL")){
                       auxEquipos.add(partido.getLocal()); 
                    }else if(partido.getGanador().equalsIgnoreCase("VISITANTE")){
                        auxEquipos.add(partido.getVisitante());
                    }else{
                        //getModelo().addRow(partido.getTableRepresentation()); 
                    }

                //creamos las columnas en función del tamaño del ArrayList auxiliar
                String[] auxColumna1 = new String[auxEquipos.size()/2];
                String[] auxColumna2 = new String[auxEquipos.size()/2];
                int indice = 0;//índice auxiliar que nos ayudará a poner cada equipo en su hueco correspondiente
                //Rellenamos las columnas con los equipos del ArrayList auxiliar
                 for (int i = 0; i < auxColumna1.length; i++) {
                        auxColumna1[i] = auxEquipos.get(indice);
                        indice+=2;
                 }
                 indice = 1;//vaciamos el indice acabado el for
                 for (int i = 0; i < auxColumna2.length; i++) {
                        auxColumna2[i] = auxEquipos.get(indice);
                        indice+=2;  
                 }
                 indice = 0;//vaciamos el indice acabado el for

                //Creamos una nueva joranda y la añadimos al calendario
                Jornada Jadd = new Jornada(jornada + 1);       
                //Añadimos los partidos a la Jornada
                for (int j = 0; j < auxEquipos.size()/2; j++) {
                    seleccionarTipoPartidoEliminatoria(deporte, auxColumna1, auxColumna2, j, Jadd, sets); 
                   /* PartidoEliminatoria partido = new PartidoEliminatoriaFutbol(auxColumna2[j], auxColumna1[j]);
                    Jadd.añadirPartido(partido);*/
                    if(num_jornadas == (jornada+1) && tercerCuartoPuesto == true){
                        seleccionarTipoPartidoEliminatoria(deporte, auxColumna1, auxColumna2, j, Jadd, sets); 
                        /*PartidoEliminatoria partidotycp = new PartidoEliminatoriaFutbol(auxColumna2[j], auxColumna1[j]);
                        Jadd.añadirPartido(partidotycp);*/
                    }
                }
                //Si ya se han añadido todas las eliminatorias, cada vez que cargamos la tabla sustituimos la jornada en lugar de añadirla
                if(calendario.jornadas.size() == num_jornadas){
                    //calendario.getCalendario().remove(jornada);
                    calendario.getCalendario().set(jornada, Jadd);
                }else{
                    calendario.añadirJornada(Jadd); //añadimos la jornada al calendario;
                }
                jornada++;//Añadimos una jornada para que pueda salir del bucle
            }//End while

            //Tras el for extraemos de nuevo las jornadas que ahora serán el doble, pero estarán mal ordenadas y ordenamos cada una con su par similar
              jornadas =  calendario.jornadas;
              int indice;
              //Reorganizamos el calendario
              for (int i = 0; i < jornadas.size()/2; i++) {
                    indice = i;
                    auxJornadas.add(jornadas.get(indice));
                    indice = ((jornadas.size()/2) + i);
                    auxJornadas.add(jornadas.get(indice));
              }//end for
            //Finalmente vaciamos el contenido del calendario y añadimos las jornadas del ArrayList auxiliar que ya estarán ordenadas
              calendario.jornadas.clear();
                for (Jornada auxJornada : auxJornadas) {
                    calendario.añadirJornada(auxJornada);  
                }
            
        }//End idaVuelta 
    }
    
    /**
     * Método que se encarga de colocar a los equipos en sus posiciones iniciales colocando
     * a los equipos fantasma en una misma columna para que no se puedan enfrentar entre ellos
     * @param columna1
     * @param columna2
     * @param num_equipos
     * @param equipos 
     */
    private static void colocarPosicionesEliminatoria(String[] columna1, String[] columna2, int num_equipos, ArrayList<String> equipos){
               
        for (int i = (num_equipos/2); i < num_equipos; i++) {
            //Para que empiece la columna2 en el índice 0 le restamos a éste el
            //número de equipos / 2 y el índice irá creciendo de 1 en 1
            columna2[i - (num_equipos/2)] = equipos.get(i);
        }           
        
        //Índice auxiliar para invertir la selección de equipos
        int indice_aux = 0;
        //Rellenamos al revés la columna 1
        for (int i = (num_equipos/2 - 1); i >= 0; i--) {
            columna1[indice_aux] = equipos.get(i);
            ++indice_aux;
        }
    }//End colocarPosicionesEliminatoria()

    /**
     * @return the izquierdaEOGen
     */
    public ArrayList<String> getIzquierdaEOGen() {
        return izquierdaEOGen;
    }

    /**
     * @param izquierdaEOGen the izquierdaEOGen to set
     */
    public void setIzquierdaEOGen(ArrayList<String> izquierdaEOGen) {
        this.izquierdaEOGen = izquierdaEOGen;
    }

    /**
     * @return the derechaEOGen
     */
    public ArrayList<String> getDerechaEOGen() {
        return derechaEOGen;
    }

    /**
     * @param derechaEOGen the derechaEOGen to set
     */
    public void setDerechaEOGen(ArrayList<String> derechaEOGen) {
        this.derechaEOGen = derechaEOGen;
    }
    
    
}//class
