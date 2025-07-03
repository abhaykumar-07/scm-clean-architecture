package com.scm.backend.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dkgwgormx",
                "api_key", "891451211854648",
                "api_secret", "1dtVgUh725PdTXxc0u0BPbLe55U"
        ));
    }
}
