package com.grumbybirb.recyclapple.barcode;

/**
 * Created by bkazi on 27/02/2017.
 */

public class Instruction {
    private String name;
    private String instruction;

    public Instruction(String name, String instruction) {
        this.name = name;
        this.instruction = name;
    }

    public String getName() {
        return name;
    }

    public String getInstruction() {
        return instruction;
    }
}
