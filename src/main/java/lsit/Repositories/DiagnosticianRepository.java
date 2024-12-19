package lsit.Repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lsit.Models.Diagnostician;
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
public class DiagnosticianRepository {
    private static final String LOCAL_PREFIX = PREFIX + "diagnosticians/";
    private final S3Client s3client;
    private final ObjectMapper objectMapper;

    public DiagnosticianRepository() {
        AwsCredentials awsCredentials = AwsBasicCredentials.create(ACCESS_KEY, SECRET_KEY);
        this.s3client = S3Client.builder()
                .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
                .endpointOverride(URI.create(ENDPOINT_URL))
                .region(Region.of("auto"))
                .forcePathStyle(true)
                .build();
        this.objectMapper = new ObjectMapper();
    }

    public void add(Diagnostician diagnostician) {
        try {
            String diagnosticianJson = objectMapper.writeValueAsString(diagnostician);
            s3client.putObject(PutObjectRequest.builder()
                            .bucket(BUCKET)
                            .key(LOCAL_PREFIX + diagnostician.id)
                            .build(),
                    RequestBody.fromString(diagnosticianJson));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public Diagnostician get(int id) {
        try {
            var objectBytes = s3client.getObject(GetObjectRequest.builder()
                    .bucket(BUCKET)
                    .key(LOCAL_PREFIX + id)
                    .build()).readAllBytes();
            return objectMapper.readValue(objectBytes, Diagnostician.class);
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

    public void update(Diagnostician diagnostician) {
        try {
            Diagnostician existingDiagnostician = get(diagnostician.id);
            if (existingDiagnostician != null) {
                existingDiagnostician.userName = diagnostician.userName;
                existingDiagnostician.password = diagnostician.password;

                String updatedJson = objectMapper.writeValueAsString(existingDiagnostician);
                s3client.putObject(PutObjectRequest.builder()
                                .bucket(BUCKET)
                                .key(LOCAL_PREFIX + diagnostician.id)
                                .build(),
                        RequestBody.fromString(updatedJson));
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public List<Diagnostician> list() {
        List<Diagnostician> diagnosticians = new ArrayList<>();
        try {
            ListObjectsV2Request listRequest = ListObjectsV2Request.builder()
                    .bucket(BUCKET)
                    .prefix(LOCAL_PREFIX)
                    .build();

            ListObjectsV2Response listResponse = s3client.listObjectsV2(listRequest);

            for (S3Object object : listResponse.contents()) {
                String key = object.key();
                int id = Integer.parseInt(key.substring(LOCAL_PREFIX.length()));
                Diagnostician diagnostician = get(id);
                if (diagnostician != null) {
                    diagnosticians.add(diagnostician);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return diagnosticians;
    }
}
