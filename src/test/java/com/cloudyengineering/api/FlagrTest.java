package com.cloudyengineering.api;

import com.tradeshift.flagr.EvaluationContext;
import com.tradeshift.flagr.EvaluationResponse;
import com.tradeshift.flagr.Flagr;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@QuarkusTest
public class FlagrTest {

    private final Logger log = LoggerFactory.getLogger(FlagrTest.class);

    @Test
    public void testFlag() {
        Flagr client = new Flagr("http://localhost:18000");

        EvaluationContext context = new EvaluationContext("log_welcomes");
        EvaluationResponse response = client.evaluate(context);
        log.debug("Flag key: {}", response.getFlagKey());
        log.debug("Flag variant Key: {}", response.getVariantKey());
        log.debug("Flag variant Id: {}", response.getVariantID());
        Optional<Object> value = client.evaluateVariantAttachment(context, Object.class);
        value.ifPresent(action -> {
            log.info("Action: {}", action);
        });

//        EvaluationContext simpleContext = new EvaluationContext("k8r52szyo4j5krp68");
        EvaluationContext simpleContext = new EvaluationContext("log_people");
        EvaluationResponse simpleResponse = client.evaluate(simpleContext);
        log.debug("Simple Flag key: {}", simpleResponse.getFlagKey());
        log.debug("Simple Flag variant Key: {}", simpleResponse.getVariantKey());
        log.debug("Simple Flag variant Id: {}", simpleResponse.getVariantID());
    }
}
