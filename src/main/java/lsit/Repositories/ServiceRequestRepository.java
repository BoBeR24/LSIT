package lsit.Repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lsit.Models.ServiceRequest;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static lsit.SupportingClasses.CloudCredentials.*;

@Repository
public class ServiceRequestRepository {

    private static final String LOCAL_PREFIX = PREFIX + "serviceRequests/";
    private final S3Client s3client;
    private final ObjectMapper objectMapper;

    public ServiceRequestRepository() {
        AwsCredentials awsCredentials = AwsBasicCredentials.create(ACCESS_KEY, SECRET_KEY);
        this.s3client = S3Client.builder()
                .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
                .endpointOverride(URI.create(ENDPOINT_URL))
                .region(Region.of("auto"))
                .forcePathStyle(true)
                .build();
        this.objectMapper = new ObjectMapper();
    }

    // Add a new service request
    public void add(ServiceRequest request) {
        try {
            String requestJson = objectMapper.writeValueAsString(request);
            s3client.putObject(PutObjectRequest.builder()
                            .bucket(BUCKET)
                            .key(LOCAL_PREFIX + request.id)
                            .build(),
                    RequestBody.fromString(requestJson));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    // Get a service request by ID
    public ServiceRequest get(UUID id) {
        try {
            var objectBytes = s3client.getObject(GetObjectRequest.builder()
                    .bucket(BUCKET)
                    .key(LOCAL_PREFIX + id)
                    .build()).readAllBytes();
            return objectMapper.readValue(objectBytes, ServiceRequest.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Remove a service request by ID
    public void remove(UUID id) {
        try {
            s3client.deleteObject(DeleteObjectRequest.builder()
                    .bucket(BUCKET)
                    .key(LOCAL_PREFIX + id)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Update an existing service request
    public void update(ServiceRequest newRequest) {
        try {
            ServiceRequest existingRequest = get(newRequest.id);
            if (existingRequest != null) {
                existingRequest.model = newRequest.model;
                existingRequest.shipClass = newRequest.shipClass;
                existingRequest.engineType = newRequest.engineType;
                existingRequest.powerSource = newRequest.powerSource;
                existingRequest.weight = newRequest.weight;
                existingRequest.length = newRequest.length;
                existingRequest.height = newRequest.height;
                existingRequest.width = newRequest.width;
                existingRequest.issueDescription = newRequest.issueDescription;

                String updatedJson = objectMapper.writeValueAsString(existingRequest);
                s3client.putObject(PutObjectRequest.builder()
                                .bucket(BUCKET)
                                .key(LOCAL_PREFIX + newRequest.id)
                                .build(),
                        RequestBody.fromString(updatedJson));
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    // List all service requests
    public List<ServiceRequest> list() {
        List<ServiceRequest> requests = new ArrayList<>();
        try {
            ListObjectsV2Request listRequest = ListObjectsV2Request.builder()
                    .bucket(BUCKET)
                    .prefix(LOCAL_PREFIX)
                    .build();

            ListObjectsV2Response listResponse = s3client.listObjectsV2(listRequest);

            for (S3Object object : listResponse.contents()) {
                String key = object.key();
                UUID id = UUID.fromString(key.substring(LOCAL_PREFIX.length()));
                ServiceRequest request = get(id);
                if (request != null) {
                    requests.add(request);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return requests;
    }
}
