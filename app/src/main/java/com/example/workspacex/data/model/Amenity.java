package com.example.workspacex.data.model;

public class Amenity {
    int roomId;
    String amenityDetails;

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getRoomId() {
        return roomId;
    }

    public String getAmenityDetails() {
        return amenityDetails;
    }

    public void setAmenityDetails(String amenityDetails) {
        this.amenityDetails = amenityDetails;
    }
}

