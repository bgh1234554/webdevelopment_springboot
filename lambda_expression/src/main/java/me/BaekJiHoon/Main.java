package me.BaekJiHoon;

import java.util.Comparator;
import java.util.function.Function;

/*
    1. 람다식의 정의
        : JAVA8에서 도입된 기능.
        함수형 프로그래밍을 Java에 도입하기 위한 핵심 기능중 하나.

        익명함수를 생성하는 표현식으로, 코드의 간결성 및 가독성을 높이는데 유용하다.

        형식:
            (매개변수) -> {실행문}

        매개변수 : 람다식에서 처리할 입력 값
        화살표 : 자바스크립트에서는 =>(Arrow Function)이라고 부르는 연산자. 매개변수와 실행문을 구현.
        실행문: 람다식이 수행할 작업.

    2. 람다식의 특징
        1) 간결성 : 익명 클래스 구현이나 기존 메서드의 정의 방식보다 짧고 간단하다.
            -> 클래스를 나눠 일일이 메서드를 구현할 필요가 없다.
        2) 함수형 인터페이스 : 함수형 인터페이스란 단 하나의 추상 메서드만 가지는 인터페이스로
            대표적으로는 Runnable, Comparator, Comparable 등이 있다.
        3) 지연 실행 : 람다식은 실행될 때까지 평가되지 않으므로, 코드의 지연 실행이 가능하다.
        4) 컨텍스트 의존성: 람다식의 타입은 함수형 인터페이스를 구현하는 곳에 따라 결정된다.

    3. 기존 메서드 표기법과의 비교 및 대조
        - main에 있음
    4. 장단점
        장점
            1) 코드의 간결성 : 불필요한 코드의 삭제로 가독성이 높아짐.
            2) 효율성 : 불필요한 익명 클래스 생성이 줄어들어 메모리 사용이 최적화된다.
            3) 함수형 프로그래밍 지원 : Java로서의 객체지향에서 벗어나 함수형 프로그래밍을 구현할 수 있는 도구 제공
            4) 코드 유지 보수성 : '간단한 동작을 위한 코드'는 수정 및 유지 보수가 적용된다.
        단점
            1) 복잡한 로직 구현의 어려움 : 긴 메서드를 구현하는 경우에 비효율적이다.
            2) 디버깅의 어려움 : 익명 클래스에 비해 디버깅이 어려울 수 있다.
                -> 오류 상황에서 람다식의 위치 파악이 어렵다.
            3) 함수형 인터페이스 제한 : 함수형 인터페이스를 기반으로 동작하기 때문에,
                모든 경우에 사용할 수 있는 것은 아니다.
 */
public class Main {
    public static void main(String[] args) {
        Comparator<Integer> comparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1-o2;
            }
        };
        //람다 형식으로 하면?
        Comparator<Integer> comparator2 = (o1, o2) -> o1-o2;

        Function<Integer, Integer> square = x->x*x;
        System.out.println(square.apply(5)); //Function 클래스의 함수 호출하는 메서드 -> apply
    }
}