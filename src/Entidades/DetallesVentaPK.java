/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Ricardo Avalos
 */
@Embeddable
public class DetallesVentaPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "NoVenta")
    private int noVenta;
    @Basic(optional = false)
    @Column(name = "id_producto")
    private int idProducto;

    public DetallesVentaPK() {
    }

    public DetallesVentaPK(int noVenta, int idProducto) {
        this.noVenta = noVenta;
        this.idProducto = idProducto;
    }

    public int getNoVenta() {
        return noVenta;
    }

    public void setNoVenta(int noVenta) {
        this.noVenta = noVenta;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) noVenta;
        hash += (int) idProducto;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetallesVentaPK)) {
            return false;
        }
        DetallesVentaPK other = (DetallesVentaPK) object;
        if (this.noVenta != other.noVenta) {
            return false;
        }
        if (this.idProducto != other.idProducto) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.DetallesVentaPK[ noVenta=" + noVenta + ", idProducto=" + idProducto + " ]";
    }
    
}
