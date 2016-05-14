package no.uib.lca092.cisqAnalyzerComparator.compare;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class XMLComparatorTest {

	private XMLComparator xmlComparator;

	@Before
	public void setUp() throws Exception {
		this.xmlComparator = new XMLComparator();
	}

	@Test
	public void testGetMetricID() {
		// CISQMM05MethodUnreachable
		// CisqMaintainability05UnreachableFunctions
		List<String> ids = new ArrayList<>();
		ids.add("CISQMM01MetasdfhodUnreachable");
		ids.add("CisqMaintainability02asdfUnreacsdfgsadfgbleFunctions");
		ids.add("CISQMM03MethodUnreachable");
		ids.add("CisqMaintainability04asdfUasdfnreachableFunctions");
		ids.add("CISQMM05MethodUnreachable");
		ids.add("CisqMaintaasdfasdfinability06UnreachableFunctions");
		ids.add("CISQMM07MethodUnreachable");
		ids.add("CisqMaintaasdfasdfinability08UnreachableFunctions");
		ids.add("CISQMM09MethodUnreachable");
		ids.add("CisqMaintasdfasdfainability10UnreachableFunctions");
		ids.add("CISQMM11MethodUnreachable");
		ids.add("CisqMaintasdfasdfainability12sadfUnreachableFunctions");

		for (int i = 0; i < ids.size(); i++) {
			String expected = "M" + String.format("%02d", i + 1);
			String actual = this.xmlComparator.getMetricID(ids.get(i));
			assertEquals(expected, actual);
		}
	}

}
