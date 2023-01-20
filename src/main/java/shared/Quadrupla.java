package shared;

public class Quadrupla<T, U, V,S> {
    private final T primo;
    private final U secondo;
    private final V terzo;
    private final S quarto;

    public Quadrupla(T prima, U second, V third, S fourth) {
        this.primo = prima;
        this.secondo = second;
        this.terzo = third;
        this.quarto = fourth;
    }

    public T getPrimo() { return primo; }
    public U getSecondo() { return secondo; }
    public V getTerzo() { return terzo; }

    public S getQuarto() { return quarto; }
}
