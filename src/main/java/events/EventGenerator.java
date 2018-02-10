package events;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class EventGenerator {

    private final IpGenerator ipGenerator;
    private final ProductGenerator productGenerator;
    private final DateGenerator dateGenerator;

    public EventGenerator(IpGenerator ipGenerator,
                          ProductGenerator productGenerator, DateGenerator dateGenerator) {
        this.ipGenerator = ipGenerator;
        this.productGenerator = productGenerator;
        this.dateGenerator = dateGenerator;
    }

    public Stream<String> generateEvents(int size) {
        return IntStream.range(0, size)
                .mapToObj(this::createEvent)
                .map(Event::toCsvString);
    }

    private Event createEvent(int position) {
        return new Event(position, productGenerator.generate(), dateGenerator.generate(), ipGenerator.generate());
    }
}
