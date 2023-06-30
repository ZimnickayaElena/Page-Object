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
            var firstBalance = dashboardPage.getCardBalance(firstCardInfo);
            var secondBalance = dashboardPage.getCardBalance(secondCardInfo);
            var amount = generateValidAmount(firstBalance);
            var expectedBalanceFirstCard = firstBalance + amount;
            var expectedBalanceSecondCard = secondBalance - amount;
            var transferPage = dashboardPage.selectCardInfoToTransfer(firstCardInfo);
            dashboardPage = transferPage.makeValidTransfer(String.valueOf(amount), secondCardInfo);
            var actualBalanceFirstCard = dashboardPage.getCardBalance(firstCardInfo);
            var actualBalanceSecondCard = dashboardPage.getCardBalance(secondCardInfo);
            assertEquals(expectedBalanceFirstCard, actualBalanceFirstCard);
            assertEquals(expectedBalanceSecondCard, actualBalanceSecondCard);
        }


}
