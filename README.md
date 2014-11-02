Media Cloud Localization
========================

As part of my research at MIT, I developed a road map for adapting [Media Cloud](http://www.mediacloud.org) to different countries. [Media Cloud](http://www.mediacloud.org) is a system developed by the [Berkman Center for Internet and Society at Harvard](http://berkman.harvard.edu) and the [MIT Center for Civic Media](http://civic.mit.edu). It performs large scale content analysis of online media. So far, Media Cloud gathers content in English, Russian, and Brazilian Portuguese. The main challenge when it comes to adapting it for a new media ecosystem is the curation of a representative set of media sources (at least, a few thousands) for each country. This GitHub repository stores the code I developed to use [Alexa](http://www.alexa.com)'s ranking of websites to curate an initial set of media sources.

Alexa's top 1,000,000 websites is a ranking published daily based on the traffic information gathered by Alexa. Nowadays there is no apparent link for downloading this list in Alexa's website. However, the list continue to be generated in a daily basis and made available [here](http://s3.amazonaws.com/alexa-static/top-1m.csv.zip) as a CSV file.

