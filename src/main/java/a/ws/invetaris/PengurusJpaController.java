/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package a.ws.invetaris;

import a.ws.invetaris.exceptions.NonexistentEntityException;
import a.ws.invetaris.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ERDA WYNE
 */
public class PengurusJpaController implements Serializable {

    public PengurusJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pengurus pengurus) throws PreexistingEntityException, Exception {
        if (pengurus.getBarangCollection() == null) {
            pengurus.setBarangCollection(new ArrayList<Barang>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Barang> attachedBarangCollection = new ArrayList<Barang>();
            for (Barang barangCollectionBarangToAttach : pengurus.getBarangCollection()) {
                barangCollectionBarangToAttach = em.getReference(barangCollectionBarangToAttach.getClass(), barangCollectionBarangToAttach.getKodeBarang());
                attachedBarangCollection.add(barangCollectionBarangToAttach);
            }
            pengurus.setBarangCollection(attachedBarangCollection);
            em.persist(pengurus);
            for (Barang barangCollectionBarang : pengurus.getBarangCollection()) {
                Pengurus oldIdPengurusBarangOfBarangCollectionBarang = barangCollectionBarang.getIdPengurusBarang();
                barangCollectionBarang.setIdPengurusBarang(pengurus);
                barangCollectionBarang = em.merge(barangCollectionBarang);
                if (oldIdPengurusBarangOfBarangCollectionBarang != null) {
                    oldIdPengurusBarangOfBarangCollectionBarang.getBarangCollection().remove(barangCollectionBarang);
                    oldIdPengurusBarangOfBarangCollectionBarang = em.merge(oldIdPengurusBarangOfBarangCollectionBarang);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPengurus(pengurus.getIdPengurusBarang()) != null) {
                throw new PreexistingEntityException("Pengurus " + pengurus + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pengurus pengurus) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pengurus persistentPengurus = em.find(Pengurus.class, pengurus.getIdPengurusBarang());
            Collection<Barang> barangCollectionOld = persistentPengurus.getBarangCollection();
            Collection<Barang> barangCollectionNew = pengurus.getBarangCollection();
            Collection<Barang> attachedBarangCollectionNew = new ArrayList<Barang>();
            for (Barang barangCollectionNewBarangToAttach : barangCollectionNew) {
                barangCollectionNewBarangToAttach = em.getReference(barangCollectionNewBarangToAttach.getClass(), barangCollectionNewBarangToAttach.getKodeBarang());
                attachedBarangCollectionNew.add(barangCollectionNewBarangToAttach);
            }
            barangCollectionNew = attachedBarangCollectionNew;
            pengurus.setBarangCollection(barangCollectionNew);
            pengurus = em.merge(pengurus);
            for (Barang barangCollectionOldBarang : barangCollectionOld) {
                if (!barangCollectionNew.contains(barangCollectionOldBarang)) {
                    barangCollectionOldBarang.setIdPengurusBarang(null);
                    barangCollectionOldBarang = em.merge(barangCollectionOldBarang);
                }
            }
            for (Barang barangCollectionNewBarang : barangCollectionNew) {
                if (!barangCollectionOld.contains(barangCollectionNewBarang)) {
                    Pengurus oldIdPengurusBarangOfBarangCollectionNewBarang = barangCollectionNewBarang.getIdPengurusBarang();
                    barangCollectionNewBarang.setIdPengurusBarang(pengurus);
                    barangCollectionNewBarang = em.merge(barangCollectionNewBarang);
                    if (oldIdPengurusBarangOfBarangCollectionNewBarang != null && !oldIdPengurusBarangOfBarangCollectionNewBarang.equals(pengurus)) {
                        oldIdPengurusBarangOfBarangCollectionNewBarang.getBarangCollection().remove(barangCollectionNewBarang);
                        oldIdPengurusBarangOfBarangCollectionNewBarang = em.merge(oldIdPengurusBarangOfBarangCollectionNewBarang);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pengurus.getIdPengurusBarang();
                if (findPengurus(id) == null) {
                    throw new NonexistentEntityException("The pengurus with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pengurus pengurus;
            try {
                pengurus = em.getReference(Pengurus.class, id);
                pengurus.getIdPengurusBarang();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pengurus with id " + id + " no longer exists.", enfe);
            }
            Collection<Barang> barangCollection = pengurus.getBarangCollection();
            for (Barang barangCollectionBarang : barangCollection) {
                barangCollectionBarang.setIdPengurusBarang(null);
                barangCollectionBarang = em.merge(barangCollectionBarang);
            }
            em.remove(pengurus);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pengurus> findPengurusEntities() {
        return findPengurusEntities(true, -1, -1);
    }

    public List<Pengurus> findPengurusEntities(int maxResults, int firstResult) {
        return findPengurusEntities(false, maxResults, firstResult);
    }

    private List<Pengurus> findPengurusEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pengurus.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Pengurus findPengurus(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pengurus.class, id);
        } finally {
            em.close();
        }
    }

    public int getPengurusCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pengurus> rt = cq.from(Pengurus.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
