/bin/bash

/home/Champagne_Shop/glassfish5/bin/asadmin stop-domain domain1;

/home/Champagne_Shop/glassfish5/bin/asadmin start-domain domain1;
/home/Champagne_Shop/glassfish5/bin/asadmin --interactive=true --host localhost --port 4848 --user admin deploy --contextroot Champagne_Shop /home/Champagne_Shop/target/Champagne_Shop-1.0-SNAPSHOT.war
