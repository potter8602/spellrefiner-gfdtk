package SpellRefiner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ejb.Stateless;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;


@Stateless
public class DAO_Check implements DAO_Interface_Check{    
    @PersistenceContext(unitName = "SpellRefiner")
    private EntityManager em;                    
    
    @Override
    public Integer SaveNew(Check entity){    
       em.persist(entity);    
       em.flush();
       //em.refresh(entity);
       return entity.getCheckID();
    }            
    
    //test
    //@Override
    //public Check Add(Check entity){    
    //   Check entity1 = em.merge(entity);    
    //   em.flush();
    //   return entity1;
    //}            
    
    
    @Override
    public void Update(Check entity){    
       em.merge(entity);        
    }            

    @Override
    public void Delete(Check entity){    
       Check entity1 = em.merge(entity);        
       em.remove(entity1);        
    }            
    
    
    @Override
    public Check Get(Integer id){    
        Check entity = em.find(Check.class, id);
        return entity;
    } 
    

    @Override
    public List<Check> findAll(){    
        Query query = em.createNamedQuery("Check.findAll");
        List<Check> list = query.getResultList();
        
        return list;
    }   
    
    
    @Override
    public List<Check> findByCheckID(Integer checkID){    
        Query query = em.createNamedQuery("Check.findByCheckID");
        query.setParameter("checkID", checkID);
        List<Check> list = query.getResultList();
        
        return list;
    }   
    
    
    @Override
    public List<Check> MyChecks(User user, Boolean deleted, Boolean only_unfinished){    
        Query query = em.createNamedQuery("Check.MyChecks");
        query.setParameter("user", user);
        query.setParameter("deleted", deleted);
        query.setParameter("only_unfinished", only_unfinished);
        
        List<Check> list = query.getResultList();
        
        return list;
    }   
    
    @Override
    public Map<String, Object> GetCheckInfo(Check check){   
        Map<String, Object> Info = new LinkedHashMap<String, Object>();
        
        String str_query = "SELECT IFNULL(SUM(1), 0) AS NumberOfCheckedWords, 0 AS Test FROM inputs WHERE Check_ID = ? and Pass_Number=1";  
        Query query = em.createNativeQuery(str_query);        
        query.setParameter(1, check.getCheckID());
        List<Object[]> list = query.getResultList();
        //List<Integer> list = query.getResultList();
        
        Integer NumberOfCheckedWords = null;
        
        if (!list.isEmpty()){
        	NumberOfCheckedWords = (Integer) ((Number)list.get(0)[0]).intValue();
        	//NumberOfCheckedWords = list.get(0);
        }
        
        Info.put("NumberOfCheckedWords", NumberOfCheckedWords);
        
        return Info;
        
    }    

    
    @Override
    public Integer NumberOfMyChecks(User user){
        Query query = em.createNamedQuery("Check.NumberOfMyChecks");
        query.setParameter("user", user);
        
        int count = (int)(long)query.getSingleResult();
        
        return count;
    }   
    
    
}
