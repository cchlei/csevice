package org.gradle.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "defult",locations = "classpath:config/defult.properties")  
public class Settings {
	
	private String name;  
    private String gender;
      
    public String getName() {  
       return name;  
    }  
    public void setName(String name) {  
          this.name = name;  
    }  
    public String getGender() {  
        return gender;  
    }  
    public void setGender(String gender) {  
        this.gender = gender;  
    }  
}  
