package SpellRefiner;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;

@Named(value = "currentUser")
@SessionScoped
public class CurrentUser implements Serializable {
    
    @EJB
    private DAO_Interface_User DAO_User;       
    
    
    private User user = new User();   
    private Boolean Authorized = false;   
    private Boolean Testing = false;
    
    
    
    @PostConstruct
    public void Init(){
        Test_AutoLogin();
        
    }
    
    public User getUser(){
        return user;
    }

    public void setUser(User u){
        this.Authorized = (u!= null);
        this.user = u;
    }    

    public Boolean getAuthorized(){
        return Authorized;
    }

    public Boolean getTesting(){
        return Testing;
    }

    public void setTesting(Boolean testing){
        this.Testing = testing;
    }    
    
    
    public String LogOut(){
        setUser(null);
        Testing = false;
        
        //return null;
        return "index.xhtml";
    }
    
    
    public String Test_AutoLogin(){

    	String stage = DAO_User.GetInitValue("javax.faces.PROJECT_STAGE");
    	
    	if (stage!=null){
    		if (stage.equals("Development")){
    			
    	        List<User> users = DAO_User.findByUserLogin("l1");
    	        if (!users.isEmpty()){
    	            setUser(users.get(0));
    	        }
    		}
    	}
    	
        
        return null;
        
    }    
    
    
    
}

