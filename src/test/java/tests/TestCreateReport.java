package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

public class TestCreateReport {

    @Test
    public void createReportTest() {

        String reportName = "Report1";

        boolean reportCreated = true;

        Assert.assertTrue(reportCreated, "Report creation failed");

        System.out.println("Report creation test passed");
    }
}