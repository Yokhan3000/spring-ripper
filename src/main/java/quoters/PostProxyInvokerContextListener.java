package quoters;

import java.lang.reflect.Method;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public class PostProxyInvokerContextListener implements ApplicationListener<ContextRefreshedEvent> {

  @Autowired
  private ConfigurableListableBeanFactory factory;

  @Override
  public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
    ApplicationContext applicationContext = contextRefreshedEvent.getApplicationContext();
    String[] names = applicationContext.getBeanDefinitionNames();
    for (String name : names) {
      BeanDefinition definition = factory.getBeanDefinition(name);
      String originalClassName = definition.getBeanClassName();
      try {
        Class<?> aClass = Class.forName(originalClassName);
        Method[] methods = aClass.getMethods();
        for (Method m : methods) {
          if (m.isAnnotationPresent(PostProxy.class)) {
            Object bean = applicationContext.getBean(name);
            Method currentMethod = bean.getClass().getMethod(m.getName(), m.getParameterTypes());
            currentMethod.invoke(bean);
          }
        }

      } catch (Exception e) {
        e.printStackTrace();
      }

    }
  }
}
