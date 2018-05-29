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

/**
 * Generates a random number between 0 and bound.
 * Everyone will get a number. Keep it !
 */
@Path("random")
@RequestScoped
public class RandomEndpoint {

    @GET
    @Produces("text/plain")
    @Metered
    public Response doGet(@QueryParam("bound") int bound) {

        return Response.ok(String.format("The winner of a gift certificate is: %d",
                ThreadLocalRandom.current().nextInt(bound))
        ).build();
    }
}