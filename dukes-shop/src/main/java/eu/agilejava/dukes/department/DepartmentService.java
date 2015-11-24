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

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;

/**
 *
 * @author Ivar Grimstad (ivar.grimstad@gmail.com)
 */
@Singleton
public class DepartmentService {

    private List<Department> departments;

    public void addDepartment(Department department) {
        department.setId(UUID.randomUUID().toString());
        departments.add(department);
    }

    public List<Department> findAll() {
        return departments;
    }
    
    public Department find(final String id) {
        return departments.stream()
                .filter(d -> d.getId().equals(id))
                .findAny()
                .orElseThrow(NoSuchElementException::new);

    }

    @PostConstruct
    private void init() {
        departments = new ArrayList<>();
        Department dep = new Department();
        dep.setId(UUID.randomUUID().toString());
        dep.setName("Jalla");

        departments.add(dep);
    }

}
