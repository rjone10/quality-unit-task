import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class QueryLine extends Line {
    private final String serviceId;
    private final String[] queryArray;
    private final String queryType;
    private final LocalDate dateFrom;
    private final LocalDate dateTo;

    private QueryLine(String serviceId, String[] queryArray, String queryType, LocalDate dateFrom, LocalDate dateTo) {
        this.serviceId = serviceId;
        this.queryArray = queryArray;
        this.queryType = queryType;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public static QueryLine getInstance(String line) {
        String[] queryArray = line.split(" ");

        String[] serviceArr = queryArray[1].split("\\.");
        String serviceId = serviceArr[0];

        String[] typeArr = queryArray[2].split("\\.");
        String queryType = typeArr[0];

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String[] dateArr = queryArray[4].split("-");

        LocalDate dateFrom = LocalDate.parse(dateArr[0], formatter);
        LocalDate dateTo = null;
        if (dateArr.length > 1) {
            dateTo = LocalDate.parse(dateArr[1], formatter);
        }
        return new QueryLine(serviceId, queryArray, queryType, dateFrom, dateTo);
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

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }
}
