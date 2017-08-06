package preprocessing.database;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * @author olyjosh
 */
public class DataClass {

    private EntityManager em = null;
    private EntityManagerFactory emf = null;

    public DataClass() {
        connectDatabase();
    }
    
    private void connectDatabase() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory("Sig.odb");
            
        }
        if (em == null) {
            em = emf.createEntityManager();
            
        }
        
    }

    
    //This should create a record in the database
    public void createSignatory(String name, String sex, short age,long time) {
        Signatory c = new Signatory(name, sex, age,time);
        em.getTransaction().begin();
        em.persist(c);
        em.getTransaction().commit();
//        em.clear();
    }

    //This will read an object from the database. 
    public Signatory getSignatory(long id) {
        connectDatabase();
        try {
            TypedQuery q = em.createQuery("SELECT si FROM Signatory si where si.id= :id", Signatory.class);
            q.setParameter("id", id);
            return (Signatory)q.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // This is another way of reading from the database
    public Signatory getSignatoryToo(long id) {
        // Note I just wrote this method now 2016/03/08 10:33:12 AM for ur 
        //demands. I never use it in this project but it should be correct
        Signatory si = em.find(Signatory.class, id);
        return si;
    }
    
    
    // This is another way of reading from the database. 
    //But with this you will read many objects from the objectdb as list
    public List<Signatory> getSignatories() {
        try {
            TypedQuery query = em.createQuery("SELECT si FROM Signatory si ORDER BY si.name", Signatory.class);
            if(query!=null){
                List<Signatory> sis = query.getResultList();
                return sis;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    //This will delete an object from the database
    public void deleteSig(long id) {
        Signatory si = em.find(Signatory.class, id);
        em.getTransaction().begin();
        em.remove(si);
        em.getTransaction().commit();
    }
    
    public void closeConnections(){
        emf.close();
        em.close();
    }
    
    
    
}