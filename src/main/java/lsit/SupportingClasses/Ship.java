package lsit.SupportingClasses;

/** Not an actual model, used to store possible specifications for ships
 * */
public abstract class Ship {
    public String id;

    public ShipModel model;
    public ShipClass shipClass;

    public EngineType engineType;
    public PowerSource powerSource;

    public double weight;

    // ship dimensions
    public double length;
    public double height;
    public double width;

    public enum ShipModel{
        BOEING, AIRBUS // just an example
    }

    public enum ShipClass{
        FIGHTER, CARGO, EXPLORER, PASSENGER
    }

    public enum EngineType{
        ION, IMPULSE,
    }

    public enum PowerSource{
        FUSION, ANTIMATTER, SOLAR
    }
}
