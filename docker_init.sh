/home/Champagne_Shop/glassfish5/bin/asadmin start-domain
# /home/Champagne_Shop/glassfish5/bin/asadmin undeploy Champagne_Shop-1.0-SNAPSHOT;
# /home/Champagne_Shop/glassfish5/bin/asadmin --interactive=true --host localhost --port 4848 --user admin --contextroot Champagne_Shop deploy /home/ion/Champagne_Shop/target/Champagne_Shop-1.0-SNAPSHOT.war
/home/Champagne_Shop/glassfish5/bin/asadmin --interactive=true --host localhost --port 4848 --user admin deploy --contextroot Champagne_Shop /home/Champagne_Shop/target/Champagne_Shop-1.0-SNAPSHOT.war

/bin/bash