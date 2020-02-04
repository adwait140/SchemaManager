from openjdk:8

ARG HOST

ARG PORT

ENV host=$HOST

ENV port=$PORT

ADD target/SchemaManager-1.0-SNAPSHOT.jar ./SchemaManager-1.0-SNAPSHOT.jar

EXPOSE $PORT

CMD ["sh","-c","java -jar -Ddbname=${host} -Dserver.port=${port} SchemaManager-1.0-SNAPSHOT.jar"]