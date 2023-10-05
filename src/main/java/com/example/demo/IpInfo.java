package com.example.demo;

import java.net.InetAddress;
import java.net.UnknownHostException;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class IpInfo {
	@Id
	private Long usernameId;
	private String username;
	private String ipAddress;
	private String machineName;
	
	
	  public IpInfo() {
	        try {
	            // Fetch the IP address and machine name automatically
	            InetAddress localhost = InetAddress.getLocalHost();
	            System.out.println("Host Address: " + localhost.getHostAddress());
	            System.out.println("Host Name: " + localhost.getHostName());
	            this.ipAddress = localhost.getHostAddress();
	            this.machineName = localhost.getHostName();
	        } catch (UnknownHostException e) {
	            // Handle exception if IP address or machine name cannot be obtained
	            this.ipAddress = "Unknown";
	            this.machineName = "Unknown";
	        }
	    }
	public Long getUsernameId() {
		return usernameId;
	}
	public void setUsernameId(Long usernameId) {
		this.usernameId = usernameId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getMachineName() {
		return machineName;
	}
	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}

	
}
