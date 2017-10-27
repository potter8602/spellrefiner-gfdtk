package SpellRefiner;

import javax.ejb.Remote;
import java.util.List;
import java.util.Map;

@Remote
public interface DAO_Interface_Check{
    public Integer SaveNew(Check entity);
    public void Update(Check entity);
    public void Delete(Check entity);    
    public List<Check> findAll();
    public List<Check> findByCheckID(Integer checkID);    
    public List<Check> MyChecks(User user, Boolean deleted, Boolean only_unfinished);    
    public Map<String, Object> GetCheckInfo(Check check);
    public Check Get(Integer id);
    //public Check Add(Check entity); //test
    public Integer NumberOfMyChecks(User user);
}

