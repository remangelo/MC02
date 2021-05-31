package ph.dlsu.s12.remudaroa.qr_maker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LocationHistoryActivity extends AppCompatActivity {


    private RecyclerView RecyclerView;
    private ArrayList<CovidTracing> covidTracingList = new ArrayList<>();
    private CovidTracing covidTracing;
    private HistoryAdapter adapter;
//    final FirebaseDatabase database = FirebaseDatabase.getInstance();
//    DatabaseReference ref = database.getReference("CovidTracker");


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("/CovidTracker/locationHistory");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_history);


        RecyclerView = findViewById(R.id.RV_locationHistory);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

        FirebaseRecyclerOptions<CovidTracing> options = new FirebaseRecyclerOptions.Builder<CovidTracing>()
                .setQuery(ref, CovidTracing.class)
                .build();

        adapter = new HistoryAdapter(options);

        RecyclerView.setLayoutManager(layoutManager);
        RecyclerView.setAdapter(adapter);
    }

    @Override protected void onStart()
    {
        super.onStart();
        adapter.startListening();
    }

    @Override protected void onStop()
    {
        super.onStop();
        adapter.stopListening();
    }


}