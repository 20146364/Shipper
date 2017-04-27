package hungpt.development;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hung Phan on 4/7/2017.
 */

public class NhanShip extends StringRequest {
    public static final String NHAN_SHIP_URL = "http://192.168.1.19/myshipper/nhanship.php";
    private Map<String, String> params;


    public NhanShip( String mAhh,String soTk, String tgnhan ,Response.Listener<String> listener){
        super(Method.POST, NHAN_SHIP_URL, listener, null);
        params = new HashMap<>();
        params.put("MaHH",mAhh );
        params.put("SoTKNhan", soTk);
        params.put("TGNhan", tgnhan);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
