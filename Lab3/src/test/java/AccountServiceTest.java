import ductv.example.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.*;

class AccountServiceTest {

    private AccountService service;

    @BeforeEach
    void setUp() {
        service = new AccountService();
    }

    @ParameterizedTest(name = "[{index}] user={0}, pass={1}, email={2} => expected={3}")
    @CsvFileSource(resources = "/test-data.csv", numLinesToSkip = 1)
    void testRegisterAccount(String username,
                                             String password,
                                             String email,
                                             boolean expected) {
        boolean emailValid = service.isValidEmail(email);
        boolean actual = service.registerAccount(username, password, email);
        if (!emailValid) {
            assertFalse(actual, "Email không hợp lệ thì registerAccount phải trả về false");
        }
        assertEquals(expected, actual);
    }
}
