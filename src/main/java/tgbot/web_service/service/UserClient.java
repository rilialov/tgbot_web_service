package tgbot.web_service.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;
import tgbot.users.service.*;

public class UserClient extends WebServiceGatewaySupport {

    @Value("${clients.users_service.address}")
    private String SERVER_URI;

    @Value("${clients.namespace.uri}")
    private String NAMESPACE_URI;

    public GetUserResponse getUserById(long chatId) {
        GetUserByIdRequest getUserByIdRequest = new GetUserByIdRequest();
        getUserByIdRequest.setChatId(chatId);
        return (GetUserResponse) getWebServiceTemplate()
                .marshalSendAndReceive(SERVER_URI, getUserByIdRequest,
                        new SoapActionCallback(NAMESPACE_URI + "/GetUserByIdRequest"));
    }

    public GetUserResponse getUserByNick(String nickname) {
        GetUserByNickRequest getUserByNickRequest = new GetUserByNickRequest();
        getUserByNickRequest.setNickname(nickname);
        return (GetUserResponse) getWebServiceTemplate()
                .marshalSendAndReceive(SERVER_URI, getUserByNickRequest,
                        new SoapActionCallback(NAMESPACE_URI + "/GetUserByNickRequest"));
    }

    public GetAllUsersResponse getAllUsers() {
        GetAllUsersRequest getUsersRequest = new GetAllUsersRequest();
        return (GetAllUsersResponse) getWebServiceTemplate()
                .marshalSendAndReceive(SERVER_URI, getUsersRequest,
                        new SoapActionCallback(NAMESPACE_URI + "/GetUsersRequest"));
    }

    public GetUserResponse saveUser(UserDTO userDTO) {
        SaveUserRequest saveUserRequest = new SaveUserRequest();
        saveUserRequest.setUserDTO(userDTO);
        return (GetUserResponse) getWebServiceTemplate()
                .marshalSendAndReceive(SERVER_URI, saveUserRequest,
                        new SoapActionCallback(NAMESPACE_URI + "/SaveUserRequest"));
    }

    public GetBooleanResponse deleteUserById(long chatId) {
        DeleteUserByIdRequest deleteUserByIdRequest = new DeleteUserByIdRequest();
        deleteUserByIdRequest.setChatId(chatId);
        return (GetBooleanResponse) getWebServiceTemplate()
                .marshalSendAndReceive(SERVER_URI, deleteUserByIdRequest,
                        new SoapActionCallback(NAMESPACE_URI + "/DeleteUserByIdRequest"));
    }
}
