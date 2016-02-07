TempoUI -- Modern UIs for VDM
===

General Information
---
TempoUI is a library for building HTML5-based UIs for VDM models using DukeScript
and Overture. 

To install TempoUI, check it out and build with Maven;  `mvn install -f tempoui/pom.xml`.

Usage
---
To use TempoUI, create a DukeScript Maven project and add the following dependency:
```
<dependency>
  <groupId>org.overturetool</groupId>
  <artifactId>tempoui</artifactId>
  <version>1.0-SNAPSHOT</version>
</dependency>
```
For more, see https://github.com/overturetool/tempo-ui/wiki/How-To-Use

Development
---
This project contains several subdirectories:
* The main TempoUI library is found in `tempoui`
* Build scripts are in `scripts`
* A demonstration example is available in `example`
* The `demo1` folder has an old proof-of-concept demo of Overture-DukeScript
  integration.

