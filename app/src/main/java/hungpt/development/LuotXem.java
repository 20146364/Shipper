package hungpt.development;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hung Phan on 4/11/2017.
 */

public class LuotXem extends StringRequest{
    public static final String LUOT_XEM_URL = "http://192.168.1.19/myshipper/LuotXem.php";
    private Map<String, String> params;

    public LuotXem(String MaHh, String soTKNhan, Response.Listener<String> listener){
        super(Request.Method.POST, LUOT_XEM_URL, listener, null);
        params = new HashMap<>();
        params.put("MaHH", MaHh);
        params.put("SoTKNhan", soTKNhan);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}