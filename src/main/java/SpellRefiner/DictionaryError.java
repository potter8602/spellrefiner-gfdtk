package SpellRefiner;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;



@Entity
@Table(name = "dictionaryerrors")
@NamedQueries({
    @NamedQuery(name = "DictionaryError.findAll", query = "SELECT d FROM DictionaryError d"),
    @NamedQuery(name = "DictionaryError.findByDictErrorID", query = "SELECT d FROM DictionaryError d WHERE d.dictErrorID = :dictErrorID"),
    @NamedQuery(name = "DictionaryError.findByWord", query = "SELECT d FROM DictionaryError d WHERE d.dictErrorWord = :word"),
    @NamedQuery(name = "DictionaryError.findNotProcessedErrorsByWord", query = "SELECT d FROM DictionaryError d WHERE d.dictErrorWord = :word and d.dictErrorProcessed = false"),
    @NamedQuery(name = "DictionaryError.findErrorsByWord", query = "SELECT d FROM DictionaryError d WHERE d.dictErrorWord = :word order by d.dictErrorID desc")
    //@NamedQuery(name = "DictionaryError.findByDictID", query = "SELECT d FROM DictionaryError d WHERE d.dictID = :dictID"),
    //@NamedQuery(name = "DictionaryError.findByUserID", query = "SELECT d FROM DictionaryError d WHERE d.userID = :userID"),
    //@NamedQuery(name = "DictionaryError.findByWordID", query = "SELECT d FROM DictionaryError d WHERE d.wordID = :wordID")
    //@NamedQuery(name = "DictionaryError.findByDictErrorDateTime", query = "SELECT d FROM DictionaryError d WHERE d.dictErrorDateTime = :dictErrorDateTime"),
    //@NamedQuery(name = "DictionaryError.findByDictErrorSpelling", query = "SELECT d FROM DictionaryError d WHERE d.dictErrorSpelling = :dictErrorSpelling"),
    //@NamedQuery(name = "DictionaryError.findByDictErrorTranslation", query = "SELECT d FROM DictionaryError d WHERE d.dictErrorTranslation = :dictErrorTranslation"),
    //@NamedQuery(name = "DictionaryError.findByDictErrorPronunciation", query = "SELECT d FROM DictionaryError d WHERE d.dictErrorPronunciation = :dictErrorPronunciation"),
    //@NamedQuery(name = "DictionaryError.findByDictErrorDescription", query = "SELECT d FROM DictionaryError d WHERE d.dictErrorDescription = :dictErrorDescription"),
    //@NamedQuery(name = "DictionaryError.findByDictErrorOther", query = "SELECT d FROM DictionaryError d WHERE d.dictErrorOther = :dictErrorOther"),
    //@NamedQuery(name = "DictionaryError.findByDictErrorComment", query = "SELECT d FROM DictionaryError d WHERE d.dictErrorComment = :dictErrorComment"),
    //@NamedQuery(name = "DictionaryError.findByDictErrorProcessed", query = "SELECT d FROM DictionaryError d WHERE d.dictErrorProcessed = :dictErrorProcessed")
})
public class DictionaryError implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "DictError_ID")
    private Integer dictErrorID;
    @Column(name = "Dict_ID")
    private Integer dictID;
    @Column(name = "User_ID")
    private Integer userID;
    @Column(name = "Word_ID")
    private Integer wordID;
    @Column(name = "DictError_DateTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dictErrorDateTime;
    
    
    /*
    @Column(name = "DictError_Spelling")
    private Integer dictErrorSpelling;
    @Column(name = "DictError_Translation")
    private Integer dictErrorTranslation;
    @Column(name = "DictError_Pronunciation")
    private Integer dictErrorPronunciation;
    @Column(name = "DictError_Description")
    private Integer dictErrorDescription;
    @Column(name = "DictError_Other")
    private Integer dictErrorOther;
    */
    
    @Column(name = "DictError_Spelling")
    @Convert(converter = BooleanToIntegerConverter.class)
    private Boolean dictErrorSpelling = false;
    @Column(name = "DictError_Translation")
    @Convert(converter = BooleanToIntegerConverter.class)
    private Boolean dictErrorTranslation = false;
    @Column(name = "DictError_Pronunciation")
    @Convert(converter = BooleanToIntegerConverter.class)
    private Boolean dictErrorPronunciation = false;
    @Column(name = "DictError_Description")
    @Convert(converter = BooleanToIntegerConverter.class)
    private Boolean dictErrorDescription = false;
    @Column(name = "DictError_Other")
    @Convert(converter = BooleanToIntegerConverter.class)
    private Boolean dictErrorOther = false;
    
    
    @Size(max = 100)
    @Column(name = "DictError_Comment")
    private String dictErrorComment;
    
    
    @Column(name = "DictError_Processed")
    @Convert(converter = BooleanToIntegerConverter.class)
    private Boolean dictErrorProcessed = false;
    
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="Dict_ID", nullable=false, insertable=false, updatable=false)
    private Dictionary dictErrorDict;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="User_ID", nullable=false, insertable=false, updatable=false)
    private User dictErrorUser;
    

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="Word_ID", nullable=false, insertable=false, updatable=false)
    private Word dictErrorWord;
    
    
    
    
    

    public DictionaryError() {
    }

    public DictionaryError(Integer dictErrorID) {
        this.dictErrorID = dictErrorID;
    }

    public Integer getDictErrorID() {
        return dictErrorID;
    }

    public void setDictErrorID(Integer dictErrorID) {
        this.dictErrorID = dictErrorID;
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

    public Integer getWordID() {
        return wordID;
    }

    public void setWordID(Integer wordID) {
        this.wordID = wordID;
    }

    public Date getDictErrorDateTime() {
        return dictErrorDateTime;
    }

    public void setDictErrorDateTime(Date dictErrorDateTime) {
        this.dictErrorDateTime = dictErrorDateTime;
    }

    /*
    public Integer getDictErrorSpelling() {
        return dictErrorSpelling;
    }

    public void setDictErrorSpelling(Integer dictErrorSpelling) {
        this.dictErrorSpelling = dictErrorSpelling;
    }

    public Integer getDictErrorTranslation() {
        return dictErrorTranslation;
    }

    public void setDictErrorTranslation(Integer dictErrorTranslation) {
        this.dictErrorTranslation = dictErrorTranslation;
    }

    public Integer getDictErrorPronunciation() {
        return dictErrorPronunciation;
    }

    public void setDictErrorPronunciation(Integer dictErrorPronunciation) {
        this.dictErrorPronunciation = dictErrorPronunciation;
    }

    public Integer getDictErrorDescription() {
        return dictErrorDescription;
    }

    public void setDictErrorDescription(Integer dictErrorDescription) {
        this.dictErrorDescription = dictErrorDescription;
    }

    public Integer getDictErrorOther() {
        return dictErrorOther;
    }

    public void setDictErrorOther(Integer dictErrorOther) {
        this.dictErrorOther = dictErrorOther;
    }
    */
    
    public Boolean getDictErrorSpelling() {
        return dictErrorSpelling;
    }

    public void setDictErrorSpelling(Boolean dictErrorSpelling) {
        this.dictErrorSpelling = dictErrorSpelling;
    }

    public Boolean getDictErrorTranslation() {
        return dictErrorTranslation;
    }

    public void setDictErrorTranslation(Boolean dictErrorTranslation) {
        this.dictErrorTranslation = dictErrorTranslation;
    }

    public Boolean getDictErrorPronunciation() {
        return dictErrorPronunciation;
    }

    public void setDictErrorPronunciation(Boolean dictErrorPronunciation) {
        this.dictErrorPronunciation = dictErrorPronunciation;
    }

    public Boolean getDictErrorDescription() {
        return dictErrorDescription;
    }

    public void setDictErrorDescription(Boolean dictErrorDescription) {
        this.dictErrorDescription = dictErrorDescription;
    }

    public Boolean getDictErrorOther() {
        return dictErrorOther;
    }

    public void setDictErrorOther(Boolean dictErrorOther) {
        this.dictErrorOther = dictErrorOther;
    }
    

    public String getDictErrorComment() {
        return dictErrorComment;
    }

    public void setDictErrorComment(String dictErrorComment) {
        this.dictErrorComment = dictErrorComment;
    }

    public Boolean getDictErrorProcessed() {
        return dictErrorProcessed;
    }

    public void setDictErrorProcessed(Boolean dictErrorProcessed) {
        this.dictErrorProcessed = dictErrorProcessed;
    }
    
    public Dictionary getDictErrorDict() { 
        return dictErrorDict; 
    }        
   
    public void setDictErrorDict(Dictionary dictErrorDict) {
        this.dictErrorDict = dictErrorDict;
    }

    public User getDictErrorUser() { 
        return dictErrorUser; 
    }        
   
    public void setDictErrorUser(User dictErrorUser) {
        this.dictErrorUser = dictErrorUser;
    }
    
    public Word getDictErrorWord() { 
        return dictErrorWord; 
    }        
   
    public void setDictErrorWord(Word dictErrorWord) {
        this.dictErrorWord = dictErrorWord;
    }

    
    public String getName() {        
  
        String str = "";
        if (dictErrorSpelling){
            str = str + "написание, ";
        };
        
        if (dictErrorTranslation){
            str = str + "перевод, ";
        };
        
        if (dictErrorPronunciation){
            str = str + "произношение, ";
        };
        
        if (dictErrorDescription){
            str = str + "описание, ";
        };
        if (dictErrorOther){
            str = str + "другое, ";
        };  
        
        if (!dictErrorComment.isEmpty()){
            str = str + dictErrorComment + ", ";
        };     
        
        if (str.length() >=2 ){
        	str = str.substring(0, str.length() - 2);
        };	
        
        return str;
    }
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dictErrorID != null ? dictErrorID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DictionaryError)) {
            return false;
        }
        DictionaryError other = (DictionaryError) object;
        if ((this.dictErrorID == null && other.dictErrorID != null) || (this.dictErrorID != null && !this.dictErrorID.equals(other.dictErrorID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SpellRefiner.DictionaryError[ dictErrorID=" + dictErrorID + " ]";
    }
    
}
