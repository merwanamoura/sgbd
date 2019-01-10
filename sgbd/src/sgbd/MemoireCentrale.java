/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sgbd;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author amoura_merwan
 */
public class MemoireCentrale {
    
    static List<Buffer> listeBuffer;
    
    public List<Buffer> getListeBuffer() {
        return listeBuffer;
    }

    public MemoireCentrale() {
        this.listeBuffer = new ArrayList<Buffer>();
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
    
    public static void hashBloc(String table_name,Bloc bloc,String nomAttribut,int moduloHashage,Buffer buf)
    {
        
        for(int i=0;i<bloc.getListeTuples().size();i++)
        {
            
            Tuple tuple = bloc.getListeTuples().get(i);
            String val;
            for(int j = 0; j<tuple.getListeAttribut().size();j++)
            {
              
                if(tuple.getListeAttribut().get(j).getNom().equals(nomAttribut))
                {
                    val = tuple.getListeAttribut().get(j).getValeur();
                    int hashValue = hashage(val,moduloHashage);
                    
                    
                    boolean buckExist = false;
                      
                    for(int k = 0;k < Sgbd.MS.getListeBucket().size();k++)
                    {
                        Bucket buc = Sgbd.MS.getListeBucket().get(k);
                        if(buc.getId_bucket().equals(""+table_name+hashValue))
                        {
                            
                            buckExist = true;
                            buc.insertTuple(tuple);
                            buf.setBloc(null);
                        } 
                    }
                    if(!buckExist)
                    {
                        Bucket buc = new Bucket(""+table_name+hashValue);
                        buc.insertTuple(tuple);
                        Sgbd.MS.getListeBucket().add(buc);
                        buf.setBloc(null);

                       // Sgbd.MS.getListeBucket().add(buc);
                    }
                    
                    
                }
            }
        }
    }
    
    public static void hashTable(Table table,String nomAttribut,int moduloHashage)
    {
        Segment seg = table.getSegment();
        boolean hashDone = false;
        
        int i=0;
        int j=0;
       
        while(!hashDone)
        {
            int nbufferFree = getNbBufferFree();
            
            if(nbufferFree > 0)
            {
                int tmp = 0 ;
                for(int k = 0;k < nbufferFree;k++)
                {
                    if(j+k < seg.getListExtent().get(i).getListeBloc().size() ) 
                    {
                        
                        getBufferFree().setBloc( seg.getListExtent().get(i).getListeBloc().get(j+k) );
                       
                        tmp++;
                    }
                   
                }
  
                j+=tmp;
            }
            
            for(int k = 0;k < listeBuffer.size();k++)
            {
                
                Bloc bloc2 = listeBuffer.get(k).getBloc();
                if(bloc2!=null)
                {
                    
                    hashBloc(table.getTable_name(),bloc2,nomAttribut,moduloHashage,listeBuffer.get(k));
                }


            }
            
             
            if( j >= seg.getListExtent().get(i).getListeBloc().size() -1)
            {
                i++;
                j = 0;
            }
            if( i >= seg.getListExtent().size())
            {
                hashDone = true;
            }
            
        }
        
        
    }
    
    public static int getNbBufferFree(){
        int nb = 0 ;
        for (int i = 0 ; i < listeBuffer.size() ; i++)
        {
            Buffer b = listeBuffer.get(i);
            if( b.bloc == null) nb++;
        }
        return nb;
    }
    
    static public Buffer getBufferFree()
    {
        Buffer buff= null;
        for (int i = 0 ; i < listeBuffer.size() ; i++)
        {
            Buffer b = listeBuffer.get(i);
            if( b.bloc == null) return b;
        }
        return buff;
    }

}
