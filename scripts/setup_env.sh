DISCORD_BOT_KEY=$(aws ssm get-parameters --region us-east-1 --names /duocord/DISCORD_BOT_KEY --with-decryption --query Parameters[0].Value --output text)
export DISCORD_BOT_KEY=$DISCORD_BOT_KEY

REDIS_HOST=$(aws ssm get-parameters --region us-east-1 --names /duocord/REDIS_HOST --with-decryption --query Parameters[0].Value --output text)
export REDIS_HOST=$REDIS_HOST

REDIS_PORT=$(aws ssm get-parameters --region us-east-1 --names /duocord/REDIS_PORT --with-decryption --query Parameters[0].Value --output text)
export REDIS_PORT=$REDIS_PORT

REDIS_PASS=$(aws ssm get-parameters --region us-east-1 --names /duocord/REDIS_PASS --with-decryption --query Parameters[0].Value --output text)
export REDIS_PASS=$REDIS_PASS
