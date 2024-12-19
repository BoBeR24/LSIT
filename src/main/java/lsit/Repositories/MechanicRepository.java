package lsit.Repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lsit.Models.Mechanic;
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

import static lsit.SupportingClasses.CloudCredentials.*;

@Repository
public class MechanicRepository {
    private static final String LOCAL_PREFIX = PREFIX + "mechanics/";
    private final S3Client s3client;
    private final ObjectMapper objectMapper;

    public MechanicRepository() {
        AwsCredentials awsCredentials = AwsBasicCredentials.create(ACCESS_KEY, SECRET_KEY);
        this.s3client = S3Client.builder()
                .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
                .endpointOverride(URI.create(ENDPOINT_URL))
                .region(Region.of("auto"))
                .forcePathStyle(true)
                .build();
        this.objectMapper = new ObjectMapper();
    }

    public void add(Mechanic mechanic) {
        try {
            String mechanicJson = objectMapper.writeValueAsString(mechanic);
            s3client.putObject(PutObjectRequest.builder()
                            .bucket(BUCKET)
                            .key(LOCAL_PREFIX + mechanic.id)
                            .build(),
                    RequestBody.fromString(mechanicJson));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public Mechanic get(int id) {
        try {
            var objectBytes = s3client.getObject(GetObjectRequest.builder()
                    .bucket(BUCKET)
                    .key(LOCAL_PREFIX + id)
                    .build()).readAllBytes();
            return objectMapper.readValue(objectBytes, Mechanic.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void remove(int id) {
        try {
            s3client.deleteObject(DeleteObjectRequest.builder()
                    .bucket(BUCKET)
                    .key(LOCAL_PREFIX + id)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(Mechanic mechanic) {
        try {
            Mechanic existingMechanic = get(mechanic.id);
            if (existingMechanic != null) {
                existingMechanic.userName = mechanic.userName;
                existingMechanic.password = mechanic.password;

                String updatedJson = objectMapper.writeValueAsString(existingMechanic);
                s3client.putObject(PutObjectRequest.builder()
                                .bucket(BUCKET)
                                .key(LOCAL_PREFIX + mechanic.id)
                                .build(),
                        RequestBody.fromString(updatedJson));
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public List<Mechanic> list() {
        List<Mechanic> mechanics = new ArrayList<>();
        try {
            ListObjectsV2Request listRequest = ListObjectsV2Request.builder()
                    .bucket(BUCKET)
                    .prefix(LOCAL_PREFIX)
                    .build();

            ListObjectsV2Response listResponse = s3client.listObjectsV2(listRequest);

            for (S3Object object : listResponse.contents()) {
                String key = object.key();
                int id = Integer.parseInt(key.substring(LOCAL_PREFIX.length()));
                Mechanic mechanic = get(id);
                if (mechanic != null) {
                    mechanics.add(mechanic);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mechanics;
    }
}
