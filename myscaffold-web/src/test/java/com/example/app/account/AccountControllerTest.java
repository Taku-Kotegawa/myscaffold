package com.example.app.account;

import com.example.domain.model.Account;
import com.example.domain.model.TempFile;
import com.example.domain.repository.fileupload.TempFileRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//以下4行は@SpringJUnitWebConfigで置き換え可能(Spring5機能)
//@ExtendWith(SpringExtension.class)
//@ContextHierarchy({@ContextConfiguration({"classpath:META-INF/spring/applicationContext.xml", "classpath:META-INF/spring/spring-security.xml"}),
//        @ContextConfiguration("classpath:META-INF/spring/spring-mvc.xml")})
//@WebAppConfiguration
@SpringJUnitWebConfig(locations = {"classpath:META-INF/spring/applicationContext.xml", "classpath:META-INF/spring/spring-security.xml", "classpath:META-INF/spring/spring-mvc.xml"})
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AccountControllerTest {

    @Autowired
    WebApplicationContext webApplicationContext;

    MockMvc mockMvc;
    @Autowired
    TempFileRepository tempFileRepository;

    // 入力チェクを通過するフォームデータ
    public AccountCreateForm createAccountCreateForm() {
        AccountCreateForm expectedForm = new AccountCreateForm();
        expectedForm.setUsername("user1");
        expectedForm.setFirstName("firstname1");
        expectedForm.setLastName("lastname1");
        expectedForm.setEmail("user1@domainexample.co.jp");
        expectedForm.setConfirmEmail("user1@domainexample.co.jp");
        expectedForm.setUrl("http://www.stnet.co.jp");
        byte[] content = {0x4a};
        expectedForm.setImage(new MockMultipartFile("image", "test.png", "image/png", content));
        expectedForm.setProfile("profile1\nprofile2");
        return expectedForm;
    }

    @BeforeAll
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .alwaysDo(log()).build();
    }

    private void insertIntoTempfile(TempFile... tempfiles) {
        for (TempFile tempFile : tempfiles) {
            tempFileRepository.insert(tempFile);
        }
    }

    private TempFile createTempFile(String id) {
        return createTempFile(id, "OriginalName.png",
                LocalDateTime.of(2020, 10, 31, 12, 59, 59));
    }

    private TempFile createTempFile(String id, String originalName, LocalDateTime uploadedDate) {
        TempFile tempFile = new TempFile();
        tempFile.setId(id);
        tempFile.setOriginalName(originalName);
        tempFile.setUploadedDate(uploadedDate);
        byte[] body = {0x4a}; // ダミーのバイナリデータ
        tempFile.setBody(body);
        return tempFile;
    }

    @Nested
    @SpringJUnitWebConfig(locations = {"classpath:META-INF/spring/applicationContext.xml", "classpath:META-INF/spring/spring-security.xml", "classpath:META-INF/spring/spring-mvc.xml"})
    @Transactional
    class view {

        @Test
        @DisplayName("ステータスコード、ビュー名、モデルの妥当性")
        @WithUserDetails("demo")
        void test001() throws Exception {
            MvcResult results = mockMvc.perform(get("/accounts"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("account/view"))
                    .andExpect(model().hasNoErrors())
                    .andExpect(model().attributeExists("account"))
                    .andExpect(model().attributeExists("accountCreateForm"))
                    .andReturn();
        }

        @Test
        @DisplayName("accountエンティティの妥当性")
        @WithUserDetails("demo")
        void test002() throws Exception {
            // ---- 実行 ----
            MvcResult results = mockMvc.perform(get("/accounts"))
                    .andReturn();
            Account actualAccount = (Account) results.getModelAndView().getModel().get("account");

            // ---- 検証 ----
            assertThat(actualAccount).isNotNull();
            assertThat(actualAccount.getUsername()).isEqualTo("demo");
        }
    }

    @Nested
    @SpringJUnitWebConfig(locations = {"classpath:META-INF/spring/applicationContext.xml", "classpath:META-INF/spring/spring-security.xml", "classpath:META-INF/spring/spring-mvc.xml"})
    @Transactional
    class showImage {
        // TODO 画像データの準備
    }

    @Nested
    @SpringJUnitWebConfig(locations = {"classpath:META-INF/spring/applicationContext.xml", "classpath:META-INF/spring/spring-security.xml", "classpath:META-INF/spring/spring-mvc.xml"})
    @Transactional
    class createForm {
        @Test
        @DisplayName("ステータスコード、ビュー名、モデルの有無")
        void test001() throws Exception {
            // ---- 準備 ----
            AccountCreateForm expectedForm = createAccountCreateForm();

            // ---- 実行 ----
            MvcResult results = mockMvc.perform(get("/accounts/create?form"))
                    // ---- 検証 ----
                    .andExpect(status().isOk())
                    .andExpect(view().name("account/accountCreateForm"))
                    .andExpect(model().attributeExists("accountCreateForm"))
                    .andReturn();
        }

        @Test
        @DisplayName("フォームオブジェクトの設定値")
        void test002() throws Exception {
            // ---- 準備 ----
            AccountCreateForm expectedForm = new AccountCreateForm(); // 初期値状態

            // ---- 実行 ----
            MvcResult results = mockMvc.perform(get("/accounts/create?form")).andReturn();
            AccountCreateForm actualForm = (AccountCreateForm) results.getModelAndView().getModel().get("accountCreateForm");

            // ---- 検証 ----
            assertThat(actualForm).isEqualTo(expectedForm);
        }
    }

    @Nested
    @SpringJUnitWebConfig(locations = {"classpath:META-INF/spring/applicationContext.xml", "classpath:META-INF/spring/spring-security.xml", "classpath:META-INF/spring/spring-mvc.xml"})
    @Transactional
    class redoCreateForm {
        @Test
        @DisplayName("ステータスコード、ビュー名、モデルの妥当性")
        void test001() throws Exception {
            MvcResult results = mockMvc.perform(post("/accounts/create?redo")
                    .param("username", "user1")
            )
                    .andExpect(status().isOk())
                    .andExpect(view().name("account/accountCreateForm"))
                    .andExpect(model().attributeExists("accountCreateForm"))
                    .andReturn();
        }

        @Test
        @DisplayName("FormBeanの妥当性")
        void test002() throws Exception {
            // ---- 準備 ----
            AccountCreateForm expectedForm = createAccountCreateForm();

            // ---- 実行 ----
            MvcResult results = mockMvc.perform(multipart("/accounts/create?confirm")
                    .file((MockMultipartFile) expectedForm.getImage())
                    .param("username", expectedForm.getUsername())
                    .param("firstName", expectedForm.getFirstName())
                    .param("lastName", expectedForm.getLastName())
                    .param("email", expectedForm.getEmail())
                    .param("confirmEmail", expectedForm.getConfirmEmail())
                    .param("url", expectedForm.getUrl())
                    .param("profile", expectedForm.getProfile())
            ).andReturn();
            AccountCreateForm actualForm = (AccountCreateForm) results.getModelAndView().getModel().get("accountCreateForm");

            // ---- 検証 ----
            assertThat(actualForm)
                    .isNotNull()
                    .isEqualToIgnoringGivenFields(expectedForm, "imageId");
        }
    }

    @Nested
    @SpringJUnitWebConfig(locations = {"classpath:META-INF/spring/applicationContext.xml", "classpath:META-INF/spring/spring-security.xml", "classpath:META-INF/spring/spring-mvc.xml"})
    @Transactional
    class createConfirm {

        @Test
        @DisplayName("入力エラー無し時のステータスコード、ビュー名、モデル有無、エラー有無")
        void test001() throws Exception {
            // ---- 準備 ----
            AccountCreateForm expectedForm = createAccountCreateForm();

            // ---- 実行 -----
            MvcResult results = mockMvc.perform(multipart("/accounts/create?confirm")
                    .file((MockMultipartFile) expectedForm.getImage())
                    .param("username", expectedForm.getUsername())
                    .param("firstName", expectedForm.getFirstName())
                    .param("lastName", expectedForm.getLastName())
                    .param("email", expectedForm.getEmail())
                    .param("confirmEmail", expectedForm.getConfirmEmail())
                    .param("url", expectedForm.getUrl())
                    .param("profile", expectedForm.getProfile())
            )
                    // ---- 検証 ----
                    .andExpect(status().isOk())
                    .andExpect(view().name("account/accountConfirm"))
                    .andExpect(model().attributeExists("accountCreateForm"))
                    .andExpect(model().hasNoErrors())
                    .andReturn();
        }

        // TODO 全項目の入力チェックの検証が必要


    }

    @Nested
    @SpringJUnitWebConfig(locations = {"classpath:META-INF/spring/applicationContext.xml", "classpath:META-INF/spring/spring-security.xml", "classpath:META-INF/spring/spring-mvc.xml"})
    @Transactional
    class create {

        @Test
        @DisplayName("ステータスコード、リダイレクト先、フラッシュモデル有無、エラー有無")
        void test001() throws Exception {
            // ---- 準備 ----
            insertIntoTempfile(createTempFile("00000000-0000-0000-0000-000000000001"));
            AccountCreateForm expectedForm = createAccountCreateForm();
            expectedForm.setImage(null);
            expectedForm.setImageId("00000000-0000-0000-0000-000000000001");

            // ---- 実行 -----
            MvcResult results = mockMvc.perform(post("/accounts/create")
                    .param("username", expectedForm.getUsername())
                    .param("firstName", expectedForm.getFirstName())
                    .param("lastName", expectedForm.getLastName())
                    .param("email", expectedForm.getEmail())
                    .param("confirmEmail", expectedForm.getConfirmEmail())
                    .param("url", expectedForm.getUrl())
                    .param("profile", expectedForm.getProfile())
                    .param("imageId", expectedForm.getImageId())
            )
                    // ---- 検証 ----
                    .andExpect(model().hasNoErrors())
                    .andExpect(status().isFound()) //302
                    .andExpect(redirectedUrl("/accounts/create?complete"))
                    .andExpect(flash().attributeCount(3))
                    .andExpect(flash().attributeExists("firstName", "lastName", "password"))
                    .andExpect(flash().attribute("firstName", "firstname1"))
                    .andExpect(flash().attribute("lastName", "lastname1"))
                    .andReturn();
        }

        @Test
        @DisplayName("入力チェックが発動するか？ @Validatedの設定忘れ確認かつ登録処理はimageフィールドが不要")
        void test002() throws Exception {
            // ---- 準備 ----
            insertIntoTempfile(createTempFile("00000000-0000-0000-0000-000000000001"));
            AccountCreateForm expectedForm = createAccountCreateForm();
            expectedForm.setImage(null);
            expectedForm.setImageId("00000000-0000-0000-0000-000000000001");

            // ---- 実行 -----
            MvcResult results = mockMvc.perform(post("/accounts/create")
                    .param("username", "123")
                    .param("firstName", expectedForm.getFirstName())
                    .param("lastName", expectedForm.getLastName())
                    .param("email", expectedForm.getEmail())
                    .param("confirmEmail", expectedForm.getConfirmEmail())
                    .param("url", expectedForm.getUrl())
                    .param("profile", expectedForm.getProfile())
                    .param("imageId", expectedForm.getImageId())
            )
                    // ---- 検証 ----
                    .andExpect(model().hasErrors())
                    .andExpect(model().attributeHasFieldErrorCode("accountCreateForm", "username", "Size"))
                    .andReturn();
        }
    }

    @Nested
    @SpringJUnitWebConfig(locations = {"classpath:META-INF/spring/applicationContext.xml", "classpath:META-INF/spring/spring-security.xml", "classpath:META-INF/spring/spring-mvc.xml"})
    @Transactional
    class createComplete {

        @Test
        @DisplayName("ステータスコード、ビュー名、モデルの有無")
        void test001() throws Exception {

            // ---- 準備 ----
            insertIntoTempfile(createTempFile("00000000-0000-0000-0000-000000000001"));
            AccountCreateForm expectedForm = createAccountCreateForm();
            expectedForm.setImage(null);
            expectedForm.setImageId("00000000-0000-0000-0000-000000000001");

            MvcResult prepareResults = mockMvc.perform(post("/accounts/create")
                    .param("username", expectedForm.getUsername())
                    .param("firstName", expectedForm.getFirstName())
                    .param("lastName", expectedForm.getLastName())
                    .param("email", expectedForm.getEmail())
                    .param("confirmEmail", expectedForm.getConfirmEmail())
                    .param("url", expectedForm.getUrl())
                    .param("profile", expectedForm.getProfile())
                    .param("imageId", expectedForm.getImageId())
            ).andReturn();

            MockHttpSession mockSession = (MockHttpSession) prepareResults.getRequest().getSession(); // セッションモックの引き渡しが必要
            String generatedPassword = (String) prepareResults.getFlashMap().get("password");

            // ---- 実行 ----
            MvcResult results = mockMvc.perform(get("/accounts/create?complete")
                    .session(mockSession)
            )
                    // ---- 検証 ----
                    .andExpect(status().isOk())
                    .andExpect(view().name("account/createComplete"))
                    .andExpect(model().attributeExists("firstName"))
                    .andExpect(model().attribute("firstName", "firstname1"))
                    .andExpect(model().attributeExists("lastName"))
                    .andExpect(model().attribute("lastName", "lastname1"))
                    .andExpect(model().attributeExists("password"))
                    .andExpect(model().attribute("password", generatedPassword)) // パスワードはランダムな値で固定値での評価が難しい
                    .andReturn();
        }
    }
}