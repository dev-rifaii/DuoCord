whoami
pwd
cat /root/.bashrc
echo "--------------------------------------------------------------------------------"
source /root/.bashrc
echo "$REDIS_PORT"

nohup java -jar /tmp/DuoCord-0.1.jar dev.rifaii.Main
