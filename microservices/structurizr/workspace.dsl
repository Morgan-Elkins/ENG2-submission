/*
 * C4 model at the system and container levels for the Microservices,
 * using the Structurizr DSL from here:
 *
 *   https://docs.structurizr.com/dsl/
 *
 * The Compose file in this repository includes a container that runs the
 * Structurizr Lite web application. Use "./compose-it.sh up -d" to bring
 * up the application, and open this webpage:
 *
 *   http://localhost:8081/
 */
workspace "To-Do" "Example to-do list system" {

    model {
      u = person "User"
      admin = person "Administrator"
      
      sV = softwareSystem "video-microservice" {
              cli = container "video-microservice CLI Client"
    
              micronaut = container "video-microservice" {
                domain = component "Domain objects and DTOs"
                services = component "Services"
                repos = component "Repositories"
                events = component "Kafka consumers and producers"
                resources = component "Resources"
              }
    
              database = container "video-microservice Database" "" "MariaDB" "database"
              kafka = container "Kafka Cluster"
              kafkaui = container "Kafka-UI Webapp" "" "" webapp
              adminer = container "Adminer Webapp" "" "" webapp
          }
          
      sT = softwareSystem "trending-microservice" {
              cliTrend = container "trending-microservice CLI Client"
    
              micronautTrend = container "trending-microservice" {
                domainTrend  = component "Domain objects and DTOs"
                servicesTrend  = component "Services"
                reposTrend  = component "Repositories"
                eventsTrend  = component "Kafka consumers and producers"
                resourcesTrend  = component "Resources"
              }
    
              databaseTrend = container "trending-microservice Database" "" "MariaDB" "database"
              kafkaTrend  = container "Kafka Cluster"
              kafkauiTrend  = container "Kafka-UI Webapp" "" "" webapp
              adminerTrend  = container "Adminer Webapp" "" "" webapp
        }    
      
      sS = softwareSystem "subscription-microservice" {
              cliSub = container "subscription-microservice CLI Client"
    
              micronautSub = container "subscription-microservice" {
                domainSub = component "Domain objects and DTOs"
                servicesSub = component "Services"
                reposSub = component "Repositories"
                eventsSub = component "Kafka consumers and producers"
                resourcesSub = component "Resources"
              }
    
              databaseSub = container "subscription-microservice Database" "" "MariaDB" "database"
              kafkaSub = container "Kafka Cluster"
              kafkauiSub = container "Kafka-UI Webapp" "" "" webapp
              adminerSub = container "Adminer Webapp" "" "" webapp
          }      
      
      u -> cli "Uses"
      u -> cliTrend "Uses"
      u -> cliSub "Uses"
      
      admin -> kafkaui "Manages"
      admin -> adminer "Uses"
      admin -> kafkauiTrend "Manages"
      admin -> adminerTrend "Uses"
      admin -> kafkauiSub "Manages"
      admin -> adminerSub "Uses"

      cli -> micronaut "Interacts with HTTP API"
      cliTrend -> micronautTrend "Interacts with HTTP API"
      cliSub -> micronautSub "Interacts with HTTP API"

      micronaut -> database "Reads from and writes to"
      micronaut -> kafka "Consumes and produces events"
      micronautTrend -> databaseTrend "Reads from and writes to"
      micronautTrend -> kafkaTrend "Consumes and produces events"
      micronautSub -> databaseSub "Reads from and writes to"
      micronautSub -> kafkaSub "Consumes and produces events"

      kafkaui -> kafka "Manages"
      adminer -> database "Manages"
      kafkauiTrend -> kafkaTrend "Manages"
      adminerTrend -> databaseTrend "Manages"
      kafkauiSub -> kafkaSub "Manages"
      adminerSub -> databaseSub "Manages"
      
      repos -> domain "Creates and updates"
      repos -> database "Queries and writes to"
      services -> domain "Runs business workflows on"
      services -> repos "Uses"
      resources -> repos "Uses"
      resources -> events "Uses"
      resources -> services "Uses"
      resources -> domain "Reads and updates"
      cli -> resources "Invokes"
      events -> kafka "Consumes and produces events in"
      
      reposTrend  -> domainTrend  "Creates and updates"
      reposTrend  -> databaseTrend  "Queries and writes to"
      servicesTrend  -> domainTrend  "Runs business workflows on"
      servicesTrend  -> reposTrend  "Uses"
      resourcesTrend  -> reposTrend  "Uses"
      resourcesTrend  -> eventsTrend  "Uses"
      resourcesTrend  -> servicesTrend  "Uses"
      resourcesTrend  -> domainTrend  "Reads and updates"
      cliTrend  -> resourcesTrend  "Invokes"
      eventsTrend  -> kafkaTrend  "Consumes and produces events in"      
      
      reposSub -> domainSub "Creates and updates"
      reposSub -> databaseSub "Queries and writes to"
      servicesSub -> domainSub "Runs business workflows on"
      servicesSub -> reposSub "Uses"
      resourcesSub -> reposSub "Uses"
      resourcesSub -> eventsSub "Uses"
      resourcesSub -> servicesSub "Uses"
      resourcesSub -> domainSub "Reads and updates"
      cliSub -> resourcesSub "Invokes"
      eventsSub -> kafkaSub "Consumes and produces events in"
    }

    views {
        theme default
        systemContext sV {
            include *
            autoLayout
        }
        container sV {
            include *
            autoLayout
        }
        systemContext sT {
            include *
            autoLayout
        }
        container sT {
            include *
            autoLayout
        }
        systemContext sS {
            include *
            autoLayout
        }
        container sS {
            include *
            autoLayout
        }
        component micronaut {
            include *
            autoLayout
        }
        component micronautTrend {
            include *
            autoLayout
        }        
        component micronautSub {
            include *
            autoLayout
        }     
        styles {
            element "database" {
              shape Cylinder
            }
            element "webapp" {
              shape WebBrowser
            }
            element external {
              background gray
            }
        }
    }

}