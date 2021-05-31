package ph.dlsu.s12.remudaroa.qr_maker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;



public class HistoryAdapter extends FirebaseRecyclerAdapter<CovidTracing, HistoryAdapter.CovidViewholder> {

    public HistoryAdapter(
            @NonNull FirebaseRecyclerOptions<CovidTracing> options)
    {
        super(options);
    }

    // Function to bind the view in Card view(here
    // "person.xml") iwth data in
    // model class(here "person.class")
    @Override
    protected void
    onBindViewHolder(@NonNull CovidViewholder holder,
                     int position, @NonNull CovidTracing model)
    {

        // Add firstname from model class (here
        holder.TV_name.setText("Name: " + model.getName());
        holder.TV_address.setText("Address: " + model.getAddress());
        holder.TV_contactNum.setText("Contact Number: " + model.getContactNum());
        holder.TV_age.setText("Age: " + model.getAge());
        holder.TV_date.setText("Date: " + model.getDateTime());
        holder.TV_location.setText("Location at: " + model.getLocation());

    }

    // Function to tell the class about the Card view (here
    // "person.xml")in
    // which the data will be shown
    @NonNull
    @Override
    public CovidViewholder
    onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.location_history_row, parent, false);
        return new HistoryAdapter.CovidViewholder(view);
    }

    class CovidViewholder
            extends RecyclerView.ViewHolder {
        TextView TV_date, TV_location, TV_name, TV_address, TV_contactNum, TV_age;
        public CovidViewholder(@NonNull View itemView)
        {
            super(itemView);

            TV_name = itemView.findViewById(R.id.TV_name);
            TV_address = itemView.findViewById(R.id.TV_address);
            TV_contactNum = itemView.findViewById(R.id.TV_contactNum);
            TV_age = itemView.findViewById(R.id.TV_age);
            TV_date = itemView.findViewById(R.id.TV_date);
            TV_location = itemView.findViewById(R.id.TV_location);
        }
    }
}


