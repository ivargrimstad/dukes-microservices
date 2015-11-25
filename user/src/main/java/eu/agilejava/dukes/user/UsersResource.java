/*
 * The MIT License
 *
 * Copyright 2015 Ivar Grimstad (ivar.grimstad@gmail.com).
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package eu.agilejava.dukes.user;

import eu.agilejava.dukes.ApiKeyRequired;
import java.util.List;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import javax.ws.rs.core.Response;
import static javax.ws.rs.core.Response.Status.CONFLICT;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Ivar Grimstad (ivar.grimstad@gmail.com)
 */
@Path("users")
public class UsersResource {

    @Context
    private UriInfo uriInfo;

    @Inject
    private UserRepository userRepository;

    /**
     * /departments
     *
     * @return
     */
    @GET
    @Produces(APPLICATION_JSON)
    public Response allUsers() {
        return Response.ok(new GenericEntity<List<User>>(userRepository.findAll()) {
        }).build();
    }

    /**
     * /departments/{id}
     *
     * @param d
     * @param id
     * @return
     */
    @POST
    @Consumes(APPLICATION_JSON)
    @ApiKeyRequired
    public Response create(@Valid User user) {

        try {
            userRepository.create(user);
            return Response.created(uriInfo.getAbsolutePathBuilder().segment(user.getId().toString()).build()).build();
        } catch (Throwable e) {
            throw new WebApplicationException(CONFLICT);
        }
    }

    @GET
    @Produces(APPLICATION_JSON)
    @Path("search")
    public Response findByEmail(@QueryParam("email") String email) {

        return Response.ok(userRepository.findByEmail(email)
                .orElseThrow(NotFoundException::new)).build();
    }
}
