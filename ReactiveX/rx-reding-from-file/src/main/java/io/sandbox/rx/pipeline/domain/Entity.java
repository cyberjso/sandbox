package io.sandbox.rx.pipeline.domain;


public abstract  class Entity {
    private String idFieldType;

    public Entity(String idFieldType) {
        this.idFieldType = idFieldType;
    }

    public String getIdFieldType() {
        return idFieldType;
    }
}
