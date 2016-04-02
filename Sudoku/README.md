# Sudoku

1) Requirements
  - Java 1.8
  - gradle

2) Make file properties.txt with following informations:
```
  T - start temperature
  minT - minimal temperature
  maxiteration - max iterations
  ```
  example:
  ```
  T=200.0
  minT=10.0
  maxiteration=1000
  ```
3) Prepare file with defined sudoku
  (0 means empty field)
  
  example:
  ```
  000401006
  850200400
  020500000
  000000504
  410000063
  905000000
  000000802
  009004075
  700602000
  ```
3) Make and Run
  - gradle Jar
  - java -jar build\libs\Sudoku-1.0.jar filename
