package app.movies.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import app.movies.R;

public class ActorsListAdapter extends RecyclerView.Adapter<ActorsListAdapter.ViewHolder> {
    List<String> list;
    Context context;

    public ActorsListAdapter(List<String> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ActorsListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_actor, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActorsListAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(list.get(position)).into(holder.pic);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView pic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pic = itemView.findViewById(R.id.imgActor);
        }
    }
}
