/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import Model.Staff;
import jakarta.servlet.http.HttpServletRequest;

/**
 *
 * @author anhdu
 */
public class SessionUserInfo {
    
    public static Staff getStaffSession(HttpServletRequest request) {
        
        return (Staff) request.getSession().getAttribute("staff");
        
    }
    
}
