package com.e_commerce.services;

import com.e_commerce.dao.RoleDao;
import com.e_commerce.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleDao roleDao;

    public Role createNewRole(Role role){

        return roleDao.save(role);
    }
}
