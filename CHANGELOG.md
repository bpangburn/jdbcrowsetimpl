# Changelog

All notable changes to `jdbcrowsetimpl` are documented here.

## JdbcRowSetImpl 1.0.5 - Released TBD

### Changed

- Stuff goes here...

## JdbcRowSetImpl 1.0.4 - Released 2026-06-12

### Changed

- Updated project source-basis documentation from the older OpenJDK 17 wording to the fixed OpenJDK `jdk-25.0.1-ga` source tag.
- Reviewed `JdbcRowSetImpl.java` against OpenJDK `jdk-25.0.1-ga`; no functional source changes were required beyond the existing `com.nqadmin.rowset` package adaptation.
- Updated `JdbcRowSetResourceBundle.java` from the OpenJDK `jdk-25.0.1-ga` source, adapted for:
  - package rename from `com.sun.rowset` to `com.nqadmin.rowset`;
  - dot-style resource bundle base name `com.nqadmin.rowset.RowSetResourceBundle`;
  - Java 8-compatible class-loader lookup instead of Java 9+ module-aware lookup.
- Synchronized `RowSetResourceBundle*.properties` files with the files present in OpenJDK `jdk-25.0.1-ga`, repackaged under `src/main/resources/com/nqadmin/rowset/`.
- Updated Maven build configuration for current non-beta plugin versions while retaining Java 8 compatibility.
- Updated Maven build policy:
  - enforce Maven 3.9.2+;
  - recommend Maven 3.9.16 for release preparation;
  - compile with Java release level 8 using the Maven Compiler Plugin `release` setting;
  - retain OWASP Dependency-Check as an opt-in Maven profile;
  - require Java 11+ only when running the optional OWASP check.
- Replaced legacy OSSRH/nexus-staging release publishing configuration with Sonatype Central Portal publishing configuration.
- Converted `CHANGELOG.txt` to Markdown as `CHANGELOG.md`.
- Converted `LICENSE` to Markdown as `LICENSE.md`.
- Reformatted and clarified `README.md`.
- Added `CHANGELOG-AI.md` to document AI-assisted review and release preparation notes.

## JdbcRowSetImpl 1.0.3 - Released 2021-10-18

### Changed

- Added `CHANGELOG.txt`.
- Updated dependency and plugin versions in the Maven POM.
- Updated `JdbcRowSetImpl` based on the 2021-09-27 OpenJDK source:
  - <https://github.com/openjdk/jdk/blob/426bcee9274bb3ec7dce551f85adb2ab61c22481/src/java.sql.rowset/share/classes/com/sun/rowset/JdbcRowSetImpl.java>
- Replaced `string.equals("")` checks with `isEmpty()` where applicable.
- Corrected various typos in SQL method documentation.
- Added `@SuppressWarnings("serial")` to various data members.

## JdbcRowSetImpl 1.0.2 - Released 2020-09-20

### Fixed

- Fixed the `PATH` constant in `RowSetResourceBundle`.
- Moved resource bundles into `src/main/resources`.

## JdbcRowSetImpl 1.0.1 - Released 2020-08-14

### Changed

- Updated the project description.
- Corrected a bad URL in the Maven POM.

## JdbcRowSetImpl 1.0.0 - Released 2020-08-13

### Added

- Initial release.
