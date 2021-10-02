/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Ricardo Avalos
 */
@Entity
@Table(name = "detalles_venta")
@NamedQueries({
    @NamedQuery(name = "DetallesVenta.findAll", query = "SELECT d FROM DetallesVenta d"),
    @NamedQuery(name = "DetallesVenta.findByNoVenta", query = "SELECT d FROM DetallesVenta d WHERE d.detallesVentaPK.noVenta = :noVenta"),
    @NamedQuery(name = "DetallesVenta.findByIdProducto", query = "SELECT d FROM DetallesVenta d WHERE d.detallesVentaPK.idProducto = :idProducto"),
    @NamedQuery(name = "DetallesVenta.findByPrecioUnitario", query = "SELECT d FROM DetallesVenta d WHERE d.precioUnitario = :precioUnitario"),
    @NamedQuery(name = "DetallesVenta.findByCantidad", query = "SELECT d FROM DetallesVenta d WHERE d.cantidad = :cantidad")})
public class DetallesVenta implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DetallesVentaPK detallesVentaPK;
    @Basic(optional = false)
    @Column(name = "precio_unitario")
    private float precioUnitario;
    @Basic(optional = false)
    @Column(name = "cantidad")
    private int cantidad;
    @JoinColumn(name = "NoVenta", referencedColumnName = "NoVenta", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Ventas ventas;
    @JoinColumn(name = "id_producto", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Productos productos;

    public DetallesVenta() {
    }

    public DetallesVenta(DetallesVentaPK detallesVentaPK) {
        this.detallesVentaPK = detallesVentaPK;
    }

    public DetallesVenta(DetallesVentaPK detallesVentaPK, float precioUnitario, int cantidad) {
        this.detallesVentaPK = detallesVentaPK;
        this.precioUnitario = precioUnitario;
        this.cantidad = cantidad;
    }

    public DetallesVenta(int noVenta, int idProducto) {
        this.detallesVentaPK = new DetallesVentaPK(noVenta, idProducto);
    }

    public DetallesVentaPK getDetallesVentaPK() {
        return detallesVentaPK;
    }

    public void setDetallesVentaPK(DetallesVentaPK detallesVentaPK) {
        this.detallesVentaPK = detallesVentaPK;
    }

    public float getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(float precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Ventas getVentas() {
        return ventas;
    }

    public void setVentas(Ventas ventas) {
        this.ventas = ventas;
    }

    public Productos getProductos() {
        return productos;
    }

    public void setProductos(Productos productos) {
        this.productos = productos;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (detallesVentaPK != null ? detallesVentaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetallesVenta)) {
            return false;
        }
        DetallesVenta other = (DetallesVenta) object;
        if ((this.detallesVentaPK == null && other.detallesVentaPK != null) || (this.detallesVentaPK != null && !this.detallesVentaPK.equals(other.detallesVentaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.DetallesVenta[ detallesVentaPK=" + detallesVentaPK + " ]";
    }
    
}
