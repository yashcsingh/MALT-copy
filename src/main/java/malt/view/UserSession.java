
package malt.view;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Yash
 */
public class UserSession {
    private static UserSession instance;
    private String userEmail;
    private String userObjectId;

    // Private constructor to prevent instantiation from outside
    private UserSession() {
    }

    // Method to get the singleton instance of UserSession
    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    // Getter and setter for userEmail
    public String getUserEmail() {
        return userEmail;
    }
    
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
        System.out.println(userEmail);
    }
    
    // Getter and setter for userObjectId
    public void setUserObjectId(String userObjectId) {
        this.userObjectId = userObjectId;
        System.out.println(userObjectId);
    }
    
    public String getUserObjectId(){
        return userObjectId;
    }
    
}
