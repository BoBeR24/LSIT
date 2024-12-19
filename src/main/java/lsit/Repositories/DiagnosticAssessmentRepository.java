package lsit.Repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lsit.Models.DiagnosticAssessment;
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
public class DiagnosticAssessmentRepository {
    private static final String LOCAL_PREFIX = PREFIX + "diagnostic-assessments/";
    private final S3Client s3client;
    private final ObjectMapper objectMapper;

    public DiagnosticAssessmentRepository() {
        AwsCredentials awsCredentials = AwsBasicCredentials.create(ACCESS_KEY, SECRET_KEY);
        this.s3client = S3Client.builder()
                .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
                .endpointOverride(URI.create(ENDPOINT_URL))
                .region(Region.of("auto"))
                .forcePathStyle(true)
                .build();
        this.objectMapper = new ObjectMapper();
    }

    public void add(DiagnosticAssessment diagnosticAssessment) {
        try {
            String diagnosticAssessmentJson = objectMapper.writeValueAsString(diagnosticAssessment);
            s3client.putObject(PutObjectRequest.builder()
                            .bucket(BUCKET)
                            .key(LOCAL_PREFIX + diagnosticAssessment.id)
                            .build(),
                    RequestBody.fromString(diagnosticAssessmentJson));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public DiagnosticAssessment get(UUID id) {
        try {
            var objectBytes = s3client.getObject(GetObjectRequest.builder()
                    .bucket(BUCKET)
                    .key(LOCAL_PREFIX + id)
                    .build()).readAllBytes();
            return objectMapper.readValue(objectBytes, DiagnosticAssessment.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

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

    public void update(DiagnosticAssessment newAssessment) {
        try {
            DiagnosticAssessment existingAssessment = get(newAssessment.id);
            if (existingAssessment != null) {
                existingAssessment.requestId = newAssessment.requestId;
                existingAssessment.authorsId = newAssessment.authorsId;
                existingAssessment.assignedTeam = newAssessment.assignedTeam;
                existingAssessment.additionalCommentary = newAssessment.additionalCommentary;

                String updatedJson = objectMapper.writeValueAsString(existingAssessment);
                s3client.putObject(PutObjectRequest.builder()
                                .bucket(BUCKET)
                                .key(LOCAL_PREFIX + newAssessment.id)
                                .build(),
                        RequestBody.fromString(updatedJson));
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public List<DiagnosticAssessment> list() {
        List<DiagnosticAssessment> assessments = new ArrayList<>();
        try {
            ListObjectsV2Request listRequest = ListObjectsV2Request.builder()
                    .bucket(BUCKET)
                    .prefix(LOCAL_PREFIX)
                    .build();

            ListObjectsV2Response listResponse = s3client.listObjectsV2(listRequest);

            for (S3Object object : listResponse.contents()) {
                String key = object.key();
                UUID id = UUID.fromString(key.substring(LOCAL_PREFIX.length()));
                DiagnosticAssessment assessment = get(id);
                if (assessment != null) {
                    assessments.add(assessment);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return assessments;
    }
}
