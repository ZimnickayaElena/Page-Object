package test;
import data.DataHelper;
import org.junit.jupiter.api.Test;
import page.DashboardPage;
import page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static data.DataHelper.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestTransferMoney {

DashboardPage dashboardPage;
   @Test
        void shouldTransferMoneyBetweenOwnCards() {
            open("http://localhost:9999");
            var loginPage = new LoginPage();
            var authInfo = DataHelper.getAuthInfo();
            var verificationPage = loginPage.validLogin(authInfo);
            var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
            dashboardPage = verificationPage.validVerify(verificationCode);
            var firstCardInfo = getfirstCardInfo();
            var secondCardInfo = getsecondCardInfo();
            var firstBalance = dashboardPage.getCardBalance(DataHelper.getfirstCardInfo().getId());
            var secondBalance = dashboardPage.getCardBalance(DataHelper.getsecondCardInfo().getId());
            var amount = validAmount(firstBalance);
            var transferPage = dashboardPage.validTransfer(secondCardInfo);
            dashboardPage = transferPage.makeValidTransfer(String.valueOf(amount), firstCardInfo);
            var expectedBalanceFirstCard = firstBalance + amount;
            var expectedBalanceSecondCard = secondBalance - amount;
            var actualBalanceFirstCard = dashboardPage.getCardBalance(DataHelper.getfirstCardInfo().getId());
            var actualBalanceSecondCard = dashboardPage.getCardBalance(DataHelper.getsecondCardInfo().getId());
            assertEquals(expectedBalanceFirstCard, actualBalanceFirstCard);
            assertEquals(expectedBalanceSecondCard, actualBalanceSecondCard);


        }


}
