package com.example.permissiona_ex1;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
public class MainActivity extends AppCompatActivity {

    private FrameLayout panel_FRAME_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initiate the state of each condition
        findeViews();
        // Inject fragment
        Fragment_main fragment_main = new Fragment_main();
        getSupportFragmentManager().beginTransaction().replace(R.id.panel_FRAME_content, fragment_main).commit();

    }

    private void findeViews() {
        panel_FRAME_content = findViewById(R.id.panel_FRAME_content);
    }




}