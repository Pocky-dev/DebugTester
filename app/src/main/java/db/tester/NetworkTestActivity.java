package db.tester;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkTestActivity extends AppCompatActivity {

    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_test);

        tvResult = findViewById(R.id.tvNetworkResult);
        Button btnPing = findViewById(R.id.btnPing);
        Button btnCheckConn = findViewById(R.id.btnCheckConn);

        // Check connectivity type
        btnCheckConn.setOnClickListener(v -> {
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            StringBuilder sb = new StringBuilder();
            sb.append("=== Connectivity Info ===\n");
            if (cm != null) {
                android.net.Network network = cm.getActiveNetwork();
                if (network != null) {
                    NetworkCapabilities caps = cm.getNetworkCapabilities(network);
                    if (caps != null) {
                        sb.append("WiFi: ").append(caps.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)).append("\n");
                        sb.append("Mobile: ").append(caps.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)).append("\n");
                        sb.append("Internet: ").append(caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)).append("\n");
                        sb.append("Validated: ").append(caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)).append("\n");
                    }
                } else {
                    sb.append("No active network\n");
                }
            }
            tvResult.setText(sb.toString());
        });

        // Ping Google
        btnPing.setOnClickListener(v -> {
            tvResult.setText("Pinging https://google.com ...");
            new PingTask().execute("https://www.google.com");
        });
    }

    private class PingTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            try {
                long start = System.currentTimeMillis();
                URL url = new URL(urls[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(5000);
                conn.setReadTimeout(5000);
                conn.connect();
                long elapsed = System.currentTimeMillis() - start;
                int code = conn.getResponseCode();
                conn.disconnect();
                return "✅ Ping success!\nURL: " + urls[0] + "\nHTTP: " + code + "\nTime: " + elapsed + "ms";
            } catch (IOException e) {
                return "❌ Ping failed!\n" + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            tvResult.setText(result);
        }
    }
}
