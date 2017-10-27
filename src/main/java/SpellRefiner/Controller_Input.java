package SpellRefiner;

import java.io.Serializable;
import javax.inject.Named;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;

@Named(value = "controller_input")
@RequestScoped
public class Controller_Input implements Serializable {
    private static final String page_success = null;//"success";
    private static final String page_error = "error";
    
    @EJB
    private DAO_Interface_Input DAO_Input;        
    
    private Input input = new Input();   
    
    
    public String SaveNew(){
        try {
            DAO_Input.SaveNew(input);
        }
        catch(Exception e){
            return page_error;               
        }                 
        
        return page_success;                
    }    
    
    
    public String Update(){
        try {
            DAO_Input.Update(input);
        }
        catch(Exception e){
            return page_error;               
        }                 
        
        return page_success;                
    }    
    
    public String Delete(){
        try {            
            DAO_Input.Delete(input);
        }
        catch(Exception e){
            return page_error;               
        }                 
        
        return page_success;                
    }        
    
    public Input getInput(){
        return input;
    }

    public void setInput(Input input){
        this.input = input;
    }    
    
}
