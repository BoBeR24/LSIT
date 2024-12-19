package lsit.Repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lsit.Models.RepairTeamReport;
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
public class RepairTeamReportRepository {
    private static final String LOCAL_PREFIX = PREFIX + "repairTeamReports/";
    private final S3Client s3client;
    private final ObjectMapper objectMapper;

    public RepairTeamReportRepository() {
        AwsCredentials awsCredentials = AwsBasicCredentials.create(ACCESS_KEY, SECRET_KEY);
        this.s3client = S3Client.builder()
                .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
                .endpointOverride(URI.create(ENDPOINT_URL))
                .region(Region.of("auto"))
                .forcePathStyle(true)
                .build();
        this.objectMapper = new ObjectMapper();
    }

    // Add a new report
    public void add(RepairTeamReport report) {
        try {
            String reportJson = objectMapper.writeValueAsString(report);
            s3client.putObject(PutObjectRequest.builder()
                            .bucket(BUCKET)
                            .key(LOCAL_PREFIX + report.reportId)
                            .build(),
                    RequestBody.fromString(reportJson));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    // Get a report by ID
    public RepairTeamReport get(UUID reportId) {
        try {
            var objectBytes = s3client.getObject(GetObjectRequest.builder()
                    .bucket(BUCKET)
                    .key(LOCAL_PREFIX + reportId)
                    .build()).readAllBytes();
            return objectMapper.readValue(objectBytes, RepairTeamReport.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Remove a report by ID
    public void remove(UUID reportId) {
        try {
            s3client.deleteObject(DeleteObjectRequest.builder()
                    .bucket(BUCKET)
                    .key(LOCAL_PREFIX + reportId)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Update an existing report
    public void update(RepairTeamReport updatedReport) {
        try {
            RepairTeamReport existingReport = get(updatedReport.reportId);
            if (existingReport != null) {
                existingReport.serviceRequestId = updatedReport.serviceRequestId;
                existingReport.serviceTeam = updatedReport.serviceTeam;
                existingReport.solvedIssues = updatedReport.solvedIssues;
                existingReport.remainingIssues = updatedReport.remainingIssues;
                existingReport.additionalNotes = updatedReport.additionalNotes;

                String updatedJson = objectMapper.writeValueAsString(existingReport);
                s3client.putObject(PutObjectRequest.builder()
                                .bucket(BUCKET)
                                .key(LOCAL_PREFIX + updatedReport.reportId)
                                .build(),
                        RequestBody.fromString(updatedJson));
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    // List all reports
    public List<RepairTeamReport> list() {
        List<RepairTeamReport> reports = new ArrayList<>();
        try {
            ListObjectsV2Request listRequest = ListObjectsV2Request.builder()
                    .bucket(BUCKET)
                    .prefix(LOCAL_PREFIX)
                    .build();

            ListObjectsV2Response listResponse = s3client.listObjectsV2(listRequest);

            for (S3Object object : listResponse.contents()) {
                String key = object.key();
                UUID reportId = UUID.fromString(key.substring(LOCAL_PREFIX.length()));
                RepairTeamReport report = get(reportId);
                if (report != null) {
                    reports.add(report);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reports;
    }
}
