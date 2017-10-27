package SpellRefiner;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.persistence.Transient;


@Entity
@Table(name = "users")
//@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
    @NamedQuery(name = "User.findByUserID", query = "SELECT u FROM User u WHERE u.userID = :userID"),
    @NamedQuery(name = "User.findByUserLogin", query = "SELECT u FROM User u WHERE u.userLogin = :userLogin"),
    @NamedQuery(name = "User.findByUserLoginPassword", query = "SELECT u FROM User u WHERE u.userLogin = :userLogin and u.userPassword = :userPassword"),
    @NamedQuery(name = "User.findByUserLoginPasswordEncrypt", query = "SELECT u FROM User u WHERE u.userLogin = :userLogin and u.userPasswordEncrypt = :userPasswordEncrypt")
    
    //@NamedQuery(name = "User.findByUserName", query = "SELECT u FROM User u WHERE u.userName = :userName"),
    //@NamedQuery(name = "User.findByUserEmail", query = "SELECT u FROM User u WHERE u.userEmail = :userEmail"),
    //@NamedQuery(name = "User.findByUserPassword", query = "SELECT u FROM User u WHERE u.userPassword = :userPassword"),
    //@NamedQuery(name = "User.findByUserAuthenticationString", query = "SELECT u FROM User u WHERE u.userAuthenticationString = :userAuthenticationString")
})
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "User_ID")
    private Integer userID;
    @Size(max = 100)
    @Column(name = "User_Login")
    private String userLogin;
    @Size(max = 100)
    @Column(name = "User_Name")
    private String userName;
    @Size(max = 100)
    @Column(name = "User_Email")
    private String userEmail;
    
    
    @Size(max = 100)
    @Column(name = "User_Password")
    private String userPassword;
    //@Size(max = 100)
    //@Column(name = "User_AuthenticationString")
    //private String userAuthenticationString;
    
    @Column(name = "User_Admin")
    @Convert(converter = BooleanToIntegerConverter.class)
    private Boolean userAdmin = false;
    
    
    @Size(max = 200)
    @Column(name = "User_Password_Encrypt")
    private String userPasswordEncrypt;
    
    @Size(max = 100)
    @Column(name = "User_Password_Salt")
    private String userPasswordSalt;
    
    @Transient
    private String userConfirmPassword;

    public User() {
    }

    public User(Integer userID) {
        this.userID = userID;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserConfirmPassword() {
        return userConfirmPassword;
    }

    public void setUserConfirmPassword(String userConfirmPassword) {
        this.userConfirmPassword = userConfirmPassword;
    }
    
    /*
    public String getUserAuthenticationString() {
        return userAuthenticationString;
    }

    public void setUserAuthenticationString(String userAuthenticationString) {
        this.userAuthenticationString = userAuthenticationString;
    }
    */
    
    public Boolean getUserAdmin() {
        return userAdmin;
    }

    public void setUserAdmin(Boolean userAdmin) {
        this.userAdmin = userAdmin;
    }
    
    
    public String getUserPasswordEncrypt() {
        return userPasswordEncrypt;
    }

    public void setUserPasswordEncrypt(String userPasswordEncrypt) {
        this.userPasswordEncrypt = userPasswordEncrypt;
    }
    
    public String getUserPasswordSalt() {
        return userPasswordSalt;
    }

    public void setUserPasswordSalt(String userPasswordSalt) {
        this.userPasswordSalt = userPasswordSalt;
    }
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userID != null ? userID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.userID == null && other.userID != null) || (this.userID != null && !this.userID.equals(other.userID))) {
            return false;
        }
        return true;
    }
    

    @Override
    public String toString() {
        return "SpellRefiner.User[ userID=" + userID + " ]";
    }
    
}
