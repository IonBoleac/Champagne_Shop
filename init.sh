#!/bin/bash

/home/ion/Champagne_Shop/glassfish5/bin/asadmin stop-domain
/home/ion/Champagne_Shop/glassfish5/bin/asadmin start-domain
/home/ion/Champagne_Shop/glassfish5/bin/asadmin undeploy Champagne_Shop-1.0-SNAPSHOT
# /home/ion/Champagne_Shop/glassfish5/bin/asadmin --interactive=true --host localhost --port 4848 --user admin --contextroot Champagne_Shop deploy /home/ion/Champagne_Shop/target/Champagne_Shop-1.0-SNAPSHOT.war
/home/ion/Champagne_Shop/glassfish5/bin/asadmin deploy /home/ion/Champagne_Shop/target/Champagne_Shop-1.0-SNAPSHOT.war
