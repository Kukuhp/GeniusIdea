package geniusidea.coworking;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import geniusidea.coworking.Model.GetCityResponse;
import geniusidea.coworking.Service.Retrofit.APIClient;
import geniusidea.coworking.Service.Retrofit.APIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Pick_City extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;
    ListView listCity;
    String[] idCity,name,picture;
    AdapterCity adapterCity = null;

    CardView cardJakarta,cardSemarang,cardJogja,cardBandung,cardSurabaya,cardOther;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick__city);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        listCity = findViewById(R.id.listCity);
        listCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(Pick_City.this,Search_Place_Available.class).
                        putExtra("city",""+idCity[i]));
            }
        });
        cardBandung = findViewById(R.id.cardBandung);
        cardBandung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Pick_City.this,Search_Place_Available.class).
                        putExtra("city","bandung"));
            }
        });
        cardJakarta = findViewById(R.id.cardJakarta);
        cardJakarta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Pick_City.this,Search_Place_Available.class).
                        putExtra("city","jakarta"));
            }
        });
        cardJogja = findViewById(R.id.cardJogja);
        cardJogja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Pick_City.this,Search_Place_Available.class).
                        putExtra("city","jogja"));
            }
        });
        cardSemarang = findViewById(R.id.cardSemarang);
        cardSemarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Pick_City.this,Search_Place_Available.class).
                        putExtra("city","semarang"));
            }
        });
        cardSurabaya = findViewById(R.id.cardSurabaya);
        cardSurabaya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Pick_City.this,Search_Place_Available.class).
                        putExtra("city","surabaya"));
            }
        });
        cardOther = findViewById(R.id.cardOther);
        cardOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Pick_City.this,Search_Place_Available.class).
                        putExtra("city","other"));
            }
        });
        getCityPict();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View view) {

    }

    public void getCityPict(){
        APIService service = APIClient.getClient().create(APIService.class);
        Call<List<GetCityResponse>> userCall = service.getCity("load_city");
        userCall.enqueue(new Callback<List<GetCityResponse>>() {
            @Override
            public void onResponse(Call<List<GetCityResponse>> call, Response<List<GetCityResponse>> response) {
                if (response.isSuccessful()){
                    List<GetCityResponse> city = response.body();
                    if (city.size()!=0){
                        idCity = new String[city.size()];
                        name = new String[city.size()];
                        picture = new String[city.size()];
                        for (int i = 0; i < city.size(); i++) {
                            idCity[i] = city.get(i).getId();
                            name[i] = city.get(i).getName();
                            picture[i] = city.get(i).getPicture();
                        }
                        adapterCity = new AdapterCity(Pick_City.this,idCity,name,picture);
                        listCity.setAdapter(adapterCity);
                        adapterCity.notifyDataSetChanged();
                    }
                }else {
                    Toast.makeText(getApplicationContext(),"gagal",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<GetCityResponse>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"failure",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class AdapterCity extends ArrayAdapter<String>{

        String[] idCity,name,picture;
        Context c;

        public AdapterCity(Context context,String[] idCity,String[] name,String[] picture){
            super(context,R.layout.model_city,idCity);
            this.c = context;
            this.idCity = idCity;
            this.name = name;
            this.picture = picture;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View view = null;
            LayoutInflater inflater = getLayoutInflater();
            view = inflater.inflate(R.layout.model_city,parent,false);
            ImageView imgCity = view.findViewById(R.id.imgCity);
            TextView txtName = view.findViewById(R.id.txtCityName);
            txtName.setText(name[position]);
            Picasso.with(c)
                    .load("" + picture[position])
                    .noPlaceholder()
                    .resize(810, 453)
                    .centerCrop()
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .into(imgCity);

            return view;
        }
    }
}
