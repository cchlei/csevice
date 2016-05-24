package org.gradle.environment;

import org.springframework.beans.factory.annotation.Autowired;

//@Configuration
//@ConditionalOnClass(Mongo.class)
//@EnableConfigurationProperties(MongoProperties.class)
public class MongoAutoConfiguration {
 
    @Autowired
    private MongoProperties properties;
 
}
