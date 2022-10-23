package core.basesyntax.service;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static RegistrationService service;
    private User user;

    @BeforeAll
    static void beforeAll() {
        service = new RegistrationServiceImpl();
        User user = new User();
        user.setLogin("Jack");
        user.setPassword("qwertyu");
        user.setAge(21);
        service.register(user);
    }

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @Test
    void addNull_NotOk() {
        user = null;
        assertThrows(RuntimeException.class, () ->
                service.register(user), "User can't be null");
    }

    @Test
    void addNegativeAge_NotOk() {
        user.setLogin("Jack");
        user.setPassword("qwertyu");
        user.setAge(-1);
        assertThrows(RuntimeException.class, () ->
                 service.register(user), "Age can't be negative");
    }

    @Test
    void addNullLogin_NotOk() {
        user.setLogin(null);
        user.setPassword("qwertyu");
        user.setAge(21);
        assertThrows(RuntimeException.class, () ->
                service.register(user), "Login can't be null");
    }

    @Test
    void addNullPassword_NotOk() {
        user.setLogin("Jack");
        user.setPassword(null);
        user.setAge(21);
        assertThrows(RuntimeException.class, () ->
                service.register(user), "Password can't be null");
    }

    @Test
    void userIsTooYoung_NotOk() {
        user.setLogin("Jack");
        user.setPassword("qwertyu");
        user.setAge(10);
        assertThrows(RuntimeException.class, () ->
                service.register(user), "User is too young: age" + user.getAge());
    }

    @Test
    void registeredUser() {
        user.setLogin("Jack");
        user.setPassword("qwertyu");
        user.setAge(21);
        assertThrows(RuntimeException.class, () ->
                service.register(user), "We can't register same user");
    }

    @Test
    void tooShortPassword_NotOk() {
        user.setLogin("Jack");
        user.setPassword("12345");
        user.setAge(21);
        assertThrows(RuntimeException.class, () ->
                service.register(user), "Password must be at least "
                + MIN_PASSWORD_LENGTH + " chars length");
    }

    @Test
    void normalRegister() {
        for (int i = 0; i < 20; i++) {
            User temp = new User();
            temp.setLogin("Jack" + i);
            temp.setPassword("qwertyu" + i);
            temp.setAge(21 + i);
            assertEquals(service.register(temp), temp,
                    "User " + user + " must be registered");
        }
    }
}