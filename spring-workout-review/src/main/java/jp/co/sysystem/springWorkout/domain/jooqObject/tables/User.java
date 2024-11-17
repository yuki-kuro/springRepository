/*
 * This file is generated by jOOQ.
 */
package jp.co.sysystem.springWorkout.domain.jooqObject.tables;


import java.util.Arrays;
import java.util.List;

import javax.annotation.processing.Generated;

import jp.co.sysystem.springWorkout.domain.jooqObject.Indexes;
import jp.co.sysystem.springWorkout.domain.jooqObject.Keys;
import jp.co.sysystem.springWorkout.domain.jooqObject.Workout;
import jp.co.sysystem.springWorkout.domain.jooqObject.tables.records.UserRecord;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row4;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


/**
 * ユーザーマスタ
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.12.4"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class User extends TableImpl<UserRecord> {

    private static final long serialVersionUID = 1029206562;

    /**
     * The reference instance of <code>workout.user</code>
     */
    public static final User USER = new User();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<UserRecord> getRecordType() {
        return UserRecord.class;
    }

    /**
     * The column <code>workout.user.ID</code>. ユーザーID
     */
    public final TableField<UserRecord, String> ID = createField(DSL.name("ID"), org.jooq.impl.SQLDataType.VARCHAR(5).nullable(false), this, "ユーザーID");

    /**
     * The column <code>workout.user.PASS</code>. パスワード
     */
    public final TableField<UserRecord, String> PASS = createField(DSL.name("PASS"), org.jooq.impl.SQLDataType.VARCHAR(8), this, "パスワード");

    /**
     * The column <code>workout.user.NAME</code>. 名前
     */
    public final TableField<UserRecord, String> NAME = createField(DSL.name("NAME"), org.jooq.impl.SQLDataType.VARCHAR(40), this, "名前");

    /**
     * The column <code>workout.user.KANA</code>. カナ
     */
    public final TableField<UserRecord, String> KANA = createField(DSL.name("KANA"), org.jooq.impl.SQLDataType.VARCHAR(40), this, "カナ");

    /**
     * Create a <code>workout.user</code> table reference
     */
    public User() {
        this(DSL.name("user"), null);
    }

    /**
     * Create an aliased <code>workout.user</code> table reference
     */
    public User(String alias) {
        this(DSL.name(alias), USER);
    }

    /**
     * Create an aliased <code>workout.user</code> table reference
     */
    public User(Name alias) {
        this(alias, USER);
    }

    private User(Name alias, Table<UserRecord> aliased) {
        this(alias, aliased, null);
    }

    private User(Name alias, Table<UserRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment("ユーザーマスタ"));
    }

    public <O extends Record> User(Table<O> child, ForeignKey<O, UserRecord> key) {
        super(child, key, USER);
    }

    @Override
    public Schema getSchema() {
        return Workout.WORKOUT;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.USER_PRIMARY);
    }

    @Override
    public UniqueKey<UserRecord> getPrimaryKey() {
        return Keys.KEY_USER_PRIMARY;
    }

    @Override
    public List<UniqueKey<UserRecord>> getKeys() {
        return Arrays.<UniqueKey<UserRecord>>asList(Keys.KEY_USER_PRIMARY);
    }

    @Override
    public User as(String alias) {
        return new User(DSL.name(alias), this);
    }

    @Override
    public User as(Name alias) {
        return new User(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public User rename(String name) {
        return new User(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public User rename(Name name) {
        return new User(name, null);
    }

    // -------------------------------------------------------------------------
    // Row4 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row4<String, String, String, String> fieldsRow() {
        return (Row4) super.fieldsRow();
    }
}
