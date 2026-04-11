# Seminar 8: Generic Programming & Metaprogramming

## Обзор перехода
В рамках Семинара 8 мы перевели фреймворк симуляции со статической привязки к `double[]/double[][]` на строгую параметризацию типов (Generic Programming) `T extends Number` и метапрограммирование.

## 1. Обобщенное программирование (Generics Implementation)
Базовые классы и их методы подверглись глубокому рефакторингу:
* `Field<T extends Number>`: Внутреннее хранилище изменено на `Number[][]` (так как Java запрещает создание массивов обобщенных типов `T[][]`).
* `Solver<T extends Number>`, `PhysicalModel<T extends Number>` и `SimulationController`: Введен параметр `<Double>` во все legacy-стратегии для совместимости.

### Проблема: Type Erasure (Стирание типов) в Java
Главной проблемой при переходе стала концепция *Type Erasure*. Java стирает информацию о `T` на этапе компиляции, превращая всё в `Number`, в результате чего `Number` не поддерживает арифметические операторы напрямую (`+`, `-`, `*`). Во всех legacy-солверах и наблюдателях (e.g. `ExplicitEulerSolver`, `LegacySolverAdapter`, `ConvergenceObserver`) нам пришлось применять даун-кастинг с использованием `.doubleValue()`.

## 2. Compile-Time Dimension Handling
Вместо динамической структуры `GridDomain` введена иерархия классов `Grid -> Grid1D -> Grid2D`:
* Проверка размерностей перенесена из Runtime (if `getSizeY() == 1`) в **Compile-Time**.
* Теперь новые солверы ограничены статически, например: `class GenericExplicitEulerSolver<T extends Number, G extends Grid2D>`. Это предотвращает передачу одномерной сетки в двумерный алгоритм на этапе компиляции кода.

## 3. Метапрограммирование в Java (Type Traits)
Так как Java не имеет метапрограммирования C++ шаблонов (где операторы разрешаются компилятором) и перегрузки операторов, мы сэмулировали концепцию **Type Class / Type Traits**:
1. `NumericOps<T>` предоставляет базовые арифметические методы (`add`, `sub`, `mul`).
2. В `NumericTraits.java` заведены статические реализации.
3. `CompileTimeCoefficients.getCornerNumber(Class<T>)` эмулирует `static constexpr`, загружая коэффициенты статически относительно типа.

Это позволило написать `GenericExplicitEulerSolver<T, G>` и `GenericHeatTransferModel<T>`, которые умеют работать с `T`, **не превращая** их принудительно в double!

## 4. Полиморфизм: Runtime vs Compile-time
В `GenericMain.java` мы продемонстрировали разницу:
* **Runtime Polymorphism**: когда `ExplicitEulerStepper` наследуется от интерфейса `IStepperStrategy` (наследие семинаров 6-7). Выбор происходит виртуально.
* **Compile-time Polymorphism**: `GenericExplicitEulerSolver<Float, Grid2D>` обязывает компилятор связать Float-физику, Float-операции и 2D-геометрию статически до исполнения.

## Вывод
Несмотря на суровые ограничения Java Type Erasure по сравнению с шаблонами C++, применение паттернов trait bounds (extends Number, extends Grid2D) и маппинга операций позволяет создать 100% type-safe физико-математическую библиотеку с поддержкой любой точности.
