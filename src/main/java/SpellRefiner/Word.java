package SpellRefiner;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.servlet.http.Part;
import javax.validation.constraints.Size;
import java.util.Random;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;


@Entity
@Table(name = "words")
@NamedQueries({
    @NamedQuery(name = "Word.findAll", query = "SELECT w FROM Word w"),
    @NamedQuery(name = "Word.findByWordID", query = "SELECT w FROM Word w WHERE w.wordID = :wordID"),
    //@NamedQuery(name = "Word.findByDictID", query = "SELECT w FROM Word w WHERE w.dictID = :dictID"),
    //@NamedQuery(name = "Word.findByWordSpelling", query = "SELECT w FROM Word w WHERE w.wordSpelling = :wordSpelling"),
    //@NamedQuery(name = "Word.findByWordTranslation", query = "SELECT w FROM Word w WHERE w.wordTranslation = :wordTranslation"),
    //@NamedQuery(name = "Word.findByWordDescription", query = "SELECT w FROM Word w WHERE w.wordDescription = :wordDescription"),
    //@NamedQuery(name = "Word.findByWordRandomNumber", query = "SELECT w FROM Word w WHERE w.wordRandomNumber = :wordRandomNumber")
    @NamedQuery(name = "Word.findWordsByDictionaryByEditionDateTime", query = "SELECT w FROM Word w WHERE w.wordDict = :dict ORDER BY w.wordEditionDateTime DESC"),
    //@NamedQuery(name = "Word.findWordsByDictionaryByEditionDateTime", query = "SELECT w FROM Word w"),
    @NamedQuery(name = "Word.findWordsWithoutSound", query = "SELECT w FROM Word w WHERE w.wordDict = :dict and w.wordHasSound = false and w.wordDeleted = false ORDER BY w.wordPostponed, w.wordID"),
    @NamedQuery(name = "Word.findByDictionaryAndSpelling", query = "SELECT w FROM Word w WHERE w.wordDict = :dict and w.wordSpelling = :wordSpelling"),
    @NamedQuery(name = "Word.DictionaryWords", query = "SELECT w FROM Word w WHERE w.wordDict = :dict"),
    @NamedQuery(name = "Word.GetWordSoundByWordID", query = "SELECT w.wordSound FROM Word w WHERE  w.wordID = :wordID")
    //@NamedQuery(name = "Word.UpdateWordSoundByWordID", query = "UPDATE Word w SET w.wordSound = :wordSound, w.wordHasSound=:wordHasSound, w.wordEditionDateTime=:wordEditionDateTime WHERE w.wordID = :wordID")
    
})
public class Word implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Word_ID")
    private Integer wordID;
    @Column(name = "Dict_ID")
    private Integer dictID;
    @Size(max = 100)
    @Column(name = "Word_Spelling")
    private String wordSpelling;
    @Size(max = 100)
    @Column(name = "Word_Translation")
    private String wordTranslation;
    @Size(max = 100)
    @Column(name = "Word_Description")
    private String wordDescription;
    @Column(name = "Word_Random_Number")
    private Integer wordRandomNumber;
    
    @Column(name = "Word_Deleted")
    @Convert(converter = BooleanToIntegerConverter.class)
    private Boolean wordDeleted = false;
    
    @Column(name = "Word_HasSound")
    @Convert(converter = BooleanToIntegerConverter.class)
    private Boolean wordHasSound = false;
    
    @Lob
    @Basic(fetch=FetchType.LAZY)    
    @Column(name="Word_Sound")        
    private byte[] wordSound;    
    
    
    @Column(name = "Word_Count")
    private Integer wordCount = 0;
    
    @Column(name = "Word_TotalCount")
    private Integer wordTotalCount = 0;
    
    @Column(name = "Word_Edition_DateTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date wordEditionDateTime;
    
    
    @Column(name = "Word_Postponed")
    @Convert(converter = BooleanToIntegerConverter.class)
    private Boolean wordPostponed = false;
    
    
    
//    //@Lob(type = LobType.BLOB)
//    @Basic(fetch=FetchType.LAZY)    
//    @Lob    
//    //@Column(name="Word_Sound", columnDefinition="BLOB", length=1000)        
//    @Column(name="Word_Sound", columnDefinition="BINARY(100000)")        
//    private byte[] wordSound;    
    
    @Transient
    transient private Part fileSound;
    
    //@Transient
    //transient private Boolean dictError;
    

    //@Transient
    //transient private Boolean wordHasErrors;
    
    
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="Dict_ID", nullable=false, insertable=false, updatable=false)
    private Dictionary wordDict;
    
    @Transient
    private List<DictionaryError> wordDictionaryErrors;

    @Transient
    private Boolean wordHasNotProcessedErrors;

    @Transient
    private Boolean wordHasErrors;
    
    public Word() {
    	
         wordDictionaryErrors = new ArrayList<DictionaryError>();
    }

    public Word(Integer wordID) {
        this.wordID = wordID;
    }

    public Integer getWordID() {
        return wordID;
    }

    public void setWordID(Integer wordID) {
        this.wordID = wordID;
    }

    public Integer getDictID() {
        return dictID;
    }

    public void setDictID(Integer dictID) {
        this.dictID = dictID;
    }

    public String getWordSpelling() {
        return wordSpelling;
    }

    public void setWordSpelling(String wordSpelling) {
        //System.out.println("--------------------setWordSpelling " + wordSpelling);
        this.wordSpelling = wordSpelling;
    }

    public String getWordTranslation() {
        return wordTranslation;
    }

    public void setWordTranslation(String wordTranslation) {
        this.wordTranslation = wordTranslation;
    }

    public String getWordDescription() {
        return wordDescription;
    }

    public void setWordDescription(String wordDescription) {
        this.wordDescription = wordDescription;
    }

    public Integer getWordRandomNumber() {
        return wordRandomNumber;
    }

    public void setWordRandomNumber(Integer wordRandomNumber) {
        this.wordRandomNumber = wordRandomNumber;
    }
    
    public Boolean getWordDeleted() {
        return wordDeleted;
    }

    public void setWordDeleted(Boolean wordDeleted) {
        this.wordDeleted = wordDeleted;
    }
    
    public Boolean getWordHasSound() {
        return wordHasSound;
    }

    public void setWordHasSound(Boolean wordHasSound) {
        this.wordHasSound = wordHasSound;
    }
    
    public byte[] getWordSound() {
        return wordSound;
    }

    public void setWordSound(byte[] wordSound) {
        this.wordSound = wordSound;
    }
    
    public Integer getWordCount() {
        return wordCount;
    }

    public void setWordCount(Integer wordCount) {
        this.wordCount = wordCount;
    }
    
    public Integer getWordTotalCount() {
        return wordTotalCount;
    }

    public void setWordTotalCount(Integer wordTotalCount) {
        this.wordTotalCount = wordTotalCount;
    }
    
    public Date getWordEditionDateTime() {
        return wordEditionDateTime;
    }

    public void setWordEditionDateTime(Date wordEditionDateTime) {
        this.wordEditionDateTime = wordEditionDateTime;
    }
            
    
    
    
    public Dictionary getWordDict() { 
        return wordDict; 
    }    
    
   
    public void setWordDict(Dictionary wordDict) {
        this.wordDict = wordDict;
    }
    
    public Part getFileSound() {
        return fileSound;
    }

    public void setFileSound(Part file) {
        //System.out.println("--------------------setFileSound " + file);
        this.fileSound = file;
    }
    
    
    public List<DictionaryError> getWordDictionaryErrors() {
        return wordDictionaryErrors;
    }
    
   public void setWordDictionaryErrors(List<DictionaryError> list) {
        this.wordDictionaryErrors = list;
        /*
        wordDictionaryErrors.clear();
        for (DictionaryError dictError: list) {
            wordDictionaryErrors.add(dictError);
        }    
        
        System.out.println("--------------------Word.setWordDictionaryErrors.wordID " + wordID);
        System.out.println("--------------------Word.setWordDictionaryErrors.wordDictionaryErrors.size() " + wordDictionaryErrors.size());
        */
        
    }    
    
 
    public Boolean getWordHasNotProcessedErrors() {
        return wordHasNotProcessedErrors;
    }
 
    public void setWordHasNotProcessedErrors(Boolean wordHasNotProcessedErrors) {
        this.wordHasNotProcessedErrors = wordHasNotProcessedErrors;
    }       
    
    public Boolean getWordHasErrors() {
        return wordHasErrors;
    }
 
    public void setWordHasErrors(Boolean wordHasErrors) {
        this.wordHasErrors = wordHasErrors;
    }       
    
    
    public Boolean getWordPostponed() {
        return wordPostponed;
    }

    public void setWordPostponed(Boolean wordPostponed) {
        this.wordPostponed = wordPostponed;
    }
    

    public int GenerateRandomNumber() {
        Random rn = new Random();
        
        int RandomNumber = (rn.nextInt(100000) + 1) * 100;             
        return RandomNumber;
    }
    
    
    public void GenerateAndSetWordRandomNumber() {
        setWordRandomNumber(GenerateRandomNumber());
    }
    
    /*
    public Boolean getWordHasErrors() {
        return wordHasErrors;
    }

    public void setWordHasErrors(Boolean wordHasErrors) {
        this.wordHasErrors = wordHasErrors;
    }
    */
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (wordID != null ? wordID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Word)) {
            return false;
        }
        Word other = (Word) object;
        if ((this.wordID == null && other.wordID != null) || (this.wordID != null && !this.wordID.equals(other.wordID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SpellRefiner.Word[ wordID=" + wordID + " wordSpelling=" + wordSpelling + " ]";
    }
    
}
