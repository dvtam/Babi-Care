package com.fihou.babicare.Data;

/**
 * Created by TAM on 20/03/2017.
 */

public class TypeUser {
    private int id;
    private String tenloai;

    public TypeUser() {
    }

    public TypeUser(int id, String tenloai) {
        this.id = id;
        this.tenloai = tenloai;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenloai() {
        return tenloai;
    }

    public void setTenloai(String tenloai) {
        this.tenloai = tenloai;
    }
}
