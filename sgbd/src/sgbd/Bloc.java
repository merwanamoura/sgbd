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
public class Bloc {
    List<Tuple> listeTuples;
    int taille;
    int adresse;
    boolean alloue;
    int nbTupleMax;


    public Bloc(int taille, int adresse, boolean alloue, int nbTupleMax) {
        this.taille = taille;
        this.adresse = adresse;
        this.alloue = alloue;
        this.nbTupleMax = nbTupleMax;
        listeTuples = new ArrayList<Tuple>();
    }
    
    public void addTupleToBloc(Tuple tup)
    {
        if(listeTuples.size() < nbTupleMax && tup != null)
        {
            listeTuples.add(tup);
        }

        
    }

    public List<Tuple> getListeTuples() {
        return listeTuples;
    }

    public void setListeTuples(List<Tuple> listeTuples) {
        this.listeTuples = listeTuples;
    }

    public int getTaille() {
        return taille;
    }

    public void setTaille(int taille) {
        this.taille = taille;
    }

    public int getAdresse() {
        return adresse;
    }

    public void setAdresse(int adresse) {
        this.adresse = adresse;
    }

    public boolean isAlloue() {
        return alloue;
    }

    public void setAlloue(boolean alloue) {
        this.alloue = alloue;
    }

    public int getNbTupleMax() {
        return nbTupleMax;
    }

    public void setNbTupleMax(int nbTupleMax) {
        this.nbTupleMax = nbTupleMax;
    }

    
    @Override
   public String toString()
   {
       String str="";
       
       for(int i = 0; i < listeTuples.size() ; i++)
       {
           str += listeTuples.get(i).toString() + "\n";
       }
       
       return str;
   }
    
}
