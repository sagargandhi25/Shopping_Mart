package info.android.shopmart.db.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Transaction extends RealmObject {
    @PrimaryKey
    int id;
}
