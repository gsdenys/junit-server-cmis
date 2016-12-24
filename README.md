# JUnit Runner for CMIS


[![Build Status](https://travis-ci.org/gsdenys/junit-server-cmis.svg?branch=master)](https://travis-ci.org/gsdenys/junit-server-cmis) [![Codacy Badge](https://api.codacy.com/project/badge/Coverage/23e85edc3bfd4323af0b671a4bb9f7b6)](https://www.codacy.com/app/gsdenys/junit-server-cmis?utm_source=github.com&utm_medium=referral&utm_content=gsdenys/junit-server-cmis&utm_campaign=Badge_Coverage) [![Codacy Badge](https://api.codacy.com/project/badge/Grade/23e85edc3bfd4323af0b671a4bb9f7b6)](https://www.codacy.com/app/gsdenys/junit-server-cmis?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=gsdenys/junit-server-cmis&amp;utm_campaign=Badge_Grade) [![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.gsdenys/junit-server-cmis/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.gsdenys/junit-server-cmis)

This project was developed to help starts a [CMIS](https://docs.oasis-open.org/cmis/CMIS/v1.1/CMIS-v1.1.html) InMemory Server ([described here](https://chemistry.apache.org/java/developing/repositories/dev-repositories-inmemory.html)) in a JUnit Test Case _Class_. 

Using this module you need just annotate the Test Class with the **@RunWith(CmisInMemoryRunner.class)** Java annotation and the CMIS InMemory will starts itself before first method execution and stop after the last method was executed.

As this module use the CMIS InMemory, all content inserted, deleted or modifyed in a executed class cannot be used in another, because on server shutdown all data will be lost. 


## Build

To create your own build on **JUnit Runner for CMIS**, you can execute the follow commands.

To clone project and access the project directory execute:

    git clone https://github.com/gsdenys/junit-server-cmis.git
    
    cd junit-server-cmis

To generate the project dist (__.jar__) you can use a [gradle](https://gradle.org/) local installation. Execute the follow command:

    gradle build -x signArchives
    
In contrast of local gradle you also can use the wrapper way executing the command:

    ./gradlew build -x signArchives
 
this will download and install gradle locally and use it to build the project. After build the __.jar__ file will be created at _<project_home>/build/junit-server-cmis-\<version>.jar_

__Notes:__ _Before execute the gradle build command, put in your env the __USER__ and __PASSWORD__ variable initialized with any value._ 

## Usage

To use the JUnit Runner for CMIS you need to import the required libs.

__with Gradle__

```
testCompile group: 'com.github.gsdenys', name: 'junit-server-cmis', version: '1.1.2'
```

__with Maven__

```xml
<dependency>
    <groupId>com.github.gsdenys</groupId>
    <artifactId>junit-server-cmis</artifactId>
    <version>1.1.2</version>
</dependency>
```

after import, create the test case for your application. To start the CMIS server you just want to add the __@RunWith(CmisInMemoryRunner.class)__ annotation to your test class'.

```java
@RunWith(CmisInMemoryRunner.class)
public class UsageTest {
    @Test
    public void someTest() throws Exception {
        //TODO: Your test code here
    }
}
```
Once running the Junit Test Case will starts a Jetty server with a deployed CMIS InMemory Server at __localhost__ using a random port that can be discovery using the __CmisInMemoryRunner.CMIS_PORT__ static variable.


Optionally, you can define the port that the server will be initiated using the annotation __@Configure__ (Version 1.1.1 or later). An use example of this annotation can be seen below.

```java
@RunWith(CmisInMemoryRunner.class)
@Configure(port = 9090)
public class UsageTest {
    @Test
    public void someTest() throws Exception {
        //TODO: Your test code here
    }
}
```
