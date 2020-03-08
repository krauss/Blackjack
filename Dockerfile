FROM fedora:30
RUN mkdir ~/BlackJack
COPY . ~/BlackJack
WORKDIR ~/BlackJack
RUN dnf install java-latest-openjdk -y
CMD ["java", "-cp", "bin:resources/External_Libs/sqlite/*:resources/External_Libs/miglayout/*", "com.eca.assignment.main.BJMain"]