FROM ubuntu

WORKDIR /home/Champagne_Shop


# Copio il file pom.xml, il codice, init.sh e Glassfish
COPY pom.xml /home/Champagne_Shop/pom.xml
COPY src /home/Champagne_Shop/src
COPY glassfish5 /home/Champagne_Shop/glassfish5
COPY docker_init.sh /home/Champagne_Shop/init.sh

RUN apt-get update

# installazione software necesssari
RUN apt-get install -y maven
RUN apt-get install -y openjdk-8-jdk
RUN apt-get remove -y openjdk-11-jre-headless #altrimenti problemi con glassfish

# Java config
ENV JAVA_HOME /usr/lib/jvm/java-8-openjdk-amd64
ENV PATH $PATH:$JAVA_HOME/bin

# database config
ENV DATABASE_HOST db
RUN echo DATABASE_HOST: $DATABASE_HOST

RUN echo java home: $JAVA_HOME

# Compilazione
# Non posso eseguire i test senza il db
RUN mvn package -DskipTests

# EXPOSE 8080

#Avvio il container con Glassfish e deployo l'applicazione
# CMD ["mvn", "package"]
CMD ["/bin/bash", "/home/Champagne_Shop/init.sh"]



