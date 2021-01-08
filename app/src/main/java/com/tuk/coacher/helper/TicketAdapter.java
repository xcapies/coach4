package com.tuk.coacher.helper;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.tuk.coacher.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class TicketAdapter extends FirestoreRecyclerAdapter<Tickets, TicketAdapter.TicketsHolder> {
    private static String TAG = "TAG";
    private  OnItemClickListener onItemClickListener;

    public TicketAdapter(@NonNull FirestoreRecyclerOptions<Tickets> options) {
        super(options);
        Log.d(TAG, "TicketAdapter :: TicketAdapter ");
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindViewHolder(@NonNull TicketsHolder holder, int position, @NonNull Tickets model) {
        holder.destination.setText(model.getDestination());
        holder.origin.setText(model.getOrigin());
        holder.number_of_travellers.setText(model.getNumber_of_travellers());
        holder.trips.setText(model.getTrips());
        holder.ticket_purchase_date.setText(model.getTicket_purchase_date());
        holder.avg_arrival_date.setText(model.getAvg_arrival_date());
        holder.avg_arrival_time.setText(model.getAvg_arrival_time());
        holder.individual_cost.setText(model.getIndividual_cost());
        holder.total_cost.setText(model.getTotal_cost());
        holder.travel_date.setText(model.getTravel_date());
        holder.travel_time.setText(model.getTravel_time());
        holder.distance.setText(model.getDistance());
        SimpleDateFormat sdformat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            if(sdformat.parse(model.getTicket_purchase_date()).compareTo(new Date()) < 0){
                Log.d(TAG,
                        "TicketAdapter :: onBindViewHolder : 1" + "Past due");
                holder.live.setText("Past due");
                holder.live.setTextColor(Color.RED);
                holder.origin.setTextColor(Color.RED);
                holder.trips.setTextColor(Color.RED);
                holder.destination.setTextColor(Color.RED);
                holder.number_of_travellers.setTextColor(Color.RED);
                holder.ticket_purchase_date.setTextColor(Color.RED);
                holder.avg_arrival_date.setTextColor(Color.RED);
                holder.avg_arrival_time.setTextColor(Color.RED);
                holder.individual_cost.setTextColor(Color.RED);
                holder.total_cost.setTextColor(Color.RED);
                holder.travel_date.setTextColor(Color.RED);
                holder.travel_time.setTextColor(Color.RED);
                holder.distance.setTextColor(Color.RED);
            }else{
                Log.d(TAG,
                        "TicketAdapter :: onBindViewHolder : 1" + "Valid");
                holder.origin.setTextColor(Color.GREEN);
                holder.trips.setTextColor(Color.GREEN);
                holder.destination.setTextColor(Color.GREEN);
                holder.number_of_travellers.setTextColor(Color.GREEN);
                holder.ticket_purchase_date.setTextColor(Color.GREEN);
                holder.avg_arrival_date.setTextColor(Color.GREEN);
                holder.avg_arrival_time.setTextColor(Color.GREEN);
                holder.individual_cost.setTextColor(Color.GREEN);
                holder.total_cost.setTextColor(Color.GREEN);
                holder.travel_date.setTextColor(Color.GREEN);
                holder.travel_time.setTextColor(Color.GREEN);
                holder.distance.setTextColor(Color.GREEN);
                holder.live.setTextColor(Color.GREEN);
                holder.live.setText("Valid");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    public void deleteTicket(int position){
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    @NonNull
    @Override
    public TicketsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tickets1, parent,
                false);

        Log.d(TAG, "TicketAdapter :: onCreateViewHolder :: " + view.getId());
        return new TicketsHolder(view);
    }

    class TicketsHolder extends RecyclerView.ViewHolder {
        private MaterialTextView destination;
        private MaterialTextView origin;
        private MaterialTextView number_of_travellers;
        private MaterialTextView trips;
        private MaterialTextView ticket_purchase_date;
        private MaterialTextView avg_arrival_date;
        private MaterialTextView avg_arrival_time;
        private MaterialTextView individual_cost;
        private MaterialTextView total_cost;
        private MaterialTextView travel_date;
        private MaterialTextView travel_time;
        private MaterialTextView distance;
        private MaterialTextView live;
        private CardView r1;


        public TicketsHolder(@NonNull View itemView) {
            super(itemView);
            Log.d(TAG, "TicketAdapter :: TicketsHolder :: ");
            destination = itemView.findViewById(R.id.hist_ticket_destination);
            origin = itemView.findViewById(R.id.hist_ticket_origin);
            number_of_travellers = itemView.findViewById(R.id.hist_ticket_travellers);
            trips = itemView.findViewById(R.id.hist_ticket_trips);
            ticket_purchase_date = itemView.findViewById(R.id.hist_ticket_tpd);
            avg_arrival_date = itemView.findViewById(R.id.hist_ticket_a_date);
            avg_arrival_time = itemView.findViewById(R.id.hist_ticket_a_time);
            individual_cost = itemView.findViewById(R.id.hist_ticket_i_cost);
            total_cost = itemView.findViewById(R.id.hist_ticket_t_cost);
            travel_date = itemView.findViewById(R.id.hist_ticket_d_date);
            travel_time = itemView.findViewById(R.id.hist_ticket_d_time);
            distance = itemView.findViewById(R.id.hist_ticket_distance);
            live = itemView.findViewById(R.id.hist_ticket_live);
            r1 = itemView.findViewById(R.id.tickets_card);

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

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
}
