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
 * @author hc047736
 */
public class Segment {
    
    List <Extent> listExtent;
    int nbExtent;
    String segment_name;

    public Segment( String segment_name) {
        this.listExtent = new ArrayList<Extent>();
        this.segment_name = segment_name;
    }

    public List<Extent> getListExtent() {
        return listExtent;
    }

    public void setListExtent(List<Extent> listExtent) {
        this.listExtent = listExtent;
    }

    public int getNbExtent() {
        return nbExtent;
    }

    public void setNbExtent(int nbExtent) {
        this.nbExtent = nbExtent;
    }
    
    Bloc getBlocFree()
    {
        Bloc bloc = null;
        for(int i=0;i<listExtent.size();i++)
        {
            for(int j=0;j< listExtent.get(i).getListeBloc().size();j++)
            {
                if(listExtent.get(i).getListeBloc().get(j).getListeTuples().size() <listExtent.get(i).getListeBloc().get(j).getNbTupleMax()  )
                {
                    return listExtent.get(i).getListeBloc().get(j);
                }
            }
        }
       
        Extent ext =  Sgbd.findExtent(Sgbd.nbBlocPerExtent);
        if(ext !=null )
        {
            listExtent.add(ext);
            
            return ext.getListeBloc().get(0);
        }
        else 
        {
            System.out.println("Pas assez de bloc contigu");
        }
        
        return bloc;
    }
    
}
