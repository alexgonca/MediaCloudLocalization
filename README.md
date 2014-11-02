Media Cloud Localization
========================

As part of my research at MIT, I developed a road map for adapting [Media Cloud](http://www.mediacloud.org) to different countries. [Media Cloud](http://www.mediacloud.org) is a system developed by the [Berkman Center for Internet and Society at Harvard](http://berkman.harvard.edu) and the [MIT Center for Civic Media](http://civic.mit.edu). It performs large scale content analysis of online media. So far, Media Cloud gathers content in English, Russian, and Brazilian Portuguese. The main challenge when it comes to adapting it for a new media ecosystem is the curation of a representative set of media sources (at least, a few thousands) for each country. This GitHub repository stores the code I developed to use [Alexa](http://www.alexa.com)'s ranking of websites to curate an initial set of media sources.

Alexa's top 1,000,000 websites is a ranking published daily based on the traffic information gathered by Alexa. There is no apparent link for downloading this list in Alexa's website. However, the list continue to be generated in a daily basis and made available [here](http://s3.amazonaws.com/alexa-static/top-1m.csv.zip) as a CSV file.

The curation of the set of media sources is a two-step process:
1. Identification of the websites related to a specific country using a computer-based approach.
2. Exclusion of websides unrelated to media content and public debate (e.g. pornographic websites, comercial websites, search engines etc.). This process has been performed manually.

The code presented here implements the first step. In fact, there are two ways of grouping Alexa's websites according to country of origin. One of them is based on language identification. The other obtains the country from Alexa's website. They are described as follows.

# By language

It is implemented in the file [AlexaLanguage.java](blob/master/AlexaLanguage.java).

This is the method I used to select the media sources for Brazil. If a language is largely related to one specific country (or if you are interested in a set of media sources grouped by language and not country), this is the way to go.

In fact, there are nine countries that have Portuguese as their official language (Portugal, Brazil, Mozambique, East Timor, etc.), however four out of five Portuguese speakers live in Brazil. So it was pretty safe to assume that most websites would be related to the Brazilian online environment.

