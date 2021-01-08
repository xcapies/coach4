package com.tuk.coacher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.tuk.coacher.helper.Bus;
import com.tuk.coacher.helper.BusAdapter;
import com.tuk.coacher.helper.TicketAdapter;
import com.tuk.coacher.helper.Tickets;

import java.util.HashMap;
import java.util.List;

public class Admin_View_Bus extends Base {
    private static String TAG = "TAG";
    private FloatingActionButton live, stale;
    private ExpandableListView expandableListView;
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private DrawerLayout d_layout;
    private NavigationView nav_view;
    private ActionBarDrawerToggle toggle;
    private ExpandableListAdapter expandableListAdapter;
    private List<String> expandableListTitle;
    private HashMap<String, List<String>> expandableListDetail;
    private BusAdapter adapter;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String UUID = auth.getCurrentUser().getUid();
    private CollectionReference collectionReference = db.collection("Buses");

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "Admin_View_Bus :: onStop ");
        adapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "Admin_View_Bus :: onStart ");
        adapter.startListening();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__view__bus);

        Query query = collectionReference
                .orderBy("capacity", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Bus> options = new FirestoreRecyclerOptions.Builder<Bus>()
                .setQuery(query, Bus.class)
                .build();

        adapter = new BusAdapter(options);
        recyclerView = findViewById(R.id.hist_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.RIGHT  | ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                adapter.deleteTicket(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new TicketAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                Bus bus = documentSnapshot.toObject(Bus.class);
                String path = documentSnapshot.getReference().getPath();
                //TODO:
            }
        });
    }
}