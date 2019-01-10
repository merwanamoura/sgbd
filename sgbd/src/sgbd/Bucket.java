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
public class Bucket {
   
    List<Bloc> listBloc;
    String id_bucket;

    public Bucket(String id_bucket) {
        this.listBloc = new ArrayList<Bloc>();
        this.id_bucket = id_bucket;
    }
    
    public Bucket(){
        this.listBloc = null;
        this.id_bucket = "default";
    }

    public List<Bloc> getListBloc() {
        return listBloc;
    }

    public void setListBloc(List<Bloc> listBloc) {
        this.listBloc = listBloc;
    }

    public String getId_bucket() {
        return id_bucket;
    }

    public void setId_bucket(String id_bucket) {
        this.id_bucket = id_bucket;
    }
    
    
}
