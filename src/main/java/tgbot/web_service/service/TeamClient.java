package tgbot.web_service.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;
import tgbot.users.service.*;

public class TeamClient extends WebServiceGatewaySupport {

    @Value("${clients.users_service.address}")
    private String SERVER_URI;

    @Value("${clients.namespace.uri}")
    private String NAMESPACE_URI;

    public GetTeamResponse getTeamById(long id) {
        GetTeamByIdRequest getTeamByIdRequest = new GetTeamByIdRequest();
        getTeamByIdRequest.setId(id);
        return (GetTeamResponse) getWebServiceTemplate()
                .marshalSendAndReceive(SERVER_URI, getTeamByIdRequest,
                        new SoapActionCallback(NAMESPACE_URI + "/GetTeamByIdRequest"));
    }

    public GetAllTeamsResponse getAllTeams(int page, int size) {
        GetAllTeamsRequest getAllTeamsRequest = new GetAllTeamsRequest();
        getAllTeamsRequest.setPage(page);
        getAllTeamsRequest.setSize(size);
        return (GetAllTeamsResponse) getWebServiceTemplate().
                marshalSendAndReceive(SERVER_URI, getAllTeamsRequest,
                        new SoapActionCallback(NAMESPACE_URI + "/GetAllTeamsRequest"));
    }

    public GetTeamResponse saveTeam(TeamDTO teamDTO) {
        SaveTeamRequest saveTeamRequest = new SaveTeamRequest();
        saveTeamRequest.setTeamDTO(teamDTO);
        return (GetTeamResponse) getWebServiceTemplate().
                marshalSendAndReceive(SERVER_URI, saveTeamRequest,
                        new SoapActionCallback(NAMESPACE_URI + "/SaveTeamRequest"));
    }

    public GetBooleanResponse deleteTeamById(long id) {
        DeleteTeamByIdRequest deleteTeamByIdRequest = new DeleteTeamByIdRequest();
        deleteTeamByIdRequest.setId(id);
        return (GetBooleanResponse) getWebServiceTemplate()
                .marshalSendAndReceive(SERVER_URI, deleteTeamByIdRequest,
                        new SoapActionCallback(NAMESPACE_URI + "/DeleteTeamByIdRequest"));
    }
}
