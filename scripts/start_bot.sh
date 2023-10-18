function getSecret() {
  secretName="${1}"
  aws ssm get-parameters --region us-east-1 --names "/duocord/${secretName}" --with-decryption --query Parameters[0].Value --output text
}

DISCORD_BOT_KEY="$(getSecret DISCORD_BOT_KEY)" \
REDIS_HOST="$(getSecret REDIS_HOST)" \
REDIS_PORT="$(getSecret REDIS_PORT)" \
REDIS_PASS="$(getSecret REDIS_PASS)" \
nohup java -jar /tmp/DuoCord-0.1.jar dev.rifaii.Main &
