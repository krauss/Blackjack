##FROM openjdk:8
FROM dockerfile/java:oracle-java8
##COPY . ../src/au/com/eca/assignment/*
ADD https://github.com/krauss/blackjack/tree/master/src/au/com/eca/assignment ./tmp/
##WORKDIR ./tmp/
RUN javac ./ ./tmp/*/*.java
CMD ["java", "BJMain"]