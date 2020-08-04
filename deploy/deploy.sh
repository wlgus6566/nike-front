#!/bin/bash

export CATALINA_PID=/home/sinbi/tomcat8/bin/catalina.pid

/home/sinbi/tomcat8/bin/catalina.sh stop -force

killall java

for((i=10;i>=1;i--)); do sleep 1; echo "Tomcat stop count down : $i "; done;

rm -rf /home/sinbi/tomcat8/webapps/ROOT*
rm -rf /home/sinbi/tomcat8/work/Catalina/*

#aws s3 cp s3://sinbi-deploy-prod/sinbi-0.0.1-SNAPSHOT.war /home/sinbi/tomcat8/webapps/ROOT.war
#cp /home/sinbi/deploy/sinbi-0.0.1-SNAPSHOT.war /home/sinbi/tomcat8/webapps/ROOT.war
unzip /home/sinbi/deploy/sinbi-0.0.1-SNAPSHOT.war -d /home/sinbi/tomcat8/webapps/ROOT

chown ec2-user.ec2-user -R /home/sinbi/tomcat8/webapps/*
chown ec2-user.ec2-user -R /home/sinbi/tomcat8/logs/*

aws s3 cp /home/sinbi/tomcat8/webapps/ROOT/WEB-INF/classes/static/ s3://sinbi-prod-static/ --recursive

ln -sf /usr/share/zoneinfo/Asia/Seoul /etc/localtime

# RUN SCOUTER HOST
cd /home/sinbi/scouter/scouter/agent.host/
sudo -u ec2-user ./host.sh

sudo -u ec2-user /home/sinbi/tomcat8/bin/setenv.sh
sudo -u ec2-user /home/sinbi/tomcat8/bin/catalina.sh start