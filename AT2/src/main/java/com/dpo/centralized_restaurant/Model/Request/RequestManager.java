package com.dpo.centralized_restaurant.Model.Request;

import java.util.ArrayList;

/**
 * Stores all the requests in the system within an ArrayList
 */
public class RequestManager {

    private ArrayList<Request> requests;

    public RequestManager(){
        this.requests = new ArrayList<>();
    }

    public RequestManager(ArrayList<Request> requests) {
        this.requests = requests;
    }

    public ArrayList<Request> getRequests() {
        return requests;
    }

    public void setRequests(ArrayList<Request> requests) {
        this.requests = requests;
    }
}
