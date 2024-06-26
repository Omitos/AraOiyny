package Ara.core.data;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class WordFilter implements DataFilter {

    @Override
    public FilteredData filter(List<String> words) {
        Random random = new Random();
        List<String> pangramWords = findPangrams(words);
        System.out.println(pangramWords.size());
        String randomPangram = pangramWords.get(random.nextInt(pangramWords.size()));
        List<Character> uniqueLetters = getUniqueLetters(randomPangram);

        StringBuilder letters = new StringBuilder();
        mixLetters(uniqueLetters);
        uniqueLetters.forEach(letters::append);

        return filter(words, letters.toString());
    }

    @Override
    public FilteredData filter(List<String> words, String letters) {
        char centerLetter = letters.charAt(letters.length() / 2);
        List<String> firstFilter = filterWordsContainUnusedLetters(words, letters);
        List<String> secondFilter = filterWordsNotContainCenterLetter(firstFilter, centerLetter);
        List<String> thirdFilter = filterWordsAppearMoreThanOnce(secondFilter);
        List<String> pangramWords = findPangrams(thirdFilter, letters);

        return new FilteredData(thirdFilter, pangramWords, letters);
    }

    private List<String> findPangrams(List<String> words) {
        List<String> pangramWords = new LinkedList<>();
        for (String word : words) {
            int uniqueLetterCount = getUniqueLetterCount(word);
            if (uniqueLetterCount == 7) {
                pangramWords.add(word);
            }
        }
        return pangramWords;
    }

    private List<Character> getUniqueLetters(String word) {
        List<Character> uniqueCharacters = new LinkedList<>();
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            if (!uniqueCharacters.contains(ch)) {
                uniqueCharacters.add(ch);
            }
        }
        return uniqueCharacters;
    }

    private int getUniqueLetterCount(String word) {
        List<Character> characters = new LinkedList<>();
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            if (!characters.contains(ch)) {
                characters.add(ch);
            }
        }

        return characters.size();
    }

    private List<String> findPangrams(List<String> words, String letters) {
        List<String> pangramWords = new LinkedList<>();
        for (String word : words) {
            if (isWordPangram(word, letters)) {
                pangramWords.add(word);
            }
        }

        return pangramWords;
    }

    private boolean isWordPangram(String word, String letters) {
        int letterCount = 0;
        for (int i = 0; i < letters.length(); i++) {
            for (int j = 0; j < word.length(); j++) {
                char ch = word.charAt(j);
                if (ch == letters.charAt(i)) {
                    letterCount++;
                    break;
                }
            }
        }

        return letterCount == letters.length();
    }

    private List<String> filterWordsContainUnusedLetters(List<String> words, String letters) {
        List<String> filteredWords = new LinkedList<>();
        for (String word : words) {
            boolean containsUnusedLetter = false;
            for (int i = 0; i < word.length(); i++) {
                char ch = word.charAt(i);
                if (!lettersContainCharacter(letters, ch)) {
                    containsUnusedLetter = true;
                    break;
                }
            }

            if (!containsUnusedLetter) {
                filteredWords.add(word);
            }
        }

        return filteredWords;
    }

    private List<String> filterWordsNotContainCenterLetter(List<String> words, char centerLetter) {
        List<String> filteredWords = new LinkedList<>();
        for (String word : words) {
            boolean containsCenterLetter = false;

            for (int i = 0; i < word.length(); i++) {
                char ch = word.charAt(i);
                if (ch == centerLetter) {
                    containsCenterLetter = true;
                    break;
                }
            }

            if (containsCenterLetter) {
                filteredWords.add(word);
            }
        }

        return filteredWords;
    }

    private List<String> filterWordsAppearMoreThanOnce(List<String> words) {
        List<String> filteredWords = new LinkedList<>();

        for (String word : words) {
            if (!filteredWords.contains(word)) {
                filteredWords.add(word);
            }
        }
        return filteredWords;
    }

    private void mixLetters(List<Character> letters) {
        Random random = new Random();
        for (int i = 0; i < letters.size(); i++) {
            int randomIndex = random.nextInt(letters.size());
            char temp = letters.get(randomIndex);
            letters.set(randomIndex, letters.get(i));
            letters.set(i, temp);
        }

    }
    private boolean lettersContainCharacter(String letters, char character) {
        for (int i = 0; i < letters.length(); i++) {
            char ch = letters.charAt(i);
            if (ch == character)
                return true;
        }
        return false;
    }
}
