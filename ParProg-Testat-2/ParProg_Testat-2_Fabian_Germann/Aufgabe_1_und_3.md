# ParProg-Testat-2

## Aufgabe 1a

- **Visibility**: Die zwei Threads sehen die Änderungen der `stateX` und `turn` Variablen eventuell nicht oder erst viel später. => Es wird lesend und schreibend ohne genügende Synchronisation auf die gleiche Variable zugegriffen (Data Race), was hier sicherlich in einer Race Condition resultiert. 
- **Ordering**: Es kann optimiert werden, solange das sequentielle Verhalten aus Sicht eines einzelnes Threads erhalten bleibt («as if serial»). Z. B. könnte in der Methode `thread0Lock()` das Statement `state0 = true;` unter die Schlaufe umgeordnet werden. Das gleich gilt dann auch für die Methode `thread1Lock()` und dem Statement `state1 = true;`.  Dadurch wäre es möglich, dass beide Threads in die Critical Section hineinkommen. 
- **Atomicity**: Dieser Aspekt ist hier kein Problem. Der logische Ausdruck in der While-Schlaufe ist zwar nicht atomar, jedoch spielt dies keine Rolle. Die Variable `turn` ist entweder 0 oder 1. Daraus lässt sich schliessen, dass mindestens eine der UND Verknüpfungen `false` ist.

## Aufgabe 3a

*Zeitmessungen in ms*

**ohne yield()**: (2544 + 2307 + 2492 + 2457 + 2463)/5 = **2452.6**

**mit yield()**: (494 + 703 + 571 + 557 + 507)(5) = **566.4**

**Speed-Up**: 2452.6 / 566.4 = **4.33**

## Aufgabe 3b

*Zeitmessungen in ms*

**onSpinWait()**: (2246 + 2481 + 2465 + 2192 + 2292) \ 5 = 2335.2

**Speed-Up**: 2452.6 / 2335.2 = **1.05** => langsamer als yield(), ungefähr gleich schnell wie ohne yield()

## Aufgabe 3c

*Zeitmessungen in ms*

**ohne Schlaufen**: (2621 + 2719 + 2536 + 2486 + 2615) / 5 = 2595.4

**Speed-Up**: 2452.6 / 2595.4 = **0.94** => ungefähr gleich schnell, da immer noch Optimistische Synchronisation angewendet wird, da intern mit einer Schlaufe implementiert ist. 