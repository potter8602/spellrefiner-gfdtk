/*
 * Here comes the text of your license
 * Each line should be prefixed with  * 
 */
package SpellRefiner;

import java.io.Serializable;
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
import javax.validation.constraints.Size;

@Entity
@Table(name = "inputs")
@NamedQueries({
    @NamedQuery(name = "Input.findAll", query = "SELECT i FROM Input i"),
    @NamedQuery(name = "Input.findByInputID", query = "SELECT i FROM Input i WHERE i.inputID = :inputID"),
    //@NamedQuery(name = "Input.getNextInputWordFromDictionary", query = "SELECT w FROM Word w WHERE w.wordDict = :dict"),
    //@NamedQuery(name = "Input.getNextInputWordFromDictionary", query = "SELECT w FROM Word w WHERE w.wordDict = :dict  AND w.wordDeleted = FALSE AND NOT w.wordID IN (SELECT i.wordID FROM Input i WHERE i.inputCheck = :check AND i.passNumber = 1) ORDER BY w.wordRandomNumber"),
    //@NamedQuery(name = "Input.getNextInputWordFromLastInput", query = "SELECT i.inputWord FROM Input i WHERE i.inputCheck = :check AND i.passNumber = :previousPassNumber  AND (i.error = TRUE OR i.uncertain = TRUE) AND i.skipped = FALSE AND NOT i.wordID IN (SELECT i.wordID FROM Input i WHERE i.inputCheck = :check AND i.passNumber = :passNumber)  ORDER BY i.inputID"),
    @NamedQuery(name = "Input.getInputWordsFromDictionary", query = "SELECT w FROM Word w WHERE w.wordDict = :dict AND w.wordDeleted = FALSE ORDER BY w.wordRandomNumber"),
    @NamedQuery(name = "Input.getInputWordsByPassNumber", query = "SELECT i.inputWord FROM Input i WHERE i.inputCheck = :check AND i.passNumber = :passNumber ORDER BY i.inputID"),
    @NamedQuery(name = "Input.NumberOfInputWordsByPassNumber", query = "SELECT COUNT(i) FROM Input i WHERE i.inputCheck = :check AND i.passNumber = :passNumber ORDER BY i.inputID"),
    @NamedQuery(name = "Input.NumberOfInputWordsFromLastInput", query = "SELECT COUNT(i) FROM Input i WHERE i.inputCheck = :check AND i.passNumber = :previousPassNumber  AND (i.error = TRUE OR i.uncertain = TRUE) AND i.skipped = FALSE ORDER BY i.inputID"),
    @NamedQuery(name = "Input.NumberOfInputWordsFromDictionary", query = "SELECT COUNT(w) FROM Word w WHERE w.wordDict = :dict AND w.wordDeleted = FALSE ORDER BY w.wordRandomNumber"),
    @NamedQuery(name = "Input.NumberOfPassedWordsByPassNumber", query = "SELECT COUNT(i) FROM Input i WHERE i.inputCheck = :check AND i.passNumber = :passNumber AND ((i.error = FALSE AND i.uncertain = FALSE) OR i.skipped = TRUE)")
})
public class Input implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Input_ID")
    private Integer inputID;
    @Column(name = "Check_ID")
    private Integer checkID;
    @Column(name = "Dict_ID")
    private Integer dictID;
    @Column(name = "User_ID")
    private Integer userID;
    @Column(name = "Word_ID")
    private Integer wordID;
    @Column(name = "Pass_Number")
    private Integer passNumber;
    @Size(max = 100)
    @Column(name = "Word_Spelling")
    private String wordSpelling;
    @Size(max = 100)
    @Column(name = "Word_Input")
    private String wordInput;
    @Column(name = "Uncertain")
    @Convert(converter = BooleanToIntegerConverter.class)
    private Boolean uncertain = false;
    @Column(name = "Error")
    @Convert(converter = BooleanToIntegerConverter.class)
    private Boolean error = false;
    @Column(name = "Skipped")
    @Convert(converter = BooleanToIntegerConverter.class)
    private Boolean skipped = false;
    
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="Dict_ID", nullable=false, insertable=false, updatable=false)
    private Dictionary inputDict;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="User_ID", nullable=false, insertable=false, updatable=false)
    private User inputUser;    

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="Word_ID", nullable=false, insertable=false, updatable=false)
    private Word inputWord;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="Check_ID", nullable=false, insertable=false, updatable=false)
    private Check inputCheck;
    
    public Dictionary getInputDict() { 
        return inputDict; 
    }        
   
    public void setInputDict(Dictionary inputDict) {
        this.inputDict = inputDict;
    }

    public User getInputUser() { 
        return inputUser; 
    }        
   
    public void setInputUser(User inputUser) {
        this.inputUser = inputUser;
    }

    public Word getInputWord() { 
        return inputWord; 
    }        
   
    public void setInputWord(Word inputWord) {
        this.inputWord = inputWord;
    }
    
    public Check getInputCheck() { 
        return inputCheck; 
    }        
   
    public void setInputCheck(Check inputWord) {
        this.inputCheck = inputCheck;
    }
    
    

    public Input() {
    }

    public Input(Integer inputID) {
        this.inputID = inputID;
    }

    public Integer getInputID() {
        return inputID;
    }

    public void setInputID(Integer inputID) {
        this.inputID = inputID;
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

    public Integer getWordID() {
        return wordID;
    }

    public void setWordID(Integer wordID) {
        this.wordID = wordID;
    }
    
    public Integer getPassNumber() {
        return passNumber;
    }

    public void setPassNumber(Integer passNumber) {
        this.passNumber = passNumber;
    }

    public String getWordSpelling() {
        return wordSpelling;
    }

    public void setWordSpelling(String wordSpelling) {
        this.wordSpelling = wordSpelling;
    }

    public String getWordInput() {
        return wordInput;
    }

    public void setWordInput(String wordInput) {
        this.wordInput = wordInput;
    }

    public Boolean getUncertain() {
        return uncertain;
    }

    public void setUncertain(Boolean uncertain) {
        this.uncertain = uncertain;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public Boolean getSkipped() {
        return skipped;
    }

    public void setSkipped(Boolean skipped) {
        this.skipped = skipped;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (inputID != null ? inputID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Input)) {
            return false;
        }
        Input other = (Input) object;
        if ((this.inputID == null && other.inputID != null) || (this.inputID != null && !this.inputID.equals(other.inputID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SpellRefiner.Input[ inputID=" + inputID + " ]";
    }
    
}
