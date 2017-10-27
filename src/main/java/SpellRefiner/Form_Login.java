package SpellRefiner;

import java.io.Serializable;
import javax.inject.Named;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

@Named(value = "form_login")
@ViewScoped
public class Form_Login implements Serializable {
    
	private Boolean closed = false;
	
    @EJB
    private DAO_Interface_User DAO_User;        
    
    @Inject
    private CurrentUser currentUser;                

    private User user = new User();
    
    @PostConstruct
    public void Init(){
    	closed = false;
        
    }    
    
    
    public User getUser() {
        return user;
    }
    
    public Boolean getClosed() {
        return closed;
    }
    
    public void setClosed(Boolean closed) {
        this.closed = closed;
    }
    
    private void AddMessage(String clientId, FacesMessage.Severity severity, String summary, String detail){
        FacesContext.getCurrentInstance().addMessage(clientId, new FacesMessage(severity, summary, detail));
    }    
    
    //not used
    public String ActionLogin_Old(){
    	
    	if (user.getUserLogin().isEmpty()){
            AddMessage(null, FacesMessage.SEVERITY_ERROR, "Введите логин", "");
            return null;             
    	}

    	if (user.getUserPassword().isEmpty()){
            AddMessage(null, FacesMessage.SEVERITY_ERROR, "Введите пароль", "");
            return null;                
    	}
    	
    	
        List<User> users = DAO_User.findByUserLogin(user.getUserLogin());
        if (users.isEmpty()){
            AddMessage(null, FacesMessage.SEVERITY_ERROR, "Неверный логин или пароль", "");
            return null;                
        }    	
        

        if (!DAO_User.CheckUserLoginAndPassword(user.getUserLogin(), user.getUserPassword())){
            AddMessage(null, FacesMessage.SEVERITY_ERROR, "Неверный логин или пароль", "");
            return null;               
        	
        }       

        
        
        currentUser.setUser(users.get(0));
        currentUser.setTesting(false);
        
        AddMessage(null, FacesMessage.SEVERITY_INFO, "Вход выполнен успешно", "");        
        
        closed = true;
        
        return null;                
    }    

    
    public String CheckError(){
    	
    	if (user.getUserLogin().isEmpty()){
    		return "Введите логин";
    	}

    	if (user.getUserPassword().isEmpty()){
    		return "Введите пароль";
    	}
    	
    	
        List<User> users = DAO_User.findByUserLogin(user.getUserLogin());
        if (users.isEmpty()){
        	return "Неверный логин или пароль";
        }    	
        

        if (!DAO_User.CheckUserLoginAndPassword(user.getUserLogin(), user.getUserPassword())){
        	return "Неверный логин или пароль";
        }     

        
        return null;                
    }    
    

    public void DoLogin(){

    	
        List<User> users = DAO_User.findByUserLogin(user.getUserLogin());
        if (users.isEmpty()){
            return;                
        }  	
    	
        currentUser.setUser(users.get(0));
        currentUser.setTesting(false);
        
    }    

    public String ActionLogin(String form_id){
    	
    	String error = CheckError();
    	if (error!=null){
            AddMessage(form_id, FacesMessage.SEVERITY_ERROR, error, "");
            return null;             
    	};
    	

    	DoLogin();
        
        AddMessage(form_id, FacesMessage.SEVERITY_INFO, "Вход выполнен успешно", "");        
        
        closed = true;
        
        return null;                
    }    
    
    
}


