package lsit.Repositories;

import java.net.URI;
import java.util.*;

import org.springframework.stereotype.Repository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lsit.Models.Assembler;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import static lsit.SupportingClasses.CloudCredentials.*;

@Repository
public class AssemblerRepository {
    String LOCAL_PREFIX = PREFIX + "assemblers/";
    S3Client s3client;
    AwsCredentials awsCredentials;

    public AssemblerRepository() {
        awsCredentials = AwsBasicCredentials.create(ACCESS_KEY, SECRET_KEY);
        s3client = S3Client.builder()
                .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
                .endpointOverride(URI.create(ENDPOINT_URL))
                .region(Region.of("auto"))
                .forcePathStyle(true)
                .build();
    }

    public void add(Assembler assembler) {
        try {
            ObjectMapper om = new ObjectMapper();
            String assemblerJson = om.writeValueAsString(assembler);

            s3client.putObject(PutObjectRequest.builder()
                            .bucket(BUCKET)
                            .key(LOCAL_PREFIX + assembler.id)
                            .build(),
                    RequestBody.fromString(assemblerJson)
            );
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public Assembler get(Integer id) {
        try {
            var objectBytes = s3client.getObject(GetObjectRequest.builder()
                    .bucket(BUCKET)
                    .key(LOCAL_PREFIX + id)
                    .build()
            ).readAllBytes();

            ObjectMapper om = new ObjectMapper();
            return om.readValue(objectBytes, Assembler.class);
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

    public void update(Assembler assembler) {
        try {
            Assembler existing = get(assembler.id);
            if (existing == null) return;

            ObjectMapper om = new ObjectMapper();
            String assemblerJson = om.writeValueAsString(assembler);

            s3client.putObject(PutObjectRequest.builder()
                            .bucket(BUCKET)
                            .key(LOCAL_PREFIX + assembler.id)
                            .build(),
                    RequestBody.fromString(assemblerJson)
            );
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public List<Assembler> list() {
        List<Assembler> assemblerList = new ArrayList<>();
        try {
            ListObjectsV2Request listRequest = ListObjectsV2Request.builder()
                    .bucket(BUCKET)
                    .prefix(LOCAL_PREFIX)
                    .build();

            ListObjectsV2Response listResponse = s3client.listObjectsV2(listRequest);

            for (S3Object object : listResponse.contents()) {
                String key = object.key();
                Integer id = Integer.parseInt(key.substring(LOCAL_PREFIX.length()));
                Assembler assembler = get(id);
                if (assembler != null) {
                    assemblerList.add(assembler);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return assemblerList;
    }
}