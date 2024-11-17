package lsit.Models;

public class ServiceRequest {
    public String requestId; // maybe use special type for ID(e.g. UUID)

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

    public enum ShipModel {
        BOEING, AIRBUS // just an example
    }

    public enum ShipClass {
        FIGHTER, CARGO, EXPLORER, PASSENGER
    }

    public enum EngineType {
        ION, IMPULSE,
    }

    public enum PowerSource {
        FUSION, ANTIMATTER, SOLAR
    }


}
