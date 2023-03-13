package ru.ilin;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.testcontainers.containers.FixedHostPortGenericContainer;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import ru.ilin.dto.AddCompanyRequest;
import ru.ilin.dto.BuySharesRequest;
import ru.ilin.dto.RegisterUserRequest;
import ru.ilin.dto.UserSharesResponse;
import ru.ilin.model.Company;
import ru.ilin.model.User;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class MainTest {
    private ObjectMapper objectMapper;
    private HttpClient httpClient;

    @ClassRule
    public static GenericContainer webServer = new FixedHostPortGenericContainer("test-testcontainers:1.0-SNAPSHOT")
        .withFixedExposedPort(8080, 8080)
        .withExposedPorts(8080);

    @Before
    public void setUp() {
        objectMapper = new ObjectMapper();
        httpClient = HttpClient.newHttpClient();
    }

    @After
    public void tearDown() {
        httpClient = null;
    }

    private HttpResponse<String> postRequest(String endpoint, Object body) throws
                                                                           URISyntaxException,
                                                                           IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(new URI("http://localhost:8080" + endpoint))
            .POST(HttpRequest.BodyPublishers.ofByteArray(objectMapper.writeValueAsBytes(body)))
            .header("Content-type", "application/json")
            .build();
        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private HttpResponse<String> postRequest(String endpoint) throws
                                                                           URISyntaxException,
                                                                           IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(new URI("http://localhost:8080" + endpoint))
            .POST(HttpRequest.BodyPublishers.noBody())
            .header("Content-type", "application/json")
            .build();
        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private HttpResponse<String> putRequest(String endpoint, Object body) throws
                                                                          URISyntaxException,
                                                                          IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(new URI("http://localhost:8080" + endpoint))
            .PUT(HttpRequest.BodyPublishers.ofByteArray(objectMapper.writeValueAsBytes(body)))
            .header("Content-type", "application/json")
            .build();
        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private HttpResponse<String> putRequest(String endpoint) throws
                                                             URISyntaxException,
                                                             IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(new URI("http://localhost:8080" + endpoint))
            .PUT(HttpRequest.BodyPublishers.noBody())
            .header("Content-type", "application/json")
            .build();
        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private HttpResponse<String> getRequest(String endpoint) throws
                                                             URISyntaxException,
                                                             IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(new URI("http://localhost:8080" + endpoint))
            .GET()
            .header("Content-type", "application/json")
            .build();
        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    @Test
    public void testCompanyAdd() throws URISyntaxException, IOException, InterruptedException {
        HttpResponse<String> response = postRequest("/exchange/company", new AddCompanyRequest("test", 10, 5.0));
        Company company = objectMapper.readValue(response.body(), Company.class);
        Assert.assertEquals(201, response.statusCode());
        Assert.assertEquals("test", company.getName());
        Assert.assertEquals(10, company.getSharesCount());
        Assert.assertEquals(5.0, company.getSharesPrice(), 0.0001);
        HttpResponse<String> response1 = postRequest("/exchange/company", new AddCompanyRequest("test", 10, 5.0));
        Assert.assertEquals(HttpStatus.CONFLICT.value(), response1.statusCode());
    }

    @Test
    public void testCompanyGet() throws Exception {
        postRequest("/exchange/company", new AddCompanyRequest("test", 10, 5.0));
        HttpResponse<String> response = getRequest("/exchange/company?company_name=test");
        Company company = objectMapper.readValue(response.body(), Company.class);
        Assert.assertEquals(200, response.statusCode());
        Assert.assertEquals("test", company.getName());
        Assert.assertEquals(10, company.getSharesCount());
        Assert.assertEquals(5.0, company.getSharesPrice(), 0.0001);

        HttpResponse<String> response1 = putRequest(String.format(
            "/exchange/%s/shares?new_price=1.0",
            company.getId()
        ));
        company = objectMapper.readValue(response1.body(), Company.class);
        Assert.assertEquals(200, response1.statusCode());
        Assert.assertEquals("test", company.getName());
        Assert.assertEquals(10, company.getSharesCount());
        Assert.assertEquals(1.0, company.getSharesPrice(), 0.0001);
        response = getRequest("/exchange/company?company_name=test");
        Assert.assertEquals(200, response.statusCode());
        Company company1 = objectMapper.readValue(response.body(), Company.class);
        Assert.assertEquals(company.getId(), company1.getId());
        Assert.assertEquals(company.getName(), company1.getName());
        Assert.assertEquals(company.getSharesCount(), company1.getSharesCount());
        Assert.assertEquals(company.getSharesPrice(), company1.getSharesPrice(), 0.0001);
    }

    @Test
    public void testUser() throws Exception {
        HttpResponse<String> response = postRequest("/exchange/company", new AddCompanyRequest("test1", 10, 5.0));
        Company company = objectMapper.readValue(response.body(), Company.class);
        response = postRequest("/users/register", new RegisterUserRequest(0.0));
        Assert.assertEquals(201, response.statusCode());
        User user = objectMapper.readValue(response.body(), User.class);
        Assert.assertEquals(0.0, user.getBalance(), 0.0001);
        Assert.assertEquals(List.of(), user.getShares());
        response = postRequest(String.format("/users/%s/balance?count=30.0", user.getId()));
        Assert.assertEquals(200, response.statusCode());
        User user1 = objectMapper.readValue(response.body(), User.class);
        Assert.assertEquals(user.getId(), user1.getId());
        Assert.assertEquals(user.getShares(), user1.getShares());
        Assert.assertEquals(30.0, user1.getBalance(), 0.0001);
        response = getRequest(String.format("/users/%s/balance", user.getId()));
        Assert.assertEquals(30.0, Double.parseDouble(response.body()), 0.0001);

        response = postRequest("/shares/buy", new BuySharesRequest(user.getId(), company.getId(), 7));
        Assert.assertEquals(400, response.statusCode());

        response = postRequest("/shares/buy", new BuySharesRequest(user.getId(), company.getId(), 5));
        Assert.assertEquals(200, response.statusCode());

        response = getRequest("/exchange/company?company_name=test1");
        Assert.assertEquals(200, response.statusCode());
        Company company1 = objectMapper.readValue(response.body(), Company.class);
        Assert.assertEquals(company.getId(), company1.getId());
        Assert.assertEquals(company.getName(), company1.getName());
        Assert.assertEquals(company.getSharesPrice(), company1.getSharesPrice(), 0.0001);
        Assert.assertEquals(company.getSharesCount() - 5, company1.getSharesCount());

        response = getRequest(String.format("/users/%s/balance", user.getId()));
        Assert.assertEquals(200, response.statusCode());
        Assert.assertEquals(30.0, Double.parseDouble(response.body()), 0.0001);

        response = getRequest(String.format("/shares/%s", user.getId()));
        Assert.assertEquals(200, response.statusCode());
        UserSharesResponse userSharesResponse = objectMapper.readValue(response.body(), UserSharesResponse.class);
        List<UserSharesResponse.ShareResponse> shareResponses = userSharesResponse.getShareResponse();
        Assert.assertEquals(1, shareResponses.size());
        Assert.assertEquals(user.getId(), shareResponses.get(0).getUserId());
        Assert.assertEquals(company.getId(), shareResponses.get(0).getCompanyId());
        Assert.assertEquals(5, shareResponses.get(0).getAmount());
        Assert.assertEquals(5.0, shareResponses.get(0).getPrice(), 0.0001);
    }

}