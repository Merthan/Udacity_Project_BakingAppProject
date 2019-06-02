package example.bakingappproject;

import android.app.Activity;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackPreparer;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecipestepDetailFragment extends Fragment {

    public static final String ARG_ITEM_LIST = "item_extra list";


    private Step mItem;


    public RecipestepDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_LIST)) {


            ArrayList<String> extras=getArguments().getStringArrayList(ARG_ITEM_LIST);
            mItem = new Step(Integer.parseInt(extras.get(0)),extras.get(1),extras.get(2),extras.get(3),extras.get(4));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.getShortDescription());
            }





        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
releaseExoPlayer();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
   releaseExoPlayer();
    }

    private void releaseExoPlayer(){
        if(videoView!=null){
            playbackPosition = player.getCurrentPosition();
            player.release();
        }


    }
    boolean playWhenReady;

    public static final String POSITION="POSITION";
    public static final String PWR="PWR";

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(POSITION,playbackPosition);
        if(videoView!=null)outState.putBoolean(PWR,videoView.getPlayer().getPlayWhenReady());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState==null)return;
        playbackPosition=savedInstanceState.getLong(POSITION,0);
        playWhenReady=savedInstanceState.getBoolean(PWR);
    }

    @Override
    public void onResume() {
        super.onResume();
        initializePlayer();
    }

    @Override
    public void onPause() {
        super.onPause();
        releaseExoPlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        releaseExoPlayer();
    }

    ExoPlayer player;
    long playbackPosition=0;

    private void initializePlayer() {


        player = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(this.getContext()), new DefaultTrackSelector(), new DefaultLoadControl());
        videoView.setPlayer(player);
        player.setPlayWhenReady(playWhenReady||true);
        player.seekTo(playbackPosition);

        if(!my_url.isEmpty()){
            Uri uri = Uri.parse(my_url);
            MediaSource mediaSource = new ExtractorMediaSource.Factory(new DefaultHttpDataSourceFactory("exoplayer-codelab")).createMediaSource(uri);
            player.prepare(mediaSource, true, false);
        }

    }

    String my_url="";

    PlayerView videoView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipestep_detail, container, false);

        if (mItem != null) {
            (((TextView) (rootView.findViewById(R.id.recipestep_detail)).findViewById(R.id.textViewDetail))).setText(mItem.getDescription());


             videoView= rootView.findViewById(R.id.exoplayerView);


            ImageView imageView=rootView.findViewById(R.id.recipeDetailImageView);
            Picasso.with(rootView.getContext()).load(Uri.parse(mItem.getThumbnailURL())).into(imageView);

            final TextView textView=rootView.findViewById(R.id.textViewExoplayerDetail);

            if(!mItem.getVideoURL().equals("")){
                my_url=mItem.getVideoURL();
          //      videoView.setVideoURI(Uri.parse(mItem.getVideoURL()));
            }else{
                textView.setVisibility(View.VISIBLE);
            }
        }

        return rootView;
    }
}
