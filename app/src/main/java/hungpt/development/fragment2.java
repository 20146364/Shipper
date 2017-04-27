package hungpt.development;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Hung Phan on 3/29/2017.
 */

public class fragment2 extends Fragment {
    SharedPreferences sharedPreferences;
    UserSessionManager session;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bt_chudon, container, false);
        final Button sua = (Button) view.findViewById(R.id.btSua);
        final Button xoa = (Button) view.findViewById(R.id.btDelete);
        final Button them = (Button) view.findViewById(R.id.btThem);
        final Button cancel = (Button) view.findViewById(R.id.btCancel);
        //final TextView Mahh = (TextView) view.findViewById(R.id.tvMaHH);
        session = new UserSessionManager(getActivity().getApplicationContext());
        sharedPreferences = getActivity().getApplicationContext().getSharedPreferences("My app", 0);
        final String MaHH = sharedPreferences.getString("MaHH", null);
        final String sotk = sharedPreferences.getString("sotk", null);


        xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getActivity().getApplicationContext(), ChuDonActivity.class);
//                startActivity(intent);
            Toast.makeText(getActivity().getApplicationContext(),MaHH,Toast.LENGTH_LONG).show();
//                final String soTK= sotk.toString();
//                final String maHH = MaHH.toString();

//                Response.Listener<String>stringListener = new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//
//                        try {
//                            JSONObject jsonResponse = new JSONObject(response);
//                            Boolean success = jsonResponse.getBoolean("success");
//
//                            if(success){
////                                AlertDialog.Builder builder = new  AlertDialog.Builder(getActivity().getApplicationContext());
////                                builder.setMessage("Xóa thàng công")
////                                        .setNegativeButton("Ok", null)
////                                        .create()
////                                        .show();
//                                Intent intent = new Intent(getActivity().getApplicationContext(), ChuDonActivity.class);
//                                startActivity(intent);
//                            }
//                            else{
//                                AlertDialog.Builder builder = new  AlertDialog.Builder(getActivity().getApplicationContext());
//                                builder.setMessage("Đơn hàng không tồn tại")
//                                        .setNegativeButton("Retry", null)
//                                        .create()
//                                        .show();
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                   }
//                };
//                XoaDonHang registerRequest = new XoaDonHang( soTK, maHH, stringListener );
//                RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
//                queue.add(registerRequest);

          }
        });
 sua.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View v) {
         Intent intent = new Intent(getActivity().getApplicationContext(), SuaDonHang.class);
         startActivity(intent);
     }
 });
        them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), ThemDonHang.class);
                startActivity(intent);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), ChuDonActivity.class);
                startActivity(intent);
            }
        });


    return view;
    }



}
