# Instructies voor het aanmaken van een Azure Virtual Machine 

## Stap 1: Een nieuwe resource aanmaken in Azure
1. Navigeer naar [Azure Portal](https://portal.azure.com).
2.  Klik op "Create a resource".
3. Zoek en selecteer **Virtual Machine**.

## Stap 2: Instellingen voor de Virtual Machine
1. Selecteer een actief abonnement.
2. **Configuratie van de VM**:
    - Kies een **minimale geheugen van 2 GB** om er voor te zorgen dat alles hier op kan draaien.
    - Je kan later upgraden als dit nodig is
3. hierna krijg je een ssh key SLA DEZE OP deze krijg je maar 1 keer

## Stap 3: JDK, DOCKER en RABBIT instellen op de VM
1. gebruik de ssh key om in je `VM` te komen 

note: je kan een programma genaamd `putty` gebruiken. Dit programma onthoud je gevens waardoor je het maar 1x in hoef te stellen hierdoor kan je elke keer je `vm` met 1 knop

2. installeer de `JDK` die je gebruikt de command hier voor is `sudo apt install default-jdk`

note: let er op dat je de juiste versie download voor je zelf
3. installeer rabbitMQ `sudo apt update && sudo apt install rabbitmq-server -y`

4. zet een `dockerFile` op de `VM` deze ziet er zo uit:

```dockerfile
version: '3'
services:
  rabbitmq:
    image: rabbitmq:management
    container_name: rabbitmq
    ports:
      - "5672:5672"
      - "15672:15672"
    environment:
      RABBITMQ_DEFAULT_USER: admin
      RABBITMQ_DEFAULT_PASS: admin
    networks:
      - rabbitmq_network
    restart: always  
    
networks:
  rabbitmq_network:
    driver: bridge
```
deze start zichzelf automatisch op als de server opstart dus hoef je geen `docker-compose up` te gebruiken elke keer wanneer die opnieuw opstart

## Stap 3: GitHub Workflow configuratie
deze yml file heb je nodig zodat elke keer dat hij op de main komt hij de nieuwste versie deployed
```yaml
name: Build and deploy JAR app - canvas

on:
  push:
    branches:
      - main
  workflow_dispatch:

jobs:
  build:
    runs-on: ubuntu-latest

    steps:
      - name: Checkout code
        uses: actions/checkout@v4

      - name: Set up Java version
        uses: actions/setup-java@v1
        with:
          java-version: '17'

      - name: Build project with Maven
        run: mvn clean package

      - name: List target directory
        run: |
          echo "Listing target directory:"
          ls -la **/target/*.jar

      - name: Upload JAR artifacts
        uses: actions/upload-artifact@v3
        with:
          name: jar-artifacts
          path: '**/target/*.jar'

  deploy:
    runs-on: ubuntu-latest
    needs: build
    environment:
      name: 'Production'

    steps:
      - name: Download JAR artifacts
        uses: actions/download-artifact@v3
        with:
          name: jar-artifacts

      - name: Set up SSH private key
        run: echo "${{secrets.AZUREKEY}}" > $GITHUB_WORKSPACE/azure_key.pem

      - name: Set permissions on the SSH key
        run: chmod 600 $GITHUB_WORKSPACE/azure_key.pem

      - name: Test SSH connection to VM
        run: |
          echo "Testing SSH connection to VM:"
          ssh -o StrictHostKeyChecking=no -i $GITHUB_WORKSPACE/azure_key.pem azureuser@${{secrets.AZURE_VM_IP}} 'echo "SSH connection successful!"'

      - name: Check target directory
        run: ls -la **/target/

      - name: List JAR files
        run: find . -name "*.jar"

      - name: Deploy and start JAR applications
        run: |
          for JAR in $(find . -name "*.jar"); do
            APP_NAME=$(basename $JAR | cut -d '-' -f 1)
            echo "Deploying $APP_NAME..."
          
            scp -o StrictHostKeyChecking=no -i $GITHUB_WORKSPACE/azure_key.pem $JAR azureuser@${{secrets.AZURE_VM_IP}}:/home/azureuser/
            ssh -o StrictHostKeyChecking=no -i $GITHUB_WORKSPACE/azure_key.pem azureuser@${{secrets.AZURE_VM_IP}} \
              "nohup java -jar /home/azureuser/$(basename $JAR) > /dev/null 2>&1 &"
            sleep 10
          done
```
zorg ervoor dat je zelf de github secrets goed instelt met de URL en de ssh key

## stap 4
deze stap is er om er voor te zorgen dat je mvn package werkt test dit eerst voordat je gaat deployen

### pom
zet deze plugin in je main pom
```xml

<build>
   <plugins>
      <plugin>
         <groupId>org.springframework.boot</groupId>
         <artifactId>spring-boot-maven-plugin</artifactId>
         <version>3.3.4</version>
         <executions>
            <execution>
               <goals>
                  <goal>repackage</goal>
               </goals>
            </execution>
         </executions>
      </plugin>
   </plugins>
</build>
```
### application
zorg ervoor dat alle modules aan application hebben en een eigen rabbit config file

voorbeeld Application
```java
package nl.hu.inno.hulp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReviewApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReviewApplication.class, args);
	}

}

```

een voorbeeld van een application.properties:
```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect

# RabbitMQ config
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=yourusername
spring.rabbitmq.password=yourpassword
spring.rabbitmq.template.reply-timeout=5000

management.endpoints.web.exposure.include=*
server.port=8080

usermoduleurl =  http://localhost:8070

```



    
