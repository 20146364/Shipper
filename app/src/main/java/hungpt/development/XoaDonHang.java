package hungpt.development;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hung Phan on 3/31/2017.
 */

public class XoaDonHang extends StringRequest {
    public static final String XOA_URL = "http://192.168.1.19/myshipper/XoaDonHang.php";
    private Map<String, String> params;

    public XoaDonHang(String soTK, String maHH, Response.Listener<String> listener){
        super(Method.POST, XOA_URL, listener, null);
        params = new HashMap<>();
        params.put("SoTK", soTK);
        params.put("MaHH", maHH);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
