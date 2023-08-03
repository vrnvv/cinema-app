package mate.academy.cinema.service.impl;

import mate.academy.cinema.dao.RoleDao;
import mate.academy.cinema.model.Role;
import mate.academy.cinema.service.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleDao roleDao;

    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public Role add(Role role) {
        return roleDao.add(role);
    }

    @Override
    public Role getByName(String roleName) {
        return roleDao.getByName(Role.RoleName.valueOf(roleName)).orElseThrow(
                () -> new RuntimeException("Role with role name " + roleName + " not found"));
    }
}
