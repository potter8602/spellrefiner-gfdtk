package SpellRefiner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ejb.Stateless;
import java.util.List;
import javax.persistence.Query;


@Stateless
public class DAO_DictionaryPermission implements DAO_Interface_DictionaryPermission{    
    @PersistenceContext(unitName = "SpellRefiner")
    private EntityManager em;                    
    
    @Override
    public Integer SaveNew(DictionaryPermission entity){    
       em.persist(entity);        
       em.flush();
       
        return entity.getDictPermID();
    }            
    
    
    @Override
    public void Update(DictionaryPermission entity){    
       em.merge(entity);        
    }            

    @Override
    public void Delete(DictionaryPermission entity){    
       DictionaryPermission entity1 = em.merge(entity);        
       em.remove(entity1);        
    }            
    
    /*
    @Override
    public DictionaryPermission Get(Integer id){    
        DictionaryPermission u = em.find(DictionaryPermission.class, id);
        return u;
    } 
    */

    @Override
    public List<DictionaryPermission> findAll(){    
        Query query = em.createNamedQuery("DictionaryPermission.findAll");
        List<DictionaryPermission> list = query.getResultList();
        
        return list;
    }   
    
    
    @Override
    public List<DictionaryPermission> findByDictPermID(Long dictPermID){    
        Query query = em.createNamedQuery("DictionaryPermission.findByDictPermID");
        query.setParameter("dictPermID", dictPermID);
        List<DictionaryPermission> list = query.getResultList();
        
        return list;
    }   
    
    @Override
    public List<DictionaryPermission> findByDict(Dictionary dict){    
        Query query = em.createNamedQuery("DictionaryPermission.findByDict");
        query.setParameter("dictPermDict", dict);
        List<DictionaryPermission> list = query.getResultList();
        
        return list;
    }   
    
    @Override
    public List<DictionaryPermission> findByDictAndUser(Dictionary dict, User user){    
        Query query = em.createNamedQuery("DictionaryPermission.findByDictAndUser");
        query.setParameter("dictPermDict", dict);
        query.setParameter("dictPermUser", user);
        List<DictionaryPermission> list = query.getResultList();
        
        return list;
    }   
    
    
}
