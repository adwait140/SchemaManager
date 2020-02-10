mvn clean compile package

docker build  --build-arg HOST=Redis \
              --build-arg PORT=8080  \
              -t schema-manager1:latest .

docker build --build-arg HOST=mongo \
              --build-arg PORT=8081 \
              -t schema-manager2:latest .

docker-compose up -d

docker ps

npm install frisby --save-dev

npm install --save-dev jest

jest test/integration-tests.js