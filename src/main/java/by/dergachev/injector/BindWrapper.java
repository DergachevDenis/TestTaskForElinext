package by.dergachev.injector;

public class BindWrapper<T> {
    private boolean isSingleton;
    private Class<T> intf;
    private Class<? extends T> clazz;

    public BindWrapper(boolean isSingleton, Class<? extends T> clazz, Class<T> intf) {
        this.intf = intf;
        this.isSingleton = isSingleton;
        this.clazz = clazz;
    }

    public boolean isSingleton() {
        return isSingleton;
    }

    public void setSingleton(boolean singleton) {
        isSingleton = singleton;
    }

    public Class<T> getIntf() {
        return intf;
    }

    public void setIntf(Class<T> intf) {
        this.intf = intf;
    }

    public Class<? extends T> getClazz() {
        return clazz;
    }

    public void setClazz(Class<? extends T> clazz) {
        this.clazz = clazz;
    }
}
