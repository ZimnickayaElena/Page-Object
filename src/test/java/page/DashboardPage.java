package page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import data.DataHelper;
import lombok.val;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;


public class DashboardPage {
    private SelenideElement heading = $("[data-test-id=dashboard]");
    private ElementsCollection card = $$(".list__item");
    private SelenideElement cardButton = $("[data-test-id=action-deposit]");
    private SelenideElement reloadButton = $("[data-test-id=action-reload]");
    private String balanceStart = "баланс: ";
    private String balanceFinish = " р.";



    public DashboardPage() {
        heading.shouldBe(visible);
    }

    public int getCardBalance(String id) {
    var text = $("[data-test-id={id}]");
    return extractBalance(String.valueOf(text));
    }

    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

    public TransferPage validTransfer(DataHelper.CardInfo cardInfo) {
        cardButton.click();
        return new TransferPage();
    }
}

