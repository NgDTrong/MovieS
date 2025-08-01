package app.movies.Adapter;

import android.content.Context;
import android.content.Intent;
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
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, app.movies.Activities.PlayerActivity.class);
            intent.putExtra("video_url", list.get(position).getLinkM3u8()); // Gửi đường dẫn video
//            intent.putExtra("episode_name", list.get(position).getName());  // Gửi tên tập (tùy chọn hiển thị)
            context.startActivity(intent);
        });
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
