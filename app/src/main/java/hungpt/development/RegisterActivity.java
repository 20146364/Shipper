package hungpt.development;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText etUsername = (EditText)findViewById(R.id.etUsername);
        final EditText etPassword = (EditText)findViewById(R.id.etPass);
        final EditText etName = (EditText) findViewById(R.id.etName);
        final EditText etAddr = (EditText) findViewById(R.id.etAddr);
        final EditText etPhone = (EditText)findViewById(R.id.etPhone);
        final Spinner SpinnerList    = (Spinner)findViewById(R.id.spTL);
        final Button btRegister = (Button) findViewById(R.id.btRegister);
        final TextView loginLink = (TextView) findViewById(R.id.tvLoginHere);

        ArrayList<String> arrayTL =  new ArrayList<String>();
        arrayTL.add("Shipper");
        arrayTL.add("Chủ Đơn");

        ArrayAdapter arrayAdapter =  new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayTL);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerList.setAdapter(arrayAdapter);

       loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                RegisterActivity.this.startActivity(loginIntent);
            }
        });


        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();
                final String hoten = etName.getText().toString() ;
                final String diachi = etAddr.getText().toString();
                final String phone =    etPhone.getText().toString();
                final String quyen =    SpinnerList.getSelectedItem().toString();


                final int quyen1;
                if (quyen.equals("Shipper")) quyen1 = 1;
                else quyen1 = 0;
                Response.Listener<String>responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            Boolean success = jsonResponse.getBoolean("success");

                            if(success){
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                RegisterActivity.this.startActivity(intent);
                            }
                            else{
                                AlertDialog.Builder builder = new  AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("Register Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

               RegisterRequest registerRequest = new RegisterRequest( username,  password, hoten, diachi,  String.valueOf(quyen1), phone,responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
               queue.add(registerRequest);
            }
        });

    }
}
