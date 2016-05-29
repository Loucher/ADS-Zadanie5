package sk.stuba.fe.ads.zadanie5

import groovy.time.TimeCategory
import groovyx.gpars.GParsExecutorsPool

class SpellChecker {
    BkTree tree

    SpellChecker(String dictionary) {
        tree = new BkTree()
        def start = new Date()
        println "Creating dictionary..."
        int wordCount = 0;
        this.getClass().getResource(dictionary).eachLine {
            tree.add(it)
            wordCount++
        }
        println "Created dictionary with $wordCount words in ${TimeCategory.minus(new Date(), start)}"
    }

    public void checkFile(String fileName) {
        def start = new Date()
        int correct = 0;
        int notFound = 0;
        int corrected = 0;
        println "Spellchecking fileName $fileName ..."
        String[] words = this.getClass().getResource(fileName).text.split(' ')
        GParsExecutorsPool.withPool {
            words.eachWithIndexParallel { String word, int index ->
                BkTree.WordMatch match = tree.findBest(word.toLowerCase())
                if (!match) {
                    notFound++
                } else if (match.distance == 0) {
                    correct++
                } else {
                    corrected++
                    String newWord;
                    if (Character.isUpperCase(word.charAt(0))) {
                        newWord = match.word.capitalize()
                    } else {
                        newWord = match.word
                    }
                    words[index] = newWord
                }
            }
        }
        println "Spellcheck finished in ${TimeCategory.minus(new Date(), start)}"
        println "Checked ${words.size()} words, from that ${getPercent(correct, words.size())}% correct, ${getPercent(notFound, words.size())}% not found and ${getPercent(corrected, words.size())}% successfully corrected."
        saveFile(words, fileName)
    }

    private static void saveFile(String[] words, String fileName) {
        def file = new File(fileName.replace(".txt", "o.txt").replace('/', ''))
        file.withWriter('UTF-8') { writer ->
            words.each {
                writer.append("$it ")
            }
        }
    }

    public void check(String word) {
        BkTree.WordMatch match = tree.findBest(word.toLowerCase())
        if (!match) {
            println "$word: No match found"
        } else if (match.distance == 0) {
            println "$word: Correct"
        } else {
            String newWord;
            if (Character.isUpperCase(word.charAt(0))) {
                newWord = match.word.capitalize()
            } else {
                newWord = match.word
            }
            println "$word: Corrected to $newWord with distance $match.distance"
        }
    }

    private static int getPercent(int count, int totalCount) {
        Math.round(count / totalCount * 100) as int
    }

}
