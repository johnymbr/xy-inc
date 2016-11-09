# xy-inc

Procedimentos para rodar o projeto:

É necessário ter instalado o JDK1.8 ou superior
É necessário ter instalado o Maven 3.0+

Após feito o clone do repositório ir até o diretório onde o mesmo foi baixado e rodar o seguinte comando:

mvn package && java -jar target/xy-inc-poi.jar

O comando acima irá construir o projeto e iniciar o container Tomcat embedded no projeto.

Para realizar os testes estou disponibilizando uma coleção do Postman, caso queiram importar a mesma nessa ferramenta. Abaixo seguem chamadaso utilizando o curl para os testes:

* findAll
curl http://localhost:8080/poi/findAll

* save
curl -X POST -H 'Content-Type: application/json' -d '{"name":"Lanchonete", "x":"27", "y":"12"}' http://localhost:8080/poi
curl -X POST -H 'Content-Type: application/json' -d '{"name":"Posto", "x":"31", "y":"18"}' http://localhost:8080/poi
curl -X POST -H 'Content-Type: application/json' -d '{"name":"Joalheria", "x":"15", "y":"12"}' http://localhost:8080/poi
curl -X POST -H 'Content-Type: application/json' -d '{"name":"Floricultura", "x":"19", "y":"21"}' http://localhost:8080/poi
curl -X POST -H 'Content-Type: application/json' -d '{"name":"Pub", "x":"12", "y":"8"}' http://localhost:8080/poi
curl -X POST -H 'Content-Type: application/json' -d '{"name":"Supermercado", "x":"23", "y":"6"}' http://localhost:8080/poi
curl -X POST -H 'Content-Type: application/json' -d '{"name":"Churrascaria", "x":"28", "y":"2"}' http://localhost:8080/poi

* findPoisNearTo
curl "http://localhost:8080/poi/findPoisNearTo?x=20&y=10&maxDistance=10"
