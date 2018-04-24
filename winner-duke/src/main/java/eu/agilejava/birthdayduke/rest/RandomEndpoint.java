package eu.agilejava.birthdayduke.rest;

import org.eclipse.microprofile.metrics.annotation.Metered;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

import static java.time.LocalDate.parse;
import static java.time.temporal.ChronoUnit.DAYS;

@Path("random")
@RequestScoped
public class RandomEndpoint {

    @GET
    @Produces("text/plain")
    @Metered
    public Response doGet(@QueryParam("min") int min, @QueryParam("max") int max) {

        return Response.ok(
                ThreadLocalRandom.current()
                .ints(min, max)
                        .findAny()
                        .orElse(-1)
        ).build();
    }
}