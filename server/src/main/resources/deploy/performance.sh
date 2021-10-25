echo ">>>>>>>>>>>>>>> tyf prod server start deploy"
CURRENT_PID=$(ps -ef | grep java | grep tyf-server* | awk '{print $2}')
if [ -z $CURRENT_PID ]; then
	echo ">>>>>>>>>>>>>>> application is not running"
else
	echo ">>>>>>>>>>>>>>> kill -9 current application"
kill -9 $CURRENT_PID
sleep 10
fi
echo ">>>>>>>>>>>>>>> new application is deployed successfully"
rm -rf /home/ubuntu/tyf/logs/tyf-test.log
nohup java -jar -Dspring.profiles.active=performance -Dlogging.slack.webhook-uri=abcdef /home/ubuntu/tyf/deploy/tyf-server-0.0.1-SNAPSHOT.jar >> /home/ubuntu/tyf/logs/tyf-test.log &
