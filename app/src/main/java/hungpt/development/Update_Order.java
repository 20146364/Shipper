package hungpt.development;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hung Phan on 3/30/2017.
 */

public class Update_Order extends StringRequest {
    public static final String UPDATE_ORDER_URL = "http://192.168.1.19/myshipper/ThemDonHang.php";
    private Map<String, String> params;

    public Update_Order( String sotk,String mota, String vtn, String vtd, String time,
                            Response.Listener<String> listener){
        super(Method.POST, UPDATE_ORDER_URL, listener, null);
        params = new HashMap<>();
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
