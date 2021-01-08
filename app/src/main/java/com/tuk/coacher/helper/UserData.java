package com.tuk.coacher.helper;

public class UserData {

    private static String destination;
    private static String origin;
    private static String number_of_travellers;
    private static String trips;
    private static String ticket_purchase_date;
    private static String avg_arrival_date;
    private static String avg_arrival_time;
    private static String individual_cost;
    private static String total_cost;
    private static String travel_date;
    private static String travel_time;

    public UserData() {
    }
//    public UserData() {
//    }

    public static String getIndividual_cost() {
        return individual_cost;
    }

    public static void setIndividual_cost(String individual_cost) {
        UserData.individual_cost = individual_cost;
    }

    public static String getTotal_cost() {
        return total_cost;
    }

    public static void setTotal_cost(String total_cost) {
        UserData.total_cost = total_cost;
    }


    public static String getTrips() {
        return trips;
    }

    public static void setTrips(String trips) {
        UserData.trips = trips;
    }

    public static String getTicket_purchase_date() {
        return ticket_purchase_date;
    }

    public static void setTicket_purchase_date(String ticket_purchase_date) {
        UserData.ticket_purchase_date = ticket_purchase_date;
    }

    public static void setAvg_arrival_date(String avg_arrival_date) {
        UserData.avg_arrival_date = avg_arrival_date;
    }

    public static String getAvg_arrival_date() {
        return avg_arrival_date;
    }

    public static String getAvg_arrival_time() {
        return avg_arrival_time;
    }

    public static void setAvg_arrival_time(String avg_arrival_time) {
        UserData.avg_arrival_time = avg_arrival_time;
    }

    public static String getTravel_date() {
        return travel_date;
    }

    public static void setTravel_date(String travel_date) {
        UserData.travel_date = travel_date;
    }

    public static String getTravel_time() {
        return travel_time;
    }

    public static void setTravel_time(String travel_time) {
        UserData.travel_time = travel_time;
    }

    public static String getDestination() {
        return destination;
    }

    public static void setDestination(String destination) {
        UserData.destination = destination;
    }

    public static String getOrigin() {
        return origin;
    }

    public static void setOrigin(String origin) {
        UserData.origin = origin;
    }

    public static String getNumber_of_travellers() {
        return number_of_travellers;
    }

    public static void setNumber_of_travellers(String number_of_travellers) {
        UserData.number_of_travellers = number_of_travellers;
    }


}

