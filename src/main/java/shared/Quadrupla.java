package shared;

/**
 * Semplice struttura dati rappresentante una tupla di quattro elementi.
 *
 * @param <T> Un'oggetto di qualsiasi tipo
 * @param <U> Un'oggetto di qualsiasi tipo
 * @param <V> Un'oggetto di qualsiasi tipo
 * @param <S> Un'oggetto di qualsiasi tipo
 */
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
