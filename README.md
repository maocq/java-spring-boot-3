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
curl 'http://localhost:8080/api/otherusercase/path'
```

