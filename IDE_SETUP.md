# IDE setup

Maven `pom.xml` is the build source of truth. Import this project using
**File → Import → Existing Maven Projects** in Eclipse (m2e), or open the
Maven project in NetBeans/IntelliJ.

- Compile against **Java 8 API/bytecode** as specified by
  `version.java.release=8` in the parent POM; the CI matrix additionally
  tests modern build JDKs. Check that the IDE uses an installed JDK.
- This repository intentionally retains the customized
  `.settings/org.eclipse.jdt.core.prefs` file with Java 8 compiler/
  diagnostic preferences. Re-evaluate if the Java compatibility policy
  changes.
- Do not commit generated `.classpath`, `.project`, generic resource
  encoding or m2e settings, `bin/`, or `target/`. Maven and m2e
  can recreate these files.
- `.editorconfig` establishes UTF-8/LF and two-space soft indentation.
  Install/enable an EditorConfig plugin if the IDE does not recognize it.
- `mvn -B -ntp clean verify` is the baseline build command.
