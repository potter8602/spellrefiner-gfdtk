package SpellRefiner;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.inject.Named;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.faces.event.AjaxBehaviorEvent;




@Named(value = "form_test")
@RequestScoped
public class Form_Test implements Serializable {

    @EJB
    private DAO_Interface_Word DAO_Word;        

    @EJB
    private DAO_Interface_User DAO_User;
    
    @EJB
    private DAO_Interface_Dictionary DAO_Dictionary;
    
    @EJB
    private DAO_Interface_Check DAO_Check;
    
    public Form_Test(){
    }    
    
    @PostConstruct
    public void Init(){
    }
    
    public void Test1(){
    	
    	   	
    	//boolean access;
    	
    	//access = DAO_Word.CheckPermissionForWord(1, 1);
    	//System.out.println("access=" + access);
    	
    	//access = DAO_Word.CheckPermissionForWord(1, 14);
    	//System.out.println("access=" + access);

    	//access = DAO_Word.CheckPermissionForWord(8781, 14);
    	//System.out.println("access=" + access);
    	
    	//access = DAO_Word.CheckPermissionForWord(8781, 1);
    	//System.out.println("access=" + access);
    	
    	//User u = new User();
    	//List<Check> List_Check = DAO_Check.MyChecks(u, false, true);
    	//List<Check> List_Check = DAO_Check.MyChecks(null, false, true);
    	
    }    

    public void Test2(){
    	//String result;
    	//result = DAO_User.GetEncryptedPasswordWithSalt("", "123456789012", 1);
    	//System.out.println("encrypt=" + result);

    	//result = DAO_User.GetEncryptedPasswordWithSalt("1", "123456789012", 1);
    	//System.out.println("encrypt=" + result);
    	
    	//result = DAO_User.GetEncryptedPasswordWithSalt("123456", "123456789012", 1);
    	//System.out.println("encrypt=" + result);

    	//result = DAO_User.GetEncryptedPasswordWithSalt("asdfghjklzxcvbnmqwertyuiop", "123456789012", 1);
    	//System.out.println("encrypt=" + result);

    	//result = DAO_User.GetEncryptedPasswordWithSalt("asdfghjklzxcvbnmqwertyuiop", "123456789012", 10);
    	//System.out.println("encrypt=" + result);
    	
    	//result = DAO_User.GetSalt();
    	//System.out.println("salt=" + result);

    	//result = DAO_User.GetSalt();
    	//System.out.println("salt=" + result);

    	//result = DAO_User.GetSalt();
    	//System.out.println("salt=" + result);

    	/*
    	String fixedsalt = "8367616e6ca2f9d342e75e8437ad9c6ff55ca08";
    	String salt = DAO_User.GetSalt();
    	System.out.println("salt=" + salt);
    	
    	String password = "asdfghjklzxcvbnmqwertyuiop";
    	
    	String password_encrypt;
    	
    	password_encrypt = DAO_User.GetEncryptedPasswordWithSaltAndNumberOfIteration(password, salt, fixedsalt, 1);
    	System.out.println("password_encrypt=" + password_encrypt);
    	
    	password_encrypt = DAO_User.GetEncryptedPasswordWithSaltAndNumberOfIteration(password, salt, fixedsalt, 1);
    	System.out.println("password_encrypt=" + password_encrypt);

    	password_encrypt = DAO_User.GetEncryptedPasswordWithSaltAndNumberOfIteration(password, salt, fixedsalt, 1000);
    	System.out.println("password_encrypt=" + password_encrypt);

    	password_encrypt = DAO_User.GetEncryptedPasswordWithSaltAndNumberOfIteration(password, salt, fixedsalt, 1000);
    	System.out.println("password_encrypt=" + password_encrypt);

    	password_encrypt = DAO_User.GetEncryptedPasswordWithSaltAndNumberOfIteration(password, salt, fixedsalt, 10000);
    	System.out.println("password_encrypt=" + password_encrypt);

    	password_encrypt = DAO_User.GetEncryptedPasswordWithSaltAndNumberOfIteration(password, salt, fixedsalt, 10000);
    	System.out.println("password_encrypt=" + password_encrypt);

    	password_encrypt = DAO_User.GetEncryptedPasswordWithSaltAndNumberOfIteration(password, salt, fixedsalt, 100000);
    	System.out.println("password_encrypt=" + password_encrypt);

    	password_encrypt = DAO_User.GetEncryptedPasswordWithSaltAndNumberOfIteration(password, salt, fixedsalt, 100000);
    	System.out.println("password_encrypt=" + password_encrypt);
    	*/
    	
    	
    	/*
    	byte i1 = 0;	
    	for(int i=0;i<300;i++){
    		//byte i1 = (byte)i;
    		
    		i1++;
    		
    		System.out.println("byte i1=" + i1);
    		System.out.println("byte i1 hex=" + Integer.toHexString(i1));
         }
         */    		
    	
    	/*
        List<User> users = DAO_User.findAll();
  	

        for (User u: users){
            
            String password = u.getUserPassword();
            String salt = DAO_User.GetSalt();
            String password_encrypt = DAO_User.GetEncryptedPasswordWithSalt(password, salt);
            
            
            u.setUserPasswordSalt(salt);
            u.setUserPasswordEncrypt(password_encrypt);
            
            DAO_User.Update(u);        
                     	
            
        }
           
        */    	
    	
    	/*
    	  Map<String, Object> parameters;
    	  
    	  System.out.println("1");
    	  
    	  FacesContext facesContext = FacesContext.getCurrentInstance();
          if(facesContext != null) { //this is always null
        	  
        	  System.out.println("2");
              ExternalContext ctx = facesContext.getExternalContext();
              parameters = ctx.getInitParameterMap();
              
              String value = (String)parameters.get("javax.faces.PROJECT_STAGE");
              System.out.println("value=" + value);
              
          } 
          */
    	
    	String value;
    	value = DAO_User.GetInitValue("javax.faces.PROJECT_STAGE");
    	System.out.println("value=" + value);
    	
    	value = DAO_User.GetInitValue("javax.faces.PROJECT_STAGE1");
    	System.out.println("value=" + value);
    	
    }
    
    public void Test_ReadAccess(Integer dictID, Integer userID){
    	boolean access;
    	
    	//List<Dictionary> list1 = DAO_Dictionary.findByDictID(dictID);
    	//Dictionary dict = list1.get(0);
    	
    	//List<User> list2 = DAO_User.findByUserID(userID);
    	//User user = list2.get(0);
    	
    	access = DAO_Dictionary.ReadAccess(dictID, userID);
    	System.out.println("access=" + access);
    	
    }    
    
    public void Test_WriteAccess(Integer dictID, Integer userID){
    	boolean access;
    	
    	//List<Dictionary> list1 = DAO_Dictionary.findByDictID(dictID);
    	//Dictionary dict = list1.get(0);
    	
    	//List<User> list2 = DAO_User.findByUserID(userID);
    	//User user = list2.get(0);
    	
    	access = DAO_Dictionary.WriteAccess(dictID, userID);
    	System.out.println("WriteAccess=" + access);
    	
    }    
    
    public void Test3(){
    	
    	Test_ReadAccess(1, 1);
    	Test_ReadAccess(2, 1);
    	Test_ReadAccess(3, 1);
    	
    	Test_ReadAccess(1, 14);
    	Test_ReadAccess(2, 14);
    	Test_ReadAccess(3, 14);
    	
    }    
    
    
    
}
