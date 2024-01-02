# microservicepausemodel
Micro-services pause time model effect on latency

the models estimates different types of expected latency that a microservices call chain will impose on the application. This latency is built in an is in addition to the latency needed to complete each end user request.

mvn javafx:run <--- runs the currently configured Application

There are current 4 different applications which will be merged in the future
To configure the run of an application, uncomment it in the pom and comment out the current target


Useful IntelliJ/OSX JVM argument configurations

--module-path $HOME$/.m2/repository/org/openjfx/javafx-base/19/javafx-base-19-mac.jar:$HOME$/.m2/repository/org/openjfx/javafx-graphics/19/javafx-graphics-19-mac.jar:$HOME$.m2/repository/org/openjfx/javafx-controls/19/javafx-controls-19-mac.jar --add-modules javafx.controls
