package app.movies.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import app.movies.Model.Episode;
import app.movies.Model.ServerDatum;
import app.movies.R;

public class EpisodeCategory extends RecyclerView.Adapter<EpisodeCategory.ViewHolder> {
    List<ServerDatum> list;
    Context context;

    public EpisodeCategory(List<ServerDatum> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public EpisodeCategory.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context= parent.getContext();
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_episode,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EpisodeCategory.ViewHolder holder, int position) {
        holder.tvEpisode.setText(list.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvEpisode;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEpisode=itemView.findViewById(R.id.tv_episode);
        }
    }
}
