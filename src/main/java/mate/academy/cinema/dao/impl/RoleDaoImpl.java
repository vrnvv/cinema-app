package mate.academy.cinema.dao.impl;

import java.util.Optional;
import mate.academy.cinema.dao.AbstractDao;
import mate.academy.cinema.dao.RoleDao;
import mate.academy.cinema.exception.DataProcessingException;
import mate.academy.cinema.model.Role;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDaoImpl extends AbstractDao<Role> implements RoleDao {
    public RoleDaoImpl(SessionFactory factory) {
        super(factory, Role.class);
    }

    @Override
    public Optional<Role> getByName(Role.RoleName roleName) {
        try (Session session = factory.openSession()) {
            Query<Role> findByRole = session.createQuery(
                    "FROM Role WHERE roleName = :roleName", Role.class);
            findByRole.setParameter("roleName", roleName);
            return findByRole.uniqueResultOptional();
        } catch (Exception e) {
            throw new DataProcessingException("Can't find a role " + roleName, e);
        }
    }
}
