FROM openjdk:8
ADD https://github.com/krauss/blackjack/tree/master/src/au/com/eca/assignment ./tmp/
WORKDIR /tmp/
RUN javac -cp resources/External_Libs/sqlite/sqlite-jdbc-3.16.1.jar:resources/External_Libs/miglayout/miglayout-4.0.jar:resources/External_Libs/miglayout/miglayout-4.0-swing.jar:resources/External_Libs/miglayout/miglayout-4.0-swt.jar ./src/au/com/eca/assignment/*/*.java
CMD ["java", "BJMain"]