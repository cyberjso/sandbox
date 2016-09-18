package io.sandbox.rxnetty;

import io.sandbox.rxnetty.contract.Service;
import io.sandbox.rxnetty.dao.MyDao;

public class Main {

    public static void main(String[] args) {
        new EntryPoint(new Service(new MyDao())).build().startAndWait();
    }

}
