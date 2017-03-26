package hungpt.development;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hung Phan on 3/24/2017.
 */

public class Update_User extends StringRequest{
    public static final String UPDATE_REQUEST_URL = "http://192.168.1.52/myshipper/update_user.php";
    private Map<String, String> params;

    public Update_User(String username, String password, String hoten, String diachi, String phone, Response.Listener<String> listener){
        super(Request.Method.POST, UPDATE_REQUEST_URL, listener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        params = new HashMap<>();
        params.put("username", username);
        params.put("hoten", hoten);
        params.put("password", password);
        params.put("diachi", diachi);
        params.put("phone", phone);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
