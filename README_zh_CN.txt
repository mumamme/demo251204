关于 Ant、Maven 和 Gradle 集成的说明，请访问:
https://docs.parasoft.com/display/JTEST20252/Integrating+with+Build+Systems

本文档介绍了如何使用 Jtest 执行静态分析、运行测试以及收集
覆盖率信息 - 使用产品中附带的 "demo" 项目。

请注意，此项目的分析是通过 [INSTALL]/examples/demo 目录下 demo.properties 文件中指定的设置配置的。
demo.properties 文件供 Ant、Maven 和 Gradle 构建脚本使用。以下命令行示例需在 "demo" 项目目录下执行。

PowerShell 用户需注意:
由于 PowerShell 解释特殊字符的方式，传递名称中包含点和前导破折号的参数有时可能会导致意外行为。
为确保顺利执行，请使用以下方法之一:
A.  使用反引号 "`" 来避免前导破折号。
    示例:
    `-Djtest.config="builtin://Demo Configuration"

B.  用双引号将参数名和值括起来。
    示例:
    "-Djtest.config=builtin://Demo Configuration"


前提条件
-------------------------------------------------
1. 在 Jtest 安装目录下的 jtestcli.properties 文件中设置 Jtest 许可证。


Jtest 数据文件
-------------------------------------------------
1. 运行 "Recommended Rules" 测试配置，以直接执行静态分析 (无需任何构建系统) - 使用以下命令:
   Windows:
     ..\..\jtestcli.exe -config "builtin://Recommended Rules" -data demo.data.json -report report
   UNIX:
     ../../jtestcli -config "builtin://Recommended Rules" -data demo.data.json -report report


Ant
-------------------------------------------------
1. 请确保您的路径上存在可用的 "ant"。

2. 运行 "Demo Configuration"，以执行静态分析，并收集单元测试的覆盖率:

     ant -file jtest.xml -Djtest.config="builtin://Demo Configuration"

   将构建 "demo" 项目并运行单元测试。Ant 的 Jtest 插件收集源代码编译数据和包含覆盖率的单元测试结果，以执行分析并生成报告。

   注意:

   若仅运行静态分析，请使用以下命令:

     ant -file jtest.xml jtest-sae

   若仅运行单元测试，请使用以下命令:

     ant -file jtest.xml jtest-utc

   jtest.xml 中指定的配置 - 请查阅 "jtest"、"jtest-sae" 或 "jtest-utc" 目标。


Maven
-------------------------------------------------
1. 请根据 Jtest 用户指南配置您的 Maven 设置:
   https://docs.parasoft.com/display/JTEST20252/Configuring+the+Jtest+Plugin+for+Maven

2. 运行 "Demo Configuration" 以执行静态分析，并收集用于单元测试的覆盖率:

     mvnw  clean test-compile jtest:agent test jtest:jtest -Djtest.config="builtin://Demo Configuration"

   将构建 "demo" 项目，并运行单元测试。Maven 的 Jtest 插件收集源代码编译数据和
   包含覆盖率的测试结果，以执行分析并生成报告。

   注意:

   若仅运行静态分析，请使用以下命令:

     mvnw jtest:jtest

   默认使用 "Recommended Rules" 测试配置。

   若仅运行单元测试，请使用以下命令:

     mvnw clean test-compile jtest:agent test jtest:jtest -Djtest.config="builtin://Unit Tests"


Gradle
-------------------------------------------------
注意: 其中 "demo" 项目使用 JUnit 5，需要 Gradle 4.6 或更高版本。

1. 请配置 Jtest 安装包或添加所需设置到构建脚本的 "jtest" 块。

2. 运行 "Demo Configuration"，以执行静态分析，并收集用于单元测试的覆盖率:

     gradlew clean jtest-agent test jtest -Djtest.config="builtin://Demo Configuration"

   将构建 "demo" 项目，并运行单元测试。Gradle 的 Jtest 插件将收集源代码编译数据和测试结果，以执行和生成报告。

   注意:

   若仅运行静态分析，请使用以下命令:

     gradlew clean assemble jtest

   默认使用 "Recommended Rules" 配置。

   若仅运行单元测试，请使用以下命令:

     gradlew clean jtest-agent test jtest -Djtest.config="builtin://Unit Tests"


=================================================

测试影响分析

Maven 和 Gradle 支持测试影响分析。

Windows:

1. 使用 Maven 或 Gradle 运行测试，以收集关于测试和覆盖率的基线数据:
   mvnw clean test-compile jtest:agent test jtest:jtest -Djtest.config="builtin://Unit Tests" -Djtest.report=tia
   或
   gradlew clean jtest-agent test jtest -Djtest.config="builtin://Unit Tests" -Djtest.report=tia

   注意: 因此，report.xml 和 cover .xml 文件将在 'tia' 子文件夹中创建。 

2. 修改测试范围中的源文件:
   src\main\java\examples\mock\FileExample.java

3. 运行以下命令来执行受更改影响的测试:
   mvnw clean tia:affected-tests test -Djtest.referenceCoverageFile=tia/coverage.xml -Djtest.referenceReportFile=tia/report.xml
   或
   gradlew clean affectedTests test -Djtest.referenceCoverageFile=tia/coverage.xml -Djtest.referenceReportFile=tia/report.xml

   不受代码修改影响的测试不会执行。

UNIX:

1. 使用 Maven 或 Gradle 运行测试，以收集测试和覆盖率的基线数据:
   ./mvnw clean test-compile jtest:agent test jtest:jtest -Djtest.config="builtin://Unit Tests" -Djtest.report=tia
   或
   ./gradlew clean jtest-agent test jtest -Djtest.config="builtin://Unit Tests" -Djtest.report=tia

   注意: 因此，report.xml 和 coverage.xml 文件将在 'tia' 子文件夹中创建。

2. 修改测试范围中的源文件:
   src/main/java/examples/mock/FileExample.java

3. 运行以下命令来执行受更改影响的测试:
   ./mvnw clean tia:affected-tests test -Djtest.referenceCoverageFile=tia/coverage.xml -Djtest.referenceReportFile=tia/report.xml
   或
   ./gradlew clean affectedTests test -Djtest.referenceCoverageFile=tia/coverage.xml -Djtest.referenceReportFile=tia/report.xml

   不受代码修改影响的测试不会执行。


更多信息，请参阅:
   https://docs.parasoft.com/display/JTEST20252/Testing+and+Analysis+with+Maven
   https://docs.parasoft.com/display/JTEST20252/Testing+and+Analysis+with+Gradle

=================================================

收集应用程序覆盖率

Windows:

1. 构建应用程序并收集监控所需的数据
     ant -file jtest.xml clean jtest-monitor
     或
     mvnw clean package jtest:monitor    
     或
     gradlew clean assemble jtest-monitor
     
   注意: 执行后应生成 monitor.zip 文件。

2. 运行应用程序并收集覆盖率数据
     
   a) 将 monitor.zip 归档文件解压到 "demo" 目录中 (将创建子目录 "monitor")。
      ant:
        Archive path: parasoft\jtest-monitor\monitor.zip
      mvn:
        Archive path: target\jtest\monitor\monitor.zip
      gradle:
        Archive path: build\jtest\monitor.zip
     
   b) 运行 agent.bat
      cd monitor
      agent.bat
      cd ..
      
   c) 使用 b) 中生成的 Java VM 参数运行应用程序
      ant
        java -cp demo.jar [粘贴在 b 点生成的参数] examples.stackmachine.RunnableStackMachine
      mvn:
        java -cp target\Demo.jar [粘贴在 b 点生成的参数] examples.stackmachine.RunnableStackMachine
      gradle:
        java -cp build\libs\demo.jar [粘贴在 b 点生成的参数] examples.stackmachine.RunnableStackMachine

   d) 使用 "Stack Machine Example" 应用程序执行多个操作:
      - 将数字 123 插入到 "Input" 字段中
      - 按 "push" 按钮 5 次
      - 按 "+"、"-"、"x" 和 "/" 按钮
      - 退出应用程序

3. 生成覆盖率报告

     ..\..\jtestcli.exe -config "builtin://Calculate Application Coverage" -staticcoverage monitor\static_coverage.xml -runtimecoverage monitor\runtime_coverage

     覆盖率的详细信息可以在 report.html 中查看

UNIX:

1. 构建应用程序并收集监控所需的数据
     ant -file jtest.xml clean jtest-monitor
     或
     ./mvnw clean package jtest:monitor    
     或
     ./gradlew clean build jtest-monitor
     
   注意: 因此，您应该获得 monitor.zip 文件。

2. 运行应用程序并收集覆盖率数据
   
   a) 将 monitor.zip 归档文件解压到 demo 路径 (将创建子目录 monitor)
      ant:
        unzip ./parasoft/jtest-monitor/monitor.zip
      mvn: 
        unzip ./target/jtest/monitor/monitor.zip
      gradle:
        unzip ./build/jtest/monitor.zip
     
   b) 运行 agent.sh
      ./monitor/agent.sh
      
   c) 使用 b) 中生成的 Java VM 参数运行应用程序
      ant
        java -cp ./demo.jar [粘贴在 b 点生成的参数] examples.stackmachine.RunnableStackMachine
      mvn:
        java -cp ./target/Demo.jar [粘贴在 b 点生成的参数] examples.stackmachine.RunnableStackMachine
      gradle:
        java -cp ./build/libs/demo.jar [粘贴在 b 点生成的参数] examples.stackmachine.RunnableStackMachine

   d) 使用 "Stack Machine Example" 应用程序执行少许操作
      - 将数字 123 插入到 "Input" 字段中
      - 按 "push" 按钮 5 次
      - 按 "+"、"-"、"x" 和 "/" 按钮
      - 退出应用程序

3. 生成覆盖率报告

     ../../jtestcli -config "builtin://Calculate Application Coverage" -staticcoverage ./monitor/static_coverage.xml -runtimecoverage ./monitor/runtime_coverage

     覆盖率的详细信息可以在 report.html 中查看


更多信息，请参阅 https://docs.parasoft.com/display/JTEST20252/Application+Coverage

=================================================

UTA 批量创建

1. 在 jtestcli.properties 文件中配置 UTA 批量创建所需的设置。

    更多信息，请参阅:
    https://docs.parasoft.com/display/JTEST20252/Unit+Test+Creation+Settings

2. 提前配置版本控制，以便查看和管理变更。

    更多信息，请参阅:
    https://docs.parasoft.com/display/JTEST20252/Source+Control+Settings

3. 使用 "builtin://Create Unit Tests" 配置运行 jtest:jtest 目标，并使用 "-Djtest.include" 参数指定所需范围。

    ./mvnw jtest:jtest -Djtest.config="builtin://Create Unit Tests" -Djtest.settings="jtestcli.properties" [下一步中选择的范围]
    or
    ./gradlew jtest -Djtest.config="builtin://Create Unit Tests" -Djtest.settings="jtestcli.properties" [下一步中选择的范围]

4. 使用 "-Djtest.include" 或 "-Djtest.includes" 设置范围:

    - 若要为某个包生成测试，需使用以下 -Djtest.include 模式:

        -Djtest.include=/examples/mock/**

    - 若要为 Java 文件的绝对路径生成测试，需使用 "path:" 前缀:

        -Djtest.include=path:/[绝对路径]/demo/src/main/java/examples/mock/PathUtil.java

    - 若要为多个路径或模式生成测试，需将所有条目收集到 includes.lst 文件中并提供其绝对路径:

        -Djtest.include=path:/[绝对路径]/includes.lst

        或者，也可以直接在 "-Djtest.includes" 参数值中列出所有包含项:

        -Djtest.includes="/examples/eval/**,path:[绝对路径]/demo/src/main/java/examples/nbank/Account.java"

更多信息，请参阅:
https://docs.parasoft.com/display/JTEST20252/Unit+Test+Creation
https://docs.parasoft.com/display/JTEST20252/Running+Unit+Test+Creation

