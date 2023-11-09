package tutorial.app.security.security.services;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tutorial.app.security.models.User;
import tutorial.app.security.repository.UserRepository;

// The class is annotated with @Service, which indicates that it is a Spring bean that provides business logic
// 1. What is a bean?
//    A bean is an object that is instantiated, assembled, and managed by a Spring IoC(Inversion of Control) container. A bean is simply one of
//    many objects in your application, but it has some special properties and annotations that allows Spring to configure it and inject dependencies
//    into it. A bean can provide business logic, data access, web services, or any other functionality that your application needs. Beans are
//    defined by using the @Bean annotation on a method or the @Component on a class. You can use @Service, @Repository, or @Controller annotations
//    indicate the role of the bean in your application.
@Service
// The UserDetailsService interface is a core component of Spring Security framework.It is used to retrieve user-related data for authentication
// and authorization purposes.
public class UserDetailsServiceImpl implements UserDetailsService {

    // It also has an @Autowired annotation on a field named userRepository, which means that Spring will inject an instance of UserRepository
    // into this class
    @Autowired
    UserRepository userRepository;

    @Override
    //This means that the method will run within a database transaction
    // 2. What is a database transaction?
    //    A database transaction is a logical unit of work that is performed within a database management system against a database. A transaction
    //    represents any changes in a database, such as inserting, updating, deleting, or querying data. A transaction has four properties:
    //    Atomicity, consistency, isolation, and durability(ACID). Atomicity means that a transaction either completes entirely or not at all.
    //    Consistency means that a transaction preserves the integrity and validity of the data.Isolation means that a transaction does not interfere
    //    with other concurrent transactions. Durability means that a transaction's effects are permanent and persist even in the event of a system
    //    failure. Transactions are usually managed by using the @Transactional annotation on a method or class, or by using the TransactionTemplate
    //    or PlatformTransactionManager classes.
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        return UserDetailsImpl.build(user);
    }
}
