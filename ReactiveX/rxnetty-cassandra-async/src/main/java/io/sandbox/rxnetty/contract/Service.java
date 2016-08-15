package io.sandbox.rxnetty.contract;

import io.sandbox.rxnetty.dao.MyDao;
import rx.Observable;

import java.io.UnsupportedEncodingException;

public class Service {
    private final MyDao dao;

    public Service(MyDao dao) {
        this.dao = dao;
    }

    public Observable<Output> getAll() {
        return dao.getAll().map(dbEntity -> {
            try {
                return new Output( new String(dbEntity.getId().array(), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        });
    }

}
