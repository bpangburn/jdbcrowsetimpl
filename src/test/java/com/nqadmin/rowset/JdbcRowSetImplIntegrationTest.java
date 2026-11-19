package com.nqadmin.rowset;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Small JDBC 4.2 integration tests for the repackaged JdbcRowSetImpl.
 * Each test uses a unique, in-memory H2 database; no services or credentials.
 */
class JdbcRowSetImplIntegrationTest {

    private String url;

    @BeforeEach
    void createDatabase() throws SQLException {
        url = "jdbc:h2:mem:rowset_" + UUID.randomUUID().toString().replace("-", "")
                + ";DB_CLOSE_DELAY=-1";
        try (Connection connection = DriverManager.getConnection(url, "sa", "");
                Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE ITEMS (ID INTEGER PRIMARY KEY, LABEL VARCHAR(40))");
            statement.execute("INSERT INTO ITEMS (ID, LABEL) VALUES (1, 'alpha')");
            statement.execute("INSERT INTO ITEMS (ID, LABEL) VALUES (2, 'beta')");
        }
    }

    @Test
    void scrollsForwardAndBackward() throws SQLException {
        try (JdbcRowSetImpl rowSet = new JdbcRowSetImpl(url, "sa", "")) {
            rowSet.setCommand("SELECT ID, LABEL FROM ITEMS ORDER BY ID");
            rowSet.execute();

            assertTrue(rowSet.next());
            assertEquals(1, rowSet.getInt("ID"));
            assertEquals("alpha", rowSet.getString("LABEL"));
            assertTrue(rowSet.next());
            assertEquals(2, rowSet.getInt("ID"));
            assertFalse(rowSet.next());
            assertTrue(rowSet.previous());
            assertEquals(2, rowSet.getInt("ID"));
            assertTrue(rowSet.absolute(1));
            assertEquals(1, rowSet.getInt("ID"));
        }
    }

    @Test
    void bindsPreparedStatementParameters() throws SQLException {
        try (JdbcRowSetImpl rowSet = new JdbcRowSetImpl(url, "sa", "")) {
            rowSet.setCommand("SELECT ID, LABEL FROM ITEMS WHERE ID = ?");
            rowSet.setInt(1, 2);
            rowSet.execute();

            assertTrue(rowSet.next());
            assertEquals("beta", rowSet.getString("LABEL"));
            assertFalse(rowSet.next());
        }
    }

    @Test
    void updatesExistingRow() throws SQLException {
        try (JdbcRowSetImpl rowSet = new JdbcRowSetImpl(url, "sa", "")) {
            rowSet.setReadOnly(false);
            rowSet.setCommand("SELECT ID, LABEL FROM ITEMS WHERE ID = 1");
            rowSet.execute();

            assertTrue(rowSet.next());
            rowSet.updateString("LABEL", "updated");
            rowSet.updateRow();
        }

        try (Connection connection = DriverManager.getConnection(url, "sa", "");
                Statement statement = connection.createStatement();
                ResultSet result = statement.executeQuery("SELECT LABEL FROM ITEMS WHERE ID = 1")) {
            assertTrue(result.next());
            assertEquals("updated", result.getString(1));
        }
    }

    @Test
    void insertsAndDeletesRows() throws SQLException {
        try (JdbcRowSetImpl rowSet = new JdbcRowSetImpl(url, "sa", "")) {
            rowSet.setReadOnly(false);
            rowSet.setCommand("SELECT ID, LABEL FROM ITEMS");
            rowSet.execute();

            rowSet.moveToInsertRow();
            rowSet.updateInt("ID", 3);
            rowSet.updateString("LABEL", "gamma");
            rowSet.insertRow();
            rowSet.moveToCurrentRow();
        }
        assertEquals(3, rowCount());

        try (JdbcRowSetImpl rowSet = new JdbcRowSetImpl(url, "sa", "")) {
            rowSet.setReadOnly(false);
            rowSet.setCommand("SELECT ID, LABEL FROM ITEMS WHERE ID = 2");
            rowSet.execute();

            assertTrue(rowSet.next());
            rowSet.deleteRow();
        }
        assertEquals(2, rowCount());
    }

    private int rowCount() throws SQLException {
        try (Connection connection = DriverManager.getConnection(url, "sa", "");
                Statement statement = connection.createStatement();
                ResultSet result = statement.executeQuery("SELECT COUNT(*) FROM ITEMS")) {
            assertTrue(result.next());
            return result.getInt(1);
        }
    }
}
