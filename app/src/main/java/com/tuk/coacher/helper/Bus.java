package com.tuk.coacher.helper;

public class Bus {
    private String capacity;
    private String number_plate;

    public Bus (){}
    public Bus(String capacity, String number_plate) {
        this.capacity = capacity;
        this.number_plate = number_plate;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getNumber_plate() {
        return number_plate;
    }

    public void setNumber_plate(String number_plate) {
        this.number_plate = number_plate;
    }
}
