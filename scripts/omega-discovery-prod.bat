@echo off
call setenv.bat
java -XX:+UseG1GC -Xmx2G -jar omega-discovery-service-@project.version.jar --spring.config.location=config/application.properties