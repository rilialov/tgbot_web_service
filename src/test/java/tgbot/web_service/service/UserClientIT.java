package tgbot.web_service.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tgbot.users.service.GetAllUsersResponse;
import tgbot.users.service.GetBooleanResponse;
import tgbot.users.service.GetUserResponse;
import tgbot.users.service.UserDTO;
import tgbot.web_service.config.UsersServiceClientsConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = UsersServiceClientsConfiguration.class)
class UserClientIT {

    @Autowired
    private UserClient userClient;

    @Test
    void getUserById() {
        GetUserResponse userResponse = userClient.getUserById(1L);
        UserDTO userDTO = userResponse.getUserDTO();

        assertEquals("Porter",userDTO.getFirstName());
        assertEquals("ultricies", userDTO.getNickname());
    }

    @Test
    void getUserByNick() {
        GetUserResponse userResponse = userClient.getUserByNick("ultricies");
        UserDTO userDTO = userResponse.getUserDTO();

        assertEquals("Porter",userDTO.getFirstName());
        assertEquals(1L, userDTO.getChatID());
    }

    @Test
    void getAllUsers() {
        GetAllUsersResponse allUsersResponse = userClient.getAllUsers();
        List<UserDTO> usersList = allUsersResponse.getUsersList();

        assertNotNull(usersList);
        assertTrue(usersList.size() > 0);
    }

    @Test
    void saveAndDeleteUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setChatID(18L);
        userDTO.setFirstName("Test FirstName");

        GetUserResponse userResponse = userClient.saveUser(userDTO);
        UserDTO saved = userResponse.getUserDTO();

        assertEquals("Test FirstName", saved.getFirstName());

        GetBooleanResponse booleanResponse = userClient.deleteUserById(saved.getChatID());
        assertTrue(booleanResponse.isDeleted());
    }
}