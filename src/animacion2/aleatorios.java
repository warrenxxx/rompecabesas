/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animacion2;

import java.util.ArrayList;

/**
 *
 * @author warren
 */
public class aleatorios {
    String h[];
    public aleatorios() {
        h=new String[5];
        h[0]="5 1 2 3 4 0 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25";
        h[1]="5 1 2 3 4 0 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25";
        h[2]="5 1 2 3 4 0 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25";
        h[3]="5 1 2 3 4 0 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25";
        h[4]="5 1 2 3 4 0 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25";
    }
    public ArrayList getMapa(int a){
        ArrayList l=new ArrayList();
        String aux[]=h[a].split(" ");
        for(int i=0;i<aux.length;i++)
            l.add(Integer.parseInt(aux[i]));
        return l;
    }   
}
