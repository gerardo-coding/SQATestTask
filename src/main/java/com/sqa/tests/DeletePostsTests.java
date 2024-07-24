package com.sqa.tests;

import com.sqa.core.BaseTest;
import com.sqa.domain.models.Post;
import io.restassured.response.Response;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static com.sqa.core.TestConstants.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
public class DeletePostsTests extends BaseTest {

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
        For this example, we are setting the record ID as a known existing value we can delete.
         */
        testPost.setId(KNOWN_ID);
    }

    @AfterMethod
    public void teardown(){
        // Makes sure the data we created is deleted.
        deleteTestPost(testPost.getId());
    }

    @Test
    public void testDeletePost(){
        // Delete the post and validate the status code
        Response response = apiClient.deletePost(testPost.getId());
        assertEquals(response.getStatusCode(),200);
        // Validate response header
        assertEquals(response.getHeader("Content-Type"),"application/json; charset=utf-8");
        // Validate it returns empty
        String responseBody = response.getBody().asString();
        assertEquals(responseBody, "{}", "Response should be an empty object");
    }

    @Test
    public void testDeletePostResponseTime(){
        // Delete the post and validate the status code
        Response response = apiClient.deletePost(testPost.getId());
        assertEquals(response.getStatusCode(),200);
        // Validating the response time. This value should be updated to match the requirements.
        long responseTime = response.getTime();
        assertTrue(responseTime <= 2000,"Response time should be lower than " + responseTime + "ms");
    }

    @Test
    public void testDeleteNonExistingPost(){

        //TODO:Need clarification. It returns a 200 success status even if the post does not exist. The Test will fail

        Response response = apiClient.deletePost(NON_EXISTING_IDS[0]);
        assertEquals(response.statusCode(),404);
    }

    @Test
    public void testDeletePostInvalidId(){

        //TODO:Need clarification. It returns a 200 success status even with invalid id. The Test will fail
        for (String id:INVALID_IDS) {
            Response response = apiClient.deletePost(id);
            assertEquals(response.statusCode(), 404);
        }
    }




}
