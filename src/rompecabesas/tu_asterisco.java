
package rompecabesas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;


/**
 *
 * @author warren
 */
public class tu_asterisco {
    
    public HashMap<String,nodo>por_procesar;
    
    public nodo ini,fin;
    
    public  int nodosprocesados;
    
    public String hu1="";
    public String hu2="";

    private final int ax[]={0,1,0,-1};
    private final int ay[]={1,0,-1,0};

    public tu_asterisco(nodo ini) {
        this.por_procesar = new HashMap();
        this.ini=ini;
        nodosprocesados=0;
    }

    public boolean resolver() throws InterruptedException{
        PriorityQueue<nodo> q=new PriorityQueue<>((nodoa,nodob)->{
            int res=0;
            if(nodoa.costo==nodob.costo)res= 0;
            if(nodoa.costo<nodob.costo)res= -1;
            if(nodoa.costo>nodob.costo)res= 1;            
//            System.out.println(res+" "+nodoa.costo+" "+nodob.costo);
            return res;
        });
        nodosprocesados=0;
        int k=0;
        
        por_procesar.put(ini.StringParaUbicar(), ini);
        ini.costo=ini.heuristica();
        q.add(ini);
//        q.enqueue(ini);
        while(!q.isEmpty()){
            nodo a=q.poll();            
//            nodo a =q.dequeue();
            if(a.verifiacr()){
                System.out.println("llego");
                fin=a;
                return true;                                
            }
            
            if(a.visitado==true)continue;
            a.visitado=true;
            nodosprocesados++;
            k++;
           
            for(int i=0;i<4;i++){
                int xx=a.posi+ax[i];
                int yy=a.posj+ay[i];
                 if(xx>=0&&xx<a.n&&yy>=0&&yy<a.m){

                    nodo aux=a.clone();                    
                    aux.visitado=false;
                    aux.costo=999999;
                    aux.swap(xx,yy);                    
                    
                    nodo h=por_procesar.get(aux.StringParaUbicar());
                    if(h!=null)
                        aux= h;
                    
                    if(aux.visitado)
                        continue;
                    if(a.costo+ aux.heuristica()<aux.costo){
                        aux.costo=a.costo+aux.heuristica();
                        por_procesar.put(aux.StringParaUbicar(),aux);
                        aux.papito=a;
//                        q.enqueue(aux);
                        q.add(aux);
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
