package SpellRefiner;

import javax.ejb.Remote;
import java.util.List;
import java.util.Map;

@Remote
public interface DAO_Interface_Dictionary{
    //public void SaveNew(Dictionary entity);
    public Integer SaveNew(Dictionary entity);
    public void Update(Dictionary entity);
    public void Delete(Dictionary entity);    
    public List<Dictionary> findAll();
    //public List<Dictionary> findByUserID(Integer userID);    
    public List<Dictionary> Shared();    
    public List<Object[]> Allowed_Old(String userLogin);    
    //public List<Dictionary> findByUser(User user);    
    public List<Dictionary> MyDictionaries(User user, Boolean onlyDeleted);    

    public List<Dictionary> findByDictID(Integer dictID);
    public List<Object[]> Allowed(User user);    
    
    public List<Dictionary> findByDictNameAndUser(String dictName, User user);
    
    //public void GetDictionariesInfo1(List<Dictionary> list);    
    public Map<String, Object> GetDictionaryInfo(Dictionary dict);
    
    public List<Dictionary> Accessible(User user);    
    
    public Boolean ReadAccess(Integer dictID, Integer userID);
    public Boolean WriteAccess(Integer dictID, Integer userID);
    public Integer NumberOfWords(Dictionary dict);
    
}

