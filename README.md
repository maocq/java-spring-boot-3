# Java spring boot 3

- Http Client

```sh
curl --location 'http://localhost:8080/api/usecase/account' \
--header 'Content-Type: application/json' \
--data '{
    "name" : "Hello",
    "statusId" : "3b5cd73e-5508-4b1d-9bbe-83bcb3e6be54"
}'
```

- ReqReply
```sh
docker run -e LICENSE=accept -e MQ_QMGR_NAME=QM1 -p 1414:1414 -p 9443:9443 -d --name ibmmq -e MQ_APP_PASSWORD=passw0rd ibmcom/mq

#Colas temporales
docker container exec -it ibmmq setmqaut -m QM1 -n SYSTEM.DEFAULT.MODEL.QUEUE -t queue -p app +get +put +inq +dsp
```

https://localhost:9443/ibmmq/console/login.html#

- admin
- passw0rd

```sh
curl 'http://localhost:8080/api/usecase/req-reply'

curl 'http://localhost:8080/api/usecase/req-reply-fixed-queue?m=HelloW'
```

- DB

```sh
docker run --name postgres-db -e POSTGRES_PASSWORD=pass -d -p 5432:5432 postgres
```

```sql
CREATE TABLE account
(
    id                          SERIAL,
    name                        VARCHAR(200) NOT NULL,
    status                      VARCHAR(200) NOT NULL,
    CONSTRAINT account_pk PRIMARY KEY (id)
);

INSERT INTO public.account
    (id, name, status)
VALUES(4000, 'AC12345', 'ENABLED');
```
```sql
select datname, application_name , count(*) as num from pg_stat_activity group by datname,  application_name order by num desc;
```

```sh
curl 'http://localhost:8080/api/db/path?id=4000'
```
