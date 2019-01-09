# bank-ocr

A Clojure cmdline app designed to read seven-segment display OCR
text from stdin and translate it into arabic digits.

This is a partial solution to the
[Bank OCR Kata](http://codingdojo.org/kata/BankOCR/).
Here, we have attempted to solve for User Stories 1, 2, and 3.

## Usage

```
  $ cat sample-data/*.data |lein run
  ### or uberjar, then run
  $ lein do clean, uberjar
  $ cat sample-data/* |java -jar target/bank-ocr.jar
```

## Tests

```
  $ lein do clean, test
```

## Author

Richard W. Norton
