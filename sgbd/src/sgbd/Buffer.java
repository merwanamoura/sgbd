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
    String typeContainer;

    
    public Buffer(Bloc bloc)
    {
        this.bloc = bloc;
        typeContainer = null;
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

    public String getTypeContainer() {
        return typeContainer;
    }

    public void setTypeContainer(String typeContainer) {
        this.typeContainer = typeContainer;
    }
    
    
    
    
}
