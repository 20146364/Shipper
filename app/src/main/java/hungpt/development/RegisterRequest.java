package hungpt.development;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hung Phan on 3/13/2017.
 */

public class RegisterRequest extends StringRequest {

    public static final String REGISTER_REQUEST_URL = "http://192.168.1.19/myshipper/register.php";
    private Map<String, String> params;

    public RegisterRequest( String username,String password, String hoten, String diachi, String quyen, String phone,
                            Response.Listener<String> listener){
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        params.put("hoten", hoten);
        params.put("diachi", diachi);
        params.put("phone", phone);
        params.put("quyen", quyen);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
