package com.sqa.tests;

import com.sqa.core.BaseTest;
import com.sqa.domain.models.Post;
import io.restassured.response.Response;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static com.sqa.core.TestConstants.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class PatchPostsTests extends BaseTest {
    private Post testPost;
    private Map<String, Object> patchData;

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
    public void testPatchPostSingleField(){
        //Create the patch data
        patchData = new HashMap<>();
        patchData.put("title", "Updated Title");
        // Patch the post and validate the status code
        Response response = apiClient.patchPost(testPost.getId(),patchData);
        assertEquals(response.statusCode(),200);
        //Validate the data was patched successfully
        testPost.setTitle("Updated Title");
        Post result = response.as(Post.class);
        assertEquals(result,testPost,"The post was not updated successfully");
        //Validate the id did not change
        assertEquals(result.getId(),KNOWN_ID, "The id should remain the same");
        // Validate response header
        assertEquals(response.getHeader("Content-Type"),"application/json; charset=utf-8");
    }

    @Test
    public void testPatchPostMultipleFields(){
        //Create the patch data
        patchData = new HashMap<>();
        patchData.put("title", "Updated Title");
        patchData.put("body", "Updated Body");
        // Patch the post and validate the status code
        Response response = apiClient.patchPost(testPost.getId(),patchData);
        assertEquals(response.statusCode(),200);
        //Validate the data was patched successfully
        testPost.setTitle("Updated Title");
        testPost.setBody("Updated Body");
        Post result = response.as(Post.class);
        assertEquals(result,testPost,"The post was not updated successfully");
        //Validate the id did not change
        assertEquals(result.getId(),KNOWN_ID, "The id should remain the same");
    }

    @Test
    public void testPatchPostEmptyBody(){
        //Create the patch data
        patchData = new HashMap<>();
        // Patch the post and validate the status code
        Response response = apiClient.patchPost(testPost.getId(),patchData);
        assertEquals(response.statusCode(),200);
        //Validate the data did not change
        Post result = response.as(Post.class);
        assertEquals(result,testPost,"The post was not updated successfully");
        //Validate the id did not change
        assertEquals(result.getId(),KNOWN_ID, "The id should remain the same");
    }

    @Test
    public void testCreatePostResponseTime(){
        //Create the patch data
        patchData = new HashMap<>();
        patchData.put("title", "Updated Title");
        // Patch the post and validate the status code
        Response response = apiClient.patchPost(testPost.getId(),patchData);
        assertEquals(response.statusCode(),200);
        long responseTime = response.getTime();
        // Validating the response time. This value should be updated to match the requirements.
        assertTrue(responseTime <= 2000,"Response time should be lower than " + responseTime + "ms");
    }
}