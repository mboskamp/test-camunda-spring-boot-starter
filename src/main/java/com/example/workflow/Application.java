package com.example.workflow;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

  public static void main(String... args) {
    Map<String, String> classloaderReplacements = new HashMap<>();
    classloaderReplacements.put(
        "org/camunda/bpm/engine/impl/mapping/entity/HistoricProcessInstance.xml",
        "org/camunda/bpm/engine/impl/mapping/entity/replaced-HistoricProcessInstance.xml");
    classloaderReplacements.put(
        "org/camunda/bpm/cockpit/impl/plugin/history/historicProcessInstance.xml",
        "org/camunda/bpm/cockpit/impl/plugin/history/replaced-historicProcessInstance.xml");

    Resources.setDefaultClassLoader(new ClassLoader() {
      public InputStream getResourceAsStream(String name) {
        String replacingResource = classloaderReplacements.get(name);
        if (replacingResource != null) {
          return Application.class.getClassLoader().getResourceAsStream(replacingResource);
        }
        else {
          return null;
        }
      }
    });

    SpringApplication.run(Application.class, args);
  }

}