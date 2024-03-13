package tiendhph30203.poly.projectdatdoan.DonMua;

import java.io.Serializable;

public class GioHang implements Serializable {
    int masanpham;
    byte[] anhsanpham;

    public byte[] getAnhsanpham() {
        return anhsanpham;
    }

    public void setAnhsanpham(byte[] anhsanpham) {
        this.anhsanpham = anhsanpham;
    }

    public GioHang(int masanpham, byte[] anhsanpham, String linkanhsanpham, String tensanpham, int soluong, int giasanpham, int manguoidung) {
        this.masanpham = masanpham;
        this.anhsanpham = anhsanpham;
        this.linkanhsanpham = linkanhsanpham;
        this.tensanpham = tensanpham;
        this.soluong = soluong;
        this.giasanpham = giasanpham;
        this.manguoidung = manguoidung;
    }

    String linkanhsanpham;
    String tensanpham;
    int soluong;
    int giasanpham;
    int manguoidung;



    public GioHang() {
    }




    public int getMasanpham() {
        return masanpham;
    }

    public void setMasanpham(int masanpham) {
        this.masanpham = masanpham;
    }


    public String getTensanpham() {
        return tensanpham;
    }

    public void setTensanpham(String tensanpham) {
        this.tensanpham = tensanpham;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public int getGiasanpham() {
        return giasanpham;
    }

    public void setGiasanpham(int giasanpham) {
        this.giasanpham = giasanpham;
    }

    public int getManguoidung() {
        return manguoidung;
    }

    public void setManguoidung(int manguoidung) {
        this.manguoidung = manguoidung;
    }

    public String getLinkanhsanpham() {
        return linkanhsanpham;
    }

    public void setLinkanhsanpham(String linkanhsanpham) {
        this.linkanhsanpham = linkanhsanpham;
    }
}
