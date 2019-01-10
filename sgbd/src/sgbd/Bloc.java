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
    int nbTuple;

    public Bloc(int taille, int adresse, boolean alloue, int nbTupleMax) {
        this.taille = taille;
        this.adresse = adresse;
        this.alloue = alloue;
        this.nbTupleMax = nbTupleMax;
        this.nbTuple = 0;
        listeTuples = new ArrayList<Tuple>();
    }
    
    public void addTupleToBloc(Tuple tup)
    {
        if(nbTuple <= nbTupleMax && tup != null)
        {
            listeTuples.add(tup);
            nbTuple++;
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

    public int getNbTuple() {
        return nbTuple;
    }

    public void setNbTuple(int nbTuple) {
        this.nbTuple = nbTuple;
    }
    
    
}
