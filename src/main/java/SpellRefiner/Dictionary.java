package SpellRefiner;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.persistence.Convert;
import javax.persistence.Transient;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.FetchType;


@Entity
@Table(name = "dictionaries")
@NamedQueries({
    @NamedQuery(name = "Dictionary.findAll", query = "SELECT d FROM Dictionary d"),
    @NamedQuery(name = "Dictionary.findByDictID", query = "SELECT d FROM Dictionary d WHERE d.dictID = :dictID"),
    @NamedQuery(name = "Dictionary.findByDictNameAndUser", query = "SELECT d FROM Dictionary d WHERE d.dictName = :dictName and d.dictUser = :user"),
    //@NamedQuery(name = "Dictionary.findByUserID", query = "SELECT d FROM Dictionary d WHERE d.userID = :userID"),
   // @NamedQuery(name = "Dictionary.findByDictDateTime", query = "SELECT d FROM Dictionary d WHERE d.dictDateTime = :dictDateTime"),
    //@NamedQuery(name = "Dictionary.findByDictName", query = "SELECT d FROM Dictionary d WHERE d.dictName = :dictName"),
    //@NamedQuery(name = "Dictionary.findByDictPropeties", query = "SELECT d FROM Dictionary d WHERE d.dictPropeties = :dictPropeties")
    @NamedQuery(name = "Dictionary.findByUser", query = "SELECT d FROM Dictionary d WHERE d.dictUser = :user"),
    @NamedQuery(name = "Dictionary.Shared", query = "SELECT d FROM Dictionary d  WHERE  d.dictDeleted = false and d.dictShared = TRUE ORDER BY d.dictUser.userLogin, d.dictDateTime DESC, d.dictName"),
    @NamedQuery(name = "Dictionary.MyDictionaries", query = "SELECT d FROM Dictionary d WHERE d.dictDeleted = :deleted and d.dictUser = :user ORDER BY d.dictDateTime DESC, d.dictName"),
    //@NamedQuery(name = "Dictionary.Allowed", query = "SELECT DISTINCT d, dp FROM Dictionary d INNER JOIN DictionaryPermission dp ON d.dictID = dp.dictID WHERE  d.dictDeleted = false and dp.dictPermUser = :user"),
	@NamedQuery(name = "Dictionary.Allowed", query = "SELECT DISTINCT d, dp, d.dictUser.userLogin FROM Dictionary d, DictionaryPermission dp WHERE d.dictID = dp.dictID AND d.dictDeleted = false and dp.dictPermUser = :user ORDER BY d.dictUser.userLogin, d.dictDateTime DESC, d.dictName"),
    @NamedQuery(name = "Dictionary.Accessible", query = "SELECT d FROM Dictionary d   WHERE  d.dictDeleted = false AND (d.dictShared = true OR d.dictUser = :user OR d.dictID in (SELECT dp.dictID FROM DictionaryPermission dp WHERE dp.dictPermUser = :user)) ORDER BY d.dictUser.userLogin, d.dictDateTime DESC, d.dictName"),
    @NamedQuery(name = "Dictionary.ReadAccess", query = "SELECT d FROM Dictionary d   WHERE d.dictID = :dictID AND (d.dictShared = true OR d.userID = :userID OR d.dictID in (SELECT dp.dictID FROM DictionaryPermission dp WHERE dp.userID = :userID))"),
    @NamedQuery(name = "Dictionary.WriteAccess", query = "SELECT d FROM Dictionary d  WHERE d.dictID = :dictID AND (d.userID = :userID OR d.dictID in (SELECT dp.dictID FROM DictionaryPermission dp WHERE dp.userID = :userID AND dp.dictPermWriteAccess = TRUE))"),
    @NamedQuery(name = "Dictionary.NumberOfWords", query = "SELECT COUNT(w) FROM Word w WHERE w.wordDict = :dict AND w.wordDeleted = FALSE")
})
public class Dictionary implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Dict_ID")
    private Integer dictID;
    @Column(name = "User_ID")
    private Integer userID;
    @Column(name = "Dict_DateTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dictDateTime;
    @Size(max = 100)
    @Column(name = "Dict_Name")
    private String dictName;
    //@Size(max = 1000)
    //@Column(name = "Dict_Propeties")
    //private String dictPropeties;        
    
    
    @Column(name = "Dict_Shared")
    @Convert(converter = BooleanToIntegerConverter.class)
    //private Integer dictShared;        
    private Boolean dictShared = false;        
    
    @Column(name = "Dict_Deleted")
    @Convert(converter = BooleanToIntegerConverter.class)
    private Boolean dictDeleted = false;
    
    @Size(max = 300)
    @Column(name = "Dict_Description")
    private String dictDescription;
    
    
    
    @Transient
    private Boolean dictWriteAccessCurrentUser;        

    
    //@Transient
    //private User dictCurrentUser;     
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="User_ID", nullable=false, insertable=false, updatable=false)
    private User dictUser;
    
    @Transient
    private List<DictionaryPermission> dictPermissions;
    
    
    @Transient
    Integer numberOfWords;
    
    @Transient
    Integer numberOfWordsWithSound;
    
    
    public Dictionary() {
    	
        dictPermissions = new ArrayList<DictionaryPermission>();
    }

    public Dictionary(Integer dictID) {
    	
        this.dictID = dictID;
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

    public Date getDictDateTime() {
        return dictDateTime;
    }

    public void setDictDateTime(Date dictDateTime) {
        this.dictDateTime = dictDateTime;
    }

    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    public String getDictDescription() {
        return dictDescription;
    }

    public void setDictDescription(String dictDescription) {
        this.dictDescription = dictDescription;
    }
    
    /*
    public Integer getDictSharedInteger() {
        return dictShared;
    }

    public void setDictSharedInteger(Integer dictShared) {
        this.dictShared = dictShared;
    }
    */

    
    /*
    public Boolean isDictShared() {
        return dictShared;
    }
    */
    
    
    public User getDictUser() { 
        return dictUser; 
    }    

    /*
    public void setDictUser(User user) {
        this.dictUser = user;
    }
    */
    
    
    
    
    public Boolean getDictShared() {
        //return (dictShared !=0);
        return dictShared;
    }

    public void setDictShared(Boolean dictShared) {
        //this.dictShared = (dictShared ? 1 : 0);
        this.dictShared = dictShared;
    }
    
    
    
    
    public Boolean getDictWriteAccessCurrentUser() {
        return dictWriteAccessCurrentUser;
    }

    public void setDictWriteAccessCurrentUser(Boolean writeAccess) {
        this.dictWriteAccessCurrentUser = writeAccess;
    }
    
    /*
    public User getDictCurrentUser() {
        return dictCurrentUser;
    }

    public void setDictCurrentUser(User dictCurrentUser) {
        this.dictCurrentUser = dictCurrentUser;
    }
    */
    
    public Boolean getDictDeleted() {
        return dictDeleted;
    }

    public void setDictDeleted(Boolean dictDeleted) {
        this.dictDeleted = dictDeleted;
    }
    
    
    public List<DictionaryPermission> getDictPermissions() {
        return dictPermissions;
    }
    
   public void setDictPermissions(List<DictionaryPermission> list) {
        this.dictPermissions = list;
    }    
    
   
    public Integer getNumberOfWords() {
        return numberOfWords;
    }

    public void setNumberOfWords(Integer numberOfWords) {
        this.numberOfWords = numberOfWords;
    }
    
    
    public Integer getNumberOfWordsWithSound() {
        return numberOfWordsWithSound;
    }

    public void setNumberOfWordsWithSound(Integer numberOfWordsWithSound) {
        this.numberOfWordsWithSound = numberOfWordsWithSound;
    }
            
   

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dictID != null ? dictID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Dictionary)) {
            return false;
        }
        Dictionary other = (Dictionary) object;
        if ((this.dictID == null && other.dictID != null) || (this.dictID != null && !this.dictID.equals(other.dictID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SpellRefiner.Dictionary[ dictID=" + dictID + " dictName=" + dictName + " ]";
    }
    
}
