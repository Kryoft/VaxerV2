package shared;

public class Tripla<T, U, V> {
    private final T primo;
    private final U secondo;
    private final V terzo;

    public Tripla(T prima, U second, V third) {
        this.primo = prima;
        this.secondo = second;
        this.terzo = third;
    }

    public T getPrimo() { return primo; }
    public U getSecondo() { return secondo; }
    public V getTerzo() { return terzo; }
}
