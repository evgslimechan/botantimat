package ru.mgkit.antimat.word;

public interface BadwordManager {

    public static final BadwordManager Instance = new BadwordManagerImpl();

    boolean isBad(String word);

}
