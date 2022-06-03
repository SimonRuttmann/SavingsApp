package service.contentservice.config.interception;

import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import relationalDatabaseModule.service.DatabaseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;

/**
 * Interceptor for authorizing requests on ContentService endpoints
 */
@Component
public class GroupAuthorizationInterceptor implements HandlerInterceptor {

    private final DatabaseService databaseService;

    @Autowired
    public GroupAuthorizationInterceptor(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }


    /**
     * The method is used to enforce users are authorized for the requested action
     * @param request All incoming requests
     * @param response The response of the
     * @param handler The handler, which will receive the request after succeeding authorization
     */
    @Override
    public boolean preHandle(
            HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {

        var pathVariables = request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

        if( pathVariables instanceof Map){
            Map<?,?> map = (Map<?,?>) pathVariables;
            if(map.containsKey("groupId")) {
                var groupIdPathVariable = map.get("groupId");
                if(groupIdPathVariable instanceof String){
                    Long groupId = Long.parseLong((String)groupIdPathVariable, 10);

                    KeycloakAuthenticationToken principal = (KeycloakAuthenticationToken) request.getUserPrincipal();

                    UUID userId = UUID.fromString(principal.getPrincipal().toString());

                    boolean isAuthorized = databaseService.checkIfPersonIsMember(userId, groupId);

                    if(!isAuthorized) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is not member of this group");

                    return true;
                }
            }
            return true;
        }
        return true;
    }
}