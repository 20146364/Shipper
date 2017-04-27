package hungpt.development;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hung Phan on 4/5/2017.
 */

public class Edit_DonHang extends StringRequest {
    public static final String UPDATE_ORDER_URL = "http://192.168.1.19/myshipper/SuaDonHang.php";
    private Map<String, String> params;

    public Edit_DonHang(String mahh, String sotk,String mota, String vtn, String vtd, String time,
                         Response.Listener<String> listener){
        super(Method.POST, UPDATE_ORDER_URL, listener, null);
        params = new HashMap<>();
        params.put("MaHH", mahh);
        params.put("SoTK", sotk);
        params.put("MoTa", mota);
        params.put("VTN", vtn);
        params.put("VTD", vtd);
        params.put("ThoiGian", time);


    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
