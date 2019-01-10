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
 

public class Sgbd {
    
    static List<Bloc> listBloc;//DISQUE
    static int nbBlocPerExtent = 5;
    static int derniereAdressealoue=0;
    static int nbTupleMax = 10;
    
    static void initBlocs(int nb)
    {
        for(int i=0;i<nb;i++)
        {
            
            double val = Math.random();
            boolean bol=true;
            if( val > 0.2)bol=false;
            
            Bloc bloc = new Bloc(8192,i,bol,nbTupleMax);
            listBloc.add(bloc);
            derniereAdressealoue = i;
        }
    }
    
    static Extent findExtent(int nbBlocConsecutif)
    {
        Extent ext = null;
        
        int nbCons= 0;
        int nbPremier;
        for(int i=0;i<listBloc.size();i++)
        {
            
            if( !listBloc.get(i).isAlloue() )
            {
                nbCons++;
                
            }
            else 
            {
                nbCons = 0;
            }
            if(nbCons == nbBlocConsecutif)
            {
                ext = new Extent(nbBlocConsecutif);
                for(int j=nbBlocConsecutif-1; j >= 0 ; j--)
                {
                    ext.getListeBloc().add( listBloc.get( i-j ) );
                    listBloc.get(i-j).setAlloue(true);
                }
            }
        }
        
        return ext;
    }
    
    
    static Table createTable(String table_name)
    {
        Table tab = null;
        Extent extent = findExtent(nbBlocPerExtent);
        if(extent != null)
        {
            Segment segment = new Segment(table_name);
            segment.getListExtent().add(extent);
            
            tab = new Table(table_name, segment);
        }
        
        
        return tab;
    }

    /**
     * @param args the command line arguments
     */
    static MemoireCentrale MC;
    static MemoireSecondaire MS;
    static int nombreBuffer = 10;
    static int nombreBucket = 100;
    static List <Buffer> listBuffer = new ArrayList <Buffer> ();
    static List <Bucket> listBucket = new ArrayList <Bucket> (); 
    
    public static void creationBuffer()
    { 
        for(int i = 0 ; i < nombreBuffer ; i++)
        {
            Buffer buffer = new Buffer();
            listBuffer.add(buffer);
        }
    }
    
    public static void creationBucket()
    { 
        for(int i = 0 ; i < 10 ; i++)
        {
            Bucket bucket = new Bucket();
            listBucket.add(bucket);
        }
    }
    

    
    public static void main(String[] args) {
        // TODO code application logic here
        
        MC = new MemoireCentrale();
        MS = new MemoireSecondaire();
        
        listBloc = new ArrayList<Bloc>();
        
        initBlocs(200);
        
        Tuple tupleAntoine = new Tuple();
        tupleAntoine.getListeAttribut().add(new Attribut("char","ville","Dijon"));
        tupleAntoine.getListeAttribut().add(new Attribut("char","nom","TRAN"));
        tupleAntoine.getListeAttribut().add(new Attribut("char","prenom","Antoine"));
        
        Tuple tupleMerwan = new Tuple();
        tupleMerwan.getListeAttribut().add(new Attribut("char","ville","Daix"));
        tupleMerwan.getListeAttribut().add(new Attribut("char","nom","AMOURA"));
        tupleMerwan.getListeAttribut().add(new Attribut("char","prenom","Merwan"));
        
        Tuple tupleBastien = new Tuple();
        tupleBastien.getListeAttribut().add(new Attribut("char","ville","Norges"));
        tupleBastien.getListeAttribut().add(new Attribut("char","nom","JOBLOT"));
        tupleBastien.getListeAttribut().add(new Attribut("char","prenom","Bastien"));
        
        Table tableEtudiant = createTable("Etudiant");
        tableEtudiant.insertInto(tupleAntoine);
        tableEtudiant.insertInto(tupleMerwan);
        tableEtudiant.insertInto(tupleBastien);
        
        
        Tuple tupleDijon = new Tuple();
        tupleDijon.getListeAttribut().add(new Attribut("char","nom","Dijon"));
        tupleDijon.getListeAttribut().add(new Attribut("int","nbHabitant","180000"));
        
        Tuple tupleDaix = new Tuple();
        tupleDaix.getListeAttribut().add(new Attribut("char","nom","Daix"));
        tupleDaix.getListeAttribut().add(new Attribut("int","nbHabitant","20000"));
        
        Tuple tupleNorges = new Tuple();
        tupleNorges.getListeAttribut().add(new Attribut("char","nom","Norges"));
        tupleNorges.getListeAttribut().add(new Attribut("int","nbHabitant","2000"));
        
        Table tableVille = createTable("Ville");
        tableVille.insertInto(tupleDijon);
        tableVille.insertInto(tupleDaix);
        tableVille.insertInto(tupleNorges);
        
         
       Segment seg = tableEtudiant.getSegment();
        for(int i=0;i<seg.getListExtent().size();i++)
        {
           
            for(int j=0;j<seg.getListExtent().get(i).getListeBloc().size();j++)
            { 
                Bloc bloc = seg.getListExtent().get(i).getListeBloc().get(j);
                for(int k = 0; k< bloc.getListeTuples().size();k++)
                {
                    Tuple tuple = bloc.getListeTuples().get(k);
                    System.out.println(tuple.getListeAttribut().get(1).getValeur());
                }
            }
        }
        
        
        creationBuffer();
        MC.setListeBuffer(listBuffer);
        
        creationBucket();
        MS.setListeBucket(listBucket);
        
        MC.hashTable(tableVille,"nom",4);
        MC.hashTable(tableEtudiant,"ville",4);
       
        
        for(int i=0;i<MS.listeBucket.size();i++)
        {
            for(int j = 0; j < MS.listeBucket.get(i).getListBloc().size();j++)
            {
               
                for(int k = 0; k < MS.listeBucket.get(i).getListBloc().get(j).getListeTuples().size();k++)
                {
                    System.out.println( MS.listeBucket.get(i).getId_bucket()+"  "+MS.listeBucket.get(i).getListBloc().get(j).getListeTuples().get(k).listeAttribut.get(0).valeur);
                }
            }
        }
        
    }
    
    
}
