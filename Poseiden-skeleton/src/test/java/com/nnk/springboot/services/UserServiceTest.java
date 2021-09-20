package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.exceptions.UserNotFoundException;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Class that test {@link UserService}
 *
 * @author Christine Duarte
 */
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    /**
     * An Instance of UserService
     */
    private UserService userServiceTest;

    /**
     * A mock of UserRepository
     */
    @Mock
    private UserRepository userRepositoryMock;

    /**
     * An attribute of User
     */
    private User userTest;

    /**
     * Method that initialize instances to perform each test
     */
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

    /**
     * Method that test get all {@link User}
     * then return a list with three elements
     */
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

    /**
     * Method that test get user by id
     * when user is found in database
     */
    @Test
    public void getUserByIdTest_whenUserIsFound_thenReturnOptionalOfUser() {
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

    /**
     * Method that test get user by id
     * when user not found in database
     * then return a {@link UserNotFoundException}
     */
    @Test
    public void getUserByIdTest_whenUserNotFound_thenUserNotFoundException() {
        //GIVEN
        when(userRepositoryMock.findById(isA(Integer.class))).thenReturn(Optional.empty());
        //WHEN
        //THEN
        verify(userRepositoryMock, times(0)).findById(isA(Integer.class));
        assertThrows(UserNotFoundException.class, () -> userServiceTest.getUserById(userTest.getId()));
    }

    /**
     * Method that test add a user
     * when user is not recorded in database
     */
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

    /**
     * Method that test update a user
     * when user exist in database
     */
    @Test
    public void updateUserTest_whenUserExist_thenReturnUserUpdated() {
        //GIVEN
        User userTestUpdated = User.builder()
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

    /**
     * Method that test update a user
     * when user not exist in database
     * then throw {@link UserNotFoundException}
     */
    @Test
    public void updateUserTest_whenUserNotExist_thenThrowUserNotFoundException() {
        //GIVEN
        when(userRepositoryMock.getById(anyInt())).thenReturn(null);
        //WHEN
        //THEN
        verify(userRepositoryMock, times(0)).save(isA(User.class));
        assertThrows(UserNotFoundException.class, () -> userServiceTest.updateUser(userTest));
    }

    /**
     * Method that test deletion by id of a user
     */
    @Test
    public void deleteUserTest_whenUserExist_ThenReturnMessageUserDeleted() {
        //GIVEN
        Integer id = 1;
        //WHEN
        String messageResult = userServiceTest.deleteUser(id);
        //THEN
        verify(userRepositoryMock, times(1)).deleteById(anyInt());
        assertEquals("User deleted", messageResult);
    }
}
