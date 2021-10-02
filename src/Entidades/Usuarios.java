/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Ricardo Avalos
 */
@Entity
@Table(name = "usuarios")
@NamedQueries({
    @NamedQuery(name = "Usuarios.findAll", query = "SELECT u FROM Usuarios u"),
    @NamedQuery(name = "Usuarios.findByIdUsuario", query = "SELECT u FROM Usuarios u WHERE u.idUsuario = :idUsuario"),
    @NamedQuery(name = "Usuarios.findByContra", query = "SELECT u FROM Usuarios u WHERE u.contra = :contra"),
    @NamedQuery(name = "Usuarios.findByNivel", query = "SELECT u FROM Usuarios u WHERE u.nivel = :nivel")})
public class Usuarios implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idVendedor")
    private List<Ventas> ventasList;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_usuario")
    private String idUsuario;
    @Basic(optional = false)
    @Column(name = "contra")
    private String contra;
    @Basic(optional = false)
    @Column(name = "nivel")
    private int nivel;
    @JoinColumn(name = "id_emp", referencedColumnName = "ID")
    @OneToOne(optional = false)
    private Empleados idEmp;

    public Usuarios() {
    }

    public Usuarios(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Usuarios(String idUsuario, String contra, int nivel) {
        this.idUsuario = idUsuario;
        this.contra = contra;
        this.nivel = nivel;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getContra() {
        return contra;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public Empleados getIdEmp() {
        return idEmp;
    }

    public void setIdEmp(Empleados idEmp) {
        this.idEmp = idEmp;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUsuario != null ? idUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuarios)) {
            return false;
        }
        Usuarios other = (Usuarios) object;
        if ((this.idUsuario == null && other.idUsuario != null) || (this.idUsuario != null && !this.idUsuario.equals(other.idUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Usuarios[ idUsuario=" + idUsuario + " ]";
    }

    public List<Ventas> getVentasList() {
        return ventasList;
    }

    public void setVentasList(List<Ventas> ventasList) {
        this.ventasList = ventasList;
    }
    
}
