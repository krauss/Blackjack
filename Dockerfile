FROM openjdk:8-jdk
RUN mkdir /tmp/BlackJack
ADD . /tmp/BlackJack
WORKDIR /tmp/BlackJack
ENV CLASSPATH /tmp/BlackJack/src
RUN export CLASSPATH=/tmp/BlackJack/src
RUN echo "COMPILING ALL THE SOURCE FILES!"
RUN javac -cp resources/External_Libs/sqlite/*:resources/External_Libs/miglayout/*:src src/au/com/eca/assignment/*/*.java
CMD ["java", "-cp", "resources/External_Libs/sqlite/*:resources/External_Libs/miglayout/*:src", " au.com.eca.assignment.main.BJMain"]