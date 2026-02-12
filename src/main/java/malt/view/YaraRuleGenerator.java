/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package malt.view;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author Yash
 */
public class YaraRuleGenerator {
    
    private String ruleName;
    private String fileName;
    private String md5Hash;
    private String sha256Hash;
    private boolean isExecutable;
    private List<String> dllFiles;
    private List<String> ipAddresses;
    private List<String> urls;
    private String analystNotes;
    private String projectName;

    // Constructor to initialize the YaraRuleGenerator with data from your project
    public YaraRuleGenerator(String ruleName, String fileName, String md5Hash, String sha256Hash, boolean isExecutable,
                             List<String> dllFiles, List<String> ipAddresses, List<String> urls,
                             String analystNotes, String projectName) {
        this.ruleName = ruleName;
        this.fileName = fileName;
        this.md5Hash = md5Hash;
        this.sha256Hash = sha256Hash;
        this.isExecutable = isExecutable;
        this.dllFiles = dllFiles;
        this.ipAddresses = ipAddresses;
        this.urls = urls;
        this.analystNotes = analystNotes;
        this.projectName = projectName;
    }

    // Method to generate a YARA rule
    public String generateYaraRule() {
        StringBuilder yaraRule = new StringBuilder();

        // Rule header
        yaraRule.append("rule ").append(ruleName).append(" {\n");

        // Meta section
        yaraRule.append("\tmeta:\n");
        yaraRule.append("\t\tfile_name = \"").append(fileName).append("\"\n");
        yaraRule.append("\t\tmd5_hash = \"").append(md5Hash).append("\"\n");
        yaraRule.append("\t\tsha256_hash = \"").append(sha256Hash).append("\"\n");
        yaraRule.append("\t\tis_executable = ").append(isExecutable ? "true" : "false").append("\n");
        yaraRule.append("\t\tanalyst_notes = \"").append(analystNotes).append("\"\n");

        // Strings section
        yaraRule.append("\tstrings:\n");

        // Variables for DLL files
        for (int i = 0; i < dllFiles.size(); i++) {
            yaraRule.append("\t\t$dll_files").append(i + 1).append(" = \"")
                    .append(dllFiles.get(i)).append("\"\n");
        }

        // Variables for IP addresses
        for (int i = 0; i < ipAddresses.size(); i++) {
            yaraRule.append("\t\t$ip_addresses").append(i + 1).append(" = \"")
                    .append(ipAddresses.get(i)).append("\"\n");
        }

        // Variables for URLs
        for (int i = 0; i < urls.size(); i++) {
            yaraRule.append("\t\t$urls").append(i + 1).append(" = \"")
                    .append(urls.get(i)).append("\"\n");
        }

        // Condition section
        yaraRule.append("\tcondition:\n");

        // Add conditions based on the available data
        boolean hasCondition = false;
        StringBuilder condition = new StringBuilder();

        // File hash conditions
        if (md5Hash != null && !md5Hash.isEmpty()) {
            if (hasCondition) {
                condition.append(" or\n");
            }
            condition.append("\t\tmd5 == \"").append(md5Hash).append("\"");
            hasCondition = true;
        }

        if (sha256Hash != null && !sha256Hash.isEmpty()) {
            if (hasCondition) {
                condition.append(" or\n");
            }
            condition.append("\t\tsha256 == \"").append(sha256Hash).append("\"");
            hasCondition = true;
        }

        // DLL file conditions
        if (dllFiles != null && !dllFiles.isEmpty()) {
            if (hasCondition) {
                condition.append(" or\n");
            }
            condition.append("\t\tall of ($dll_files*)");
            hasCondition = true;
        }

        // IP address conditions
        if (ipAddresses != null && !ipAddresses.isEmpty()) {
            if (hasCondition) {
                condition.append(" or\n");
            }
            condition.append("\t\tall of ($ip_addresses*)");
            hasCondition = true;
        }

        // URL conditions
        if (urls != null && !urls.isEmpty()) {
            if (hasCondition) {
                condition.append(" or\n");
            }
            condition.append("\t\tall of ($urls*)");
            hasCondition = true;
        }

        yaraRule.append(condition.toString());
        yaraRule.append("\n}");

        return yaraRule.toString();
    }

    
    // Method to save the YARA rule as a .yara file
    public void saveYaraRuleToFile(String projectName) throws IOException {
        // Generate the YARA rule
        String yaraRule = generateYaraRule();

        // Get the current date and time
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String formattedDateTime = now.format(formatter);

        // Construct the file path with the desired format
        String filePath = String.format("%s_%s_yararule.yara", projectName, formattedDateTime);

        // Use a FileWriter to save the rule to a file
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(yaraRule);
        }

        // Print a success message with the file path
        System.out.println("YARA rule saved successfully to " + filePath);
    }

}
