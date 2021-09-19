package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.exceptions.TradeNotFoundException;
import com.nnk.springboot.exceptions.UserNotFoundException;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private UserService userServiceTest;

    @Mock
    private UserRepository userRepositoryMock;

    private User userTest;

    @BeforeEach
    public void setPerTest() {
        userServiceTest = new UserService(userRepositoryMock);
        userTest = User.builder()
                .id(1)
                .fullname("Fullname")
                .username("Username")
                .role("Role")
                .password("Password")
                .build();
    }

    @Test
    public void getUsersTest_whenListOfUserContainThreeElements_thenReturnSizeIsGreaterThanZero() {
        //GIVEN
        List<User> users = new ArrayList<>(
                Arrays.asList(
                        User.builder().id(1).fullname("Fullname").username("Username").role("Role").password("Password").build(),
                        User.builder().id(2).fullname("Fullname1").username("Username1").role("Role1").password("Password1").build(),
                        User.builder().id(3).fullname("Fullname1").username("Username1").role("Role1").password("Password1").build()
                       ));
        when(userRepositoryMock.findAll()).thenReturn(users);
        //WHEN
        List<User> usersResult = userServiceTest.getUsers();
        //THEN
        verify(userRepositoryMock, times(1)).findAll();
        assertEquals(users, usersResult);
        assertTrue(usersResult.size() > 0);
    }

    @Test
    public void getUserByIdTest_whenUserFound_thenReturnOptionalOfUser() {
        //GIVEN
        when(userRepositoryMock.findById(isA(Integer.class))).thenReturn(Optional.of(userTest));
        //WHEN
       User userByIdResult = userServiceTest.getUserById(userTest.getId());
        //THEN
        verify(userRepositoryMock, times(1)).findById(isA(Integer.class));
        assertNotNull(userByIdResult);
        assertEquals(1, userByIdResult.getId());
        assertEquals("Fullname", userByIdResult.getFullname());
        assertEquals("Username", userByIdResult.getUsername());
        assertEquals("Password", userByIdResult.getPassword());
        assertEquals("Role", userByIdResult.getRole());
    }

    @Test
    public void getUserByIdTest_whenUserNotFound_thenUserNotFoundException() {
        //GIVEN
        when(userRepositoryMock.findById(isA(Integer.class))).thenReturn(Optional.empty());
        //WHEN
        //THEN
        verify(userRepositoryMock, times(0)).findById(isA(Integer.class));
        assertThrows(UserNotFoundException.class, () -> userServiceTest.getUserById(userTest.getId()));
    }

    @Test
    public void addUserTest_whenUserNotRecordedInDb_thenReturnUserAdded() {
        //GIVEN
        when(userRepositoryMock.save(isA(User.class))).thenReturn(userTest);
        //WHEN
        User userResult = userServiceTest.addUser(userTest);
        //THEN
        verify(userRepositoryMock, times(1)).save(isA(User.class));
        assertEquals(1, userResult.getId());
        assertEquals("Fullname", userResult.getFullname());
        assertEquals("Username", userResult.getUsername());
        assertEquals("Role", userResult.getRole());
    }

    @Test
    public void updateUserTest_whenUserExist_thenReturnUserUpdated() {
        //GIVEN
       User  userTestUpdated = User.builder()
                .id(1)
                .fullname("FullnameUpdated")
                .username("UsernameUpdated")
                .role("Admin")
                .password("Password")
                .build();

        when(userRepositoryMock.getById(isA(Integer.class))).thenReturn(userTest);
        when(userRepositoryMock.save(isA(User.class))).thenReturn(userTestUpdated);
        //WHEN
        User userUpdatedResult = userServiceTest.updateUser(userTestUpdated);
        //THEN
        verify(userRepositoryMock, times(1)).save(isA(User.class));
        assertEquals("UsernameUpdated", userUpdatedResult.getUsername());
        assertEquals("FullnameUpdated", userUpdatedResult.getFullname());
        assertEquals("Admin", userUpdatedResult.getRole());
    }

    @Test
    public void updateUserTest_whenUserNotExist_thenThrowUserNotFoundException() {
        //GIVEN
        when(userRepositoryMock.getById(anyInt())).thenReturn(null);
        //WHEN
        //THEN
        verify(userRepositoryMock, times(0)).save(isA(User.class));
        assertThrows(UserNotFoundException.class, () -> userServiceTest.updateUser(userTest));
    }

    @Test
    public void deleteUserTest_whenUserExist_ThenReturnMessageUserDeleted() {
        //GIVEN
        Integer id = 1;
        //WHEN
        String messageResult = userServiceTest.deleteUser(id);
        //THEN
        verify(userRepositoryMock,times(1)).deleteById(anyInt());
        assertEquals("User deleted", messageResult);
    }



}
