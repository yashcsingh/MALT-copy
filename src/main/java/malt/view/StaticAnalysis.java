/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 *
 * @author Yash
 */
package malt.view;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StaticAnalysis {

    // Analyze a file and return an instance of AnalysisResult
    public AnalysisResult analyzeFile(String fileName, String projectName) throws NoSuchAlgorithmException, IOException {
        // Calculate MD5 hash
        String md5Hash = calculateMD5Hash(fileName);

        // Calculate SHA-256 hash
        String sha256Hash = calculateSHA256Hash(fileName);

        // Extract file name
        String extractedFileName = extractFileName(fileName);

        // Get file information
        boolean isExecutable = isExecutableFile(fileName);
        long rawSize = getRawSize(fileName);
        long virtualSize = getVirtualSize(fileName);
        long sizeDifference = virtualSize - rawSize;

        // Analyze file content for strings
        List<String> dllFiles = new ArrayList<>();
        List<String> ipAddresses = new ArrayList<>();
        List<String> urls = new ArrayList<>();
        analyzeFileContentForStrings(fileName, dllFiles, ipAddresses, urls);

        // Create and return an AnalysisResult instance
        return new AnalysisResult(md5Hash, sha256Hash, extractedFileName, isExecutable, rawSize, virtualSize, sizeDifference, dllFiles, ipAddresses, urls);
    }

    // Method to calculate MD5 hash of a file
    private String calculateMD5Hash(String fileName) throws IOException, NoSuchAlgorithmException {
        return calculateHash(fileName, "MD5");
    }

    // Method to calculate SHA-256 hash of a file
    private String calculateSHA256Hash(String fileName) throws IOException, NoSuchAlgorithmException {
        return calculateHash(fileName, "SHA-256");
    }

    // Generic method to calculate hash of a file
    private String calculateHash(String fileName, String algorithm) throws IOException, NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(algorithm);
        try (FileInputStream inputStream = new FileInputStream(fileName)) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                md.update(buffer, 0, bytesRead);
            }
        }
        byte[] hashBytes = md.digest();
        return bytesToHex(hashBytes);
    }

    // Helper method to convert bytes to hexadecimal string
    private String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }

    // Method to extract file name from a given file path
    private String extractFileName(String filePath) {
        File file = new File(filePath);
        return file.getName();
    }

    // Method to check if a file is an executable file (MZ signature)
    private boolean isExecutableFile(String filePath) throws IOException {
        try (FileInputStream inputStream = new FileInputStream(filePath)) {
            byte[] signature = new byte[2];
            inputStream.read(signature);
            return (signature[0] == 'M' && signature[1] == 'Z');
        }
    }

    // Method to get raw size of the file
    private long getRawSize(String filePath) {
        File file = new File(filePath);
        return file.length();
    }

    // Method to get virtual size of the file
    private long getVirtualSize(String filePath) throws IOException {
        // Read the file as a FileInputStream
        try (FileInputStream fileInputStream = new FileInputStream(filePath)) {
            // Check if the file starts with the MZ signature (PE header)
            byte[] signature = new byte[2];
            fileInputStream.read(signature);
            if (signature[0] == 'M' && signature[1] == 'Z') {
                // Move to the PE header
                fileInputStream.skip(58); // Skip the rest of the DOS header

                // Read the PE header offset
                byte[] peOffsetBytes = new byte[4];
                fileInputStream.read(peOffsetBytes);
                int peOffset = bytesToInt(peOffsetBytes);

                // Move to the PE header
                fileInputStream.skip(peOffset - 64); // Subtracting the bytes read so far (64 = 60 + 4 bytes)

                // Read SizeOfImage (offset 0x50 from PE header start)
                fileInputStream.skip(80); // Move to the SizeOfImage field
                byte[] sizeOfImageBytes = new byte[4];
                fileInputStream.read(sizeOfImageBytes);

                // Convert the bytes to an integer
                return bytesToInt(sizeOfImageBytes);
            }
        }

        // If the file is not an executable or the PE header is not found, return raw size
        return getRawSize(filePath);
    }

    // Helper method to convert a byte array to an integer 
    private int bytesToInt(byte[] bytes) {
        int value = 0;
        for (int i = 0; i < 4; i++) {
            value |= (bytes[i] & 0xFF) << (8 * i);
        }
        return value;
    }

    // Method to analyze file content for strings (DLL file names, function names, IP addresses, and URLs)
    private void analyzeFileContentForStrings(String filePath, List<String> dllFiles, List<String> ipAddresses, List<String> urls) throws IOException {
        // Read the file content as a string
        String fileContent = readFileContent(filePath);

        // Pattern to match DLL file names (e.g., ending in ".dll")
        Pattern dllPattern = Pattern.compile("\\b\\w+\\.dll\\b", Pattern.CASE_INSENSITIVE);
        // Pattern to match IP addresses (IPv4)
        Pattern ipPattern = Pattern.compile("\\b(?:[0-9]{1,3}\\.){3}[0-9]{1,3}\\b");
        // Pattern to match URLs
        Pattern urlPattern = Pattern.compile("(https?://[\\w\\-]+(\\.[\\w\\-]+)+(/[-\\w ./?%&=]*)?)", Pattern.CASE_INSENSITIVE);
        
        // Check for DLL file names
        Matcher dllMatcher = dllPattern.matcher(fileContent);
        while (dllMatcher.find()) {
            dllFiles.add(dllMatcher.group());
        }

        // Check for IP addresses
        Matcher ipMatcher = ipPattern.matcher(fileContent);
        while (ipMatcher.find()) {
            ipAddresses.add(ipMatcher.group());
        }

        // Check for URLs
        Matcher urlMatcher = urlPattern.matcher(fileContent);
        while (urlMatcher.find()) {
            urls.add(urlMatcher.group());
        }
    }

    // Helper method to read file content as a string
    private String readFileContent(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }
}
