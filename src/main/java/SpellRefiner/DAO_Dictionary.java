package SpellRefiner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ejb.Stateless;
import java.util.List;
import javax.persistence.Query;
import java.util.Map;
import java.util.LinkedHashMap;

@Stateless
public class DAO_Dictionary implements DAO_Interface_Dictionary{    
    @PersistenceContext(unitName = "SpellRefiner")
    private EntityManager em;                    
    
    /*
    @Override
    public void SaveNew(Dictionary entity){    
       em.persist(entity);        
    } 
    */
    
    @Override
    public Integer SaveNew(Dictionary entity){    
       em.persist(entity);    
       em.flush();
       return entity.getDictID();
    }            
    
    
    
    @Override
    public void Update(Dictionary entity){    
       em.merge(entity);        
    }            

    @Override
    public void Delete(Dictionary entity){    
       Dictionary entity1 = em.merge(entity);        
       em.remove(entity1);        
    }            
    
    /*
    @Override
    public Dictionary Get(Integer id){    
        Dictionary u = em.find(Dictionary.class, id);
        return u;
    } 
    */

    @Override
    public List<Dictionary> findAll(){    
        Query query = em.createNamedQuery("Dictionary.findAll");
        List<Dictionary> list = query.getResultList();
        
        return list;
    }   
    
    /*
    @Override
    public List<Dictionary> findByDictID(Long dictID){    
        Query query = em.createNamedQuery("Dictionary.findByDictID");
        query.setParameter("dictID", dictID);
        List<Dictionary> list = query.getResultList();
        
        return list;
    } 
    */
    
    
    /*
    @Override
    public List<Dictionary> findByUserID(Integer userID){    
        Query query = em.createNamedQuery("Dictionary.findByUserID");
        query.setParameter("userID", userID);
        List<Dictionary> list = query.getResultList();
        
        return list;
    }   
    
    */

    @Override
    public List<Dictionary> Shared(){    
        Query query = em.createNamedQuery("Dictionary.Shared");
        List<Dictionary> list = query.getResultList();
        
        return list;
    }   
 
    
    /*
    @Override
    public List<Dictionary> findAllowed(String userLogin){    
        Query query = em.createNamedQuery("Dictionary.findAllowed");
        query.setParameter("userLogin", userLogin);
        List<Dictionary> list = query.getResultList();
        
        return list;
    } 
    
    */
    
    @Override
    public List<Object[]> Allowed_Old(String userLogin){    
        Query query = em.createNamedQuery("Dictionary.Allowed_Old");
        query.setParameter("userLogin", userLogin);
        List<Object[]> list = query.getResultList();
        
        return list;
    }   
    
    /*
    @Override
    public List<Dictionary> findByUser(User user){    
        Query query = em.createNamedQuery("Dictionary.findByUser");
        query.setParameter("user", user);
        List<Dictionary> list = query.getResultList();
        
        return list;
    } 
    */
    
    @Override
    public List<Dictionary> MyDictionaries(User user, Boolean onlyDeleted){    
        Query query = em.createNamedQuery("Dictionary.MyDictionaries");
        query.setParameter("user", user);
        query.setParameter("deleted", onlyDeleted);
        List<Dictionary> list = query.getResultList();
        
        return list;
    }   
    
    @Override
    public List<Dictionary> findByDictID(Integer dictID){    
        Query query = em.createNamedQuery("Dictionary.findByDictID");
        query.setParameter("dictID", dictID);
        List<Dictionary> list = query.getResultList();
        
        return list;
    }   
    

    @Override
    public List<Object[]> Allowed(User user){    
        Query query = em.createNamedQuery("Dictionary.Allowed");
        query.setParameter("user", user);
        List<Object[]> list = query.getResultList();
        
        return list;
    }   
    
