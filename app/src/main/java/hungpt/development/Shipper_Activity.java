package hungpt.development;

import android.*;
import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;


public class Shipper_Activity extends AppCompatActivity implements OnMapReadyCallback, DirectionFinderListener, GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView navigationView;
    UserSessionManager session;
    SharedPreferences sharedPreferences;
    private GoogleMap mMap;
    private Button btnFindPath;
    private EditText etOrigin;
    private EditText etDestination;
    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    private ProgressDialog progressDialog;


    private static final String LOG_TAG = "SuaDonHang";
    private static final int GOOGLE_API_CLIENT_ID = 0;
    private AutoCompleteTextView mAutocompleteTextView, mAutocompleteTextView2;
    private GoogleApiClient mGoogleApiClient;
    private PlaceArrayAdapter mPlaceArrayAdapter;
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(21.028148, 105.836963), new LatLng(21.028148, 106.836963));

    ListView lvDonHang, lvDaNhan, lvTBShip;
    ArrayList<DonHang> mangDonHang;
    ArrayList<DaNhan> mangDaNhan;
    ArrayList<TB_Ship> mangTB_Ship;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipper);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(this, GOOGLE_API_CLIENT_ID, this)
                .addConnectionCallbacks(this)
                .build();
        mAutocompleteTextView = (AutoCompleteTextView) findViewById(R.id
                .etOrigin);
        mAutocompleteTextView.setThreshold(3);
        mAutocompleteTextView.setOnItemClickListener(mAutocompleteClickListener);
        mPlaceArrayAdapter = new PlaceArrayAdapter(this, android.R.layout.simple_list_item_1,
                BOUNDS_MOUNTAIN_VIEW, null);
        mAutocompleteTextView.setAdapter(mPlaceArrayAdapter);

        mAutocompleteTextView2 = (AutoCompleteTextView) findViewById(R.id
                .etDestination);
        mAutocompleteTextView2.setThreshold(3);
        mAutocompleteTextView2.setOnItemClickListener(mAutocompleteClickListener);
        mAutocompleteTextView2.setAdapter(mPlaceArrayAdapter);


        btnFindPath = (Button) findViewById(R.id.btnFindPath);
        etOrigin = (AutoCompleteTextView) findViewById(R.id.etOrigin);
        etDestination = (AutoCompleteTextView) findViewById(R.id.etDestination);

        btnFindPath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest();
            }
        });

        lvDonHang = (ListView) findViewById(R.id.lvDonHang);
        lvDaNhan = (ListView) findViewById(R.id.lvDonHang1);
        lvTBShip = (ListView) findViewById(R.id.lvThongBao);
        mangDonHang = new ArrayList<DonHang>();
        mangDaNhan = new ArrayList<DaNhan>();
        mangTB_Ship = new ArrayList<TB_Ship>();
        session = new UserSessionManager(getApplicationContext());

        sharedPreferences = getApplicationContext().getSharedPreferences("My app", 0);

        lvDonHang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String username = mangDonHang.get(position).Username;
                final String mahh = mangDonHang.get(position).MaHH;
                final String mota = mangDonHang.get(position).MoTa;
                final String vtn = mangDonHang.get(position).VTN;
                final String vtd = mangDonHang.get(position).VTD;
                final String time = mangDonHang.get(position).ThoiGian;
                Intent intent = new Intent(getApplicationContext(), ChiTietDon.class);
                SharedPreferences.Editor edit = sharedPreferences.edit();
                edit.putString("Username", username);
                edit.putString("MaHH", mahh);
                edit.putString("MoTa", mota);
                edit.putString("VTN", vtn);
                edit.putString("VTD", vtd);
                edit.putString("ThoiGian", time);

                edit.commit();
                Shipper_Activity.this.startActivity(intent);
            }
        });

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new docJASON().execute("http://192.168.1.19/myshipper/db_DonHang.php");
                new docJASON1().execute("http://192.168.1.19/myshipper/DaNhan.php");
                new docJASON3().execute("http://192.168.1.19/myshipper/DaNhan.php");
            }
        });
