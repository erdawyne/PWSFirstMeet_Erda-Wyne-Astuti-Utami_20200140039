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
@Table(name = "barang_keluar")
@NamedQueries({
    @NamedQuery(name = "BarangKeluar.findAll", query = "SELECT b FROM BarangKeluar b"),
    @NamedQuery(name = "BarangKeluar.findByNoKeluarBarang", query = "SELECT b FROM BarangKeluar b WHERE b.noKeluarBarang = :noKeluarBarang"),
    @NamedQuery(name = "BarangKeluar.findByNamaBarang", query = "SELECT b FROM BarangKeluar b WHERE b.namaBarang = :namaBarang"),
    @NamedQuery(name = "BarangKeluar.findByTanggalKeluar", query = "SELECT b FROM BarangKeluar b WHERE b.tanggalKeluar = :tanggalKeluar"),
    @NamedQuery(name = "BarangKeluar.findByHargaSatuan", query = "SELECT b FROM BarangKeluar b WHERE b.hargaSatuan = :hargaSatuan"),
    @NamedQuery(name = "BarangKeluar.findByKuantitas", query = "SELECT b FROM BarangKeluar b WHERE b.kuantitas = :kuantitas"),
    @NamedQuery(name = "BarangKeluar.findByHargatotal", query = "SELECT b FROM BarangKeluar b WHERE b.hargatotal = :hargatotal")})
public class BarangKeluar implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "No_Keluar_Barang")
    private String noKeluarBarang;
    @Column(name = "Nama_Barang")
    private String namaBarang;
    @Column(name = "Tanggal_Keluar")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tanggalKeluar;
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

    public BarangKeluar() {
    }

    public BarangKeluar(String noKeluarBarang) {
        this.noKeluarBarang = noKeluarBarang;
    }

    public String getNoKeluarBarang() {
        return noKeluarBarang;
    }

    public void setNoKeluarBarang(String noKeluarBarang) {
        this.noKeluarBarang = noKeluarBarang;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    public Date getTanggalKeluar() {
        return tanggalKeluar;
    }

    public void setTanggalKeluar(Date tanggalKeluar) {
        this.tanggalKeluar = tanggalKeluar;
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
        hash += (noKeluarBarang != null ? noKeluarBarang.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BarangKeluar)) {
            return false;
        }
        BarangKeluar other = (BarangKeluar) object;
        if ((this.noKeluarBarang == null && other.noKeluarBarang != null) || (this.noKeluarBarang != null && !this.noKeluarBarang.equals(other.noKeluarBarang))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "a.ws.invetaris.BarangKeluar[ noKeluarBarang=" + noKeluarBarang + " ]";
    }
    
}
