package com.sqa.tests;

import com.sqa.core.ApiClient;
import com.sqa.core.BaseTest;
import com.sqa.domain.models.Post;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.List;
import static org.testng.Assert.*;

public class GetAllPostsTests extends BaseTest {

    private int MIN_RESULT_COUNT;
    @BeforeMethod
    public void setup(){
        /*
        For this example, we know we have 100 records. In a real scenario, we implement a mechanism to guarantee we have
        enough data
         */
        MIN_RESULT_COUNT = 100;
    }

    private final ApiClient apiClient = new ApiClient();

    @Test
    public void testGetAllPosts(){
        // Gets all the posts and validates the status code
        Response response = apiClient.getAllPosts();
        assertEquals(response.getStatusCode(),200);
        // Validates retrieved the expected minimum record count.
        List<Post> results = response.jsonPath().getList("",Post.class);
        assertTrue(results.size() >= MIN_RESULT_COUNT, String.format("Number of results should be at least %d",MIN_RESULT_COUNT));
        // Validates the results fields are correct.
        for (Post post : results) {
            validatePostFields(post);
        }
    }

    @Test
    public void testGetAllPostsResponseTime(){
        // Gets all the posts and validates the status code
        Response response = apiClient.getAllPosts();
        assertEquals(response.getStatusCode(),200);
        // Validating the response time. This value should be updated to match the requirements.
        long responseTime = response.getTime();
        assertTrue(responseTime <= 2000,"Response time should be lower than " + responseTime + "ms");
    }
}
