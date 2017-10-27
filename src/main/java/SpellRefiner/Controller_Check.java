package SpellRefiner;

import java.io.Serializable;
import javax.inject.Named;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
//import javax.faces.view.ViewScoped;

@Named(value = "controller_check")
@RequestScoped
//@ViewScoped
public class Controller_Check implements Serializable {
    private static final String page_success = null;//"success";
    private static final String page_error = "error";
    
    @EJB
    private DAO_Interface_Check DAO_Check;        
    
    private Check check = new Check();   
    
    
    public String SaveNew(){
        try {
            DAO_Check.SaveNew(check);
        }
        catch(Exception e){
            return page_error;               
        }                 
        
        return page_success;                
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
    
    
    
}
