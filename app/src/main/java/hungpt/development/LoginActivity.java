package hungpt.development;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    UserSessionManager session;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        session = new UserSessionManager(getApplicationContext());

        sharedPreferences = getApplicationContext().getSharedPreferences("My app",0);

        final EditText etUsername = (EditText)findViewById(R.id.etUsername);
        final EditText etPassword = (EditText)findViewById(R.id.etPass);

        final Button btLogin = (Button) findViewById(R.id.btLogin);
        final TextView registerLink = (TextView) findViewById(R.id.tvRegisterHere);

        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent =new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();
                //Toast.makeText(LoginActivity.this, "Loi", Toast.LENGTH_LONG).show();
                Response.Listener<String>responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject jsonResponse = new JSONObject(response);
                            Boolean success = jsonResponse.getBoolean("success");

                            if(success){
                                String phone = jsonResponse.getString("phone");
                                String quyen = jsonResponse.getString("quyen");
                                String hoten = jsonResponse.getString("hoten");
                                String diachi = jsonResponse.getString("diachi");

                                if(quyen.equals("1")) {

                                    Intent intent =new Intent(getApplicationContext(), Shipper_Activity.class);

                                    SharedPreferences.Editor edit= sharedPreferences.edit();
                                    edit.putString("username", username );
                                    edit.putString("password", password);
                                    edit.putString("hoten", hoten);
                                    edit.putString("diachi", diachi);
                                    edit.putString("phone", phone);
                                    edit.putString("quyen", quyen);

                                    edit.commit();

                                    startActivity(intent);


                                }
                                else{
                                    Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                                    LoginActivity.this.startActivity(intent);
                                }


                            }
                            else{
                                AlertDialog.Builder builder = new  AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("Login Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                LoginRequest loginRequest = new LoginRequest(username, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });

    }


}
