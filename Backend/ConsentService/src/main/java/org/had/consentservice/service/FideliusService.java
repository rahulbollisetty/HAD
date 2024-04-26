package org.had.consentservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class FideliusService {

    private static final Logger log = LoggerFactory.getLogger(FideliusService.class);

    @Autowired
    private ResourceLoader resourceLoader;

    public JsonNode generateKeyMaterials() throws IOException {
        List<String> command = new ArrayList<>();
        String fideliusCliPath = resourceLoader.getResource("classpath:fidelius-cli/bin/fidelius-cli").getFile().getAbsolutePath();
        command.add(fideliusCliPath);
        command.add("gkm");
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        Process process = processBuilder.start();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new RuntimeException("Failed to execute shell script with exit code: " + exitCode);
            }

            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readTree(output.toString());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public String decrypt(String stringToDecrypt,String senderNonce, String requesterNonce, String requesterPrivateKey, String senderPublicKey) throws IOException, InterruptedException {

        File tempFile = File.createTempFile("fidelius_decrypt_params", ".txt");
        try (PrintWriter writer = new PrintWriter(new FileWriter(tempFile))) {
            writer.println("decrypt");
            writer.println(stringToDecrypt);
            writer.println(requesterNonce);
            writer.println(senderNonce);
            writer.println(requesterPrivateKey);
            writer.println(senderPublicKey);
        }

        List<String> command = new ArrayList<>();
        String fideliusCliPath = resourceLoader.getResource("classpath:fidelius-cli/bin/fidelius-cli").getFile().getAbsolutePath();
        command.add(fideliusCliPath);
        command.add("-f");
        command.add(tempFile.getAbsolutePath());

        ProcessBuilder processBuilder = new ProcessBuilder(command);
        Process process = processBuilder.start();

        // Read the output from the shell script
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                log.error("Failed to execute shell script with exit code");
                throw new RuntimeException("Failed to execute shell script with exit code: " + exitCode);
            }
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode decryptedData = objectMapper.readTree(output.toString());
            return decryptedData.get("decryptedData").textValue();

        } finally {
            // Delete the file after encryption
            new File(tempFile.getAbsolutePath()).delete();
        }
    }
}
