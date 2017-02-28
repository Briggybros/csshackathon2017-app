package com.grumbybirb.recyclapple.barcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bkazi on 28/02/2017.
 */

public class RequestResults {
    private List<Instruction> instructions;

    public RequestResults(List<Instruction> instructions) {
        this.instructions = instructions;
    }

    public List<Instruction> getInstructions() {
        return instructions;
    }
}
