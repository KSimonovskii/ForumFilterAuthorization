package telran.java57.forum.accounting.service;

import telran.java57.forum.accounting.dto.RolesDto;
import telran.java57.forum.accounting.dto.UpdateUserDto;
import telran.java57.forum.accounting.dto.UserDto;
import telran.java57.forum.accounting.dto.UserRegisterDto;

public interface UserAccountService {
    UserDto register(UserRegisterDto userRegisterDto);

    UserDto getUser(String name);

    void changePassword(String name, String newPassword);

    UserDto removeUser(String login);

    UserDto updateUser(String login, UpdateUserDto updateUserDto);

    RolesDto changeRolesList(String login, String role, boolean isAddRole);
}
