package com.qthegamep.application.config;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP;
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;
import ch.qos.logback.core.util.FileSize;
import ch.qos.logback.core.util.StatusPrinter;
import com.qthegamep.application.Application;
import com.qthegamep.application.util.Constants;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;

@Configuration
public class LogConfig {

    private final String rootLoggerLevel;
    private final Boolean rootLoggerFileEnabled;
    private final String rootLoggerFileName;
    private final String rootLoggerFilePath;
    private final String rootLoggerFileAppender;
    private final String rootLoggerFileNamePattern;
    private final String rootLoggerFileMaxFileSize;
    private final String rootLoggerFileMaxHistory;
    private final String rootLoggerFileTotalSizeCap;
    private final String rootLoggerFilePattern;

    public LogConfig(@Value("${root.logger.level}") String rootLoggerLevel,
                     @Value("${root.logger.file.enable}") Boolean rootLoggerFileEnabled,
                     @Value("${root.logger.file.name}") String rootLoggerFileName,
                     @Value("${root.logger.file.path}") String rootLoggerFilePath,
                     @Value("${root.logger.file.appender}") String rootLoggerFileAppender,
                     @Value("${root.logger.file.name.pattern}") String rootLoggerFileNamePattern,
                     @Value("${root.logger.file.max.file.size}") String rootLoggerFileMaxFileSize,
                     @Value("${root.logger.file.max.history}") String rootLoggerFileMaxHistory,
                     @Value("${root.logger.file.total.size.cap}") String rootLoggerFileTotalSizeCap,
                     @Value("${root.logger.file.pattern}") String rootLoggerFilePattern) {
        this.rootLoggerLevel = rootLoggerLevel;
        this.rootLoggerFileEnabled = rootLoggerFileEnabled;
        this.rootLoggerFileName = rootLoggerFileName;
        this.rootLoggerFilePath = rootLoggerFilePath;
        this.rootLoggerFileAppender = rootLoggerFileAppender;
        this.rootLoggerFileNamePattern = rootLoggerFileNamePattern;
        this.rootLoggerFileMaxFileSize = rootLoggerFileMaxFileSize;
        this.rootLoggerFileMaxHistory = rootLoggerFileMaxHistory;
        this.rootLoggerFileTotalSizeCap = rootLoggerFileTotalSizeCap;
        this.rootLoggerFilePattern = rootLoggerFilePattern;
    }

    @PostConstruct
    public void configureLogLevels() {
        configureRootLogger();
    }

    private void configureRootLogger() {
        Logger logger = (Logger) LoggerFactory.getLogger(Application.class.getPackage().getName());
        logger.setLevel(Level.toLevel(rootLoggerLevel));
        String dockerImageName = System.getProperty(Constants.DOCKER_IMAGE_NAME_PROPERTY);
        if (rootLoggerFileEnabled && dockerImageName != null && !dockerImageName.isEmpty()) {
            LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
            logger.addAppender(buildRollingFileAppender(loggerContext, dockerImageName));
            StatusPrinter.print(loggerContext);
            logger.warn("Log: {} Level: {} File appender enabled!", logger.getName(), logger.getLevel());
        } else {
            logger.warn("Log: {} Level: {} File appender not enabled!", logger.getName(), logger.getLevel());
        }
    }

    private RollingFileAppender<ILoggingEvent> buildRollingFileAppender(LoggerContext loggerContext, String dockerImageName) {
        RollingFileAppender<ILoggingEvent> rollingFileAppender = new RollingFileAppender<>();
        rollingFileAppender.setContext(loggerContext);
        rollingFileAppender.setName(rootLoggerFileName);
        rollingFileAppender.setFile(rootLoggerFilePath.replaceAll(Constants.LOGGER_REPLACE_PATTERN, dockerImageName));
        rollingFileAppender.setAppend(Boolean.parseBoolean(rootLoggerFileAppender));
        rollingFileAppender.setRollingPolicy(buildTimeBasedRollingPolicy(loggerContext, rollingFileAppender, dockerImageName));
        rollingFileAppender.setEncoder(buildPatternLayoutEncoder(loggerContext, rollingFileAppender));
        rollingFileAppender.start();
        return rollingFileAppender;
    }

    private TimeBasedRollingPolicy<ILoggingEvent> buildTimeBasedRollingPolicy(LoggerContext loggerContext, RollingFileAppender<ILoggingEvent> rollingFileAppender, String dockerImageName) {
        TimeBasedRollingPolicy<ILoggingEvent> timeBasedRollingPolicy = new TimeBasedRollingPolicy<>();
        timeBasedRollingPolicy.setContext(loggerContext);
        timeBasedRollingPolicy.setParent(rollingFileAppender);
        timeBasedRollingPolicy.setFileNamePattern(rootLoggerFileNamePattern.replaceAll(Constants.LOGGER_REPLACE_PATTERN, dockerImageName));
        timeBasedRollingPolicy.setTimeBasedFileNamingAndTriggeringPolicy(buildSizeAndTimeBasedFNATP(loggerContext, timeBasedRollingPolicy));
        timeBasedRollingPolicy.setMaxHistory(Integer.parseInt(rootLoggerFileMaxHistory));
        timeBasedRollingPolicy.setTotalSizeCap(FileSize.valueOf(rootLoggerFileTotalSizeCap));
        timeBasedRollingPolicy.start();
        return timeBasedRollingPolicy;
    }

    private SizeAndTimeBasedFNATP<ILoggingEvent> buildSizeAndTimeBasedFNATP(LoggerContext loggerContext, TimeBasedRollingPolicy<ILoggingEvent> timeBasedRollingPolicy) {
        SizeAndTimeBasedFNATP<ILoggingEvent> sizeAndTimeBasedFNATP = new SizeAndTimeBasedFNATP<>();
        sizeAndTimeBasedFNATP.setContext(loggerContext);
        sizeAndTimeBasedFNATP.setTimeBasedRollingPolicy(timeBasedRollingPolicy);
        sizeAndTimeBasedFNATP.setMaxFileSize(FileSize.valueOf(rootLoggerFileMaxFileSize));
        return sizeAndTimeBasedFNATP;
    }

    private PatternLayoutEncoder buildPatternLayoutEncoder(LoggerContext loggerContext, RollingFileAppender<ILoggingEvent> rollingFileAppender) {
        PatternLayoutEncoder patternLayoutEncoder = new PatternLayoutEncoder();
        patternLayoutEncoder.setContext(loggerContext);
        patternLayoutEncoder.setParent(rollingFileAppender);
        patternLayoutEncoder.setCharset(StandardCharsets.UTF_8);
        patternLayoutEncoder.setPattern(rootLoggerFilePattern);
        patternLayoutEncoder.start();
        return patternLayoutEncoder;
    }
}
