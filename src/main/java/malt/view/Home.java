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
import javax.swing.JLabel;
import org.bson.Document;
import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.GroupLayout;

public class Home extends javax.swing.JFrame {

    // MongoDB connection and collection
    private MongoClient mongo;
    private MongoDatabase dbconnection;
    private MongoCollection<Document> collection;

    public Home() {
        initComponents();
        setResizable(false);
        connectToDatabase();
        customizeUI();
    }

    private void connectToDatabase() {
        mongo = new MongoClient("localhost", 27017);
        dbconnection = mongo.getDatabase("MALT");
        collection = dbconnection.getCollection("UserData");
        System.out.println("Home Page Connected to Database");
    }

    private void customizeUI() {
        // Customizing colors
        Color backgroundColor = new Color(240, 240, 240); // Light gray background
        Color buttonColor = new Color(70, 130, 180); // Steel blue for buttons
        Color textColor = new Color(255, 255, 255); // White text on buttons
        Color labelColor = new Color(70, 70, 70); // Dark gray color for label text
        
        // Customizing fonts
        Font buttonFont = new Font("Arial", Font.BOLD, 20); // Larger button font
        Font labelFont = new Font("Arial", Font.PLAIN, 16); // Larger label font
        
        // Customizing borders
        Border buttonBorder = BorderFactory.createLineBorder(buttonColor, 1); // Button border
        
        // Applying customizations
        NewProjectBtnOnScreen.setBackground(buttonColor);
        NewProjectBtnOnScreen.setForeground(textColor);
        NewProjectBtnOnScreen.setFont(buttonFont);
        NewProjectBtnOnScreen.setBorder(buttonBorder);
        NewProjectBtnOnScreen.setPreferredSize(new java.awt.Dimension(200, 60)); // Increased button size
        
        OpenProjectBtnOnScreen.setBackground(buttonColor);
        OpenProjectBtnOnScreen.setForeground(textColor);
        OpenProjectBtnOnScreen.setFont(buttonFont);
        OpenProjectBtnOnScreen.setBorder(buttonBorder);
        OpenProjectBtnOnScreen.setPreferredSize(new java.awt.Dimension(200, 60)); // Increased button size
        
        // Setting background color of the frame
        getContentPane().setBackground(backgroundColor);
        
        // Customizing explanatory label
        descriptionLabel.setFont(labelFont);
        descriptionLabel.setForeground(labelColor);
        descriptionLabel.setText("\"MALT\" simplifies malware analysis.");
        descriptionLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        
        // Set layout configurations
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        
        // Horizontal Group
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.CENTER) // Centering components horizontally
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE) // Even space on both sides
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(descriptionLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(OpenProjectBtnOnScreen, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                            .addGap(50, 50, 50) // Space between buttons
                            .addComponent(NewProjectBtnOnScreen, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                        )
                    )
                    .addGap(0, 0, Short.MAX_VALUE)
                )
        );
        
        // Vertical Group
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.CENTER) // Centering components vertically
                .addGroup(layout.createSequentialGroup()
                    .addGap(50, 50, 50) // Padding from the top
                    .addComponent(descriptionLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGap(30, 30, 30) // Space below label
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(OpenProjectBtnOnScreen, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
                        .addComponent(NewProjectBtnOnScreen, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
                    )
                    .addContainerGap(50, Short.MAX_VALUE) // Padding at the bottom
                )
        );
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        // Initializing UI components
        NewProjectBtnOnScreen = new javax.swing.JButton();
        OpenProjectBtnOnScreen = new javax.swing.JButton();
        descriptionLabel = new javax.swing.JLabel();
        jMenuBar2 = new javax.swing.JMenuBar();
        FileBtn = new javax.swing.JMenu();
        NewProjectBtn = new javax.swing.JMenuItem();
        OpenProjectBtn = new javax.swing.JMenuItem();
        HelpBtn = new javax.swing.JMenu();

        // Setting default close operation
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Home");
        setPreferredSize(new java.awt.Dimension(800, 500));
        
        // New Project button setup
        NewProjectBtnOnScreen.setText("New Project");
        NewProjectBtnOnScreen.setPreferredSize(new java.awt.Dimension(200, 60));
        NewProjectBtnOnScreen.addActionListener(evt -> NewProjectBtnOnScreenActionPerformed(evt));

        // Open Project button setup
        OpenProjectBtnOnScreen.setText("Open Project");
        OpenProjectBtnOnScreen.setPreferredSize(new java.awt.Dimension(200, 60));
        OpenProjectBtnOnScreen.addActionListener(evt -> OpenProjectBtnOnScreenActionPerformed(evt));

        // File menu setup
        FileBtn.setText("File");
        NewProjectBtn.setText("New Project");
        NewProjectBtn.addActionListener(evt -> NewProjectBtnActionPerformed(evt));
        FileBtn.add(NewProjectBtn);
        OpenProjectBtn.setText("Open Project");
        OpenProjectBtn.addActionListener(evt -> OpenProjectBtnActionPerformed(evt));
        FileBtn.add(OpenProjectBtn);
        jMenuBar2.add(FileBtn);

        // Help menu setup
        HelpBtn.setText("Help");
        jMenuBar2.add(HelpBtn);

        // Setting the menu bar
        setJMenuBar(jMenuBar2);
        
        // Customizing layout (already included above)
        customizeUI();

        pack();
    }

    private void NewProjectBtnOnScreenActionPerformed(java.awt.event.ActionEvent evt) {
        // Open the New Project Setup window
        NewProjectSetUpWindow projectWindow = new NewProjectSetUpWindow();
        projectWindow.setVisible(true);
    }

    private void OpenProjectBtnOnScreenActionPerformed(java.awt.event.ActionEvent evt) {
        // Open the Open Project Page window
        OpenProjectPage openProjectPageWindow = new OpenProjectPage();
        openProjectPageWindow.setVisible(true);
    }

    private void NewProjectBtnActionPerformed(java.awt.event.ActionEvent evt) {
        // Open the New Project Setup window from menu
        NewProjectSetUpWindow projectWindow = new NewProjectSetUpWindow();
        projectWindow.setVisible(true);
    }

    private void OpenProjectBtnActionPerformed(java.awt.event.ActionEvent evt) {
        // Open the Open Project Page window from menu
        OpenProjectPage openProjectPageWindow = new OpenProjectPage();
        openProjectPageWindow.setVisible(true);
    }

    // Variables declaration
    private javax.swing.JLabel descriptionLabel; // Label to explain the tool's purpose
    private javax.swing.JMenu FileBtn;
    private javax.swing.JMenu HelpBtn;
    private javax.swing.JMenuItem NewProjectBtn;
    private javax.swing.JButton NewProjectBtnOnScreen;
    private javax.swing.JMenuItem OpenProjectBtn;
    private javax.swing.JButton OpenProjectBtnOnScreen;
    private javax.swing.JMenuBar jMenuBar2;
    // End of variables declaration
}

