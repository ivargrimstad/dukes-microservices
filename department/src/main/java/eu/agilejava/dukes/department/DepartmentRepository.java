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

import java.util.List;
import java.util.Optional;
import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Ivar Grimstad (ivar.grimstad@gmail.com)
 */
@Dependent
public class DepartmentRepository {

    @PersistenceContext(unitName = "departmentPU")
    private EntityManager em;

    public List<Department> findAll() {

        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Department.class));

        return em.createQuery(cq).getResultList();
    }

    public Optional<Department> findById(Long id) {

        return Optional.ofNullable(em.find(Department.class, id));
    }

    public Optional<Department> findByUUID(final String uuid) {

        Optional<Department> returnValue = Optional.empty();

        try {

            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Department> cq = cb.createQuery(Department.class);

            Root<Department> root = cq.from(Department.class);
            cq.select(root).where(cb.equal(root.get("uuid"), uuid));

            returnValue = Optional.of(em.createQuery(cq).getSingleResult());

        } catch (NoResultException e) {
            // log or do something cool
        }

        return returnValue;
    }

    public void create(Department department) {
        em.persist(department);
    }

    public Department update(Department department) {
        return em.merge(department);
    }
}
