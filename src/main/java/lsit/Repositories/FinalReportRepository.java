package lsit.Repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lsit.Models.FinalReport;
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
public class FinalReportRepository {
    private static final String LOCAL_PREFIX = PREFIX + "finalReports/";
    private final S3Client s3client;
    private final ObjectMapper objectMapper;

    public FinalReportRepository() {
        AwsCredentials awsCredentials = AwsBasicCredentials.create(ACCESS_KEY, SECRET_KEY);
        this.s3client = S3Client.builder()
                .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
                .endpointOverride(URI.create(ENDPOINT_URL))
                .region(Region.of("auto"))
                .forcePathStyle(true)
                .build();
        this.objectMapper = new ObjectMapper();
    }

    public void add(FinalReport finalReport) {
        try {
            String finalReportJson = objectMapper.writeValueAsString(finalReport);
            s3client.putObject(PutObjectRequest.builder()
                            .bucket(BUCKET)
                            .key(LOCAL_PREFIX + finalReport.finalReportId)
                            .build(),
                    RequestBody.fromString(finalReportJson));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public FinalReport get(String id) {
        try {
            var objectBytes = s3client.getObject(GetObjectRequest.builder()
                    .bucket(BUCKET)
                    .key(LOCAL_PREFIX + id)
                    .build()).readAllBytes();
            return objectMapper.readValue(objectBytes, FinalReport.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void remove(String id) {
        try {
            s3client.deleteObject(DeleteObjectRequest.builder()
                    .bucket(BUCKET)
                    .key(LOCAL_PREFIX + id)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(FinalReport finalReport) {
        try {
            FinalReport existingReport = get(finalReport.finalReportId);
            if (existingReport != null) {
                existingReport.serviceRequestId = finalReport.serviceRequestId;
                existingReport.costBreakdown = finalReport.costBreakdown;
                existingReport.totalCost = finalReport.totalCost;
                existingReport.serviceStatus = finalReport.serviceStatus;
                existingReport.finalNotes = finalReport.finalNotes;
                existingReport.remainingIssues = finalReport.remainingIssues;
                existingReport.recommendations = finalReport.recommendations;

                String updatedJson = objectMapper.writeValueAsString(existingReport);
                s3client.putObject(PutObjectRequest.builder()
                                .bucket(BUCKET)
                                .key(LOCAL_PREFIX + finalReport.finalReportId)
                                .build(),
                        RequestBody.fromString(updatedJson));
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public List<FinalReport> list() {
        List<FinalReport> finalReports = new ArrayList<>();
        try {
            ListObjectsV2Request listRequest = ListObjectsV2Request.builder()
                    .bucket(BUCKET)
                    .prefix(LOCAL_PREFIX)
                    .build();

            ListObjectsV2Response listResponse = s3client.listObjectsV2(listRequest);

            for (S3Object object : listResponse.contents()) {
                String key = object.key();
                String id = key.substring(LOCAL_PREFIX.length());
                FinalReport finalReport = get(id);
                if (finalReport != null) {
                    finalReports.add(finalReport);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return finalReports;
    }
}
