FROM openjdk:15
RUN mkdir /home/blackjack
COPY ./blackjack.jar /home/blackjack
WORKDIR /home/blackjack
RUN chmod +x blackjack.jar
CMD ["java", "-jar", "/home/blackjack/blackjack.jar"]