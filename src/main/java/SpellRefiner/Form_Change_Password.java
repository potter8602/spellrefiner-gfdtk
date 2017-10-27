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

@Named(value = "form_change_password")
@RequestScoped
public class Form_Change_Password implements Serializable {
        
	private Boolean closed = false;
	
    @EJB
    private DAO_Interface_User DAO_User;        
    
    @Inject
    private CurrentUser currentUser;                

    
    private String oldPassword;
    private User user = new User();
    
    @PostConstruct
    public void Init(){
    	closed = false;
        
    }    
    
    
    public User getUser() {
        return user;
    }
    
    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
    
    public Boolean getClosed() {
        return closed;
    }
    
    
    private void AddMessage(String clientId, FacesMessage.Severity severity, String summary, String detail){
        FacesContext.getCurrentInstance().addMessage(clientId, new FacesMessage(severity, summary, detail));
    }    
    
    public String ChangePassword(){
        
        
        if (!currentUser.getAuthorized()){
            AddMessage(null, FacesMessage.SEVERITY_ERROR, "Вход не выполнен", "");
            return null;                            
        }
        
        /*
        List<User> users = DAO_User.findByUserLoginPassword(currentUser.getUser().getUserLogin(), oldPassword);
        if (users.isEmpty()){
            AddMessage(null, FacesMessage.SEVERITY_ERROR, "Неверный пароль", "");
            return null;                
        }
        */
        
        if (!DAO_User.CheckUserLoginAndPassword(currentUser.getUser().getUserLogin(), oldPassword)){
            AddMessage(null, FacesMessage.SEVERITY_ERROR, "Неверный пароль", "");
            return null;                
        }       
        
        String password = user.getUserPassword();
        String salt = DAO_User.GetSalt();
        String password_encrypt = DAO_User.GetEncryptedPasswordWithSalt(password, salt);
        
        
        if (password_encrypt == null){
            AddMessage(null, FacesMessage.SEVERITY_ERROR, "Системная ошибка", "");
            return null;                
        }
        
        currentUser.getUser().setUserPasswordSalt(salt);
        currentUser.getUser().setUserPasswordEncrypt(password_encrypt);
        currentUser.getUser().setUserPassword("");
        
        
        //currentUser.getUser().setUserPassword(user.getUserPassword());
        DAO_User.Update(currentUser.getUser());        
        
        
        AddMessage(null, FacesMessage.SEVERITY_INFO, "Пароль изменен", "");        
        
        closed = true;
        
        return null;                
    }    
    


}

