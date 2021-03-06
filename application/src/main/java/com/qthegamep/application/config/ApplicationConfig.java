package com.qthegamep.application.config;

import com.qthegamep.application.exception.ApplicationConfigInitializationException;
import com.qthegamep.application.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

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

@Slf4j
@Configuration("ApplicationConfig")
public class ApplicationConfig {

    private final String dockerImageNameFilePath;

    @Autowired
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
            log.info("Server IP: {}", serverIp);
            System.setProperty(Constants.SERVER_IP_PROPERTY, serverIp);
        } else {
            log.warn("Server IP is not defined!");
        }
    }

    private Optional<String> getServerIp() throws SocketException {
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        while (networkInterfaces.hasMoreElements()) {
            NetworkInterface networkInterface = networkInterfaces.nextElement();
            log.debug("Interface display name: {}", networkInterface.getDisplayName());
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
                log.debug("Interface display name: {} host address: {}", networkInterface.getDisplayName(), hostAddress);
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
                log.warn("Docker image name file: {} is empty!", dockerImageNameFilePath);
            } else {
                String dockerImageName = lines.get(0);
                log.info("Docker image name: {}", dockerImageName);
                System.setProperty(Constants.DOCKER_IMAGE_NAME_PROPERTY, dockerImageName);
            }
        } else {
            log.warn("Docker image name file: {} not exists!", dockerImageNameFilePath);
        }
    }
}
