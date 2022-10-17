/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package a.ws.invetaris;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author ERDA WYNE
 */
@Entity
@Table(name = "pengurus")
@NamedQueries({
    @NamedQuery(name = "Pengurus.findAll", query = "SELECT p FROM Pengurus p"),
    @NamedQuery(name = "Pengurus.findByIdPengurusBarang", query = "SELECT p FROM Pengurus p WHERE p.idPengurusBarang = :idPengurusBarang"),
    @NamedQuery(name = "Pengurus.findByNama", query = "SELECT p FROM Pengurus p WHERE p.nama = :nama"),
    @NamedQuery(name = "Pengurus.findByJenisKelamin", query = "SELECT p FROM Pengurus p WHERE p.jenisKelamin = :jenisKelamin"),
    @NamedQuery(name = "Pengurus.findByNotelepon", query = "SELECT p FROM Pengurus p WHERE p.notelepon = :notelepon")})
public class Pengurus implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Id_Pengurus_Barang")
    private Integer idPengurusBarang;
    @Column(name = "Nama")
    private String nama;
    @Column(name = "Jenis_Kelamin")
    private Character jenisKelamin;
    @Column(name = "No_telepon")
    private String notelepon;
    @OneToMany(mappedBy = "idPengurusBarang")
    private Collection<Barang> barangCollection;

    public Pengurus() {
    }

    public Pengurus(Integer idPengurusBarang) {
        this.idPengurusBarang = idPengurusBarang;
    }

    public Integer getIdPengurusBarang() {
        return idPengurusBarang;
    }

    public void setIdPengurusBarang(Integer idPengurusBarang) {
        this.idPengurusBarang = idPengurusBarang;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public Character getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(Character jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getNotelepon() {
        return notelepon;
    }

    public void setNotelepon(String notelepon) {
        this.notelepon = notelepon;
    }

    public Collection<Barang> getBarangCollection() {
        return barangCollection;
    }

    public void setBarangCollection(Collection<Barang> barangCollection) {
        this.barangCollection = barangCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPengurusBarang != null ? idPengurusBarang.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pengurus)) {
            return false;
        }
        Pengurus other = (Pengurus) object;
        if ((this.idPengurusBarang == null && other.idPengurusBarang != null) || (this.idPengurusBarang != null && !this.idPengurusBarang.equals(other.idPengurusBarang))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "a.ws.invetaris.Pengurus[ idPengurusBarang=" + idPengurusBarang + " ]";
    }
    
}
