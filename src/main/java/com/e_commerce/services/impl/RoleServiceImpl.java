package com.e_commerce.services.impl;

import com.e_commerce.dao.RoleDao;
import com.e_commerce.entity.Role;
import com.e_commerce.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleDao roleDao;

    public Role createNewRole(Role role){

        return roleDao.save(role);
    }
}
