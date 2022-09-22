package com.example.system.services;

import com.example.system.entity.User;
import com.example.system.entity.UserImage;
import com.example.system.exceptions.badrequest.UserNotFoundException;
import com.example.system.exceptions.unauthorized.UnauthorizedException;
import com.example.system.repository.UserRepository;
import com.example.system.services.image.ImageService;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    public static final List<GrantedAuthority> DEFAULT_USER_AUTHORITIES =
            AuthorityUtils.commaSeparatedStringToAuthorityList("user");

    private final UserRepository userRepository;
    private final ImageService imageService;
    @Autowired
    @Getter @Setter
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User getUserById(int id) {
        return userRepository.findById(id).orElseThrow(()->new UserNotFoundException(id+""));
    }

    public User getUserByUsername(String username){
        return userRepository.findByUsername(username).orElse(null);
    }

    public User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || authentication.getPrincipal() == null) throw new UnauthorizedException();
        String username = authentication.getPrincipal().toString();
        User user = getUserByUsername(username);
        if(user == null) throw new UnauthorizedException();
        return user;
    }

    public List<User> getAllUsers() { return userRepository.findAll(); }

    public User setProfileImageAndSave(User user, MultipartFile multipartFile) throws IOException {
        return setProfileImageAndSave(user, imageService.saveImage(multipartFile, user));
    }

    public User setProfileImageAndSave(User user, UserImage userImage) {
        user.setImageUrl(userImage.getUrl());
        return userRepository.saveAndFlush(user);
    }

    public User setDepartmentAndSave(String department) {
        User currentUser = getCurrentUser();
        if( !StringUtils.isEmpty(currentUser.getDepartment()) ) return currentUser;
        currentUser.setDepartment(department);
        return userRepository.saveAndFlush(currentUser);
    }

    public User createUser(@NonNull String username, @NonNull String plainPass, @Nullable String imageUrl, @Nullable String integrationId) {
        User user = new User(username, bCryptPasswordEncoder.encode(plainPass), DEFAULT_USER_AUTHORITIES, imageUrl, integrationId);
        return userRepository.saveAndFlush(user);
    }

    public User createUser(@NonNull String username, @NonNull String plainPass, @Nullable byte[] image, @Nullable String imageFilename, @Nullable String integrationId) throws IOException {
        User user = createUser(username, plainPass, null, integrationId);
        // Uncomment if detached entity error is thrown
//        user = userRepository.findById(user.getId()).orElseThrow(()->new UserNotFoundException(username));

        if (image != null && image.length > 0) {
            imageFilename = StringUtils.isEmpty(imageFilename) ? username+"_profile_image.png" : imageFilename;
            UserImage userProfileImg = imageService.saveImage(image, imageFilename, user);
            user = setProfileImageAndSave(user, userProfileImg);
        }
        return user;
    }
}
