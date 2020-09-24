package com.cloudyengineering.api;

import io.quarkus.runtime.StartupEvent;
import io.split.client.SplitClient;
import io.split.client.SplitClientConfig;
import io.split.client.SplitFactory;
import io.split.client.SplitFactoryBuilder;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

@ApplicationScoped
public class FeatureSwitcher implements Evaluator<String>, SwitchingClient<SplitClient> {

    private Logger log = LoggerFactory.getLogger(FeatureSwitcher.class);

    @Inject
    @ConfigProperty(name = "split.apitoken")
    String apiToken;

    SplitClient client;

    public FeatureSwitcher() {
    }

    void startup(@Observes StartupEvent event) throws Exception {
        SplitClientConfig config = SplitClientConfig.builder()
                .setBlockUntilReadyTimeout(10000)
                .build();

        SplitFactory splitFactory = SplitFactoryBuilder.build(apiToken, config);
        this.client = splitFactory.client();
        try {
            client.blockUntilReady();
        } catch (TimeoutException | InterruptedException e) {
            log.error("Error loading Split config", e);
        }
    }

    @Override
    public Optional<String> evaluate(String customerId, String flag) {
        String treatment = this.client.getTreatment(customerId, flag);
        return Optional.of(treatment);
    }

    @Override
    public SplitClient getClient() {
        return this.client;
    }
}
