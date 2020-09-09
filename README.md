# jdbcrowsetimpl
The standard implementation of the JdbcRowSet interface from OpenJDK, which does not require use of RowSetFactory.

In Java 7, the RowSetFactory Interface was introduced as the preferred way to create JdbcRowSet objects:
https://docs.oracle.com/javase/tutorial/jdbc/basics/jdbcrowset.html

The problem is that JdbcRowSet objects created in this manner lack a setConnection() method, which means
that connections cannot easily be reused. There is a setDataSourceName() method, which can be used to
pool database connections if the DataSource is configured to support pooling:
https://docs.oracle.com/javase/8/docs/api/javax/sql/RowSet.html#setDataSourceName-java.lang.String-

From OpenJDK 9+ (unconfirmed for OracleJDK), the com.sun.rowset package was not exported in
the javax.sql.rowset module:
https://stackoverflow.com/questions/48129475/java-error-package-com-sun-rowset-is-not-visible-com-sun-rowset-is-declared

For backwards compatibility and to allow for the continued leverage of connection reuse with
setConnection(), we're creating this jdbcrowsetimpl Maven artifact.

It is based on the latest OpenJDK 14 code available from: https://hg.openjdk.java.net/jdk/jdk14

  1. JdbcRowSetImpl.java
  2. JdbcRowSetResourceBundle.java
  3. RowSetResourceBundle_de.properties
  4. RowSetResourceBundle_es.properties
  5. RowSetResourceBundle_fr.properties
  6. RowSetResourceBundle_it.properties
  7. RowSetResourceBundle_ja.properties
  8. RowSetResourceBundle_ko.properties
  9. RowSetResourceBundle_pt_BR.properties
 10. RowSetResourceBundle_sv.properties
 11. RowSetResourceBundle_zh_CN.properties
 12. RowSetResourceBundle_zh_TW.properties
 13. RowSetResourceBundle.properties

The constructor for JdbcRowSetResourceBundle is modified around line 100 based on the Java 8 version of the file:
https://hg.openjdk.java.net/jdk8/jdk8/jdk/file/687fd7c7986d/src/share/classes/com/sun/rowset/JdbcRowSetResourceBundle.java
```
        // Load appropriate bundle according to locale
        propResBundle = (PropertyResourceBundle) ResourceBundle.getBundle(PATH,
                           locale, JdbcRowSetResourceBundle.class.getModule());
```
Is replaced with:
```
        // Load appropriate bundle according to locale
        propResBundle = (PropertyResourceBundle) ResourceBundle.getBundle(PATH,
                locale, Thread.currentThread().getContextClassLoader());
```
This allows the code to continue to work with Java 8.

In addition the path specified in JdbcRowSetResourceBundle.java needs to be updated near line 87 from:
```
	private static final String PATH = "com/sun/rowset/RowSetResourceBundle";
```
to:
```
    private static final String PATH = "com/nqadmin/rowset/RowSetResourceBundle";
```

We plan to periodically monitor the OpenJDK source for these files and update this artifact as required.

OpenJDK 14 rowset source: https://hg.openjdk.java.net/jdk/jdk14/file/6c954123ee8d/src/java.sql.rowset/share/classes/com/sun/rowset
OpenJDK 14 rowset source zip: https://hg.openjdk.java.net/jdk/jdk14/archive/6c954123ee8d.zip/src/java.sql.rowset/share/classes/com/sun/rowset/

This code is released under the GNU General Public License, version 2,with the Classpath Exception:
https://openjdk.java.net/legal/gplv2+ce.html
