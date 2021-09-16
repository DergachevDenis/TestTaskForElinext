package by.dergachev.injector;

public class BindWrapper<T> {

    private final boolean isSingleton;
    private final Class<T> intf;
    private final Class<? extends T> clazz;

    public BindWrapper(boolean isSingleton, Class<? extends T> clazz, Class<T> intf) {
        this.intf = intf;
        this.isSingleton = isSingleton;
        this.clazz = clazz;
    }

    public boolean isSingleton() {
        return isSingleton;
    }

    public Class<T> getIntf() {
        return intf;
    }

    public Class<? extends T> getClazz() {
        return clazz;
    }

}
