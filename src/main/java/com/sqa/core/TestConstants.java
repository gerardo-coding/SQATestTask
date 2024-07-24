package com.sqa.core;

public final class TestConstants {

    // Prevent instantiation
    private TestConstants() { }

    public static final int KNOWN_ID = 45;
    public static final int USER_ID = 5;
    public static final String TITLE = "ut numquam possimus omnis eius suscipit laudantium iure";
    public static final String BODY = "est natus reiciendis nihil possimus aut provident\nex et dolor\nrepellat pariatur est\nnobis rerum repellendus dolorem autem";
    public static final int[] NON_EXISTING_IDS = {0, 999};
    public static final String[] INVALID_IDS = {"-1", "_", "*", "2147483649"};
    public static final int[] BOUNDARY_IDS = {0, Integer.MAX_VALUE};
}
