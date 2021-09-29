package com.oskarskalski.cms;

import java.util.Date;

public class TestData {
    public static final String TEST_FIRST_NAME = "testFirstName" + new Date();
    public static final String TEST_LAST_NAME = "testLastName" + new Date();
    public static final String TEST_EMAIL = "test@email.email" + new Date();
    public static final String TEST_PASSWORD = "testPassword";
    public static final long TEST_ID = 10;

    public static final String TEST_JWT = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhdXRoMCIsImlkIjoiMTAiLCJhdXRob3JpdGllcyI6IltdIn0.LoeIUKp5j3MBAHgnCYPjoapJjTnyG6cvgCRKJX1PQvA";

    public static final String TEST_TEAM_ID = "0c4b6fd7-d528-4189-9d2d-465ea64a7166";
    public static final String TEST_TEAM_NAME = "testTeamName";
    public static final String TEST_TEAM_DESCRIPTION = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
            .substring(0, 90);
    public static final String TEST_ARTICLE_CONTENT = "At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium voluptatum deleniti atque corrupti quos dolores et quas molestias excepturi sint occaecati cupiditate non provident, similique sunt in culpa qui officia deserunt mollitia animi, id est laborum et dolorum fuga. Et harum quidem rerum facilis est et expedita distinctio. Nam libero tempore, cum soluta nobis est eligendi optio cumque nihil impedit quo minus id quod maxime placeat facere possimus, omnis voluptas assumenda est, omnis dolor repellendus. Temporibus autem quibusdam et aut officiis debitis aut rerum necessitatibus saepe eveniet ut et voluptates repudiandae sint et molestiae non recusandae. Itaque earum rerum hic tenetur a sapiente delectus, ut aut reiciendis voluptatibus maiores alias consequatur aut perferendis doloribus asperiores repellat.";
    public static final String TEST_ARTICLE_TITLE = "Quis autem vel eum iure reprehenderit qui in ea voluptat";
    public static final String TEST_ARTICLE_ID = "60eb3ae2-8b90-49ab-b98a-c2f94127bef3";
}
