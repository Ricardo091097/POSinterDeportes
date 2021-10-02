/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Controladores.exceptions.IllegalOrphanException;
import Controladores.exceptions.NonexistentEntityException;
import Controladores.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.Empleados;
import Entidades.Usuarios;
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
public class UsuariosJpaController implements Serializable {

    public UsuariosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public UsuariosJpaController() {
        this.emf = Persistence.createEntityManagerFactory("DeportesPU");
    }
    
    
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuarios usuarios) throws IllegalOrphanException, PreexistingEntityException, Exception {
        if (usuarios.getVentasList() == null) {
            usuarios.setVentasList(new ArrayList<Ventas>());
        }
        List<String> illegalOrphanMessages = null;
        Empleados idEmpOrphanCheck = usuarios.getIdEmp();
        if (idEmpOrphanCheck != null) {
            Usuarios oldUsuariosOfIdEmp = idEmpOrphanCheck.getUsuarios();
            if (oldUsuariosOfIdEmp != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Empleados " + idEmpOrphanCheck + " already has an item of type Usuarios whose idEmp column cannot be null. Please make another selection for the idEmp field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empleados idEmp = usuarios.getIdEmp();
            if (idEmp != null) {
                idEmp = em.getReference(idEmp.getClass(), idEmp.getId());
                usuarios.setIdEmp(idEmp);
            }
            List<Ventas> attachedVentasList = new ArrayList<Ventas>();
            for (Ventas ventasListVentasToAttach : usuarios.getVentasList()) {
                ventasListVentasToAttach = em.getReference(ventasListVentasToAttach.getClass(), ventasListVentasToAttach.getNoVenta());
                attachedVentasList.add(ventasListVentasToAttach);
            }
            usuarios.setVentasList(attachedVentasList);
            em.persist(usuarios);
            if (idEmp != null) {
                idEmp.setUsuarios(usuarios);
                idEmp = em.merge(idEmp);
            }
            for (Ventas ventasListVentas : usuarios.getVentasList()) {
                Usuarios oldIdVendedorOfVentasListVentas = ventasListVentas.getIdVendedor();
                ventasListVentas.setIdVendedor(usuarios);
                ventasListVentas = em.merge(ventasListVentas);
                if (oldIdVendedorOfVentasListVentas != null) {
                    oldIdVendedorOfVentasListVentas.getVentasList().remove(ventasListVentas);
                    oldIdVendedorOfVentasListVentas = em.merge(oldIdVendedorOfVentasListVentas);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUsuarios(usuarios.getIdUsuario()) != null) {
                throw new PreexistingEntityException("Usuarios " + usuarios + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuarios usuarios) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuarios persistentUsuarios = em.find(Usuarios.class, usuarios.getIdUsuario());
            Empleados idEmpOld = persistentUsuarios.getIdEmp();
            Empleados idEmpNew = usuarios.getIdEmp();
            List<Ventas> ventasListOld = persistentUsuarios.getVentasList();
            List<Ventas> ventasListNew = usuarios.getVentasList();
            List<String> illegalOrphanMessages = null;
            if (idEmpNew != null && !idEmpNew.equals(idEmpOld)) {
                Usuarios oldUsuariosOfIdEmp = idEmpNew.getUsuarios();
                if (oldUsuariosOfIdEmp != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Empleados " + idEmpNew + " already has an item of type Usuarios whose idEmp column cannot be null. Please make another selection for the idEmp field.");
                }
            }
            for (Ventas ventasListOldVentas : ventasListOld) {
                if (!ventasListNew.contains(ventasListOldVentas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Ventas " + ventasListOldVentas + " since its idVendedor field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idEmpNew != null) {
                idEmpNew = em.getReference(idEmpNew.getClass(), idEmpNew.getId());
                usuarios.setIdEmp(idEmpNew);
            }
            List<Ventas> attachedVentasListNew = new ArrayList<Ventas>();
            for (Ventas ventasListNewVentasToAttach : ventasListNew) {
                ventasListNewVentasToAttach = em.getReference(ventasListNewVentasToAttach.getClass(), ventasListNewVentasToAttach.getNoVenta());
                attachedVentasListNew.add(ventasListNewVentasToAttach);
            }
            ventasListNew = attachedVentasListNew;
            usuarios.setVentasList(ventasListNew);
            usuarios = em.merge(usuarios);
            if (idEmpOld != null && !idEmpOld.equals(idEmpNew)) {
                idEmpOld.setUsuarios(null);
                idEmpOld = em.merge(idEmpOld);
            }
            if (idEmpNew != null && !idEmpNew.equals(idEmpOld)) {
                idEmpNew.setUsuarios(usuarios);
                idEmpNew = em.merge(idEmpNew);
            }
            for (Ventas ventasListNewVentas : ventasListNew) {
                if (!ventasListOld.contains(ventasListNewVentas)) {
                    Usuarios oldIdVendedorOfVentasListNewVentas = ventasListNewVentas.getIdVendedor();
                    ventasListNewVentas.setIdVendedor(usuarios);
                    ventasListNewVentas = em.merge(ventasListNewVentas);
                    if (oldIdVendedorOfVentasListNewVentas != null && !oldIdVendedorOfVentasListNewVentas.equals(usuarios)) {
                        oldIdVendedorOfVentasListNewVentas.getVentasList().remove(ventasListNewVentas);
                        oldIdVendedorOfVentasListNewVentas = em.merge(oldIdVendedorOfVentasListNewVentas);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = usuarios.getIdUsuario();
                if (findUsuarios(id) == null) {
                    throw new NonexistentEntityException("The usuarios with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuarios usuarios;
            try {
                usuarios = em.getReference(Usuarios.class, id);
                usuarios.getIdUsuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuarios with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Ventas> ventasListOrphanCheck = usuarios.getVentasList();
            for (Ventas ventasListOrphanCheckVentas : ventasListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuarios (" + usuarios + ") cannot be destroyed since the Ventas " + ventasListOrphanCheckVentas + " in its ventasList field has a non-nullable idVendedor field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Empleados idEmp = usuarios.getIdEmp();
            if (idEmp != null) {
                idEmp.setUsuarios(null);
                idEmp = em.merge(idEmp);
            }
            em.remove(usuarios);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuarios> findUsuariosEntities() {
        return findUsuariosEntities(true, -1, -1);
    }

    public List<Usuarios> findUsuariosEntities(int maxResults, int firstResult) {
        return findUsuariosEntities(false, maxResults, firstResult);
    }

    private List<Usuarios> findUsuariosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuarios.class));
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

    public Usuarios findUsuarios(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuarios.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuariosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuarios> rt = cq.from(Usuarios.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
