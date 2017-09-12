package rompecabesas;

import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author warren
 */
public class nodo {

    public int a[][];
    
    public int costo;
    public boolean visitado;
    public int posi,posj;    
    public int n, m;    
    public nodo papito;
    public int heur;
    public nodo(int i, int j) {
        a = new int[i][j];
        visitado=false;
        this.n = i;
        this.m = j;
        costo=9999999;
        papito=null;
    }
    public void swap(int ii,int jj){
       int k=a[ii][jj];
       a[ii][jj]=a[posi][posj];
       a[posi][posj]=k;
       posi=ii;
       posj=jj;
    }
    public int[][] getMatris(){
        int [][]k=new int[n][m];
        for(int i=0;i<n;i++)
            for(int j=0;j<m;j++)
                k[i][j]=this.a[i][j];
        return k;
    }    
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
    

    public nodo clone(){
        nodo b=new nodo(n,m);
        b.costo=costo;
        b.visitado=visitado;
        b.posi=posi;
        b.posj=posj;
        b.a=getMatris();
        return b;
    }
    public ArrayList<Integer> getMovimiento(){
        ArrayList<Integer> res=new ArrayList();
        for(int i=0;i<n;i++)
            for(int j=0;j<m;j++)
                res.add(a[i][j]);
        return res;
    }
    public int h1() {
        int k=0;
        for (int i = 0; i < n; i++) 
            for (int j = 0; j < m; j++) {
                if (a[i][j] == 0)   continue;                               
                if (a[i][j] != i * 3 + j + 1)  k++;                
            }        
        return k;
    }
    public int h2(){
        int k=0;
        for (int i = 0; i < n; i++) 
            for (int j = 0; j < m; j++) {
                if (a[i][j] == 0)   continue;                               
		k+=abs(a[i][j]%3-j)+abs(a[i][j]/3-i);
            }        
        return k;    
    }
    public int heuristica(){
        heur=h1()+h2();
        return heur;
    }
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