//        mangDonHang.add(new DonHang("hungpt", "Mo ta", "Ha Noi", "20/11/2017"));


        //tạo Toolbar
        final Toolbar toolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(toolbar);
        //tạo nav menu
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //set click item nav menu
        navigationView = (NavigationView) findViewById(R.id.navigation_layout);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                if (menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
                        Intent intent = new Intent(getApplicationContext(), Shipper_Activity.class);

                        //SharedPreferences.Editor edit= sharedPreferences.edit();

                        startActivity(intent);
                        return true;
                    case R.id.nav_logout:
                        session.logoutUser();
                        return true;
                    default:
                        Toast.makeText(getApplicationContext(), "Somethings Wrong", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.nav_acount:
                        Intent intentt1 = new Intent(Shipper_Activity.this, Acount_Activity.class);
                        Shipper_Activity.this.startActivity(intentt1);
                        return true;

                }
            }
        });
        //Tạo thanh TabHost
        TabHost tabHost = (TabHost) findViewById(R.id.TabHost);
        tabHost.setup();
        TabHost.TabSpec spe1 = tabHost.newTabSpec("TAB 1");
        spe1.setIndicator("TAB 1");
        spe1.setContent(R.id.layout1);
        spe1.setIndicator("", getResources().getDrawable(R.mipmap.ic_home_black_24dp));
        tabHost.addTab(spe1);
        //load listview cho trang dơn hàng


        //Tạo thanh TabHost
        TabHost.TabSpec spe2 = tabHost.newTabSpec("TAB 2");
        spe2.setIndicator("TAB 2");
        spe2.setContent(R.id.layout2);
        spe2.setIndicator("", getResources().getDrawable(R.mipmap.ic_note_add_black_24dp));
        tabHost.addTab(spe2);

        //Tạo thanh TabHost
        TabHost.TabSpec spe3 = tabHost.newTabSpec("TAB 3");
        spe3.setIndicator("TAB 3");
        spe3.setIndicator("", getResources().getDrawable(R.mipmap.ic_message_black_24dp));
        spe3.setContent(R.id.layout3);
        tabHost.addTab(spe3);

        //Tạo thanh TabHost
        TabHost.TabSpec spe4 = tabHost.newTabSpec("TAB 4");
        spe4.setIndicator("TAB 4");
        spe4.setContent(R.id.layout4);
        spe4.setIndicator("", getResources().getDrawable(R.mipmap.ic_notifications_black_24dp));
        tabHost.addTab(spe4);


        //Tạo thanh TabHost
        TabHost.TabSpec spe5 = tabHost.newTabSpec("TAB 5");
        spe5.setIndicator("TAB 5");
        spe5.setContent(R.id.layout5);
        spe5.setIndicator("", getResources().getDrawable(R.drawable.ic_map_black_24dp));
        tabHost.addTab(spe5);
    }

    private void sendRequest() {
        String origin = etOrigin.getText().toString();
        String destination = etDestination.getText().toString();
        if (origin.isEmpty()) {
            Toast.makeText(this, "Bạn Chưa Chọn Vị Trí Xuất Phát!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (destination.isEmpty()) {
            Toast.makeText(this, "Bạn chưa chọn vị trí đến!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            new DirectionFinder(this, origin, destination).execute();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 333: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //mMap.setMyLocationEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        LatLng BKA = new LatLng(21.007082, 105.842896);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(BKA, 14));
        originMarkers.add(mMap.addMarker(new MarkerOptions()
                .title("Đại học Bách Khoa Hà Nội")
                .position(BKA)));
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 333);
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
    }


    @Override
    public void onDirectionFinderStart() {
        progressDialog = ProgressDialog.show(this, "Please wait.",
                "Đang tìm kiếm..!", true);

        if (originMarkers != null) {
            for (Marker marker : originMarkers) {
                marker.remove();
            }
        }

        if (destinationMarkers != null) {
            for (Marker marker : destinationMarkers) {
                marker.remove();
            }
        }

        if (polylinePaths != null) {
            for (Polyline polyline:polylinePaths ) {
                polyline.remove();
            }
        }
    }

    @Override
    public void onDirectionFinderSuccess(List<Route> routes) {
        progressDialog.dismiss();
        polylinePaths = new ArrayList<>();
        originMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();

        for (Route route : routes) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.startLocation, 16));
            ((TextView) findViewById(R.id.tvDuration)).setText(route.duration.text);
            ((TextView) findViewById(R.id.tvDistance)).setText(route.distance.text);

            originMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.start_blue))
                    .title(route.startAddress)
                    .position(route.startLocation)));
            destinationMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.end_green))
                    .title(route.endAddress)
                    .position(route.endLocation)));

            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(Color.BLUE).
                    width(10);

            for (int i = 0; i < route.points.size(); i++)
                polylineOptions.add(route.points.get(i));

            polylinePaths.add(mMap.addPolyline(polylineOptions));
        }
    }

    class docJASON3 extends AsyncTask<String, Integer, String>{

        @Override
        protected String doInBackground(String... params) {
            return docNoiDung_Tu_URL(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                session = new UserSessionManager(getApplicationContext());
                sharedPreferences = getApplicationContext().getSharedPreferences("My app", 0);
                final String sotk = sharedPreferences.getString("sotk", null);

                JSONObject root1 = new JSONObject(s);
                JSONArray mang = root1.getJSONArray("danhan");
                for (int i=0; i< mang.length();i++){
                    JSONObject tbship = mang.getJSONObject(i);
                    if(tbship.getString("SoTKNhan").toString().equals(sotk) && tbship.getString("TTDon").equals("1")) {
                        mangTB_Ship.add(new TB_Ship(tbship.getString("SoTK"), tbship.getString("SoTKNhan"),
                                tbship.getString("MaHH"), tbship.getString("Username"), tbship.getString("MoTa"),
                                tbship.getString("VTN"), tbship.getString("VTD"), tbship.getString("ThoiGian"),
                                tbship.getString("TTDon"), tbship.getString("TGNhan"), tbship.getString("LuotXemShip")));
                    }

                }

                TB_Ship_Adapter adapter = new TB_Ship_Adapter(getApplicationContext(), R.layout.lv_tb_ship, mangTB_Ship);
                lvTBShip.setAdapter(adapter);



            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
        }
    }

    class  docJASON1 extends AsyncTask<String, Integer, String>{

        @Override
        protected String doInBackground(String... params) {
            return docNoiDung_Tu_URL(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                session = new UserSessionManager(getApplicationContext());
                sharedPreferences = getApplicationContext().getSharedPreferences("My app", 0);
                final String sotk = sharedPreferences.getString("sotk", null);

                JSONObject root = new JSONObject(s);
                JSONArray mang = root.getJSONArray("danhan");
                for (int i=0; i< mang.length();i++){
                    JSONObject danhan = mang.getJSONObject(i);
                    if(danhan.getString("SoTKNhan").equals(sotk)) {

                        mangDaNhan.add(new DaNhan(danhan.getString("SoTK"), danhan.getString("SoTKNhan"), danhan.getString("MaHH"),
                                danhan.getString("Username"), danhan.getString("MoTa"), danhan.getString("VTN"), danhan.getString("VTD"),
                                danhan.getString("ThoiGian"), danhan.getString("TTDon")));
                    }
                }

                DaNhanAdapter adapter = new DaNhanAdapter(getApplicationContext(), R.layout.lv_donhang, mangDaNhan);
                lvDaNhan.setAdapter(adapter);



            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
        }
    }
    class  docJASON extends AsyncTask<String, Integer, String>{
        @Override
        protected String doInBackground(String... params) {
            return docNoiDung_Tu_URL(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            try {

                JSONObject root = new JSONObject(s);
                JSONArray mang = root.getJSONArray("donhang");
                for (int i=0; i< mang.length();i++){
                    JSONObject donhang = mang.getJSONObject(i);

                    mangDonHang.add(new DonHang(donhang.getString("SoTK"), donhang.getString("Username"), donhang.getString("MoTa"),
                            donhang.getString("VTN"), donhang.getString("VTD"), donhang.getString("ThoiGian"),
                            donhang.getString("MaHH")));
                }

                DonHangAdapter adapter = new DonHangAdapter(Shipper_Activity.this, R.layout.lv_donhang, mangDonHang);
                lvDonHang.setAdapter(adapter);



            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
        }
    }


    private static String docNoiDung_Tu_URL(String theUrl)
    {
        StringBuilder content = new StringBuilder();

        try
        {
            // create a url object
            URL url = new URL(theUrl);

            // create a urlconnection object
            URLConnection urlConnection = url.openConnection();

            // wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line;

            // read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null)
            {
                content.append(line + "\n");
            }
            bufferedReader.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return content.toString();
    }


    // tạo option menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflate = getMenuInflater();
        inflate.inflate(R.menu.option_menu, menu);
        inflate.inflate(R.menu.search, menu);
        return  true;
    }

    //set click item
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.Acount:
                Intent intentt = new Intent(Shipper_Activity.this, Acount_Activity.class);
                Shipper_Activity.this.startActivity(intentt);

                return true;
            case R.id.Setting:
                return true;
            case R.id.logout:
                session.logoutUser();
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

    private AdapterView.OnItemClickListener mAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final PlaceArrayAdapter.PlaceAutocomplete item = mPlaceArrayAdapter.getItem(position);
            final String placeId = String.valueOf(item.placeId);
            Log.i(LOG_TAG, "Selected: " + item.description);
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
            Log.i(LOG_TAG, "Fetching details for ID: " + item.placeId);
        }
    };

    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                Log.e(LOG_TAG, "Place query did not complete. Error: " +
                        places.getStatus().toString());
                return;
            }
            // Selecting the first object buffer.
            final Place place = places.get(0);
            CharSequence attributions = places.getAttributions();

        }
    };

    @Override
    public void onConnected(Bundle bundle) {
        mPlaceArrayAdapter.setGoogleApiClient(mGoogleApiClient);
        Log.i(LOG_TAG, "Google Places API connected.");

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e(LOG_TAG, "Google Places API connection failed with error code: "
                + connectionResult.getErrorCode());

        Toast.makeText(this,
                "Google Places API connection failed with error code:" +
                        connectionResult.getErrorCode(),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionSuspended(int i) {
        mPlaceArrayAdapter.setGoogleApiClient(null);
        Log.e(LOG_TAG, "Google Places API connection suspended.");
    }
}