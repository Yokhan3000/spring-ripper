package screensaver;


import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import javafx.util.Pair;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;
import org.springframework.lang.Nullable;

public class PeriodicalScopeCongigurer implements Scope {

  Map<String, Pair<LocalTime, Object>> map = new HashMap<>();

  @Override
  public Object get(String s, ObjectFactory<?> objectFactory) {
    if (map.containsKey(s)) {
      Pair<LocalTime, Object> pair = map.get(s);
      int secSinceLastRequest = LocalTime.now().getSecond() - pair.getKey().getSecond();
      if (secSinceLastRequest > 3) {
        map.put(s, new Pair(LocalTime.now(), objectFactory.getObject()));
      }

    } else {
      map.put(s, new Pair(LocalTime.now(), objectFactory.getObject()));
    }

    return map.get(s).getValue();
  }

  @Nullable
  @Override
  public Object remove(String s) {
    return null;
  }

  @Override
  public void registerDestructionCallback(String s, Runnable runnable) {

  }

  @Nullable
  @Override
  public Object resolveContextualObject(String s) {
    return null;
  }

  @Nullable
  @Override
  public String getConversationId() {
    return null;
  }
}
