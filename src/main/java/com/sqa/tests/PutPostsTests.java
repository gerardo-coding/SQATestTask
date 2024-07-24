package com.sqa.tests;

import com.sqa.core.BaseTest;
import com.sqa.domain.models.Post;
import io.restassured.response.Response;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Map;

import static com.sqa.core.TestConstants.*;
import static com.sqa.core.TestConstants.KNOWN_ID;
import static org.testng.Assert.assertEquals;

public class PutPostsTests extends BaseTest {

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
    public void testPutUpdateExistingPost(){
        // Updating the existing testPost object and validate status code
        testPost.setTitle("Updated tile");
        testPost.setBody("Updated Body");
        testPost.setUserId(222);
        Response response = apiClient.updatePost(testPost.getId(), testPost);
        assertEquals(response.statusCode(),200);
        //Validate the record was updated
        Post result = response.as(Post.class);
        assertEquals(result, testPost, "The post was not updated successfully");
        //Validate the id did not change
        assertEquals(result.getId(), testPost.getId());

    }

    @Test
    public void testPutNonExistingPost(){
        // Create an object with an id that does not exist
        Post newPost = new Post(123,2,"Test title", "Test body");
        // Put the record and validate the status code is 201 Created
        Response response = apiClient.updatePost(newPost.getId(), newPost);
        assertEquals(response.statusCode(),201);
        //Validate the record was created successfully
        Post result = response.as(Post.class);
        assertEquals(result, testPost, "The post was not created successfully");
    }
}
