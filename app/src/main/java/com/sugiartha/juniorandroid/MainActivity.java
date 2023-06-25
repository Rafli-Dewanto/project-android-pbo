package com.sugiartha.juniorandroid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.carousel.CarouselLayoutManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.os.Handler;
import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.sugiartha.juniorandroid.adapter.CarouselAdapter;
import com.sugiartha.juniorandroid.helper.AuthDao;
import com.sugiartha.juniorandroid.helper.DbHelper;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    LinearLayout btnNama, btnKalkulator, btnLingkaran, btnBilangan, btnLogin, btnSignup, btnCalculator, btnBMI, btnListview, btnList, btnSqlite, btnMysql, btngps, btnseluler, btnsensor, btncatatan, btninternalexternal, btnstorage, btnLogout;

    CarouselAdapter carouselAdapter;
    SharedPreferences sharedPreferences;
    RecyclerView carouselRecyclerView;
    NavigationView navigationView;
    private final List<Integer> sampleImages = Arrays.asList(
            R.drawable.gambar_1,
            R.drawable.gambar_2,
            R.drawable.gambar_3
    );
    TextView currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        btnNama = findViewById(R.id.nama);
        btnKalkulator = findViewById(R.id.kalkulator);
        btnLingkaran = findViewById(R.id.lingkaran);
        btnBilangan = findViewById(R.id.bilangan);
        btnLogin = findViewById(R.id.login);
        btnSignup = findViewById(R.id.signup);
        btnCalculator = findViewById(R.id.calculator);
        btnBMI = findViewById(R.id.bmi);
        btnListview = findViewById(R.id.listview);
        btnList = findViewById(R.id.list);
        btnSqlite = findViewById(R.id.sqlite);
        btnMysql = findViewById(R.id.mysql);
        btngps = findViewById(R.id.gps);
        btnseluler = findViewById(R.id.seluler);
        btnsensor = findViewById(R.id.sensor);
        btncatatan = findViewById(R.id.catatan);
        btninternalexternal = findViewById(R.id.internalexternal);
        btnstorage = findViewById(R.id.storage);
        btnLogout = findViewById(R.id.logout);

        sharedPreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", null);

        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView currentUser = headerView.findViewById(R.id.tv_current_user);


        if (token != null) {
            try {
                Jws<Claims> parsedToken = Jwts.parser().setSigningKey(BuildConfig.SECRET_KEY).parseClaimsJws(token);
                Claims claims = parsedToken.getBody();

                String fullname = claims.get("fullname", String.class);
                String formattedName = convertToTitleCaseIteratingChars(fullname);
                if (formattedName != null && !formattedName.isEmpty()) {
                    currentUser.setText(formattedName);
                }
            } catch (JwtException e) {
                e.printStackTrace();
            }
        } else {
            btnLogout.setVisibility(View.GONE);
        }

        btnLogout.setOnClickListener(v -> {
            sharedPreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);
            if (token == null) {
                Toast.makeText(this, "belom login bang", Toast.LENGTH_SHORT).show();
            } else {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("token");
                editor.apply();
                currentUser.setText("User");
                Toast.makeText(this, "logout berhasil", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(getIntent());
            }
        });

        // Carousel Adapter
        carouselAdapter = new CarouselAdapter(sampleImages);
        carouselRecyclerView = findViewById(R.id.carouselView);
        carouselRecyclerView.setLayoutManager(new CarouselLayoutManager());
        carouselRecyclerView.setAdapter(carouselAdapter);
        // auto slide carousel
        // Set up automatic sliding
        final int delayMillis = 3000;
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            int currentItem = 0;
            int itemCount = carouselAdapter.getItemCount();

            @Override
            public void run() {
                // Calculate the next position to scroll to
                int nextPosition = (currentItem + 1) % itemCount;

                // Scroll to the next position
                carouselRecyclerView.smoothScrollToPosition(nextPosition);

                // Update the current item
                currentItem = nextPosition;

                // Schedule the next run after the specified delay
                handler.postDelayed(this, delayMillis);
            }
        };

        // Start the automatic sliding
        handler.postDelayed(runnable, delayMillis);


        btnNama.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, NamaActivity.class);
                startActivity(i);
            }
        });

        btnKalkulator.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, KalkulatorActivity.class);
                startActivity(i);
            }
        });

        btnLingkaran.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, LingkaranActivity.class);
                startActivity(i);
            }
        });

        btnBilangan.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, BilanganActivity.class);
                startActivity(i);
            }
        });

        btnLogin.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

        btnSignup.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(i);
            }
        });

        btnCalculator.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, CalculatorActivity.class);
                startActivity(i);
            }
        });

        btnBMI.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, HitungBMIActivity.class);
                startActivity(i);
            }
        });

        btnList.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ListActivity.class);
                startActivity(i);
            }
        });

        btnListview.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ListViewActivity.class);
                startActivity(i);
            }
        });

        btnSqlite.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SQLiteActivity.class);
                startActivity(i);
            }
        });

        btnMysql.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, PegawaiMainActivity.class);
                startActivity(i);
            }
        });

        btngps.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, GPS.class);
                startActivity(i);
            }
        });

        btnseluler.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, KoneksiActivity.class);
                startActivity(i);
            }
        });

        btnsensor.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SensorActivity.class);
                startActivity(i);
            }
        });

        btncatatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, CatatanExternalActivity.class);
                startActivity(i);
            }
        });

        btninternalexternal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, InternalExternalActivity.class);
                startActivity(i);
            }
        });

        btnstorage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, StorageActivity.class);
                startActivity(i);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, CatatanExternalActivity.class);
                startActivity(i);
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });


//        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public static String convertToTitleCaseIteratingChars(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }

        StringBuilder converted = new StringBuilder();

        boolean convertNext = true;
        for (char ch : text.toCharArray()) {
            if (Character.isSpaceChar(ch)) {
                convertNext = true;
            } else if (convertNext) {
                ch = Character.toTitleCase(ch);
                convertNext = false;
            } else {
                ch = Character.toLowerCase(ch);
            }
            converted.append(ch);
        }

        return converted.toString();
    }

    private void recreateTables() {
        DbHelper peserta = new DbHelper(MainActivity.this);
        AuthDao user = new AuthDao(MainActivity.this);
        SQLiteDatabase db = peserta.getWritableDatabase();
        SQLiteDatabase db2 = user.getWritableDatabase();
        peserta.dropAllTables(db);
        user.dropAllTables(db2);
    }
}
