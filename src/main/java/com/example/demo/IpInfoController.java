package com.example.demo;


import java.net.InetAddress;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/ipinfo")
public class IpInfoController {

    private final IpInfoService ipInfoService;

    public IpInfoController(IpInfoService ipInfoService) {
        this.ipInfoService = ipInfoService;
    }

    // Get all IP information
    @GetMapping
    public ResponseEntity<List<IpInfo>> getAllIpInfo() {
        try {
            List<IpInfo> ipInfoList = ipInfoService.getAllIpInfo();
            return ResponseEntity.ok(ipInfoList);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to retrieve IP information", e);
        }
    }

    // Get IP information by usernameId
    @GetMapping("/{usernameId}")
    public ResponseEntity<IpInfo> getIpInfoById(@PathVariable Long usernameId) {
        try {
            Optional<IpInfo> ipInfo = ipInfoService.getIpInfoById(usernameId);
            return ipInfo.map(info -> ResponseEntity.ok(info))
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "IP information not found"));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to retrieve IP information", e);
        }
    }

    // Get client's IP address
    @GetMapping("/get-client-ip")
    public String getClientIp(HttpServletRequest request) {
        String clientIpAddress = request.getRemoteAddr();
        return "Client IP Address: " + clientIpAddress;
    }

 // Update IP information
    @PutMapping("/{usernameId}")
    public ResponseEntity<IpInfo> updateIpInfo(@PathVariable Long usernameId, @RequestBody IpInfo ipInfo, HttpServletRequest request) {
        try {
            // Fetch the client's IP address
            String clientIpAddress = getClientIpAddress(request);

            // Fetch the machine name
            String machineName = InetAddress.getLocalHost().getHostName();

            // Set the client's IP address and machine name
            ipInfo.setIpAddress(clientIpAddress);
            ipInfo.setMachineName(machineName);

            // Update the IpInfo object
            IpInfo updatedIpInfo = ipInfoService.updateIpInfo(ipInfo);

            return ResponseEntity.ok(updatedIpInfo);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to update IP information", e);
        }
    }

    // Delete IP information by usernameId
    @DeleteMapping("/{usernameId}")
    public ResponseEntity<Void> deleteIpInfo(@PathVariable Long usernameId) {
        try {
            ipInfoService.deleteIpInfo(usernameId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to delete IP information", e);
        }
    }

    // Create IP information
    @PostMapping
    public ResponseEntity<IpInfo> createIpInfo(@RequestBody IpInfo ipInfo, HttpServletRequest request) {
        try {
            // Fetch the client's IP address
            String clientIpAddress = getClientIpAddress(request);

            // Fetch the machine name
            String machineName = InetAddress.getLocalHost().getHostName();

            // Set the client's IP address and machine name
            ipInfo.setIpAddress(clientIpAddress);
            ipInfo.setMachineName(machineName);

            // Save the IpInfo object
            IpInfo createdIpInfo = ipInfoService.createIpInfo(ipInfo);

            return ResponseEntity.status(HttpStatus.CREATED).body(createdIpInfo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // ... other methods

    // Helper method to fetch client's IP address
    private String getClientIpAddress(HttpServletRequest request) {
        String clientIpAddress = request.getHeader("X-Forwarded-For");
        // Extract the client's IP address based on your application's requirements

        return clientIpAddress;
    }
}
