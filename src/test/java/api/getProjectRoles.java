package api;


import org.testng.annotations.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class getProjectRoles {

    @Test
    public void getVersion() throws IOException {
        String url = "http://localhost/jsonrpc.php";
        String method = "getProjectRoles";
        int id = 1661138292;
        String username = "jsonrpc";
        String apiToken = "db82293b8502b02b57ae392a0eeab3cb5cbcc1b828f794f8ecd9c3487b30";

        // Encode API token
        String auth = username + ":" + apiToken;
        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));
        String authHeader = "Basic " + new String(encodedAuth);

        // Request JSON
        String requestJson = "{\"jsonrpc\": \"2.0\", \"method\": \"" + method + "\", \"id\": " + id + "}";

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
