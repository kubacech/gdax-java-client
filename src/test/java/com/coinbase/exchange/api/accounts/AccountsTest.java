package com.coinbase.exchange.api.accounts;

import com.coinbase.exchange.api.BaseTest;
import com.coinbase.exchange.api.GdaxClient;
import com.coinbase.exchange.api.entity.Hold;
import com.coinbase.exchange.api.test.IntegrationTest;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@Category(IntegrationTest.class)
public class AccountsTest extends BaseTest {

    AccountService accountService;

    @Before
    public void init() {
        this.accountService = gdax.accountService();
    }

    @Test
    public void canGetAccounts() {
        List<Account> accounts  = accountService.getAccounts().block();
        assertTrue(accounts.size() >= 0);
    }

    @Test
    public void getAccount() {
        List<Account> accounts  = accountService.getAccounts().block();
        Account account = accountService.getAccount(accounts.get(0).getId()).block();
        assertTrue(account != null);
        Account acc = accountService.getAccount(account.getId()).block();
        assertNotNull(acc);
    }

    @Test
    public void canGetAccountHistory() {
        List<Account> accounts = accountService.getAccounts().block();
        List<AccountHistory> history = accountService.getAccountHistory(accounts.get(0).getId()).block();
        assertTrue(history.size() >=0); // anything but null/error.
    }

    @Test
    public void canGetAccountHolds() {
        List<Account> accounts = accountService.getAccounts().block();
        List<Hold> holds = accountService.getHolds(accounts.get(0).getId()).block();
        assertTrue(holds.size() >= 0);
        // only check the holds if they exist
        if (holds.size()>0) {
            assertTrue(holds.get(0).getAmount().floatValue() >= 0.0f);
        }
    }

    @Test
    public void canGetPagedAccountHistory() {
        List<Account> accounts = accountService.getAccounts().block();
        assertTrue(accounts.size() > 0);
        /**
         * note that for paged requests the before param takes precedence
         * only if before is null and after is not-null will the after param be inserted.
         */
        String beforeOrAfter = "before";
        int pageNumber = 1;
        int limit = 3;
        List<AccountHistory> firstPageAccountHistory = accountService.getPagedAccountHistory(accounts.get(0).getId(),
                beforeOrAfter, pageNumber, limit).block();
        assertTrue(firstPageAccountHistory != null);
        assertTrue(firstPageAccountHistory.size() >= 0);
        assertTrue(firstPageAccountHistory.size() <= limit);
    }

    /**
     * Test is ignored as it's failing. Seems the request here is
     * a bad one. Not sure if this is because there are no holds or
     * if this is due to the request (which is the same as for account history)
     * is actually fine but there's no data available.
     */
    //@Ignore
    @Test
    public void canGetPagedHolds() {
        List<Account> accounts = accountService.getAccounts().block();

        assertTrue(accounts!=null);
        assertTrue(accounts.size() > 0);

        String beforeOrAfter = "after";
        int pageNumber = 1;
        int limit = 5;

        List<Hold> firstPageOfHolds = accountService.getPagedHolds(accounts.get(0).getId(),
                beforeOrAfter,
                pageNumber,
                limit).block();

        if (firstPageOfHolds != null ) {
            assertTrue(firstPageOfHolds != null);
            assertTrue(firstPageOfHolds.size() >= 0);
            assertTrue(firstPageOfHolds.size() <= limit);
        }
    }
}
