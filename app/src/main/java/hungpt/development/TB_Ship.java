package hungpt.development;

/**
 * Created by Hung Phan on 4/10/2017.
 */

public class TB_Ship {
    String SoTK;
    String SoTKNhan;
    String MaHH;
    String Username;
    String MoTa;
    String VTN;
    String VTD;
    String ThoiGian;
    String TTDon;
    String TGNhan;
    String LuotXemShip;

    public TB_Ship(String soTK, String soTKNhan, String maHH, String username, String moTa, String VTN, String VTD, String thoiGian, String TTDon, String TGNhan, String luotXemShip) {
        SoTK = soTK;
        SoTKNhan = soTKNhan;
        MaHH = maHH;
        Username = username;
        MoTa = moTa;
        this.VTN = VTN;
        this.VTD = VTD;
        ThoiGian = thoiGian;
        this.TTDon = TTDon;
        this.TGNhan = TGNhan;
        LuotXemShip = luotXemShip;
    }
}
