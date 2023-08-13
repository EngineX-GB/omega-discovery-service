@echo off
call setenv.bat
java -XX:+UseG1GC -Xmx2G -jar omega-discovery-service-0.0.1-SNAPSHOT.jar --spring.config.location=config/application.properties