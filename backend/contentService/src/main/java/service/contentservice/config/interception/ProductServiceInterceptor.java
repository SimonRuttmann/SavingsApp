package service.contentservice.config.interception;

import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import relationalDatabaseModule.service.DatabaseService;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;

@Component
public class ProductServiceInterceptor implements HandlerInterceptor {

    private final DatabaseService databaseService;

    @Autowired
    public ProductServiceInterceptor(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }


    @Override
    public boolean preHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // get groupId from request-path
        Map<String,String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

        if(map != null && map.size() != 0) {
            Long groupId = Long.parseLong(map.get("groupId"), 10);

            KeycloakAuthenticationToken principal = (KeycloakAuthenticationToken) request.getUserPrincipal();
            UUID userId = UUID.fromString(principal.getPrincipal().toString());
            Boolean check = databaseService.checkIfPersonIsMember(userId, groupId);
            if(!check) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is not member of this group");
        }




        return true;
    }
}