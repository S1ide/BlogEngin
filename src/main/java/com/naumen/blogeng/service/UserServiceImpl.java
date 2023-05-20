package com.naumen.blogeng.service;

import com.naumen.blogeng.dto.DtoUser;
import com.naumen.blogeng.model.Image;
import com.naumen.blogeng.model.User;
import com.naumen.blogeng.repository.ImageRepository;
import com.naumen.blogeng.repository.UserRepository;
import com.naumen.blogeng.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ImageRepository imageRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${upload.path}")
    private String getUploadDirectory;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, ImageRepository imageRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.imageRepository = imageRepository;
    }
    @Override
    public void saveUser(DtoUser userDto) {
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRoles(List.of(roleRepository.findByName("ROLE_USER")));
        userRepository.save(user);
    }
    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<DtoUser> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::mapToUseDto)
                .collect(Collectors.toList());
    }
    @Override
    public boolean remove(String email) {
        if (email.equals("admin@admin.ru")) {
            return false;
        }
        userRepository.delete(userRepository.findByEmail(email));
        return true;
    }
    public DtoUser mapToUseDto(User user) {
        DtoUser dtoUser = new DtoUser();
        String[] str = user.getUsername().split(" ");
        dtoUser.setFirstName(str[0]);
        dtoUser.setLastName(str[1]);
        dtoUser.setEmail(user.getEmail());
        dtoUser.setId(user.getId());
        return dtoUser;
    }
    @Override
    public void changeFields(User currentUser, String name, String lastName, String email, String password) {
        if (!name.isEmpty()){
            currentUser.setFirstName(name);
        }
        if (!lastName.isEmpty()){
            currentUser.setLastName(lastName);
        }
        if (!email.isEmpty()){
            currentUser.setEmail(email);
        }
        if (!password.isEmpty()){
            currentUser.setPassword(passwordEncoder.encode(password));
        }
        userRepository.save(currentUser);
    }
    @Override
    public void setImage(User user, MultipartFile file) throws IOException {
        String[] strings = file.getOriginalFilename().split("\\.");
        Image image = new Image(ImageIO.read(file.getInputStream()), strings[strings.length-1]);
        Path fileNameAndPath = Paths.get(getUploadDirectory, File.separator, imageRepository.count() + "." + image.getExt());
        Files.write(fileNameAndPath, file.getBytes());
        user.setProfileImage(image);
        imageRepository.save(image);
        userRepository.save(user);
    }
}
