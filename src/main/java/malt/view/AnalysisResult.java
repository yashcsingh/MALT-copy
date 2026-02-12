/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package malt.view;

import java.util.List;

/**
 *
 * @author Yash
 */

public class AnalysisResult {
    private String md5Hash;
    private String sha256Hash;
    private String fileName;
    private boolean isExecutable;
    private long rawSize;
    private long virtualSize;
    private long sizeDifference;
    private List<String> dllFiles;
    private List<String> ipAddresses;
    private List<String> urls;  // New field for URLs

    // Constructor with URLs parameter added
    public AnalysisResult(String md5Hash, String sha256Hash, String fileName, boolean isExecutable, long rawSize, long virtualSize, long sizeDifference, List<String> dllFiles, List<String> ipAddresses, List<String> urls) {
        this.md5Hash = md5Hash;
        this.sha256Hash = sha256Hash;
        this.fileName = fileName;
        this.isExecutable = isExecutable;
        this.rawSize = rawSize;
        this.virtualSize = virtualSize;
        this.sizeDifference = sizeDifference;
        this.dllFiles = dllFiles;
        this.ipAddresses = ipAddresses;
        this.urls = urls;  // Initialize the URLs field
    }

    // Getter methods
    public String getMd5Hash() {
        return md5Hash;
    }

    public String getSha256Hash() {
        return sha256Hash;
    }

    public String getFileName() {
        return fileName;
    }

    public boolean isExecutable() {
        return isExecutable;
    }

    public long getRawSize() {
        return rawSize;
    }

    public long getVirtualSize() {
        return virtualSize;
    }

    public long getSizeDifference() {
        return sizeDifference;
    }

    public List<String> getDllFiles() {
        return dllFiles;
    }

    public List<String> getIpAddresses() {
        return ipAddresses;
    }

    // New getter method for URLs
    public List<String> getUrls() {
        return urls;
    }
}
