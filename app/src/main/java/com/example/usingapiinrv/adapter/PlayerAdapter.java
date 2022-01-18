package com.example.usingapiinrv.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.usingapiinrv.R;
import com.example.usingapiinrv.model.Player;

import java.util.List;

public class PlayerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Player> players;

    public PlayerAdapter(Context context, List<Player> players) {
        this.context = context;
        this.players = players;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_custom_view, parent, false);
        return new PlayerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PlayerViewHolder){
            Player player = players.get(position);

            ImageView imagePlayer = ((PlayerViewHolder)holder).playerImage;
            TextView namePlayer = ((PlayerViewHolder)holder).playerName;
            TextView locationPlayer = ((PlayerViewHolder)holder).playerLocation;

            Glide.with(context).load(player.getImgURL()).into(imagePlayer);  // Set Image in imagePlayer(ImageView)
            namePlayer.setText(player.getName());
            locationPlayer.setText((player.getCountry()+", "+player.getCity()));
        }
    }

    @Override
    public int getItemCount() {
        return players.size();
    }


    class PlayerViewHolder extends RecyclerView.ViewHolder{
        public View view;
        public ImageView playerImage;
        public TextView playerName;
        public TextView playerLocation;

        public PlayerViewHolder(View view){
            super(view);
            this.view= view;
            // Add your UI Components here
            playerImage = view.findViewById(R.id.iv_tennisImage);
            playerName = view.findViewById(R.id.tv_tennisName);
            playerLocation = view.findViewById(R.id.tv_tennisLocation);
        }

    }


}
