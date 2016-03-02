


import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;



public class ApplicationLoader {

    public ApplicationLoader() {
    }

    public static AnnotationConfigWebApplicationContext loadWebApplicationContext(Class<?>... contextClasses) {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.getEnvironment().setActiveProfiles("developer");

        context.register(contextClasses);
        context.refresh();
        return context;
    }

}