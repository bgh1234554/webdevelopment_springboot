import org.junit.jupiter.api.*;

public class JUnitCycleTest {
    @BeforeAll //생성된 객체가 없어서 static으로 설정.
    //DB에 연결해야 하거나, 테스트 환경을 초기화 할때 사용.
    //한번만 호출되어야 하기 때문에 static으로 사용.
    static void beforeAll(){
        System.out.println("@BeforeAll");
    }

    @BeforeEach
    //테스트 케이스를 시작하기 전에 매번 실행
    //객체를 초기화하거나 테스트에 필요한 값을 미리 넣을 때 사용.
    //각 인스턴스에 대한 메서드를 호출해야 하기 때문에 static이 아니어야함.
    public void beforeEach(){
        System.out.println("@BeforeEach");
    }

    @Test
    public void test1(){
        System.out.println("@Test1");
    }
    @Test
    public void test2(){
        System.out.println("@Test2");
    }
    @Test
    public void test3(){
        System.out.println("@Test3");
    }

    @AfterAll //다 끝나면 객체가 사라지기 때문에
    static void afterAll(){
        System.out.println("@AfterAll");
    }

    @AfterEach
    //각 테스트 케이스 종료 전 실행. 특정 데이터를 삭제해야 하는 경우 사용.
    public void afterEach(){
        System.out.println("@AfterEach");
    }
}
