FROM openjdk:8-jdk
ADD . /tmp
WORKDIR /tmp/
ENV CLASSPATH /tmp/src
RUN mkdir ./bin/
RUN javac -d ./bin -cp resources/External_Libs/sqlite/*:resources/External_Libs/miglayout/*:src src/au/com/eca/assignment/*/*.java
CMD ["java", "-cp", "resources/External_Libs/sqlite/*:resources/External_Libs/miglayout/*:src au.com.eca.assignment.main.BJMain"]