package eu.agilejava.dukes;

import java.io.IOException;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;

/**
 *
 * @author Ivar Grimstad (ivar.grimstad@cybercom.com)
 */
@ApiKeyRequired
@ApplicationScoped
public class ApiKeyFilter implements ContainerRequestFilter {

   private static final String API_KEY_HEADER = "X-API-KEY";
   private static final Logger LOGGER = Logger.getLogger(ApiKeyFilter.class.getName());

//   @Inject
//   @Config(key = "api.key")
   private String apiKey = "ithuset";

   @Override
   public void filter(ContainerRequestContext requestContext) throws IOException {
      
      final String reqApiKey = requestContext.getHeaderString(API_KEY_HEADER);
      LOGGER.fine("Filtering API Key");
      
      if (!apiKey.equals(reqApiKey)) {
         LOGGER.warning(() -> "Invalid API KEY: " + reqApiKey);
         requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                 .entity("Invaild API Key")
                 .build());
      }
   }
}
