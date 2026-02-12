package malt.view;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import org.bson.Document;

public class OpenProjectPage extends javax.swing.JFrame {

    private String userEmail;
    private MongoClient mongo;
    private MongoDatabase dbconnection;
    private MongoCollection<org.bson.Document> collection;

    // Panel to hold buttons
    private JPanel buttonPanel1;

    public OpenProjectPage() {
        initComponents();
        setResizable(false);
        setLocationRelativeTo(null);
        connect();
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        // Get the current user session
        UserSession userSession = UserSession.getInstance();
        userEmail = userSession.getUserEmail();

        System.out.println("User email from OpenProjectPage: " + userEmail);
        
        // Display projects as buttons
        displayProjectsAsButtons();
    }

    private void connect() {
        mongo = new MongoClient("localhost", 27017);
        dbconnection = mongo.getDatabase("MALT");
        collection = dbconnection.getCollection("ProjectData");
        System.out.println("OpenProjectPage Connected to Database");
    }

    /**
     * Displays projects as buttons in the button panel.
     */
    private void displayProjectsAsButtons() {
        FindIterable<Document> result = collection.find(new Document("analystemail", userEmail));

        ArrayList<Document> documents = new ArrayList<>();
        result.into(documents);

        System.out.println("Number of documents retrieved: " + documents.size());

        // Create a vertical BoxLayout for the panel
        buttonPanel1.setLayout(new BoxLayout(buttonPanel1, BoxLayout.Y_AXIS));

        // Iterate through each document to create buttons
        for (Document doc : documents) {
            System.out.println("Project Name: " + doc.getString("projectname"));
            
            // Create a button for each project
            JButton button = new JButton(doc.getString("projectname"));
            Dimension buttonSize = new Dimension(250, 60); // Set preferred size
            button.setPreferredSize(buttonSize);
            button.setMaximumSize(buttonSize);
            button.setMinimumSize(buttonSize);
            
            // Customize button appearance
            button.setBackground(new Color(70, 130, 180)); // Steel blue
            button.setForeground(Color.WHITE); // White text
            button.setFont(new Font("Arial", Font.BOLD, 14)); // Font style
            
            // Add padding to buttons
            button.setBorder(new EmptyBorder(10, 20, 10, 20));
            
            // Center button horizontally
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            // Add action listener to the button
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Open ProjectWorkingPage and pass the ObjectId
                    String objectId = doc.getObjectId("_id").toString();
                    ProjectWorkingPage projectWorkingPage = new ProjectWorkingPage(objectId);
                    projectWorkingPage.setVisible(true);
                    dispose();
                }
            });

            // Add the button to the button panel
            buttonPanel1.add(button);
            
            // Add some vertical spacing between buttons
            buttonPanel1.add(Box.createVerticalStrut(10));
        }
        
        // Refresh the panel layout
        buttonPanel1.revalidate();
        buttonPanel1.repaint();
    }

    /**
     * Initializes the UI components of the OpenProjectPage.
     */
    @SuppressWarnings("unchecked")
    private void initComponents() {
        // Create the scroll pane and button panel
        jScrollPane1 = new JScrollPane();
        buttonPanel1 = new JPanel();

        // Set default frame properties
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(400, 600)); // Set preferred size

        // Set button panel properties
        buttonPanel1.setPreferredSize(new Dimension(400, 600)); // Match preferred size
        buttonPanel1.setLayout(new BoxLayout(buttonPanel1, BoxLayout.Y_AXIS));
        buttonPanel1.setBorder(new EmptyBorder(20, 20, 20, 20)); // Add padding to the panel

        // Add button panel to scroll pane
        jScrollPane1.setViewportView(buttonPanel1);
        jScrollPane1.setBorder(null); // Remove border around scroll pane for a cleaner look

        // Create the layout for the main frame
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack(); // Adjust frame size based on components
    }

    // Variables declaration
    private javax.swing.JScrollPane jScrollPane1;
    // Custom button panel for project buttons
    private JPanel buttonPanel;
    // End of variables declaration
}
