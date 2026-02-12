/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package malt.view;

import org.bson.Document;

import javax.swing.JFileChooser;
import java.io.File;
import org.bson.Document;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import java.awt.Component;
import java.awt.Image;
import java.awt.Window;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author Yash
 */
public class ProjectWorkingPage extends javax.swing.JFrame {

    /**
     * Creates new form ProjectWorkingPage
     */
	
	String projectId;
        String projectName;
        String analystName;
	
	
	
    public ProjectWorkingPage(String objectId) {
        initComponents();
        setLocationRelativeTo(null);
        setResizable(false);
        connect();
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        this.projectId = objectId;
        
        // Call the new method to initialize the fields with data when the page loads
        initializeFields();
    }
    
    private void initializeFields() {
        // Retrieve project details from the database
        Document projectDoc = collection.find(Filters.eq("_id", new org.bson.types.ObjectId(projectId))).first();
        if (projectDoc != null) {
            projectName = projectDoc.getString("projectname");
            analystName = projectDoc.getString("analystname");
            String md5Hash = projectDoc.getString("md5Hash");
            String sha256Hash = projectDoc.getString("sha256Hash");
            String fileName = projectDoc.getString("fileName");
            Boolean isExecutable = projectDoc.getBoolean("isExecutable");
            Long rawSize = projectDoc.getLong("rawSize");
            Long virtualSize = projectDoc.getLong("virtualSize");
            List<String> dllFiles = projectDoc.getList("dllFiles", String.class);
            List<String> ipAddresses = projectDoc.getList("ipAddresses", String.class);
            List<String> urls = projectDoc.getList("urls", String.class);
            String analystNotes = projectDoc.getString("analystNotes");
            List<Document> artifacts = projectDoc.getList("artifact", Document.class);
            String analystDANotes = projectDoc.getString("analystNotesforDA");
            List<Document> DAartifacts = projectDoc.getList("DAartifact", Document.class);

            // Update GUI components with retrieved data, only if the value is not null
            if (projectName != null) {
                projectNameLabel.setText("Project Name: " + projectName);
            }

            if (analystName != null) {
                analystNameLabel.setText("Analyst Name: " + analystName);
            }

            if (fileName != null) {
                FileNameTxt1.setText(fileName);
            }

            if (md5Hash != null) {
                MD5Txt.setText(md5Hash);
            }

            if (sha256Hash != null) {
                SHA256Txt.setText(sha256Hash);
            }

            if (isExecutable != null) {
                FileTypeTxt.setText(isExecutable ? "Executable (.exe)" : "Non-executable");
            }

            if (rawSize != null) {
                RawFileSizeTxt.setText(Long.toString(rawSize));
            }

            if (virtualSize != null) {
                VirtualFileSizeTxt.setText(Long.toString(virtualSize));
            }

            // Clear existing lists before adding new items
            DllFilesListtxt.removeAll();
            IPAddListTxt.removeAll();
            URLsListTxt.removeAll();

            // Add DLL files to the list if the list is not null
            if (dllFiles != null) {
                dllFiles.forEach(dllFile -> DllFilesListtxt.add(dllFile));
            }

            // Add IP addresses to the list if the list is not null
            if (ipAddresses != null) {
                ipAddresses.forEach(ip -> IPAddListTxt.add(ip));
            }

            // Add URLs to the URLs list if the list is not null
            if (urls != null) {
                urls.forEach(url -> URLsListTxt.add(url));
            }

            // Update analyst's notes if not null
            if (analystNotes != null) {
                AnalystNotesTextArea.setText(analystNotes);
            }

            // Load artifacts if not null
            if (artifacts != null) {
                artifacts.forEach(artifact -> ArtifactListTxt.add(artifact.getString("filename")));
            }
            
            // Update analyst's notes on DA if not null
            if (analystNotes != null) {
                AnalystNotesForDynmAnalyTextArea.setText(analystDANotes);
            }

            // Load artifacts for DA if not null
            if (artifacts != null) {
                artifacts.forEach(artifact -> ArtifactListforDATxt.add(artifact.getString("filename")));
            }
        } else {
            System.out.println("No document found with _id: " + projectId);
        }
    }

    
    MongoClient mongo;
    MongoDatabase dbconnection;
    MongoCollection<org.bson.Document> collection;
    
