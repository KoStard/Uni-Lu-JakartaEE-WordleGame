# Wordle Game

## Description
- This is a simple implementation of the wordle game using Jakarta EE and JSF

## How to run
- You need to have a docker container running with the wildfly image there
```
cd .\docker-wildfly-mariadb\
docker compose up -d
```

- Then you need to deploy this game to the wildfly container
```
cd .\WordleGame\
mvn clean package wildfly:deploy
```