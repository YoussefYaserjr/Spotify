package com.example.Spotify.exception;

public class InvalidInput extends IllegalArgumentException{
    public InvalidInput(String message){
        super(message);
    }
}