    @Override
    public List<Dictionary> findByDictNameAndUser(String dictName, User user){    
        Query query = em.createNamedQuery("Dictionary.findByDictNameAndUser");
        query.setParameter("user", user);        
        query.setParameter("dictName", dictName);
        List<Dictionary> list = query.getResultList();
        
        return list;
    }   
    
    /*
    @Override
    public void GetDictionariesInfo1(List<Dictionary> list){   
        
        String str_query = "SELECT IFNULL(SUM(1), 0) AS NumberOfWords, IFNULL(SUM(CASE WHEN Word_HasSound = 1 THEN 1 ELSE 0 END), 0) AS NumberOfWordsWithSound FROM Words WHERE dict_id = ?";  
        Query query = em.createNativeQuery(str_query);        
        
        for (Dictionary dict: list) {
            
            query.setParameter(1, dict.getDictID());
            List<Object[]> list1 = query.getResultList();
            if (!list1.isEmpty()){
                System.out.println("--------------------dict.getDictID() = " + dict.getDictID());
                System.out.println("--------------------list1.get(0)[0] = " + list1.get(0)[0]);
                System.out.println("--------------------list1.get(0)[1] = " + list1.get(0)[1]);
                
                dict.setNumberOfWords((Integer) ((Number)list1.get(0)[0]).intValue());
                dict.setNumberOfWordsWithSound((Integer) ((Number)list1.get(0)[1]).intValue());
                
            }
            
	}    
    } 
    */

    
    @Override
    public Map<String, Object> GetDictionaryInfo(Dictionary dict){   
        Map<String, Object> Info = new LinkedHashMap<String, Object>();
        
        //String str_query = "SELECT IFNULL(SUM(1), 0) AS NumberOfWords, IFNULL(SUM(CASE WHEN Word_HasSound = 1 THEN 1 ELSE 0 END), 0) AS NumberOfWordsWithSound FROM words WHERE dict_id = ?";
        String str_query = "SELECT IFNULL(SUM(1), 0) AS NumberOfWords, IFNULL(SUM(CASE WHEN Word_HasSound = 1 THEN 1 ELSE 0 END), 0) AS NumberOfWordsWithSound FROM words WHERE dict_id = ? AND Word_Deleted = 0";
        Query query = em.createNativeQuery(str_query);        
        query.setParameter(1, dict.getDictID());
        List<Object[]> list = query.getResultList();
        
        Integer NumberOfWords = null;
        Integer NumberOfWordsWithSound = null;
        
        if (!list.isEmpty()){
            NumberOfWords = (Integer) ((Number)list.get(0)[0]).intValue();            
            NumberOfWordsWithSound = (Integer) ((Number)list.get(0)[1]).intValue();
        }
        Info.put("NumberOfWords", NumberOfWords);
        Info.put("NumberOfWordsWithSound", NumberOfWordsWithSound);
        
        return Info;
        
    }    
    
    public List<Dictionary> Accessible(User user){    
        Query query = em.createNamedQuery("Dictionary.Accessible");
        query.setParameter("user", user);        
        List<Dictionary> list = query.getResultList();
        
        return list;
    }
    
    
    @Override
    public Boolean ReadAccess(Integer dictID, Integer userID){    
        Query query = em.createNamedQuery("Dictionary.ReadAccess");
        query.setParameter("userID", userID);
        query.setParameter("dictID", dictID);
        List<Dictionary> list = query.getResultList();
        
        return !list.isEmpty();
    }    
    
    @Override
    public Boolean WriteAccess(Integer dictID, Integer userID){    
        Query query = em.createNamedQuery("Dictionary.WriteAccess");
        query.setParameter("userID", userID);
        query.setParameter("dictID", dictID);
        List<Dictionary> list = query.getResultList();
        
        return !list.isEmpty();
    }    

    
    @Override
    public Integer NumberOfWords(Dictionary dict){
        Query query = em.createNamedQuery("Dictionary.NumberOfWords");
        query.setParameter("dict", dict);
        
        int count = (int)(long)query.getSingleResult();
        
        return count;
    }   
    
}
