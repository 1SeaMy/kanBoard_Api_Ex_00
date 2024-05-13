package api;

import org.testng.annotations.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class createProject {

    @Test
    public void createProject() throws IOException {
        String url = "http://localhost/jsonrpc.php";
        String method = "createProject";
        int id = 1111111111; // Dikkat: ID bir sayı olmalıdır, sabit bir değer verildi.
        String username = "admin";
        String apiToken = "admin";

        // Encode API token
        String auth = username + ":" + apiToken;
        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));
        String authHeader = "Basic " + new String(encodedAuth);

        // Request JSON
        String requestJson = "{\"jsonrpc\": \"2.0\", \"method\": \"" + method + "\", \"id\": " + id + ", \"params\": {\"name\": \"MYSX\"}}";

        // Create connection
        URL endpoint = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) endpoint.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Authorization", authHeader);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        // Send request
        try (OutputStream outputStream = connection.getOutputStream()) {
            byte[] input = requestJson.getBytes(StandardCharsets.UTF_8);
            outputStream.write(input, 0, input.length);
        }

        // Get response
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (var reader = new java.io.BufferedReader(new java.io.InputStreamReader(connection.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                System.out.println(response.toString());
            }
        } else {
            System.out.println("Error: " + responseCode);
        }
    }
}
