package com.ralohmus.rallyresults.importer.strategy.impl;

import com.ralohmus.rallyresults.importer.model.CompetitorImport;

public class ResultsDataWrapper {

    private ResultsDataType type;
    private Object object;

    public boolean isDataTypeOfCompetitor() {
        return type == ResultsDataType.COMPETITOR;
    }

    public CompetitorImport getCompetitor() {
        if (! (object instanceof CompetitorImport)) {
            throw new ResultsDataWrapperException("Data is in wrong object");
        }
        return (CompetitorImport) object;
    }

     static class ResultsDataWrapperException extends RuntimeException {
        public ResultsDataWrapperException(String msg) {
            super(msg);
        }
    }
}
