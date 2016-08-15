package io.sandbox.rxnetty.dao;


import net.oneandone.troilus.Dao;
import net.oneandone.troilus.DaoImpl;
import rx.Observable;
import rx.RxReactiveStreams;

public class MyDao {
    private Dao dao;

    public MyDao() {
        this.dao = new DaoImpl(new SessionBuilder().build(), "standard1");
    }

    public Observable<DBEntity> getAll() {
        return RxReactiveStreams.toObservable(
                dao.readSequence().asEntity(DBEntity.class).executeRx());
    }

}
