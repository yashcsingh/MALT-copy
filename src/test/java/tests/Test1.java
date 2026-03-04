package tests;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Test1 {

    @Test
    public void testCreateNewProject() {

        // Step 1: Connect to MongoDB
        MongoClient mongo = new MongoClient("localhost", 27017);
        MongoDatabase db = mongo.getDatabase("MALT");
        MongoCollection<Document> collection = db.getCollection("ProjectData");

        // Step 2: Create test project data
        String testProjectName = "JUnitTestProject";
        String analystName = "TestAnalyst";

        Document newProject = new Document("analystname", analystName)
                .append("projectname", testProjectName)
                .append("analystemail", "test@test.com");

        // Step 3: Insert project
        collection.insertOne(newProject);

        // Step 4: Verify project exists
        Document foundProject = collection.find(new Document("projectname", testProjectName)).first();

        Assert.assertNotNull(foundProject, "Project was not created in database");

        System.out.println("Project creation test passed");

        mongo.close();
    }
}