package edu.cad.utils.hibernateutils;

public class NativeQueryExecutor {
    private NativeQueryExecutor() {
        //this is a utility method thus it should not have  public constructor
    }

    public static void executeQueryWithinTransaction(final String sql) {
//        final Session session = SESSION_MANAGER.getCurrentSession();
//        final Transaction transaction = session.beginTransaction();
//        final NativeQuery query = session.createNativeQuery(sql);
//
//        query.executeUpdate();
//        transaction.commit();
    }
}
