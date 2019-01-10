/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sgbd;

/**
 *
 * @author amoura_merwan
 */
public class Buffer{
    
    Bloc bloc;
    
    public Buffer(Bloc bloc)
    {
        this.bloc = bloc;
    }
    
    public Buffer()
    {
        this.bloc = null ;
    }

    public Bloc getBloc() {
        return bloc;
    }

    public void setBloc(Bloc bloc) {
        this.bloc = bloc;
    }
    
  
    
    
    
    
}
