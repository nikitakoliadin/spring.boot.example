package com.qthegamep.admin.config;

import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.notify.CompositeNotifier;
import de.codecentric.boot.admin.server.notify.LoggingNotifier;
import de.codecentric.boot.admin.server.notify.Notifier;
import de.codecentric.boot.admin.server.notify.RemindingNotifier;
import de.codecentric.boot.admin.server.notify.filter.FilteringNotifier;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

@Configuration
public class NotifierConfiguration {

    private final Integer reminderPeriod;
    private final Integer checkReminderInverval;
    private final InstanceRepository repository;
    private final ObjectProvider<List<Notifier>> otherNotifiers;

    public NotifierConfiguration(@Value("${reminder.period}") Integer reminderPeriod,
                                 @Value("${check.reminder.inverval}") Integer checkReminderInverval,
                                 InstanceRepository repository,
                                 ObjectProvider<List<Notifier>> otherNotifiers) {
        this.reminderPeriod = reminderPeriod;
        this.checkReminderInverval = checkReminderInverval;
        this.repository = repository;
        this.otherNotifiers = otherNotifiers;
    }

    @Bean
    public FilteringNotifier filteringNotifier() {
        CompositeNotifier delegate = new CompositeNotifier(otherNotifiers.getIfAvailable(Collections::emptyList));
        return new FilteringNotifier(delegate, repository);
    }

    @Bean
    public LoggingNotifier notifier() {
        return new LoggingNotifier(repository);
    }

    @Primary
    @Bean(initMethod = "start", destroyMethod = "stop")
    public RemindingNotifier remindingNotifier() {
        RemindingNotifier remindingNotifier = new RemindingNotifier(filteringNotifier(), repository);
        remindingNotifier.setReminderPeriod(Duration.ofMinutes(reminderPeriod));
        remindingNotifier.setCheckReminderInverval(Duration.ofSeconds(checkReminderInverval));
        return remindingNotifier;
    }
}
