package SpellRefiner;

import java.io.Serializable;
import javax.inject.Named;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;

@Named(value = "controller_user")
@RequestScoped
public class Controller_User implements Serializable {
    private static final String page_success = null;//"success";
    private static final String page_error = "error";
    
    @EJB
    private DAO_Interface_User DAO_User;        
    
    private User user = new User();   
    
    
    public String SaveNew(){
        try {
            DAO_User.SaveNew(user);
        }
        catch(Exception e){
            return page_error;               
        }                 
        
        return page_success;                
    }    
    
    
    public String Update(){
        try {
            DAO_User.Update(user);
        }
        catch(Exception e){
            return page_error;               
        }                 
        
        return page_success;                
    }    
    
    public String Delete(){
        try {            
            DAO_User.Delete(user);
        }
        catch(Exception e){
            return page_error;               
        }                 
        
        return page_success;                
    }        
    
    public User getUser(){
        return user;
    }

    public void setUser(User u){
        this.user = u;
    }    
    
}
