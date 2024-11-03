Primer Parcial - Desarrollo de Software

La API se hosteaó en el cloud computing libre Render.
Las solicitudes POST en Postman se envían con la URL:
https://desarrollo-primerparcial.onrender.com/mutante/
La Request tiene que tener un formato JSON. Por ejemplo:
{
    "adn": ["AATAG","AATCG","ACGCG","AGGAA","TTTTG"]
}
En el caso de que el ADN sea mutante, se devuelve un HTTP 200-OK
En el caso de que el ADN sea humano, se devuelve un 403-Forbidden

Para obtener las stats: https://desarrollo-primerparcial.onrender.com/mutante/stats

A continuación se brindan opciones de solicitudes:
{
    "adn": ["ATAAAA","AGGGGC","ATATGG","AGAAGG","CCCCTG","TCACTG"]
}
{
    "adn": ["ATGAAA","AGGGGC","CTATGG","ACAAGG","CCCCTG","TCACTG"]
}
{
    "adn": ["ATGAAA","AGAAAG","CAGATG","ACATGA","CCTGCC","TTGATA"]
}
{
    "adn": ["ATAAAA","AGGGGC","ATATGG","AGAAGG","CCCGTG","TCGATG"]
}
{
    "adn": ["AAAA","AAAT","AAAG","ACCA"]
}
{
    "adn": ["ATAAA","AGGGG","ATGGG","AGGGG","CGCTG"]
}
{
    "adn": ["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]
}
{
    "adn": ["AAAA","AAAT","AAAG"]
}
{
    "adn": ["ATAAR","AGGGG","ATGGG","AGGGG","CGCGG"]
}
{
    "adn": ["ATA","AGG","ATG"]
}

Los test automáticos se encuentran en la carpeta "test"
Finalmente, el Code Coverage junto con el diagrama de secuencia referente al proyecto, se adjuntan como PDF.
