package lsit.Models;

import lsit.SupportingClasses.Ship;

import java.util.UUID;

public class ServiceRequest {
    public UUID id; // this id will be used to refer to particular repair case through the whole application
    public int clientId;

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
