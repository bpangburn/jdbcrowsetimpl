# IDE setup

Maven `pom.xml` is the build source of truth. Import this project using
**File → Import → Existing Maven Projects** in Eclipse (m2e), or open the
Maven project in NetBeans/IntelliJ.

- Compile against **Java 8 API/bytecode** as specified by
  `version.java.release=8` in the parent POM; the CI matrix additionally
  tests modern build JDKs. Check that the IDE uses an installed JDK.
- Eclipse `.project`, `.classpath`, and all `.settings/` directories are
  local generated metadata and should not be committed. Maven/m2e is the
  source of compiler configuration; verify the Java release matches the POM.
  Custom Eclipse diagnostic preferences are local to each developer.
- After pulling this cleanup, use **Maven → Update Project** if necessary.
  If Eclipse project registration is stale, remove it from the workspace
  without deleting project contents on disk, then re-import as a Maven project.
  Do not commit generated `bin/` or `target/` output.
- `.editorconfig` establishes UTF-8/LF and two-space soft indentation.
  Install/enable an EditorConfig plugin if the IDE does not recognize it.
- `mvn -B -ntp clean verify` is the baseline build command.
