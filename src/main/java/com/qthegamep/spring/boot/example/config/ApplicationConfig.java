package com.qthegamep.spring.boot.example.config;

import com.qthegamep.spring.boot.example.exception.ApplicationConfigInitializationException;
import com.qthegamep.spring.boot.example.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;

@Component
public class ApplicationConfig {

    private static final Logger LOG = LoggerFactory.getLogger(ApplicationConfig.class);

    private final String dockerImageNameFilePath;

    public ApplicationConfig(@Value("${docker.image.name.file.path}") String dockerImageNameFilePath) {
        this.dockerImageNameFilePath = dockerImageNameFilePath;
    }

    @PostConstruct
    public void init() throws ApplicationConfigInitializationException {
        try {
            loadServerIp();
            loadDockerImageName();
        } catch (Exception e) {
            throw new ApplicationConfigInitializationException(e);
        }
    }

    private void loadServerIp() throws SocketException {
        Optional<String> serverIpOptional = getServerIp();
        if (serverIpOptional.isPresent()) {
            String serverIp = serverIpOptional.get();
            LOG.info("Server IP: {}", serverIp);
            System.setProperty(Constants.SERVER_IP_PROPERTY, serverIp);
        } else {
            LOG.warn("Server IP is not defined!");
        }
    }

    private Optional<String> getServerIp() throws SocketException {
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        while (networkInterfaces.hasMoreElements()) {
            NetworkInterface networkInterface = networkInterfaces.nextElement();
            LOG.debug("Interface display name: {}", networkInterface.getDisplayName());
            if (networkInterface.isLoopback()
                    || !networkInterface.isUp()
                    || networkInterface.isVirtual()
                    || networkInterface.isPointToPoint()
                    || networkInterface.getDisplayName().contains("docker")) {
                continue;
            }
            Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
            while (inetAddresses.hasMoreElements()) {
                InetAddress inetAddress = inetAddresses.nextElement();
                String hostAddress = inetAddress.getHostAddress();
                LOG.debug("Interface display name: {} host address: {}", networkInterface.getDisplayName(), hostAddress);
                if (Inet4Address.class == inetAddress.getClass()) {
                    return Optional.of(hostAddress);
                }
            }
        }
        return Optional.empty();
    }

    private void loadDockerImageName() throws IOException {
        File dockerImageNameFile = new File(dockerImageNameFilePath);
        if (dockerImageNameFile.exists() && !dockerImageNameFile.isDirectory()) {
            List<String> lines = Files.readAllLines(Paths.get(dockerImageNameFilePath), StandardCharsets.UTF_8);
            if (lines.isEmpty()) {
                LOG.warn("Docker image name file: {} is empty!", dockerImageNameFilePath);
            } else {
                String dockerImageName = lines.get(0);
                LOG.info("Docker image name: {}", dockerImageName);
                System.setProperty(Constants.DOCKER_IMAGE_NAME_PROPERTY, dockerImageName);
            }
        } else {
            LOG.warn("Docker image name file: {} not exists!", dockerImageNameFilePath);
        }
    }
}
