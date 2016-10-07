package test.integration.auctionsniper.ui;

import com.objogate.wl.swing.probe.ValueMatcherProbe;
import auctionsniper.SniperPortfolio;
import auctionsniper.UserRequestListener;
import auctionsniper.ui.MainWindow;
import test.endtoend.auctionsniper.AuctionSniperDriver;
import static org.hamcrest.CoreMatchers.equalTo;
import org.junit.After;
import org.junit.Test;

public class MainWindowTest {
    private final MainWindow mainWindow = new MainWindow(new SniperPortfolio());
    private final AuctionSniperDriver driver = new AuctionSniperDriver(100);

    @Test public void makesUserRequestWhenJoinButtonClicked() {
        final ValueMatcherProbe<String> buttonProbe =
                new ValueMatcherProbe<>(equalTo("an item-id"), "join request");

        mainWindow.addUserRequestListener(
                new UserRequestListener() {
                    public void joinAuction(String itemId) {
                        buttonProbe.setReceivedValue(itemId);
                    }
                });

        driver.startBiddingFor("an item-id");
        driver.check(buttonProbe);
    }

    @After public void disposeDriver() {
        driver.dispose();
    }
}