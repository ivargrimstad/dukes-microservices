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

import eu.agilejava.snoop.annotation.Snoop;
import eu.agilejava.snoop.client.SnoopServiceClient;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import static javax.ws.rs.core.Response.Status.OK;

/**
 *
 * @author Ivar Grimstad (ivar.grimstad@gmail.com)
 */
@Stateless
public class DepartmentService {

    @Snoop(serviceName = "department")
    @Inject
    private SnoopServiceClient departmentService;

    public Optional<String> addDepartment(Department department) {
        
        department.setUuid(UUID.randomUUID().toString());

        Response response = departmentService.getServiceRoot()
                .path("departments")
                .request()
                .header("X-API-KEY", "ithuset")
                .post(Entity.entity(department, APPLICATION_JSON));

        if (response.getStatus() == 201) {
            MultivaluedMap<String, Object> headers = response.getHeaders();
            return Optional.of(department.getUuid());
        }

        return Optional.empty();
    }

    public List<Department> findAll() {
        return departmentService.getServiceRoot()
                .path("departments")
                .request(APPLICATION_JSON)
                .get(new GenericType<List<Department>>() {
                });

    }

    public Department find(final String uuid) throws SuperException {

        return departmentService.simpleGet("departments/" + uuid)
                .filter(r -> r.getStatus() == OK.getStatusCode())
                .map(r -> r.readEntity(Department.class))
                .orElseThrow(SuperException::new);
    }

}
