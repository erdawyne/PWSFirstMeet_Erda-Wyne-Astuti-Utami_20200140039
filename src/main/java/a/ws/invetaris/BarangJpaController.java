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
import javax.persistence.Persistence;

/**
 *
 * @author ERDA WYNE
 */
public class BarangJpaController implements Serializable {

    public BarangJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory(ws.a_learningapi_jar_0.0.1-SNAPSHOTPU)
    
    public BarangJpaController(){
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Barang barang) throws PreexistingEntityException, Exception {
        if (barang.getBarangKeluarCollection() == null) {
            barang.setBarangKeluarCollection(new ArrayList<BarangKeluar>());
        }
        if (barang.getBarangMasukCollection() == null) {
            barang.setBarangMasukCollection(new ArrayList<BarangMasuk>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pengurus idPengurusBarang = barang.getIdPengurusBarang();
            if (idPengurusBarang != null) {
                idPengurusBarang = em.getReference(idPengurusBarang.getClass(), idPengurusBarang.getIdPengurusBarang());
                barang.setIdPengurusBarang(idPengurusBarang);
            }
            Collection<BarangKeluar> attachedBarangKeluarCollection = new ArrayList<BarangKeluar>();
            for (BarangKeluar barangKeluarCollectionBarangKeluarToAttach : barang.getBarangKeluarCollection()) {
                barangKeluarCollectionBarangKeluarToAttach = em.getReference(barangKeluarCollectionBarangKeluarToAttach.getClass(), barangKeluarCollectionBarangKeluarToAttach.getNoKeluarBarang());
                attachedBarangKeluarCollection.add(barangKeluarCollectionBarangKeluarToAttach);
            }
            barang.setBarangKeluarCollection(attachedBarangKeluarCollection);
            Collection<BarangMasuk> attachedBarangMasukCollection = new ArrayList<BarangMasuk>();
            for (BarangMasuk barangMasukCollectionBarangMasukToAttach : barang.getBarangMasukCollection()) {
                barangMasukCollectionBarangMasukToAttach = em.getReference(barangMasukCollectionBarangMasukToAttach.getClass(), barangMasukCollectionBarangMasukToAttach.getNoMasukBarang());
                attachedBarangMasukCollection.add(barangMasukCollectionBarangMasukToAttach);
            }
            barang.setBarangMasukCollection(attachedBarangMasukCollection);
            em.persist(barang);
            if (idPengurusBarang != null) {
                idPengurusBarang.getBarangCollection().add(barang);
                idPengurusBarang = em.merge(idPengurusBarang);
            }
            for (BarangKeluar barangKeluarCollectionBarangKeluar : barang.getBarangKeluarCollection()) {
                Barang oldKodeBarangOfBarangKeluarCollectionBarangKeluar = barangKeluarCollectionBarangKeluar.getKodeBarang();
                barangKeluarCollectionBarangKeluar.setKodeBarang(barang);
                barangKeluarCollectionBarangKeluar = em.merge(barangKeluarCollectionBarangKeluar);
                if (oldKodeBarangOfBarangKeluarCollectionBarangKeluar != null) {
                    oldKodeBarangOfBarangKeluarCollectionBarangKeluar.getBarangKeluarCollection().remove(barangKeluarCollectionBarangKeluar);
                    oldKodeBarangOfBarangKeluarCollectionBarangKeluar = em.merge(oldKodeBarangOfBarangKeluarCollectionBarangKeluar);
                }
            }
            for (BarangMasuk barangMasukCollectionBarangMasuk : barang.getBarangMasukCollection()) {
                Barang oldKodeBarangOfBarangMasukCollectionBarangMasuk = barangMasukCollectionBarangMasuk.getKodeBarang();
                barangMasukCollectionBarangMasuk.setKodeBarang(barang);
                barangMasukCollectionBarangMasuk = em.merge(barangMasukCollectionBarangMasuk);
                if (oldKodeBarangOfBarangMasukCollectionBarangMasuk != null) {
                    oldKodeBarangOfBarangMasukCollectionBarangMasuk.getBarangMasukCollection().remove(barangMasukCollectionBarangMasuk);
                    oldKodeBarangOfBarangMasukCollectionBarangMasuk = em.merge(oldKodeBarangOfBarangMasukCollectionBarangMasuk);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findBarang(barang.getKodeBarang()) != null) {
                throw new PreexistingEntityException("Barang " + barang + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Barang barang) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Barang persistentBarang = em.find(Barang.class, barang.getKodeBarang());
            Pengurus idPengurusBarangOld = persistentBarang.getIdPengurusBarang();
            Pengurus idPengurusBarangNew = barang.getIdPengurusBarang();
            Collection<BarangKeluar> barangKeluarCollectionOld = persistentBarang.getBarangKeluarCollection();
            Collection<BarangKeluar> barangKeluarCollectionNew = barang.getBarangKeluarCollection();
            Collection<BarangMasuk> barangMasukCollectionOld = persistentBarang.getBarangMasukCollection();
            Collection<BarangMasuk> barangMasukCollectionNew = barang.getBarangMasukCollection();
            if (idPengurusBarangNew != null) {
                idPengurusBarangNew = em.getReference(idPengurusBarangNew.getClass(), idPengurusBarangNew.getIdPengurusBarang());
                barang.setIdPengurusBarang(idPengurusBarangNew);
            }
            Collection<BarangKeluar> attachedBarangKeluarCollectionNew = new ArrayList<BarangKeluar>();
            for (BarangKeluar barangKeluarCollectionNewBarangKeluarToAttach : barangKeluarCollectionNew) {
                barangKeluarCollectionNewBarangKeluarToAttach = em.getReference(barangKeluarCollectionNewBarangKeluarToAttach.getClass(), barangKeluarCollectionNewBarangKeluarToAttach.getNoKeluarBarang());
                attachedBarangKeluarCollectionNew.add(barangKeluarCollectionNewBarangKeluarToAttach);
            }
            barangKeluarCollectionNew = attachedBarangKeluarCollectionNew;
            barang.setBarangKeluarCollection(barangKeluarCollectionNew);
            Collection<BarangMasuk> attachedBarangMasukCollectionNew = new ArrayList<BarangMasuk>();
            for (BarangMasuk barangMasukCollectionNewBarangMasukToAttach : barangMasukCollectionNew) {
                barangMasukCollectionNewBarangMasukToAttach = em.getReference(barangMasukCollectionNewBarangMasukToAttach.getClass(), barangMasukCollectionNewBarangMasukToAttach.getNoMasukBarang());
                attachedBarangMasukCollectionNew.add(barangMasukCollectionNewBarangMasukToAttach);
            }
            barangMasukCollectionNew = attachedBarangMasukCollectionNew;
            barang.setBarangMasukCollection(barangMasukCollectionNew);
            barang = em.merge(barang);
            if (idPengurusBarangOld != null && !idPengurusBarangOld.equals(idPengurusBarangNew)) {
                idPengurusBarangOld.getBarangCollection().remove(barang);
                idPengurusBarangOld = em.merge(idPengurusBarangOld);
            }
            if (idPengurusBarangNew != null && !idPengurusBarangNew.equals(idPengurusBarangOld)) {
                idPengurusBarangNew.getBarangCollection().add(barang);
                idPengurusBarangNew = em.merge(idPengurusBarangNew);
            }
            for (BarangKeluar barangKeluarCollectionOldBarangKeluar : barangKeluarCollectionOld) {
                if (!barangKeluarCollectionNew.contains(barangKeluarCollectionOldBarangKeluar)) {
                    barangKeluarCollectionOldBarangKeluar.setKodeBarang(null);
                    barangKeluarCollectionOldBarangKeluar = em.merge(barangKeluarCollectionOldBarangKeluar);
                }
            }
            for (BarangKeluar barangKeluarCollectionNewBarangKeluar : barangKeluarCollectionNew) {
                if (!barangKeluarCollectionOld.contains(barangKeluarCollectionNewBarangKeluar)) {
                    Barang oldKodeBarangOfBarangKeluarCollectionNewBarangKeluar = barangKeluarCollectionNewBarangKeluar.getKodeBarang();
                    barangKeluarCollectionNewBarangKeluar.setKodeBarang(barang);
                    barangKeluarCollectionNewBarangKeluar = em.merge(barangKeluarCollectionNewBarangKeluar);
                    if (oldKodeBarangOfBarangKeluarCollectionNewBarangKeluar != null && !oldKodeBarangOfBarangKeluarCollectionNewBarangKeluar.equals(barang)) {
                        oldKodeBarangOfBarangKeluarCollectionNewBarangKeluar.getBarangKeluarCollection().remove(barangKeluarCollectionNewBarangKeluar);
                        oldKodeBarangOfBarangKeluarCollectionNewBarangKeluar = em.merge(oldKodeBarangOfBarangKeluarCollectionNewBarangKeluar);
                    }
                }
            }
            for (BarangMasuk barangMasukCollectionOldBarangMasuk : barangMasukCollectionOld) {
                if (!barangMasukCollectionNew.contains(barangMasukCollectionOldBarangMasuk)) {
                    barangMasukCollectionOldBarangMasuk.setKodeBarang(null);
                    barangMasukCollectionOldBarangMasuk = em.merge(barangMasukCollectionOldBarangMasuk);
                }
            }
            for (BarangMasuk barangMasukCollectionNewBarangMasuk : barangMasukCollectionNew) {
                if (!barangMasukCollectionOld.contains(barangMasukCollectionNewBarangMasuk)) {
                    Barang oldKodeBarangOfBarangMasukCollectionNewBarangMasuk = barangMasukCollectionNewBarangMasuk.getKodeBarang();
                    barangMasukCollectionNewBarangMasuk.setKodeBarang(barang);
                    barangMasukCollectionNewBarangMasuk = em.merge(barangMasukCollectionNewBarangMasuk);
                    if (oldKodeBarangOfBarangMasukCollectionNewBarangMasuk != null && !oldKodeBarangOfBarangMasukCollectionNewBarangMasuk.equals(barang)) {
                        oldKodeBarangOfBarangMasukCollectionNewBarangMasuk.getBarangMasukCollection().remove(barangMasukCollectionNewBarangMasuk);
                        oldKodeBarangOfBarangMasukCollectionNewBarangMasuk = em.merge(oldKodeBarangOfBarangMasukCollectionNewBarangMasuk);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = barang.getKodeBarang();
                if (findBarang(id) == null) {
                    throw new NonexistentEntityException("The barang with id " + id + " no longer exists.");
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
            Barang barang;
            try {
                barang = em.getReference(Barang.class, id);
                barang.getKodeBarang();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The barang with id " + id + " no longer exists.", enfe);
            }
            Pengurus idPengurusBarang = barang.getIdPengurusBarang();
            if (idPengurusBarang != null) {
                idPengurusBarang.getBarangCollection().remove(barang);
                idPengurusBarang = em.merge(idPengurusBarang);
            }
            Collection<BarangKeluar> barangKeluarCollection = barang.getBarangKeluarCollection();
            for (BarangKeluar barangKeluarCollectionBarangKeluar : barangKeluarCollection) {
                barangKeluarCollectionBarangKeluar.setKodeBarang(null);
                barangKeluarCollectionBarangKeluar = em.merge(barangKeluarCollectionBarangKeluar);
            }
            Collection<BarangMasuk> barangMasukCollection = barang.getBarangMasukCollection();
            for (BarangMasuk barangMasukCollectionBarangMasuk : barangMasukCollection) {
                barangMasukCollectionBarangMasuk.setKodeBarang(null);
                barangMasukCollectionBarangMasuk = em.merge(barangMasukCollectionBarangMasuk);
            }
            em.remove(barang);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Barang> findBarangEntities() {
        return findBarangEntities(true, -1, -1);
    }

    public List<Barang> findBarangEntities(int maxResults, int firstResult) {
        return findBarangEntities(false, maxResults, firstResult);
    }

    private List<Barang> findBarangEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Barang.class));
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

    public Barang findBarang(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Barang.class, id);
        } finally {
            em.close();
        }
    }

    public int getBarangCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Barang> rt = cq.from(Barang.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
