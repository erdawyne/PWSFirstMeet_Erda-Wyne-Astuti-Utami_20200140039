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
public class BarangKeluarJpaController implements Serializable {

    public BarangKeluarJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(BarangKeluar barangKeluar) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Barang kodeBarang = barangKeluar.getKodeBarang();
            if (kodeBarang != null) {
                kodeBarang = em.getReference(kodeBarang.getClass(), kodeBarang.getKodeBarang());
                barangKeluar.setKodeBarang(kodeBarang);
            }
            em.persist(barangKeluar);
            if (kodeBarang != null) {
                kodeBarang.getBarangKeluarCollection().add(barangKeluar);
                kodeBarang = em.merge(kodeBarang);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findBarangKeluar(barangKeluar.getNoKeluarBarang()) != null) {
                throw new PreexistingEntityException("BarangKeluar " + barangKeluar + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(BarangKeluar barangKeluar) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            BarangKeluar persistentBarangKeluar = em.find(BarangKeluar.class, barangKeluar.getNoKeluarBarang());
            Barang kodeBarangOld = persistentBarangKeluar.getKodeBarang();
            Barang kodeBarangNew = barangKeluar.getKodeBarang();
            if (kodeBarangNew != null) {
                kodeBarangNew = em.getReference(kodeBarangNew.getClass(), kodeBarangNew.getKodeBarang());
                barangKeluar.setKodeBarang(kodeBarangNew);
            }
            barangKeluar = em.merge(barangKeluar);
            if (kodeBarangOld != null && !kodeBarangOld.equals(kodeBarangNew)) {
                kodeBarangOld.getBarangKeluarCollection().remove(barangKeluar);
                kodeBarangOld = em.merge(kodeBarangOld);
            }
            if (kodeBarangNew != null && !kodeBarangNew.equals(kodeBarangOld)) {
                kodeBarangNew.getBarangKeluarCollection().add(barangKeluar);
                kodeBarangNew = em.merge(kodeBarangNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = barangKeluar.getNoKeluarBarang();
                if (findBarangKeluar(id) == null) {
                    throw new NonexistentEntityException("The barangKeluar with id " + id + " no longer exists.");
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
            BarangKeluar barangKeluar;
            try {
                barangKeluar = em.getReference(BarangKeluar.class, id);
                barangKeluar.getNoKeluarBarang();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The barangKeluar with id " + id + " no longer exists.", enfe);
            }
            Barang kodeBarang = barangKeluar.getKodeBarang();
            if (kodeBarang != null) {
                kodeBarang.getBarangKeluarCollection().remove(barangKeluar);
                kodeBarang = em.merge(kodeBarang);
            }
            em.remove(barangKeluar);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<BarangKeluar> findBarangKeluarEntities() {
        return findBarangKeluarEntities(true, -1, -1);
    }

    public List<BarangKeluar> findBarangKeluarEntities(int maxResults, int firstResult) {
        return findBarangKeluarEntities(false, maxResults, firstResult);
    }

    private List<BarangKeluar> findBarangKeluarEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(BarangKeluar.class));
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

    public BarangKeluar findBarangKeluar(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(BarangKeluar.class, id);
        } finally {
            em.close();
        }
    }

    public int getBarangKeluarCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<BarangKeluar> rt = cq.from(BarangKeluar.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
