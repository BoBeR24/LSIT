package lsit.Models;

import java.util.UUID;

public class ServiceRequest {
//    public int requestId; // maybe use special type for ID(e.g. UUID)

    public UUID id;
    public ShipModel model;
    public ShipClass shipClass;
    public EngineType engineType;
    public PowerSource powerSource;
    public ShipStatus shipStatus;
//    public double weight;
//
//    // ship dimensions
//    public double length;
//    public double height;
//    public double width;
    public String issueDescription;

    enum issueType{
        MINOR,
        MAJOR
    }

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
    public enum ShipStatus {
        SUBMITTED, IN_DIAGNOSTIC, IN_REPAIR, FIXED, READY_FOR_RELEASE
    }

    public ServiceRequest(String id, ShipModel shipModel,ShipClass shipClass,EngineType engineType,PowerSource powerSource,ShipStatus shipStatus){
        this.id= UUID.fromString(id);
        this.shipClass=shipClass;
        this.model=shipModel;
        this.engineType=engineType;
        this.powerSource=powerSource;
        this.shipStatus=shipStatus;
    }

    public void setShipClass(ShipClass shipClass) {
        this.shipClass = shipClass;
    }

    public void setPowerSource(PowerSource powerSource) {
        this.powerSource = powerSource;
    }

    public void setModel(ShipModel model) {
        this.model = model;
    }

    public ShipModel getModel() {
        return model;
    }

    public void setEngineType(EngineType engineType) {
        this.engineType = engineType;
    }

    public PowerSource getPowerSource() {
        return powerSource;
    }

    public ShipClass getShipClass() {
        return shipClass;
    }

    public EngineType getEngineType() {
        return engineType;
    }

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }

    public void setIssueDescription(String issueDescription) {
        this.issueDescription = issueDescription;
    }

    public String getIssueDescription() {
        return issueDescription;
    }

    public ShipStatus getShipStatus() {
        return shipStatus;
    }

    public void setShipStatus(ShipStatus shipStatus) {
        this.shipStatus = shipStatus;
    }

    @Override
    public String toString() {
        return "ServiceRequest{" +
                "requestId=" + id +
                ", model=" + model +
                ", shipClass=" + shipClass +
                ", engineType=" + engineType +
                ", powerSource=" + powerSource +
                ", issueDescription='" + issueDescription + '\'' +
                '}';
    }
}

