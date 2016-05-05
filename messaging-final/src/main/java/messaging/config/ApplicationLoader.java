package messaging.config;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationLoader {

    public ApplicationLoader() {
    }

    public static AnnotationConfigApplicationContext loadWebApplicationContext(Class<?>... contextClasses) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.getEnvironment().setActiveProfiles("developer");

        context.register(contextClasses);
        context.refresh();
        return context;
    }

    public static AnnotationConfigApplicationContext loadWebApplicationContext() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
        return context;
    }

}