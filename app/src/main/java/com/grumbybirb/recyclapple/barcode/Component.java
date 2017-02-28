package com.grumbybirb.recyclapple.barcode;

/**
 * Created by bkazi on 28/02/2017.
 */

public class Component {
    private String name;
    private String materialId;

    public Component(String name, String materialId) {
        this.materialId = materialId;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public String getMaterialId() {
        return materialId;
    }
}
