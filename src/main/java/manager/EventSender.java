package manager;

import events.DateGenerator;
import events.EventGenerator;
import events.IpGenerator;
import events.ProductGenerator;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Iterator;
import java.util.stream.Stream;

public class EventSender {

    private static final int EVENT_SIZE = 10;
    private static final int PRODUCT_SIZE = 1000;
    private static final int DATE_SIZE = 7;
    private static final String IP_FILE = "GeoLite2-Country-CSV_20180102/GeoLite2-Country-Blocks-IPv4.csv";

    public static void main(String[] args) throws IOException {

        Socket pingSocket = null;
        PrintWriter out = null;

        EventGenerator eventGenerator = new EventGenerator(
                new IpGenerator(IP_FILE),
                new ProductGenerator(PRODUCT_SIZE),
                new DateGenerator(DATE_SIZE)
        );
        //Stream<String> stream = Stream.of("2015-11-12,123");
        Stream<String> stream = eventGenerator.generateEvents(EVENT_SIZE);
        try {
            pingSocket = new Socket("192.168.56.101", 56565);
            out = new PrintWriter(pingSocket.getOutputStream(), false);
            writeData(out, stream);
            //stream.forEach(s -> System.out.println(s));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
            if (pingSocket != null) {
                pingSocket.close();
            }
        }
    }

    private static void writeData(PrintWriter out, Stream<String> stream) {
        int index = 0;
        Iterator<String> iterator = stream.iterator();
        while (iterator.hasNext()) {
            out.println(iterator.next());
            index++;
            if (index % 10 == 0) {
                out.flush();
            }
        }
        out.flush();
    }
}
