package com.example.domain.repository.account;

import com.example.domain.model.*;
import com.example.domain.repository.authenticationevent.FailedAuthenticationRepository;
import com.example.domain.repository.authenticationevent.SuccessfulAuthenticationRepository;
import com.example.domain.repository.passwordhistory.PasswordHistoryRepository;
import com.example.domain.repository.passwordreissue.PasswordReissueInfoRepository;
import org.apache.ibatis.session.RowBounds;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import static com.example.domain.common.StringUtils.rightPad;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * DDL: myscaffold-initdb/src/main/sqls/postgres/00110_create_al_tables.sql
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:test-context.xml"})
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AccountRepositoryTest {

    @Autowired
    AccountRepository target;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    SuccessfulAuthenticationRepository successfulAuthenticationRepository;

    @Autowired
    FailedAuthenticationRepository failedAuthenticationRepository;

    @Autowired
    PasswordHistoryRepository passwordHistoryRepository;

    @Autowired
    PasswordReissueInfoRepository passwordReissueInfoRepository;

    @Autowired
    AccountImageRepository accountImageRepository;


    @AfterAll
    static void tearDown() {
    }

    @BeforeAll
    void setUp() {
    }

    @BeforeEach
    void setUpEach() {
    }

    @AfterEach
    void tearDownEach() {
    }

    /**
     * accountテーブルにデータを挿入する。 (Repository.insert使用)
     *
     * @param accounts Account(カンマ区切りで複数指定可)
     */
    private void insertIntoDatabase(Account... accounts) {
        for (Account account : accounts) {
            target.insert(account);
        }
    }

    /**
     * テストエンティティの作成(各フィールドに最大桁数のダミーデータをセット)
     *
     * @param id テストデータを一意に特定する番号
     * @return Accountエンティティ
     */
    private Account createAccount(String id) {
        Account account = new Account();
        account.setUsername(rightPad(id, 88, "0"));
        account.setPassword(rightPad("Password:" + id, 88, "0"));
        account.setFirstName(rightPad("FirstName:" + id, 128, "0"));
        account.setLastName(rightPad("LastName:" + id, 128, "0"));
        account.setEmail(rightPad("Email:" + id, 128, "0"));
        account.setUrl(rightPad("Url:" + id, 255, "0"));
        account.setProfile(rightPad("Profile:" + id, 100000, "0"));
        return account;
    }

    /**
     * Accountテーブル全件削除(依存するテーブルも含む)
     */
    private void deleteAccountAll() {
        roleRepository.deleteByExample(new RoleExample());
        accountImageRepository.deleteByExample(new AccountImageExample());
        successfulAuthenticationRepository.deleteByExample(new SuccessfulAuthenticationExample());
        failedAuthenticationRepository.deleteByExample(new FailedAuthenticationExample());
        passwordHistoryRepository.deleteByExample(new PasswordHistoryExample());
        passwordReissueInfoRepository.deleteByExample(new PasswordReissueInfoExample());
        target.deleteByExample(new AccountExample());
    }

    @Nested
    @ContextConfiguration(locations = {"classpath:test-context.xml"})
    @Transactional
    class countByExample {

        @Test
        @DisplayName("[正常系]テーブルに登録された件数を返す(0件)")
        void test001() {
            // 準備
            deleteAccountAll();

            // 実行
            long actualCount = target.countByExample(new AccountExample());

            // 検証
            assertThat(actualCount).isEqualTo(0L);
        }

        @Test
        @DisplayName("[正常系]テーブルに登録された件数を返す(1件)")
        void test002() {
            // 準備
            deleteAccountAll();
            insertIntoDatabase(
                    createAccount("1") // OK
            );

            // 実行
            long actualCount = target.countByExample(new AccountExample());

            // 検証
            assertThat(actualCount).isEqualTo(1L);
        }

        @Test
        @DisplayName("[正常系]テーブルに登録された件数を返す(2件)")
        void test003() {
            // 準備
            deleteAccountAll();
            insertIntoDatabase(
                    createAccount("1"), // OK
                    createAccount("2")  // OK
            );

            // 実行
            long actualCount = target.countByExample(new AccountExample());

            // 検証
            assertThat(actualCount).isEqualTo(2L);
        }

        @Test
        @DisplayName("[正常系]テーブルに登録されたデータから検索条件に合致する件数を返す")
        void test004() {
            // 準備
            deleteAccountAll();
            insertIntoDatabase(
                    createAccount("1"), // NG
                    createAccount("2")  // OK
            );

            // 実行
            AccountExample example = new AccountExample();
            example.or().andPasswordLike("Password:2%");
            long actualCount = target.countByExample(example);

            // 検証
            assertThat(actualCount).isEqualTo(1L);
        }
    }

    @Nested
    @ContextConfiguration(locations = {"classpath:test-context.xml"})
    @Transactional
    class deleteByPrimaryKey {

        @Test
        @DisplayName("[正常系]指定した主キーでデータを削除する、戻り値は削除件数。")
        void test001() {
            // 準備
            deleteAccountAll();
            insertIntoDatabase(
                    createAccount("10"), // NOT
                    createAccount("11"), // DELETE
                    createAccount("20")  // NOT
            );

            // 実行
            int actualDeleteCount = target.deleteByPrimaryKey(rightPad("11", 88, "0"));

            // 検証
            assertThat(actualDeleteCount).isEqualTo(1L); // 削除件数は1件

            AccountExample example = new AccountExample();
            example.or().andUsernameEqualTo(rightPad("11", 88, "0"));
            long actualExistCount = target.countByExample(example);
            assertThat(actualExistCount).isEqualTo(0L); // 残っていない

            long actualExistCount2 = target.countByExample(new AccountExample());
            assertThat(actualExistCount2).isEqualTo(2L); // 2件残る
        }
    }

    @Nested
    @ContextConfiguration(locations = {"classpath:test-context.xml"})
    @Transactional
    class deleteByExample {

        @Test
        @DisplayName("[正常系]条件に合致したデータを削除する、戻り値は削除件数。(1件)")
        void test001() {
            // 準備
            deleteAccountAll();
            insertIntoDatabase(
                    createAccount("1"), // NOT
                    createAccount("2"), // DELETE
                    createAccount("3")  // NOT
            );

            // 実行
            AccountExample example = new AccountExample();
            example.or().andPasswordLike("Password:2%");
            int actualDeleteCount = target.deleteByExample(example);

            // 検証
            assertThat(actualDeleteCount).isEqualTo(1L); // 削除件数は1件

            long actualCount = target.countByExample(example);
            assertThat(actualCount).isEqualTo(0L); // テーブルに残っていない

            long actualExistCount = target.countByExample(new AccountExample());
            assertThat(actualExistCount).isEqualTo(2L); // 2件残る

        }

        @Test
        @DisplayName("[正常系]条件に合致したデータを削除する、戻り値は削除件数。(2件)")
        void test002() {
            // 準備
            deleteAccountAll();
            insertIntoDatabase(
                    createAccount("10"), // OK
                    createAccount("11"), // OK
                    createAccount("20")  // NG
            );

            // 実行
            AccountExample example = new AccountExample();
            example.or().andPasswordLike("Password:1%");
            int actualDeleteCount = target.deleteByExample(example);

            // 検証
            assertThat(actualDeleteCount).isEqualTo(2L); // 削除件数は2件

            long actualCount = target.countByExample(example);
            assertThat(actualCount).isEqualTo(0L); // テーブルに残っていない

            long actualExistCount = target.countByExample(new AccountExample());
            assertThat(actualExistCount).isEqualTo(1L); // 1件残る
        }
    }

    @Nested
    @ContextConfiguration(locations = {"classpath:test-context.xml"})
    @Transactional
    class insert {

        @Test
        @DisplayName("[正常系]データが登録され、全項目に値がセットされる。戻り値は登録件数。")
        void test001() {
            // 準備
            deleteAccountAll();

            // 実行
            Account expectedAccount = createAccount("1");
            int actualCount = target.insert(expectedAccount);

            // 検証
            assertThat(actualCount).isEqualTo(1L); // 登録件数は1件

            Account actualAccount = target.selectByPrimaryKey(expectedAccount.getUsername());
            assertThat(actualAccount).isEqualTo(expectedAccount); // 正しく登録されている
        }

        @Test
        @DisplayName("[異常系]一意制約違反でSQL例外がスローされる。")
        void test101() {
            // 準備
            deleteAccountAll();
            insertIntoDatabase(
                    createAccount("1")
            );

            // 実行・検証
            assertThatThrownBy(() -> {
                target.insert(createAccount("1"));
            }).isInstanceOf(DuplicateKeyException.class)
                    .hasMessageContaining("(username)=(1");
        }
    }

    @Nested
    @ContextConfiguration(locations = {"classpath:test-context.xml"})
    @Transactional
    class insertSelective {

        @Test
        @DisplayName("[正常系]データが登録され、全項目に値がセットされる。戻り値は登録件数。(DBの初期値の検証なし)")
        void test001() {
            // 準備
            deleteAccountAll();

            // 実行
            Account expectedAccount = createAccount("1");
            int actualCount = target.insertSelective(expectedAccount);

            // 検証
            assertThat(actualCount).isEqualTo(1L);

            Account actualAccount = target.selectByPrimaryKey(expectedAccount.getUsername());
            assertThat(actualAccount).isEqualTo(expectedAccount);
        }

        @Test
        @DisplayName("[異常系]一意制約違反でSQL例外がスローされる。")
        void test101() {
            // 準備
            deleteAccountAll();
            insertIntoDatabase(
                    createAccount("1")
            );

            // 実行・検証
            assertThatThrownBy(() -> {
                target.insert(createAccount("1"));
            }).isInstanceOf(DuplicateKeyException.class)
                    .hasMessageContaining("(username)=(1");
        }
    }

    @Nested
    @ContextConfiguration(locations = {"classpath:test-context.xml"})
    @Transactional
    class selectByPrimaryKey {
        @Test
        @DisplayName("[正常系]主キーを指定して抽出できること")
        void test001() {
            // 準備
            deleteAccountAll();
            insertIntoDatabase(
                    createAccount("10"), // NG
                    createAccount("11"), // OK
                    createAccount("23")  // NG
            );

            // 実行
            Account actualAccount = target.selectByPrimaryKey(rightPad("11", 88, "0"));

            // 検証
            assertThat(actualAccount).isEqualTo(createAccount("11"));
        }

        @Test
        @DisplayName("[異常系] 指定したキーのデータが未登録の場合はnull")
        void test101() {
            // 準備
            deleteAccountAll();
            insertIntoDatabase(createAccount("1"));

            // 実行
            Account actualAccount = target.selectByPrimaryKey("NotExist");

            // 検証
            assertThat(actualAccount).isNull();
        }
    }

    @Nested
    @ContextConfiguration(locations = {"classpath:test-context.xml"})
    @Transactional
    class selectByExample {

        @Test
        @DisplayName("[正常系]検索条件を指定して抽出できること")
        void test001() {
            // 準備
            deleteAccountAll();
            insertIntoDatabase(
                    createAccount("10"), // NG
                    createAccount("11"), // OK
                    createAccount("12"), // OK
                    createAccount("23")  // NG
            );

            // 実行
            AccountExample example = new AccountExample();
            example.or().andPasswordBetween("Password:11", "Password:20");
            example.setOrderByClause("username");
            List<Account> actualAccounts = target.selectByExample(example);

            // 検証
            assertThat(actualAccounts)
                    .hasSize(2)
                    .containsOnly(
                            createAccount("11"),
                            createAccount("12")
                    );
        }
    }

    @Nested
    @ContextConfiguration(locations = {"classpath:test-context.xml"})
    @Transactional
    class selectByExampleWithRowbounds {

        @Test
        @DisplayName("[正常系]件数と検索条件を指定して抽出できること")
        void test001() {
            // 準備
            deleteAccountAll();
            insertIntoDatabase(
                    createAccount("10"), // NG
                    createAccount("11"), // NG
                    createAccount("12"), // OK
                    createAccount("13"), // OK
                    createAccount("14"), // OK
                    createAccount("15"), // NG
                    createAccount("23")  // NG
            );

            // 実行
            RowBounds rowBounds = new RowBounds(2, 3);
            AccountExample example = new AccountExample();
            example.or().andPasswordBetween("Password:10", "Password:20");
            example.setOrderByClause("username");
            List<Account> actualAccounts = target.selectByExampleWithRowbounds(example, rowBounds);

            // 検証
            assertThat(actualAccounts)
                    .hasSize(3)
                    .containsOnly(
                            createAccount("12"),
                            createAccount("13"),
                            createAccount("14")
                    );
        }
    }

    @Nested
    @ContextConfiguration(locations = {"classpath:test-context.xml"})
    @Transactional
    class updateByPrimaryKey {
        @Test
        @DisplayName("[正常系]主キーを指定して更新できること、戻値は更新件数")
        void test001() {
            // 準備
            deleteAccountAll();
            insertIntoDatabase(
                    createAccount("10")
            );

            // 実行
            Account expectedAccount = createAccount("11");
            expectedAccount.setUsername(rightPad("10", 88, "0"));
            int actualCount = target.updateByPrimaryKey(expectedAccount);

            // 検証
            assertThat(actualCount).isEqualTo(1);

            Account actualAccount = target.selectByPrimaryKey(expectedAccount.getUsername());
            assertThat(actualAccount).isEqualTo(expectedAccount);
        }

        @Test
        @DisplayName("[正常系]指定した主キーのデータがなければ0が返る。")
        void test002() {
            // 準備
            deleteAccountAll();
            insertIntoDatabase(
                    createAccount("10")
            );

            // 実行
            Account expectedAccount = createAccount("11");
            int actualCount = target.updateByPrimaryKey(expectedAccount);

            // 検証
            assertThat(actualCount).isEqualTo(0);
        }
    }

    @Nested
    @ContextConfiguration(locations = {"classpath:test-context.xml"})
    @Transactional
    class updateByPrimaryKeySelective {
        @Test
        @DisplayName("[正常系]主キーを指定して更新できること、戻値は更新件数")
        void test001() {
            // 準備
            deleteAccountAll();
            insertIntoDatabase(
                    createAccount("10")
            );

            // 実行
            Account expectedAccount = createAccount("11");
            expectedAccount.setUsername(rightPad("10", 88, "0"));
            int actualCount = target.updateByPrimaryKeySelective(expectedAccount);

            // 検証
            assertThat(actualCount).isEqualTo(1);

            Account actualAccount = target.selectByPrimaryKey(expectedAccount.getUsername());
            assertThat(actualAccount).isEqualTo(expectedAccount);
        }

        @Test
        @DisplayName("[正常系]指定した主キーのデータがなければ0が返る。")
        void test002() {
            // 準備
            deleteAccountAll();
            insertIntoDatabase(
                    createAccount("10")
            );

            // 実行
            Account expectedAccount = createAccount("11");
            int actualCount = target.updateByPrimaryKeySelective(expectedAccount);

            // 検証
            assertThat(actualCount).isEqualTo(0);
        }

        @Test
        @DisplayName("[正常系]特定の項目のみ更新する。")
        void test003() {
            // 準備
            deleteAccountAll();
            insertIntoDatabase(
                    createAccount("10")
            );

            // 実行
            Account expectedAccount = new Account();
            expectedAccount.setUsername(rightPad("10", 88, "0"));
            expectedAccount.setFirstName("Change");
            target.updateByPrimaryKeySelective(expectedAccount);

            // 検証
            Account actualAccount = target.selectByPrimaryKey(rightPad("10", 88, "0"));
            assertThat(actualAccount.getFirstName()).isEqualTo("Change");

            // FirstName以外を比較
            assertThat(actualAccount)
                    .isEqualToIgnoringGivenFields(createAccount("10"), "firstName");
        }
    }

    @Nested
    @ContextConfiguration(locations = {"classpath:test-context.xml"})
    @Transactional
    class updateByExample {
        @Test
        @DisplayName("[正常系]条件を指定して更新できること、戻値は更新件数")
        void test001() {
            // 準備
            deleteAccountAll();
            insertIntoDatabase(
                    createAccount("10"),
                    createAccount("11"),
                    createAccount("12"),
                    createAccount("20")
            );

            // 実行
            Account expectedAccount = createAccount("11");
            expectedAccount.setPassword("change");

            AccountExample example = new AccountExample();
            example.or().andUsernameEqualTo(rightPad("11", 88, "0"));
            int actualCount = target.updateByExample(expectedAccount, example);

            // 検証
            assertThat(actualCount).isEqualTo(1);

            Account actualAccount = target.selectByPrimaryKey(expectedAccount.getUsername());
            assertThat(actualAccount).isEqualTo(expectedAccount);
        }

        @Test
        @DisplayName("[正常系]指定した条件のデータがなければ0が返る。")
        void test002() {
            // 準備
            deleteAccountAll();
            insertIntoDatabase(
                    createAccount("10"),
                    createAccount("11"),
                    createAccount("12"),
                    createAccount("20")
            );

            // 実行
            Account expectedAccount = createAccount("11");
            AccountExample example = new AccountExample();
            example.or().andEmailEqualTo("NOT EXIST");
            int actualCount = target.updateByExample(expectedAccount, example);

            // 検証
            assertThat(actualCount).isEqualTo(0);
        }
    }

    @Nested
    @ContextConfiguration(locations = {"classpath:test-context.xml"})
    @Transactional
    class updateByExampleSelective {
        @Test
        @DisplayName("[正常系]条件を指定して更新できること、戻値は更新件数")
        void test001() {
            // 準備
            deleteAccountAll();
            insertIntoDatabase(
                    createAccount("10"),
                    createAccount("11"),
                    createAccount("12"),
                    createAccount("20")
            );

            // 実行
            Account expectedAccount = createAccount("11");
            expectedAccount.setPassword("change");

            AccountExample example = new AccountExample();
            example.or().andUsernameEqualTo(rightPad("11", 88, "0"));
            int actualCount = target.updateByExampleSelective(expectedAccount, example);

            // 検証
            assertThat(actualCount).isEqualTo(1);

            Account actualAccount = target.selectByPrimaryKey(expectedAccount.getUsername());
            assertThat(actualAccount).isEqualTo(expectedAccount);
        }

        @Test
        @DisplayName("[正常系]指定した条件のデータがなければ0が返る。")
        void test002() {
            // 準備
            deleteAccountAll();
            insertIntoDatabase(
                    createAccount("10"),
                    createAccount("11"),
                    createAccount("12"),
                    createAccount("20")
            );

            // 実行
            Account expectedAccount = createAccount("11");
            AccountExample example = new AccountExample();
            example.or().andEmailEqualTo("NOT EXIST");
            int actualCount = target.updateByExampleSelective(expectedAccount, example);

            // 検証
            assertThat(actualCount).isEqualTo(0);
        }

        @Test
        @DisplayName("[正常系]特定の条件で選択可能な複数のデータの特定の項目を一括更新、戻値は更新件数")
        void test003() {
            // 準備
            deleteAccountAll();
            insertIntoDatabase(
                    createAccount("10"),
                    createAccount("11"),
                    createAccount("12"),
                    createAccount("20")
            );

            // 実行
            Account expectedAccount = new Account();
            expectedAccount.setPassword("Change");
            AccountExample example = new AccountExample();
            example.or().andUsernameNotEqualTo(rightPad("20", 88, "0"));
            int actualCount = target.updateByExampleSelective(expectedAccount, example);

            // 検証
            assertThat(actualCount).isEqualTo(3);

            List<Account> actualAccounts = target.selectByExample(example);
            assertThat(actualAccounts)
                    .extracting(Account::getPassword)
                    .containsOnly("Change");
        }
    }
}
