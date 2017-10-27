package SpellRefiner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ejb.Stateless;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;


@Stateless
public class DAO_DictionaryError implements DAO_Interface_DictionaryError{    
    @PersistenceContext(unitName = "SpellRefiner")
    private EntityManager em;                    
    
    @Override
    public Integer SaveNew(DictionaryError entity){    
       em.persist(entity);    
       em.flush();
       return entity.getDictErrorID();
    }            
    
    
    @Override
    public void Update(DictionaryError entity){    
       em.merge(entity);        
    }            

    @Override
    public void Delete(DictionaryError entity){    
       DictionaryError entity1 = em.merge(entity);        
       em.remove(entity1);        
    }            
    

    @Override
    public List<DictionaryError> findAll(){    
        Query query = em.createNamedQuery("DictionaryError.findAll");
        List<DictionaryError> list = query.getResultList();
        
        
        return list;
    }   
    
    
    @Override
    public List<DictionaryError> findByDictErrorID(Long dictErrorID){    
        Query query = em.createNamedQuery("DictionaryError.findByDictErrorID");
        query.setParameter("dictErrorID", dictErrorID);
        List<DictionaryError> list = query.getResultList();
        
        return list;
    }   
    
    @Override
    public List<DictionaryError> findByWord(Word word){    
        Query query = em.createNamedQuery("DictionaryError.findByWord");
        query.setParameter("word", word);
        List<DictionaryError> list = query.getResultList();
        
        return list;
    }   

    @Override
    public List<DictionaryError> findNotProcessedErrorsByWord(Word word){    
        Query query = em.createNamedQuery("DictionaryError.findNotProcessedErrorsByWord");
        query.setParameter("word", word);
        List<DictionaryError> list = query.getResultList();
        
        return list;
    }   

    @Override
    public List<DictionaryError> findErrorsByWord(Word word){    
        Query query = em.createNamedQuery("DictionaryError.findErrorsByWord");
        query.setParameter("word", word);
        List<DictionaryError> list = query.getResultList();
        
        return list;
    }   
    
    /*
    @Override
    public void UpdateListWord_WordDictionaryErrors(List<Word> List_Word){
        
        for (Word w: List_Word) {
             List<DictionaryError> List_DictionaryError = findByWord(w);  
             w.setWordDictionaryErrors(List_DictionaryError);
             System.out.println("--------------------UpdateListWord_WordDictionaryErrors word.getWordID() " + w.getWordID());
             System.out.println("--------------------UpdateListWord_WordDictionaryErrors List_DictionaryError.size() " + List_DictionaryError.size());
             System.out.println("--------------------UpdateListWord_WordDictionaryErrors w.getWordDictionaryErrors().size() " + w.getWordDictionaryErrors().size());
        }    
        
    } 
    */

    
    @Override
    public List<Integer> GetList_WordID_From_DictionaryError(Integer dict_id){
    	//System.out.println("--------------------GetList_WordID_With_DictionaryError");
    	
        String str_query ="SELECT DISTINCT Word_ID FROM dictionaryerrors AS de WHERE de.Dict_ID = :dict_id";
        	
        //System.out.println("--------------------str_query = " + str_query);
        
        Query query = em.createNativeQuery(str_query);
        query.setParameter("dict_id", dict_id);
        List<Integer> list = query.getResultList();        
    	
    	return list;
    }   
    
    
}
