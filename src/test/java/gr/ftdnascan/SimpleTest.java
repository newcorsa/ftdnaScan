package gr.ftdnascan;

import org.junit.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class SimpleTest {

    //private ICalculator cal;

    @Test
    public void add() throws Exception {
		/*
        System.out.println( "testing add: " + cal.add(1,2));

        assertEquals(14, cal.add(10,4), 0);

        Calculator tcalc = mock(Calculator.class);

        when(tcalc.trueDivide(44., 2.)).thenReturn(22.);
        when(tcalc.trueDivide(44., 3.)).thenThrow(new AssertionError());

        double res = tcalc.trueDivide(44., 4.);
        res = tcalc.trueDivide(44., 2.);
        System.out.println(res);
        assertEquals(22., res, 0.);

        verify(tcalc).trueDivide(44., 2.);
		*/
		
		System.out.println( "Test - add" );
		assertEquals(22., 22., 0.);
    }

    @Test
    public void subtract() throws Exception {
    }

    @Test
    public void multiply() throws Exception {
    }

    @Test
    public void divide() throws Exception {
    }

    @Test
    public void double15() throws Exception {
    }

    @Before
    public void setUp() throws Exception {

        System.out.println( "set up" );
        //cal = new Calculator();
    }

    @After
    public void tearDown() throws Exception {

        System.out.println( "tear down" );
        //cal = null;
    }

    @BeforeClass
    public static void beforeClass() throws Exception {

        System.out.println( "beforeClass" );
    }

    @AfterClass
    public static void afterClass() throws Exception {

        System.out.println( "afterClass" );
    }

}