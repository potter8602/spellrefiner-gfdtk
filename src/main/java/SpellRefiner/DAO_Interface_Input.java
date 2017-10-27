package SpellRefiner;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface DAO_Interface_Input{
    public Integer SaveNew(Input entity);
    public void Update(Input entity);
    public void Delete(Input entity);    
    public List<Input> findAll();
    public List<Input> findByInputID(Integer inputID);   
    public List<Word> getNextInputWordFromDictionary(Check check, String wordsOrder, boolean ascendingOrder, boolean WithSound, boolean WithoutSound, boolean ExceptWordsInFinishedChecks, boolean ExceptWordsInFinishedChecksSameDictionary);   
    public List<Word> getNextInputWordFromLastInput(Check check, Integer passNumber);   
    public List<Word> getInputWordsFromDictionary(Dictionary dict);       
    public List<Word> getInputWordsByPassNumber(Check check, Integer passNumber);   
    
    public Integer NumberOfInputWordsByPassNumber(Check check, Integer passNumber);   
    
    public Integer NumberOfInputWordsFromLastInput(Check check, Integer previousPassNumber);   
    public Integer NumberOfInputWordsFromDictionary(Dictionary dict);   
    public Integer NumberOfInputWordsFromDictionaryWithParams(Dictionary dict, boolean WithSound, boolean WithoutSound, boolean ExceptWordsInFinishedChecks, boolean ExceptWordsInFinishedChecksSameDictionary, User user);
    
    public List<Input> getPreviousInput(Check check, Integer passNumber, Integer inputID);
    public List<Input> getNextInput(Check check, Integer passNumber, Integer inputID);
    public Integer NumberOfPassedWordsByPassNumber(Check check, Integer passNumber);
    
}

