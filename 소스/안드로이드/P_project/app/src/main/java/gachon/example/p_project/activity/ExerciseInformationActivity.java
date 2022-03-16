package gachon.example.p_project.activity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;
import gachon.example.p_project.R;

public class ExerciseInformationActivity extends AppCompatActivity {
ImageView exercise_img;
TextView exercise_name,exercise_explain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_information);
exercise_img=(ImageView)findViewById(R.id.exercise_img);
        Picasso.with(this)
                .load(getIntent().getStringExtra("imgurl"))
                .resize(200,250)
                .into(exercise_img);
        exercise_name=(TextView)findViewById(R.id.exercise_name);
        exercise_name.setText(getIntent().getStringExtra("name"));
        exercise_explain=(TextView)findViewById(R.id.exercise_explain);
        exercise_explain.setText(getIntent().getStringExtra("explain"));

        VideoView videoView = (VideoView) findViewById(R.id.exercise_video);
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        // Set video link (mp4 format )
        Uri video = Uri.parse(getIntent().getStringExtra("videourl"));
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(video);
        videoView.requestFocus();
        videoView.start();





    }
}
