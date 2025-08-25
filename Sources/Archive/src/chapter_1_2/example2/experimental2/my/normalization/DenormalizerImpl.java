package chapter_1_2.example_02.experimental2.my.normalization;


import jmul.math.functions.repository.FunctionIdentifiers;
import jmul.math.numbers.Number;


public class DenormalizerImpl implements Denormalizer {

    private Number minXPointDl;

    private Number maxXPointDh;

    private Number Nh;

    private Number Nl;

    public DenormalizerImpl(Number minXPointDl, Number maxXPointDh, Number Nl, Number Nh) {

        super();

        this.minXPointDl = minXPointDl;
        this.maxXPointDh = maxXPointDh;
        this.Nl = Nl;
        this.Nh = Nh;
    }

    public Number denormalize(Number n) {

        // ((minXPointDl - maxXPointDh) * n - Nh * minXPointDl + maxXPointDh * Nl) / (Nl - Nh);

        Number term1 = minXPointDl.subtract(maxXPointDh);
        Number term2 = Nh.multiply(minXPointDl);
        Number term3 = maxXPointDh.multiply(Nl);
        Number term4 = Nl.subtract(Nh);

        Number result = term1.multiply(n);
        result = result.subtract(term2);
        result = result.add(term3);
        result = result.divide(FunctionIdentifiers.RUSSIAN_DIVISION_FUNCTION, term4);

        return result;
    }

}
