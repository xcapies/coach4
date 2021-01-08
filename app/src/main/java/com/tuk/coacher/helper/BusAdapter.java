package com.tuk.coacher.helper;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.tuk.coacher.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BusAdapter extends FirestoreRecyclerAdapter<Bus, BusAdapter.BusHolder> {
    private static String TAG = "TAG";
    private TicketAdapter.OnItemClickListener onItemClickListener;

    public BusAdapter(@NonNull FirestoreRecyclerOptions<Bus> options) {
        super(options);
        Log.d(TAG, "TicketAdapter :: TicketAdapter ");
    }
    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindViewHolder(@NonNull BusAdapter.BusHolder holder, int position,
                                    @NonNull Bus model) {
        holder.number_plate.setText(model.getNumber_plate());
        holder.capacity.setText(model.getCapacity());
    }
    public void deleteTicket(int position){
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    @NonNull
    @Override
    public BusAdapter.BusHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bus1, parent,
                false);

        Log.d(TAG, "BusAdapter :: onCreateViewHolder :: " + view.getId());
        return new BusAdapter.BusHolder(view);
    }
    class BusHolder extends RecyclerView.ViewHolder {
        private TextView number_plate;
        private TextView capacity;
        public BusHolder(@NonNull View itemView) {
            super(itemView);
            Log.d(TAG, "TicketAdapter :: TicketsHolder :: ");
            number_plate = itemView.findViewById(R.id.bus_numplate_tv);
            capacity = itemView.findViewById(R.id.bus_capacity_tv);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION && onItemClickListener != null){
                        onItemClickListener.onItemClick(getSnapshots().getSnapshot(pos), pos);
                    }
                }
            });
        }

    }
    public interface OnItemClickListener{
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListener(TicketAdapter.OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
}
