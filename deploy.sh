#cd /usr/local/tomcat
#bin/shutdown.sh
#cd webapps
#rm -rf ROOT/
#cp ~/build/test-app.war ./ROOT.war
#cd ..
#bin/startup.sh

cd /home/ec2-user/
/home/ec2-user/apache-tomcat-8.5.57/bin/shutdown.sh
cd /home/ec2-user/apache-tomcat-8.5.57/webapps
rm -rf ROOT/ ./ROOT.war
cp ~/build/test-app.war ./ROOT.war
cd ..
/home/ec2-user/apache-tomcat-8.5.57/bin/startup.sh
