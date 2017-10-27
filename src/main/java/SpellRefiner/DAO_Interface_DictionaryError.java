package SpellRefiner;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface DAO_Interface_DictionaryError{
    public Integer SaveNew(DictionaryError entity);
    public void Update(DictionaryError entity);
    public void Delete(DictionaryError entity);    
    public List<DictionaryError> findAll();
    public List<DictionaryError> findByDictErrorID(Long dictErrorID);    
    public List<DictionaryError> findByWord(Word word);        
    public List<DictionaryError> findNotProcessedErrorsByWord(Word word);    
    public List<DictionaryError> findErrorsByWord(Word word);    
    //public void UpdateListWord_WordDictionaryErrors(List<Word> List_Word);
    public List<Integer> GetList_WordID_From_DictionaryError(Integer dict_id);
}

