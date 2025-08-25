package chapter_1_2.example_02.experimental1.network;


import chapter_1_2.example_02.experimental1.network.hash.HashCounter;
import chapter_1_2.example_02.experimental1.network.trackers.ParticleSignalTracker;


abstract class CellBase implements Cell {

    private final int hashCode;
    
    protected ParticleSignalTracker signalTracker;

    public CellBase() {

        this(HashCounter.nextHashCode());
    }

    public CellBase(int hashCode) {

        super();

        this.hashCode = hashCode;
        this.signalTracker = new ParticleSignalTracker();
    }

    @Override
    public int hashCode() {

        return hashCode;
    }

    @Override
    public boolean equals(Object o) {

        if (o == null) {

            return false;
        }

        if (this == o) {

            return true;
        }

        if (o instanceof Cell) {

            Cell other = (Cell) o;

            return this.hashCode() == other.hashCode();
        }

        return false;
    }

    @Override
    public String toString() {

        return String.format("cell (hash code=%d)", hashCode());
    }

}
