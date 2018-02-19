for $x in doc("http://www.microsiervos.com/index.xml")/rss/channel/item
where $x[contains (title, 'moneda')] or contains ($x/description, 'moneda') or contains ($x/category, 'moneda')
return ($x/title, $x/pubDate, $x/link)
