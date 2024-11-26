package lsit.Repositories;

import lsit.Models.ServiceRequest;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Repository
public class ServiceRequestRepository {
    static HashMap<UUID, ServiceRequest> serviceRequests = new HashMap<UUID, ServiceRequest>();

    public void add(ServiceRequest request){
        serviceRequests.put(request.id, request);
    }

    public ServiceRequest get(UUID id){
        return serviceRequests.get(id);
    }

    public void remove(UUID id){
        serviceRequests.remove(id);
    }

    public void update(ServiceRequest newRequest){
        ServiceRequest x = serviceRequests.get(newRequest.id);

        x.model = newRequest.model;
        x.shipClass = newRequest.shipClass;
        x.engineType = newRequest.engineType;
        x.powerSource = newRequest.powerSource;

        x.weight = newRequest.weight;

        x.length = newRequest.length;
        x.height = newRequest.height;
        x.width = newRequest.width;

        x.issueDescription = newRequest.issueDescription;
    }

    public List<ServiceRequest> list(){
        return new ArrayList<>(serviceRequests.values());
    }
}
