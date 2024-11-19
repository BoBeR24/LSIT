package lsit.Models;

public class Client {
    public String userName;
    public int userID;

    public Client(String userName,int userID){
        this.userName=userName;
        this.userID=userID;
    }

    public void submitRequest(ServiceRequest ship){
        System.out.println(userName + " submitted ship: " + ship);
        ship.setShipStatus(ServiceRequest.ShipStatus.IN_DIAGNOSTIC);
    }

    public int getUserID() {
        return userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "Client{" +
                "userName='" + userName + '\'' +
                ", userID=" + userID +
                '}';
    }
}
