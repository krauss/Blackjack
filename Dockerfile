FROM openjdk:8-jdk
ADD . /tmp
WORKDIR /tmp/
ENV CLASSPATH /tmp/src
RUN echo "COMPILING ALL THE SOURCE FILES!"
RUN javac -cp resources/External_Libs/sqlite/*:resources/External_Libs/miglayout/*:src src/au/com/eca/assignment/*/*.java
RUN echo "BEGINING EXECUTION..."
#RUN java -cp resources/External_Libs/sqlite/*:resources/External_Libs/miglayout/*:src au.com.eca.assignment.main.BJMain
CMD ["java", "-cp", "resources/External_Libs/sqlite/*:resources/External_Libs/miglayout/*:src", " au.com.eca.assignment.main.BJMain"]