package SpellRefiner;


//import javax.ejb.Local;
import javax.ejb.Remote;
import java.util.List;

//@Local
@Remote
public interface DAO_Interface_User{
    public void SaveNew(User u);
    public void Update(User u);
    public void Delete(User u);    
    //public User Get(Integer id);
    public List<User> findAll();
    public List<User> findByUserID(Integer userID);    
    public List<User> findByUserLoginPassword(String userLogin, String userPassword);    
    public List<User> findByUserLogin(String userLogin);
    //public List<User> findByUserLoginPasswordEncrypt(String userLogin, String userPasswordEncrypt);
    public boolean CheckUserLoginAndPassword(String userLogin, String userPassword);
    public String GetEncryptedPasswordWithSaltAndNumberOfIteration(String password, String salt, String fixedsalt, int NumberOfIteration);
    public String GetEncryptedPasswordWithSalt(String password, String salt);
    public String GetSalt();
    public String GetInitValue(String param);
}
