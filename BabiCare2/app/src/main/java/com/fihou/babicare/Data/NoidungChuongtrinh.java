package com.fihou.babicare.Data;

/**
 * Created by TAM on 26/03/2017.
 */

public class NoidungChuongtrinh {
    private int id;
    private String tieudeamnhac;
    private String naoidungamnhac;
    private String tieudevanhoc;
    private String noidungvanhoc;
    private int idchuongtrinh;

    public NoidungChuongtrinh() {
    }

    public NoidungChuongtrinh(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTieudeamnhac() {
        return tieudeamnhac;
    }

    public void setTieudeamnhac(String tieudeamnhac) {
        this.tieudeamnhac = tieudeamnhac;
    }

    public String getNaoidungamnhac() {
        return naoidungamnhac;
    }

    public void setNaoidungamnhac(String naoidungamnhac) {
        this.naoidungamnhac = naoidungamnhac;
    }

    public String getTieudevanhoc() {
        return tieudevanhoc;
    }

    public void setTieudevanhoc(String tieudevanhoc) {
        this.tieudevanhoc = tieudevanhoc;
    }

    public String getNoidungvanhoc() {
        return noidungvanhoc;
    }

    public void setNoidungvanhoc(String noidungvanhoc) {
        this.noidungvanhoc = noidungvanhoc;
    }

    public int getIdchuongtrinh() {
        return idchuongtrinh;
    }

    public void setIdchuongtrinh(int idchuongtrinh) {
        this.idchuongtrinh = idchuongtrinh;
    }

}
