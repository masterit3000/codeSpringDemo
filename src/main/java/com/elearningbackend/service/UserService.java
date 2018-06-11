package com.elearningbackend.service;

import com.elearningbackend.customerrorcode.Errors;
import com.elearningbackend.customexception.ElearningException;
import com.elearningbackend.dto.Pager;
import com.elearningbackend.dto.UserDto;
import com.elearningbackend.entity.User;
import com.elearningbackend.repository.IUserRepository;
import com.elearningbackend.utility.Constants;
import com.elearningbackend.utility.Paginator;
import com.elearningbackend.utility.SecurityUtil;
import com.elearningbackend.utility.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
public class UserService extends AbstractUserService<UserDto, String, User> {

    @Value("${url.user.anonymous_picture}")
    private String anonymousImageUrl;

    @Autowired
    public UserService(JpaRepository<User, String> repository) {
        super(repository, new Paginator<>(UserDto.class));
    }

    @Override
    public Pager<UserDto> loadAll(int currentPage, int noOfRowInPage, String sortBy, String direction) {
        Page<User> pager = getUserRepository().findAll(
            Paginator.getValidPageRequest(currentPage, noOfRowInPage, ServiceUtils.proceedSort(sortBy, direction)));
        return paginator.paginate(currentPage, pager, noOfRowInPage, mapper);
    }

    @Override
    public UserDto getOneByKey(String key) throws ElearningException{
        User user = getUserRepository().findOne(key);
        if (user == null) {
            throw new ElearningException(Errors.USER_NOT_FOUND.getId(), Errors.USER_NOT_FOUND.getMessage());
        }
        UserDto userDto = mapUserDto(user);
        return userDto;

    }

    @Override
    public UserDto getOneByEmail(String email) {
        User user = getUserRepository().findByEmail(email);
        return user == null ? null : mapUserDto(user);
    }

    @Override
    public UserDto add(UserDto userDto) throws ElearningException {
        if (getUserRepository().findOne(userDto.getUsername()) != null)
            throw new ElearningException(Errors.USER_EXISTS.getId(), Errors.USER_EXISTS.getMessage());
        if (getUserRepository().findByEmail(userDto.getEmail()) != null)
            throw new ElearningException(Errors.EMAIL_EXISTS.getId(), Errors.EMAIL_EXISTS.getMessage());
        userDto.setAvatar(anonymousImageUrl);
        saveUser(userDto,true);
        return userDto;
    }

    @Override
    public UserDto edit(UserDto userDto) throws ElearningException {
        UserDto userDtoCheck = getOneByKey(userDto.getUsername());
        if (validateUserDtoForUpdate(userDto, userDtoCheck)) {
            saveUser(userDto,true);
            return userDto;
        }
        throw new ElearningException(Errors.USER_ERROR.getId(),Errors.USER_ERROR.getMessage());
    }

    @Override
    public UserDto delete(String key) throws ElearningException {
        UserDto userDto = getOneByKey(key);
        if (userDto != null){
            userDto.setActivated(Constants.STATUS_LOCKED);
            saveUser(userDto, false);
            return userDto;
        }
        throw new ElearningException(Errors.USER_NOT_FOUND.getId(),Errors.USER_NOT_FOUND.getMessage());
    }

    @Override
    public boolean active(UserDto userDto) throws ElearningException {
        UserDto userDtoChange = getOneByEmail(userDto.getEmail());
        if (userDto != null){
            userDtoChange.setActivated(Constants.STATUS_ACTIVATED);
            userDtoChange.setActivatedAt(userDto.getActivatedAt());
            saveUser(userDtoChange,false);
            return true;
        }
        throw new ElearningException(Errors.USER_NOT_FOUND.getId(),Errors.USER_NOT_FOUND.getMessage());
    }

    boolean validateUserDtoForUpdate(UserDto userDto, UserDto userDtoCheck) throws ElearningException{
        if(!userDtoCheck.getPassword().equals(SecurityUtil.sha256(userDto.getPassword())))
            throw new ElearningException(Errors.USER_PASSWORD_NOT_MATCH.getId(), Errors.USER_PASSWORD_NOT_MATCH.getMessage());
        if (userDtoCheck == null) {
            throw new ElearningException(Errors.USER_NOT_FOUND.getId(), Errors.USER_NOT_FOUND.getMessage());
        }
        if (getOneByEmail(userDto.getEmail()) != null && !userDto.getEmail().equals(userDtoCheck.getEmail())){
            throw new ElearningException(Errors.EMAIL_SAME_WITH_OTHER_USERS.getId(), Errors.EMAIL_SAME_WITH_OTHER_USERS.getMessage());
        }
        return true;
    }

    public UserDto updateAvatar(String avatarUrl, String username) throws ElearningException{
        UserDto userDto = getOneByKey(username);
        userDto.setAvatar(avatarUrl);
        saveUser(userDto, false);
        return userDto;
    }

    @Override
    public UserDto updateRole(String key, String role) throws ElearningException {
        UserDto userDto = getOneByKey(key);
        if (userDto.getRole().equals(role)){
            return userDto;
        }
        if(Constants.ROLES_LIST.stream().noneMatch(e -> e.equals(role))) {
            throw new ElearningException(Errors.ROLE_NOT_EXIST.getId(), Errors.ROLE_NOT_EXIST.getMessage());
        }
        userDto.setRole(role);
        saveUser(userDto, false);
        return userDto;
    }

    private UserDto mapUserDto(User user) {
        UserDto userDto = mapper.map(user, UserDto.class);
        userDto.setPassword(user.getPasswordDigest());
        return userDto;
    }

    void saveUser (UserDto userDto,boolean encryptPassword){
        User entity = mapper.map(userDto, User.class);
        entity.setPasswordDigest(encryptPassword ? SecurityUtil.sha256(userDto.getPassword()) : userDto.getPassword() );
        getUserRepository().save(entity);
    }

    IUserRepository getUserRepository() {
        return (IUserRepository) getRepository();
    }
}
