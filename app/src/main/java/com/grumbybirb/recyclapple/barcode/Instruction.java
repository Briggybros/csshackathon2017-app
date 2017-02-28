package com.grumbybirb.recyclapple.barcode;

/**
 * Created by bkazi on 27/02/2017.
 */

public class Instruction {
    private String name;
    private String instruction;

    public Instruction(String name, String instruction) {
        this.name = name;
        this.instruction = instruction;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getName() {
        return name;
    }

    public String getInstruction() {
        return instruction;
    }
}
