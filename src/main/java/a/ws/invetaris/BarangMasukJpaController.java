/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package a.ws.invetaris;

import a.ws.invetaris.exceptions.NonexistentEntityException;
import a.ws.invetaris.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author ERDA WYNE
 */
public class BarangMasukJpaController implements Serializable {

    public BarangMasukJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(BarangMasuk barangMasuk) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Barang kodeBarang = barangMasuk.getKodeBarang();
            if (kodeBarang != null) {
                kodeBarang = em.getReference(kodeBarang.getClass(), kodeBarang.getKodeBarang());
                barangMasuk.setKodeBarang(kodeBarang);
            }
            em.persist(barangMasuk);
            if (kodeBarang != null) {
                kodeBarang.getBarangMasukCollection().add(barangMasuk);
                kodeBarang = em.merge(kodeBarang);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findBarangMasuk(barangMasuk.getNoMasukBarang()) != null) {
                throw new PreexistingEntityException("BarangMasuk " + barangMasuk + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(BarangMasuk barangMasuk) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            BarangMasuk persistentBarangMasuk = em.find(BarangMasuk.class, barangMasuk.getNoMasukBarang());
            Barang kodeBarangOld = persistentBarangMasuk.getKodeBarang();
            Barang kodeBarangNew = barangMasuk.getKodeBarang();
            if (kodeBarangNew != null) {
                kodeBarangNew = em.getReference(kodeBarangNew.getClass(), kodeBarangNew.getKodeBarang());
                barangMasuk.setKodeBarang(kodeBarangNew);
            }
            barangMasuk = em.merge(barangMasuk);
            if (kodeBarangOld != null && !kodeBarangOld.equals(kodeBarangNew)) {
                kodeBarangOld.getBarangMasukCollection().remove(barangMasuk);
                kodeBarangOld = em.merge(kodeBarangOld);
            }
            if (kodeBarangNew != null && !kodeBarangNew.equals(kodeBarangOld)) {
                kodeBarangNew.getBarangMasukCollection().add(barangMasuk);
                kodeBarangNew = em.merge(kodeBarangNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = barangMasuk.getNoMasukBarang();
                if (findBarangMasuk(id) == null) {
                    throw new NonexistentEntityException("The barangMasuk with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            BarangMasuk barangMasuk;
            try {
                barangMasuk = em.getReference(BarangMasuk.class, id);
                barangMasuk.getNoMasukBarang();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The barangMasuk with id " + id + " no longer exists.", enfe);
            }
            Barang kodeBarang = barangMasuk.getKodeBarang();
            if (kodeBarang != null) {
                kodeBarang.getBarangMasukCollection().remove(barangMasuk);
                kodeBarang = em.merge(kodeBarang);
            }
            em.remove(barangMasuk);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<BarangMasuk> findBarangMasukEntities() {
        return findBarangMasukEntities(true, -1, -1);
    }

    public List<BarangMasuk> findBarangMasukEntities(int maxResults, int firstResult) {
        return findBarangMasukEntities(false, maxResults, firstResult);
    }

    private List<BarangMasuk> findBarangMasukEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(BarangMasuk.class));
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

    public BarangMasuk findBarangMasuk(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(BarangMasuk.class, id);
        } finally {
            em.close();
        }
    }

    public int getBarangMasukCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<BarangMasuk> rt = cq.from(BarangMasuk.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
