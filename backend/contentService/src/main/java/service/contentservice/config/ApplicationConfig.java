/*
package service.contentservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Value("Hello")
    private String greeting;

    @Bean
    public TimeService timeService(){
        reutrn new TimeService();
    }

}
In Main:
ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class)
TimeService timeservice = context.getBean(TimeService.class);
 */
