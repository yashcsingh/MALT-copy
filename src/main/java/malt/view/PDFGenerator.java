/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Yash
 */

package malt.view;

import java.io.IOException;
import java.util.List;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.element.Image;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;


public class PDFGenerator {

    public void createPDF(String filePath, String projectName, String analystName, String fileName,
                          String md5Hash, String sha256Hash, boolean isExecutable, long rawSize, long virtualSize,
                          long sizeDifference, List<String> dllFiles, List<String> ipAddresses, List<String> urls, String analystNotes, List<org.bson.Document> imageArtifacts, String analystNotesforDA, List<org.bson.Document> DAimageArtifacts) throws IOException {
        // Create a new PDF writer and document
        PdfWriter writer = new PdfWriter(filePath);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        // Add the title at the top of the document
        Paragraph title = new Paragraph("Static Analysis Report")
            .setTextAlignment(TextAlignment.CENTER)
            .setFontSize(20)
            .setBold();
        document.add(title);

        // Add some space between title and the rest of the document
        document.add(new Paragraph("\n"));

        // Add the project name and analyst name
        document.add(new Paragraph(new Text("Project Name: ").setBold()).add(projectName));
        document.add(new Paragraph(new Text("Analyst Name: ").setBold()).add(analystName));

        // Add file details
        document.add(new Paragraph(new Text("File Name: ").setBold()).add(fileName));
        document.add(new Paragraph(new Text("MD5 Hash: ").setBold()).add(md5Hash));
        document.add(new Paragraph(new Text("SHA-256 Hash: ").setBold()).add(sha256Hash));
        
        // Create a Paragraph instance for "Is Executable: " and set it to bold
        Paragraph isExecutableParagraph = new Paragraph()
            .add(new Text("Is Executable: ").setBold())
            .add(isExecutable ? "Yes" : "No");

        // Add the Paragraph instance to the document
        document.add(isExecutableParagraph);
        
        document.add(new Paragraph(new Text("Raw File Size: ").setBold()).add(String.format("%d bytes", rawSize)));
        document.add(new Paragraph(new Text("Virtual File Size: ").setBold()).add(String.format("%d bytes", virtualSize)));
        document.add(new Paragraph(new Text("Size Difference: ").setBold()).add(String.format("%d bytes", sizeDifference)));

        // Add DLL files
        document.add(new Paragraph(new Text("DLL Files / Libraries Used:").setBold()));
        for (String dllFile : dllFiles) {
            document.add(new Paragraph("- " + dllFile));
        }

        // Add IP addresses
        document.add(new Paragraph(new Text("IP Addresses:").setBold()));
        for (String ipAddress : ipAddresses) {
            document.add(new Paragraph("- " + ipAddress));
        }

        // Add URLs
        document.add(new Paragraph(new Text("URLs:").setBold()));
        for (String url : urls) {
            document.add(new Paragraph("- " + url));
        }
        
        // Add analyst's notes
        document.add(new Paragraph("Analyst's Notes:").setBold());
        document.add(new Paragraph(analystNotes));

        // Add a section for Artifacts
        document.add(new Paragraph("\nArtifacts:").setBold());

        // Add images under the Artifact section
        for (org.bson.Document artifact : imageArtifacts) {
            // Retrieve the filename from the artifact
            String filename = artifact.getString("filename");

            // Retrieve the binary data from the artifact as an org.bson.types.Binary object
            org.bson.types.Binary binaryData = artifact.get("data", org.bson.types.Binary.class);
            
            // Check if binary data is not null
            if (binaryData != null) {
                byte[] data = binaryData.getData();

                // Create ImageData from the binary data
                ImageData imgData = ImageDataFactory.create(data);

                // Create an Image object from the ImageData
                Image image = new Image(imgData);

                // Calculate the scale factor for the image to fit within the available width
                float availableWidth = document.getPdfDocument().getDefaultPageSize().getWidth() - document.getLeftMargin() - document.getRightMargin();
                float scaleFactor = availableWidth / image.getImageScaledWidth();

                // Scale the image to fit within the available width
                image.scale(scaleFactor, scaleFactor);

                // Add the filename as a paragraph
                document.add(new Paragraph(filename));

                // Add the scaled image to the document
                document.add(image);

                // Add a small space after each image
                document.add(new Paragraph("\n"));
            } else {
                System.err.println("No binary data found for artifact: " + filename);
            }
        }
        
        
        //Section for Dynamic Analysis
        
        // Add the title at the top of the document
        Paragraph subtitle = new Paragraph("Dynamic Analysis Report")
            .setTextAlignment(TextAlignment.CENTER)
            .setFontSize(20)
            .setBold();
        document.add(subtitle);
        
        // Add analyst's notes
        document.add(new Paragraph("Analyst's Notes for Dynamic Analysis:").setBold());
        document.add(new Paragraph(analystNotesforDA));

        // Add a section for Artifacts
        document.add(new Paragraph("\nDynamic Analysis Artifacts:").setBold());

        // Add images under the Artifact section
        for (org.bson.Document artifact : DAimageArtifacts) {
            // Retrieve the filename from the artifact
            String filename = artifact.getString("filename");

            // Retrieve the binary data from the artifact as an org.bson.types.Binary object
            org.bson.types.Binary binaryData = artifact.get("data", org.bson.types.Binary.class);
            
            // Check if binary data is not null
            if (binaryData != null) {
                byte[] data = binaryData.getData();

                // Create ImageData from the binary data
                ImageData imgData = ImageDataFactory.create(data);

                // Create an Image object from the ImageData
                Image image = new Image(imgData);

                // Calculate the scale factor for the image to fit within the available width
                float availableWidth = document.getPdfDocument().getDefaultPageSize().getWidth() - document.getLeftMargin() - document.getRightMargin();
                float scaleFactor = availableWidth / image.getImageScaledWidth();

                // Scale the image to fit within the available width
                image.scale(scaleFactor, scaleFactor);

                // Add the filename as a paragraph
                document.add(new Paragraph(filename));

                // Add the scaled image to the document
                document.add(image);

                // Add a small space after each image
                document.add(new Paragraph("\n"));
            } else {
                System.err.println("No binary data found for artifact: " + filename);
            }   
        }        
        // Close the document
        document.close();
    }
}
