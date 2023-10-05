package com.example.unscramble.ui

import androidx.lifecycle.ViewModel
import com.example.unscramble.data.allWords
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GameViewModel : ViewModel() {

    // Game UI state
    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    // stores current scrambled word
    private lateinit var currentWord: String

    // stores already used words
    private var usedWords: MutableSet<String> = mutableSetOf()

    // function that shuffles the characters of the chosen word
    private fun shuffleCurrentWord(word: String): String{
        val tempWord = word.toCharArray()
        // Begin scrambling
        tempWord.shuffle()
        while(String(tempWord).equals(word)){
            tempWord.shuffle()
        }
        return String(tempWord)
    }
    
    // chooses a random word that has not been used before
    private fun pickRandomWordAndShuffle(): String {
        currentWord = allWords.random()
        if(usedWords.contains(currentWord)){
            return pickRandomWordAndShuffle()
        }else{
            usedWords.add(currentWord)
            return shuffleCurrentWord(currentWord)
        }
    }

    // resets game
    fun resetGame(){
        usedWords.clear()
        _uiState.value = GameUiState(currentScrambledWord = pickRandomWordAndShuffle())
    }

    init{
        resetGame()
    }
}

data class GameUiState(
    val currentScrambledWord: String = ""
)