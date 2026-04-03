package db.tester;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Show build info
        TextView tvBuildInfo = findViewById(R.id.tvBuildInfo);
        String buildInfo = "Version: " + BuildConfig.VERSION_NAME + "\n" +
                "Build: " + (BuildConfig.IS_DEBUG ? "DEBUG" : "RELEASE") + "\n" +
                "Built at: " + BuildConfig.BUILD_TIME + "\n" +
                "Package: " + BuildConfig.APPLICATION_ID;
        tvBuildInfo.setText(buildInfo);

        // Card: Network Test
        CardView cardNetwork = findViewById(R.id.cardNetwork);
        cardNetwork.setOnClickListener(v ->
                startActivity(new Intent(this, NetworkTestActivity.class)));

        // Card: Device Info
        CardView cardDevice = findViewById(R.id.cardDevice);
        cardDevice.setOnClickListener(v ->
                startActivity(new Intent(this, DeviceInfoActivity.class)));

        // Card: Log Viewer
        CardView cardLog = findViewById(R.id.cardLog);
        cardLog.setOnClickListener(v ->
                startActivity(new Intent(this, LogViewerActivity.class)));
    }
}
