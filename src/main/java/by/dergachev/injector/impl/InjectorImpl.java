package by.dergachev.injector.impl;

import by.dergachev.annotations.Inject;
import by.dergachev.exceptions.BeanCreationException;
import by.dergachev.exceptions.BindingNotFoundException;
import by.dergachev.exceptions.ConstructorNotFoundException;
import by.dergachev.exceptions.TooManyConstructorsException;
import by.dergachev.injector.BindWrapper;
import by.dergachev.injector.Injector;
import by.dergachev.provider.Provider;
import by.dergachev.provider.impl.ProviderImpl;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("unchecked")
public class InjectorImpl implements Injector {

    private final Map<String, BindWrapper<?>> mapBinds = new ConcurrentHashMap<>();
    private final Map<String, Object> mapInstance = new ConcurrentHashMap<>();

    @Override
    public <T> Provider<T> getProvider(Class<T> type) {

        if (!mapBinds.containsKey(type.getSimpleName())) {
            return null;
        }
        T instance;
        try {
            instance = getBean(type);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new BeanCreationException("Can not build bean");
        }
        return new ProviderImpl<T>(instance);
    }


    public synchronized <T> T getBean(Class<T> type) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        BindWrapper bindWrapper = getBindWrapper(type);

        if (bindWrapper.isSingleton() && bindWrapper.getIntf()!= null && mapInstance.containsKey(bindWrapper.getIntf().getSimpleName())) {
                return (T) mapInstance.get(bindWrapper.getIntf().getSimpleName());
        }

        Constructor<? extends T>[] constructorsBean = (Constructor<? extends T>[]) bindWrapper.getClazz().getConstructors();
        Constructor<?> constructorBean = getConstructor(constructorsBean);

        if (constructorBean != null && constructorBean.getParameterCount() == 0) {
            Object instance = constructorBean.newInstance();
            mapInstance.put(type.getSimpleName(), instance);
            return (T) instance;
        } else if (constructorBean != null) {
            Class<?>[] parameterTypes = constructorBean.getParameterTypes();
            for (Class<?> clazz : parameterTypes) {
                getBean(clazz);
            }

            List<Object> listInstance = new ArrayList<>();
            Arrays.stream(parameterTypes).forEach(clazz -> listInstance.add(mapInstance.get(clazz.getSimpleName())));

            Object instance = constructorBean.newInstance(listInstance.toArray());
            mapInstance.put(type.getSimpleName(), instance);
            return (T) instance;
        }
        return null;
    }

    private Constructor<?> getConstructor(Constructor<?>[] constructorsBean) {
        Constructor<?> constructorBeanInject = null;
        Constructor<?> constructorBean = null;
        int count = 0;
        boolean constructorWithOutParameters = false;
        for (Constructor<?> constructor : constructorsBean) {
            if (constructor.getParameterCount() == 0) {
                constructorWithOutParameters = true;
                constructorBean = constructor;
            }
            if (constructor.isAnnotationPresent(Inject.class)) {
                constructorBeanInject = constructor;
                count++;
                if (count > 1) {
                    throw new TooManyConstructorsException("There are several constructors with @Inject annotation in the class");
                }
            }
        }
        if (count == 1) {
            return constructorBeanInject;
        } else if (!constructorWithOutParameters) {
            throw new ConstructorNotFoundException("No default constructor found");
        }
        return constructorBean;
    }

    private <T> BindWrapper getBindWrapper(Class<T> type) {

        if (!type.isInterface()) {
            return new BindWrapper(false, type, null);
        }
        if (!mapBinds.containsKey(type.getSimpleName())) {
            throw new BindingNotFoundException("There is no implementation for this interface - " + type.getSimpleName());
        }
        return mapBinds.get(type.getSimpleName());
    }

    @Override
    public synchronized  <T> void bind(Class<T> intf, Class<? extends T> impl) {
        BindWrapper bindWrapper = new BindWrapper(false, impl, intf);
        mapBinds.put(intf.getSimpleName(), bindWrapper);
    }

    @Override
    public synchronized  <T> void bindSingleton(Class<T> intf, Class<? extends T> impl) {
        BindWrapper bindWrapper = new BindWrapper(true, impl, intf);
        mapBinds.put(intf.getSimpleName(), bindWrapper);
    }
}
