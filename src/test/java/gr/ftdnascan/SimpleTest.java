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
    public void simpleKitTest() throws Exception {
        String raw = "9. ..>Z645>Z93(?)>Z94(?)-x1 (Big Y. Z93 SNP test or R1a-Z93 SNP pack needed) [L657-]";
        Kit kit1 = new Kit( "", raw, "123123", "Ancestor 1", "Algeria", "1,2,3", 3 );

        assertEquals(9, kit1.section );
        if( !kit1.rawGroup.equals(raw) )
            fail();
        if( !kit1.haplogroup.equals("Z94") )
            fail();
        if( !kit1.country2.equals("Arabia") )
            fail();
        if( !kit1.recommendation.startsWith("Big Y") )
            fail();
        if( !kit1.recommendation.endsWith("pack needed") )
            fail();

    }

    @Test
    public void normalizeSequence() throws Exception {
        String sequence = "M420>YP4141>YP4132>YP4131-x1";
        String normalized = FtdnaSequenceBuilder.normalizeSequence(sequence);
        assertEquals("M420>YP4141>YP4132>YP4131>YP4131-x1", normalized);
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