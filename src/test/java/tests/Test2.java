package tests;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Test2 {

    @Test
    public void testOpenProjectDisplaysCreatedProject() {

        // Step 1: Connect to MongoDB
        MongoClient mongo = new MongoClient("localhost", 27017);
        MongoDatabase db = mongo.getDatabase("MALT");
        MongoCollection<Document> collection = db.getCollection("ProjectData");

        // Step 2: Project created in Test1
        String projectName = "JUnitTestProject";

        // Step 3: Retrieve project from DB
        Document project = collection.find(new Document("projectname", projectName)).first();

        // Step 4: Verify project exists
        Assert.assertNotNull(project, "Project not found in Open Project list");

        System.out.println("Open project test passed. Project found: " + projectName);

        mongo.close();
    }
}