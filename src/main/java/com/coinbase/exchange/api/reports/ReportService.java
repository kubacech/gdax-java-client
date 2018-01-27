package com.coinbase.exchange.api.reports;

import com.coinbase.exchange.api.exchange.GdaxExchange;
import org.springframework.core.ParameterizedTypeReference;
import reactor.core.publisher.Mono;

/**
 * Created by robevansuk on 16/02/2017.
 */
public class ReportService {

    static final String REPORTS_ENDPOINT = "/reports";

    private GdaxExchange exchange;

    public ReportService(GdaxExchange exchange) {
        this.exchange = exchange;
    }

    // TODO untested
    public Mono<ReportResponse> createReport(String type, String startDate, String endDate){
        ReportRequest reportRequest = new ReportRequest(type, startDate, endDate);
        return exchange.post(REPORTS_ENDPOINT, new ParameterizedTypeReference<ReportResponse>(){}, reportRequest);
    }

    // TODO untested
    public Mono<ReportResponse> getReportStatus(String id) {
        return exchange.get(REPORTS_ENDPOINT + "/" + id, new ParameterizedTypeReference<ReportResponse>(){});
    }
}
