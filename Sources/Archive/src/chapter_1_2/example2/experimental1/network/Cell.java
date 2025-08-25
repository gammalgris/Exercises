package chapter_1_2.example_02.experimental1.network;


import jmul.misc.state.State;


public interface Cell extends Runnable {

    State state();

    void cloneAndReplaceOldCell();

}
