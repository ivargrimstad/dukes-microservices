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

import static java.time.LocalDate.parse;
import static java.time.temporal.ChronoUnit.DAYS;

@Path("birthday")
@RequestScoped
public class BirthdayEndpoint {

    @GET
    @Produces("text/plain")
    @Metered
    public Response doGet(@QueryParam("date") String date) {

        return Response.ok(calculateDaysToBirthday(date)).build();
    }

    private int calculateDaysToBirthday(final String birthDate) {

        final LocalDate now = LocalDate.now();
        final LocalDate bdThisYear = parse(birthDate, DateTimeFormatter.ISO_DATE).withYear(now.getYear());

        if (bdThisYear.isAfter(now)) {
            return (int) now.until(bdThisYear, DAYS);
        } else {
            return (int) now.until(bdThisYear.plusYears(1), DAYS);
        }
    }
}