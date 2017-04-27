package hungpt.development;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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


/**
 * Created by Hung Phan on 3/23/2017.
 */

public class Acount_Activity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView navigationView;
    UserSessionManager session;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acount);


        final TextView id = (TextView) findViewById(R.id.id);
        final TextView name = (TextView) findViewById(R.id.etName);
        final TextView pass = (TextView) findViewById(R.id.etPass);
        final TextView addr = (TextView) findViewById(R.id.etAddr);
        final EditText etphone = (EditText) findViewById(R.id.etPhone);
        final Button save = (Button) findViewById(R.id.btSave);

        //Season ghi nhớ đăng nhập
        session = new UserSessionManager(getApplicationContext());
        sharedPreferences = getApplicationContext().getSharedPreferences("My app", 0);
        final String ten = sharedPreferences.getString("username", null);
        final String phone1 = sharedPreferences.getString("phone", null);
        final String name1 = sharedPreferences.getString("hoten", null);
        final String addr1 = sharedPreferences.getString("diachi", null);
        final String pass1 = sharedPreferences.getString("password", null);
        final String quyen = sharedPreferences.getString("quyen", null);
        final String sotk = sharedPreferences.getString("sotk", null);


        id.setText(ten);
        etphone.setText(phone1);
        name.setText(name1);
        addr.setText(addr1);
        pass.setText(pass1);


        // tạo thanh toolbar
        final Toolbar toolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(toolbar);

        // Tạo navigation menu

            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
            mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        //else {
//            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout1);
//            mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        //}


        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView = (NavigationView) findViewById(R.id.navigation_layout);

        //Set click item cho navigation menu
        navigationView = (NavigationView) findViewById(R.id.navigation_layout);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                if (menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
                        if(quyen.equals("1")){
                        Intent intent =new Intent(getApplicationContext(), Shipper_Activity.class);
                        startActivity(intent);}
                        else {
                            Intent intent =new Intent(getApplicationContext(), ChuDonActivity.class);
                            startActivity(intent);
                        }
                        return true;
                    case R.id.nav_logout:
                        session.logoutUser();
                        return true;
                    case R.id.nav_acount:
                        return true;
                    default:
                        Toast.makeText(getApplicationContext(), "Somethings Wrong", Toast.LENGTH_SHORT).show();
                        return true;

                }
            }
        });

        //Set click Button Save
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final  String username =  id.getText().toString();
                final  String hoten = name.getText().toString();
                final String password = pass.getText().toString();
                final  String diachi = addr.getText().toString();
                final String phone = etphone.getText().toString();

                Response.Listener<String>responseListener = new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            Boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                AlertDialog.Builder builder = new  AlertDialog.Builder(Acount_Activity.this);
                                builder.setMessage("Cập nhật thành công")
                                        .setNegativeButton("Ok", null)
                                        .create()
                                        .show();

                            }
                            else {
                                AlertDialog.Builder builder = new  AlertDialog.Builder(Acount_Activity.this);
                                builder.setMessage("Có Lỗi Xảy ra")
                                        .setNegativeButton("Ok", null)
                                        .create()
                                        .show();
                            }

                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }

                    }
                };
                Update_User update_user = new Update_User(username, password, hoten, diachi, phone,  responseListener);
                RequestQueue queue = Volley.newRequestQueue(Acount_Activity.this);
                queue.add(update_user);
            }
        });

   }

   //tạo option menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflate = getMenuInflater();
        inflate.inflate(R.menu.option_menu, menu);
        return true;
    }

    //set click item
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.Acount:
                Intent intentt1 = new Intent(Acount_Activity.this, Acount_Activity.class);
                Acount_Activity.this.startActivity(intentt1);
                return true;
            case R.id.Setting:
                return true;
            case R.id.logout:
                session.logoutUser();
                return true;
        }


        //set click icon menu navigation menu
        if (mToggle.onOptionsItemSelected(item)) {
            final TextView hello = (TextView) findViewById(R.id.hello);
            session = new UserSessionManager(getApplicationContext());
            sharedPreferences = getApplicationContext().getSharedPreferences("My app", 0);
            final String ten = sharedPreferences.getString("username", null);
            hello.setText("Hello" + " " + ten);
            return true;


        }
        return super.onOptionsItemSelected(item);

    }
}