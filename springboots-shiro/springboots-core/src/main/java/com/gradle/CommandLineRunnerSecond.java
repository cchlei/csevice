package com.gradle;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommandLineRunnerSecond implements CommandLineRunner {
 
    @Override
    public void run(String... args) throws Exception {
       System.out.println("CommandLineRunnerSecond.run()");
    }
}
