package io.sandbox.rx.pipeline.domain;

import java.util.List;

public class MasterDetail {
    private Header header;
    private List<Item> items;

    public MasterDetail(Header header, List<Item> items) {
        this.header = header;
        this.items = items;
    }

    public Header getHeader() {
        return header;
    }

    public List<Item> getItems() {
        return items;
    }

}
