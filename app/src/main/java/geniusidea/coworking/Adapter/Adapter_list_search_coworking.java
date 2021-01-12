package geniusidea.coworking.Adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import geniusidea.coworking.Model.ListCoworking;
import geniusidea.coworking.Office_detail;
import geniusidea.coworking.R;

public class Adapter_list_search_coworking extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    List<ListCoworking> myArray;
    RecyclerView mRecyclerView;
    private boolean isLoading;
    private int visibleThreshold = 1;
    private int lastVisibleItem, totalItemCount;
//    private OnLoadMoreListener mOnLoadMoreListener;
    Context c;

    public Adapter_list_search_coworking (Context c, RecyclerView mRecyclerView, List<ListCoworking> myArray){
        this.mRecyclerView = mRecyclerView;
        this.myArray = myArray;
        this.c = c;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      //  if (viewType == VIEW_TYPE_ITEM){
            View view = LayoutInflater.from(c).inflate(R.layout.model_list_coworking,parent,false);
            return new Adapter_list_search_coworking.UserViewHolder(view);
//        }else if (viewType == VIEW_TYPE_LOADING){
//            View view = LayoutInflater.from(c).inflate(R.layout.progressbar,parent,false);
//            return new Adapter_list_search_coworking.LoadingViewHolder(view);
//        }
    //    return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof UserViewHolder){
            final ListCoworking listCoworking = myArray.get(position);
            UserViewHolder userViewHolder = (UserViewHolder)holder;
            Picasso.with(c)
                    .load("" + listCoworking.getMain_picture())
                    .noPlaceholder()
                    .resize(810, 453)
                    .centerCrop()
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .into(userViewHolder.imgCowork);
            userViewHolder.txtCoworkName.setText(listCoworking.getNama_tempat());
            userViewHolder.txtLocation.setText(listCoworking.getAddress());
            NumberFormat formatter = NumberFormat.getInstance(Locale.US);
            int price = Integer.parseInt(listCoworking.getPrice());
            userViewHolder.txtPrice.setText("Rp. "+formatter.format(price)+",-");
            userViewHolder.linClicked.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    c.startActivity(new Intent(c, Office_detail.class).
                            putExtra("idOffice",""+listCoworking.getId())
                            .putExtra("Lat",""+listCoworking.getLat())
                            .putExtra("Lng",""+listCoworking.getLongi()));
                }
            });
            userViewHolder.relative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    c.startActivity(new Intent(c, Office_detail.class).
                            putExtra("idOffice",""+listCoworking.getId())
                            .putExtra("Lat",""+listCoworking.getLat())
                            .putExtra("Lng",""+listCoworking.getLongi()));
                }
            });
            userViewHolder.imgCowork.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    c.startActivity(new Intent(c, Office_detail.class).
                            putExtra("idOffice",""+listCoworking.getId())
                            .putExtra("Lat",""+listCoworking.getLat())
                            .putExtra("Lng",""+listCoworking.getLongi()));
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return myArray == null ? 0 : myArray.size();
    }

//    @Override
//    public int getItemViewType(int position) {
//        return myArray.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
//    }
//    public void setLoaded() {
//        isLoading = false;
//    }

    static class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar1);
        }
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        // public TextView t_title, t_name, t_address, t_date, t_update, t_dates, t_view, t_status, t_description;
        LinearLayout linClicked;
        CardView status;
        ImageView imgCowork;
        TextView txtPrice,txtRating,txtCoworkName,txtLocation;
        RelativeLayout relative;

        public UserViewHolder(View view) {
            super(view);
            c = itemView.getContext();

            txtPrice = view.findViewById(R.id.txtPrice);
            txtRating = view.findViewById(R.id.txtRating);
            txtCoworkName = view.findViewById(R.id.txtCoworkName);
            txtLocation = view.findViewById(R.id.txtLocation);
            linClicked = view.findViewById(R.id.linClicked);
            imgCowork = view.findViewById(R.id.imgCowork);
            relative = view.findViewById(R.id.relative);
        }
    }
}
