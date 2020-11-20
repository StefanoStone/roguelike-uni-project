package com.stonewolf.game.utils;

/**
 * Thread che consente l' esecuzione di un {@link Runnable} che viene eseguito al termine di un intervallo di tempo
 * definito dall'inizializzatore.
 *
 * @author Stefano Campanella
 * @version 1.0
 */
public class Timer extends Thread {

    private long delay;
    private Runnable actionToBePerformed;

    /**
     *  Costruttore della classe Timer
     *
     * @param delayInSeconds durata del threads richiesta (in secondi)
     * @param actionToBePerformed azione che il Thread deve eseguire allo scadere del tempo inserito
     *
     * @author Stefano Campanella
     */
    public Timer(long delayInSeconds, Runnable actionToBePerformed) {
        super();
        this.delay = delayInSeconds;
        this.actionToBePerformed = actionToBePerformed;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(this.delay * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        actionToBePerformed.run();
    }

}
