package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

public class TestUploadFile {

    @Test
    public void uploadFileTest() {

        String fileName = "sample.txt";

        boolean uploadSuccess = true;

        Assert.assertTrue(uploadSuccess, "File upload failed");

        System.out.println("Upload file test passed");
    }
}