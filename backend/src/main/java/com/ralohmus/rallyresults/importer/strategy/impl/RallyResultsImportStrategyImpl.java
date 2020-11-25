package com.ralohmus.rallyresults.importer.strategy.impl;

import com.ralohmus.rallyresults.importer.model.*;
import com.ralohmus.rallyresults.importer.strategy.RallyResultsImportStrategy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class RallyResultsImportStrategyImpl implements RallyResultsImportStrategy {

    private static final String BASE_URL = "http://autosport.ee/rallyreg/?page=22";

    @Override
    public Set<RallyImport> findAllResults() {
        return getAllRalliesPage();
    }

    private Set<RallyImport> getAllRalliesPage() {
        var competitionTable = getCompetitionTable();
        var tableColumns = competitionTable.select("tbody").select("tr").stream().skip(4).collect(Collectors.toList());
        var pagingElement = tableColumns.get(tableColumns.size() - 1);
        tableColumns.remove(pagingElement);
        return tableColumns.stream().map(this::getCompetitionFromHtml).collect(Collectors.toSet());
    }

    private Element getCompetitionTable() {
        Map<String, String> elementAttributesMap = Map.of("width", "100%", "cellpadding", "0", "cellspacing", "0", "border", "0", "align", "center");
        var doc = getDocumentFromLink(BASE_URL);
        return doc.select("table").stream().filter(element -> validateElement(element, elementAttributesMap)).skip(1).findFirst().orElseThrow();
    }

    private Document getDocumentFromLink(String url) {
        try {
            return Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    private boolean validateElement(Element element, Map<String, String> elementAttributesMap) {
        var attributes = element.attributes();
        for (Map.Entry<String, String> attributesEntry : elementAttributesMap.entrySet()) {
            if (!(attributes.hasKey(attributesEntry.getKey()) && attributes.get(attributesEntry.getKey()).equals(attributesEntry.getValue()))) {
                return false;
            }
        }
        return true;
    }

    private RallyImport getCompetitionFromHtml(Element element) {
        var tableColumns = element.select("td").stream().skip(2).collect(Collectors.toUnmodifiableList());
        return new RallyImport()
                .parseDateString(tableColumns.get(0).text())
                .parseCompetitionNameAndLink(tableColumns.get(1))
                .setStages(getStageResultImport(tableColumns.get(1).selectFirst("a").attr("href")));
    }

    private Set<StageImport> getStageResultImport(String link) {
        var table = getCompetitionLinksTable(link);
        var areResultsPresent = table.select("tr").outerHtml().contains("Tulemused");
        if (!areResultsPresent) return Set.of();

        var stagesLink = table.select("tr").stream().skip(4).filter(td -> td.text().contains("Tulemused"))
                .findFirst().orElseThrow()
                .select("a")
                .attr("href");

        return parseStages(stagesLink);
    }

    private Element getCompetitionLinksTable(String link) {
        Map<String, String> elementAttributesMap = Map.of("width", "100%", "cellpadding", "0", "cellspacing", "3", "border", "0", "align", "center");
        var url = BASE_URL.substring(0, BASE_URL.lastIndexOf("/")) + link;
        var doc = getDocumentFromLink(url);
        return doc.select("table").stream().filter(element -> validateElement(element, elementAttributesMap)).findFirst().orElseThrow();
    }


    private Set<StageImport> parseStages(String link) {
        return getStagesTable(link).select("tr").stream().skip(4).map(this::createStageResultImport)
                .collect(Collectors.toUnmodifiableSet());
    }

    private Element getStagesTable(String link) {
        Map<String, String> elementAttributesMap = Map.of("width", "100%", "cellpadding", "0", "cellspacing", "0", "border", "0", "align", "center");
        var url = BASE_URL.substring(0, BASE_URL.lastIndexOf("/")) + link;
        var doc = getDocumentFromLink(url);
        return doc.select("table").stream().filter(element -> validateElement(element, elementAttributesMap)).findFirst().orElseThrow();
    }

    private StageImport createStageResultImport(Element element) {
        var stageResultsHtml = getStageTable(
                element.select("td").select("a").first()
                        .attr("href")
        );

        return new StageImport()
                .setName(element.select("td").stream().skip(1).findFirst().orElseThrow().text())
                .setLength(BigDecimal.valueOf(Double.parseDouble(element.select("td").stream().skip(3).findFirst().orElseThrow().text())))
                .setStageResultsImport(stageResultsHtml.stream()
                        .map(this::parseResult)
                        .collect(Collectors.toSet()))
                .setStageNumber(Integer.parseInt(element.select("td").first().text().replace(".", "")));
    }

    private List<Element> getStageTable(String link) {
        Map<String, String> elementAttributesMap = Map.of("width", "100%", "cellpadding", "0", "cellspacing", "3", "border", "0", "align", "center");
        var url = BASE_URL.substring(0, BASE_URL.lastIndexOf("/")) + link;
        var doc = getDocumentFromLink(url);
        return doc.select("table")
                .stream().filter(s -> validateElement(s, elementAttributesMap))
                .skip(3).findFirst().orElseThrow()
                .select("td").stream().skip(4).findFirst().orElseThrow()
                .select("tr").stream().skip(1).collect(Collectors.toList());
    }

    private StageResultsImport parseResult(Element element) {
        var result = new StageResultsImport();
        var usefulColumnsArray = element.select("td")
                .stream().skip(3)
                .collect(Collectors.toList());

        usefulColumnsArray = usefulColumnsArray.stream().skip(2).collect(Collectors.toList());
        result.setCompetitorPairImport(parseCompetitor(element).setCompetitionClass(usefulColumnsArray.get(0).text()));
        result.setTime(usefulColumnsArray.get(1).text());
        return result;
    }

    private CompetitorPairImport parseCompetitor(Element element) {
        var competitorPairImport = new CompetitorPairImport();
        Map<String, String> elementAttributesMap = Map.of("width", "100%", "cellpadding", "0", "cellspacing", "3", "border", "0", "align", "center");
        var competitorElement = getCompetitorNamesPage(element.select("a").attr("href"));
        var competitorNameElement = competitorElement.select("table")
                .select("tr").stream().skip(1).findFirst().orElseThrow()
                .select("td").select("table")
                .stream().filter(s -> validateElement(s, elementAttributesMap)).skip(2).findFirst().orElseThrow()
                .select("tr").select("th").stream().skip(2).findFirst().orElseThrow();
        var competitorNamesArray = competitorNameElement.text().split("/");
        competitorPairImport.setDriver(extractDriverName(competitorNamesArray[0]))
                .setCoDriver(extractDriverName(competitorNamesArray[1]));
        return competitorPairImport;

    }

    private Element getCompetitorNamesPage(String url) {
        return getDocumentFromLink(BASE_URL.substring(0, BASE_URL.lastIndexOf("/")) + url);
    }

    private CompetitorImport extractDriverName(String driverName) {
        var driverNameSplitted = new ArrayList<>(Arrays.asList(driverName.split(" ")));
        var driver = new CompetitorImport();
        driver.setLastName(driverNameSplitted.get(driverNameSplitted.size() - 1).strip());
        driver.setFirstName(driverNameSplitted.subList(0, driverNameSplitted.size() - 1).stream().map(String::strip).collect(Collectors.joining(" ")));
        return driver;
    }
}
