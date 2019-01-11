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
        int hashValue = valeurHashage % moduloHashage;
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
            
            if(getNbBufferFree() > 0)
            {
                int tmp = 0 ;
                for(int k = 0;k < getNbBufferFree();k++)
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












    
    public static Table joinTable(Table tableA,Table tableB,String nomAttributA,String nomAttributB,int moduloHashage)
    {
        List<Bucket> listBuc = Sgbd.MS.getListeBucket();
        int indice=0;
        Table tableResultat = Sgbd.createTable(tableA.getTable_name()+"_"+tableB.getTable_name());
        
       
        //ON BOUCLE SUR TOUS LES BUCKETS 
        while(indice < moduloHashage)
        {
            
            //RECUPERATION DES BUCKETS Ai ET Bi
            Bucket bucA=null,bucB=null;
            //VARIABLE DONNANT LE NOMBRE DE BLOC DU BUCKET EN TRAITEMENT
            int nbBlocBucA =0,nbBlocBucB=0;
            for(int k=0;k<listBuc.size();k++)
            {
                if(listBuc.get(k).getId_bucket().equals(tableA.table_name+indice))
                {
                    bucA=listBuc.get(k);
                    nbBlocBucA = bucA.getListBloc().size();
                }
                if(listBuc.get(k).getId_bucket().equals(tableB.table_name+indice))
                {
                    bucB=listBuc.get(k);
                    nbBlocBucB = bucB.getListBloc().size();
                }
            }

            if(bucA==null || bucB == null)
            {
                indice+=1;
            }
            else
            {
                //BOOLEAN QUI FERA TOURNER EN BOUCLE LE WHILE TANT QUE AU MOINS 2 BUFFERS NE SONT PAS LIBRE
                boolean bufferFree = false;

                //VARIABLE DONNANT LE BLOC EN TRAITEMENT
                int dernierBlocBTraite = 0,dernierBlocATraite = 0;

                while(!bufferFree)
                {
                   
                    if(getNbBufferFree() >=2 )
                    {
                        
                        //BOUCLE TOUS LES BLOCS DU BUCKET B EN TRAITEMENT
                        while(dernierBlocBTraite<bucB.getListBloc().size())
                        {
                            
                            //PLACEMENT D'UN BLOC  Bi DANS UN BUFFER  
                            Buffer bufB = getBufferFree();
                            bufB.setBloc(bucB.getListBloc().get(dernierBlocBTraite)); // ON MET LE PREMIER BLOC DE B DANS LE BUFFER
                            bufB.setTypeContainer(tableB.getTable_name());

                            
                            //BOUCLE TANT QU IL RESTE DES BLOCS DANS Ai
                            while(dernierBlocATraite<nbBlocBucA)
                            {
                                //PLACEMENT DES M-1 BLOC Ai DANS LES M-1 BUFFER DISPONIBLE
                                int limite;
                                if(nbBlocBucA < getNbBufferFree())
                                {
                                    limite = nbBlocBucA;
                                }
                                else 
                                {
                                    limite=getNbBufferFree();
                                }
                              //  System.out.println(bufB.getBSystemloc().toString());
                                for(int k = 0;k < limite;k++)
                                {
                                    if(k+dernierBlocATraite < bucA.getListBloc().size())
                                    {
                                        
                                        Buffer buf = getBufferFree();
                                        if(buf!=null)
                                        {
                                            
                                            buf.setBloc(bucA.getListBloc().get(k+dernierBlocATraite));
                                            buf.setTypeContainer(tableA.getTable_name());      
                                        }
                                        else
                                        {
                                            System.out.println("Pas de buffer dispo");
                                        }

                                    }

                                } 
                                dernierBlocATraite+=limite;
                                //TRAITEMENT
                                
                                String valA="",valB="";
                                //ON PARCOURS TOUS LES BUFFER
                                for(int k=0;k<listeBuffer.size();k++)
                                {
                                    // ON REGARDE LES BUFFER DE A
                                    if( listeBuffer.get(k).getBloc() != null && listeBuffer.get(k).getTypeContainer()!=null )
                                    {    
                                        if(listeBuffer.get(k).getTypeContainer().equals(tableA.getTable_name()))
                                        {
                                            Buffer bufA = listeBuffer.get(k);
                                            //ON PARCOUS LES TUPLES DE A
                                            for(int t=0;t<bufA.getBloc().getListeTuples().size();t++)
                                            {
                                                //POUR CHAQUE TUPLE DE A
                                                Tuple tupA = bufA.getBloc().getListeTuples().get(t);
                                                // ON LES COMPARE AUX BLOCS DE B
                                                for(int b=0;b<bufB.getBloc().getListeTuples().size();b++)
                                                {
                                                    Tuple tupB = bufB.getBloc().getListeTuples().get(b);
                                                    for(int z=0;z<tupB.getListeAttribut().size();z++)
                                                    {
                                                        String nom =  tupB.getListeAttribut().get(z).getNom();
                                                        if(nom.equals(nomAttributB))
                                                        {
                                                           valB=bufB.getBloc().getListeTuples().get(b).getListeAttribut().get(z).getValeur();
                                                        }
                                                    }

                                                    for(int z=0;z<tupA.getListeAttribut().size();z++)
                                                    {
                                                        String nom =  tupA.getListeAttribut().get(z).getNom();
                                                        if(nom.equals(nomAttributA))
                                                        {
                                                            valA= tupA.getListeAttribut().get(z).getValeur();
                                                        }
                                                    }

                                                    if(valA.equals(valB))
                                                    {
                                                        Tuple tupTab = new Tuple();
                                                        for(int i = 0; i< tupA.getListeAttribut().size();i++)
                                                        {
                                                            tupTab.getListeAttribut().add(tupA.getListeAttribut().get(i));
                                                        }
                                                        for(int i = 0; i< tupB.getListeAttribut().size();i++)
                                                        {
                                                            if(!tupB.getListeAttribut().get(i).getNom().equals(nomAttributB))
                                                            {
                                                                tupTab.getListeAttribut().add(tupB.getListeAttribut().get(i));
                                                            }
                                                            
                                                        }
                                                        tableResultat.insertInto(tupTab);

                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                
                                //LIBERATION DES BUFFER
                                for(int w=0;w<listeBuffer.size();w++)
                                {
                                    if(listeBuffer.get(w).getTypeContainer() != null)
                                    {
                                        if(listeBuffer.get(w).getTypeContainer().equals(tableA.getTable_name()))
                                        {
                                            listeBuffer.get(w).setBloc(null);
                                            listeBuffer.get(w).setTypeContainer(null);
                                        }
                                    }

                                }
                            }
                            
                            
                            bufB.setBloc(null); // ON MET LE PREMIER BLOC DE B DANS LE BUFFER
                            bufB.setTypeContainer(null);
                            
                            dernierBlocBTraite+=1;//ON A FINI DE TRAITER TOUS LES BLOCS DE Ai, ON PASSE DONC AU BLOC SUIVANT DE Bi
                        }
                        
                        bufferFree=true;
                    }
                }
                indice+=1;
            }
        }  
        return tableResultat;
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
