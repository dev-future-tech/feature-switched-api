package com.cloudyengineering.api;

import com.launchdarkly.sdk.LDUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("/v1/puppy")
public class PuppyResource {

    @Inject
    FeatureSwitcher switcher;

    private final Logger log = LoggerFactory.getLogger(PuppyResource.class);

    @GET
    @Produces(value = "application/json")
    public Response getPuppies(    @DefaultValue("all")
                                   @QueryParam(value = "breed") String breed,
                               @DefaultValue("both")
                               @QueryParam("gender") String gender) {

        LDUser user = new LDUser("anthony@test.com");
        boolean treatment = switcher.getClient().boolVariation("log_welcomes", user, false);

        log.info("treatment is {}", treatment);
        if (treatment) {
            log.info("Welcome to the feature!");
        }
        log.debug("Assembling puppies...");

        PuppyDTO puppy1 = new PuppyDTO();
        puppy1.setId("1GH564");
        puppy1.setBreed("Chihuahua");
        puppy1.setName("Josh");
        puppy1.setGender("Male");

        PuppyDTO puppy2 = new PuppyDTO();
        puppy2.setId("98HRYW");
        puppy2.setBreed("German Shepherd");
        puppy2.setName("Nora");
        puppy2.setGender("Female");

        PuppyDTO puppy3 = new PuppyDTO();
        puppy3.setId("57HE5D");
        puppy3.setBreed("German Shepherd");
        puppy3.setName("Beth");
        puppy3.setGender("Female");

        PuppyDTO puppy4 = new PuppyDTO();
        puppy4.setId("98HRYW");
        puppy4.setBreed("German Shepherd");
        puppy4.setName("Jack");
        puppy4.setGender("Male");


        List<PuppyDTO> results = new ArrayList<>();
        Collections.addAll(results, puppy1, puppy2, puppy3, puppy4);

        log.debug("Filtering level 1...");
        if (!gender.equals("both")) {
            results = results
                    .stream()
                    .filter(puppy -> puppy.getGender().toLowerCase().equals(gender.toLowerCase())).collect(Collectors.toList());
        }

        log.debug("Filtering level 2...");
        if (!breed.equals("all")) {
            results = results
                    .stream()
                    .filter(puppy -> puppy.getBreed().equals(breed)).collect(Collectors.toList());
        }

        log.debug("Responding");
        return Response.ok(results).build();
    }
}
