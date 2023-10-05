package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IpInfoService {

    private final IpInfoRepository ipInfoRepository;

    @Autowired
    public IpInfoService(IpInfoRepository ipInfoRepository) {
        this.ipInfoRepository = ipInfoRepository;
    }

    public List<IpInfo> getAllIpInfo() {
        return (List<IpInfo>) ipInfoRepository.findAll();
    }

    public Optional<IpInfo> getIpInfoById(Long usernameId) {
        return ipInfoRepository.findById(usernameId);
    }

    public IpInfo createIpInfo(IpInfo ipInfo) {
        return ipInfoRepository.save(ipInfo);
    }

    public IpInfo updateIpInfo(IpInfo ipInfo) {
        return ipInfoRepository.save(ipInfo);
    }

    public void deleteIpInfo(Long usernameId) {
        ipInfoRepository.deleteById(usernameId);
    }
}
