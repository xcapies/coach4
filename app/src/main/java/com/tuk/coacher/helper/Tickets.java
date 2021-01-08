package com.tuk.coacher.helper;

import java.util.ArrayList;

public class Tickets {
    private ArrayList<String> colls;
    private String UUID, destination, origin, number_of_travellers, trips, ticket_purchase_date,
            avg_arrival_date, avg_arrival_time, individual_cost, total_cost, travel_date, travel_time, distance;



    private static Tickets tick;

    private boolean live;

    public Tickets() {
    }


    public Tickets(
            String destination, String origin, String number_of_travellers, String trips,
            String ticket_purchase_date, String avg_arrival_date, String avg_arrival_time,
            String individual_cost, String total_cost, String travel_date, String travel_time,
            String distance, String UUID, boolean live) {
        this.live = live;
        this.destination = destination;
        this.origin = origin;
        this.number_of_travellers = number_of_travellers;
        this.trips = trips;
        this.ticket_purchase_date = ticket_purchase_date;
        this.avg_arrival_date = avg_arrival_date;
        this.avg_arrival_time = avg_arrival_time;
        this.individual_cost = individual_cost;
        this.total_cost = total_cost;
        this.travel_date = travel_date;
        this.travel_time = travel_time;
        this.UUID = UUID;
        this.distance = distance;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }
    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getNumber_of_travellers() {
        return number_of_travellers;
    }

    public void setNumber_of_travellers(String number_of_travellers) {
        this.number_of_travellers = number_of_travellers;
    }

    public String getTrips() {
        return trips;
    }

    public void setTrips(String trips) {
        this.trips = trips;
    }

    public String getTicket_purchase_date() {
        return ticket_purchase_date;
    }

    public void setTicket_purchase_date(String ticket_purchase_date) {
        this.ticket_purchase_date = ticket_purchase_date;
    }

    public String getAvg_arrival_date() {
        return avg_arrival_date;
    }

    public void setAvg_arrival_date(String avg_arrival_date) {
        this.avg_arrival_date = avg_arrival_date;
    }

    public String getAvg_arrival_time() {
        return avg_arrival_time;
    }

    public void setAvg_arrival_time(String avg_arrival_time) {
        this.avg_arrival_time = avg_arrival_time;
    }

    public String getIndividual_cost() {
        return individual_cost;
    }

    public void setIndividual_cost(String individual_cost) {
        this.individual_cost = individual_cost;
    }

    public String getTotal_cost() {
        return total_cost;
    }

    public void setTotal_cost(String total_cost) {
        this.total_cost = total_cost;
    }

    public String getTravel_date() {
        return travel_date;
    }

    public void setTravel_date(String travel_date) {
        this.travel_date = travel_date;
    }

    public String getTravel_time() {
        return travel_time;
    }

    public void setTravel_time(String travel_time) {
        this.travel_time = travel_time;
    }

    public boolean isLive() {
        return live;
    }
    public void setLive(boolean live) {
        this.live = live;
    }
    public static Tickets getTick() { return tick; }
    public static void setTick(Tickets tick) { Tickets.tick = tick; }
}
