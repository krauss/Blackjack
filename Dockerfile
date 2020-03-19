FROM fedora:30
RUN mkdir -p /home/blackjack/src && mkdir -p /home/blackjack/resources
COPY ./src /home/blackjack/src 
COPY ./resources /home/blackjack/resources
WORKDIR /home/blackjack
RUN dnf install java-latest-openjdk java-latest-openjdk-devel -y
RUN javac -d bin -cp src:resources/libs/sqlite/*:resources/libs/miglayout/*:resources/libs/flatlaf/* src/org/krauss/main/Main.java
RUN chmod +x -R bin/
CMD ["java", "-cp", "bin:resources/libs/sqlite/*:resources/libs/miglayout/*:resources/libs/flatlaf/*", "org.krauss.main.Main"] 