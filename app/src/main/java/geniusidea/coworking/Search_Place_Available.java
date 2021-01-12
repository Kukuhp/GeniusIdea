package geniusidea.coworking;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Handler;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import geniusidea.coworking.Adapter.Adapter_list_search_coworking;
import geniusidea.coworking.Model.ListCoworking;
import geniusidea.coworking.Model.PostCity;
import geniusidea.coworking.Service.Retrofit.APIService;
import geniusidea.coworking.Service.Retrofit.APIClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Search_Place_Available extends AppCompatActivity {

    String[] id;
    String[] nama_tempat;
    String[] main_pictue;
    String[] facilities;
    String[] citys;
    String[] lat;
    String[] longi;
    String[] address;
    String[] price;
    String[] description;
    String[] floorPlans;
    SwipeRefreshLayout swipeRefreshLayout;
    Adapter_list_search_coworking myAdapter;
    RecyclerView lists;
    ShimmerFrameLayout shimmerFrameLayout;
    private List<ListCoworking> myArray = new ArrayList<>();
    Handler handler;
    Runnable myRunnable;
    LinearLayout linDate,linPickCity;
    String getCity;
    TextView txtKota;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search__place__available);

        getCity = getIntent().getStringExtra("city");

        shimmerFrameLayout = findViewById(R.id.shimmer_view_container);
        lists = findViewById(R.id.list);
        txtKota = findViewById(R.id.txtKota);
        txtKota.setText(getCity);
        linDate = findViewById(R.id.linDate);
        linPickCity = findViewById(R.id.linPickCity);
        linPickCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        swipeRefreshLayout = findViewById(R.id.refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
//                lists.setOnTouchListener(new View.OnTouchListener() {
//                    @Override
//                    public boolean onTouch(View v, MotionEvent event) {
//                        return true;
//                    }
//                });
                swipeRefreshLayout.setVisibility(View.GONE);
                shimmerFrameLayout.setVisibility(View.VISIBLE);
                shimmerFrameLayout.startShimmer();
                getCoworkingData(getCity);

            }
        });
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
//                lists.setOnTouchListener(new View.OnTouchListener() {
//                    @Override
//                    public boolean onTouch(View v, MotionEvent event) {
//                        return true;
//                    }
//                });
                swipeRefreshLayout.setVisibility(View.GONE);
                shimmerFrameLayout.setVisibility(View.VISIBLE);
                shimmerFrameLayout.startShimmer();
                getCoworkingData(getCity);
            }
        });
        lists.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new Adapter_list_search_coworking(Search_Place_Available.this,lists,myArray);

        linDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });

    }

    private void showDateDialog(){

        /**
         * Calendar untuk mendapatkan tanggal sekarang
         */
        Calendar newCalendar = Calendar.getInstance();

        /**
         * Initiate DatePicker dialog
         */
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                /**
                 * Method ini dipanggil saat kita selesai memilih tanggal di DatePicker
                 */

                /**
                 * Set Calendar untuk menampung tanggal yang dipilih
                 */
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                /**
                 * Update TextView dengan tanggal yang kita pilih
                 */
              //  tvDateResult.setText("Tanggal dipilih : "+dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        /**
         * Tampilkan DatePicker dialog
         */
        datePickerDialog.show();
    }

    public void getCoworkingData(final String city){
        myArray.clear();
        PostCity postCity = new PostCity(city);
        APIService service = APIClient.getClient().create(APIService.class);
        Call<List<ListCoworking>> getData = service.getListCoworking(city,"getDataCoworking");
        getData.enqueue(new Callback<List<ListCoworking>>() {
            @Override
            public void onResponse(Call<List<ListCoworking>> call, Response<List<ListCoworking>> response) {
                List<ListCoworking> list = response.body();
                if (response.isSuccessful()) {

                    if (list.size() != 0) {
                        id = new String[list.size()];
                        nama_tempat = new String[list.size()];
                        main_pictue = new String[list.size()];
                        facilities = new String[list.size()];
                        citys = new String[list.size()];
                        lat = new String[list.size()];
                        longi = new String[list.size()];
                        address = new String[list.size()];
                        price = new String[list.size()];
                        description = new String[list.size()];
                        floorPlans = new String[list.size()];
                        for (int i = 0; i < list.size(); i++) {
                            id[i] = list.get(i).getId();
                            nama_tempat[i] = list.get(i).getNama_tempat();
                            main_pictue[i] = list.get(i).getMain_picture();
                            facilities[i] = list.get(i).getFacilities();
                            citys[i] = list.get(i).getCity();
                            lat[i] = list.get(i).getLat();
                            longi[i] = list.get(i).getLongi();
                            address[i] = list.get(i).getAddress();
                            price[i] = list.get(i).getPrice();
                            description[i] = list.get(i).getDescription();
                            floorPlans[i] = list.get(i).getFloorPlans();
                   //         Toast.makeText(getApplicationContext(),""+id[i],Toast.LENGTH_SHORT).show();
                            ListCoworking listCoworking = new ListCoworking(address[i], citys[i], description[i], facilities[i], floorPlans[i], id[i], lat[i],
                                    longi[i], main_pictue[i], nama_tempat[i], price[i]);
                            myArray.add(listCoworking);
                        }


                        myAdapter.notifyDataSetChanged();
                   //     myAdapter.setLoaded();
                        lists.setAdapter(myAdapter);

                    }else {
                        Toast.makeText(getApplicationContext(), "tidak ada data", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(getApplicationContext(), "gagal", Toast.LENGTH_SHORT).show();
                }
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                swipeRefreshLayout.setVisibility(View.VISIBLE);
              //  animation();
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<ListCoworking>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "failure", Toast.LENGTH_SHORT).show();
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                swipeRefreshLayout.setVisibility(View.VISIBLE);
              //  animation();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public void animation(){
        handler =  new Handler();
        myRunnable = new Runnable() {
            public void run() {
                // do something
                shimmerFrameLayout.stopShimmer();
                lists.setVisibility(View.VISIBLE);
                shimmerFrameLayout.setVisibility(View.GONE);
            }
        };
        handler.postDelayed(myRunnable,2000);
    }

    @Override
    public void onResume() {
        super.onResume();
        shimmerFrameLayout.startShimmer();
    }

    @Override
    public void onPause() {
        shimmerFrameLayout.stopShimmer();
        super.onPause();
    }
}
