package hungpt.development;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Hung Phan on 3/29/2017.
 */

public class fragment1 extends Fragment {
    SharedPreferences sharedPreferences;
    UserSessionManager session;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bt_shipper, container, false);
        session = new UserSessionManager(getActivity().getApplicationContext());
        sharedPreferences = getActivity().getApplicationContext().getSharedPreferences("My app", 0);
        final String MaHH = sharedPreferences.getString("MaHH", null);
        final String sotk = sharedPreferences.getString("sotk", null);

        final Button huy = (Button) view.findViewById(R.id.btHuy);
        final  Button nhan = (Button) view.findViewById(R.id.btNhan);
        huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), Shipper_Activity.class);
                startActivity(intent);
            }
        });

        nhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final  String mAhh = MaHH.toString();
                final String soTk = sotk.toString();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
                Date date = new Date();
                final String tgnhan = dateFormat.format(date);
                Response.Listener<String>responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            Boolean success = jsonResponse.getBoolean("success");

                            if(success){
                                Intent intent = new Intent(getActivity().getApplicationContext(), Shipper_Activity.class);
                                startActivity(intent);
                            }
                            else{
                                AlertDialog.Builder builder = new  AlertDialog.Builder(getActivity().getApplicationContext());
                                builder.setMessage("Dơn Hàng Không Tồn Tại")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                NhanShip nhanShip = new NhanShip( mAhh, soTk, tgnhan,responseListener);
                RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
                queue.add(nhanShip);
            }
        });

        return  view;
    }
}
