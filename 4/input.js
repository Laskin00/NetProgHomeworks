regExps = {
"exercise_1": /[A-Z][a-z]+/,
"exercise_2": /088[^0]\d{6}/,
"exercise_3": /[^0-1]+/,
"exercise_4": /^[a-z][^no]+[._A-Za-z\d]+/,
"exercise_5": /^(1[0-4][0-9][0-9])|1500|[1-9]{3}/,
"exercise_6": /class=['"].*['"]/
};
cssSelectors = {
"exercise_1": "item>java",
"exercise_2": "different>java",
"exercise_3": "item > java > tag",
"exercise_4": "item:nth-child(3):last",
"exercise_5": "item >tag > java.class1:last",
"exercise_6": "item#someId> item > item> item> item",
"exercise_7": "different > different#diffId2 > java:last",
"exercise_8": "item#someId"
};
