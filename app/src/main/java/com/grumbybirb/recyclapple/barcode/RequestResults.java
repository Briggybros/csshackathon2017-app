package com.grumbybirb.recyclapple.barcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bkazi on 28/02/2017.
 */

public class RequestResults {
    private List<Instruction> results;

    public RequestResults(List<Instruction> results) {
        this.results = results;
    }

    public void setResults(List<Instruction> results) {
        this.results = results;
    }

    public List<Instruction> getResults() {
        return results;
    }
}
