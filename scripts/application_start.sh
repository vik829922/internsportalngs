#!/bin/bash
 
echo "Starting the Spring Boot application..."
java -jar /home/ec2-user/my-app/02_MINI-PROJECT-0.0.1-SNAPSHOT.jar > /home/ec2-user/my-app/app.log 2>&1 &
 