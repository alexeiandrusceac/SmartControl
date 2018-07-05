package smartcontrol.com.smartcontrol;

import android.media.Image;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class LoadingActivity extends AppCompatActivity {

    private ImageView imageLoading;
    private ProgressBar loadingProgress;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.loading_activity);
        imageLoading = (ImageView)findViewById(R.id.imageLoading);
        loadingProgress = (ProgressBar)findViewById(R.id.loadingProgress);

    }
}
