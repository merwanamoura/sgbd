/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sgbd;

import java.util.List;

/**
 *
 * @author amoura_merwan
 */
public class MemoireCentrale {
    
    List<Buffer> listeBuffer;
    
    public List<Buffer> getListeBuffer() {
        return listeBuffer;
    }

    public MemoireCentrale(List<Buffer> listeBuffer) {
        this.listeBuffer = listeBuffer;
    }

    public void setListeBuffer(List<Buffer> listeBuffer) {
        this.listeBuffer = listeBuffer;
    }

    
    
    
    
    public static int hashage(String valueToHash,int moduloHashage)
    {
        int valeurHashage = 0 ;
        
        for ( int i = 0 ; i < valueToHash.length() ; i++)
        {
            int tmp = (int) valueToHash.charAt(i);
            valeurHashage = valeurHashage + tmp;
        }
        
        // il faut maintenant appliquer la fonction de hashage //
        
        int hashValue = valeurHashage % moduloHashage;
        
       /* System.out.println("on hash la valeur -> " + valueToHash + " avec un fonction modulo - > " + moduloHashage + " \n et on obtient la valeur de hashage suivante --> " 
        + hashValue + " ------> on le place dans le Bucket A" + hashValue );
       */
      return hashValue;      
       
    }
    
    public static void hashTable(Table table)
    {
        Segment seg = table.getSegment();
        boolean hashDone = false;
        
     
        for(int i = 0; i < seg.getListExtent().size();i++)
        {
            for(int j=0;j < seg.getListExtent().get(i).getListeBloc().size();j++)
            {
                
            }
        }
    }
    
    public int getNbBufferFree(){
        int nb = 0 ;
        for (int i = 0 ; i < Sgbd.MC.getListeBuffer().size() ; i++)
        {
            Buffer b = Sgbd.MC.getListeBuffer().get(i);
            if( b == null) nb++;
        }
        return nb;
    }
    
    public Buffer getBufferFree()
    {
        Buffer buff= null;
        for (int i = 0 ; i < Sgbd.MC.getListeBuffer().size() ; i++)
        {
            Buffer b = Sgbd.MC.getListeBuffer().get(i);
            if( b == null) return b;
        }
        return buff;
    }

}
