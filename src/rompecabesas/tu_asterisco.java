
package rompecabesas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;


/**
 *
 * @author warren
 */
public class tu_asterisco {
    
    public HashMap<String,nodo>por_procesar;//hashmap o arbol de hash donde se almacenan los nodos 
                                            //solo se usara para buscar un nodo basado en su stringParaUbicar
                                            //y mejorar la velocidad y reconocer cuales nodos ya an sido procesados y visitados
    
    public nodo ini,fin;//nodo inicial y final
    
    public  int nodosprocesados;// es la cantidad de nodos que son procesados
    
    public String hu1="";// es la cantidad de nodos en la cola
    public String hu2="";

    private final int ax[]={0,1,0,-1};//enteros usados
    private final int ay[]={1,0,-1,0};// para verificar las pociciones de arriba abajo izquierda y derecha

    //connstructor
    public tu_asterisco(nodo ini) {
        this.por_procesar = new HashMap();
        this.ini=ini;
        nodosprocesados=0;
    }

    //resoler el mediante el asterisco el puzzle
    public boolean resolver() throws InterruptedException{
        //para este algoritmo se usara una cola de prioridad(cola de prioridad es la que almacena nodos de forma ordenada)
        //en este caso la cola de proridad se ordenara por el costo del nodo        
        PriorityQueue<nodo> q=new PriorityQueue<>((nodoa,nodob)->{
            int res=0;
            if(nodoa.costo==nodob.costo)res= 0;
            if(nodoa.costo<nodob.costo)res= -1;
            if(nodoa.costo>nodob.costo)res= 1;            
            return res;
        });
        //al principio los nodos procesados son 09
        nodosprocesados=0;
        int k=0;
        por_procesar.put(ini.StringParaUbicar(), ini);//ingresamo al mapa de visitado el primer nodo
        ini.costo=ini.heuristica(); //hallamaos la heuristicas
        q.add(ini);       //ingresamos a la cola el primer nodo

        
        //iniciamos el bucle 
        //cuando la cola este vacia significata que pasamos por todos los nodos
        while(!q.isEmpty()){
            
            nodo a=q.poll();//sacamos el nodo de la cabesa de la cola y lo eliminamos de la cola            

            //si es que el nodo esta en orden correcto 
            //significa que lo resolvimos
            //y retornamos true
            if(a.verifiacr()){
                fin=a;
                return true;                                
            }
            
            //si el nodo ya a sido visitado 
            //se ignorara
            if(a.visitado==true)continue;
            
            
            a.visitado=true;//visitamos el nodo para no verificarlo mas de dos veses
            nodosprocesados++;
            k++;
           
            
            //verificamos las 4 posiciones del puzzle
            for(int i=0;i<4;i++){
                int xx=a.posi+ax[i];//posisines siguientes
                int yy=a.posj+ay[i];// de movimiento del puzzle
                
                
                 if(xx>=0&&xx<a.n&&yy>=0&&yy<a.m){//si las posisiones estan en el rango del puzzle ingresamos

                    nodo aux=a.clone();//clonamos el nodo para no alterar este mismo                    
                    aux.visitado=false;//y los usamos el nodo clonado para los canvios respectivos
                    aux.costo=999999;
                    aux.swap(xx,yy);                    
                    
                    nodo h=por_procesar.get(aux.StringParaUbicar());
                    if(h!=null)//si el nodo esta en el arbol de nodos por procesar remplazamos el nodo clonado
                        aux= h;
                    
                    if(aux.visitado)//si esta visitado continuamos
                        continue;
                    
                    //si la heuristica de l antorior + la nueva heuristica < costo de la nueva
                    //al principio todos los nodos tienen una heuristica alta 9999999
                    //a si que ingresaran sin rpoblema
                    //sin enbargo si se encuentra un nodo ya procesado en el mapa tendra un costo diferente
                    //en este caso se verificara si su costo es menor a l de los dos anteriores 
                    if(a.costo+ aux.heuristica()<aux.costo){
                        
                        aux.costo=a.costo+aux.heuristica();//si es que entro asignamos el nuevo costo
                        por_procesar.put(aux.StringParaUbicar(),aux);//añadimos en nodo al mapa
                        aux.papito=a;//asignamos el nodo padre
                        q.add(aux);//aumentamos a la cola
                    }
                }
            }
        }        
                        System.out.println(por_procesar.size()+" hh");
        return false;
    }
    public ArrayList<nodo> getMov(){
        hu1="";
        hu2="";
        ArrayList l=new ArrayList();
        if(fin==null)
            return new ArrayList();
        while(fin.papito!=null){
            l.add(fin);
            hu1=fin.heuristica()+","+hu1;
            hu2=fin.costo+","+hu2;

            fin=fin.papito;
     
        }
                    hu1=fin.heuristica()+","+hu1;
            hu2=fin.costo+","+hu2;

        l.add(fin);
        return l;
    }
}
