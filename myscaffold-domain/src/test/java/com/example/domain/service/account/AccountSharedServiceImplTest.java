package com.example.domain.service.account;

import com.example.domain.model.*;
import com.example.domain.repository.account.AccountImageRepository;
import com.example.domain.repository.account.AccountRepository;
import com.example.domain.repository.account.RoleRepository;
import com.example.domain.repository.authenticationevent.FailedAuthenticationRepository;
import com.example.domain.repository.authenticationevent.SuccessfulAuthenticationRepository;
import com.example.domain.repository.fileupload.TempFileRepository;
import com.example.domain.repository.passwordhistory.PasswordHistoryRepository;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.exception.ResourceNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(locations = {"classpath:test-context.xml"})
@SpringJUnitConfig(locations = {"classpath:test-context.xml"})
@Transactional
class AccountSharedServiceImplTest {

    @Autowired
    AccountSharedService target;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    AccountImageRepository accountImageRepository;

    @Autowired
    TempFileRepository tempFileRepository;

    @Autowired
    SuccessfulAuthenticationRepository successfulAuthenticationRepository;

    @Autowired
    FailedAuthenticationRepository failedAuthenticationRepository;

    @Autowired
    PasswordHistoryRepository passwordHistoryRepository;

    private void insertIntoAccountRoles(AccountRoles... accountRolesList) {
        for (AccountRoles accountRoles : accountRolesList) {
            accountRepository.insert(accountRoles);
            for (Role role : accountRoles.getRoles()) {
                roleRepository.insert(role);
            }
            accountImageRepository.insert(createAccountImage(accountRoles.getUsername()));
        }
    }

    private AccountRoles createAccountRoles(String username) {
        AccountRoles accountRoles = new AccountRoles();
        accountRoles.setRoleLables(Lists.newArrayList("USER"));
        accountRoles.setUsername(username);
        accountRoles.setPassword("Password:" + username);
        accountRoles.setFirstName("FirstName:" + username);
        accountRoles.setLastName("LastName:" + username);
        accountRoles.setEmail("Email:" + username);
        accountRoles.setUrl("Url:" + username);
        accountRoles.setProfile("Profile:" + username);
        return accountRoles;
    }

