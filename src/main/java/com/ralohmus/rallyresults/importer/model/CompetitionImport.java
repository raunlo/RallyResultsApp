package com.ralohmus.rallyresults.importer.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.jsoup.nodes.Element;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@Data
@Accessors(chain = true)
public class CompetitionImport {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private LocalDate startDate;
    private LocalDate endDate;
    private String name;
    private String link;
    private Set<StageResultsImport> stages;

    public CompetitionImport parseDateString(String dateString) {
        var containsMultipleDates = dateString.contains("-");
        if(containsMultipleDates) {
            startDate = LocalDate.parse(dateString.substring(0, dateString.indexOf('-')).stripTrailing(), DATE_FORMATTER);
            endDate = LocalDate.parse(dateString.substring(dateString.indexOf('-') + 1).strip(), DATE_FORMATTER);
        } else {
           var date =  LocalDate.parse(dateString.strip(), DATE_FORMATTER);
           startDate = date;
           endDate = date;
        }
        return this;
    }

    public CompetitionImport parseCompetitionNameAndLink(Element element) {
        this.name = element.text();
        this.link = element.selectFirst("a").attr("href");
        return this;
    }
}
