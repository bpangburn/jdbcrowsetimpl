# jdbcrowsetimpl

`jdbcrowsetimpl` provides a repackaged implementation of the standard OpenJDK `JdbcRowSet` implementation for applications that need direct access to implementation behavior such as `setConnection()` without relying on the non-exported `com.sun.rowset` package.

## Background

In Java 7, `RowSetFactory` was introduced as the preferred way to create `JdbcRowSet` objects:

- <https://docs.oracle.com/javase/tutorial/jdbc/basics/jdbcrowset.html>

However, `JdbcRowSet` objects created through `RowSetFactory` do not expose a `setConnection()` method, which can make connection reuse more difficult in some legacy or compatibility scenarios.

The standard `RowSet` API does provide `setDataSourceName()`, which can be used with a configured `DataSource` to support pooled database connections:

- <https://docs.oracle.com/javase/8/docs/api/javax/sql/RowSet.html#setDataSourceName-java.lang.String->

Beginning with OpenJDK 9, the `com.sun.rowset` package is not exported from the `java.sql.rowset` module, which prevents direct use of that internal implementation package in modular Java runtimes:

- <https://stackoverflow.com/questions/48129475/java-error-package-com-sun-rowset-is-not-visible-com-sun-rowset-is-declared>

For backward compatibility and continued connection reuse through `setConnection()`, this project publishes the OpenJDK rowset implementation under the `com.nqadmin.rowset` package as a Maven artifact.

## Source basis

Version 1.0.4 is based on the OpenJDK `java.sql.rowset` implementation from the fixed OpenJDK source tag `jdk-25.0.1-ga` in the `openjdk/jdk25u` repository:

- <https://github.com/openjdk/jdk25u/tree/jdk-25.0.1-ga/src/java.sql.rowset/share/classes/com/sun/rowset>

This project does not track OpenJDK `master` directly. Future releases should be reviewed against a newer fixed OpenJDK GA tag when appropriate.

The copied/adapted files are:

1. `JdbcRowSetImpl.java`
2. `JdbcRowSetResourceBundle.java`
3. `RowSetResourceBundle.properties`
4. `RowSetResourceBundle_de.properties`
5. `RowSetResourceBundle_es.properties`
6. `RowSetResourceBundle_fr.properties`
7. `RowSetResourceBundle_it.properties`
8. `RowSetResourceBundle_ja.properties`
9. `RowSetResourceBundle_ko.properties`
10. `RowSetResourceBundle_pt_BR.properties`
11. `RowSetResourceBundle_sv.properties`
12. `RowSetResourceBundle_zh_CN.properties`
13. `RowSetResourceBundle_zh_TW.properties`

## Project adaptations

The OpenJDK source files are adapted for this artifact in the following ways:

- package references are changed from `com.sun.rowset` to `com.nqadmin.rowset`;
- resource bundle lookup is changed to use the `com.nqadmin.rowset.RowSetResourceBundle` base name;
- Java 8 compatibility is preserved where current OpenJDK source uses Java 9+ module APIs.

## Java 8 compatibility

The OpenJDK `jdk-25.0.1-ga` version of `JdbcRowSetResourceBundle` uses module-aware resource lookup:

```java
// Load appropriate bundle according to locale
propResBundle = (PropertyResourceBundle) ResourceBundle.getBundle(PATH,
        locale, JdbcRowSetResourceBundle.class.getModule());
```

That code is replaced with a Java 8-compatible class-loader lookup:

```java
// Load appropriate bundle according to locale
propResBundle = (PropertyResourceBundle) ResourceBundle.getBundle(PATH,
        locale, Thread.currentThread().getContextClassLoader());
```

This allows the code to compile and run in Java 8 environments.

## Resource bundle base name

The OpenJDK resource bundle base name:

```java
private static final String PATH = "com.sun.rowset.RowSetResourceBundle";
```

is changed to the repackaged project base name:

```java
private static final String PATH = "com.nqadmin.rowset.RowSetResourceBundle";
```

This uses the dot-style `ResourceBundle` base-name convention from current OpenJDK source while pointing to the resources included under `src/main/resources/com/nqadmin/rowset/`.

## Build requirements

- Java 8 or later for normal compile/package builds.
- Maven 3.9.2 or later.
  - Maven 3.9.16 is recommended and was used for release preparation.
- Java 11 or later is required only when running the optional OWASP Dependency-Check profile.

## Build

To build locally:

```bash
mvn clean verify
```

To run OWASP Dependency-Check:

```bash
mvn -Powasp-check verify
```

To prepare release artifacts with sources, Javadocs, signatures, and Central Portal publishing support:

```bash
mvn -Prelease clean deploy
```

Before using the release profile, configure the required Central Portal and GPG credentials in your Maven `settings.xml`.

## License

This code is released under the GNU General Public License, version 2, with the Classpath Exception.

- <https://spdx.org/licenses/GPL-2.0-with-classpath-exception.html>
- <https://github.com/openjdk/jdk25u/blob/jdk-25.0.1-ga/LICENSE>

See [LICENSE.md](LICENSE.md) for the full license text.
