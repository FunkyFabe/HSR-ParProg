# Aufgabe 1a

- **Visibility**: Die zwei Threads sehen die Änderungen der `stateX` und `turn` Variablen eventuell nicht oder erst viel später. => Es wird lesend und schreibend ohne genügende Synchronisation auf die gleiche Variable zugegriffen (Data Race), was hier sicherlich in einer Race Condition resultiert. 
- **Ordering**: Es kann optimiert werden solange das sequentielle Verhalten aus Sicht eines einzelnes Threads erhalten bleibt («as if serial»). Z. B. könnte in der Methode `thread0Lock()` das Statement `state0 = true;` unter die Schlaufe umgeordnet werden. Das gleich gilt dann auch für die Methode `thread0Lock()` und dem Statement `state1 = true;`.  Dadurch wäre es möglich, dass beide Threads in die Critical Section hineinkommen. 
- **Atomicity**: Dieser Aspekt ist hier kein Problem. Der logische Ausdruck in der While-Schlaufe ist zwar nicht atomar, jedoch spielt dies keine Rolle. Die Variable `turn` ist entweder 0 oder 1 ist. Daraus lässt sich schliessen, dass mindestens eine der UND Verknüpfungen `false` ist.



