package manager;

import com.google.common.base.Charsets;
import events.DateGenerator;
import events.EventGenerator;
import events.IpGenerator;
import events.ProductGenerator;
import org.apache.flume.Event;
import org.apache.flume.api.RpcClient;
import org.apache.flume.api.RpcClientFactory;
import org.apache.flume.event.EventBuilder;

import java.util.Iterator;
import java.util.stream.Stream;

public class AvroSender {

    private static final String IP = "192.168.56.101";
    private static final int PORT = 10017;
    private static final int EVENT_SIZE = 20000;
    private static final int PRODUCT_SIZE = 10000;
    private static final int DATE_SIZE = 7;
    private static final String IP_FILE = "GeoLite2-Country-CSV_20180102/GeoLite2-Country-Blocks-IPv4.csv";

    public static void main(String[] args) {
        RpcClient client = RpcClientFactory.getDefaultInstance(IP, PORT);
        EventGenerator eventGenerator = new EventGenerator(
                new IpGenerator(IP_FILE),
                new ProductGenerator(PRODUCT_SIZE),
                new DateGenerator(DATE_SIZE)
        );
        Stream<String> stream = eventGenerator.generateEvents(EVENT_SIZE);
        try {
            writeData(client, stream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            client.close();
        }
    }

    private static void writeData(RpcClient client, Stream<String> stream) throws Exception {
        Iterator<String> iterator = stream.iterator();
        while (iterator.hasNext()) {
            Event event = EventBuilder.withBody(iterator.next(), Charsets.UTF_8);
            client.append(event);
        }
    }
}
