package com.bajaj.webhook_solver;

import com.bajaj.webhook_solver.service.WebhookService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@SpringBootApplication
public class WebhookSolverApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebhookSolverApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public CommandLineRunner run(WebhookService webhookService) {
		return args -> {
			// Step 1: Generate webhook
			Map<String, String> webhookData = webhookService.generateWebhook();
			String webhookUrl = webhookData.get("webhookUrl");
			String accessToken = webhookData.get("accessToken");

			// Step 2: Solve SQL problem
			String finalQuery = webhookService.solveSQLProblem();

			// Step 3: Submit solution
			webhookService.submitSolution(webhookUrl, accessToken, finalQuery);
		};
	}
}
