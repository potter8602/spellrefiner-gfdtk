/*
 * Here comes the text of your license
 * Each line should be prefixed with  * 
 */
package SpellRefiner;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

@Entity
@Table(name = "checks")
@NamedQueries({
    @NamedQuery(name = "Check.findAll", query = "SELECT c FROM Check c"),
    @NamedQuery(name = "Check.MyChecks", query = "SELECT c FROM Check c WHERE c.checkUser = :user AND c.checkDeleted = :deleted AND (:only_unfinished = false or c.checkFinished = false) ORDER BY c.checkDateTime DESC"),
    @NamedQuery(name = "Check.findByCheckID", query = "SELECT c FROM Check c WHERE c.checkID = :checkID"),
    //@NamedQuery(name = "Check.findByDictID", query = "SELECT c FROM Check c WHERE c.dictID = :dictID"),
    //@NamedQuery(name = "Check.findByUserID", query = "SELECT c FROM Check c WHERE c.userID = :userID"),
    //@NamedQuery(name = "Check.findByCheckName", query = "SELECT c FROM Check c WHERE c.checkName = :checkName"),
    //@NamedQuery(name = "Check.findByCheckDateTime", query = "SELECT c FROM Check c WHERE c.checkDateTime = :checkDateTime")
    @NamedQuery(name = "Check.NumberOfMyChecks", query = "SELECT COUNT(c) FROM Check c WHERE c.checkUser = :user")
})
public class Check implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)    
    @Column(name = "Check_ID")
    private Integer checkID;
    
    @Column(name = "Dict_ID")
    private Integer dictID;
    
    @Column(name = "User_ID")
    private Integer userID;
    
    @Size(max = 100)
    @Column(name = "Check_Name")
    private String checkName;
    
    @Column(name = "Check_DateTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date checkDateTime;
    
    @Column(name = "Check_Deleted")
    @Convert(converter = BooleanToIntegerConverter.class)
    private Boolean checkDeleted = false;
    
    @Column(name = "Check_Pass")
    private Integer checkPass = 0;
    
    @Column(name = "Check_Finished")
    @Convert(converter = BooleanToIntegerConverter.class)
    private Boolean checkFinished = false;
    
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="Dict_ID", nullable=false, insertable=false, updatable=false)
    private Dictionary checkDict;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="User_ID", nullable=false, insertable=false, updatable=false)
    private User checkUser;
    
    @Transient
    Integer numberOfCheckedWords;
    

    public Check() {
    }

    public Check(Integer checkID) {
    	
        this.checkID = checkID;
    }

    public Integer getCheckID() {
        return checkID;
    }

    public void setCheckID(Integer checkID) {
        this.checkID = checkID;
    }

    public Integer getDictID() {
        return dictID;
    }

    public void setDictID(Integer dictID) {
        this.dictID = dictID;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getCheckName() {
        return checkName;
    }

    public void setCheckName(String checkName) {
        this.checkName = checkName;
    }

    public Date getCheckDateTime() {
        return checkDateTime;
    }

    public void setCheckDateTime(Date checkDateTime) {
        this.checkDateTime = checkDateTime;
    }
    
    
    public Boolean getCheckDeleted() {
        return checkDeleted;
    }

    public void setCheckDeleted(Boolean checkDeleted) {
        this.checkDeleted = checkDeleted;
    }
    
    
    public Integer getCheckPass() {
        return checkPass;
    }

    public void setCheckPass(Integer checkPass) {
        this.checkPass = checkPass;
    }
    
    
    public Dictionary getCheckDict() { 
        return checkDict; 
    }        
   
    public void setCheckDict(Dictionary checkDict) {
        this.checkDict = checkDict;
    }

    public User getCheckUser() { 
        return checkUser; 
    }        
   
    public void setCheckUser(User checkUser) {
        this.checkUser = checkUser;
    }
    
    public Boolean getCheckFinished() {
        return checkFinished;
    }

    public void setCheckFinished(Boolean checkFinished) {
        this.checkFinished = checkFinished;
    }
    
    public Integer getNumberOfCheckedWords() {
        return numberOfCheckedWords;
    }

    public void setNumberOfCheckedWords(Integer numberOfCheckedWords) {
        this.numberOfCheckedWords = numberOfCheckedWords;
    }
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (checkID != null ? checkID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Check)) {
            return false;
        }
        Check other = (Check) object;
        if ((this.checkID == null && other.checkID != null) || (this.checkID != null && !this.checkID.equals(other.checkID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SpellRefiner.Check[ checkID=" + checkID + " ]";
    }
    
}
