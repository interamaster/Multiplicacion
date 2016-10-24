package jrdv.mio.com.entrenatablasmultiplicacion;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.VideoView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class VideoPremio151PokemoActivity extends Activity implements MediaPlayer.OnCompletionListener {

    private VideoView mVV;

    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);

        setContentView(R.layout.activity_video_premio151_pokemo);






        int fileRes=0;
        Bundle e = getIntent().getExtras();
        if (e!=null) {
            fileRes = e.getInt("fileRes");
        }

        mVV = (VideoView)findViewById(R.id.myvideoview);


        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;

        Log.e("INFO", "H" + height + " y W"+ width);

        //   videoview.setLayoutParams(new FrameLayout.LayoutParams(550,550));

        mVV.setLayoutParams(new FrameLayout.LayoutParams(width,height));


        mVV.setOnCompletionListener(this);


        if (!playFileRes(fileRes)) return;

        mVV.start();



        //que vibre al fallar

        //navigator.vibrate([500,110,500,110,450,110,200,110,170,40,450,110,200,110,170,40,500]);
        // vibration for 800 milliseconds
        //((Vibrator)getSystemService(VIBRATOR_SERVICE)).vibrate(800);

        long[] vibrationPattern = {0,150,150,150,150,75,75,150,150,150,150,450};

        //-1 = don't repeat
        final int indexInPatternToRepeat = -1;
        ((Vibrator)getSystemService(VIBRATOR_SERVICE)).vibrate(vibrationPattern, indexInPatternToRepeat);


    }

    private boolean playFileRes(int fileRes) {
        if (fileRes==0) {
            stopPlaying();
            return false;
        } else {
            mVV.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + fileRes));
            return true;
        }
    }

    public void stopPlaying() {
        mVV.stopPlayback();
        this.finish();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        finish();
    }
}