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
public class Tuple {
    List<Attribut> listeAttribut;
    int taille;

    public Tuple() {
        this.listeAttribut = new ArrayList<Attribut>();
        this.taille = 1;
    }

    public List<Attribut> getListeAttribut() {
        return listeAttribut;
    }

    public void setListeAttribut(List<Attribut> listeAttribut) {
        this.listeAttribut = listeAttribut;
    }

    public int getTaille() {
        return taille;
    }

    public void setTaille(int taille) {
        this.taille = taille;
    }
    
    @Override
    public String toString()
    {
        String str ="";
        
        for( int i = 0 ; i < listeAttribut.size() ; i++)
        {
            System.out.println( listeAttribut.get(i).getType() +" " + listeAttribut.get(i).nom + " : " + listeAttribut.get(i).valeur );
        }
        
        return str;
    }

}
