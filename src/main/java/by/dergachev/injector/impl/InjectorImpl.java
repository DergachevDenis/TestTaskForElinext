package by.dergachev.injector.impl;

import by.dergachev.annotations.Inject;
import by.dergachev.exceptions.BindingNotFoundException;
import by.dergachev.exceptions.ConstructorNotFoundException;
import by.dergachev.exceptions.TooManyConstructorsException;
import by.dergachev.injector.Injector;
import by.dergachev.provider.Provider;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InjectorImpl implements Injector {

    private Map<String, Class<?>> mapBinds = new ConcurrentHashMap<>();
    private Map<String, Object> mapInstance = new ConcurrentHashMap<>();

    @Override
    public <T> Provider<T> getProvider(Class<T> type) {
        return null;
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> type) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<? extends T> bean;
        if (type.isInterface()) {
            bean = (Class<? extends T>) checkBind(type);
        } else {
            bean = type;
        }

        if (mapInstance.containsKey(bean.getSimpleName())) {
            return (T) mapInstance.get(bean.getSimpleName());
        }

        Constructor<? extends T>[] constructorsBean = (Constructor<? extends T>[]) bean.getConstructors();
        Constructor<?> constructorBean = checkConstructor(constructorsBean);

        if (constructorBean != null && constructorBean.getParameterCount() == 0) {
            Object instance = constructorBean.newInstance();
            mapInstance.put(type.getSimpleName(), instance );
            return (T) instance;
        } else if (constructorBean != null) {
            Class<?>[] parameterTypes = constructorBean.getParameterTypes();
            for (Class<?> clazz : parameterTypes) {
                getBean(clazz);
            }
            List<Object> listInstance = new ArrayList<>();
            for (Class<?> clazz : parameterTypes) {
                listInstance.add(mapInstance.get(clazz.getSimpleName()));
            }
            Object instance = constructorBean.newInstance(listInstance.toArray());
            mapInstance.put(type.getSimpleName(), instance);
            return (T) instance;
        }

        return null;
    }

    private Constructor<?> checkConstructor(Constructor<?>[] constructorsBean) {
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

    private <T> Class<?> checkBind(Class<T> type) {
        if (!mapBinds.containsKey(type.getSimpleName())) {
            throw new BindingNotFoundException("There is no implementation for this interface - " + type.getSimpleName());
        }
        return mapBinds.get(type.getSimpleName());
    }

    @Override
    public <T> void bind(Class<T> intf, Class<? extends T> impl) {
        mapBinds.put(intf.getSimpleName(), impl);
    }

    @Override
    public <T> void bindSingleton(Class<T> intf, Class<? extends T> impl) {
    }
}
