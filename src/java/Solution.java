import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Solution {
    public static void main(String[] args) {
        getWaitingTime();
    }

    public static void getWaitingTime() {
        List<String> records;
        Path path = Paths.get("src/java/resources/fileTest.txt");
        try {
            records = Files.readAllLines(path);
        } catch (IOException e) {
            System.out.println("Oops... some problems with your file.");
            return;
        }

        if (records.size() <= 1) return;

        List<Line> lines = new ArrayList<>();
        for (String string : records) {
            lines.add(string.startsWith("C") ? WaitingTimeLine.getInstance(string) : QueryLine.getInstance(string));
        }

        List<WaitingTimeLine> waitingTimeLineList = new ArrayList<>();
        for (Line line : lines) {
            if (line instanceof QueryLine) {
                waitingTimeLine((QueryLine) line, waitingTimeLineList);
            } else {
                waitingTimeLineList.add((WaitingTimeLine) line);
            }
        }
    }

    public static void waitingTimeLine(QueryLine queryLine, List<WaitingTimeLine> lines) {
        String queryLineServiceId = queryLine.getServiceId();
        String queryLineType = queryLine.getQueryType();
        AtomicInteger counter = new AtomicInteger();
        AtomicInteger time = new AtomicInteger();

        lines.forEach(waitingTimeLine -> {
            boolean isServiceIdEquals = waitingTimeLine.getServiceId().equals(queryLineServiceId);
            boolean isQueryTypeEquals = "*".equals(queryLineType) || waitingTimeLine.getQueryType().equals(queryLineType);

            LocalDate waitingTimeLineDate = waitingTimeLine.getDate();
            LocalDate queryTimeLineDateFrom = queryLine.getDateFrom();
            LocalDate queryTimeLineDateTo = queryLine.getDateTo();

            boolean isDate = queryTimeLineDateTo != null ?
                    waitingTimeLineDate.isAfter(queryTimeLineDateFrom) &&
                            waitingTimeLineDate.isBefore(queryTimeLineDateTo) :
                    waitingTimeLineDate.isEqual(queryTimeLineDateFrom);

            if (isServiceIdEquals && isQueryTypeEquals && isDate) {
                time.addAndGet(waitingTimeLine.getTime());
                counter.getAndIncrement();
            }
        });
        System.out.println(time.get() != 0 ? time.get() / counter.get() : "-");
    }
}
