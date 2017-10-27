package SpellRefiner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.ejb.Stateful;
//import javax.ejb.TransactionManagement;
//import javax.ejb.TransactionManagementType;
//import javax.ejb.TransactionAttribute;
//import static javax.ejb.TransactionAttributeType.REQUIRED;
//import static javax.ejb.TransactionAttributeType.REQUIRES_NEW;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;


@Stateless
//@Stateful
//@TransactionManagement(value=TransactionManagementType.CONTAINER)
//@TransactionAttribute(value=REQUIRED)
//@TransactionAttribute(value=REQUIRES_NEW)
public class DAO_User implements DAO_Interface_User{    
//public class TestBean15{    
    @PersistenceContext(unitName = "SpellRefiner")
    private EntityManager em;                
    
    //public DAO_User(){
    //}    
    
    @Override
    public void SaveNew(User u){   
       em.persist(u);        
    }            
    
    
    @Override
    public void Update(User u){    
       em.merge(u);        
    }            

    @Override
    public void Delete(User u){    
       User u1 = em.merge(u);        
       em.remove(u1);        
    }            
    
    /*
    @Override
    public User Get(Integer id){    
        User u = em.find(User.class, id);
        return u;
    } 
    */

    @Override
    public List<User> findAll(){    
        Query query = em.createNamedQuery("User.findAll");
        List<User> list = query.getResultList();
        
        return list;
    }   
    
    
    @Override
    public List<User> findByUserID(Integer userID){    
        Query query = em.createNamedQuery("User.findByUserID");
        query.setParameter("userID", userID);
        List<User> list = query.getResultList();
        
        return list;
    }   
    
    @Override
    public List<User> findByUserLoginPassword(String userLogin, String userPassword){    
        Query query = em.createNamedQuery("User.findByUserLoginPassword");
        query.setParameter("userLogin", userLogin);
        query.setParameter("userPassword", userPassword);
        List<User> list = query.getResultList();
        
        return list;
    }   
    
    @Override
    public List<User> findByUserLogin(String userLogin){    
        Query query = em.createNamedQuery("User.findByUserLogin");
        query.setParameter("userLogin", userLogin);
        List<User> list = query.getResultList();
        
        return list;
    }   

    /*
    @Override
    public List<User> findByUserLoginPasswordEncrypt(String userLogin, String userPasswordEncrypt){    
        Query query = em.createNamedQuery("User.findByUserLoginPasswordEncrypt");
        query.setParameter("userLogin", userLogin);
        query.setParameter("userPasswordEncrypt", userPasswordEncrypt);
        List<User> list = query.getResultList();
        
        return list;
    } 
    */  
    
    
    /*
    public String GetEncryptedPassword(String password, MessageDigest md){
       	    //System.out.println("password=" + password);
    	
            byte[] passBytes = password.getBytes();
            //md.reset();
            byte[] digested = md.digest(passBytes);
            
            StringBuffer sb = new StringBuffer();
            for(int i=0;i<digested.length;i++){            	 
                sb.append(Integer.toHexString(0xff & digested[i]));
            }
            
            return sb.toString();
    }
    
    */
    
    public String GetHexStringFromBytes(byte[] bytes){
        StringBuffer sb = new StringBuffer();
        for(int i=0; i< bytes.length;i++){            	 
            sb.append(Integer.toHexString(0xff & bytes[i]));
        }
        
        return sb.toString();
     }

    
    
    /*
    @Override
    public String GetEncryptedPasswordWithSalt(String password, String salt, int NumberOfIteration){
    	System.out.println("password=" + password);
    	
    	MessageDigest md;
    	   
        try {
            md = MessageDigest.getInstance("MD5");
            md.reset();
            
            String password_encrypted;
            password_encrypted = password.concat(salt);
            
        	for(int i = 0; i < NumberOfIteration; i++){
        		password_encrypted = GetEncryptedPassword(password_encrypted, md);
        		System.out.println("password_encrypted=" + password_encrypted);
             }
            
        	return password_encrypted;
            
        } catch (NoSuchAlgorithmException ex) {
        }

        return null;
       }
       */
       
       
    @Override
    public String GetEncryptedPasswordWithSaltAndNumberOfIteration(String password, String salt, String fixedsalt, int NumberOfIteration){
    	MessageDigest md;

    	
        try {
        	md = MessageDigest.getInstance("SHA-1");
            md.reset();
            
            
            String password_with_salt = "";
            password_with_salt = password_with_salt.concat(password);
            password_with_salt = password_with_salt.concat(salt);
            password_with_salt = password_with_salt.concat(fixedsalt);
            
            byte[] bytes = password_with_salt.getBytes("UTF-8");
            
            String password_encrypted;
            
        	for(int i = 0; i < NumberOfIteration; i++){
        		bytes = md.digest(bytes);
             }
            
        	password_encrypted = GetHexStringFromBytes(bytes);
        	
        	return password_encrypted;
        } 
        catch (NoSuchAlgorithmException ex) {
        }
        catch (UnsupportedEncodingException ex){        	
        }

        return null;
    }
    

