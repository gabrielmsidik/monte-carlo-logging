# Logging test project
Testing of logging ELK pipelines with a monte carlo logging (forest) use case
ELK Back-end used is version 8.1.3

Download links here:
- elasticsearch 8.1.3 - https://www.elastic.co/downloads/past-releases/elasticsearch-8-1-3
- logstash 8.1.3 - https://www.elastic.co/downloads/past-releases/logstash-8-1-3
- kibana 8.1.3 - https://www.elastic.co/downloads/past-releases/kibana-8-1-3

### Logging with Logback
Check out the logback.xml file
Layout Documentation can be found here - https://logback.qos.ch/manual/layouts.html

Try to run the project w/o the logback.xml file - see how the logs differ!

Good resources to learn about Logback - https://www.baeldung.com/logback

## ELK Set-up + Details
### Logstash

#### Resources
- https://www.elastic.co/guide/en/logstash/current/configuration-file-structure.html
- https://logz.io/blog/logstash-grok/
- https://grokdebug.herokuapp.com/

### Elasticsearch
Important localhost locations:
- http://localhost:9200/ - see generic details
- [http://localhost:9200/_cat/indices](http://localhost:9200/_cat/indices) - see indices location
- http://localhost:9200/.ds-logs-generic-default-2022.06.23-000001/_search - search API

### Kibana
- log visualization
- [dev_tools#/console](http://localhost:5601/app/dev_tools#/console)
