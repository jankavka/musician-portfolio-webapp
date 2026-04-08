package cz.kavka.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Value("${photos.resource}")
    private String photoResourceLocation;

    @Value("${projects.resource}")
    private String projectPhotoResourceLocation;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/photos/**", "/project-photos/**")
                .addResourceLocations(photoResourceLocation)
                .addResourceLocations(projectPhotoResourceLocation);
    }
}
