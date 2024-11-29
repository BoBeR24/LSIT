//package lsit.Repositories;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lsit.Models.Client;
//import org.springframework.context.annotation.Primary;
//import org.springframework.stereotype.Repository;
//import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
//import software.amazon.awssdk.auth.credentials.AwsCredentials;
//import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
//import software.amazon.awssdk.core.sync.RequestBody;
//import software.amazon.awssdk.regions.Region;
//import software.amazon.awssdk.services.s3.S3Client;
//import software.amazon.awssdk.services.s3.model.*;
//
//import java.net.URI;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//
////@Primary
//@Repository
//public class S3ClientRepository implements IClientRepository {
//    final String BUCKET="starship-mechanic-shop";
//    final String PREFIX="clientstore/clients/";
//    final String ACCESS_KEY=""; // can be received from google cloud bucket settings
//    final String SECRET_KEY=""; // from the same place as ACCESS_KEY
//    final String ENDPOINT_URL="https://storage.googleapis.com";
//
//    S3Client s3client;
//    AwsCredentials awsCredentials;
//
//    public S3ClientRepository(){
//        awsCredentials = AwsBasicCredentials.create(ACCESS_KEY, SECRET_KEY);
//        s3client = S3Client.builder()
//            .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
//            .endpointOverride(URI.create(ENDPOINT_URL))
//            .region(Region.of("auto"))
//            .build();
//    }
//
//    public void add(Client client){
//        try{
//            client.id = UUID.randomUUID();
//
//            ObjectMapper om = new ObjectMapper();
//
//            String clientJson = om.writeValueAsString(client);
//
//            s3client.putObject(PutObjectRequest.builder()
//                .bucket(BUCKET)
//                .key(PREFIX + client.id.toString())
//                .build(),
//                RequestBody.fromString(clientJson)
//            );
//        }
//        catch(JsonProcessingException e){}
//    }
//
//    public Client get(UUID id){
//        try{
//            var objectBytes = s3client.getObject(GetObjectRequest.builder()
//                .bucket(BUCKET)
//                .key(PREFIX + id.toString())
//                .build()
//            ).readAllBytes();
//
//            ObjectMapper om = new ObjectMapper();
//            Client client = om.readValue(objectBytes, Client.class);
//
//            return client;
//        }catch(Exception e){
//            return null;
//        }
//    }
//
//    public void remove(UUID id){
//        s3client.deleteObject(DeleteObjectRequest.builder()
//            .bucket(BUCKET)
//            .key(PREFIX + id.toString())
//            .build()
//        );
//    }
//
//    public void update(Client client){
//        try{
//            Client x = this.get(client.id);
//            if(x == null) return;
//
//            ObjectMapper om = new ObjectMapper();
//            String clientJson = om.writeValueAsString(client);
//            s3client.putObject(PutObjectRequest.builder()
//                .bucket(BUCKET)
//                .key(PREFIX + client.id.toString())
//                .build(),
//                RequestBody.fromString(clientJson)
//            );
//        }
//        catch(JsonProcessingException e){}
//    }
//
//    public List<Client> list(){
//        List<Client> clients = new ArrayList<Client>();
//        List<S3Object> objects = s3client.listObjects(ListObjectsRequest.builder()
//          .bucket(BUCKET)
//          .prefix(PREFIX)
//          .build()
//        ).contents();
//
//        for(S3Object o : objects){
//            Client client = new Client();
//            //client = this.get(UUID.fromString(o.key().substring(PREFIX.length())));
//            client.id = UUID.fromString(o.key().substring(PREFIX.length()));
//            clients.add(client);
//        }
//
//        return clients;
//    }
//}
