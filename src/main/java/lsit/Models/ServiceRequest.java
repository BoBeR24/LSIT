package lsit.Models;

import java.util.UUID;

public class ServiceRequest {
    public UUID id;

    public Ship.ShipModel model;
    public Ship.ShipClass shipClass;

    public Ship.EngineType engineType;
    public Ship.PowerSource powerSource;

    public double weight;

    // ship dimensions
    public double length;
    public double height;
    public double width;

    public String issueDescription;

}
