package com.naumen.blogeng.service;

import com.naumen.blogeng.dto.DtoBlogUser;
import com.naumen.blogeng.model.BlogUser;
import com.naumen.blogeng.model.Role;
import com.naumen.blogeng.repository.BlogUserRepository;
import com.naumen.blogeng.repository.RoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BlogUserServiceImpl implements BlogUserService{
    private BlogUserRepository blogUserRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;


    public BlogUserServiceImpl(BlogUserRepository blogUserRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder ) {
        this.blogUserRepository = blogUserRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(DtoBlogUser userDto){
        BlogUser user = new BlogUser();
        user.setUsername(userDto.getFirstName() + userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setUsername(passwordEncoder.encode(userDto.getPassword()));

        Role role = roleRepository.findByName("ROLE_ADMIN");
        if(role == null){
            role = checkRoleExist();
        }
        user.setRoles(Arrays.asList(role));
        blogUserRepository.save(user);
    }
    @Override
    public BlogUser findUserByEmail(String email){
        return blogUserRepository.findByEmail(email);
    }

    @Override
    public List<DtoBlogUser> findAllUsers(){
        List<BlogUser> users = blogUserRepository.findAll();
        return users.stream()
                .map((user) -> mapToUseDto(user))
                .collect(Collectors.toList());
    }

    public DtoBlogUser mapToUseDto(BlogUser user){
        DtoBlogUser dtoBlogUser = new DtoBlogUser();
        String[] str = user.getUsername().split(" ");
        dtoBlogUser.setFirstName(str[0]);
        dtoBlogUser.setLastName(str[1]);
        dtoBlogUser.setEmail(user.getEmail());
        return dtoBlogUser;
    }

    public Role checkRoleExist(){
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        return roleRepository.save(role);
    }
}
