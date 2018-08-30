# Application

http://noteapp-search-developer.127.0.0.1.nip.io/swagger-ui/index.html?url=/api/swagger.json

# infrastructure

## solr

http://solr-developer.127.0.0.1.nip.io/solr/#/

### setup

curl -X POST -H 'Content-type:application/json' --data-binary '{
  "replace-field-type":{
    "name":"text_fr",
    "class":"solr.TextField",
    "positionIncrementGap":"100",
    "analyzer": {
      "tokenizer": {
        "class": "solr.StandardTokenizerFactory"
      },
      "filters": [
        {
          "class": "solr.ElisionFilterFactory",
          "articles": "lang/contractions_fr.txt",
          "ignoreCase": "true"
        },
        {
          "class": "solr.LowerCaseFilterFactory"
        },
        {
          "class": "solr.StopFilterFactory",
          "format": "snowball",
          "words": "lang/stopwords_fr.txt",
          "ignoreCase": "true"
        },
        {
          "class":"solr.ASCIIFoldingFilterFactory"
        },
        {
          "class": "solr.FrenchLightStemFilterFactory"
        }
      ]
    }
  }
}' http://localhost:8983/solr/mycores/schema


### delete query

http://solr-developer.127.0.0.1.nip.io/solr/#/mycores/documents

/update

xml

<delete><query>*:*</query></delete> 

