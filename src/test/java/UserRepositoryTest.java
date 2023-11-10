/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import com.gameblog.app.model.User;
import com.gameblog.app.repository.RepositoryDAO;
import com.gameblog.app.repository.UserRepository;
import com.gameblog.app.utils.RepositoryException;
import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author orlan
 */
public class UserRepositoryTest {
    
    RepositoryDAO<User> repository;
    
    private User invalidUser;
    private User validUser;
    
    @BeforeEach
    public void setUp() {
        repository = new UserRepository();
        invalidUser = new User("Orlando","1234", "email", new Date());
        validUser = new User("Orlando","1234aA#", "user@gmail.com", new Date());
    }
    
    
    @Test
    public void addCorrectUserTest() throws RepositoryException{
        repository.create(validUser);
    }
    
    @Test
    public void addIncorrectUserTest() throws RepositoryException{
        repository.create(invalidUser);
    }
    
   
   
}
