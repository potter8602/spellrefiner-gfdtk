package SpellRefiner;

import java.io.Serializable;
import javax.inject.Named;
import javax.ejb.EJB;
import java.util.List;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;

@Named(value = "controller_table_inputs")
@ViewScoped
public class Controller_Table_Inputs implements Serializable {
    private static final String page_success = null;
    private static final String page_error = "error";
    
    @EJB
    private DAO_Interface_Input DAO_Input;        
    
    private Input input = new Input();   
    private List<Input> List_Input;
    
    public Controller_Table_Inputs(){
        List_Input = new ArrayList<Input>();
    }
    
    @PostConstruct
    public void Init(){
        UpdateList();
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
            List_Input.remove(input);
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
    
    public List<Input> getList_Input(){
        return List_Input;
    }
    
    public String UpdateList(){
        try {
            List_Input = DAO_Input.findAll();
        }    
        catch(Exception e){
            return page_error;               
        }                 
        
        return page_success;
    }
    
}
