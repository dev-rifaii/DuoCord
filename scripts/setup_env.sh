DISCORD_BOT_KEY=$(aws ssm get-parameters --region us-east-1 --names /duocord/DISCORD_BOT_KEY --with-decryption --query Parameters[0].Value --output text)
export DISCORD_BOT_KEY=$DISCORD_BOT_KEY
echo "export DISCORD_BOT_KEY=$DISCORD_BOT_KEY" >> /root/.bashrc && source /root/.bashrc

REDIS_HOST=$(aws ssm get-parameters --region us-east-1 --names /duocord/REDIS_HOST --with-decryption --query Parameters[0].Value --output text)
echo "$REDIS_HOST"
echo "export REDIS_HOST=$REDIS_HOST" >> /root/.bashrc && source /root~/.bashrc

REDIS_PORT=$(aws ssm get-parameters --region us-east-1 --names /duocord/REDIS_PORT --with-decryption --query Parameters[0].Value --output text)
echo "$REDIS_PORT"
echo "export REDIS_PORT=$REDIS_PORT" >> /root/.bashrc && source /root/.bashrc

REDIS_PASS=$(aws ssm get-parameters --region us-east-1 --names /duocord/REDIS_PASS --with-decryption --query Parameters[0].Value --output text)
echo "export REDIS_PASS=$REDIS_PASS" >> /root/.bashrc && source /root/.bashrc
