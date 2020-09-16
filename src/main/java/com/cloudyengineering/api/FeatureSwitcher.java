package com.cloudyengineering.api;

import com.optimizely.ab.Optimizely;
import com.optimizely.ab.OptimizelyFactory;
import io.quarkus.runtime.StartupEvent;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.concurrent.TimeoutException;

@ApplicationScoped
public class FeatureSwitcher {

    private Logger log = LoggerFactory.getLogger(FeatureSwitcher.class);

    @Inject
    @ConfigProperty(name = "optimizely.apitoken")
    String apiToken;

    private Optimizely optimizelyClient;

    public FeatureSwitcher() {
    }

    void startup(@Observes StartupEvent event) throws Exception {
        this.optimizelyClient = OptimizelyFactory.newDefaultInstance(apiToken);
    }

    public Optimizely getClient() {
        return this.optimizelyClient;
    }
}
