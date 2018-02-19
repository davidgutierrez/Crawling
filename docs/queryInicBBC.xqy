for $x in doc("http://feeds.bbci.co.uk/news/technology/rss.xml")/rss/channel/item
return $x/title