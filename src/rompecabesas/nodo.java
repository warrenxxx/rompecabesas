package rompecabesas;

import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author warren
 */
public class nodo {

    public int a[][];//matris en el que almacena el puzzle
    
    public int costo;//el costo del nodo
    public boolean visitado;// booleando que verifica si esta visitado o no    
    public int posi,posj;//posiciones x e y de la piesa que se encuentra vacia    
    public int n, m;// tamaño del puzzle n x m    
    public nodo papito;//padre del nodo actual (el padre es la jugada anterior)
    public int heur;//heuristica hallada 
    
    //iniciando contructor 
    public nodo(int n, int m) {//requiere de los tamaños del puzzle
        a = new int[n][m];
        visitado=false;
        this.n = n;
        this.m = m;
        costo=9999999;//al inicio el costo es el mas alto        
        papito=null;//al inicio el padre es null
    }

    //modulo pora cambiar las pociciones de la ficahs
    public void swap(int ii,int jj){
       int k=a[ii][jj];
       a[ii][jj]=a[posi][posj];
       a[posi][posj]=k;
       posi=ii;
       posj=jj;
    }
    
    //modulo para clonar la matris
    public int[][] getMatris(){
        int [][]k=new int[n][m];
        for(int i=0;i<n;i++)
            for(int j=0;j<m;j++)
                k[i][j]=this.a[i][j];
        return k;
    }    
    
    //modulo para rellenar la matris del puzzle basado en un arreglo
    public void setmatris(ArrayList<Integer> l) {
        int k=0;
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                a[i][j] = (int) l.get(k++);
                if(a[i][j]==0){
                    this.posi=i;
                    this.posj=j;
                }
            }
        }
    }
    
    //modulo para clonar el nodo
    public nodo clone(){
        nodo b=new nodo(n,m);
        b.costo=costo;
        b.visitado=visitado;
        b.posi=posi;
        b.posj=posj;
        b.a=getMatris();
        return b;
    }
    
    //obtiene una lista con el estado de la matris
    //ejemplo
    //[5,6,3]
    //[0,1,2]
    //=5,6,3,0,1,2
    public ArrayList<Integer> getMovimiento(){
        ArrayList<Integer> res=new ArrayList();
        for(int i=0;i<n;i++)
            for(int j=0;j<m;j++)
                res.add(a[i][j]);
        return res;
    }
    
    //heuristica de casillas fuera de lugar
    public int h1() {
        int k=0;
        for (int i = 0; i < n; i++) 
            for (int j = 0; j < m; j++) {
                if (a[i][j] == 0)   continue;                               
                if (a[i][j] != i * 3 + j + 1)  k++;                
            }        
        return k;
    }
    
    //heuristica de distancias de manjatan
    public int h2(){
        int k=0;
        for (int i = 0; i < n; i++) 
            for (int j = 0; j < m; j++) {
                if (a[i][j] == 0)   continue;                               
		k+=abs(a[i][j]%3-j)+abs(a[i][j]/3-i);
            }        
        return k;    
    }
    
    //suma de las heuristicas
    public int heuristica(){
        heur=h1()+h2();
        return heur;
    }
    
    //modulo para verificar si el puzzle llego al estado inicial 
    //0 1 2
    //3 4 5
    public boolean verifiacr(){
        int k=0;
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(a[i][j]!=k)return false;
                k++;
            }
        }
        return true;
    }
    
    //String que contine el puzzle 
    // se usa para ubicar un puzzle segun su codigo hash en base a ese string
    public String StringParaUbicar(){
        String h="";
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                h+=a[i][j];
            }
        }
        return h;
    }
    
    @Override
    public String toString() {
        String h="";
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                h=h+a[i][j]+" ";
            }h=h+"\n";
        }
        h= h+" "+posi+" "+posj+" ->";
        if(papito!=null)
            h=h+papito.StringParaUbicar();
        return h;
    }
}
