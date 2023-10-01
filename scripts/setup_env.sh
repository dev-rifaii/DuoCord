whoami
if [ -z "$DISCORD_BOT_KEY" ]; then
    DISCORD_BOT_KEY=$(aws ssm get-parameters --region us-east-1 --names /duocord/DISCORD_BOT_KEY --with-decryption --query Parameters[0].Value --output text)
    echo "export DISCORD_BOT_KEY=$DISCORD_BOT_KEY" >> /root/.bashrc && source /root/.bashrc
  else
    echo "DISCORD_BOT_KEY is already set"
fi

if [ -z "$REDIS_HOST" ]; then
    REDIS_HOST=$(aws ssm get-parameters --region us-east-1 --names /duocord/REDIS_HOST --with-decryption --query Parameters[0].Value --output text)
    echo "$REDIS_HOST"
    echo "export REDIS_HOST=$REDIS_HOST" >> /root/.bashrc && source /root/.bashrc
  else
    echo "REDIS_HOST is already set"
fi

if [ -z "$REDIS_PORT" ]; then
    REDIS_PORT=$(aws ssm get-parameters --region us-east-1 --names /duocord/REDIS_PORT --with-decryption --query Parameters[0].Value --output text)
    echo "$REDIS_PORT"
    echo "export REDIS_PORT=$REDIS_PORT" >> /root/.bashrc && source /root/.bashrc
  else
    echo "REDIS_PORT is already set"
fi

if [ -z "$REDIS_PASS" ]; then
    REDIS_PASS=$(aws ssm get-parameters --region us-east-1 --names /duocord/REDIS_PASS --with-decryption --query Parameters[0].Value --output text)
    echo "export REDIS_PASS=$REDIS_PASS" >> /root/.bashrc && source /root/.bashrc
  else
    echo "REDIS_PASS is already set"
fi
