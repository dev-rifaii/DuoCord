DISCORD_BOT_KEY=$(aws ssm get-parameters --region us-east-1 --names /duocord/DISCORD_BOT_KEY --with-decryption --query Parameters[0].Value --output text)
export DISCORD_BOT_KEY=$DISCORD_BOT_KEY
echo "export DISCORD_BOT_KEY=$DISCORD_BOT_KEY" >> ~/.bashrc && source ~/.bashrc

REDIS_HOST=$(aws ssm get-parameters --region us-east-1 --names /duocord/REDIS_HOST --with-decryption --query Parameters[0].Value --output text)
echo "$REDIS_HOST"
echo "export REDIS_HOST=$REDIS_HOST" >> ~/.bashrc && source ~/.bashrc

REDIS_PORT=$(aws ssm get-parameters --region us-east-1 --names /duocord/REDIS_PORT --with-decryption --query Parameters[0].Value --output text)
echo "$REDIS_PORT"
echo "export REDIS_PORT=$REDIS_PORT" >> ~/.bashrc && source ~/.bashrc

REDIS_PASS=$(aws ssm get-parameters --region us-east-1 --names /duocord/REDIS_PASS --with-decryption --query Parameters[0].Value --output text)
echo "export REDIS_PASS=$REDIS_PASS" >> ~/.bashrc && source ~/.bashrc
