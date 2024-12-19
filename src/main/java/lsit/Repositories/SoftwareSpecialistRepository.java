package lsit.Repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lsit.Models.SoftwareSpecialist;
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
public class SoftwareSpecialistRepository {

    private static final String LOCAL_PREFIX = PREFIX + "softwareSpecialists/";
    private final S3Client s3client;
    private final ObjectMapper objectMapper;

    public SoftwareSpecialistRepository() {
        AwsCredentials awsCredentials = AwsBasicCredentials.create(ACCESS_KEY, SECRET_KEY);
        this.s3client = S3Client.builder()
                .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
                .endpointOverride(URI.create(ENDPOINT_URL))
                .region(Region.of("auto"))
                .forcePathStyle(true)
                .build();
        this.objectMapper = new ObjectMapper();
    }

    // Add a new software specialist
    public void add(SoftwareSpecialist softwareSpecialist) {
        try {
            String specialistJson = objectMapper.writeValueAsString(softwareSpecialist);
            s3client.putObject(PutObjectRequest.builder()
                            .bucket(BUCKET)
                            .key(LOCAL_PREFIX + softwareSpecialist.id)
                            .build(),
                    RequestBody.fromString(specialistJson));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    // Get a software specialist by ID
    public SoftwareSpecialist get(int id) {
        try {
            var objectBytes = s3client.getObject(GetObjectRequest.builder()
                    .bucket(BUCKET)
                    .key(LOCAL_PREFIX + id)
                    .build()).readAllBytes();
            return objectMapper.readValue(objectBytes, SoftwareSpecialist.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Remove a software specialist by ID
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

    // Update an existing software specialist
    public void update(SoftwareSpecialist softwareSpecialist) {
        try {
            SoftwareSpecialist existing = get(softwareSpecialist.id);
            if (existing != null) {
                existing.userName = softwareSpecialist.userName;
                existing.password = softwareSpecialist.password;

                String updatedJson = objectMapper.writeValueAsString(existing);
                s3client.putObject(PutObjectRequest.builder()
                                .bucket(BUCKET)
                                .key(LOCAL_PREFIX + softwareSpecialist.id)
                                .build(),
                        RequestBody.fromString(updatedJson));
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    // List all software specialists
    public List<SoftwareSpecialist> list() {
        List<SoftwareSpecialist> specialists = new ArrayList<>();
        try {
            ListObjectsV2Request listRequest = ListObjectsV2Request.builder()
                    .bucket(BUCKET)
                    .prefix(LOCAL_PREFIX)
                    .build();

            ListObjectsV2Response listResponse = s3client.listObjectsV2(listRequest);

            for (S3Object object : listResponse.contents()) {
                String key = object.key();
                int id = Integer.parseInt(key.substring(LOCAL_PREFIX.length()));
                SoftwareSpecialist specialist = get(id);
                if (specialist != null) {
                    specialists.add(specialist);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return specialists;
    }
}
