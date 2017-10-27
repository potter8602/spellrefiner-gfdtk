package SpellRefiner;

import java.io.Serializable;
import javax.inject.Named;
import javax.ejb.EJB;
import java.util.List;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;

@Named(value = "controller_table_checks")
@ViewScoped
public class Controller_Table_Checks implements Serializable {
    private static final String page_success = null;
    private static final String page_error = "error";
    
    @EJB
    private DAO_Interface_Check DAO_Check;        
    
    private Check check = new Check();   
    private List<Check> List_Check;
    
    public Controller_Table_Checks(){
        List_Check = new ArrayList<Check>();
    }
    
    @PostConstruct
    public void Init(){
        UpdateList();
    }
    
    
    public String Update(){
        try {
            DAO_Check.Update(check);
        }
        catch(Exception e){
            return page_error;               
        }                 
        
        return page_success;                
    }    
    
    public String Delete(){
        try {            
            DAO_Check.Delete(check);
            List_Check.remove(check);
        }
        catch(Exception e){
            return page_error;               
        }                 
        
        return page_success;                
    }        
    
    public Check getCheck(){
        return check;
    }

    public void setCheck(Check check){
        this.check = check;
    }
    
    public List<Check> getList_Check(){
        return List_Check;
    }
    
    public String UpdateList(){
        try {
            List_Check = DAO_Check.findAll();
        }    
        catch(Exception e){
            return page_error;               
        }                 
        
        return page_success;
    }
    
}
