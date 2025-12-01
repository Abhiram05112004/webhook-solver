package com.bajaj.webhook_solver.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class WebhookService {

    private final RestTemplate restTemplate;

    public WebhookService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Map<String, String> generateWebhook() {
        String url = "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("name", "John Doe");
        requestBody.put("regNo", "REG12347");
        requestBody.put("email", "john@example.com");

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);

        Map<String, String> result = new HashMap<>();
        result.put("webhookUrl", (String) response.getBody().get("webhook"));
        result.put("accessToken", (String) response.getBody().get("accessToken"));

        return result;
    }

    public String solveSQLProblem() {
        return "SELECT d.DEPARTMENT_NAME, AVG(YEAR(CURDATE()) - YEAR(e.DOB)) AS AVERAGE_AGE, " +
               "GROUP_CONCAT(CONCAT(e.FIRST_NAME, ' ', e.LAST_NAME) ORDER BY e.EMP_ID SEPARATOR ', ') AS EMPLOYEE_LIST " +
               "FROM DEPARTMENT d " +
               "JOIN EMPLOYEE e ON d.DEPARTMENT_ID = e.DEPARTMENT " +
               "JOIN PAYMENTS p ON e.EMP_ID = p.EMP_ID " +
               "WHERE p.AMOUNT > 70000 " +
               "GROUP BY d.DEPARTMENT_ID " +
               "ORDER BY d.DEPARTMENT_ID DESC " +
               "LIMIT 10;";
    }

    public void submitSolution(String webhookUrl, String accessToken, String finalQuery) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);

        Map<String, String> solutionBody = new HashMap<>();
        solutionBody.put("finalQuery", finalQuery);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(solutionBody, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(webhookUrl, entity, String.class);

        System.out.println("Submission Response: " + response.getBody());
    }
}