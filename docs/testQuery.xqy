for $x in doc("http://www.w3schools.com/xml/cd_catalog.xml")/CATALOG/CD
where $x[contains (TITLE, 'Eros')]
return $x