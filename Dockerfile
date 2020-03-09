FROM fedora:30
RUN mkdir -p /home/blackjack/src && mkdir -p /home/blackjack/resources
COPY ./src /home/blackjack/src 
COPY ./resources /home/blackjack/resources 
WORKDIR /home/blackjack
RUN dnf install java-latest-openjdk java-latest-openjdk-devel -y
RUN javac -d bin -cp src:resources/External_Libs/sqlite/*:resources/External_Libs/miglayout/* src/com/eca/assignment/main/BJMain.java
RUN chmod +x -R bin/
CMD ["java", "-cp", "bin:resources/External_Libs/sqlite/*:resources/External_Libs/miglayout/*", "com.eca.assignment.main.BJMain"] 