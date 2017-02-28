package com.grumbybirb.recyclapple.barcode;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by bkazi on 28/02/2017.
 */

public class RequestResults {
    private List<Instruction> results;
    private String error;

    public RequestResults(List<Instruction> results, String error) {
        this.results = results;
        this.error = error;
    }

    public void setResults(List<Instruction> results) {
        this.results = results;
    }

    public List<Instruction> getResults() {
        return results;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
