# Test task for Elinext

### Installation

* Build requires JDK and Maven to be installed
* Detailed step-by-step installation instructions for different operating systems can be found
  at https://www.baeldung.com/install-maven-on-windows-linux-mac

### Assembly

The assembly takes place using the mvn package command

### Usage

An example of a class with dependencies:

```java
public class UserServiceImpl implements UserService {

  private MailSender mailSender;
  private UserDao userDao;

  @Inject
  public UserServiceImpl(MailSender mailSender, UserDao userDao) {
    this.mailSender = mailSender;
    this.userDao = userDao;
  }
}
```

To implement the binding, the @Inject annotation is added to the constructor of the implementation class as shown above. Then
you need to bind all the dependencies of the class and the class itself using the bind () method:

```java
    Injector injector = new InjectorImpl();
        injector.bind(UserService.class, UserServiceImpl.class);
        injector.bind(MailSender.class, MailSenderImpl.class);
        injector.bind(MessageDao.class, MessageDaoImpl.class);
        injector.bind(UserDao.class, UserDaoImpl.class);
```
To create Singleton you need to use the bindSingleton () method instead of bind ().
The getProvider () method is used to get the Provider of the required class. Then the instance is provided by the Provider using the getInstance () method:
```java
    Provider<UserService> provider = this.injector.getProvider(UserService.class);
    UserService instance = provider.getInstance();
```
### Tests

There are 4 tests written for the assembly, covering the following situations:

* the first test checks the class, belongs to the desired class and to create an instance.
* second test third test checks for Prototype creation for class dependencies.
* the third test checks for the creation of Singletons for class dependencies.











