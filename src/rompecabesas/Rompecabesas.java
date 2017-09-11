/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rompecabesas;

import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 *
 * @author warren
 */
public class Rompecabesas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
         
        nodo a = new nodo(2, 3);
        a.a[0][0]=1;    a.a[0][1]=4;    a.a[0][2]=2;
        a.a[1][0]=3;    a.a[1][1]=5;    a.a[1][2]=0;
        a.posi=1;
        a.posj=2;
        
        tu_asterisco t = new tu_asterisco(a);
 
        boolean h=        t.resolver();
        nodo k=t.fin;

        
       ArrayList l=t.getMov();
       
       

     }

}
