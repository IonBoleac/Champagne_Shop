/home/ion/Champagne_Shop/glassfish5_new/bin/asadmin start-domain;

/home/ion/Champagne_Shop/glassfish5_new/bin/asadmin --interactive=true --host localhost --port 4848 --user admin deploy --contextroot Champagne_Shop /home/ion/Champagne_Shop/target/Champagne_Shop-1.0-SNAPSHOT.war;

/bin/bash