# Static File Server Boilerplate Java Spark

Crear proyecto con Maven

    $ mvn archetype:generate -DgroupId=pe.softweb -DartifactId=lite -DarchetypeArtifactId=maven-archetype-webapp -DinteractiveMode=false

Ubicación de los archivos estáticos:

    src/main/resources/public

Crear war usando Maven:

    $ mvn package

Cargar dependencias bower y npm:

    $ bower install && npm install

Ejecutar Main Class usando Maven:

    $ mvn clean && mvn install && mvn exec:java -Dexec.mainClass="configs.App"
    
### BDD con cucumber

Instalación de dependencias:

	$ gem install cucumber
	$ bundler install

Ejecutar pruebas:

	$ cucumber 
    $ cucumber features/<file_name>.feature


--- 

Fuentes

+ https://www.mkyong.com/maven/how-to-create-a-web-application-project-with-maven/
+ https://stackoverflow.com/questions/9846046/run-main-class-of-maven-project
+ http://sparkjava.com/