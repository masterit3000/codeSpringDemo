package com.elearningbackend.service;

import com.elearningbackend.customerrorcode.Errors;
import com.elearningbackend.customexception.ElearningException;
import com.elearningbackend.dto.ChangePassUserDto;
import com.elearningbackend.dto.CurrentUser;
import com.elearningbackend.dto.UserDto;
import com.elearningbackend.entity.User;
import com.elearningbackend.repository.IUserRepository;
import com.elearningbackend.utility.SecurityUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ChangePasswordService implements IChangePasswordService {
    ModelMapper mapper = new ModelMapper();

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    @Qualifier("userService")
    private AbstractUserService<UserDto, String, User> abstractService;

    @Override
    public UserDto changePass(ChangePassUserDto changePassUserDto, CurrentUser currentUser) throws ElearningException {
        UserDto userDto = abstractService.getOneByKey(currentUser.getUsername());
        validateChangePassword(userDto, changePassUserDto);
        userDto.setPassword(changePassUserDto.getPasswordNew());
        saveUser(userDto, true);
        return userDto;
    }

    private void validateChangePassword(UserDto userDto, ChangePassUserDto changePassUserDto) throws ElearningException{
        if (userDto == null)
            throw new ElearningException(Errors.USER_NOT_FOUND.getId(), Errors.USER_NOT_FOUND.getMessage());
        if (userDto.getPassword().equals(SecurityUtil.sha256(changePassUserDto.getPasswordNew())))
            throw new ElearningException(Errors.NEWPASSWORD_EQUALS_OLDPASSWORD.getId(),
                    Errors.NEWPASSWORD_EQUALS_OLDPASSWORD.getMessage());
        if (!userDto.getPassword().equals(SecurityUtil.sha256(changePassUserDto.getPassword())))
            throw new ElearningException(Errors.CHANGE_PASS_ERROR.getId(),
                    Errors.CHANGE_PASS_ERROR.getMessage());
    }

    void saveUser (UserDto userDto,boolean encryptPassword){
        User entity = mapper.map(userDto, User.class);
        entity.setPasswordDigest(encryptPassword ? SecurityUtil.sha256(userDto.getPassword()) : userDto.getPassword() );
        userRepository.save(entity);
    }
}
