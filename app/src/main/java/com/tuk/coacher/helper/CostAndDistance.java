package com.tuk.coacher.helper;

public class CostAndDistance {
    private String origin, destination;
    private int nums;

    private int distance, individualCost, totalCost;

    public CostAndDistance(String origin, String destination, int nums){
        this.origin = origin;
        this.destination = destination;
        this.nums = nums;
        calculate();
    }

    private void calculate(){
        distance = 10000;
        individualCost = 2000;
        totalCost = individualCost * nums;
    }
    public int getDistance(){
        return distance;
    }

    public int getIndividualCost(){
        return individualCost;
    }

    public int getTotalCost(){
        return totalCost;
    }
}
