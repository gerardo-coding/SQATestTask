package com.sqa.tests;

import com.sqa.core.BaseTest;
import com.sqa.domain.models.Post;
import io.restassured.response.Response;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.sqa.core.TestConstants.*;
import static org.testng.Assert.*;

public class GetPostsByIDTests extends BaseTest {

    private Post testPost;

    @BeforeMethod
    public void setup(){
        /*
        Creates the post to be used by each test method to isolate the test data, prevent conflict with other tests,
        and guarantee the data's existence.
         */
        testPost = createTestPost(USER_ID,TITLE,BODY);
        /*
        IMPORTANT: The JSON placeholder does not allow data updates, so the created record is not actually added.
        For this example, we are setting the record ID as a known existing value use.
         */
        testPost.setId(KNOWN_ID);
    }

    @AfterMethod
    public void teardown(){
        // Makes sure the data we created is deleted.
        deleteTestPost(testPost.getId());
    }

    @Test
    public void testGetPostById(){
        // Gets all the posts and validates the status code
        Response response = apiClient.getPostByID(testPost.getId());
        assertEquals(response.getStatusCode(),200);
        //Validates the response fields are correct
        Post result = response.as(Post.class);
        validatePostFields(result);
    }

    @Test
    public void testGetByIdResponseData(){
        // Gets the Post by the reference ID
        Response response = apiClient.getPostByID(testPost.getId());
        assertEquals(response.getStatusCode(),200);
        Post result = response.as(Post.class);

        // Validate the result matches the test post object
        assertEquals(result, testPost, "The result object does not match the reference object");
    }

    @Test
    public void testGetByIdNonExistingIds(){
        for(int id: NON_EXISTING_IDS){
            Response response = apiClient.getPostByID(id);

            // Validate it returns the NOT FOUND Http status code
            assertEquals(response.getStatusCode(),404);

            // Validate response body is an empty object.
            String responseBody = response.getBody().asString();
            assertEquals(responseBody, "{}", "Response should be an empty object");
        }
    }

    @Test
    public void testGetByIdInvalidIds(){
        for(String id:INVALID_IDS){
            Response response = apiClient.getPostByID(id);
            // Validate it returns NOT FOUND
            assertEquals(response.getStatusCode(),404);
        }
    }

    @Test
    public void testGetByIdValidBoundaryID(){
        for(int id: BOUNDARY_IDS){
            Response response = apiClient.getPostByID(id);
            // Validate it returns the NOT FOUND Http status code
            assertEquals(response.getStatusCode(),404);
        }
    }

}
