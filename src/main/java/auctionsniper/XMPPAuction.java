package auctionsniper;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import auctionsniper.util.Announcer;
import static java.lang.String.format;

public class XMPPAuction implements Auction {

    private final Announcer<AuctionEventListener> auctionEventListeners =
            Announcer.to(AuctionEventListener.class);
    private final Chat chat;

    public XMPPAuction(XMPPConnection connection, String itemId) {
        chat = connection.getChatManager().createChat(
                auctionId(itemId, connection),
                new AuctionMessageTranslator(connection.getUser(), auctionEventListeners.announce()));
    }

    public void bid(int amount) {
        sendMessage(format(Main.BID_COMMAND_FORMAT, amount));
    }

    public void join() {
        sendMessage(Main.JOIN_COMMAND_FORMAT);
    }

    @Override
    public void addAuctionEventListener(AuctionEventListener listener) {
        auctionEventListeners.addListener(listener);
    }

    private void sendMessage(final String message) {
        try {
            chat.sendMessage(message);
        } catch (XMPPException e) {
            e.printStackTrace();
        }
    }

    private static String auctionId(String itemId, XMPPConnection connection) {
        return String.format(Main.AUCTION_ID_FORMAT, itemId, connection.getServiceName());
    }
}