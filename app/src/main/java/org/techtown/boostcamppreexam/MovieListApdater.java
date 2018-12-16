package org.techtown.boostcamppreexam;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.net.URL;
import java.util.ArrayList;

public class MovieListApdater extends RecyclerView.Adapter<MovieListApdater.ViewHolder> {

    Context mContext;
    private ArrayList<Movie> movies;     //영화정보를 가지고 있는 리스트
    private OnClickListener onClickListener;

    public MovieListApdater(Context context) {
        this.mContext = context;
        movies = new ArrayList<>();
    }

    public void setItemClick(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.item_moive,parent,false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final int finalPosition = position;
        Movie item = movies.get(finalPosition);
        holder.setItem(mContext,item);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onClickListener != null){
                    onClickListener.onClick(v,finalPosition);
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return movies.size();
    }
    //검색시 결과에 해당하는 영화정보를 recyclerView에 추가하는 함수
    public void addItem(Movie item){
        movies.add(item);
    }
    //리사이클러뷰 목록 초기화하는 함수
    public void clearItem(){
        movies.clear();
    }
    public Movie getItem(int index){
        return movies.get(index);
    }
    static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView posterImageView;
        TextView titleTextView;
        TextView yearTextView;
        TextView directorTextView;
        TextView actorTextView;
        RatingBar ratingbar;

        public ViewHolder(View itemView) {
            super(itemView);
            posterImageView = itemView.findViewById(R.id.poster_imageView);
            titleTextView = itemView.findViewById(R.id.title_textView);
            yearTextView = itemView.findViewById(R.id.year_textView);
            directorTextView = itemView.findViewById(R.id.director_textView);
            actorTextView = itemView.findViewById(R.id.actor_textView);
            ratingbar = itemView.findViewById(R.id.movie_ratingbar);

        }
        public void setItem(Context context, Movie item){
            Glide.with(context).load(Uri.parse(item.getImageUrl())).into(posterImageView);
            titleTextView.setText(item.getTitle());
            yearTextView.setText(item.getPubDate().toString());
            directorTextView.setText(item.getDirector());
            actorTextView.setText(item.getActor());
            ratingbar.setRating(item.getUserRating());
        }
    }
}
