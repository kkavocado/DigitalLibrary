package com.example.digitallibrary;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.digitallibrary.Adapter.BookRecyclerViewAdapter;

import java.util.Objects;

public class home extends Fragment implements View.OnClickListener{

    private static final String TAG = home.class.getSimpleName();
    private RecyclerView recyclerView;
    private BookRecyclerViewAdapter adapter;
    private DatabaseAccess DB;
    private String editor= "true";
    CardView detective, romance, science, mystery, more, horror;
    private User currentUser;
    VideoView videoView;
    private boolean mVolumePlaying = false;
    AppCompatImageView volume;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);

        Bundle bundle = getArguments();
        currentUser = (User) bundle.getSerializable("user");

        detective= view.findViewById(R.id.detectiveCard);
        romance= view.findViewById(R.id.romanceCard);
        science= view.findViewById(R.id.scienceCard);
        mystery= view.findViewById(R.id.fantasyCard);
        more= view.findViewById(R.id.nonficCard);
        horror= view.findViewById(R.id.horrorCard);
        videoView=view.findViewById(R.id.video);
        volume = (AppCompatImageView) view.findViewById(R.id.volume);

        detective.setOnClickListener(this);
        romance.setOnClickListener(this);
        science.setOnClickListener(this);
        mystery.setOnClickListener(this);
        more.setOnClickListener(this);
        horror.setOnClickListener(this);

        //check user value
        Log.d("User", currentUser.getName());
        Log.d("Userid", String.valueOf(currentUser.getUid()));
        Log.d("Email", String.valueOf(currentUser.getEmail()));


        //connect to database
        DB= DatabaseAccess.getInstance(getContext());
        DB.open();

        //initialize recycler view
        recyclerView = view.findViewById(R.id.recyclerview);
        //set recycler view with custom adapter
        adapter= new BookRecyclerViewAdapter(DB.getEditorBooks(editor), currentUser,getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL,false));
        adapter.notifyDataSetChanged();

        //set the video
        //set the video path that in the raw folder
        String videoPath =
                "android.resource://" +
                        requireActivity().getPackageName() +
                        "/" +
                        R.raw.video;
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);

        //Mediacontroller contains the buttons like "Play/Pause"that we need
        MediaController mediaController = new MediaController(getActivity());
        //set the video to the media controller
        videoView.setMediaController(mediaController);
        //set the video to start automatically
        videoView.start();
        //set the video a callback to be invoked, take a listener as parameter.
        videoView.setOnPreparedListener(this::PreparedListener);


        return view;

    }

    @Override
    public void onClick(View v) {
        Intent i;

        switch (v.getId()){
            case R.id.detectiveCard:
                i=new Intent(getActivity(), GenresCollections.class);
                i.putExtra("genres", "Detective");
                i.putExtra("user", currentUser);
                startActivity(i);
                break;
            case R.id.romanceCard:
                i=new Intent(getActivity(), GenresCollections.class);
                i.putExtra("genres", "Romance");
                i.putExtra("user", currentUser);
                startActivity(i);
                break;
            case R.id.scienceCard:
                i=new Intent(getActivity(), GenresCollections.class);
                i.putExtra("genres", "SciFic");
                i.putExtra("user", currentUser);
                startActivity(i);
                break;
            case R.id.horrorCard:
                i=new Intent(getActivity(), GenresCollections.class);
                i.putExtra("genres", "Horror");
                i.putExtra("user", currentUser);
                startActivity(i);
                break;
            case R.id.fantasyCard:
                i=new Intent(getActivity(), GenresCollections.class);
                i.putExtra("genres", "Fantasy");
                i.putExtra("user", currentUser);
                startActivity(i);
                break;
            case R.id.nonficCard:
                i=new Intent(getActivity(), GenresCollections.class);
                i.putExtra("genres", "NonFic");
                i.putExtra("user", currentUser);
                startActivity(i);
                break;
        }

    }

    //the listener parameter of video
    private void PreparedListener(MediaPlayer mp) {
        try {
            //mute the volume of the video when the user enter the page
            mp.setVolume(0f, 0f);
            //if mute volume is true, the mute icon is shown
            if (!mVolumePlaying) {
                volume.setImageResource(R.drawable.ic_baseline_volume_off_24);
            }

            //set the looping of video on
            mp.setLooping(true);
            mp.start();

            //user can click on the image to change the volume (mute or unmute)
            volume.setOnClickListener(v -> {

                //if mute is false, and the icon is clicked
                // means the user want to mute the volume, so mute icon is shown
                if (mVolumePlaying) {
                    Log.d(TAG, "setVolume OFF"); //text show in console to double check
                    volume.setImageResource(R.drawable.ic_baseline_volume_off_24);
                    mp.setVolume(0F, 0F);
                }
                //else if mute is true, and the icon is clicked
                // means the user want to unmute the volume, so unmute icon is shown
                else {
                    Log.d(TAG, "setVolume ON"); //text show in console to double check
                    volume.setImageResource(R.drawable.ic_baseline_volume_up_24);
                    mp.setVolume(1F, 1F);
                }
                //when the clicking is finished, set the mVolumePlaying to opposite (due to the if else above)
                //so the the difference can be observed the click is functional
                mVolumePlaying = !mVolumePlaying;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}