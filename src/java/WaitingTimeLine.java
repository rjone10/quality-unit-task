import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class WaitingTimeLine extends Line {
    private final String serviceId;
    private final String[] queryArray;
    private final String queryType;
    private final LocalDate date;
    private final int time;

    private WaitingTimeLine(String serviceId, String[] queryArray, String queryType, LocalDate date, int time) {
        this.serviceId = serviceId;
        this.queryArray = queryArray;
        this.queryType = queryType;
        this.date = date;
        this.time = time;
    }

    public static WaitingTimeLine getInstance(String line) {
        String[] queryArray = line.split(" ");

        String[] serviceArr = queryArray[1].split("\\.");
        String serviceId = serviceArr[0];

        String[] typeArr = queryArray[2].split("\\.");
        String queryType = typeArr[0];

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String[] dateArr = queryArray[4].split("-");

        LocalDate date = LocalDate.parse(dateArr[0], formatter);

        int time = Integer.parseInt(queryArray[5]);
        return new WaitingTimeLine(serviceId, queryArray, queryType, date, time);
    }


    public String getServiceId() {
        return serviceId;
    }

    public String[] getQueryArray() {
        return queryArray;
    }

    public String getQueryType() {
        return queryType;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getTime() {
        return time;
    }
}
