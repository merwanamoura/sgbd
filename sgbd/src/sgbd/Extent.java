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
public class Extent {
    List<Bloc> listeBloc;
    int nbBloc;
    
    public List<Bloc> getListeBloc() {
        return listeBloc;
    }

    public void setListeBloc(List<Bloc> listeBloc) {
        this.listeBloc = listeBloc;
    }

    public int getNbBloc() {
        return nbBloc;
    }

    public void setNbBloc(int nbBloc) {
        this.nbBloc = nbBloc;
    }
    

    public Extent( int nbBloc) {
        this.listeBloc = new ArrayList<Bloc>();
        this.nbBloc = nbBloc;
    }
   
}
