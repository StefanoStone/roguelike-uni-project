FROM openjdk:11

RUN apt-get -y update
RUN apt-get -y upgrade

RUN apt-get install unzip
RUN apt-get install curl
RUN apt-get install -y libxrender1 libxtst6 libxi6

RUN curl -L -H "Authorization: token {{token}}" \
    https://api.github.com/repos/StefanoStone/roguelike-uni-project/actions/artifacts/35366048/zip \
    --output ./jar-artifact.zip

RUN unzip jar-artifact.zip && rm jar-artifact.zip

CMD ["java", "-jar", "ZeldaRoguelikeGame-1.0.jar"]