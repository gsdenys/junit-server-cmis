# JUnit Runner for CMIS


[![Build Status](https://travis-ci.org/gsdenys/junit-server-cmis.svg?branch=master)](https://travis-ci.org/gsdenys/junit-server-cmis)   [![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

This project has was developed to help starts a [CMIS](https://docs.oasis-open.org/cmis/CMIS/v1.1/CMIS-v1.1.html) InMemory Server ([that can be better described here](https://chemistry.apache.org/java/developing/repositories/dev-repositories-inmemory.html)) in a JUnit Test Case _Class_. 

Using this module you can just annotate the Test Class with the **@RunWith(CmisInMemoryRunner.class)** Java annotation and the CMIS InMemory will starts itself before first method execution and stop after last method execution.

As this module use the CMIS InMemory, all content inserted, deleted or modifyed in a executed class cannot be used in annother. Cause in the server shutdow all data will be lost. 


## Build

To create your own **JUnit Runner for CMIS** build, you can execute the follow commands.

To clone project and access the project directory execute:

    git clone https://github.com/gsdenys/junit-server-cmis.git
    
    cd junit-server-cmis

To generate the project dist (__.jar__) you can use a [graddle](https://gradle.org/) local installation, executing the follow command:

    gradle build
    
In contrast of local gradle you also can use the wrapper way executing the follow command:

    gradlew build
 
this will download and install gradle locally and use it to build the project. After build the __.jar__ file will stay at _<project_home>/build/junit-server-cmis-\<version>.jar_


## Usage

To use the JUnit Runner for CMIS you need to import the required libs.

__with Gradle__
```json
testCompile group: 'com.github.gsdenys', name: 'junit-server-cmis', version: '0.0.1'
```

__with Maven__
```xml
<dependency>
    <groupId>com.github.gsdenys</groupId>
    <artifactId>junit-server-cmis</artifactId>
    <version>0.0.1</version>
</dependency>
```

after import, create the test case for your application. To stars the CMIS server you just want to add the __@RunWith(CmisInMemoryRunner.class)__ annotation to your test class'.

```java
@RunWith(CmisInMemoryRunner.class)
public class UsageTest {
    @Test
    public void someTest() throws Exception {
        //TODO: Your test code here
    }
}
```
Once running the Junit Test Case will starts a Jetty server with a deployed CMIS InMemory Server at __localhost__ using a random port that can be descovery using the __CmisInMemoryRunner.cmisPort__ static variable.