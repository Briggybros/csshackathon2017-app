package com.grumbybirb.recyclapple.barcode;

import java.util.List;

/**
 * Created by bkazi on 28/02/2017.
 */

public class ItemData {
    private List<Component> components;

    public  ItemData(List<Component> components) {
        this.components = components;
    }

    public List<Component> getComponents() {
        return components;
    }

    public void setComponents(List<Component> components) {
        this.components = components;
    }
}
