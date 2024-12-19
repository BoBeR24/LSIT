package lsit.Repositories;

import java.net.URI;
import java.util.*;

import org.springframework.stereotype.Repository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lsit.Models.Admin;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import static lsit.SupportingClasses.CloudCredentials.*;

@Repository
public class AdminRepository {
    String LOCAL_PREFIX = PREFIX + "admins/";
    S3Client s3client;
    AwsCredentials awsCredentials;

    public AdminRepository() {
        awsCredentials = AwsBasicCredentials.create(ACCESS_KEY, SECRET_KEY);
        s3client = S3Client.builder()
                .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
                .endpointOverride(URI.create(ENDPOINT_URL))
                .region(Region.of("auto"))
                .forcePathStyle(true)
                .build();
    }

    public void add(Admin admin) {
        try {
            ObjectMapper om = new ObjectMapper();
            String adminJson = om.writeValueAsString(admin);

            s3client.putObject(PutObjectRequest.builder()
                            .bucket(BUCKET)
                            .key(LOCAL_PREFIX + admin.id)
                            .build(),
                    RequestBody.fromString(adminJson)
            );
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public Admin get(Integer id) {
        try {
            var objectBytes = s3client.getObject(GetObjectRequest.builder()
                    .bucket(BUCKET)
                    .key(LOCAL_PREFIX + id)
                    .build()
            ).readAllBytes();

            ObjectMapper om = new ObjectMapper();
            return om.readValue(objectBytes, Admin.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void remove(Integer id) {
        try {
            s3client.deleteObject(DeleteObjectRequest.builder()
                    .bucket(BUCKET)
                    .key(LOCAL_PREFIX + id)
                    .build()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(Admin admin) {
        try {
            Admin existing = get(admin.id);
            if (existing == null) return;

            ObjectMapper om = new ObjectMapper();
            String adminJson = om.writeValueAsString(admin);

            s3client.putObject(PutObjectRequest.builder()
                            .bucket(BUCKET)
                            .key(LOCAL_PREFIX + admin.id)
                            .build(),
                    RequestBody.fromString(adminJson)
            );
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public List<Admin> list() {
        List<Admin> adminList = new ArrayList<>();
        try {
            ListObjectsV2Request listRequest = ListObjectsV2Request.builder()
                    .bucket(BUCKET)
                    .prefix(LOCAL_PREFIX)
                    .build();

            ListObjectsV2Response listResponse = s3client.listObjectsV2(listRequest);

            for (S3Object object : listResponse.contents()) {
                String key = object.key();
                Integer id = Integer.parseInt(key.substring(LOCAL_PREFIX.length()));
                Admin admin = get(id);
                if (admin != null) {
                    adminList.add(admin);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return adminList;
    }
}