package com.oskarskalski.cms;

import java.util.Date;

public class TestData {
    public static final String TEST_FIRST_NAME = "testFirstName" + new Date();
    public static final String TEST_LAST_NAME = "testLastName" + new Date();
    public static final String TEST_EMAIL = "test@email.email" + new Date();
    public static final String TEST_PASSWORD = "testPassword";

    public static final String TEST_JWT = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhdXRoMCIsImlkIjoiMiIsImF1dGhvcml0aWVzIjoiW10ifQ.ewsL4KNYrmuY-d4ickcQ6A6hp_UmD0s9AhlNsVhck8M";

    public static final String TEST_TEAM_ID = "2969f66d-a5b7-4da9-9c76-90818eba294e";
    public static final String TEST_TEAM_NAME = "testTeamName";
    public static final String TEST_TEAM_DESCRIPTION = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
            .substring(0, 90);
}
