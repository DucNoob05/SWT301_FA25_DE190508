package tests;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import pages.RegistrationFormPage;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RegistrationFormTest extends BaseTest {

    private RegistrationFormPage formPage;

    @BeforeEach
    void init() {
        formPage = new RegistrationFormPage(driver);
    }

    // ========== TC_REG_001: Dùng CSV với scenario = FULL_VALID ==========
    @ParameterizedTest
    @Order(1)
    @DisplayName("Submit form với dữ liệu hợp lệ (FULL_VALID từ 1 file CSV)")
    @CsvFileSource(resources = "/registration_data.csv", numLinesToSkip = 1)
    void submitFormSuccessfully_Csv(String scenario,
                                    String firstName,
                                    String lastName,
                                    String email,
                                    String gender,
                                    String mobile,
                                    String hobby,
                                    String address) {

        // Chỉ chạy test này cho các dòng scenario = FULL_VALID
        Assumptions.assumeTrue("FULL_VALID".equalsIgnoreCase(scenario));

        formPage.navigate();

        formPage.setFirstName(firstName);
        formPage.setLastName(lastName);
        formPage.setEmail(email);
        formPage.selectGender(gender);
        formPage.setMobile(mobile);
        formPage.selectHobby(hobby);
        formPage.setCurrentAddress(address);

        formPage.submit();

        assertTrue(formPage.isResultModalDisplayed(),
                "Modal kết quả phải được hiển thị sau khi submit thành công");
        assertEquals("Thanks for submitting the form",
                formPage.getModalTitle(),
                "Tiêu đề modal phải đúng thông báo thành công");

        formPage.closeModal();
    }

    // ========== TC_REG_002: Thiếu toàn bộ field bắt buộc (không dùng CSV) ==========
    @Test
    @Order(2)
    @DisplayName("Submit form thiếu các field bắt buộc")
    void submitFormWithMissingRequiredFields() {
        formPage.navigate();

        // Không nhập gì, bấm Submit luôn
        formPage.submit();

        // Không được hiển thị modal
        assertFalse(formPage.isResultModalDisplayed(),
                "Không được hiển thị modal khi thiếu dữ liệu bắt buộc");

        // HTML5 validation message của FirstName & Mobile phải khác rỗng
        String firstNameMsg = formPage.getFirstNameValidationMessage();
        String mobileMsg = formPage.getMobileValidationMessage();

        assertFalse(firstNameMsg.isEmpty(),
                "First name phải có validation message (required)");
        assertFalse(mobileMsg.isEmpty(),
                "Mobile phải có validation message (required)");
    }

    // ========== TC_REG_003: Email không hợp lệ (không dùng CSV) ==========
    @Test
    @Order(3)
    @DisplayName("Submit form với email không hợp lệ")
    void submitFormWithInvalidEmail_ShouldNotShowModal() {
        formPage.navigate();

        formPage.setFirstName("John");
        formPage.setLastName("Doe");
        formPage.setEmail("abc"); // email không hợp lệ
        formPage.selectGender("Male");
        formPage.setMobile("0123456789");

        formPage.submit();

        // Nếu email type="email", form sẽ bị chặn submit → không có modal
        assertFalse(formPage.isResultModalDisplayed(),
                "Không được hiển thị modal khi email không hợp lệ");
    }

    // ========== TC_REG_004: Thiếu Gender (không dùng CSV) ==========
    @Test
    @Order(4)
    @DisplayName("Submit form thiếu giới tính (Gender)")
    void submitFormWithoutGender_ShouldNotShowModal() {
        formPage.navigate();

        formPage.setFirstName("Anna");
        formPage.setLastName("Nguyen");
        formPage.setEmail("anna.nguyen@example.com");
        // KHÔNG chọn gender
        formPage.setMobile("0987654321");
        formPage.setCurrentAddress("Hanoi");

        formPage.submit();

        assertFalse(formPage.isResultModalDisplayed(),
                "Không được hiển thị modal khi thiếu Gender");
    }

    // ========== TC_REG_005: Chỉ điền field bắt buộc (dùng CSV với scenario = REQUIRED_ONLY) ==========
    @ParameterizedTest
    @Order(5)
    @DisplayName("Submit form chỉ với các field bắt buộc (REQUIRED_ONLY từ 1 file CSV)")
    @CsvFileSource(resources = "/registration_data.csv", numLinesToSkip = 1)
    void submitFormWithOnlyRequiredFields_Csv(String scenario,
                                              String firstName,
                                              String lastName,
                                              String email,
                                              String gender,
                                              String mobile,
                                              String hobby,
                                              String address) {

        // Chỉ chạy test này cho các dòng scenario = REQUIRED_ONLY
        Assumptions.assumeTrue("REQUIRED_ONLY".equalsIgnoreCase(scenario));

        formPage.navigate();

        // Các field bắt buộc: FirstName, LastName, Gender, Mobile
        formPage.setFirstName(firstName);
        formPage.setLastName(lastName);
        formPage.selectGender(gender);
        formPage.setMobile(mobile);

        // Không dùng email/hobby/address
        formPage.submit();

        assertTrue(formPage.isResultModalDisplayed(),
                "Form với đủ field bắt buộc vẫn phải submit thành công");
        assertEquals("Thanks for submitting the form",
                formPage.getModalTitle(),
                "Tiêu đề modal phải đúng thông báo thành công");

        formPage.closeModal();
    }
}
