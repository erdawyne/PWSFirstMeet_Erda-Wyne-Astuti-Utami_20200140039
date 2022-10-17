/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package a.ws.invetaris;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author ERDA WYNE
 */
@Entity
@Table(name = "barang_masuk")
@NamedQueries({
    @NamedQuery(name = "BarangMasuk.findAll", query = "SELECT b FROM BarangMasuk b"),
    @NamedQuery(name = "BarangMasuk.findByNoMasukBarang", query = "SELECT b FROM BarangMasuk b WHERE b.noMasukBarang = :noMasukBarang"),
    @NamedQuery(name = "BarangMasuk.findByNamaBarang", query = "SELECT b FROM BarangMasuk b WHERE b.namaBarang = :namaBarang"),
    @NamedQuery(name = "BarangMasuk.findByTanggalMasuk", query = "SELECT b FROM BarangMasuk b WHERE b.tanggalMasuk = :tanggalMasuk"),
    @NamedQuery(name = "BarangMasuk.findByHargaSatuan", query = "SELECT b FROM BarangMasuk b WHERE b.hargaSatuan = :hargaSatuan"),
    @NamedQuery(name = "BarangMasuk.findByKuantitas", query = "SELECT b FROM BarangMasuk b WHERE b.kuantitas = :kuantitas"),
    @NamedQuery(name = "BarangMasuk.findByHargatotal", query = "SELECT b FROM BarangMasuk b WHERE b.hargatotal = :hargatotal")})
public class BarangMasuk implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "No_Masuk_Barang")
    private String noMasukBarang;
    @Column(name = "Nama_Barang")
    private String namaBarang;
    @Column(name = "Tanggal_Masuk")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tanggalMasuk;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Harga_Satuan")
    private BigDecimal hargaSatuan;
    @Column(name = "Kuantitas")
    private Integer kuantitas;
    @Column(name = "Harga_total")
    private BigDecimal hargatotal;
    @JoinColumn(name = "Kode_Barang", referencedColumnName = "Kode_Barang")
    @ManyToOne
    private Barang kodeBarang;

    public BarangMasuk() {
    }

    public BarangMasuk(String noMasukBarang) {
        this.noMasukBarang = noMasukBarang;
    }

    public String getNoMasukBarang() {
        return noMasukBarang;
    }

    public void setNoMasukBarang(String noMasukBarang) {
        this.noMasukBarang = noMasukBarang;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    public Date getTanggalMasuk() {
        return tanggalMasuk;
    }

    public void setTanggalMasuk(Date tanggalMasuk) {
        this.tanggalMasuk = tanggalMasuk;
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

    public Barang getKodeBarang() {
        return kodeBarang;
    }

    public void setKodeBarang(Barang kodeBarang) {
        this.kodeBarang = kodeBarang;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (noMasukBarang != null ? noMasukBarang.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BarangMasuk)) {
            return false;
        }
        BarangMasuk other = (BarangMasuk) object;
        if ((this.noMasukBarang == null && other.noMasukBarang != null) || (this.noMasukBarang != null && !this.noMasukBarang.equals(other.noMasukBarang))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "a.ws.invetaris.BarangMasuk[ noMasukBarang=" + noMasukBarang + " ]";
    }
    
}
