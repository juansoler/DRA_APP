# Api rest creada con SpringBoot. 

Esta sencilla api genera una base de datos en PostgreSQL con dos tablas, Temas y Preguntas, con una relación uno a muchos, por lo que un tema podrá tener muchas preguntas y una pregunta puede pertenecer a un solo tema. Gracias a la sencillez de SpringBoot eso podemos lograrlo con tan solo un par de líneas de código.

### Clonar repo 

```shell
git clone https://github.com/juansoler/api_rest_springboot.git
cd api_rest_springboot
```
### Importar proyecto maven y ejecutar.

Para generar y ejectuar proyecto Maven desde la línea de comandos usamos la librería [Maven](https://maven.apache.org/) de Apache®

Linux:
```shell
$ sudo apt-get install mvn
# if that does not work, try
$sudo apt-get install maven
$ mvn install
$ mvn spring-boot:run

```
Windows, usando [chocolatey](https://chocolatey.org/)

```shell
choco install maven
mvn install
mvn spring-boot:run
```

Si queremos compilar nuestro proyecto en archivo JAR, lo podemos hacer con el siguiente comando
```shell
mvn compile
```

### Crear servicio base de datos con PostreSQL

Para crear el servicio de base de datos:
```shell
$ docker pull postgres
```

Cambiar el usuario, la contraseña y puerto a gusto del consumidor.

```shell
$ docker run -d --name postgresql_database -e POSTGRES_USER=user -e POSTGRES_PASSWORD=pass -e POSTGRES_DB=db -p 5432:5432 -v data:/var/lib/pgsql/data postgres
```


