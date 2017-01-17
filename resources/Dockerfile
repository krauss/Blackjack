FROM openjdk:7
COPY . /blackjack/*
WORKDIR /blackjack/
RUN javac /blackjack/src/au/com/eca/assignment/main/BJMain.java
CMD ["java", "BJMain"]