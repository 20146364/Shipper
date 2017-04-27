package hungpt.development;

/**
 * Created by Hung Phan on 3/26/2017.
 */

public class DonHang {
    String SoTK;
    String Username;
    String MoTa;
    String VTN;
    String VTD;
    String ThoiGian;
    String MaHH;

    public DonHang(String soTK, String username, String moTa, String VTN, String VTD, String thoiGian, String maHH) {
        SoTK = soTK;
        Username = username;
        MoTa = moTa;
        this.VTN = VTN;
        this.VTD = VTD;
        ThoiGian = thoiGian;
        MaHH = maHH;
    }
}
