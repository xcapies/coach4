package com.tuk.coacher;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.tuk.coacher.helper.TicketAdapter;
import com.tuk.coacher.helper.Tickets;

import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class History extends Base {
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
    private TicketAdapter adapter;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String UUID = auth.getCurrentUser().getUid();
    private CollectionReference collectionReference = db.collection("Bookings");
//    private DocumentReference documentReference = db.collection("Bookings").document(UUID);

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "History :: onStop ");
        adapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "History :: onStart ");
        adapter.startListening();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "History :: onCreate ");
        setContentView(R.layout.activity_history);
//        setProgressBar(R.id.hist_progressBar);
        live = findViewById(R.id.hist_btn_back);
        live.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        Query query = collectionReference
                .orderBy("ticket_purchase_date", Query.Direction.DESCENDING)
                .whereEqualTo("uuid", UUID);
        FirestoreRecyclerOptions<Tickets> options = new FirestoreRecyclerOptions.Builder<Tickets>()
                .setQuery(query, Tickets.class)
                .build();

        adapter = new TicketAdapter(options);
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
                Tickets tickets = documentSnapshot.toObject(Tickets.class);
                String path = documentSnapshot.getReference().getPath();
                //TODO:
            }
        });
    }


}
