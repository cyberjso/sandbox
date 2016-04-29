#DAEMONED CONTAINERS
docker run --name my_first_container --log-driver="json-file" -d ubuntu -c "while true; do echo hello world; sleep 1; done;"
docker logs -f my_first_container

docker stats my_first_container