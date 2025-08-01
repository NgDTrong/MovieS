package app.movies.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import app.movies.Activities.DetailActivity;
import app.movies.Model.Item;
import app.movies.R;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private List<Item> movies;
    Context context;

    public SearchAdapter(List<Item> movies, Context context) {
        this.movies = movies;
        this.context = context;
    }

    public void updateData(List<Item> newMovies) {
        this.movies.clear();
        this.movies.addAll(newMovies);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item= movies.get(position);
        holder.tvTitle.setText(item.getName());
//        Glide.with(context)
//                .load(item.getPosterUrl())
//                .into(holder.imagePoster);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(holder.itemView.getContext(), DetailActivity.class);
                intent.putExtra("slug",item.getSlug());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        ImageView imagePoster;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle=itemView.findViewById(R.id.tvTitle);
//            imagePoster=itemView.findViewById(R.id.imgFilm);
        }
    }
}
