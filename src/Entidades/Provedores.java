/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Ricardo Avalos
 */
@Entity
@Table(name = "provedores")
@NamedQueries({
    @NamedQuery(name = "Provedores.findAll", query = "SELECT p FROM Provedores p"),
    @NamedQuery(name = "Provedores.findById", query = "SELECT p FROM Provedores p WHERE p.id = :id"),
    @NamedQuery(name = "Provedores.findByNombre", query = "SELECT p FROM Provedores p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "Provedores.findByTelefono", query = "SELECT p FROM Provedores p WHERE p.telefono = :telefono"),
    @NamedQuery(name = "Provedores.findByCorreo", query = "SELECT p FROM Provedores p WHERE p.correo = :correo"),
    @NamedQuery(name = "Provedores.findByNombrecontacto", query = "SELECT p FROM Provedores p WHERE p.nombrecontacto = :nombrecontacto")})
public class Provedores implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "Nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "Telefono")
    private String telefono;
    @Basic(optional = false)
    @Column(name = "Correo")
    private String correo;
    @Basic(optional = false)
    @Column(name = "Nombre_contacto")
    private String nombrecontacto;

    public Provedores() {
    }

    public Provedores(Integer id) {
        this.id = id;
    }

    public Provedores(Integer id, String nombre, String telefono, String correo, String nombrecontacto) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.correo = correo;
        this.nombrecontacto = nombrecontacto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombrecontacto() {
        return nombrecontacto;
    }

    public void setNombrecontacto(String nombrecontacto) {
        this.nombrecontacto = nombrecontacto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Provedores)) {
            return false;
        }
        Provedores other = (Provedores) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Provedores[ id=" + id + " ]";
    }
    
}
