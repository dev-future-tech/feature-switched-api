package com.cloudyengineering.api;

import com.launchdarkly.sdk.server.LDClient;
import io.quarkus.runtime.StartupEvent;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@ApplicationScoped
public class FeatureSwitcher {

    private Logger log = LoggerFactory.getLogger(FeatureSwitcher.class);

    @Inject
    @ConfigProperty(name = "launchdarkly.apitoken")
    String apiToken;

    LDClient client;

    public FeatureSwitcher() {
    }

    void startup(@Observes StartupEvent event) throws Exception {
        this.client = new LDClient(apiToken);
    }

    public LDClient getClient() {
        return this.client;
    }
}
