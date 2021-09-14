package by.dergachev.injector;

import by.dergachev.provider.Provider;

public interface Injector {

   public  <T> Provider<T> getProvider(Class<T> type);
   public  <T> void bind(Class<T> intf, Class<? extends T> impl);
   public  <T> void bindSingleton(Class<T> intf, Class<? extends T> impl);
}
