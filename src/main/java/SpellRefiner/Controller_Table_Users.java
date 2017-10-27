package SpellRefiner;

import java.io.Serializable;
import javax.inject.Named;
import javax.ejb.EJB;
//import javax.enterprise.context.RequestScoped;
import java.util.List;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;

@Named(value = "controller_table_users")
//@RequestScoped
@ViewScoped
public class Controller_Table_Users implements Serializable {
    private static final String page_success = null;
    private static final String page_error = "error";
    
    @EJB
    private DAO_Interface_User DAO_User;        
    
    private User user = new User();   
    private List<User> List_User;
    
    public Controller_Table_Users(){
        List_User = new ArrayList<User>();
    }
    
    @PostConstruct
    public void Init(){
        UpdateList();
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
            List_User.remove(user);
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
    
    public List<User> getList_User(){
        return List_User;
    }
    
    public String UpdateList(){
        try {
            List_User = DAO_User.findAll();
        }    
        catch(Exception e){
            return page_error;               
        }                 
        
        return page_success;
    }
    

    
}

