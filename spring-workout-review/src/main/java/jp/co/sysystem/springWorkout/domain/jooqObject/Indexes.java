/*
 * This file is generated by jOOQ.
 */
package jp.co.sysystem.springWorkout.domain.jooqObject;


import javax.annotation.processing.Generated;

import jp.co.sysystem.springWorkout.domain.jooqObject.tables.User;
import jp.co.sysystem.springWorkout.domain.jooqObject.tables.Userdetail;

import org.jooq.Index;
import org.jooq.OrderField;
import org.jooq.impl.Internal;


/**
 * A class modelling indexes of tables of the <code>workout</code> schema.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.12.4"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Indexes {

    // -------------------------------------------------------------------------
    // INDEX definitions
    // -------------------------------------------------------------------------

    public static final Index USER_PRIMARY = Indexes0.USER_PRIMARY;
    public static final Index USERDETAIL_PRIMARY = Indexes0.USERDETAIL_PRIMARY;

    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Indexes0 {
        public static Index USER_PRIMARY = Internal.createIndex("PRIMARY", User.USER, new OrderField[] { User.USER.ID }, true);
        public static Index USERDETAIL_PRIMARY = Internal.createIndex("PRIMARY", Userdetail.USERDETAIL, new OrderField[] { Userdetail.USERDETAIL.NO }, true);
    }
}
