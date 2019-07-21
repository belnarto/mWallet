package com.vironit.mwallet.services.impl;

import com.vironit.mwallet.dao.RoleDao;
import com.vironit.mwallet.models.dto.RoleDto;
import com.vironit.mwallet.models.entity.Role;
import com.vironit.mwallet.services.RoleService;
import com.vironit.mwallet.services.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Service
@Transactional(value = "hibernateTransactionManager")
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public RoleDto findById(int id) {
        Role role = roleDao.findById(id);
        return roleMapper.toDto(role);
    }

    @Override
    public RoleDto findByName(String roleName) {
        Role role = roleDao.findByName(roleName);
        return roleMapper.toDto(role);
    }

    @Override
    public void save(RoleDto roleDto) {
        roleDao.save(roleMapper.toEntity(roleDto));
    }

    @Override
    public void delete(RoleDto roleDto) {
        roleDao.delete(roleMapper.toEntity(roleDto));
    }

    @Override
    public void update(RoleDto roleDto) {
        roleDao.update(roleMapper.toEntity(roleDto));
    }

    @Override
    public List<RoleDto> findAll() {
        return roleDao.findAll().stream()
                .map(role -> roleMapper.toDto(role))
                .collect(Collectors.toList());
    }

}
