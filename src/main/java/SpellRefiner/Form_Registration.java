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

@Named(value = "form_Registration")
@RequestScoped
public class Form_Registration implements Serializable {
	
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
    public String ActionRegistration_Old(){
        
        List<User> users = DAO_User.findByUserLogin(user.getUserLogin());
        if (!users.isEmpty()){
            AddMessage(null, FacesMessage.SEVERITY_ERROR, "Пользователь с таким логином уже существует", "");
            return null;                
        }
        
        String password = user.getUserPassword();
        String salt = DAO_User.GetSalt();
        String password_encrypt = DAO_User.GetEncryptedPasswordWithSalt(password, salt);
        
        if (password_encrypt == null){
            AddMessage(null, FacesMessage.SEVERITY_ERROR, "Системная ошибка", "");
            return null;                
        }
        
        user.setUserPasswordSalt(salt);
        user.setUserPasswordEncrypt(password_encrypt);
        
        user.setUserPassword("");
        
        user.setUserAdmin(Boolean.FALSE);
        DAO_User.SaveNew(user);
        
        currentUser.setUser(user);
        
        user = new User();
        
        AddMessage(null, FacesMessage.SEVERITY_INFO, "Регистрация успешно завершена", "");

        closed = true;
        
        return null;
    }    
    

    public String CheckError(){
        
    	if (user.getUserLogin().isEmpty()){
    		return "Введите логин";
    	}

    	if (user.getUserName().isEmpty()){
    		return "Введите имя";
    	}

    	if (user.getUserPassword().isEmpty()){
    		return "Введите пароль";
    	}

    	if (user.getUserConfirmPassword().isEmpty()){
    		return "Введите подтверждение пароля";
    	}
    	
    	if (!user.getUserPassword().equals(user.getUserConfirmPassword())){
    		return "Пароль и подтверждение пароля не совпадают";
    	}
    	
    	
        List<User> users = DAO_User.findByUserLogin(user.getUserLogin());
        if (!users.isEmpty()){
        	return "Пользователь с таким логином уже существует";
        }
        
        return null;
        
    }
    
    public Boolean DoRegistration(){
        
        String password = user.getUserPassword();
        String salt = DAO_User.GetSalt();
        String password_encrypt = DAO_User.GetEncryptedPasswordWithSalt(password, salt);
        
        if (password_encrypt == null){
            //Системная ошибка
            return false;                
        }
        
        user.setUserPasswordSalt(salt);
        user.setUserPasswordEncrypt(password_encrypt);
        
        user.setUserPassword("");
        
        user.setUserAdmin(Boolean.FALSE);
        DAO_User.SaveNew(user);
        
        currentUser.setUser(user);
        
        user = new User();
        
        
        return true;
    }    
    
    
    public String ActionRegistration(String form_id){
    	
    	String error = CheckError();
    	
    	if (error!=null){
    		AddMessage(form_id, FacesMessage.SEVERITY_ERROR, error, "");
    		return null;
    	}
    	
    	if (!DoRegistration()){
    		AddMessage(form_id, FacesMessage.SEVERITY_ERROR, "Системная ошибка", "");
    		return null;
    	}
        
        
        AddMessage(form_id, FacesMessage.SEVERITY_INFO, "Регистрация успешно завершена", "");

        closed = true;
        
        return null;
    }    
    
}

