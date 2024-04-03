package com.example.jpaemsi.service;

import com.example.jpaemsi.entities.Role;
import com.example.jpaemsi.entities.User;
import com.example.jpaemsi.repositories.RoleRepository;
import com.example.jpaemsi.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements UserService {
private UserRepository userRepository;
    private RoleRepository roleRepository;



    @Override
    public User addNewUser(User user) {
        user.setUserId(UUID.randomUUID().toString());

        return userRepository.save(user);
    }


    @Override
    public Role addNewRole(Role role) {

        return roleRepository.save(role);
    }

    @Override
    public User findUserByUserName(String userName) {
        return userRepository.findByUsername(userName) ;
    }

    @Override
    public Role findRoleByRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }

    @Override
    public void addRoleToUser(String userName, String roleName) {
        User user = findUserByUserName(userName);
        Role role = findRoleByRoleName(roleName);
        if(user.getRoles()!=null) {
            user.getRoles().add(role);
            role.getUsers().add(user);
        }
        //cette ligne dessous n'est pas necessaire car on a utilisee l'anotation @Transactional
        //userRepository.save(user)
    }

    @Override
    public User authenticate(String userName, String password) {
        User user= userRepository.findByUsername(userName);
        if(user==null)throw new RuntimeException("Bad Credentials!!");
        if(user.getPassword().equals(password)){
        return user;
        }
        throw new RuntimeException("Bad Credentials!!");
    }
//



}
