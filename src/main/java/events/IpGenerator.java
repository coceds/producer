package events;

import com.google.common.base.Charsets;
import com.google.common.base.Throwables;
import com.google.common.net.InetAddresses;
import org.apache.commons.net.util.SubnetUtils;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

//not thread safe
public class IpGenerator {

    private final List<String> addresses;
    private final Random random = new Random();

    public IpGenerator(String resource) {

        try {
            URL url = com.google.common.io.Resources.getResource(resource);
            List<String> lines = com.google.common.io.Resources.readLines(url, Charsets.UTF_8);
            this.addresses = lines.stream()
                    .skip(1)
                    .filter(s -> s.contains(","))
                    .map(s -> s.substring(0, s.indexOf(",")))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw Throwables.propagate(e);
        }
    }

    private String prepare(String network) {
        SubnetUtils utils = new SubnetUtils(network.trim());
        int min = InetAddresses.coerceToInteger(InetAddresses.forString(utils.getInfo().getLowAddress()));
        int max = InetAddresses.coerceToInteger(InetAddresses.forString(utils.getInfo().getHighAddress()));
        int ip = min + random.nextInt(Math.abs(max - min));
        return InetAddresses.fromInteger(ip).getHostAddress();
    }

    public String generate() {
        String network = addresses.get(random.nextInt(addresses.size()));
        return prepare(network);
    }
}
