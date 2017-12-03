package quoters;

import org.springframework.beans.factory.support.PropertiesBeanDefinitionReader;
import org.springframework.context.support.GenericApplicationContext;

public class PropertieFileApplicationContex extends GenericApplicationContext {

  public PropertieFileApplicationContex(String fileName) {
    PropertiesBeanDefinitionReader reader = new PropertiesBeanDefinitionReader(
        this);
    int i = reader.loadBeanDefinitions(fileName);
    System.out.println("Found " + i + " beans");
    refresh();
  }

  public static void main(String[] args) {
    PropertieFileApplicationContex contex = new PropertieFileApplicationContex(
        "context.properties");
    contex.getBean(Quoter.class).sayQuote();

  }
}
