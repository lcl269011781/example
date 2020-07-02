package com.lcl.dataxexample;

import com.alibaba.datax.core.Engine;
import com.lcl.dataxexample.datax.NewEngine;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalTime;

@SpringBootApplication
public class DataxExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataxExampleApplication.class, args);
    }
}
