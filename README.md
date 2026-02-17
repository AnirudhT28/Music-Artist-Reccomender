[![Deploy to Render](https://render.com/images/deploy-to-render-button.svg)](https://music-artist-reccomender.onrender.com/)
Note on Performance: This application is hosted on a Render Free Instance. If the site hasn't been visited recently, the container may be asleep.

Please allow 30–60 seconds for the initial spin-up.
Once active, the Java backend will be fully responsive as it navigates the pre-loaded SQLite graph.


# Artist Recommender System-
An artist recommendation system that takes in an input of an artist and returns the 10 most similar artists that you may not have known beforehand

Data Engineering (Python): A custom crawler using pylast that performs a depth-limited traversal of the Last.fm influence graph.

Graph Processing: NetworkX & SciPy to calculate the PageRank vector across over 3100 artists and 17,516 influence connections.

Backend (Java): Spring Boot + Spring Data JPA. I implemented native SQL joins to handle multi-hop graph discovery in milliseconds.

Frontend (JS/HTML): A responsive, Spotify-themed dashboard that visualizes "Compatibility %" using a normalized affinity scale

technical tools

Backend: Java 17, Spring Boot, Spring Data JPA, SQLite

Data/Python: NetworkX, SciPy, PyLast (Last.fm API), PageRank Algorithm

Infrastructure: RESTful APIs, Environment Variables (.env), Git


