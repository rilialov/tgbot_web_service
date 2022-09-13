package tgbot.web_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import tgbot.web_service.service.TeamClient;
import tgbot.web_service.service.UserClient;

@Configuration
public class UsersServiceClientsConfiguration {

    @Value("${users_service.configuration.uri}")
    private String DEFAULT_URI;

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("tgbot.users.service");
        return marshaller;
    }

    @Bean
    public UserClient userClient(Jaxb2Marshaller marshaller) {
        UserClient client = new UserClient();
        client.setDefaultUri(DEFAULT_URI);
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }

    @Bean
    public TeamClient teamClient(Jaxb2Marshaller marshaller) {
        TeamClient client = new TeamClient();
        client.setDefaultUri(DEFAULT_URI);
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }
}
