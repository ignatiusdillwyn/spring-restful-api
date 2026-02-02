package com.belajar_restful_api.roles;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RolesServiceImpl implements RolesService{

    private final RolesRepository rolesRepository;

    @Override
    public Roles getRolesById(Long id) {
        return rolesRepository.findById(id)
                .orElse(new Roles());
    }

    @Override
    public Roles saveRoles(Roles roles) {
        return rolesRepository.save(roles);
    }
}