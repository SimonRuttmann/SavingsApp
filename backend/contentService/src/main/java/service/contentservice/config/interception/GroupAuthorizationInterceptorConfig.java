package service.contentservice.config.interception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Adds the GroupAuthorizationInterceptor to the registry
 * @see GroupAuthorizationInterceptor
 */
@Component
public class GroupAuthorizationInterceptorConfig implements WebMvcConfigurer {

    private final GroupAuthorizationInterceptor groupAuthorizationInterceptor;

    @Autowired
    public GroupAuthorizationInterceptorConfig(GroupAuthorizationInterceptor groupAuthorizationInterceptor) {
        this.groupAuthorizationInterceptor = groupAuthorizationInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(groupAuthorizationInterceptor);
    }
}