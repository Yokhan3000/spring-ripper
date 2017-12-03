package quoters;

import java.lang.annotation.Annotation;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

public class DeprecationHandlerBeeanFactoryPostProcessor implements BeanFactoryPostProcessor {

  @Override
  public void postProcessBeanFactory(
      ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
    String[] beanDefinitionNames = configurableListableBeanFactory.getBeanDefinitionNames();
    for (String name : beanDefinitionNames) {
      BeanDefinition beanDefinition = configurableListableBeanFactory.getBeanDefinition(name);
      String beanClassName = beanDefinition.getBeanClassName();
      try {
        Class<?> beanClass = Class.forName(beanClassName);
        DeprecatedClass annotation = beanClass.getAnnotation(DeprecatedClass.class);
        if (annotation != null) {
          beanDefinition.setBeanClassName(annotation.newImpl().getName());
        }
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      }

    }
  }
}
