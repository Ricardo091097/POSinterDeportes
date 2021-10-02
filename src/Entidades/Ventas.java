/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Ricardo Avalos
 */
@Entity
@Table(name = "ventas")
@NamedQueries({
    @NamedQuery(name = "Ventas.findAll", query = "SELECT v FROM Ventas v"),
    @NamedQuery(name = "Ventas.findByNoVenta", query = "SELECT v FROM Ventas v WHERE v.noVenta = :noVenta"),
    @NamedQuery(name = "Ventas.findByFecha", query = "SELECT v FROM Ventas v WHERE v.fecha = :fecha"),
    @NamedQuery(name = "Ventas.countAllTotal", query = "SELECT SUM(v.total) FROM Ventas v"),
    @NamedQuery(name = "Ventas.findByTotal", query = "SELECT v FROM Ventas v WHERE v.total = :total")})
public class Ventas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "NoVenta")
    private Integer noVenta;
    @Basic(optional = false)
    @Column(name = "Fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Basic(optional = false)
    @Column(name = "Total")
    private float total;
    @JoinColumn(name = "id_vendedor", referencedColumnName = "id_usuario")
    @ManyToOne(optional = false)
    private Usuarios idVendedor;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ventas")
    private List<DetallesVenta> detallesVentaList;

    public Ventas() {
    }

    public Ventas(Integer noVenta) {
        this.noVenta = noVenta;
    }

    public Ventas(Integer noVenta, Date fecha, float total) {
        this.noVenta = noVenta;
        this.fecha = fecha;
        this.total = total;
    }

    public Integer getNoVenta() {
        return noVenta;
    }

    public void setNoVenta(Integer noVenta) {
        this.noVenta = noVenta;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public Usuarios getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(Usuarios idVendedor) {
        this.idVendedor = idVendedor;
    }

    public List<DetallesVenta> getDetallesVentaList() {
        return detallesVentaList;
    }

    public void setDetallesVentaList(List<DetallesVenta> detallesVentaList) {
        this.detallesVentaList = detallesVentaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (noVenta != null ? noVenta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ventas)) {
            return false;
        }
        Ventas other = (Ventas) object;
        if ((this.noVenta == null && other.noVenta != null) || (this.noVenta != null && !this.noVenta.equals(other.noVenta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Ventas[ noVenta=" + noVenta + " ]";
    }
    
}
