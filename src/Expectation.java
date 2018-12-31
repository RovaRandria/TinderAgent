import ExpectationType;

public class Expectation<T> {
    private T expected;
    private ExpectationType function;

    public Expectation(T exp, ExpectationType func) {
        expected = exp;
        function = func;
    }

    public T getValue() {
        return expected;
    }

    public ExpectationType getFunction() {
        return function;
    }
}