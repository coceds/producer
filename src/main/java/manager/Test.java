package manager;

import com.google.common.base.Charsets;
import org.apache.commons.net.util.SubnetUtils;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Test {

    private static final String IP_FILE = "GeoLite2-Country-CSV_20180102/GeoLite2-Country-Blocks-IPv4.csv";

    public static void main(String[] args) throws Exception {

//        InetAddress res = InetAddresses.forString("0.255.255.255");
//        int result = InetAddresses.coerceToInteger(res);
//        System.out.println(result);
//        System.out.println(ipToLong("0.255.255.255"));
//        System.out.println(InetAddresses.fromInteger(5555).getHostAddress());
//
//        SubnetUtils utils = new SubnetUtils("127.0.1.0/16");
//        System.out.println(utils.getInfo().getHighAddress());

//        SubnetUtils utils = new SubnetUtils(network.trim());
//        this.begin = ipToLong(utils.getInfo().getLowAddress());
        URL url = com.google.common.io.Resources.getResource(IP_FILE);
        List<String> lines = com.google.common.io.Resources.readLines(url, Charsets.UTF_8);
        CodeToIp codeToIp = new CodeToIp();
        lines.stream()
                .skip(1)
                .map(s -> s.split(","))
                .filter(parts -> parts.length >= 6)
                .forEach(parts -> {
                    SubnetUtils utils = new SubnetUtils(parts[0].trim());
                    long begin = ipToLong(utils.getInfo().getLowAddress());
                    long end = ipToLong(utils.getInfo().getLowAddress());
                    codeToIp.addNetwork(begin, end, parts[1].trim());
                });
        String result = codeToIp.getGeoId("1.0.0.1");
        System.out.println(1);
    }

    static long ipToLong(String ipAddress) {
        String[] ipAddressInArray = ipAddress.split("\\.");
        long result = 0;
        for (int i = 0; i < ipAddressInArray.length; i++) {

            int power = 3 - i;
            int ip = Integer.parseInt(ipAddressInArray[i]);
            result += ip * Math.pow(256, power);
        }
        return result;
    }

    public static class CodeToIp {

        private final TreeMap<Long, Entry> map = new TreeMap<Long, Entry>();

        public void addNetwork(long startIp, long endIp, String geoId) {
            map.put(startIp, new Entry(endIp, geoId));
        }

        public String getGeoId(String ip) {
            long parsed = ipToLong(ip);
            Map.Entry<Long, Entry> entry = map.floorEntry(parsed);
            if (entry != null && parsed <= entry.getValue().end) {
                return new String(entry.getValue().geoId);
            } else {
                return null;
            }
        }
    }

    private static class Entry {

        private final long end;
        private final byte[] geoId;

        Entry(long end, String geoId) {
            this.end = end;
            this.geoId = geoId.getBytes();
        }
    }
}
