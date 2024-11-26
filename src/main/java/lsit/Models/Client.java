package lsit.Models;

import java.util.UUID;

public class Client {
    public String userName;
    public UUID id;
    public String password;
    public String pendingRequestId; // same ID as in ServiceRequest. Creates a link between client and his request
}