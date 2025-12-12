The description of Ant, Maven, and Gradle integration is available at https://docs.parasoft.com/display/JTEST20252/Integrating+with+Build+Systems

This document describes how to perform static analysis, run tests, and collect coverage information with Jtest - using the "demo" project shipped with the product.

Please note that the analysis of this project is configured with the settings specified in the demo.properties file located in the [INSTALL]/examples/demo directory.
The demo.properties file is used by Ant, Maven and Gradle build scripts. The following command line examples are intended to be executed from the "demo" project directory.

Note for PowerShell users:
Passing parameters with dots in their names and leading dashes can sometimes result in unexpected behavior due to the way PowerShell interprets special characters.
To ensure smooth execution, use one of the approaches described below:
A.  Use a backtick "`" to escape the leading dash.
    Example:
    `-Djtest.config="builtin://Demo Configuration"

B.  Enclose both the parameter name and its value in double quotes.
    Example:
    "-Djtest.config=builtin://Demo Configuration"


Prerequisites
-------------------------------------------------
1. Set up the Jtest license in jtestcli.properties in the Jtest installation directory.


Jtest data file
-------------------------------------------------
1. Run the "Recommended Rules" test configuration to perform static analysis directly (without any build system) - using the following command:
   Windows:
     ..\..\jtestcli.exe -config "builtin://Recommended Rules" -data demo.data.json -report report
   UNIX:
     ../../jtestcli -config "builtin://Recommended Rules" -data demo.data.json -report report


Ant
-------------------------------------------------
1. Make sure that you have "ant" available on your path.

2. Run "Demo Configuration" to perform static analysis and collect coverage for unit tests:

     ant -file jtest.xml -Djtest.config="builtin://Demo Configuration"

   The "demo" project will be built and unit tests will be run. The Jtest Plugin for Ant collects source code compilation data,
   and unit test results with coverage to perform analysis and generate the report.

   Note:

   To only run static analysis, use the following command:

     ant -file jtest.xml jtest-sae

   To only run unit tests, use the following command:

     ant -file jtest.xml jtest-utc

   Configurations are specified in jtest.xml - see "jtest", "jtest-sae", or "jtest-utc" targets.


Maven
-------------------------------------------------
1. Configure your Maven setup by following the Jtest User Guide:
   https://docs.parasoft.com/display/JTEST20252/Configuring+the+Jtest+Plugin+for+Maven

2. Run "Demo Configuration" to perform static analysis and collect coverage for unit tests:

     mvnw clean test-compile jtest:agent test jtest:jtest -Djtest.config="builtin://Demo Configuration"

   The "demo" project will be built and unit tests will be run. The Jtest Plugin for Maven will collect source code compilation data,
   and test results with coverage to perform analysis and generate the report.

   Note:

   To only run static analysis, use the following command:

     mvnw jtest:jtest

   The "Recommended Rules" test configuration is used by default.

   To only run unit tests, use the following command:

     mvnw clean test-compile jtest:agent test jtest:jtest -Djtest.config="builtin://Unit Tests"


Gradle
-------------------------------------------------
Note: The "demo" project uses JUnit 5, which requires Gradle 4.6 or higher.

1. Configure the installed Jtest package or add the desired settings to the "jtest" block of your buildscript.

2. Run "Demo Configuration" to perform static analysis and collect coverage for unit tests:

     gradlew clean jtest-agent test jtest -Djtest.config="builtin://Demo Configuration"

   The "demo" project will be built and Junit tests will be run. The Jtest Plugin for Gradle will collect
   source code compilation data, and test results to perform analysis and generate report.

   Note:

   To only run static analysis, use the following command:

     gradlew clean assemble jtest

   "Recommended Rules" configuration is used by default.

   To only run unit tests, use the following command:

     gradlew clean jtest-agent test jtest -Djtest.config="builtin://Unit Tests"


=================================================

Test Impact Analysis

Test Impact analysis is supported for Maven and Gradle.

Windows:

1. Run the tests with Maven or Gradle to collect the baseline data about the tests and coverage:
   mvnw clean test-compile jtest:agent test jtest:jtest -Djtest.config="builtin://Unit Tests" -Djtest.report=tia
   or
   gradlew clean jtest-agent test jtest -Djtest.config="builtin://Unit Tests" -Djtest.report=tia

   Note: As a result, the report.xml and coverage.xml files will be created in the 'tia' subfolder.

2. Modify a source file from the test scope:
   src\main\java\examples\mock\FileExample.java

3. Run the following command to execute the tests affected by your change:
   mvnw clean tia:affected-tests test -Djtest.referenceCoverageFile=tia/coverage.xml -Djtest.referenceReportFile=tia/report.xml
   or
   gradlew clean affectedTests test -Djtest.referenceCoverageFile=tia/coverage.xml -Djtest.referenceReportFile=tia/report.xml

   Tests that aren't affected by your code modification are not executed.

UNIX:

1. Run the tests with Maven or Gradle to collect the baseline data about the tests and coverage:
   ./mvnw clean test-compile jtest:agent test jtest:jtest -Djtest.config="builtin://Unit Tests" -Djtest.report=tia
   or
   ./gradlew clean jtest-agent test jtest -Djtest.config="builtin://Unit Tests" -Djtest.report=tia

   Note: As a result, the report.xml and coverage.xml files will be created in the 'tia' subfolder.

2. Modify a source file from the test scope:
   src/main/java/examples/mock/FileExample.java

3. Run the following command to execute the tests affected by your change:
   ./mvnw clean tia:affected-tests test -Djtest.referenceCoverageFile=tia/coverage.xml -Djtest.referenceReportFile=tia/report.xml
   or
   ./gradlew clean affectedTests test -Djtest.referenceCoverageFile=tia/coverage.xml -Djtest.referenceReportFile=tia/report.xml

   Tests that aren't affected by your code modification are not executed.


For more information see:
   https://docs.parasoft.com/display/JTEST20252/Testing+and+Analysis+with+Maven
   https://docs.parasoft.com/display/JTEST20252/Testing+and+Analysis+with+Gradle

=================================================

Collecting application coverage

Windows:

1. Build application and collect data necessary for monitoring
     ant -file jtest.xml clean jtest-monitor
     or
     mvnw clean package jtest:monitor    
     or
     gradlew clean assemble jtest-monitor
     
   Note: As a result you should get monitor.zip file.

2. Run the application and collect the coverage data
     
   a) Unpack the monitor.zip archive into the "demo" directory (the subdirectory "monitor" will be created).
      ant:
        Archive path: parasoft\jtest-monitor\monitor.zip
      mvn:
        Archive path: target\jtest\monitor\monitor.zip
      gradle:
        Archive path: build\jtest\monitor.zip
     
   b) Run agent.bat
      cd monitor
      agent.bat
      cd ..
      
   c) Run the application using the Java VM argument generated in point b)
      ant
        java -cp demo.jar [paste argument generated in point b] examples.stackmachine.RunnableStackMachine
      mvn:
        java -cp target\Demo.jar [paste argument generated in point b] examples.stackmachine.RunnableStackMachine
      gradle:
        java -cp build\libs\demo.jar [paste argument generated in point b] examples.stackmachine.RunnableStackMachine

   d) Perform several actions using "Stack Machine Example" application:
      - Insert 123 number into "Input" field
      - press "push" button 5 times
      - press "+", "-", "x" and "/" buttons
      - exit application

3. Generate the coverage report

     ..\..\jtestcli.exe -config "builtin://Calculate Application Coverage" -staticcoverage monitor\static_coverage.xml -runtimecoverage monitor\runtime_coverage

     Coverage details are available in report.html

UNIX:

1. Build the application and collect the data necessary for monitoring
     ant -file jtest.xml clean jtest-monitor
     or
     ./mvnw clean package jtest:monitor    
     or
     ./gradlew clean build jtest-monitor
     
   Note: As a result you should get monitor.zip file.

2. Run the application and collect the coverage data
   
   a) Unpack monitor.zip archive into demo directory (subdirectory monitor will be created)
      ant:
        unzip ./parasoft/jtest-monitor/monitor.zip
      mvn: 
        unzip ./target/jtest/monitor/monitor.zip
      gradle:
        unzip ./build/jtest/monitor.zip
     
   b) Run agent.sh
      ./monitor/agent.sh
      
   c) Run application using Java VM argument generated in point b)
      ant
        java -cp ./demo.jar [paste argument generated in point b] examples.stackmachine.RunnableStackMachine
      mvn:
        java -cp ./target/Demo.jar [paste argument generated in point b] examples.stackmachine.RunnableStackMachine
      gradle:
        java -cp ./build/libs/demo.jar [paste argument generated in point b] examples.stackmachine.RunnableStackMachine

   d) Perform few actions using "Stack Machine Example" application
      - Insert 123 number into "Input" field
      - press "push" button 5 times
      - press "+", "-", "x" and "/" buttons
      - exit application

3. Generate coverage report

     ../../jtestcli -config "builtin://Calculate Application Coverage" -staticcoverage ./monitor/static_coverage.xml -runtimecoverage ./monitor/runtime_coverage

     Coverage details are available in report.html


For more information see https://docs.parasoft.com/display/JTEST20252/Application+Coverage

=================================================

UTA Bulk Creation

1. Configure desired settings for UTA Bulk Creation in the jtestcli.properties file.

    For more information see:
    https://docs.parasoft.com/display/JTEST20252/Unit+Test+Creation+Settings

2. Set up version control in advance to easily review and manage the changes.

    For more information, see:
    https://docs.parasoft.com/display/JTEST20252/Source+Control+Settings

3. Run the jtest:jtest goal with the "builtin://Create Unit Tests" configuration and specify the desired scope using the "-Djtest.include" parameter.

    ./mvnw jtest:jtest -Djtest.config="builtin://Create Unit Tests" -Djtest.settings="jtestcli.properties" [selected scope from the next step]
    or
    ./gradlew jtest -Djtest.config="builtin://Create Unit Tests" -Djtest.settings="jtestcli.properties" [selected scope from the next step]

4. Set the scope using "-Djtest.include" or "-Djtest.includes":

    - To generate tests for a package, use the following -Djtest.include pattern:

        -Djtest.include=/examples/mock/**

    - To generate tests for an absolute path to a Java file, use a "path:" prefix:

        -Djtest.include=path:/[absolute path to]/demo/src/main/java/examples/mock/PathUtil.java

    - To generate tests for multiple paths or patterns, collect all entries in an includes.lst file and provide its absolute path:

        -Djtest.include=path:/[absolute path to]/includes.lst

        Alternatively, list all includes directly in the "-Djtest.includes" parameter value:

        -Djtest.includes="/examples/eval/**,path:[absolute path to]/demo/src/main/java/examples/nbank/Account.java"

For more information see:
https://docs.parasoft.com/display/JTEST20252/Unit+Test+Creation
https://docs.parasoft.com/display/JTEST20252/Running+Unit+Test+Creation

