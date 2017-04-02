package com.fihou.babicare.Data;

import java.util.ArrayList;

/**
 * Created by TAM on 26/03/2017.
 */

public class Chuongtrinh {
    private int id;
    private String chudiem;
    private String chude;
    private String ngaybatdau;
    private String ngayketthuc;
    private int idnguoidung;
    private ArrayList<ChitietChuongtrinh> chitietchuongtrinh;


    public Chuongtrinh() {

    }

    public Chuongtrinh(int id, String chudiem, String chude, String ngaybatdau, String ngayketthuc, int idnguoidung) {
        this.id = id;
        this.chudiem = chudiem;
        this.chude = chude;
        this.ngaybatdau = ngaybatdau;
        this.ngayketthuc = ngayketthuc;
        this.idnguoidung = idnguoidung;
    }

    public Chuongtrinh(int id, String chudiem, String chude, String ngaybatdau, String ngayketthuc, int idnguoidung, ArrayList<ChitietChuongtrinh> chitietchuongtrinh) {
        this.id = id;
        this.chudiem = chudiem;
        this.chude = chude;
        this.ngaybatdau = ngaybatdau;
        this.ngayketthuc = ngayketthuc;
        this.idnguoidung = idnguoidung;
        this.chitietchuongtrinh = chitietchuongtrinh;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getChudiem() {
        return chudiem;
    }

    public void setChudiem(String chudiem) {
        this.chudiem = chudiem;
    }

    public String getChude() {
        return chude;
    }

    public void setChude(String chude) {
        this.chude = chude;
    }

    public String getNgaybatdau() {
        return ngaybatdau;
    }

    public void setNgaybatdau(String ngaybatdau) {
        this.ngaybatdau = ngaybatdau;
    }

    public String getNgayketthuc() {
        return ngayketthuc;
    }

    public void setNgayketthuc(String ngayketthuc) {
        this.ngayketthuc = ngayketthuc;
    }

    public int getIdnguoidung() {
        return idnguoidung;
    }

    public void setIdnguoidung(int idnguoidung) {
        this.idnguoidung = idnguoidung;
    }

    public ArrayList<ChitietChuongtrinh> getChitietchuongtrinh() {
        return chitietchuongtrinh;
    }

    public void setChitietchuongtrinh(ArrayList<ChitietChuongtrinh> chitietchuongtrinh) {
        this.chitietchuongtrinh = chitietchuongtrinh;
    }

}
