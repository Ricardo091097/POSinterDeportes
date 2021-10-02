/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Controladores.exceptions.NonexistentEntityException;
import Controladores.exceptions.PreexistingEntityException;
import Entidades.DetallesVenta;
import Entidades.DetallesVentaPK;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.Ventas;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Ricardo Avalos
 */
public class DetallesVentaJpaController implements Serializable {

    public DetallesVentaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public DetallesVentaJpaController() {
        this.emf = Persistence.createEntityManagerFactory("DeportesPU");
    }
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DetallesVenta detallesVenta) throws PreexistingEntityException, Exception {
        if (detallesVenta.getDetallesVentaPK() == null) {
            detallesVenta.setDetallesVentaPK(new DetallesVentaPK());
        }
        detallesVenta.getDetallesVentaPK().setNoVenta(detallesVenta.getVentas().getNoVenta());
        detallesVenta.getDetallesVentaPK().setIdProducto(detallesVenta.getProductos().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ventas ventas = detallesVenta.getVentas();
            if (ventas != null) {
                ventas = em.getReference(ventas.getClass(), ventas.getNoVenta());
                detallesVenta.setVentas(ventas);
            }
            em.persist(detallesVenta);
            if (ventas != null) {
                ventas.getDetallesVentaList().add(detallesVenta);
                ventas = em.merge(ventas);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDetallesVenta(detallesVenta.getDetallesVentaPK()) != null) {
                throw new PreexistingEntityException("DetallesVenta " + detallesVenta + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DetallesVenta detallesVenta) throws NonexistentEntityException, Exception {
        detallesVenta.getDetallesVentaPK().setNoVenta(detallesVenta.getVentas().getNoVenta());
        detallesVenta.getDetallesVentaPK().setIdProducto(detallesVenta.getProductos().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DetallesVenta persistentDetallesVenta = em.find(DetallesVenta.class, detallesVenta.getDetallesVentaPK());
            Ventas ventasOld = persistentDetallesVenta.getVentas();
            Ventas ventasNew = detallesVenta.getVentas();
            if (ventasNew != null) {
                ventasNew = em.getReference(ventasNew.getClass(), ventasNew.getNoVenta());
                detallesVenta.setVentas(ventasNew);
            }
            detallesVenta = em.merge(detallesVenta);
            if (ventasOld != null && !ventasOld.equals(ventasNew)) {
                ventasOld.getDetallesVentaList().remove(detallesVenta);
                ventasOld = em.merge(ventasOld);
            }
            if (ventasNew != null && !ventasNew.equals(ventasOld)) {
                ventasNew.getDetallesVentaList().add(detallesVenta);
                ventasNew = em.merge(ventasNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                DetallesVentaPK id = detallesVenta.getDetallesVentaPK();
                if (findDetallesVenta(id) == null) {
                    throw new NonexistentEntityException("The detallesVenta with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(DetallesVentaPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DetallesVenta detallesVenta;
            try {
                detallesVenta = em.getReference(DetallesVenta.class, id);
                detallesVenta.getDetallesVentaPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The detallesVenta with id " + id + " no longer exists.", enfe);
            }
            Ventas ventas = detallesVenta.getVentas();
            if (ventas != null) {
                ventas.getDetallesVentaList().remove(detallesVenta);
                ventas = em.merge(ventas);
            }
            em.remove(detallesVenta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DetallesVenta> findDetallesVentaEntities() {
        return findDetallesVentaEntities(true, -1, -1);
    }

    public List<DetallesVenta> findDetallesVentaEntities(int maxResults, int firstResult) {
        return findDetallesVentaEntities(false, maxResults, firstResult);
    }

    private List<DetallesVenta> findDetallesVentaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DetallesVenta.class));
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

    public DetallesVenta findDetallesVenta(DetallesVentaPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DetallesVenta.class, id);
        } finally {
            em.close();
        }
    }

    public int getDetallesVentaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DetallesVenta> rt = cq.from(DetallesVenta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
