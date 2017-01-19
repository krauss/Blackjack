FROM openjdk:8
##COPY . ../src/au/com/eca/assignment/*
ADD https://github.com/krauss/blackjack/tree/master/src/au/com/eca/assignment ./tmp/
WORKDIR ./tmp/
RUN javac ./main/BJMain.java
CMD ["java", "BJMain"]