    private AccountImage createAccountImage(String username) {
        AccountImage accountImage = new AccountImage();
        accountImage.setUsername(username);
        accountImage.setExtension("png");
        byte[] body = {0x4a};
        accountImage.setBody(body);
        return accountImage;
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

    private void insertIntoSuccessfulAuthentication(SuccessfulAuthentication... successfulAuthentications) {
        for (SuccessfulAuthentication successfulAuthentication : successfulAuthentications) {
            successfulAuthenticationRepository.insert(successfulAuthentication);
        }
    }

    private SuccessfulAuthentication createSucessfulAuthentication(String username, LocalDateTime loginDate) {
        SuccessfulAuthentication successfulAuthentications = new SuccessfulAuthentication();
        successfulAuthentications.setUsername(username);
        successfulAuthentications.setAuthenticationTimestamp(loginDate);
        return successfulAuthentications;
    }

    private void insertIntoFailedAuthentication(FailedAuthentication... failedAuthentications) {
        for (FailedAuthentication failedAuthentication : failedAuthentications) {
            failedAuthenticationRepository.insert(failedAuthentication);
        }
    }

    private FailedAuthentication createFailedAuthentication(String username, LocalDateTime loginDate) {
        FailedAuthentication failedAuthentications = new FailedAuthentication();
        failedAuthentications.setUsername(username);
        failedAuthentications.setAuthenticationTimestamp(loginDate);
        return failedAuthentications;
    }

    private void insertIntoPasswordHistory(PasswordHistory... passwordHistories) {
        for (PasswordHistory passwordHistory : passwordHistories) {
            passwordHistoryRepository.insert(passwordHistory);
        }
    }

    private PasswordHistory createPasswordHistory(String username, LocalDateTime UseFrom) {
        PasswordHistory passwordHistory = new PasswordHistory();
        passwordHistory.setUsername(username);
        passwordHistory.setUseFrom(UseFrom);
        passwordHistory.setPassword("PasswordOf" + username);
        return passwordHistory;
    }


    @Nested
    @SpringJUnitConfig(locations = {"classpath:test-context.xml"})
    @Transactional
    class create {

        @Test
        @DisplayName("[正常系]新規登録したら所定のテーブルにデータが格納される。")
        void test001() {
            // ---- 準備 ----
            insertIntoTempfile(createTempFile("00000000-0000-0000-0000-000000000001"));

            // ---- 実行 ----
            String acutualRawPassword = target.create(createAccountRoles("user1"), "00000000-0000-0000-0000-000000000001");

            // ---- 検証 ----
            assertThat(accountRepository.selectByPrimaryKey("user1")).isNotNull();

            RoleExample example = new RoleExample();
            example.or().andUsernameEqualTo("user1");
            assertThat(roleRepository.selectByExample(example)).extracting(Role::getRole).containsOnly("USER");
        }

        @Test
        @DisplayName("[異常系]指定したユーザが既に存在する場合は例外")
        void test002() {
            // ---- 準備 ----
            insertIntoAccountRoles(createAccountRoles("user1"));
            insertIntoTempfile(createTempFile("00000000-0000-0000-0000-000000000001"));

            // ---- 実行 ----
            assertThatThrownBy(() -> {
                target.create(createAccountRoles("user1"), "00000000-0000-0000-0000-000000000001");
            })
                    // ---- 検証 ----
                    .isInstanceOf(BusinessException.class);
        }
    }

    @Nested
    @ContextConfiguration(locations = {"classpath:test-context.xml"})
    @Transactional
    class findOne {

        @Test
        @DisplayName("[正常系]指定されたキーに一致するユーザが存在すればデータを取得する。戻値はアカウントロールエンティティ")
        void test001() {
            // ---- 準備 ----
            insertIntoAccountRoles(createAccountRoles("user1"));

            // ---- 実行 ----
            AccountRoles actualAccountRoles = target.findOne("user1");

            // ---- 検証 ----
            assertThat(actualAccountRoles)
                    .isNotNull()
                    .isEqualTo(createAccountRoles("user1"))
                    .isEqualToComparingOnlyGivenFields(createAccountRoles("user1"), "username"); // フィールド指定の比較もできる
        }

        @Test
        @DisplayName("[異常系]指定されたキーに一致するユーザが存在しなければ例外")
        void test002() {
            // ---- 準備 ----

            // ---- 実行 ----
            assertThatThrownBy(() -> {
                target.findOne("user1");
            })
                    // ---- 検証 ----
                    .isInstanceOf(ResourceNotFoundException.class);
        }
    }

    @Nested
    @ContextConfiguration(locations = {"classpath:test-context.xml"})
    @Transactional
    class getLastLoginDate {

        @Test
        @DisplayName("[正常系]指定されたユーザの最終ログイン日時を取得")
        void test001() {
            // ---- 準備 ----
            insertIntoAccountRoles(createAccountRoles("user1"));
            insertIntoSuccessfulAuthentication(createSucessfulAuthentication("user1", LocalDateTime.of(2020, 10, 31, 12, 59, 59)));
            insertIntoSuccessfulAuthentication(createSucessfulAuthentication("user1", LocalDateTime.of(2020, 10, 31, 12, 59, 58)));

            // ---- 実行 ----
            LocalDateTime actualLoginDate = target.getLastLoginDate("user1");

            // ---- 検証 ----
            assertThat(actualLoginDate).isEqualTo(LocalDateTime.of(2020, 10, 31, 12, 59, 59));
        }

        @Test
        @DisplayName("[異常系]指定されたユーザの認証成功履歴がなければNullを返す")
        void test002() {
            // ---- 準備 ----
            insertIntoAccountRoles(createAccountRoles("user1"));

            // ---- 実行 ----
            LocalDateTime actualLoginDate = target.getLastLoginDate("user1");

            // ---- 検証 ----
            assertThat(actualLoginDate).isNull();
        }
    }

    @Nested
    @ContextConfiguration(locations = {"classpath:test-context.xml"})
    @Transactional
    class exists {

        @Test
        @DisplayName("[正常系]指定されたユーザが存在すればTrue")
        void test001() {
            // ---- 準備 ----
            insertIntoAccountRoles(createAccountRoles("user1"));

            // ---- 実行 ----
            Boolean actualExists = target.exists("user1");

            // ---- 検証 ----
            assertThat(actualExists).isTrue();
        }

        @Test
        @DisplayName("[正常系]指定されたユーザが存在しなければFalse")
        void test002() {
            // ---- 準備 ----

            // ---- 実行 ----
            Boolean actualExists = target.exists("user1");

            // ---- 検証 ----
            assertThat(actualExists).isFalse();
        }
    }

    /**
     * 注意: isLocked()のテスト結果は環境設定に依存します。
     * # ロックアウトの継続時間(秒)
     * security.lockingDurationSeconds=30
     * # ロックアウトするまでの認証失敗回数
     * security.lockingThreshold=3
     */
    @Nested
    @ContextConfiguration(locations = {"classpath:test-context.xml"})
    @Transactional
    class isLocked {

        @Test
        @DisplayName("[正常系]指定されたユーザが認証失敗回数が3以上でロック(True)")
        void test001() {

            // ---- 準備 ----
            insertIntoAccountRoles(createAccountRoles("user1"));
            insertIntoFailedAuthentication(createFailedAuthentication("user1", LocalDateTime.now())); // 1
            insertIntoFailedAuthentication(createFailedAuthentication("user1", LocalDateTime.now())); // 2
            insertIntoFailedAuthentication(createFailedAuthentication("user1", LocalDateTime.now())); // 3

            // ---- 実行 ----
            Boolean actualLocked = target.isLocked("user1");

            // ---- 検証 ----
            assertThat(actualLocked).isTrue();
        }

        @Test
        @DisplayName("[正常系]指定されたユーザが認証失敗回数が3未満はでロックしない(False)")
        void test002() {

            // ---- 準備 ----
            insertIntoAccountRoles(createAccountRoles("user1"));
            insertIntoFailedAuthentication(createFailedAuthentication("user1", LocalDateTime.now())); // 1
            insertIntoFailedAuthentication(createFailedAuthentication("user1", LocalDateTime.now())); // 2

            // ---- 実行 ----
            Boolean actualLocked = target.isLocked("user1");

            // ---- 検証 ----
            assertThat(actualLocked).isFalse();
        }

        @Test
        @DisplayName("[正常系]古い(30秒経過)した認証失敗は、回数に含まれないので、ロックしない(False)")
        void test003() {

            // ---- 準備 ----
            insertIntoAccountRoles(createAccountRoles("user1"));
            insertIntoFailedAuthentication(createFailedAuthentication("user1", LocalDateTime.now())); // 1
            insertIntoFailedAuthentication(createFailedAuthentication("user1", LocalDateTime.now().minusSeconds(29))); // 2(古くない)
            insertIntoFailedAuthentication(createFailedAuthentication("user1", LocalDateTime.now().minusSeconds(30))); // 3(古い)

            // ---- 実行 ----
            Boolean actualLocked = target.isLocked("user1");

            // ---- 検証 ----
            assertThat(actualLocked).isFalse();
        }

        @Test
        @DisplayName("[正常系]30秒経過していない認証失敗は、回数に含まれる")
        void test004() {

            // ---- 準備 ----
            insertIntoAccountRoles(createAccountRoles("user1"));
            insertIntoFailedAuthentication(createFailedAuthentication("user1", LocalDateTime.now())); // 1
            insertIntoFailedAuthentication(createFailedAuthentication("user1", LocalDateTime.now())); // 2
            insertIntoFailedAuthentication(createFailedAuthentication("user1", LocalDateTime.now().minusSeconds(29))); // 3(古くない)
            insertIntoFailedAuthentication(createFailedAuthentication("user1", LocalDateTime.now().minusSeconds(30))); // 4(古い)

            // ---- 実行 ----
            Boolean actualLocked = target.isLocked("user1");

            // ---- 検証 ----
            assertThat(actualLocked).isTrue();
        }

    }

    /**
     * 注意: このテストは環境変数に依存します。
     */
    @Nested
    @ContextConfiguration(locations = {"classpath:test-context.xml"})
    @Transactional
    class isInitialPassword {

        @Test
        @DisplayName("[正常系]パスワードを変更していない場合はTrue")
        void test001() {

            // ---- 準備 ----
            insertIntoAccountRoles(createAccountRoles("user1"));

            // ---- 実行 ----
            Boolean actualInitialPassword = target.isInitialPassword("user1");

            // ---- 検証 ----
            assertThat(actualInitialPassword).isTrue();
        }

        @Test
        @DisplayName("[正常系]パスワードを変更したらFalse")
        void test002() {

            // ---- 準備 ----
            insertIntoAccountRoles(createAccountRoles("user2"));
            insertIntoPasswordHistory(createPasswordHistory("user2", LocalDateTime.of(1999, 12, 31, 12, 59, 59, 999)));

            // ---- 実行 ----
            Boolean actualInitialPassword = target.isInitialPassword("user2");

            // ---- 検証 ----
            assertThat(actualInitialPassword).isFalse();
        }
    }

    /**
     * 注意: テスト結果が環境変数に依存します。
     * # パスワードの有効期間(秒)
     * security.passwordLifeTimeSeconds=10000
     */
    @Nested
    @ContextConfiguration(locations = {"classpath:test-context.xml"})
    @Transactional
    class isCurrentPasswordExpired {

        @Test
        @DisplayName("[正常系]パスワードを変更していない場合はTrue")
        void test001() {

            // ---- 準備 ----
            insertIntoAccountRoles(createAccountRoles("user1"));

            // ---- 実行 ----
            Boolean actualCurrentPasswordExpired = target.isCurrentPasswordExpired("user1");

            // ---- 検証 ----
            assertThat(actualCurrentPasswordExpired).isTrue();
        }

        @Test
        @DisplayName("[正常系]パスワードを変更し、有効期限までの間はFALSE")
        void test002() {

            // ---- 準備 ----
            insertIntoAccountRoles(createAccountRoles("user2"));
            insertIntoPasswordHistory(createPasswordHistory("user2", LocalDateTime.now().minusSeconds(9999L)));

            // ---- 実行 ----
            Boolean actualCurrentPasswordExpired = target.isCurrentPasswordExpired("user2");

            // ---- 検証 ----
            assertThat(actualCurrentPasswordExpired).isFalse();
        }

        @Test
        @DisplayName("[正常系]パスワードを変更し、有効期限を過ぎたらTRUE")
        void test003() {

            // ---- 準備 ----
            insertIntoAccountRoles(createAccountRoles("user3"));
            insertIntoPasswordHistory(createPasswordHistory("user3", LocalDateTime.now().minusSeconds(100001L)));

            // ---- 実行 ----
            Boolean actualCurrentPasswordExpired = target.isCurrentPasswordExpired("user3");

            // ---- 検証 ----
            assertThat(actualCurrentPasswordExpired).isTrue();
        }


    }

    @Nested
    @ContextConfiguration(locations = {"classpath:test-context.xml"})
    @Transactional
    class updatePassword {

        @Test
        @DisplayName("[正常系]パスワードを変更できること")
        void test001() {
            // ---- 準備 ----
            insertIntoAccountRoles(createAccountRoles("user1"));

            PasswordHistoryExample example = new PasswordHistoryExample();
            example.or().andUsernameEqualTo("user1");
            List<PasswordHistory> beforePasswordHistories = passwordHistoryRepository.selectByExample(example);

            // ---- 実行 ----
            Boolean actualupdatePassword = target.updatePassword("user1", "NEWPASSWORD");

            // ---- 検証 ----
            assertThat(actualupdatePassword).isTrue();

            List<PasswordHistory> actualPasswordHistories = passwordHistoryRepository.selectByExample(example);
            assertThat(actualPasswordHistories).hasSize(beforePasswordHistories.size() + 1);
        }
    }

    @Nested
    @ContextConfiguration(locations = {"classpath:test-context.xml"})
    @Transactional
    class clearPasswordValidationCache {

        @Test
        @DisplayName("[正常系]???")
        void test001() {
            // ---- 準備 ----

            // ---- 実行 ----
            target.clearPasswordValidationCache("user1");
        }

    }

    @Nested
    @ContextConfiguration(locations = {"classpath:test-context.xml"})
    @Transactional
    class getImage {

        @Test
        @DisplayName("[正常系]アカウントが情報が登録されていたらそれを取得できる")
        void test001() {
            // ---- 準備 ----
            insertIntoAccountRoles(createAccountRoles("user1"));
            AccountImage expectedAccountImage = createAccountImage("user1");

            // ---- 実行 ----
            AccountImage actualAccountImage = target.getImage("user1");

            // ---- 検証 ----
            assertThat(actualAccountImage).isEqualTo(expectedAccountImage);
        }

        @Test
        @DisplayName("[正常系]アカウントが情報が登録されていなかったらnull")
        void test002() {
            // ---- 準備 ----

            // ---- 実行 ----
            AccountImage actualAccountImage = target.getImage("user2");

            // ---- 検証 ----
            assertThat(actualAccountImage).isNull();
        }
    }
}