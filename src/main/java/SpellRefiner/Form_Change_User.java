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

@Named(value = "form_change_user")
@RequestScoped
public class Form_Change_User implements Serializable {
	
    //private Boolean closed = false;
        
    @EJB
    private DAO_Interface_User DAO_User;        
    
    @Inject
    private CurrentUser currentUser; 
    
    private User user;
    
    Form_Change_User(){
    	user = new User();
    }
    
    
    @PostConstruct
    public void Init(){
    	//closed = false;
    	
    	User u = currentUser.getUser();
    	
    	if (u!=null){
    		user.setUserLogin(u.getUserLogin());
    		user.setUserName(u.getUserName());
    		user.setUserEmail(u.getUserEmail());
    	}
    	
        
    }    
    
    public User getUser() {
        return user;
    }

    /*
    public Boolean getClosed() {
        return closed;
    }
    */
    
    
    private void AddMessage(String clientId, FacesMessage.Severity severity, String summary, String detail){
        FacesContext.getCurrentInstance().addMessage(clientId, new FacesMessage(severity, summary, detail));
    }    
    
    
    
    public void Save(){
    	
        User u = currentUser.getUser();

    	if (u==null){
    		return;
    	};
        
    	if (user.getUserPassword().isEmpty()){
            AddMessage(null, FacesMessage.SEVERITY_ERROR, "Введите текущий пароль", "");
            return;                
    	}
    	
    	/*
        List<User> users = DAO_User.findByUserLoginPassword(u.getUserLogin(), user.getUserPassword());
        if (users.isEmpty()){
            AddMessage(null, FacesMessage.SEVERITY_ERROR, "Неверный пароль", "");
            return;                
        } 
        */   
        
        if (!DAO_User.CheckUserLoginAndPassword(u.getUserLogin(), user.getUserPassword())){
            AddMessage(null, FacesMessage.SEVERITY_ERROR, "Неверный пароль", "");
            return;                
        }       
        
        
    	if (user.getUserLogin().isEmpty()){
            AddMessage(null, FacesMessage.SEVERITY_ERROR, "Введите логин", "");
            return;             
    	}   	
        
    	if (user.getUserName().isEmpty()){
            AddMessage(null, FacesMessage.SEVERITY_ERROR, "Введите имя", "");
            return;             
    	}   	
        
        
        
        if (!u.getUserLogin().equals(user.getUserLogin())){
        	
        	List<User> users = DAO_User.findByUserLogin(user.getUserLogin());
        	if (!users.isEmpty()){
        		AddMessage(null, FacesMessage.SEVERITY_ERROR, "Пользователь с таким логином уже существует", "");
        		return;                
        	}
        }
        
        
        u.setUserLogin(user.getUserLogin());
        u.setUserName(user.getUserName());
        u.setUserEmail(user.getUserEmail());
        
        DAO_User.Update(u);
        
        AddMessage(null, FacesMessage.SEVERITY_INFO, "Пользователь изменен", "");

        //closed = true;
        
    }    
    
    
}

