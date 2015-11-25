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

import java.util.List;
import java.util.Optional;
import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

/**
 *
 * @author Ivar Grimstad (ivar.grimstad@gmail.com)
 */
@Dependent
public class UserRepository {

    @PersistenceContext(unitName = "userPU")
    private EntityManager em;

    public List<User> findAll() {

        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(User.class));

        return em.createQuery(cq).getResultList();
    }

    public Optional<User> findById(Long id) {

        return Optional.ofNullable(em.find(User.class, id));
    }

    public Optional<User> findByEmail(final String email) {

        Optional<User> returnValue = Optional.empty();

        try {

            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<User> cq = cb.createQuery(User.class);

            Root<User> root = cq.from(User.class);
            cq.select(root).where(cb.equal(root.get("email"), email));

            returnValue = Optional.of(em.createQuery(cq).getSingleResult());

        } catch (NoResultException e) {
            // log or do something cool
        }

        return returnValue;
    }

    @Transactional
    public void create(User department) {
        em.persist(department);
    }

    public User update(User department) {
        return em.merge(department);
    }
}
