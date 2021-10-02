/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Controladores.exceptions.IllegalOrphanException;
import Controladores.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.Usuarios;
import Entidades.DetallesVenta;
import Entidades.Ventas;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Ricardo Avalos
 */
public class VentasJpaController implements Serializable {

    public VentasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public VentasJpaController() {
        this.emf = Persistence.createEntityManagerFactory("DeportesPU");
    }
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ventas ventas) {
        if (ventas.getDetallesVentaList() == null) {
            ventas.setDetallesVentaList(new ArrayList<DetallesVenta>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuarios idVendedor = ventas.getIdVendedor();
            if (idVendedor != null) {
                idVendedor = em.getReference(idVendedor.getClass(), idVendedor.getIdUsuario());
                ventas.setIdVendedor(idVendedor);
            }
            List<DetallesVenta> attachedDetallesVentaList = new ArrayList<DetallesVenta>();
            for (DetallesVenta detallesVentaListDetallesVentaToAttach : ventas.getDetallesVentaList()) {
                detallesVentaListDetallesVentaToAttach = em.getReference(detallesVentaListDetallesVentaToAttach.getClass(), detallesVentaListDetallesVentaToAttach.getDetallesVentaPK());
                attachedDetallesVentaList.add(detallesVentaListDetallesVentaToAttach);
            }
            ventas.setDetallesVentaList(attachedDetallesVentaList);
            em.persist(ventas);
            if (idVendedor != null) {
                idVendedor.getVentasList().add(ventas);
                idVendedor = em.merge(idVendedor);
            }
            for (DetallesVenta detallesVentaListDetallesVenta : ventas.getDetallesVentaList()) {
                Ventas oldVentasOfDetallesVentaListDetallesVenta = detallesVentaListDetallesVenta.getVentas();
                detallesVentaListDetallesVenta.setVentas(ventas);
                detallesVentaListDetallesVenta = em.merge(detallesVentaListDetallesVenta);
                if (oldVentasOfDetallesVentaListDetallesVenta != null) {
                    oldVentasOfDetallesVentaListDetallesVenta.getDetallesVentaList().remove(detallesVentaListDetallesVenta);
                    oldVentasOfDetallesVentaListDetallesVenta = em.merge(oldVentasOfDetallesVentaListDetallesVenta);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ventas ventas) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ventas persistentVentas = em.find(Ventas.class, ventas.getNoVenta());
            Usuarios idVendedorOld = persistentVentas.getIdVendedor();
            Usuarios idVendedorNew = ventas.getIdVendedor();
            List<DetallesVenta> detallesVentaListOld = persistentVentas.getDetallesVentaList();
            List<DetallesVenta> detallesVentaListNew = ventas.getDetallesVentaList();
            List<String> illegalOrphanMessages = null;
            for (DetallesVenta detallesVentaListOldDetallesVenta : detallesVentaListOld) {
                if (!detallesVentaListNew.contains(detallesVentaListOldDetallesVenta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DetallesVenta " + detallesVentaListOldDetallesVenta + " since its ventas field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idVendedorNew != null) {
                idVendedorNew = em.getReference(idVendedorNew.getClass(), idVendedorNew.getIdUsuario());
                ventas.setIdVendedor(idVendedorNew);
            }
            List<DetallesVenta> attachedDetallesVentaListNew = new ArrayList<DetallesVenta>();
            for (DetallesVenta detallesVentaListNewDetallesVentaToAttach : detallesVentaListNew) {
                detallesVentaListNewDetallesVentaToAttach = em.getReference(detallesVentaListNewDetallesVentaToAttach.getClass(), detallesVentaListNewDetallesVentaToAttach.getDetallesVentaPK());
                attachedDetallesVentaListNew.add(detallesVentaListNewDetallesVentaToAttach);
            }
            detallesVentaListNew = attachedDetallesVentaListNew;
            ventas.setDetallesVentaList(detallesVentaListNew);
            ventas = em.merge(ventas);
            if (idVendedorOld != null && !idVendedorOld.equals(idVendedorNew)) {
                idVendedorOld.getVentasList().remove(ventas);
                idVendedorOld = em.merge(idVendedorOld);
            }
            if (idVendedorNew != null && !idVendedorNew.equals(idVendedorOld)) {
                idVendedorNew.getVentasList().add(ventas);
                idVendedorNew = em.merge(idVendedorNew);
            }
            for (DetallesVenta detallesVentaListNewDetallesVenta : detallesVentaListNew) {
                if (!detallesVentaListOld.contains(detallesVentaListNewDetallesVenta)) {
                    Ventas oldVentasOfDetallesVentaListNewDetallesVenta = detallesVentaListNewDetallesVenta.getVentas();
                    detallesVentaListNewDetallesVenta.setVentas(ventas);
                    detallesVentaListNewDetallesVenta = em.merge(detallesVentaListNewDetallesVenta);
                    if (oldVentasOfDetallesVentaListNewDetallesVenta != null && !oldVentasOfDetallesVentaListNewDetallesVenta.equals(ventas)) {
                        oldVentasOfDetallesVentaListNewDetallesVenta.getDetallesVentaList().remove(detallesVentaListNewDetallesVenta);
                        oldVentasOfDetallesVentaListNewDetallesVenta = em.merge(oldVentasOfDetallesVentaListNewDetallesVenta);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ventas.getNoVenta();
                if (findVentas(id) == null) {
                    throw new NonexistentEntityException("The ventas with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ventas ventas;
            try {
                ventas = em.getReference(Ventas.class, id);
                ventas.getNoVenta();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ventas with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<DetallesVenta> detallesVentaListOrphanCheck = ventas.getDetallesVentaList();
            for (DetallesVenta detallesVentaListOrphanCheckDetallesVenta : detallesVentaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Ventas (" + ventas + ") cannot be destroyed since the DetallesVenta " + detallesVentaListOrphanCheckDetallesVenta + " in its detallesVentaList field has a non-nullable ventas field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Usuarios idVendedor = ventas.getIdVendedor();
            if (idVendedor != null) {
                idVendedor.getVentasList().remove(ventas);
                idVendedor = em.merge(idVendedor);
            }
            em.remove(ventas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ventas> findVentasEntities() {
        return findVentasEntities(true, -1, -1);
    }

    public List<Ventas> findVentasEntities(int maxResults, int firstResult) {
        return findVentasEntities(false, maxResults, firstResult);
    }

    private List<Ventas> findVentasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ventas.class));
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

    public Ventas findVentas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ventas.class, id);
        } finally {
            em.close();
        }
    }

    public int getVentasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ventas> rt = cq.from(Ventas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
