/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 *
 * @author Yash
 */
package malt.view;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javax.swing.JOptionPane;
import org.bson.Document;
import org.bson.types.ObjectId;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import java.awt.Color;
import java.awt.Font;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.util.ArrayList;
import java.util.List;

public class NewProjectSetUpWindow extends javax.swing.JFrame {

    // MongoDB connection and user information
    private MongoClient mongo;
    private MongoDatabase dbconnection;
    private MongoCollection<Document> collection;
    private String userEmail;
    private String userObjectId;

    // UI Components
    private javax.swing.JTextField AnalystName;
    private javax.swing.JLabel AnalystNameLabel;
    private javax.swing.JTextField NewProjectName;
    private javax.swing.JLabel NewProjectNameLabel;
    private javax.swing.JButton jButton1;

    public NewProjectSetUpWindow() {
        initComponents();
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        // Connect to database
        connect();

        // Get user session information
        UserSession userSession = UserSession.getInstance();
        userEmail = userSession.getUserEmail();
        userObjectId = userSession.getUserObjectId();

        System.out.println("User email from NewProjectSetUpWindow: " + userEmail);
    }

    private void connect() {
        mongo = new MongoClient("localhost", 27017);
        dbconnection = mongo.getDatabase("MALT");
        collection = dbconnection.getCollection("ProjectData");
        System.out.println("NewProjectSetUpWindow connected to database");
    }

    private void initComponents() {
        // Initialize components
        NewProjectNameLabel = new javax.swing.JLabel();
        NewProjectName = new javax.swing.JTextField();
        AnalystNameLabel = new javax.swing.JLabel();
        AnalystName = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        // Set fonts
        Font labelFont = new Font("Arial", Font.BOLD, 16);
        Font inputFont = new Font("Arial", Font.PLAIN, 14);

        // Set label fonts and text
        NewProjectNameLabel.setFont(labelFont);
        NewProjectNameLabel.setText("Enter Project Name");
        AnalystNameLabel.setFont(labelFont);
        AnalystNameLabel.setText("Analyst Name");

        // Set text field fonts
        NewProjectName.setFont(inputFont);
        AnalystName.setFont(inputFont);

        // Set button properties
        jButton1.setFont(labelFont);
        jButton1.setText("Create Project");
        jButton1.setBackground(new Color(70, 130, 180)); // Steel blue
        jButton1.setForeground(Color.WHITE); // White text
        jButton1.addActionListener(this::jButton1ActionPerformed);

        // Configure layout
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        // Set horizontal layout group
        layout.setHorizontalGroup(
            layout.createParallelGroup(Alignment.CENTER) // Centering all components
                .addGroup(layout.createSequentialGroup()
                    .addGap(50) // Add left padding
                    .addGroup(layout.createParallelGroup(Alignment.CENTER)
                        .addComponent(NewProjectNameLabel)
                        .addComponent(NewProjectName, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)
                        .addComponent(AnalystNameLabel)
                        .addComponent(AnalystName, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton1, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
                    )
                    .addGap(50) // Add right padding
                )
        );

        // Set vertical layout group
        layout.setVerticalGroup(
            layout.createParallelGroup(Alignment.CENTER)
                .addGroup(layout.createSequentialGroup()
                    .addGap(50)
                    .addComponent(NewProjectNameLabel)
                    .addGap(10)
                    .addComponent(NewProjectName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGap(20)
                    .addComponent(AnalystNameLabel)
                    .addGap(10)
                    .addComponent(AnalystName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGap(30)
                    .addComponent(jButton1, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(50, Short.MAX_VALUE) // Add bottom padding
                )
        );

        pack(); // Adjust frame size based on components
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        // Retrieve input from text fields
        String AnalystNameText = AnalystName.getText().trim();
        String ProjectName = NewProjectName.getText().trim();

        // Check for empty fields
        if (AnalystNameText.isEmpty() || ProjectName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return;
        }

        // Check for duplicate project name
        if (isProjectNameDuplicate(ProjectName)) {
            JOptionPane.showMessageDialog(this, "Project name already exists.");
            return;
        }

        // Create and insert a new project document
        Document newProjectDoc = new Document("userObjectId", new ObjectId(userObjectId))
                                .append("analystemail", userEmail)
                                .append("analystname", AnalystNameText)
                                .append("projectname", ProjectName);
        collection.insertOne(newProjectDoc);

        // Retrieve the new project ObjectId
        ObjectId projectObjectId = newProjectDoc.getObjectId("_id");
        System.out.println("New project ObjectId: " + projectObjectId);

        // Update user document with the new project ObjectId
        updateUserData(projectObjectId);

        System.out.println("Project record entered into the database.");

        // Display success message and close the window
        JOptionPane.showMessageDialog(this, "Project Created");
        dispose();
    }

    private void updateUserData(ObjectId projectObjectId) {
        // Retrieve the user's document from the UserData collection
        MongoCollection<Document> userDataCollection = dbconnection.getCollection("UserData");
        Document userDocument = userDataCollection.find(Filters.eq("email", userEmail)).first();

        // Get the list of project ObjectIds from the user's document
        List<ObjectId> projectIds = userDocument.getList("projectIds", ObjectId.class);

        // Initialize projectIds list if null
        if (projectIds == null) {
            projectIds = new ArrayList<>();
        }

        // Add the new project ObjectId to the list
        projectIds.add(projectObjectId);

        // Update the user's document with the new project ObjectId
        userDataCollection.updateOne(
            Filters.eq("email", userEmail),
            Updates.set("projectIds", projectIds)
        );
    }

    private boolean isProjectNameDuplicate(String projectName) {
        // Check if the project name already exists in the collection
        Document result = collection.find(new Document("projectname", projectName)).first();
        return result != null;
    }

    // Variables declaration - do not modify
    private javax.swing.JTextField AnalystName1;
    private javax.swing.JLabel AnalystNameLabel1;
    private javax.swing.JTextField NewProjectName1;
    private javax.swing.JLabel NewProjectNameLabel1;
    private javax.swing.JButton jButton11;
    // End of variables declaration
}
