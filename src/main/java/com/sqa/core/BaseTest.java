package com.sqa.core;

import com.sqa.domain.models.Post;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import static org.testng.Assert.*;
import static org.testng.Assert.assertFalse;

public class BaseTest {

    protected ApiClient apiClient;

    @BeforeClass
    public void setUpClass(){
        // Creating an instance of the API Client
        apiClient = new ApiClient();
    }

    /**
     * Validates that the fields on the post object exist and are correct.
     * @param post The post object to validate.
     */
    protected void validatePostFields(Post post){
        // Validate the id and the user id are valid
        assertTrue(post.getId() >= 0,"Post ID should be a non-negative integer");
        assertTrue(post.getUserId() >= 0,"User ID should be a non-negative integer");

        // Validate that the title is not empty or null, and it is of the type String
        assertNotNull(post.getTitle(), "Post title should not be null");
        assertFalse(post.getTitle().isEmpty(),"Post title should not be empty");

        // Validate that the body is not empty or null, and it is of the type String
        assertNotNull(post.getBody(), "Post body should not be null");
        assertFalse(post.getBody().isEmpty(), "Post body should not be empty");
    }

    /**
     * It creates the data to be used by the test methods. Although it uses the ApiClient, it is preferred to
     * create it by direct interaction with the database whenever possible.
     * @param userId The user ID
     * @param title The post title
     * @param body The post body
     * @return the created post as an object of the type Post
     */
    protected Post createTestPost(int userId, String title, String body){
        Post testPost = new Post(userId, title, body);
        Response response = apiClient.createPost(testPost);

        // If the status code is different from 201, it throws an exception.
        if (response.statusCode() != 201){
            throw new TestSetupException("Post creation Failed");
        }
        // Return the created post as object
        return response.as(Post.class);
    }

    /**
     * It deletes the data used by the test methods. Although it uses the ApiClient, it is preferred to
     * delete it by direct interaction with the database whenever possible.
     * @param id the id of the post to delete
     */
    protected void deleteTestPost(int id){
        // Deletes the post by ID
        Response response = apiClient.deletePost(id);

        // Response should be 200; or 404 if the record was already deleted during the test.
        if (response.statusCode() != 200 && response.statusCode() != 404){
            throw new TestSetupException("Post deletion Failed");
        }
    }

}
