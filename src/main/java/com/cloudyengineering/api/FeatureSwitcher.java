package com.cloudyengineering.api;

import com.tradeshift.flagr.EvaluationContext;
import com.tradeshift.flagr.Flagr;
import io.quarkus.runtime.StartupEvent;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.Optional;

@ApplicationScoped
public class FeatureSwitcher implements Evaluator<String>, SwitchingClient<Flagr> {

    private Logger log = LoggerFactory.getLogger(FeatureSwitcher.class);

    @Inject
    @ConfigProperty(name = "flagr.host")
    String flagrHost;

    Flagr client;

    public FeatureSwitcher() {
    }

    void startup(@Observes StartupEvent event) throws Exception {
        this.client = new Flagr(this.flagrHost);
    }

    @Override
    public Flagr getClient() {
        return this.client;
    }

    @Override
    public Optional<String> evaluate(String customerId, String flag) {
        EvaluationContext context = new EvaluationContext(flag);
        return Optional.of(this.client.evaluate(context).getVariantKey());
    }
}
