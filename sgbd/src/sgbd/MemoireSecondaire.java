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
public class MemoireSecondaire {
    
    List<Bucket> listeBucket ;

    public MemoireSecondaire() {
        this.listeBucket = new ArrayList<Bucket>();
    }

    public List<Bucket> getListeBucket() {
        return listeBucket;
    }

    public void setListeBucket(List<Bucket> listeBucket) {
        this.listeBucket = listeBucket;
    }

    
    
}
