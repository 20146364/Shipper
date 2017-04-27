package hungpt.development;

/**
 * Created by Hung Phan on 4/18/2017.
 */

public class DaNhan {
    String SoTK;
    String SoTKNhan;
    String MaHH;
    String Username;
    String MoTa;
    String VTN;
    String VTD;
    String ThoiGian;
    String TTDon;

    public DaNhan(String soTK, String soTKNhan, String maHH, String username, String moTa, String VTN, String VTD, String thoiGian, String TTDon) {
        SoTK = soTK;
        SoTKNhan = soTKNhan;
        MaHH = maHH;
        Username = username;
        MoTa = moTa;
        this.VTN = VTN;
        this.VTD = VTD;
        ThoiGian = thoiGian;
        this.TTDon = TTDon;
    }
}
