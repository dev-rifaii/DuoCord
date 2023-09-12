FROM openjdk:17

WORKDIR bot

COPY . ./

RUN chmod +x gradlew
RUN sed -i 's/\r$//' gradlew
RUN ./gradlew build

CMD ["java", "-cp", "./build/libs/DuoCord-0.1.jar", "dev.rifaii.Main"]