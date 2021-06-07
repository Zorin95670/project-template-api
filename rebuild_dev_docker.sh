#!/bin/bash

echo -e "\e[1m"
echo "----------------------------------"
echo -e "\e[92m"
echo "Destroy existing container(s)"
echo -e "\e[39m"
echo "----------------------------------"
echo -e "\e[0m"
docker container rm PROJECT_NAME_db_1 PROJECT_NAME_api_1

echo -e "\e[1m"
echo "----------------------------------"
echo -e "\e[92m"
echo "Build application docker for dev"
echo -e "\e[39m"
echo "----------------------------------"
echo -e "\e[0m"
docker build -t PROJECT_NAME .

echo -e "\e[1m"
echo "----------------------------------"
echo -e "\e[92m"
echo "Execute docker compose"
echo -e "\e[39m"
echo "----------------------------------"
echo -e "\e[0m"
docker-compose up