    @Override
    public String GetEncryptedPasswordWithSalt(String password, String salt){
    	int NumberOfIteration = 10000;
    	String fixedsalt = "8367616e6ca2f9d342e75e8437ad9c6ff55ca08";
    	String password_encrypted = GetEncryptedPasswordWithSaltAndNumberOfIteration(password, salt, fixedsalt, NumberOfIteration);
    	return password_encrypted;
    	
    }
    
    
    /*
    @Override
    public String GetSalt(){
    	System.out.println("GetSalt---------------------");
    	
    	int min = 1000000;
    	int max = 9000000;
    	Random rand = new Random();
    	
    	int n;
    	String salt = "";
    	
    	n = rand.nextInt(max - min) + min;
    	System.out.println("n = " + n);
    	salt = Integer.toHexString(n);
    	
    	n = rand.nextInt(max - min) + min;
    	System.out.println("n = " + n);
    	salt = salt + Integer.toHexString(n);

        return salt;
    }
	*/
    
    @Override
    public String GetSalt(){
    	//System.out.println("GetSalt---------------------");
    	
    	Random r = new SecureRandom();
    	
    	byte[] bytes = new byte[20];
    	r.nextBytes(bytes);
    	
    	String salt = GetHexStringFromBytes(bytes);
    	
        return salt;
    }
    
    
    @Override
    public boolean CheckUserLoginAndPassword(String userLogin, String userPassword){
    	//System.out.println("CheckUserLoginAndPassword---------------------");
    	
    	//System.out.println("userLogin=" + userLogin);
    	//System.out.println("userPassword=" + userPassword);
    	
        List<User> users = findByUserLogin(userLogin);
        if (users.isEmpty()){
        	return false;
        }
         
        User u = users.get(0);
        System.out.println("u=" + u);
        
        String salt = u.getUserPasswordSalt();
        //System.out.println("salt=" + salt);
        
        if(salt==null){
        	return false;
        }
        
        String password_encrypt = GetEncryptedPasswordWithSalt(userPassword, salt);
    	//System.out.println("password_encrypt=" + password_encrypt);
             
    	if (password_encrypt==null){
    		return false;
    	}
    	
    	//System.out.println("u.getUserPasswordEncrypt()=" + u.getUserPasswordEncrypt());

        
        if (password_encrypt.equals(u.getUserPasswordEncrypt())){
        	return true;
        }
        
        return false;
    } 
    
    
    
    @Override
    public String GetInitValue(String param){
    
    
    	Map<String, Object> parameters;
	  
    	FacesContext facesContext = FacesContext.getCurrentInstance();
    	if(facesContext != null) {
  	  
    		ExternalContext ctx = facesContext.getExternalContext();
    		parameters = ctx.getInitParameterMap();
        
    		Object value = parameters.get(param);
    		
    		if (value!=null){
        		String value_str = (String)value;
        		return value_str; 
    		}
        
    	}
    	
    	return null;
    }
    
    
}


/*
public class CryptWithMD5 {
	   private static MessageDigest md;

	   public static String cryptWithMD5(String pass){
	    try {
	        md = MessageDigest.getInstance("MD5");
	        byte[] passBytes = pass.getBytes();
	        md.reset();
	        byte[] digested = md.digest(passBytes);
	        StringBuffer sb = new StringBuffer();
	        for(int i=0;i<digested.length;i++){
	            sb.append(Integer.toHexString(0xff & digested[i]));
	        }
	        return sb.toString();
	    } catch (NoSuchAlgorithmException ex) {
	        Logger.getLogger(CryptWithMD5.class.getName()).log(Level.SEVERE, null, ex);
	    }
	        return null;


	   }
	}

*/