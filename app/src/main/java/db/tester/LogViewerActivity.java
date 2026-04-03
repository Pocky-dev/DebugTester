package db.tester;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LogViewerActivity extends AppCompatActivity {

    private static final String TAG = "DebugTester";
    private TextView tvLog;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_viewer);

        tvLog = findViewById(R.id.tvLog);
        scrollView = findViewById(R.id.scrollViewLog);
        Button btnRefresh = findViewById(R.id.btnRefreshLog);
        Button btnClear = findViewById(R.id.btnClearLog);
        Button btnTest = findViewById(R.id.btnTestLog);

        btnRefresh.setOnClickListener(v -> loadLogcat());
        btnClear.setOnClickListener(v -> {
            try {
                Runtime.getRuntime().exec("logcat -c");
                tvLog.setText("Log cleared.");
            } catch (IOException e) {
                tvLog.setText("Failed to clear log: " + e.getMessage());
            }
        });

        btnTest.setOnClickListener(v -> {
            Log.d(TAG, "This is a DEBUG log");
            Log.i(TAG, "This is an INFO log");
            Log.w(TAG, "This is a WARNING log");
            Log.e(TAG, "This is an ERROR log");
            tvLog.append("\n[Logs written! Tap Refresh to see them]");
        });

        loadLogcat();
    }

    private void loadLogcat() {
        tvLog.setText("Loading logcat...");
        new LogcatTask().execute();
    }

    private class LogcatTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            StringBuilder sb = new StringBuilder();
            try {
                Process process = Runtime.getRuntime().exec(
                        new String[]{"logcat", "-d", "-t", "200", "-v", "time", "DebugTester:D", "*:E"});
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(process.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
            } catch (IOException e) {
                sb.append("Error reading logcat: ").append(e.getMessage());
            }
            return sb.length() > 0 ? sb.toString() : "(no logs yet — tap Test Log to generate some)";
        }

        @Override
        protected void onPostExecute(String result) {
            tvLog.setText(result);
            scrollView.post(() -> scrollView.fullScroll(ScrollView.FOCUS_DOWN));
        }
    }
}
