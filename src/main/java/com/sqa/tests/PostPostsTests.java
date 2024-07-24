package com.sqa.tests;

import com.sqa.core.BaseTest;
import com.sqa.domain.models.Post;
import io.restassured.response.Response;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.concurrent.atomic.AtomicInteger;

import static org.testng.Assert.*;

public class PostPostsTests extends BaseTest {

    // Index to make user id, title and body unique
    private static final AtomicInteger userIdCounter = new AtomicInteger(1);
    private Post testPost;

    @BeforeMethod
    public void setup(){
        // Creates the post to create with unique user ID, title and body
        int uniqueUserId = userIdCounter.getAndIncrement();
        String uniqueTitle = "Test title " + uniqueUserId;
        String uniqueBody = "Test body " + uniqueUserId;
        testPost = new Post(uniqueUserId, uniqueTitle, uniqueBody);
    }

    @AfterMethod
    public void teardown(){
        // Makes sure the data we created is deleted.
        deleteTestPost(testPost.getId());
    }

    @Test
    public void testCreatePostResultFields(){
        // Create post and validate status code
        Response response = apiClient.createPost(testPost);
        assertEquals(response.getStatusCode(),201);
        // Validate the response fields are correct
        Post result = response.as(Post.class);
        validatePostFields(result);
        // Validate response header
        assertEquals(response.getHeader("Content-Type"),"application/json; charset=utf-8");
    }

    @Test
    public void testCreatePostResultData(){
        Response response = apiClient.createPost(testPost);
        assertEquals(response.getStatusCode(),201);
        Post result = response.as(Post.class);
        // Validate the id is valid
        assertTrue(result.getId() > 0);
        // Validate the result matches the reference post object
        assertEquals(result, testPost, "The result object does not match the reference object");
    }

    @Test
    public void testCreatePostResponseTime(){
        Response response = apiClient.createPost(testPost);
        assertEquals(response.getStatusCode(),201);
        long responseTime = response.getTime();
        // Validating the response time. This value should be updated to match the requirements.
        assertTrue(responseTime <= 2000,"Response time should be lower than " + responseTime + "ms");
    }

    @Test
    public void testCreatePostEmptyBody(){
        /*
        TODO:Need clarification. It is returning a success status even if the post object is empty.
        The test will expect 400 Bad Request until this is clarified.
         */
        Post emptyPost = new Post();
        Response response = apiClient.createPost(emptyPost);
        assertEquals(response.statusCode(),400);
    }
}