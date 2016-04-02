# TSP

1) Requirements
  - Java 8
  - gradle

2) Make file properties.txt with following informations:
```
  n - number of points
  T - start temperature
  minT - minimal temperature
  maxiteration - max iterations
  distribution - GROUPS/NORMAL/UNIFORM
  speed - FAST/SLOW
  ```
  example:
  ```
  n=50
  T=200.0
  minT=10.0
  maxiteration=1000
  distribution=GROUPS
  speed=SLOW
  ```

3) Make and Run
  - gradle Jar
  - java -jar build\libs\TSP-1.0.jar
