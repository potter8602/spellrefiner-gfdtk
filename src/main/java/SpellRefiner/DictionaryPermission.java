package SpellRefiner;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.persistence.FetchType;

@Entity
@Table(name = "dictionarypermissions")

@NamedQueries({
    @NamedQuery(name = "DictionaryPermission.findAll", query = "SELECT d FROM DictionaryPermission d"),
    @NamedQuery(name = "DictionaryPermission.findByDictPermID", query = "SELECT d FROM DictionaryPermission d WHERE d.dictPermID = :dictPermID"),
    @NamedQuery(name = "DictionaryPermission.findByDict", query = "SELECT d FROM DictionaryPermission d WHERE d.dictPermDict = :dictPermDict"),
    @NamedQuery(name = "DictionaryPermission.findByDictAndUser", query = "SELECT d FROM DictionaryPermission d WHERE d.dictPermDict = :dictPermDict and d.dictPermUser = :dictPermUser")
    //@NamedQuery(name = "DictionaryPermission.findByDictID", query = "SELECT d FROM DictionaryPermission d WHERE d.dictID = :dictID"),
    //@NamedQuery(name = "DictionaryPermission.findByUserLogin", query = "SELECT d FROM DictionaryPermission d WHERE d.userLogin = :userLogin"),
    //@NamedQuery(name = "DictionaryPermission.findByUserID", query = "SELECT d FROM DictionaryPermission d WHERE d.userID = :userID"),
    //@NamedQuery(name = "DictionaryPermission.findByUser", query = "SELECT d FROM DictionaryPermission d WHERE d.dictPermID = :user")
    
})
public class DictionaryPermission implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "DictPerm_ID")
    private Integer dictPermID;
    @Column(name = "Dict_ID")
    private Integer dictID;
    
    @Column(name = "User_ID")
    private Integer userID;
    
    
    @Column(name = "DictPerm_WriteAccess")
    @Convert(converter = BooleanToIntegerConverter.class)
    private Boolean dictPermWriteAccess = false;
    
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="User_ID", nullable=false, insertable=false, updatable=false)
    private User dictPermUser;    
    

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="Dict_ID", nullable=false, insertable=false, updatable=false)
    private Dictionary dictPermDict;    
    
    public DictionaryPermission() {
    }

    public DictionaryPermission(Integer dictPermID) {
        this.dictPermID = dictPermID;
    }

    public Integer getDictPermID() {
        return dictPermID;
    }

    public void setDictPermID(Integer dictPermID) {
        this.dictPermID = dictPermID;
    }

    public Integer getDictID() {
        return dictID;
    }

    public void setDictID(Integer dictID) {
        this.dictID = dictID;
    }

    
    public Boolean getDictPermWriteAccess() {
        return dictPermWriteAccess;
    }

    public void setDictPermWriteAccess(Boolean dictPermWriteAccess) {
        this.dictPermWriteAccess = dictPermWriteAccess;
    }
    
    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }
    
    public User getDictPermUser() { 
        return dictPermUser; 
    }    

    public Dictionary getDictPermDict() { 
        return dictPermDict; 
    }    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dictPermID != null ? dictPermID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DictionaryPermission)) {
            return false;
        }
        DictionaryPermission other = (DictionaryPermission) object;
        if ((this.dictPermID == null && other.dictPermID != null) || (this.dictPermID != null && !this.dictPermID.equals(other.dictPermID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SpellRefiner.DictionaryPermission[ dictPermID=" + dictPermID + " ]";
    }
    
}
