# api_rest_springboot
Api rest creada con SpringBoot. 

Importar proyecto maven y ejecutar.


Para crear el servicio de base de datos:
```shell
$ docker pull postgres
```

Cambiar el usuario, la contraseña y puerto a gusto del consumidor.

```shell
$ docker run -d --name postgresql_database -e POSTGRES_USER=user -e POSTGRES_PASSWORD=pass -e POSTGRES_DB=db -p 5432:5432 -v data:/var/lib/pgsql/data postgres
```
