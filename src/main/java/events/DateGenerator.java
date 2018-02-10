package events;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DateGenerator {

    private final List<LocalDate> dates;
    private final int range;
    private final Random random = new Random();
    private final LocalDate now = LocalDate.of(2018, 02, 07);

    public DateGenerator(int range) {
        this.range = range;
        this.dates = IntStream.range(0, range)
                .mapToObj(now::plusDays)
                .collect(Collectors.toList());
    }

    public LocalDate generate() {
        //return now;
        return dates.get(random.nextInt(range));
    }
}
