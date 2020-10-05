package ru.mgkit.antimat.word;

public interface BadwordManager {

    public static final BadwordManager Instance = new BadwordManagerImpl();

    boolean isBad(String word);

    void addWord(String word);

    void removeWord(String word);

    void setCharDict(char key, char[] charDict);

    char[] getCharDict(char key);

    void addCharToDict(char key, char charToAdd);

    void removeCharFromDict(char key, char charToRemove);

}
