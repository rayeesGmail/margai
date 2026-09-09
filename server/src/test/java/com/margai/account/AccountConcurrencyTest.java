package com.margai.account;

import static org.assertj.core.api.Assertions.assertThat;

import com.margai.TestcontainersConfiguration;
import com.margai.account.api.Accounts;
import com.margai.account.api.LoginIdentifier;
import com.margai.account.api.SignIn;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * PLAN D9 "duplicate accounts" (checklist row 7): a brand-new identifier verified from several
 * devices at once — or one impatient tap racing its own retry — must end as one account with
 * every caller signed in, never as a unique-index violation surfacing as {@code INTERNAL}
 * (the D7 known edge). Real database, real transactions, one thread per login.
 */
@SpringBootTest
@Import(TestcontainersConfiguration.class)
class AccountConcurrencyTest {

    private static final int LOGINS = 8;
    private static final int ROUNDS = 5;

    @Autowired
    private Accounts accounts;

    @Autowired
    private JdbcTemplate jdbc;

    @Test
    void simultaneousFirstLoginsOfOneEmailMakeOneAccount() throws Exception {
        ExecutorService pool = Executors.newFixedThreadPool(LOGINS);
        try {
            for (int round = 0; round < ROUNDS; round++) {
                String email = "race-" + UUID.randomUUID() + "@example.com";
                CyclicBarrier start = new CyclicBarrier(LOGINS);
                List<Future<SignIn>> logins = new ArrayList<>();
                for (int i = 0; i < LOGINS; i++) {
                    logins.add(pool.submit(signInAtTheBarrier(email, start)));
                }

                List<SignIn> outcomes = new ArrayList<>();
                for (Future<SignIn> login : logins) {
                    outcomes.add(login.get()); // a failed login rethrows here and fails the test
                }

                assertThat(outcomes).hasSize(LOGINS);
                assertThat(outcomes.stream().map(signIn -> signIn.user().id()).distinct()).hasSize(1);
                assertThat(outcomes.stream().filter(SignIn::isNew)).as("exactly one login created the row").hasSize(1);
                assertThat(jdbc.queryForObject("select count(*) from users where email = ?", Integer.class, email))
                        .isEqualTo(1);
            }
        } finally {
            pool.shutdownNow();
        }
    }

    private Callable<SignIn> signInAtTheBarrier(String email, CyclicBarrier start) {
        return () -> {
            start.await();
            return accounts.signIn(new LoginIdentifier.Email(email));
        };
    }
}
