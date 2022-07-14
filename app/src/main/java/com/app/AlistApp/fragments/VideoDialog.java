package com.app.AlistApp.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import com.app.AlistApp.R;
import com.app.AlistApp.databinding.FragmentVideoDiloageBinding;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;


public class VideoDialog extends DialogFragment {

    FragmentVideoDiloageBinding binding;
    MediaItem mediaItem;
    ExoPlayer exoPlayer;
    String VIDEO_URL = "https://video.fjrs2-2.fna.fbcdn.net/v/t66.36240-2/120675378_736910894326921_599127712297900126_n.mp4?_nc_cat=105&ccb=1-7&_nc_sid=985c63&efg=eyJ2ZW5jb2RlX3RhZyI6Im9lcF9oZCJ9&_nc_ohc=XvDK46_5m2wAX-27qQB&_nc_ht=video.fjrs2-2.fna&oh=00_AT-R2Tt3rWvwJAIoYD5RivhgHKDUPQMfXmFV9bR0LVMFhw&oe=62926575";

    boolean isFullScreen = false;

    public static VideoDialog newInstance() {
        VideoDialog videoDialog = new VideoDialog();
        return videoDialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //video link
        mediaItem = MediaItem.fromUri(VIDEO_URL);
        exoPlayer = new ExoPlayer.Builder(requireActivity()).build();

    }

    @Override
    public int getTheme() {
        return R.style.VideoDialogTheme;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(requireActivity()).inflate(R.layout.fragment_video_diloage, null, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity(), R.style.VideoDialogTheme).setView(view);


        binding = FragmentVideoDiloageBinding.bind(view);
        binding.playerView.setPlayer(exoPlayer);
        exoPlayer.setMediaItem(mediaItem);
        exoPlayer.prepare();
        exoPlayer.setPlayWhenReady(true);


        exoPlayer.addListener(new Player.Listener() {
            @Override
            public void onPlaybackStateChanged(int playbackState) {
                if (playbackState == Player.STATE_ENDED) {
                    dismissDialog();

                }
            }
        });


        ImageView fullScreenBtn = binding.playerView.findViewById(R.id.exo_fullscreen_button);
        ImageView exitFullScreeBtn = binding.playerView.findViewById(R.id.exo_exit_fullscreen_button);

        fullScreenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isFullScreen = true;
                fullScreenBtn.setVisibility(View.GONE);
                exitFullScreeBtn.setVisibility(View.VISIBLE);


                int screenWidth = binding.getRoot().getLayoutParams().width;
                binding.playerView.getLayoutParams().height = screenWidth;

                requireActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

                requireActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

            }
        });


        exitFullScreeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fullScreenBtn.setVisibility(View.VISIBLE);
                exitFullScreeBtn.setVisibility(View.GONE);

                exitFullScreen();

            }
        });


        return builder.create();
    }


    public void dismissDialog() {
        if (isFullScreen) {
            exitFullScreen();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dismiss();
            }
        }, 1000);

    }


    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if (isFullScreen)
            requireActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        exoPlayer.stop();
        exoPlayer.release();

    }

    public void exitFullScreen() {
        binding.playerView.getLayoutParams().height = (int) getResources().getDimension(R.dimen.portrait_video_height);
        requireActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        isFullScreen = false;
    }
}