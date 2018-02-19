for $x in doc("http://feeds.bbci.co.uk/news/technology/rss.xml")/rss/channel/item
where $x[contains (title, 'moneda')] or contains ($x/description, 'moneda') or contains ($x/category, 'moneda')
return ($x/title, $x/pubDate, $x/link)
