package org.example;

import java.util.concurrent.locks.ReentrantLock;

public class TokenBucket {
    private static final int numTokens = 5;
    private static int currentToken = 0;
    private static final ReentrantLock lock = new ReentrantLock();

    public static void rateLimit(String message){
        long currentTime = System.currentTimeMillis();
        Request request = new Request(message, currentTime);
        lock.lock();
        if(currentToken>0){
            currentToken--;
            System.out.println("REQUEST PROCESSED : STATUS 200 OK "+message);
        }else{
            System.out.println("TOO MANY REQUESTS : STATUS 429 " );
        }
        lock.unlock();

    }
    public static void addToken(){
        lock.lock();
        if(currentToken<numTokens){

            System.out.println("TOKENS ADDED");
        }
        currentToken=numTokens;
        lock.unlock();
    }
}
