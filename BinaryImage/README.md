# BinaryImage

1) Requirements
  - Java 1.8
  - gradle

2) Make file properties.txt with following informations:
```
  n - width and height
  sigma - density of black points
  T - start temperature
  minT - minimal temperature
  maxiteration - max number of iterations
  energy=OPTION1/OPTION2/OPTION3/OPTION4/OPTION5/OPTION6
  neighbourhood=OPTION1/OPTION2/OPTION3/OPTION4
  ```
  example:
  ```
  n=100
  sigma=0.3
  T=200.0
  minT=0.0
  maxiteration=10000
  energy=OPTION1
  neighbourhood=OPTION4
  ```

3) Make and Run
  - gradle Jar
  - java -jar build\libs\BinaryImage-1.0.jar
