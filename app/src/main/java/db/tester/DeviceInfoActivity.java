package db.tester;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DeviceInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_info);

        TextView tvInfo = findViewById(R.id.tvDeviceInfo);
        tvInfo.setText(collectDeviceInfo());
    }

    private String collectDeviceInfo() {
        StringBuilder sb = new StringBuilder();

        sb.append("=== Device Info ===\n");
        sb.append("Brand       : ").append(Build.BRAND).append("\n");
        sb.append("Model       : ").append(Build.MODEL).append("\n");
        sb.append("Device      : ").append(Build.DEVICE).append("\n");
        sb.append("Manufacturer: ").append(Build.MANUFACTURER).append("\n");
        sb.append("Product     : ").append(Build.PRODUCT).append("\n");
        sb.append("Hardware    : ").append(Build.HARDWARE).append("\n");
        sb.append("Board       : ").append(Build.BOARD).append("\n");

        sb.append("\n=== OS Info ===\n");
        sb.append("Android Ver : ").append(Build.VERSION.RELEASE).append("\n");
        sb.append("SDK Level   : ").append(Build.VERSION.SDK_INT).append("\n");
        sb.append("Build ID    : ").append(Build.ID).append("\n");
        sb.append("Fingerprint : ").append(Build.FINGERPRINT).append("\n");
        sb.append("Incremental : ").append(Build.VERSION.INCREMENTAL).append("\n");
        sb.append("Security Patch: ").append(Build.VERSION.SECURITY_PATCH).append("\n");

        sb.append("\n=== CPU Info ===\n");
        sb.append("CPU ABI     : ").append(Build.SUPPORTED_ABIS[0]).append("\n");
        sb.append("CPU Count   : ").append(Runtime.getRuntime().availableProcessors()).append("\n");

        sb.append("\n=== Memory Info ===\n");
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        if (am != null) {
            ActivityManager.MemoryInfo memInfo = new ActivityManager.MemoryInfo();
            am.getMemoryInfo(memInfo);
            sb.append("Total RAM   : ").append(formatBytes(memInfo.totalMem)).append("\n");
            sb.append("Avail RAM   : ").append(formatBytes(memInfo.availMem)).append("\n");
            sb.append("Low Memory  : ").append(memInfo.lowMemory).append("\n");
            sb.append("Threshold   : ").append(formatBytes(memInfo.threshold)).append("\n");
        }

        sb.append("\n=== Storage Info ===\n");
        StatFs stat = new StatFs(Environment.getDataDirectory().getPath());
        long blockSize = stat.getBlockSizeLong();
        long totalBlocks = stat.getBlockCountLong();
        long availBlocks = stat.getAvailableBlocksLong();
        sb.append("Total Storage: ").append(formatBytes(totalBlocks * blockSize)).append("\n");
        sb.append("Free Storage : ").append(formatBytes(availBlocks * blockSize)).append("\n");

        sb.append("\n=== App Info ===\n");
        sb.append("App ID      : ").append(BuildConfig.APPLICATION_ID).append("\n");
        sb.append("Version     : ").append(BuildConfig.VERSION_NAME).append("\n");
        sb.append("Debug Mode  : ").append(BuildConfig.IS_DEBUG).append("\n");
        sb.append("Build Time  : ").append(BuildConfig.BUILD_TIME).append("\n");

        return sb.toString();
    }

    private String formatBytes(long bytes) {
        if (bytes < 1024) return bytes + " B";
        if (bytes < 1024 * 1024) return String.format("%.1f KB", bytes / 1024.0);
        if (bytes < 1024 * 1024 * 1024) return String.format("%.1f MB", bytes / (1024.0 * 1024));
        return String.format("%.2f GB", bytes / (1024.0 * 1024 * 1024));
    }
}
