package com.ralohmus.rallyresults.importer.strategy.impl;

import com.ralohmus.rallyresults.importer.model.CompetitionImport;
import com.ralohmus.rallyresults.importer.model.StageImport;
import com.ralohmus.rallyresults.importer.model.StageResultsImport;
import com.ralohmus.rallyresults.importer.strategy.RallyResultsImportStrategy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class RallyResultsImportStrategyImpl implements RallyResultsImportStrategy {

    private static final String BASE_URL = "http://autosport.ee/rallyreg/?page=22";

    @Override
    public Set<CompetitionImport> findAllResults() {
        return getAllRalliesPage();
    }

    private Set<CompetitionImport> getAllRalliesPage() {
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

    private CompetitionImport getCompetitionFromHtml(Element element) {
        var tableColumns = element.select("td").stream().skip(2).collect(Collectors.toUnmodifiableList());
        return new CompetitionImport()
          .parseDateString(tableColumns.get(0).text())
          .parseCompetitionNameAndLink(tableColumns.get(1))
          .setStages(getStageResultImport(tableColumns.get(1).selectFirst("a").attr("href")));
    }

    private Set<StageResultsImport> getStageResultImport(String link) {
        var table = getCompetitionLinksTable(link);
        var areResultsPresent = table.select("tr").outerHtml().contains("Tulemused");
        if (!areResultsPresent) return Set.of();

        var stagesLink = table.select("tr").stream().skip(4).filter(td -> td.text().contains("Tulemused"))
          .findFirst().orElseThrow()
          .select("a")
          .attr("href");

        return parseStages(stagesLink);
    }


    private Set<StageResultsImport> parseStages(String link) {
        return getStagesTable(link).select("tr").stream().skip(4).map(this::createStageResultImport)
          .flatMap(Collection::stream)
          .collect(Collectors.toUnmodifiableSet());
    }

    private Set<StageResultsImport> createStageResultImport(Element element) {
        System.out.println(element.outerHtml());
        var stageResultTable = getStageTable(
          element.select("td").select("a")
          .stream().findFirst()
          .orElseThrow()
          .attr("href")
        );
        System.out.println(stageResultTable.outerHtml());


//        return new StageResultsImport()
//          .setStage()
//          .setCoDriver()
//          .setDriver();
        return null;
    }

    private StageImport createStage(Element element) {
       return new StageImport()
          .setName(element.select("td").get(1).text());
//          .setLength()
    }

//    private Set<StageImport> getStages(String link) {
//        var table = getCompetitionLinksTable(link);
//        var stagesLink = table.select("tr").stream().skip(4)
//          .findFirst().orElseThrow()
//          .select("a")
//          .attr("href");
//        return parseStages(stagesLink);
//    }

    private Element getCompetitionLinksTable(String link) {
        Map<String, String> elementAttributesMap = Map.of("width", "100%", "cellpadding", "0", "cellspacing", "3", "border", "0", "align", "center");
        var url = BASE_URL.substring(0, BASE_URL.lastIndexOf("/")) + link;
        var doc = getDocumentFromLink(url);
        return doc.select("table").stream().filter(element -> validateElement(element, elementAttributesMap)).findFirst().orElseThrow();
    }

//    private Set<StageImport> parseStages(String link) {
//        return getDocumentFromLink(link).select("tr").stream().skip(1)
//          .map(this::createStageImport)
//          .collect(Collectors.toUnmodifiableSet());
//
//    }

    private StageImport createStageImport(Element element) {
        var tdElement = element.select("td");
        new StageImport()
          .setName(tdElement.get(1).text().strip())
          .setLength(Double.valueOf(tdElement.get(3).text().strip()));
        return null;
    }

    private Element getStagesTable(String link) {
        Map<String, String> elementAttributesMap = Map.of("width", "100%", "cellpadding", "0", "cellspacing", "0", "border", "0", "align", "center");
        var url = BASE_URL.substring(0, BASE_URL.lastIndexOf("/")) + link;
        var doc = getDocumentFromLink(url);
        return doc.select("table").stream().filter(element -> validateElement(element, elementAttributesMap)).findFirst().orElseThrow();
    }

    private Element getStageTable(String link) {
        Map<String, String> elementAttributesMap = Map.of("width", "100%", "cellpadding", "0", "cellspacing", "3", "border", "0", "align", "center");
        var url = BASE_URL.substring(0, BASE_URL.lastIndexOf("/")) + link;
        var doc = getDocumentFromLink(url);
        System.out.println(doc.outerHtml());
        return doc.select("table").stream().skip(4).filter(element -> validateElement(element, elementAttributesMap)).findFirst().orElseThrow();
    }

}
