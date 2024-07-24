package com.sqa.core;

import com.sqa.domain.models.Post;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class ApiClient {

    private static final String BASE_URI = "https://jsonplaceholder.typicode.com";
    private static final String POSTS_ENDPOINT = "/posts";

    /**
     * Retrieves all the posts
     * @return the response object
     */
    public Response getAllPosts(){
        return given()
                .baseUri(BASE_URI)
                .when()
                .get( POSTS_ENDPOINT)
                .then()
                .extract()
                .response();

    }

    /**
     * Gets a post by its id.
     * @param id the int id of the post
     * @return the response object
     */
    public Response getPostByID(int id){
        return given()
                .baseUri(BASE_URI)
                .when()
                .get(String.format("%s/%d",POSTS_ENDPOINT,id))
                .then()
                .extract()
                .response();
    }

    /**
     * Overloaded method to get the post with an id of the type String
     * @param id the string id of the post
     * @return the response object
     */
    public Response getPostByID(String id){
        return given()
                .baseUri(BASE_URI)
                .when()
                .get(String.format("%s/%s", POSTS_ENDPOINT, id))
                .then()
                .extract()
                .response();
    }

    /**
     * Deletes the post by its id
     * @param id the int post id
     * @return the response object
     */
    public Response deletePost(int id) {
        return given()
                .baseUri(BASE_URI)
                .when()
                .delete(String.format("%s/%d", POSTS_ENDPOINT, id))
                .then()
                .extract()
                .response();
    }

    /**
     * Overload Deletes method by id of the type String
     * @param id the String post id
     * @return the response object
     */
    public Response deletePost(String id) {
        return given()
                .baseUri(BASE_URI)
                .when()
                .delete(String.format("%s/%s", POSTS_ENDPOINT, id))
                .then()
                .extract()
                .response();
    }

    /**
     * Creates a new post
     * @param testPost the Post object to be created
     * @return the response object
     */
    public Response createPost(Post testPost) {
        return given()
                .baseUri(BASE_URI)
                .contentType(ContentType.JSON)
                .body(testPost)
                .when()
                .post(POSTS_ENDPOINT)
                .then()
                .extract()
                .response();

    }

    /**
     * Patches existing post
     * @param id the id of the post to patch
     * @param patchData the stat to patach
     * @return the response object
     */
    public Response patchPost(int id, Map<String, Object> patchData) {
        return given()
                .baseUri(BASE_URI)
                .contentType(ContentType.JSON)
                .body(patchData)
                .when()
                .patch(String.format("%s/%d", POSTS_ENDPOINT, id))
                .then()
                .extract()
                .response();
    }

    /**
     * Updates a post
     * @param id the id of the post to update
     * @param post the data to update
     * @return the response object
     */
    public Response updatePost(int id,Post post) {
        return given()
                .baseUri(BASE_URI)
                .contentType(ContentType.JSON)
                .body(post)
                .when()
                .put(String.format("%s/%d", POSTS_ENDPOINT, id))
                .then()
                .extract()
                .response();
    }
}
