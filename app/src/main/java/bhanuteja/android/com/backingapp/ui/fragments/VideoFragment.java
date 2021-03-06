package bhanuteja.android.com.backingapp.ui.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaButtonReceiver;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;
import java.util.List;

import bhanuteja.android.com.backingapp.R;
import bhanuteja.android.com.backingapp.ui.models.StepsModel;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by root on 6/6/17.
 */

public class VideoFragment extends Fragment implements ExoPlayer.EventListener{
    @BindView(R.id.playerView)
    SimpleExoPlayerView simpleExoPlayerView;
    @BindView(R.id.description)
    TextView description;
    @BindView(R.id.thumbnail)
    ImageView thumbnail;
    @BindView(R.id.prev_button)
    Button prevButton;
    @BindView(R.id.next_button)
    Button nextButton;
    @BindView(R.id.video_container)
    LinearLayout videoContainer;

    List<StepsModel> list;
    private int pos;
    public static final String LIST_INDEX = "position";
    public static final String STEP_ID_LIST = "step";
    private SimpleExoPlayer simpleExoPlayer;

    private static MediaSessionCompat mediaSession;
    private PlaybackStateCompat.Builder stateBuilder;

    public VideoFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.video_fragment,container,false);
        ButterKnife.bind(this,rootView);

        if (list != null) {

            simpleExoPlayerView.setDefaultArtwork(BitmapFactory.decodeResource
                    (getResources(), R.drawable.video_placeholder));

            initializeMediaSession();
            initializePlayer(Uri.parse(list.get(pos).getVideoURL()));

            description.setText(list.get(pos).getDescription());
            if (list.get(pos).getThumbnailURL().equals("")){
                Glide.with(getContext()).load(list.get(pos).getThumbnailURL()).placeholder(R.drawable.image_placeholder)
                        .error(R.drawable.image_not_available).into(thumbnail);
            }else {
                Glide.with(getContext()).load(list.get(pos).getThumbnailURL()).placeholder(R.drawable.image_placeholder)
                        .error(R.drawable.image_not_available).into(thumbnail);
            }

        }
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousStep();
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextstep();
            }
        });
        return rootView;
    }
    
    public void previousStep(){
        if (pos == 0) {
            Toast.makeText(getContext(),"No previous step",Toast.LENGTH_SHORT).show();
        }else{
            pos--;
        }
        releasePlayer();
        initializePlayer(Uri.parse(list.get(pos).getVideoURL()));
        description.setText(list.get(pos).getDescription());
        if (list.get(pos).getThumbnailURL() == ""){
            Glide.with(getContext()).load(list.get(pos).getThumbnailURL()).placeholder(R.drawable.image_placeholder)
                    .error(R.drawable.image_not_available).into(thumbnail);
        }
    }
    
    public void nextstep(){
        if (pos < list.size() - 1){
            pos++;
        }else{
            Toast.makeText(getContext(),"No more next steps",Toast.LENGTH_SHORT).show();
        }
        releasePlayer();
        initializePlayer(Uri.parse(list.get(pos).getVideoURL()));
        description.setText(list.get(pos).getDescription());
        if (list.get(pos).getThumbnailURL() == ""){
            Glide.with(getContext()).load(list.get(pos).getThumbnailURL()).placeholder(R.drawable.image_placeholder)
                    .error(R.drawable.image_not_available).into(thumbnail);
        }
    }

    private void initializeMediaSession() {

        mediaSession = new MediaSessionCompat(getContext(),"VideFragment");

        mediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        mediaSession.setMediaButtonReceiver(null);

        stateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);

        mediaSession.setPlaybackState(stateBuilder.build());

        mediaSession.setCallback(new MySessionCallback());


        mediaSession.setActive(true);
    }

    private void initializePlayer(Uri videoURL) {
        if (simpleExoPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            simpleExoPlayerView.setPlayer(simpleExoPlayer);
            simpleExoPlayer.addListener(this);
            String userAgent = Util.getUserAgent(getContext(), getString(R.string.app_name));
            MediaSource mediaSource = new ExtractorMediaSource(videoURL, new DefaultDataSourceFactory(getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            simpleExoPlayer.prepare(mediaSource);
            simpleExoPlayer.setPlayWhenReady(true);
        }
    }

    public void setPosition(int position) {
        this.pos = position;
    }

    public void setStepList(List<StepsModel> stepList) {
        this.list = stepList;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(STEP_ID_LIST, (ArrayList<? extends Parcelable>) list);
        outState.putInt(LIST_INDEX, pos);
    }
    @Override
    public void onResume() {
        super.onResume();
    }
    @Override
    public void onPause(){
        super.onPause();
        releasePlayer();
        if (mediaSession != null) {
            mediaSession.setActive(false);
        }
    }

    @Override
    public void onStop(){
        super.onStop();
        releasePlayer();
        if (mediaSession != null) {
            mediaSession.setActive(false);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        releasePlayer();
        if (mediaSession != null) {
            mediaSession.setActive(false);
        }
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if ((playbackState == ExoPlayer.STATE_READY) && playWhenReady) {
            stateBuilder.setState(PlaybackStateCompat.STATE_PLAYING,
                    simpleExoPlayer.getCurrentPosition(), 1f);
        } else if ((playbackState == ExoPlayer.STATE_READY)) {
            stateBuilder.setState(PlaybackStateCompat.STATE_PAUSED,
                    simpleExoPlayer.getCurrentPosition(), 1f);
        }
        mediaSession.setPlaybackState(stateBuilder.build());
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }

    private class MySessionCallback extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            simpleExoPlayer.setPlayWhenReady(true);
        }
        
        @Override
        public void onPause() {
            simpleExoPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            simpleExoPlayer.seekTo(0);
        }
    }

    public static class MediaReceiver extends BroadcastReceiver {
        public MediaReceiver() {
        }
        @Override
        public void onReceive(Context context, Intent intent) {
            MediaButtonReceiver.handleIntent(mediaSession, intent);
        }
    }

    private void releasePlayer() {
        if (simpleExoPlayer != null) {
            simpleExoPlayer.stop();
            simpleExoPlayer.release();
            simpleExoPlayer = null;
        }
    }
    
}
