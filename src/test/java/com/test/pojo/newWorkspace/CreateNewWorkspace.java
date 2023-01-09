package com.test.pojo.newWorkspace;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.responseSpecification;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesPattern;

public class CreateNewWorkspace extends WorkspaceBaseTest{
    @Test
    public void postNewWorkspace() {
        Workspace workspace = new Workspace("LetsWork", "personal", "pojoStyle workspace creation");
        WorkspaceRoot workspaceRoot = new WorkspaceRoot(workspace);
        WorkspaceRoot deserializedWorkspaceroot = given()
            .body(workspaceRoot) //automatically serialized
            .when()
            .post("/workspaces")
            .then().spec(responseSpecification)
            .extract()
            .response()
            .as(WorkspaceRoot.class);

            assertThat(deserializedWorkspaceroot.getWorkspace().getName(), equalTo(workspace.getName()));
            assertThat(deserializedWorkspaceroot.getWorkspace().getId(), matchesPattern("^[a-z0-9-]{36}$"));
    }

    @Test (dataProvider = "workspace")
    public void serializeNdeserialize(String name, String type, String description ) {
        Workspace workspace = new Workspace(name, type, description);
        WorkspaceRoot workspaceRoot = new WorkspaceRoot(workspace);
        WorkspaceRoot deserializedWorkspaceRoot = given()
            .body(workspaceRoot) //automatically serialized
            .when()
            .post("/workspaces")
            .then().spec(responseSpecification)
            .extract()
            .response()
            .as(WorkspaceRoot.class);

        assertThat(deserializedWorkspaceRoot.getWorkspace().getName(), equalTo(workspace.getName()));
        assertThat(deserializedWorkspaceRoot.getWorkspace().getId(), matchesPattern("^[a-z0-9-]{36}$"));
    }

    @DataProvider(name = "workspace")
    public Object[][] getWorkspace() {
        return new Object[][] {
            {"workspace1", "personal", "description"},
            {"workspace2", "team", "description"}
        };
    }
}
