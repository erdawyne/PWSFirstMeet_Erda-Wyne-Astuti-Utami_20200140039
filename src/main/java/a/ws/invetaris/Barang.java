/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package a.ws.invetaris;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author ERDA WYNE
 */
@Entity
@Table(name = "barang")
@NamedQueries({
    @NamedQuery(name = "Barang.findAll", query = "SELECT b FROM Barang b"),
    @NamedQuery(name = "Barang.findByKodeBarang", query = "SELECT b FROM Barang b WHERE b.kodeBarang = :kodeBarang"),
    @NamedQuery(name = "Barang.findByNamaBarang", query = "SELECT b FROM Barang b WHERE b.namaBarang = :namaBarang"),
    @NamedQuery(name = "Barang.findByKeadaan", query = "SELECT b FROM Barang b WHERE b.keadaan = :keadaan"),
    @NamedQuery(name = "Barang.findByKeterangan", query = "SELECT b FROM Barang b WHERE b.keterangan = :keterangan"),
    @NamedQuery(name = "Barang.findByHargaSatuan", query = "SELECT b FROM Barang b WHERE b.hargaSatuan = :hargaSatuan"),
    @NamedQuery(name = "Barang.findByKuantitas", query = "SELECT b FROM Barang b WHERE b.kuantitas = :kuantitas"),
    @NamedQuery(name = "Barang.findByHargatotal", query = "SELECT b FROM Barang b WHERE b.hargatotal = :hargatotal")})
public class Barang implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Kode_Barang")
    private String kodeBarang;
    @Column(name = "Nama_Barang")
    private String namaBarang;
    @Column(name = "Keadaan")
    private String keadaan;
    @Column(name = "Keterangan")
    private String keterangan;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Harga_Satuan")
    private BigDecimal hargaSatuan;
    @Column(name = "Kuantitas")
    private Integer kuantitas;
    @Column(name = "Harga_total")
    private BigDecimal hargatotal;
    @JoinColumn(name = "Id_Pengurus_Barang", referencedColumnName = "Id_Pengurus_Barang")
    @ManyToOne
    private Pengurus idPengurusBarang;
    @OneToMany(mappedBy = "kodeBarang")
    private Collection<BarangKeluar> barangKeluarCollection;
    @OneToMany(mappedBy = "kodeBarang")
    private Collection<BarangMasuk> barangMasukCollection;

    public Barang() {
    }

    public Barang(String kodeBarang) {
        this.kodeBarang = kodeBarang;
    }

    public String getKodeBarang() {
        return kodeBarang;
    }

    public void setKodeBarang(String kodeBarang) {
        this.kodeBarang = kodeBarang;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    public String getKeadaan() {
        return keadaan;
    }

    public void setKeadaan(String keadaan) {
        this.keadaan = keadaan;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public BigDecimal getHargaSatuan() {
        return hargaSatuan;
    }

    public void setHargaSatuan(BigDecimal hargaSatuan) {
        this.hargaSatuan = hargaSatuan;
    }

    public Integer getKuantitas() {
        return kuantitas;
    }

    public void setKuantitas(Integer kuantitas) {
        this.kuantitas = kuantitas;
    }

    public BigDecimal getHargatotal() {
        return hargatotal;
    }

    public void setHargatotal(BigDecimal hargatotal) {
        this.hargatotal = hargatotal;
    }

    public Pengurus getIdPengurusBarang() {
        return idPengurusBarang;
    }

    public void setIdPengurusBarang(Pengurus idPengurusBarang) {
        this.idPengurusBarang = idPengurusBarang;
    }

    public Collection<BarangKeluar> getBarangKeluarCollection() {
        return barangKeluarCollection;
    }

    public void setBarangKeluarCollection(Collection<BarangKeluar> barangKeluarCollection) {
        this.barangKeluarCollection = barangKeluarCollection;
    }

    public Collection<BarangMasuk> getBarangMasukCollection() {
        return barangMasukCollection;
    }

    public void setBarangMasukCollection(Collection<BarangMasuk> barangMasukCollection) {
        this.barangMasukCollection = barangMasukCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kodeBarang != null ? kodeBarang.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Barang)) {
            return false;
        }
        Barang other = (Barang) object;
        if ((this.kodeBarang == null && other.kodeBarang != null) || (this.kodeBarang != null && !this.kodeBarang.equals(other.kodeBarang))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "a.ws.invetaris.Barang[ kodeBarang=" + kodeBarang + " ]";
    }
    
}
