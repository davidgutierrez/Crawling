for $x in doc("http://feeds.bbci.co.uk/news/technology/rss.xml")/rss/channel/item
where $x[contains (title, 'US')] or contains ($x/description, 'US')
return $x/title