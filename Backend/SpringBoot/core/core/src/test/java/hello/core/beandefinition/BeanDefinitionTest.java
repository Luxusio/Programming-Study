package hello.core.beandefinition;

import hello.core.AppConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class BeanDefinitionTest {

    AnnotationConfigApplicationContext aac = new AnnotationConfigApplicationContext(AppConfig.class);
    GenericXmlApplicationContext xac = new GenericXmlApplicationContext("appConfig.xml");

    @Test
    @DisplayName("check bean setting metadata - annotation")
    void findAnnotationApplicationBeanMetadata() {
        String[] beanDefinitionNames = aac.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = aac.getBeanDefinition(beanDefinitionName);

            if (beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {
                System.out.println(
                        "beanDefinitionName = " + beanDefinitionName +
                                "beanDefinition = " + beanDefinition
                );
            }
        }
    }


    @Test
    @DisplayName("check bean setting metadata - xml")
    void findXmlApplicationBeanMetadata() {
        String[] beanDefinitionNames = xac.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = xac.getBeanDefinition(beanDefinitionName);

            if (beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {
                System.out.println(
                        "beanDefinitionName = " + beanDefinitionName +
                                "beanDefinition = " + beanDefinition
                );
            }
        }
    }


}
