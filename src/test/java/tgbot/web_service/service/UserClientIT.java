package tgbot.web_service.service;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(UserClientIT.class);

    @Autowired
    private UserClient userClient;

    @Test
    void getUserById() {
        GetUserResponse userResponse = userClient.getUserById(1L);
        UserDTO userDTO = userResponse.getUserDTO();

        assertEquals("Porter",userDTO.getFirstName());
        assertEquals("ultricies", userDTO.getNickname());
        logger.info(userDTO.getFirstName() + " " + userDTO.getNickname());
    }

    @Test
    void getUserByNick() {
        GetUserResponse userResponse = userClient.getUserByNick("ultricies");
        UserDTO userDTO = userResponse.getUserDTO();

        assertEquals("Porter",userDTO.getFirstName());
        assertEquals(1L, userDTO.getChatID());
        logger.info(userDTO.getFirstName() + " " + userDTO.getNickname());
    }

    @Test
    void getAllUsers() {
        GetAllUsersResponse allUsersResponse = userClient.getAllUsers(1,10);
        List<UserDTO> usersList = allUsersResponse.getUsersList();

        assertNotNull(usersList);
        assertTrue(usersList.size() > 0);

        logger.info("Total pages: " + allUsersResponse.getTotalPages());
        for (int i = 0; i < usersList.size(); i++) {
            logger.info(usersList.get(i).getFirstName());
        }
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