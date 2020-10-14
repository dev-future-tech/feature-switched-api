package com.cloudyengineering.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path(("/autocomplete"))
public class AutoCompleteResource {

    @GET
    public Response autocomplete(@QueryParam("char_str") String value) {
        return null;
    }
}
