package hungpt.development;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hung Phan on 4/11/2017.
 */

public class NhanShiper extends StringRequest {
    public static final String CHECK_SHIP_URL = "http://192.168.1.19/myshipper/CheckShip.php";
    private Map<String, String> params;


    public NhanShiper( String soTknhan, String MAHH, Response.Listener<String> listener){
        super(Method.POST, CHECK_SHIP_URL, listener, null);
        params = new HashMap<>();
        params.put("SoTKNhan", soTknhan);
        params.put("MaHH",MAHH);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
