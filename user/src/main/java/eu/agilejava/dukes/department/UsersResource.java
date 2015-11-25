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
package eu.agilejava.dukes.department;

import eu.agilejava.dukes.ApiKeyRequired;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Ivar Grimstad (ivar.grimstad@gmail.com)
 */
@ApplicationScoped
@Path("users")
public class UsersResource {

    @Context
    private UriInfo uriInfo;

    @EJB
    private UserService userService;

    /**
     * /departments
     *
     * @return
     */
    @GET
    @Produces(APPLICATION_JSON)
    public Response allDepartments() {

        return Response.ok(userService.findAll()).build();
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
    public Response create(@Valid User d) {

        userService.addDepartment(d);
        return Response.created(uriInfo.getAbsolutePathBuilder().segment(d.getId().toString()).build()).build();
    }

    @GET
    @Produces(APPLICATION_JSON)
    @Path("{id}")
    public Response getDepartment(@PathParam("id") String id) {

        try {

            return Response.ok(userService.find(id)).build();

        } catch (SuperException e) {
            
            return Response.status(Status.NOT_FOUND).build();
        }
    }
}
