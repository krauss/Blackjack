FROM openjdk:8
ADD ../* ./tmp/
WORKDIR /tmp/
ENV CLASSPATH /tmp/src/au/com/eca/assignment/
RUN javac -cp resources/External_Libs/sqlite/sqlite-jdbc-3.16.1.jar:resources/External_Libs/miglayout/miglayout-4.0.jar:resources/External_Libs/miglayout/miglayout-4.0-swing.jar:resources/External_Libs/miglayout/miglayout-4.0-swt.jar ./src/au/com/eca/assignment/*/*.java
CMD ["java", ".main.BJMain"]