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
public class Table {
    Segment segment;
    String table_name;


    public Table(String table_name,Segment segment) {
        this.segment = segment;
        this.table_name = table_name;
    }
    

    public Segment getSegment() {
        return segment;
    }

    public void setSegment(Segment segment) {
        this.segment = segment;
    }

    public String getTable_name() {
        return table_name;
    }

    public void setTable_name(String table_name) {
        this.table_name = table_name;
    }
    
    public void insertInto(Tuple tup)
    {
        if(tup != null ) 
        {
            if(this.segment.getBlocFree() != null)
            {

                this.segment.getBlocFree().getListeTuples().add(tup);
  
            }

            

        }
    }
}
