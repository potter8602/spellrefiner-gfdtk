package SpellRefiner;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface DAO_Interface_Word{
    public Integer SaveNew(Word entity);
    public void Update(Word entity);
    public void Delete(Word entity);    
    public List<Word> findAll();
    public List<Word> findByWordID(Integer wordID);    
    public List<Word> DictionaryWords(Dictionary dict);
    
    public Word GetWordByID(Integer wordID);    
    
    public byte[] GetWordSoundByWordID(Integer wordID);    
    
    public void SetWordSoundByWordID(Integer wordID, byte[] wordSound, Boolean wordHasSound);    
    public List<Word> DictionaryWordsWithParams(Dictionary dict, String wordsOrder, boolean ascendingOrder, boolean DeletedOnly, boolean WithSound, boolean WithoutSound, boolean WithErrorsOnly, boolean WithNotProcessedErrorsOnly, String WordSpellingFilter);    
    public Integer DictionaryWordsWithParams_Count(Dictionary dict, String wordsOrder, boolean ascendingOrder, boolean DeletedOnly, boolean WithSound, boolean WithoutSound, boolean WithErrorsOnly, boolean WithNotProcessedErrorsOnly);    
    public List<Word> DictionaryWordsWithParamsInInterval(Dictionary dict, String wordsOrder, boolean ascendingOrder, boolean DeletedOnly, boolean WithSound, boolean WithoutSound, boolean WithErrorsOnly, boolean WithNotProcessedErrorsOnly, int FirstRecord, int LastRecord );    
  
    public List<Word> findByDictionaryAndSpelling(Dictionary dict, String wordSpelling);
    
    
    public Word GetLastWordWithoutSound(Dictionary dict);
    public List<Word> findWordsByDictionaryByEditionDateTime(Dictionary dict);
    public Word GetLastWordWithoutSoundWithParams(Dictionary dict, String wordsOrder, boolean ascendingOrder);
    
    public boolean CheckPermissionForWord(Integer word_id, Integer user_id);
    
    //public List<Word> Test1();    
    //public List<Word> Test2();    
    //public List<Word> Test3(Dictionary dict);    
    
    
}

