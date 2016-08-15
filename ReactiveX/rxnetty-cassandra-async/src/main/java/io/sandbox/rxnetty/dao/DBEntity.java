package io.sandbox.rxnetty.dao;


import net.oneandone.troilus.Field;

import java.nio.ByteBuffer;

public class DBEntity {
    @Field(name = "key")
    private ByteBuffer id;

    public DBEntity() {   }

    public DBEntity(ByteBuffer id) {
        this.id = id;
    }

    public ByteBuffer getId() {
        return id;
    }

}