    public void connect(){
        mongo = new MongoClient ("localhost",27017);
        dbconnection = mongo.getDatabase("MALT");
        collection = dbconnection.getCollection("ProjectData");
        System.out.println("Project working page Connected to Database");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        projectNameLabel = new javax.swing.JLabel();
        analystNameLabel = new javax.swing.JLabel();
        ChooseFileBtn = new javax.swing.JButton();
        FileNameLbl = new javax.swing.JLabel();
        PerfStaticAnalysis = new javax.swing.JButton();
        MD5Label = new javax.swing.JLabel();
        MD5Txt = new javax.swing.JTextField();
        SHA256Label = new javax.swing.JLabel();
        SHA256Txt = new javax.swing.JTextField();
        FileNameLabel = new javax.swing.JLabel();
        FileNameTxt1 = new javax.swing.JTextField();
        FileTypeLabel = new javax.swing.JLabel();
        FileTypeTxt = new javax.swing.JTextField();
        RawFileSizeLabel = new javax.swing.JLabel();
        RawFileSizeTxt = new javax.swing.JTextField();
        VirtualFileSizeLabel = new javax.swing.JLabel();
        VirtualFileSizeTxt = new javax.swing.JTextField();
        DllFilesListLabel = new javax.swing.JLabel();
        DllFilesListtxt = new java.awt.List();
        IPAddListLabel = new javax.swing.JLabel();
        IPAddListTxt = new java.awt.List();
        URLsListLabel = new javax.swing.JLabel();
        URLsListTxt = new java.awt.List();
        AnalystNotesLabel = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        AnalystNotesTextArea = new javax.swing.JTextArea();
        AddArtifactsLabel = new javax.swing.JLabel();
        ChooseArtifactsBtn = new javax.swing.JButton();
        ArtifactListTxt = new java.awt.List();
        DeleteArtifactBtn = new javax.swing.JButton();
        Label1 = new javax.swing.JLabel();
        Label2 = new javax.swing.JLabel();
        AnalystNotesForDynmAnalyLabel = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        AnalystNotesForDynmAnalyTextArea = new javax.swing.JTextArea();
        AddArtifactsLabel1 = new javax.swing.JLabel();
        ChooseArtifactsfoyDABtn = new javax.swing.JButton();
        ArtifactListforDATxt = new java.awt.List();
        DeleteArtifactDABtn = new javax.swing.JButton();
        CreateReportBtn = new javax.swing.JButton();
        CreateYARARuleBtn = new javax.swing.JButton();
        CheckURLBtn = new javax.swing.JButton();
        URLTxt = new javax.swing.JTextField();
        AddURLLabel = new javax.swing.JLabel();
        URLResultTxt = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(900, 500));

        projectNameLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        projectNameLabel.setAlignmentY(0.0F);
        projectNameLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        analystNameLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        analystNameLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        ChooseFileBtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ChooseFileBtn.setText("Choose File");
        ChooseFileBtn.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        ChooseFileBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChooseFileBtnActionPerformed(evt);
            }
        });

        FileNameLbl.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        PerfStaticAnalysis.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        PerfStaticAnalysis.setText("Perform Static Analysis");
        PerfStaticAnalysis.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        PerfStaticAnalysis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PerfStaticAnalysisActionPerformed(evt);
            }
        });

        MD5Label.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        MD5Label.setText("MD5 Hash Value");
        MD5Label.setAlignmentY(0.0F);
        MD5Label.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        SHA256Label.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        SHA256Label.setText("SHA-256 Hash Value");
        SHA256Label.setAlignmentY(0.0F);
        SHA256Label.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        FileNameLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        FileNameLabel.setText("File Name");
        FileNameLabel.setAlignmentY(0.0F);
        FileNameLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        FileTypeLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        FileTypeLabel.setText("File Type");
        FileTypeLabel.setAlignmentY(0.0F);
        FileTypeLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        RawFileSizeLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        RawFileSizeLabel.setText("Raw File Size");
        RawFileSizeLabel.setAlignmentY(0.0F);
        RawFileSizeLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        RawFileSizeTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RawFileSizeTxtActionPerformed(evt);
            }
        });

        VirtualFileSizeLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        VirtualFileSizeLabel.setText("Virtual File Size");
        VirtualFileSizeLabel.setAlignmentY(0.0F);
        VirtualFileSizeLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        DllFilesListLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        DllFilesListLabel.setText("DLL File List");
        DllFilesListLabel.setAlignmentY(0.0F);
        DllFilesListLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        IPAddListLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        IPAddListLabel.setText("IP Addressess List");
        IPAddListLabel.setAlignmentY(0.0F);
        IPAddListLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        URLsListLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        URLsListLabel.setText("URL List");
        URLsListLabel.setAlignmentY(0.0F);
        URLsListLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AnalystNotesLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        AnalystNotesLabel.setText("Analyst's Notes");
        AnalystNotesLabel.setAlignmentY(0.0F);
        AnalystNotesLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AnalystNotesTextArea.setColumns(20);
        AnalystNotesTextArea.setRows(5);
        AnalystNotesTextArea.setPreferredSize(new java.awt.Dimension(350, 100));
        jScrollPane2.setViewportView(AnalystNotesTextArea);

        AddArtifactsLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        AddArtifactsLabel.setText("Add Artifacts");
        AddArtifactsLabel.setAlignmentY(0.0F);
        AddArtifactsLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        ChooseArtifactsBtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ChooseArtifactsBtn.setText("Choose Artifacts");
        ChooseArtifactsBtn.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        ChooseArtifactsBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChooseArtifactsBtnActionPerformed(evt);
            }
        });

        ArtifactListTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ArtifactListTxtActionPerformed(evt);
            }
        });

        DeleteArtifactBtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        DeleteArtifactBtn.setText("Delete");
        DeleteArtifactBtn.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        DeleteArtifactBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteArtifactBtnActionPerformed(evt);
            }
        });

        Label1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        Label1.setText("This Section is for Dynamic Malware Testing");
        Label1.setAlignmentY(0.0F);
        Label1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        Label2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        Label2.setText("Note: Run the malware in a secure environment and use monitoring tools like Promon to track malicious behavior.");
        Label2.setAlignmentY(0.0F);
        Label2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        AnalystNotesForDynmAnalyLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        AnalystNotesForDynmAnalyLabel.setText("Analyst's Notes for Dynamic Analysis");
        AnalystNotesForDynmAnalyLabel.setAlignmentY(0.0F);
        AnalystNotesForDynmAnalyLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AnalystNotesForDynmAnalyTextArea.setColumns(20);
        AnalystNotesForDynmAnalyTextArea.setRows(5);
        AnalystNotesForDynmAnalyTextArea.setPreferredSize(new java.awt.Dimension(350, 100));
        jScrollPane3.setViewportView(AnalystNotesForDynmAnalyTextArea);

        AddArtifactsLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        AddArtifactsLabel1.setText("Add Artifacts");
        AddArtifactsLabel1.setAlignmentY(0.0F);
        AddArtifactsLabel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        ChooseArtifactsfoyDABtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ChooseArtifactsfoyDABtn.setText("Choose Artifacts");
        ChooseArtifactsfoyDABtn.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        ChooseArtifactsfoyDABtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChooseArtifactsfoyDABtnActionPerformed(evt);
            }
        });

        ArtifactListforDATxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ArtifactListforDATxtActionPerformed(evt);
            }
        });

        DeleteArtifactDABtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        DeleteArtifactDABtn.setText("Delete");
        DeleteArtifactDABtn.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        DeleteArtifactDABtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteArtifactDABtnActionPerformed(evt);
            }
        });

        CreateReportBtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        CreateReportBtn.setText("Create Report");
        CreateReportBtn.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        CreateReportBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CreateReportBtnActionPerformed(evt);
            }
        });

        CreateYARARuleBtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        CreateYARARuleBtn.setText("Create YARA Rule");
        CreateYARARuleBtn.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        CreateYARARuleBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CreateYARARuleBtnActionPerformed(evt);
            }
        });

        CheckURLBtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        CheckURLBtn.setText("CheckURL");
        CheckURLBtn.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        CheckURLBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CheckURLBtnActionPerformed(evt);
            }
        });

        URLTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                URLTxtActionPerformed(evt);
            }
        });

        AddURLLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        AddURLLabel.setText("Enter URL");
        AddURLLabel.setAlignmentY(0.0F);
        AddURLLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        URLResultTxt.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        URLResultTxt.setAlignmentY(0.0F);
        URLResultTxt.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(27, 27, 27)
                                    .addComponent(AddArtifactsLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(ChooseArtifactsfoyDABtn, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(AnalystNotesForDynmAnalyLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(DeleteArtifactDABtn, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(126, 126, 126)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ArtifactListforDATxt, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addComponent(AddURLLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(URLResultTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(URLTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(40, 40, 40))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(Label1)
                                .addGap(244, 244, 244))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(CreateReportBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(114, 114, 114)
                                .addComponent(CreateYARARuleBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(178, 178, 178))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(Label2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(563, 563, 563)
                        .addComponent(CheckURLBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(AnalystNotesLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(URLsListLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(IPAddListLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(DllFilesListLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(ChooseFileBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(FileNameLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(SHA256Label, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(MD5Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(FileNameLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(FileTypeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(RawFileSizeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(0, 2, Short.MAX_VALUE)
                                    .addComponent(VirtualFileSizeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(projectNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(AddArtifactsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(23, 23, 23)
                                .addComponent(ChooseArtifactsBtn)
                                .addGap(71, 71, 71))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(DeleteArtifactBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(96, 96, 96)))
                        .addGap(34, 34, 34)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ArtifactListTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(RawFileSizeTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(PerfStaticAnalysis, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(MD5Txt, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
                                    .addComponent(SHA256Txt, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(FileNameTxt1))
                                .addComponent(FileTypeTxt, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(URLsListTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
                                .addComponent(IPAddListTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
                                .addComponent(VirtualFileSizeTxt)
                                .addComponent(analystNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(DllFilesListtxt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
                                .addComponent(jScrollPane2)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(analystNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(projectNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ChooseFileBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(FileNameLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(PerfStaticAnalysis, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(43, 43, 43)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(MD5Label, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                    .addComponent(MD5Txt))
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(SHA256Txt)
                    .addComponent(SHA256Label, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE))
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(FileNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(FileNameTxt1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(FileTypeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(FileTypeTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(RawFileSizeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(RawFileSizeTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(VirtualFileSizeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(VirtualFileSizeTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(DllFilesListLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(DllFilesListtxt, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(IPAddListLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(IPAddListTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(URLsListLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(URLsListTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(AnalystNotesLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(AddArtifactsLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                            .addComponent(ChooseArtifactsBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(DeleteArtifactBtn))
                    .addComponent(ArtifactListTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38)
                .addComponent(Label1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Label2, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(AnalystNotesForDynmAnalyLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ChooseArtifactsfoyDABtn)
                            .addComponent(AddArtifactsLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(DeleteArtifactDABtn)
                        .addGap(83, 83, 83))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(ArtifactListforDATxt, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AddURLLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                    .addComponent(URLTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CheckURLBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(URLResultTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CreateYARARuleBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CreateReportBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(57, 57, 57))
        );

        jScrollPane1.setViewportView(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 24, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 800, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1825, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(145, 145, 145))
        );

        // Add jPanel1 to jScrollPane1 as the viewport view
        jScrollPane1.setViewportView(jPanel1);

        // Configure jScrollPane1 for vertical and horizontal scrolling as needed
        jScrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        jScrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        // Set jScrollPane1 as the content pane of the frame
        setContentPane(jScrollPane1);

        pack();
    }// </editor-fold>                        

    private void PerfStaticAnalysisActionPerformed(java.awt.event.ActionEvent evt) {                                                   
        // TODO add your handling code here:
        if (selectedFile != null) {
            try {
                // Create an instance of StaticAnalysis and perform analysis
                StaticAnalysis staticAnalysis = new StaticAnalysis();
                AnalysisResult analysisResult = staticAnalysis.analyzeFile(selectedFile.getAbsolutePath(), projectName);

                // Process the analysis results (e.g., save to database, update GUI)
                //updateDatabase(analysisResult);
                updateGUI(analysisResult);
            } catch (NoSuchAlgorithmException | IOException ex) {
                // Handle exceptions
                Logger.getLogger(ProjectWorkingPage.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(this, "An error occurred while processing the file. Please try again.");
            }
        } else {
            // If no file has been selected, show an error message
            JOptionPane.showMessageDialog(this, "Please select a file first.");
        }
    }                                                  

    private void ChooseFileBtnActionPerformed(java.awt.event.ActionEvent evt) {                                              
        // TODO add your handling code here:
        // Create a file chooser dialog
        JFileChooser fileChooser = new JFileChooser();

        // Show the dialog and wait for the user's selection
        int result = fileChooser.showOpenDialog(this);

        // Check if the user selected a file
        if (result == JFileChooser.APPROVE_OPTION) {
            // Get the selected file
            selectedFile = fileChooser.getSelectedFile();

            // Perform actions with the selected file, such as displaying its path
            String filePath = selectedFile.getAbsolutePath();
            System.out.println("Selected file: " + filePath);

            // Display the file name in the FileNameLbl
            FileNameLbl.setText(selectedFile.getName()); // Displaying only the file name

            System.out.println(selectedFile.getName());

            // Here you can perform further actions with the selected file, such as reading its content
        } else {
            // User cancelled the file selection, do nothing or provide feedback
            System.out.println("No file selected");
        }
    }                                             

    private void RawFileSizeTxtActionPerformed(java.awt.event.ActionEvent evt) {                                               
        // TODO add your handling code here:
    }                                              

    private void CreateReportBtnActionPerformed(java.awt.event.ActionEvent evt) {                                                
        // TODO add your handling code here:
        // Get the current date and time
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String formattedDateTime = now.format(formatter);

        // Define the file path for the PDF report in the desired format
        String filePath = projectName + "_" + formattedDateTime + "_Report.pdf";

        // Retrieve the analyst's notes from the text area
        String analystNotes = AnalystNotesTextArea.getText();
        
        // Retrieve the analyst's notes from the text area
        String analystNotesforDA = AnalystNotesForDynmAnalyTextArea.getText();

        // Define the list of image artifacts
        List<Document> imageArtifacts = retrieveArtifactsFromDatabase();
        
        //Define the list of DA image artifacts
        List<Document> DAimageArtifacts = retrieveDAArtifactsFromDatabase();
        
        // Create an instance of PDFGenerator
        PDFGenerator pdfGenerator = new PDFGenerator();

        try {
            // Perform static analysis
            StaticAnalysis staticAnalysis = new StaticAnalysis();
            AnalysisResult analysisResult = staticAnalysis.analyzeFile(selectedFile.getAbsolutePath(), projectName);
            
            // Convert arrays to lists using Arrays.asList()
            List<String> dllFilesList = Arrays.asList(DllFilesListtxt.getItems());
            List<String> ipAddressesList = Arrays.asList(IPAddListTxt.getItems());
            List<String> urlsList = Arrays.asList(URLsListTxt.getItems());

            // Call createPDF method and pass in the data from the GUI
            pdfGenerator.createPDF(filePath, projectName, analystName, FileNameTxt1.getText(), MD5Txt.getText(),
                                   SHA256Txt.getText(), FileTypeTxt.getText().equals("Executable (.exe)"),
                                   Long.parseLong(RawFileSizeTxt.getText()), Long.parseLong(VirtualFileSizeTxt.getText()),
                                   Long.parseLong(VirtualFileSizeTxt.getText()) - Long.parseLong(RawFileSizeTxt.getText()),
                                   dllFilesList, ipAddressesList, urlsList, analystNotes, imageArtifacts, analystNotesforDA, DAimageArtifacts);
            
            // Call updateDatabase and pass the analyst's notes
            updateDatabase(analysisResult, analystNotes, analystNotesforDA);
            
            // Show a success message
            //JOptionPane.showMessageDialog(this, "Report created successfully: " + filePath);
            
            // Create a "Download" button for the JOptionPane
            JButton downloadButton = new JButton("Download");
            downloadButton.addActionListener(e -> {
                // Use a JFileChooser to allow the user to choose where to save the PDF file
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setSelectedFile(new File(filePath));
                int result = fileChooser.showSaveDialog(null);

                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try {
                        // Save the PDF file to the chosen location
                        // You can use a utility method to copy the file to the chosen location
                        Files.copy(Paths.get(filePath), selectedFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException ex) {
                        Logger.getLogger(ProjectWorkingPage.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                // Close the pop-up message dialog
                Window window = SwingUtilities.getWindowAncestor((Component) e.getSource());
                if (window != null) {
                    window.dispose();
                }
            });


            // Show a success message with a "Download" button
            JOptionPane.showOptionDialog(this, "Report Created", "Report",
                                         JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                                         null, new Object[] {downloadButton}, null);
            
        } catch (Exception ex) {
            // Handle exceptions
            Logger.getLogger(ProjectWorkingPage.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "An error occurred while creating the report. Please try again.");
        }
    }                                               

    private void ChooseArtifactsBtnActionPerformed(java.awt.event.ActionEvent evt) {                                                   
        // TODO add your handling code here:
        // Create a file chooser dialog
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(true); // Enable multiple file selection
        fileChooser.setDialogTitle("Select Images");

        // Set file filters to allow only image files
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Image files", "jpg", "jpeg", "png", "gif", "bmp"));

        // Show the dialog and wait for the user's selection
        int result = fileChooser.showOpenDialog(this);

        // Check if the user selected files
        if (result == JFileChooser.APPROVE_OPTION) {
            // Get the selected files
            File[] selectedFiles = fileChooser.getSelectedFiles();

            // Clear existing items in ArtifactListTxt
            ArtifactListTxt.removeAll();

            // List to store tuples of image filenames and binary data
            List<Document> imageArtifacts = new ArrayList<>();

            // Process each selected file
            for (File imageFile : selectedFiles) {
                try {
                    // Read the binary data of the image file
                    byte[] imageData = Files.readAllBytes(imageFile.toPath());

                    // Create a Document for the image artifact
                    Document imageArtifact = new Document("filename", imageFile.getName())
                                            .append("data", imageData);

                    // Add the artifact to the list
                    imageArtifacts.add(imageArtifact);

                    // Add the file name to ArtifactListTxt
                    ArtifactListTxt.add(imageFile.getName());

                } catch (IOException e) {
                    // Handle exceptions while reading the file
                    Logger.getLogger(ProjectWorkingPage.class.getName()).log(Level.SEVERE, null, e);
                    JOptionPane.showMessageDialog(this, "An error occurred while reading the image file: " + imageFile.getName());
                }
            }

            // Save the list of image artifacts to the database
            saveImagesToDatabase(imageArtifacts);
        } else {
            // User cancelled the file selection
            System.out.println("No files selected");
        }
    }                                                  

    private void ArtifactListTxtActionPerformed(java.awt.event.ActionEvent evt) {                                                
        // TODO add your handling code here:
    }                                               

    private void DeleteArtifactBtnActionPerformed(java.awt.event.ActionEvent evt) {                                                  
        // TODO add your handling code here:
        // Get the index of the selected item in the list
        int selectedIndex = ArtifactListTxt.getSelectedIndex();

        // Check if any item is selected
        if (selectedIndex != -1) {
            // Get the selected item from the list
            String selectedItem = ArtifactListTxt.getItem(selectedIndex);

            // Remove the selected item from the list
            ArtifactListTxt.remove(selectedIndex);

            // Remove the corresponding artifact from the database
            removeArtifactFromDatabase(selectedItem);

            // Optional: Show a confirmation message to the user
            JOptionPane.showMessageDialog(this, "Artifact deleted successfully.");
        } else {
            // No item is selected, show a message to the user
            JOptionPane.showMessageDialog(this, "No item selected for deletion.");
        }
    }                                                 

    private void ChooseArtifactsfoyDABtnActionPerformed(java.awt.event.ActionEvent evt) {                                                        
        // TODO add your handling code here:
        // Create a file chooser dialog
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(true); // Enable multiple file selection
        fileChooser.setDialogTitle("Select Images");

        // Set file filters to allow only image files
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Image files", "jpg", "jpeg", "png", "gif", "bmp"));

        // Show the dialog and wait for the user's selection
        int result = fileChooser.showOpenDialog(this);

        // Check if the user selected files
        if (result == JFileChooser.APPROVE_OPTION) {
            // Get the selected files
            File[] selectedFiles = fileChooser.getSelectedFiles();

            // Clear existing items in ArtifactListTxt
            ArtifactListforDATxt.removeAll();

            // List to store tuples of image filenames and binary data
            List<Document> imageArtifactsforDA = new ArrayList<>();

            // Process each selected file
            for (File imageFile : selectedFiles) {
                try {
                    // Read the binary data of the image file
                    byte[] imageData = Files.readAllBytes(imageFile.toPath());

                    // Create a Document for the image artifact
                    Document imageArtifact = new Document("filename", imageFile.getName())
                                            .append("data", imageData);

                    // Add the artifact to the list
                    imageArtifactsforDA.add(imageArtifact);

                    // Add the file name to ArtifactListTxt
                    ArtifactListforDATxt.add(imageFile.getName());

                } catch (IOException e) {
                    // Handle exceptions while reading the file
                    Logger.getLogger(ProjectWorkingPage.class.getName()).log(Level.SEVERE, null, e);
                    JOptionPane.showMessageDialog(this, "An error occurred while reading the image file: " + imageFile.getName());
                }
            }

            // Save the list of image artifacts to the database
            saveDAImagesToDatabase(imageArtifactsforDA);
        } else {
            // User cancelled the file selection
            System.out.println("No files selected");
        }
    }                                                       

    private void ArtifactListforDATxtActionPerformed(java.awt.event.ActionEvent evt) {                                                     
        // TODO add your handling code here:
    }                                                    

    private void DeleteArtifactDABtnActionPerformed(java.awt.event.ActionEvent evt) {                                                    
        // TODO add your handling code here:
        // Get the index of the selected item in the list
        int selectedIndex = ArtifactListforDATxt.getSelectedIndex();

        // Check if any item is selected
        if (selectedIndex != -1) {
            // Get the selected item from the list
            String selectedItem = ArtifactListforDATxt.getItem(selectedIndex);

            // Remove the selected item from the list
            ArtifactListforDATxt.remove(selectedIndex);

            // Remove the corresponding artifact from the database
            removeDAArtifactFromDatabase(selectedItem);

            // Optional: Show a confirmation message to the user
            JOptionPane.showMessageDialog(this, "Artifact deleted successfully.");
        } else {
            // No item is selected, show a message to the user
            JOptionPane.showMessageDialog(this, "No item selected for deletion.");
        }
    }                                                   

    private void CreateYARARuleBtnActionPerformed(java.awt.event.ActionEvent evt) {                                                  
        // TODO add your handling code here:
        // Extract the required data from the class
        String ruleName = "YaraRuleForProject"; // Choose an appropriate name for the rule
        String fileName = FileNameTxt1.getText();
        String md5Hash = MD5Txt.getText();
        String sha256Hash = SHA256Txt.getText();
        boolean isExecutable = FileTypeTxt.getText().equals("Executable (.exe)");
        List<String> dllFiles = Arrays.asList(DllFilesListtxt.getItems());
        List<String> ipAddresses = Arrays.asList(IPAddListTxt.getItems());
        List<String> urls = Arrays.asList(URLsListTxt.getItems());
        String analystNotes = AnalystNotesTextArea.getText();

        // Create an instance of YaraRuleGenerator with the extracted data
        YaraRuleGenerator ruleGenerator = new YaraRuleGenerator(
            ruleName, fileName, md5Hash, sha256Hash,
            isExecutable, dllFiles, ipAddresses, urls,
            analystNotes, projectName
        );
        
        // Generate the YARA rule
        String yaraRule = ruleGenerator.generateYaraRule();

        // Print or display the generated YARA rule
        System.out.println("Generated YARA rule:");
        System.out.println(yaraRule);
        
        // Create a "Download" button for the JOptionPane
        JButton downloadButton = new JButton("Download");
        downloadButton.addActionListener(e -> {
            // Use a JFileChooser to allow the user to choose where to save the YARA rule file
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save YARA Rule");
            fileChooser.setSelectedFile(new File(ruleName + ".yara")); // Set the default file name

            int result = fileChooser.showSaveDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                try {
                    // Save the YARA rule to the chosen location
                    ruleGenerator.saveYaraRuleToFile(selectedFile.getAbsolutePath());

                    // Show a success message after saving
                    JOptionPane.showMessageDialog(null, "YARA rule saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    // Show an error message if the file could not be saved
                    JOptionPane.showMessageDialog(null, "Failed to save YARA rule: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }

            // Close the pop-up message dialog
            Window window = SwingUtilities.getWindowAncestor((Component) e.getSource());
            if (window != null) {
                window.dispose();
            }
        });

        // Show a pop-up message with the "Download" button
        JOptionPane.showOptionDialog(this, "YARA rule created successfully!", "YARA Rule Created",
                                     JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                                     null, new Object[] {downloadButton}, null);

        // Save the generated YARA rule to a file with the specified format
        try {
            ruleGenerator.saveYaraRuleToFile(projectName);
        } catch (IOException e) {
            System.out.println("Failed to save YARA rule: " + e.getMessage());
        }

    }                                                 

    private void CheckURLBtnActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
        // The URL you want to analyze
        System.out.println("The URL analysis code is running");
        String urlToAnalyze = URLTxt.getText().trim();
        
        // The path to your Python script
        String pythonScriptPath = "D:\\SEM6\\PROJECT\\PYTHON_VIRUSTOTAL\\File_Scan.py";
        
        // Create a process builder to run the Python script
        ProcessBuilder processBuilder = new ProcessBuilder(
                "python", pythonScriptPath, urlToAnalyze);

        // Redirect error stream to the standard output stream
        processBuilder.redirectErrorStream(true);

        try {
            // Start the process
            Process process = processBuilder.start();

            // Create a reader to read the output from the Python script
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            // Read the output from the Python script
            String line;
            StringBuilder output = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            // Wait for the process to complete
            int exitCode = process.waitFor();

            // Check the exit code and print the output
            if (exitCode == 0) {
                // Print the output
                System.out.println("Python script output:\n" + output);
                
                // Output from the Python script
                String result = output.toString().trim();

                // Check the output to see if the URL is malicious or not
                if (result.contains("is malicious")) {
                    URLResultTxt.setText("Malicious URL");
                } else if (result.contains("is not malicious")) {
                    URLResultTxt.setText("Not Malicious");
                } else {
                    URLResultTxt.setText("Unknown result");
                }
            } else {
                // If the process didn't exit cleanly, print an error message
                System.err.println("Error: The Python script failed to execute.");
                URLResultTxt.setText("Error running script");
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }                                           

    private void URLTxtActionPerformed(java.awt.event.ActionEvent evt) {                                       
        // TODO add your handling code here:
    }                                      

    private void saveImagesToDatabase(List<Document> imageArtifacts) {
        // Define the filter to search for the document by project ID
        Document filter = new Document("_id", new org.bson.types.ObjectId(projectId));

        // Define the update document to append the list of image artifacts to the `artifact` field
        Document update = new Document("$push", new Document("artifact", new Document("$each", imageArtifacts)));

        // Update the project document in the collection
        collection.updateOne(filter, update);
    }
    
    private void saveDAImagesToDatabase(List<Document> imageArtifacts) {
        // Define the filter to search for the document by project ID
        Document filter = new Document("_id", new org.bson.types.ObjectId(projectId));

        // Define the update document to append the list of image artifacts to the `artifact` field
        Document update = new Document("$push", new Document("DAartifact", new Document("$each", imageArtifacts)));

        // Update the project document in the collection
        collection.updateOne(filter, update);
    }
    
    // Define updateDatabase and updateGUI methods
    private void updateDatabase(AnalysisResult analysisResult, String analystNotes, String analystNotesforDA) {
        // Define the filter to search for the document by project name
        Document filter = new Document("projectname", projectName);

        // Define the update document with analysis results
        Document update = new Document("$set", new Document()
            .append("md5Hash", analysisResult.getMd5Hash())
            .append("sha256Hash", analysisResult.getSha256Hash())
            .append("fileName", analysisResult.getFileName())
            .append("isExecutable", analysisResult.isExecutable())
            .append("rawSize", analysisResult.getRawSize())
            .append("virtualSize", analysisResult.getVirtualSize())
            .append("sizeDifference", analysisResult.getSizeDifference())
            .append("dllFiles", analysisResult.getDllFiles())
            .append("ipAddresses", analysisResult.getIpAddresses())
            .append("urls", analysisResult.getUrls())
            .append("analystNotes", analystNotes)
            .append("analystNotesforDA",analystNotesforDA)
        );

        // Update the document in the collection
        collection.updateOne(filter, update);
    }

    private void updateGUI(AnalysisResult analysisResult) {
        // Logic to update the GUI with analysis results
        // For example, updating labels with the results
        MD5Txt.setText(analysisResult.getMd5Hash());
        SHA256Txt.setText(analysisResult.getSha256Hash());
        FileNameTxt1.setText(analysisResult.getFileName());
        if (analysisResult.isExecutable()) {
            FileTypeTxt.setText("Executable (.exe)");
        }
        RawFileSizeTxt.setText(Long.toString(analysisResult.getRawSize()));
        VirtualFileSizeTxt.setText(Long.toString(analysisResult.getVirtualSize()));

        // Clear the existing lists before adding new items
        DllFilesListtxt.removeAll();
        IPAddListTxt.removeAll();
        URLsListTxt.removeAll();

        // Add DLL files to the list
        for (String dllFile : analysisResult.getDllFiles()) {
            DllFilesListtxt.add(dllFile);
        }

        // Add IP addresses to the list
        for (String ipAddress : analysisResult.getIpAddresses()) {
            IPAddListTxt.add(ipAddress);
        }
        
        // Add URLs to the URLs list
        for (String url : analysisResult.getUrls()) {
            URLsListTxt.add(url);
        }
    }
 
    private List<Document> retrieveArtifactsFromDatabase() {
        // Define the filter to search for the document by project ID
        Document filter = new Document("_id", new org.bson.types.ObjectId(projectId));

        // Fetch the project document from the collection
        Document projectDoc = collection.find(filter).first();

        // Check if project document exists
        if (projectDoc != null) {
            // Retrieve the list of artifacts (images)
            List<Document> artifacts = (List<Document>) projectDoc.get("artifact");
            return artifacts != null ? artifacts : new ArrayList<>();
        } else {
            // Return an empty list if no project document is found
            return new ArrayList<>();
        }
    }
    
    private List<Document> retrieveDAArtifactsFromDatabase() {
        // Define the filter to search for the document by project ID
        Document filter = new Document("_id", new org.bson.types.ObjectId(projectId));

        // Fetch the project document from the collection
        Document projectDoc = collection.find(filter).first();

        // Check if project document exists
        if (projectDoc != null) {
            // Retrieve the list of artifacts (images)
            List<Document> artifacts = (List<Document>) projectDoc.get("DAartifact");
            return artifacts != null ? artifacts : new ArrayList<>();
        } else {
            // Return an empty list if no project document is found
            return new ArrayList<>();
        }
    }

    private void removeArtifactFromDatabase(String filename) {
        // Define the filter to search for the artifact by filename and project ID
        Document filter = new Document("artifact.filename", filename)
                            .append("_id", new org.bson.types.ObjectId(projectId));

        // Define the update document to remove the artifact from the `artifact` field
        Document update = new Document("$pull", new Document("artifact", new Document("filename", filename)));

        // Update the project document in the collection
        collection.updateOne(filter, update);
    }
    
    private void removeDAArtifactFromDatabase(String filename) {
        // Define the filter to search for the artifact by filename and project ID
        Document filter = new Document("DAartifact.filename", filename)
                            .append("_id", new org.bson.types.ObjectId(projectId));

        // Define the update document to remove the artifact from the `artifact` field
        Document update = new Document("$pull", new Document("DAartifact", new Document("filename", filename)));

        // Update the project document in the collection
        collection.updateOne(filter, update);
    }

    
    private File selectedFile;
    
    // Variables declaration - do not modify                     
    private javax.swing.JLabel AddArtifactsLabel;
    private javax.swing.JLabel AddArtifactsLabel1;
    private javax.swing.JLabel AddURLLabel;
    private javax.swing.JLabel AnalystNotesForDynmAnalyLabel;
    private javax.swing.JTextArea AnalystNotesForDynmAnalyTextArea;
    private javax.swing.JLabel AnalystNotesLabel;
    private javax.swing.JTextArea AnalystNotesTextArea;
    private java.awt.List ArtifactListTxt;
    private java.awt.List ArtifactListforDATxt;
    private javax.swing.JButton CheckURLBtn;
    private javax.swing.JButton ChooseArtifactsBtn;
    private javax.swing.JButton ChooseArtifactsfoyDABtn;
    private javax.swing.JButton ChooseFileBtn;
    private javax.swing.JButton CreateReportBtn;
    private javax.swing.JButton CreateYARARuleBtn;
    private javax.swing.JButton DeleteArtifactBtn;
    private javax.swing.JButton DeleteArtifactDABtn;
    private javax.swing.JLabel DllFilesListLabel;
    private java.awt.List DllFilesListtxt;
    private javax.swing.JLabel FileNameLabel;
    private javax.swing.JLabel FileNameLbl;
    private javax.swing.JTextField FileNameTxt1;
    private javax.swing.JLabel FileTypeLabel;
    private javax.swing.JTextField FileTypeTxt;
    private javax.swing.JLabel IPAddListLabel;
    private java.awt.List IPAddListTxt;
    private javax.swing.JLabel Label1;
    private javax.swing.JLabel Label2;
    private javax.swing.JLabel MD5Label;
    private javax.swing.JTextField MD5Txt;
    private javax.swing.JButton PerfStaticAnalysis;
    private javax.swing.JLabel RawFileSizeLabel;
    private javax.swing.JTextField RawFileSizeTxt;
    private javax.swing.JLabel SHA256Label;
    private javax.swing.JTextField SHA256Txt;
    private javax.swing.JLabel URLResultTxt;
    private javax.swing.JTextField URLTxt;
    private javax.swing.JLabel URLsListLabel;
    private java.awt.List URLsListTxt;
    private javax.swing.JLabel VirtualFileSizeLabel;
    private javax.swing.JTextField VirtualFileSizeTxt;
    private javax.swing.JLabel analystNameLabel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel projectNameLabel;
    // End of variables declaration                   
}
