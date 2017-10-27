package SpellRefiner;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface DAO_Interface_DictionaryPermission{
    public Integer SaveNew(DictionaryPermission entity);
    public void Update(DictionaryPermission entity);
    public void Delete(DictionaryPermission entity);    
    public List<DictionaryPermission> findAll();
    public List<DictionaryPermission> findByDictPermID(Long dictPermID);    
    public List<DictionaryPermission> findByDict(Dictionary dict);    
    public List<DictionaryPermission> findByDictAndUser(Dictionary dict, User user);    
}

