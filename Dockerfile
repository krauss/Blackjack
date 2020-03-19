FROM fedora:30
RUN mkdir -p /home/blackjack/src && mkdir -p /home/blackjack/res
COPY ./src /home/blackjack/src 
COPY ./res /home/blackjack/res
WORKDIR /home/blackjack
RUN dnf install java-latest-openjdk java-latest-openjdk-devel -y
RUN javac -d bin -cp src:res/libs/sqlite/*:res/libs/miglayout/*:res/libs/flatlaf/* src/org/krauss/main/Main.java
RUN chmod +x -R bin/
CMD ["java", "-cp", "bin:res/libs/sqlite/*:res/libs/miglayout/*:res/libs/flatlaf/*", "org.krauss.main.Main"] 